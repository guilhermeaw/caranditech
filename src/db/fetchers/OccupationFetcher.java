package db.fetchers;

import db.Schema;
import models.Occupation;

import java.sql.ResultSet;

public class OccupationFetcher implements Fetcher<Occupation> {
    @Override
    public Occupation fetch(ResultSet resultSet) throws Exception {
        Schema.Occupations OC = Schema.Occupations.table;

        Occupation occupation = new Occupation();

        occupation.setId(resultSet.getInt(OC.columns.ID));
        occupation.setName(resultSet.getString(OC.columns.NAME));
        occupation.setDescription(resultSet.getString(OC.columns.DESCRIPTION));
        occupation.setState(resultSet.getInt(OC.columns.STATE));

        return occupation;
    }
}
