package db.fetchers;

import db.Schema;
import models.Visit;

import java.sql.ResultSet;
public class VisitFetcher implements Fetcher<Visit> {

    @Override
    public Visit fetch(ResultSet resultSet) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        Visit visit = new Visit();

        visit.setId(resultSet.getInt(V.columns.ID));
        visit.setScheduleDate(resultSet.getDate(V.columns.SCHEDULE_DATE));
        visit.setPrisonerId(resultSet.getInt(V.columns.REF_PRISONER));
        visit.setVisitorId(resultSet.getInt(V.columns.REF_VISITOR));
        visit.setState(resultSet.getInt(V.columns.STATE));

        return visit;
    }
}
