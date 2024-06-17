package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return getSelect(false);
    }

    @Override
    public String getSelectByIdSql() {
        return getSelect(true);
    }

    private String getSelect(boolean isFilterById) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(entityClassMetaData.getName());
        if (isFilterById) {
            sb.append(" WHERE ")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(" = ?");
        }
        return sb.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(entityClassMetaData.getName())
                .append(" (")
                .append(separatedBy(entityClassMetaData.getFieldsWithoutId(), ", "))
                .append(") VALUES (")
                .append(preparePlaceholders(
                        entityClassMetaData.getFieldsWithoutId().size()))
                .append(")");
        return sb.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
                .append(entityClassMetaData.getName())
                .append(" SET ")
                .append(prepareSetClause(entityClassMetaData.getFieldsWithoutId()))
                .append(" WHERE ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?");
        return sb.toString();
    }

    private String prepareSetClause(List<Field> fields) {
        return fields.stream().map(field -> field.getName() + " = ?").collect(Collectors.joining(", "));
    }

    private String preparePlaceholders(int count) {
        return "?".repeat(Math.max(0, count)).replace("", ", ").trim();
    }

    private String separatedBy(List<Field> fields, String separator) {
        return fields.stream().map(Field::getName).collect(Collectors.joining(separator));
    }
}
