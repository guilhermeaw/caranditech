package db.transactions;

import db.Database;
import db.Schema;
import models.Wing;

import java.util.List;

public class WingManagerTransactions {
    public void create(Wing wing, Database db) throws Exception {
        Schema.Wings W = Schema.Wings.table;

        String sql = "insert into " + W.name +
                "(" +
                W.columns.NAME  + ", " +
                W.columns.STATE +
                ")"+
                " values" +
                "( " +
                db.quote(wing.getName()) + ", " +
                wing.getState()          +
                " )";

        db.executeCommand(sql);
    }

    public void update(Wing wing, Database db) throws Exception {
        Schema.Wings W = Schema.Wings.table;

        String sql = "update " + W.name + " set " +
                W.columns.NAME + " = " + db.quote(wing.getName()) +
                " where " + W.columns.ID + " = " + wing.getId();

        db.executeCommand(sql);
    }

    public void delete(Wing wing, Database db) throws Exception {
        Schema.Wings W = Schema.Wings.table;

        String sql = "update " + W.name + " set " +
                W.columns.STATE + " = " + Wing.STATE_DELETED +
                " where " + W.columns.ID + " = " + wing.getId();

        db.executeCommand(sql);
    }

    public Wing getById(int id, Database db) throws Exception {
        Schema.Wings W = Schema.Wings.table;

        String sql = W.select +
                " where " + W.columns.ID + " = " + id;

        return db.fetchOne(sql, W.fetcher);
    }

    public List<Wing> getAll(Database db) throws Exception {
        Schema.Wings W = Schema.Wings.table;

        String sql = W.select +
                " where " + W.columns.STATE + " <> " + Wing.STATE_DELETED;

        return db.fetchMany(sql, W.fetcher);
    }
}
