package db.transactions;

import db.Database;
import db.Schema;
import models.Occupation;

import java.util.List;

public class OccupationManagerTransactions {
    public void create(Occupation occupation, Database db) throws Exception {
        Schema.Occupations OC = Schema.Occupations.table;

        String sql = "insert into " + OC.name +
                "(" +
                OC.columns.NAME        + ", " +
                OC.columns.DESCRIPTION + ", " +
                OC.columns.STATE + ", " +
                OC.columns.WAGE       +
                ")"+
                " values" +
                "( " +
                db.quote(occupation.getName())        + ", " +
                db.quote(occupation.getDescription()) + ", " +
                occupation.getState() + ", " +
                occupation.getWage()                 +
                " )";

        db.executeCommand(sql);
    }

    public void update(Occupation occupation, Database db) throws Exception {
        Schema.Occupations OC = Schema.Occupations.table;

        String sql = "update " + OC.name + " set " +
                OC.columns.NAME + " = " + db.quote(occupation.getName()) + ", " +
                OC.columns.DESCRIPTION + " = " + db.quote(occupation.getDescription()) + ", " +
                OC.columns.WAGE + " = " + occupation.getWage() +
                " where " + OC.columns.ID + " = " + occupation.getId();

        db.executeCommand(sql);
    }

    public void delete(Occupation occupation, Database db) throws Exception {
        Schema.Occupations OC = Schema.Occupations.table;

        String sql = "update " + OC.name + " set " +
                OC.columns.STATE + " = " + Occupation.STATE_DELETED +
                " where " + OC.columns.ID + " = " + occupation.getId();

        db.executeCommand(sql);
    }

    public Occupation getById(int id, Database db) throws Exception {
        Schema.Occupations OC = Schema.Occupations.table;

        String sql = OC.select +
                " where " + OC.columns.ID + " = " + id;

        return db.fetchOne(sql, OC.fetcher);
    }

    public List<Occupation> getAll(Database db) throws Exception {
        Schema.Occupations OC = Schema.Occupations.table;

        String sql = OC.select +
                " where " + OC.columns.STATE + " <> " + Occupation.STATE_DELETED;

        return db.fetchMany(sql, OC.fetcher);
    }
}
