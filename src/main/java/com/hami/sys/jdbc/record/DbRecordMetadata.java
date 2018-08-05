package com.hami.sys.jdbc.record;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <pre>
 * <li>Program Name : DbRecordMetadata
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class DbRecordMetadata extends RecordMetadata {
    public class ColumnMetadata implements Serializable {

        private static final long serialVersionUID = 1L;
        private String columnName;
        private int columnType;
        private String columnTypeName;
        private int precision;
        private int scale;
        final DbRecordMetadata dbRecordMetadata;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public int getColumnType() {
            return columnType;
        }

        public void setColumnType(int columnType) {
            this.columnType = columnType;
        }

        public String getColumnTypeName() {
            return columnTypeName;
        }

        public void setColumnTypeName(String columnTypeName) {
            this.columnTypeName = columnTypeName;
        }

        public int getPrecision() {
            return precision;
        }

        public void setPrecision(int precision) {
            this.precision = precision;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Column [columnName=");
            builder.append(columnName);
            builder.append(",  columnType=");
            builder.append(columnType);
            builder.append(",  columnTypeName=");
            builder.append(columnTypeName);
            builder.append(",  precision=");
            builder.append(precision);
            builder.append(",  scale=");
            builder.append(scale);
            builder.append("]");
            return builder.toString();
        }

        public ColumnMetadata() {
            super();
            dbRecordMetadata = DbRecordMetadata.this;
        }
    }

    private static final long serialVersionUID = 800989679L;
    private ColumnMetadata dbMetadataArray[];

    public DbRecordMetadata(RecordMetadata metadata) {
        super(metadata);
        init(metadata.size());
    }

    public DbRecordMetadata(String keys[]) {
        super(keys);
        init(keys.length);
    }

    private void init(int length) {
        dbMetadataArray = new ColumnMetadata[length];
    }

    public ColumnMetadata getColumnMetadata(int index) {
        return dbMetadataArray[index];
    }

    public ColumnMetadata getColumnMetadata(String fieldName) {
        return dbMetadataArray[getIndex(fieldName)];
    }

    protected ColumnMetadata[] getColumnMetadata() {
        return dbMetadataArray;
    }

    public void consumeResultSetMetaData(String fieldName, int column, ResultSetMetaData resultSetMetaData) {
        try {
            ColumnMetadata dbMetadata = new ColumnMetadata();
            int thisIndex = getIndex(fieldName);
            dbMetadataArray[thisIndex] = dbMetadata;
            dbMetadata.setColumnName(resultSetMetaData.getColumnLabel(column));
            dbMetadata.setColumnType(resultSetMetaData.getColumnType(column));
            dbMetadata.setColumnTypeName(resultSetMetaData.getColumnTypeName(column));
            dbMetadata.setPrecision(resultSetMetaData.getPrecision(column));
            dbMetadata.setScale(resultSetMetaData.getScale(column));
        } catch (SQLException e) {
            throw new RuntimeException("Extract ResultSetMetaData", e);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DbRecordMetadata [");
        String arr$[] = getKeyArray();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String name = arr$[i$];
            builder.append("\n\t").append(name);
            builder.append(" ,\t").append(getColumnMetadata(name));
        }

        builder.append("\n]");
        return builder.toString();
    }
}
