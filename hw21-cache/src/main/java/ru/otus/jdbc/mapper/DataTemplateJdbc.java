package ru.otus.jdbc.mapper;

import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;


/** Saves an object to the database, reads an object from the database */
@SuppressWarnings({"java:S1068", "java:S3011"})
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private EntityClassMetaDataImpl<T> entityClassMetaData;
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final HwCache<Long, T> cache;

    public DataTemplateJdbc(HwCache<Long, T> cache, DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.cache = cache;
    }


    @Override
    public Optional<T> findById(Connection connection, long id) {
        T clientFromCache = cache.get(id);
        if (clientFromCache != null) {
            return Optional.of(clientFromCache);
        }
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    EntityClassMetaData<T> metaData = getObjMetaData();
                    Constructor<T> constructor = metaData.getConstructor();
                    T obj = constructor.newInstance();
                    setObjFieldsAndGetId(metaData, obj, rs);
                    cache.put(id, obj);
                    return obj;
                }
                return null;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    List<T> clients = new ArrayList<>();
                    try {

                        while (rs.next()) {
                            EntityClassMetaData<T> metaData = getObjMetaData();
                            Constructor<T> constructor = metaData.getConstructor();
                            T obj = constructor.newInstance();
                            long id = setObjFieldsAndGetId(metaData, obj, rs);
                            clients.add(obj);
                            if (cache.get(id) == null) {
                                cache.put(id, obj);
                            }
                        }
                        return clients;
                    } catch (SQLException
                            | InstantiationException
                            | IllegalAccessException
                            | InvocationTargetException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        EntityClassMetaData<T> metaData = getObjMetaData();
        List<Object> params = getInsertParams(metaData, client);
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    private List<Object> getInsertParams(EntityClassMetaData<T> metaData, T client) {
        List<String> fields =
                metaData.getFieldsWithoutId().stream().map(Field::getName).toList();
        List<Object> params = new ArrayList<>();
        for (String field : fields) {
            try {
                Field f = client.getClass().getDeclaredField(field);
                f.setAccessible(true);
                params.add(f.get(client));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
        return params;
    }

    @SuppressWarnings("unchecked")
    private EntityClassMetaData<T> getObjMetaData() {
        if (entityClassMetaData == null) {
            try {
                Field field = entitySQLMetaData.getClass().getDeclaredField("entityClassMetaData");
                field.setAccessible(true);
                entityClassMetaData = (EntityClassMetaDataImpl<T>) field.get(entitySQLMetaData);
                return entityClassMetaData;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        } else return entityClassMetaData;
    }

    private long setObjFieldsAndGetId(EntityClassMetaData<T> metaData, T obj, ResultSet rs) {
        List<Field> fields = metaData.getAllFields();
        Object value;
        long id = 0;
        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                Field objField = obj.getClass().getDeclaredField(fieldName);
                objField.setAccessible(true);
                Class<?> fieldType = field.getType();

                if (fieldType.equals(Long.class)) {
                    value = rs.getLong(fieldName);
                } else if (fieldType.equals(String.class)) {
                    value = rs.getString(fieldName);
                } else {
                    throw new DataTemplateException("Unknown class type");
                }
                objField.set(obj, value);
                if (objField.getDeclaredAnnotation(Id.class) != null) {
                    id = (long) value;
                }
            } catch (NoSuchFieldException | SQLException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
        return id;
    }
}
