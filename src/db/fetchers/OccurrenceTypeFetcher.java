package db.fetchers;

import db.Schema;
import models.OccurrenceType;

import java.sql.ResultSet;

public class OccurrenceTypeFetcher implements Fetcher<OccurrenceType> {
    @Override
    public OccurrenceType fetch(ResultSet resultSet) throws Exception {
        Schema.OccurrenceTypes OT = Schema.OccurrenceTypes.table;

        OccurrenceType occurrenceType = new OccurrenceType();

        occurrenceType.setId(resultSet.getInt(OT.columns.ID));
        occurrenceType.setName(resultSet.getString(OT.columns.NAME));
        occurrenceType.setDescription(resultSet.getString(OT.columns.DESCRIPTION));
        occurrenceType.setState(resultSet.getInt(OT.columns.STATE));

        return occurrenceType;
    }
}
