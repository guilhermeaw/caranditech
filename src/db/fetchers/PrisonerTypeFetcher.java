package db.fetchers;

import db.Schema;
import models.PrisonerType;

import java.sql.ResultSet;

public class PrisonerTypeFetcher implements Fetcher<PrisonerType> {
    @Override
    public PrisonerType fetch(ResultSet resultSet) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        PrisonerType prisonerType = new PrisonerType();

        prisonerType.setId(resultSet.getInt(PT.columns.ID));
        prisonerType.setName(resultSet.getString(PT.columns.NAME));
        prisonerType.setProfit(resultSet.getDouble(PT.columns.PROFIT));
        prisonerType.setDescription(resultSet.getString(PT.columns.DESCRIPTION));
        prisonerType.setState(resultSet.getInt(PT.columns.STATE));

        return prisonerType;
    }
}
