package db.transactions;

import db.Database;
import db.Schema;
import models.Prisoner;

import java.util.List;

public class PrisonerManagerTransactions {
    public void create(Prisoner prisoner, Database db) throws Exception {
        Schema.Prisoners P = Schema.Prisoners.table;

        String sql = "insert into " + P.name +
                "(" +
                P.columns.NAME              + ", " +
                P.columns.ENTER_DATE        + ", " +
                P.columns.EXIT_DATE         + ", " +
                P.columns.REF_CELL          + ", " +
                P.columns.REF_PRISONER_TYPE + ", " +
                P.columns.STATE             +
                ")"+
                " values" +
                "( " +
                db.quote(prisoner.getName())      + ", " +
                db.quote(prisoner.getEnterDate()) + ", " +
                db.quote(prisoner.getExitDate())  + ", " +
                prisoner.getCellId()              + ", " +
                prisoner.getPrisonerTypeId()      + ", " +
                prisoner.getState()               +
                " )";

        db.executeCommand(sql);
    }

    public void update(Prisoner prisoner, Database db) throws Exception {
        Schema.Prisoners P = Schema.Prisoners.table;

        String sql = "update " + P.name + " set " +
                P.columns.NAME + " = " + db.quote(prisoner.getName()) + ", " +
                P.columns.ENTER_DATE + " = " + db.quote(prisoner.getEnterDate()) + ", " +
                P.columns.EXIT_DATE + " = " + db.quote(prisoner.getExitDate()) + ", " +
                P.columns.REF_CELL + " = " + prisoner.getCellId() + ", " +
                P.columns.REF_PRISONER_TYPE + " = " + prisoner.getPrisonerTypeId() + ", " +
                " where " + P.columns.ID + " = " + prisoner.getId();

        db.executeCommand(sql);
    }

    public void delete(Prisoner prisoner, Database db) throws Exception {
        Schema.Prisoners P = Schema.Prisoners.table;

        String sql = "update " + P.name + " set " +
                P.columns.STATE + " = " + Prisoner.STATE_DELETED +
                " where " + P.columns.ID + " = " + prisoner.getId();

        db.executeCommand(sql);
    }

    public Prisoner getById(int id, Database db) throws Exception {
        Schema.Prisoners P = Schema.Prisoners.table;

        String sql = P.select +
                " where " + P.columns.ID + " = " + id;

        return db.fetchOne(sql, P.fetcher);
    }

    public List<Prisoner> getAll(Database db) throws Exception {
        Schema.Prisoners P = Schema.Prisoners.table;

        String sql = P.select +
                " where " + P.columns.STATE + " <> " + Prisoner.STATE_DELETED;

        return db.fetchMany(sql, P.fetcher);
    }
}
