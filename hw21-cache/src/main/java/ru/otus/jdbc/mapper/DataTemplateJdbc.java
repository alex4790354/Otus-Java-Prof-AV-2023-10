package ru.otus.jdbc.mapper;

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
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private EntityClassMetaDataImpl<T> entityClassMetaData;
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }


    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    EntityClassMetaData<T> metaData = getObjMetaData();
                    Constructor<T> constructor = metaData.getConstructor();
                    T obj = constructor.newInstance();
                    setObjFields(metaData, obj, rs);
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
                            setObjFields(metaData, obj, rs);
                            clients.add(obj);
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
        EntityClassMetaData<T> metaData = getObjMetaData();
        List<Object> params = new ArrayList<>();

        // Collect new values for each field (except the ID) from the client object
        for (Field field : metaData.getFieldsWithoutId()) {
            try {
                field.setAccessible(true);
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }

        // The ID should be the last parameter for the WHERE clause of the UPDATE statement
        try {
            Field idField = metaData.getIdField();
            idField.setAccessible(true);
            params.add(idField.get(client));
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }

        // Execute the update statement
        String updateSql = entitySQLMetaData.getUpdateSql();  // Ensure this method returns the correct SQL
        dbExecutor.executeStatement(connection, updateSql, params);
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


    private void setObjFields(EntityClassMetaData<T> metaData, T obj, ResultSet rs) {
        List<Field> fields = metaData.getAllFields();
        Object value;
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
            } catch (NoSuchFieldException | SQLException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
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
}
