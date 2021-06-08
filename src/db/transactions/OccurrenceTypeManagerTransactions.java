package db.transactions;

import db.Database;
import db.Schema;
import models.OccurrenceType;

import java.util.List;

public class OccurrenceTypeManagerTransactions {
    public void create(OccurrenceType occurrenceType, Database db) throws Exception {
        Schema.OccurrenceTypes OT = Schema.OccurrenceTypes.table;

        String sql = "insert into " + OT.name +
                "(" +
                OT.columns.NAME        + ", " +
                OT.columns.DESCRIPTION    + ", " +
                OT.columns.STATE       +
                ")"+
                " values" +
                "( " +
                db.quote(occurrenceType.getName()) + ", " +
                db.quote(occurrenceType.getDescription())        + ", " +
                occurrenceType.getState()          +
                " )";

        db.executeCommand(sql);
    }

    public void update(OccurrenceType occurrenceType, Database db) throws Exception {
        Schema.OccurrenceTypes OT = Schema.OccurrenceTypes.table;

        String sql = "update " + OT.name + " set " +
                OT.columns.NAME + " = " + db.quote(occurrenceType.getName()) + ", " +
                OT.columns.DESCRIPTION + " = " + db.quote(occurrenceType.getDescription()) +
                " where " + OT.columns.ID + " = " + occurrenceType.getId();

        db.executeCommand(sql);
    }

    public void delete(OccurrenceType occurrenceType, Database db) throws Exception {
        Schema.OccurrenceTypes OT = Schema.OccurrenceTypes.table;

        String sql = "update " + OT.name + " set " +
                OT.columns.STATE + " = " + OccurrenceType.STATE_DELETED +
                " where " + OT.columns.ID + " = " + occurrenceType.getId();

        db.executeCommand(sql);
    }

    public OccurrenceType getById(int id, Database db) throws Exception {
        Schema.OccurrenceTypes OT = Schema.OccurrenceTypes.table;

        String sql = OT.select +
                " where " + OT.columns.ID + " = " + id;

        return db.fetchOne(sql, OT.fetcher);
    }

    public List<OccurrenceType> getAll(Database db) throws Exception {
        Schema.OccurrenceTypes OT = Schema.OccurrenceTypes.table;

        String sql = OT.select +
                " where " + OT.columns.STATE + " <> " + OccurrenceType.STATE_DELETED;

        return db.fetchMany(sql, OT.fetcher);
    }
}
