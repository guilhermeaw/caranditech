package db.transactions;

import db.Database;
import db.Schema;
import filters.data.OccurrenceFilter;
import models.Occurrence;
import models.Occurrence;

import java.util.List;

public class OccurrenceManagerTransactions {
    public void create(Occurrence occurrence, Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = "insert into " + OC.name +
                "(" +
                OC.columns.TITLE              + ", " +
                OC.columns.DESCRIPTION        + ", " +
                OC.columns.CREATED_DATE         + ", " +
                OC.columns.REF_PRISONER          + ", " +
                OC.columns.REF_USER + ", " +
                OC.columns.STATE + ", " +
                OC.columns.REF_OCCURRENCE_TYPE             +
                ")"+
                " values" +
                "( " +
                db.quote(occurrence.getTitle())      + ", " +
                db.quote(occurrence.getDescription()) + ", " +
                db.quote(occurrence.getCreatedDate())  + ", " +
                occurrence.getPrisonerId()              + ", " +
                occurrence.getAuthorId()      + ", " +
                occurrence.getState()      + ", " +
                occurrence.getOccurrenceTypeId()               +
                " )";

        db.executeCommand(sql);
    }

    public void update(Occurrence occurrence, Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = "update " + OC.name + " set " +
                OC.columns.TITLE + " = " + db.quote(occurrence.getTitle()) + ", " +
                OC.columns.DESCRIPTION + " = " + db.quote(occurrence.getDescription()) + ", " +
                OC.columns.CREATED_DATE + " = " + db.quote(occurrence.getCreatedDate()) + ", " +
                OC.columns.REF_PRISONER + " = " + occurrence.getPrisonerId() + ", " +
                OC.columns.REF_USER + " = " + occurrence.getAuthorId() + ", " +
                OC.columns.REF_OCCURRENCE_TYPE + " = " + occurrence.getOccurrenceTypeId() +
                " where " + OC.columns.ID + " = " + occurrence.getId();

        db.executeCommand(sql);
    }

    public void delete(Occurrence occurrence, Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = "update " + OC.name + " set " +
                OC.columns.STATE + " = " + Occurrence.STATE_DELETED +
                " where " + OC.columns.ID + " = " + occurrence.getId();

        db.executeCommand(sql);
    }

    public Occurrence getById(int id, Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = OC.select +
                " where " + OC.columns.ID + " = " + id;

        return db.fetchOne(sql, OC.fetcher);
    }

    public List<Occurrence> getAll(Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = OC.select +
                " where " + OC.columns.STATE + " <> " + Occurrence.STATE_DELETED;

        return db.fetchMany(sql, OC.fetcher);
    }

    public List<Occurrence> getByFilter(OccurrenceFilter filter, Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = OC.select;
        sql += composeFilterConditions(filter, db);

        return db.fetchMany(sql, OC.fetcher);
    }

    private String composeFilterConditions(OccurrenceFilter filter, Database db) {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = " where " + OC.columns.STATE + " = " + filter.getState();

        if (filter.getOccurrenceType() != null) {
            sql += " and " + OC.columns.REF_OCCURRENCE_TYPE + " = " + filter.getOccurrenceType().getId();
        }

        if (filter.getAuthor() != null) {
            sql += " and " + OC.columns.REF_USER + " = " + filter.getAuthor().getId();
        }

        if (filter.getTitle() != null && !filter.getTitle().trim().isEmpty()) {
            sql += " and " + OC.columns.TITLE + " like " + db.quote("%" + filter.getTitle() + "%");
        }

        if (filter.getPrisoner() != null) {
            sql += " and " + OC.columns.REF_PRISONER + " = " + filter.getPrisoner().getId();
        }

        if (filter.getStartDateRange() != null && filter.getEndDateRange() != null) {
            sql += " and " + OC.columns.CREATED_DATE + " >= " + db.quote(filter.getStartDateRange());
            sql += " and " + OC.columns.CREATED_DATE + " <= " + db.quote(filter.getEndDateRange());
        }

        return sql;
    }
}
