package db.transactions;

import db.Database;
import db.Schema;
import models.Visitor;

import java.util.List;

public class VisitorManagerTransactions {
    public void create(Visitor visitor, Database db) throws Exception {
        Schema.Visitors V = Schema.Visitors.table;

        String sql = "insert into " + V.name +
                "(" +
                V.columns.NAME              + ", " +
                V.columns.CPF        + ", " +
                V.columns.PHONE         + ", " +
                V.columns.STATE             +
                ")"+
                " values" +
                "( " +
                db.quote(visitor.getName())      + ", " +
                db.quote(visitor.getCpf()) + ", " +
                db.quote(visitor.getPhone())  + ", " +
                visitor.getState()               +
                " )";

        db.executeCommand(sql);
    }

    public void update(Visitor visitor, Database db) throws Exception {
        Schema.Visitors V = Schema.Visitors.table;

        String sql = "update " + V.name + " set " +
                V.columns.NAME + " = " + db.quote(visitor.getName()) + ", " +
                V.columns.CPF + " = " + db.quote(visitor.getCpf()) + ", " +
                V.columns.PHONE + " = " + db.quote(visitor.getPhone()) + ", " +
                " where " + V.columns.ID + " = " + visitor.getId();

        db.executeCommand(sql);
    }

    public void delete(Visitor visitor, Database db) throws Exception {
        Schema.Visitors V = Schema.Visitors.table;

        String sql = "update " + V.name + " set " +
                V.columns.STATE + " = " + Visitor.STATE_DELETED +
                " where " + V.columns.ID + " = " + visitor.getId();

        db.executeCommand(sql);
    }

    public Visitor getById(int id, Database db) throws Exception {
        Schema.Visitors P = Schema.Visitors.table;

        String sql = P.select +
                " where " + P.columns.ID + " = " + id;

        return db.fetchOne(sql, P.fetcher);
    }

    public List<Visitor> getAll(Database db) throws Exception {
        Schema.Visitors V = Schema.Visitors.table;

        String sql = V.select +
                " where " + V.columns.STATE + " <> " + Visitor.STATE_DELETED;

        return db.fetchMany(sql, V.fetcher);
    }
}
