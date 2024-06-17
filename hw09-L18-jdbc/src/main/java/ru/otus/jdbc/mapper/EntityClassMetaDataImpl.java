package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private final Class<T> clazz;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> fields;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                log.error("Error: getConstructor (try to get and set constructor) for class {}", clazz.getName(), e);
                throw new RuntimeException(e);
            }
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                    break;
                }
            }
            if (idField == null) {
                log.error("Error: No field with @Id annotation found in class {}", clazz.getName());
                throw new RuntimeException("No field with @Id annotation found");
            }
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        if (fields == null) {
            fields = List.of(clazz.getDeclaredFields());
        }
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream()
                .filter(field -> !Objects.equals(field, getIdField()))
                .collect(Collectors.toList());
    }
}
