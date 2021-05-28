package db.fetchers;

import db.Schema;
import models.Prisoner;

import java.sql.ResultSet;

public class PrisonerFetcher implements Fetcher<Prisoner> {
    @Override
    public Prisoner fetch(ResultSet resultSet) throws Exception {
        Schema.Prisoners P = Schema.Prisoners.table;

        Prisoner prisoner = new Prisoner();

        prisoner.setId(resultSet.getInt(P.columns.ID));
        prisoner.setName(resultSet.getString(P.columns.NAME));
        prisoner.setEnterDate(resultSet.getDate(P.columns.ENTER_DATE));
        prisoner.setExitDate(resultSet.getDate(P.columns.EXIT_DATE));
        prisoner.setCellId(resultSet.getInt(P.columns.REF_CELL));
        prisoner.setPrisonerTypeId(resultSet.getInt(P.columns.REF_PRISONER_TYPE));
        prisoner.setState(resultSet.getInt(P.columns.STATE));

        return prisoner;
    }
}
