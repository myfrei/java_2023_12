package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.core.repository.DataTemplate;
import ru.otus.jdbc.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        try {
            return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
                try {
                    if (resultSet.next()) {
                        return getResultFields(resultSet);
                    }
                } catch (SQLException e) {
                    log.error("Error in findById while processing resultSet for id = {}", id, e);
                }
                return null;
            });
        } catch (Exception e) {
            log.error("Error in findById for id = {}", id, e);
            throw new RuntimeException(e);
        }
    }

    private T getResultFields(ResultSet rs) {
        try {
            T obj = entityClassMetaData.getConstructor().newInstance();
            Map<String, Field> fields = entityClassMetaData.getAllFields()
                    .stream()
                    .collect(Collectors.toMap(Field::getName, Function.identity()));

            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                Field field = fields.get(columnName);
                if (field != null) {
                    setField(field, obj, rs.getObject(i));
                }
            }
            return obj;
        } catch (Exception e) {
            log.error("Error in getResultFields", e);
            throw new RuntimeException(e);
        }
    }

    private void setField(Field field, Object obj, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("Error in setField for field: {}", field.getName(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> findAll(Connection connection) {
        try {
            return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultSet -> {
                List<T> result = new ArrayList<>();
                try {
                    while (resultSet.next()) {
                        result.add(getResultFields(resultSet));
                    }
                } catch (SQLException e) {
                    log.error("Error in findAll while processing resultSet", e);
                    throw new RuntimeException(e);
                }
                return result;
            }).orElseGet(Collections::emptyList);
        } catch (Exception e) {
            log.error("Error in findAll", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getValuesOfFields(client, false));
        } catch (Exception e) {
            log.error("Error in insert for client: {}", client, e);
            throw new RuntimeException(e);
        }
    }

    private List<Object> getValuesOfFields(T client, boolean includeId) {
        try {
            List<Object> values = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                values.add(getFieldValue(field, client));
            }
            if (includeId) {
                values.add(getFieldValue(entityClassMetaData.getIdField(), client));
            }
            return values;
        } catch (Exception e) {
            log.error("Error in getValuesOfFields for client: {}", client, e);
            throw new RuntimeException(e);
        }
    }

    private Object getFieldValue(Field field, T client) {
        try {
            field.setAccessible(true);
            return field.get(client);
        } catch (IllegalAccessException e) {
            log.error("Error in getFieldValue for field: {}", field.getName(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getValuesOfFields(client, true));
        } catch (Exception e) {
            log.error("Error in update for client: {}", client, e);
            throw new RuntimeException(e);
        }
    }
}