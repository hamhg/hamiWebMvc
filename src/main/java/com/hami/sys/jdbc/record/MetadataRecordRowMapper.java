package com.hami.sys.jdbc.record;

/**
 * <pre>
 * <li>Program Name : MetadataRecordRowMapper
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class MetadataRecordRowMapper extends RecordRowMapper {
    private RecordMetadata metadata;

    public MetadataRecordRowMapper(RecordMetadata metadata) {
        this.metadata = metadata;
    }

    protected MetadataRecord createRecord() {
        return new MetadataRecord(metadata);
    }
}
