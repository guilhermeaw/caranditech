package db.fetchers;

import db.Schema;
import models.Occurrence;

import java.sql.ResultSet;

public class OccurrenceFetcher implements Fetcher<Occurrence> {
    @Override
    public Occurrence fetch(ResultSet resultSet) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        Occurrence occurrence = new Occurrence();

        occurrence.setId(resultSet.getInt(OC.columns.ID));
        occurrence.setTitle(resultSet.getString(OC.columns.TITLE));
        occurrence.setDescription(resultSet.getString(OC.columns.DESCRIPTION));
        occurrence.setCreatedDate(resultSet.getDate(OC.columns.CREATED_DATE));
        occurrence.setPrisonerId(resultSet.getInt(OC.columns.REF_PRISONER));
        occurrence.setAuthorId(resultSet.getInt(OC.columns.REF_USER));
        occurrence.setState(resultSet.getInt(OC.columns.STATE));
        occurrence.setOccurrenceTypeId(resultSet.getInt(OC.columns.REF_OCCURRENCE_TYPE_ID));

        return occurrence;
    }
}
