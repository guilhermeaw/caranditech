package db.transactions;

import db.Database;
import db.Schema;
import filters.data.VisitFilter;
import models.Visit;

import java.util.List;

public class VisitManagerTransactions {
    public void create(Visit visit, Database db) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        String sql = "insert into " + V.name +
                "(" +
                V.columns.SCHEDULE_DATE              + ", " +
                V.columns.REF_PRISONER        + ", " +
                V.columns.REF_VISITOR         + ", " +
                V.columns.STATE             +
                ")"+
                " values" +
                "( " +
                db.quote(visit.getScheduleDate())      + ", " +
                visit.getPrisonerId()              + ", " +
                visit.getVisitorId()      + ", " +
                visit.getState()               +
                " )";

        db.executeCommand(sql);
    }

    public void update(Visit visit, Database db) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        String sql = "update " + V.name + " set " +
                V.columns.SCHEDULE_DATE + " = " + db.quote(visit.getScheduleDate()) + ", " +
                V.columns.REF_PRISONER + " = " + visit.getPrisonerId() + ", " +
                V.columns.REF_VISITOR + " = " + visit.getVisitorId() +
                " where " + V.columns.ID + " = " + visit.getId();

        db.executeCommand(sql);
    }

    public void delete(Visit visit, Database db) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        String sql = "update " + V.name + " set " +
                V.columns.STATE + " = " + Visit.STATE_DELETED +
                " where " + V.columns.ID + " = " + visit.getId();

        db.executeCommand(sql);
    }

    public Visit getById(int id, Database db) throws Exception {
        Schema.Visits P = Schema.Visits.table;

        String sql = P.select +
                " where " + P.columns.ID + " = " + id;

        return db.fetchOne(sql, P.fetcher);
    }

    public List<Visit> getAll(Database db) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        String sql = V.select +
                " where " + V.columns.STATE + " <> " + Visit.STATE_DELETED;

        return db.fetchMany(sql, V.fetcher);
    }

    public List<Visit> getByFilter(VisitFilter filter, Database db) throws Exception {
        Schema.Visits OC = Schema.Visits.table;

        String sql = OC.select;
        sql += composeFilterConditions(filter, db);

        return db.fetchMany(sql, OC.fetcher);
    }

    private String composeFilterConditions(VisitFilter filter, Database db) {
        Schema.Visits OC = Schema.Visits.table;

        String sql = " where " + OC.columns.STATE + " = " + filter.getState();

        if (filter.getPrisoner() != null) {
            sql += " and " + OC.columns.REF_PRISONER + " = " + filter.getPrisoner().getId();
        }

        if (filter.getVisitor() != null) {
            sql += " and " + OC.columns.REF_VISITOR + " = " + filter.getVisitor().getId();
        }

        if (filter.getStartScheduleDateRange() != null && filter.getEndScheduleDateRange() != null) {
            sql += " and " + OC.columns.SCHEDULE_DATE + " >= " + db.quote(filter.getStartScheduleDateRange());
            sql += " and " + OC.columns.SCHEDULE_DATE + " <= " + db.quote(filter.getEndScheduleDateRange());
        }

        return sql;
    }

    public boolean hasVisit(Visit visit, Database db) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        String sql = V.select +
                " where " + V.columns.SCHEDULE_DATE + " = " + db.quote(visit.getScheduleDate()) +
                " and " + V.columns.REF_PRISONER + " = " + visit.getPrisonerId();

        return db.fetchOne(sql, V.fetcher) != null;
    }
}
