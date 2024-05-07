package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/** "Disassembles" an object into its component parts */
public interface EntityClassMetaData<T> {
    String getName();

    Constructor<T> getConstructor();

    // The Id field must be determined by the presence of the Id annotation
    // The @Id annotation must be done independently
    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();
}
