package db.transactions;

import db.Database;
import db.Schema;
import models.Cell;

import java.util.List;

public class CellManagerTransactions {
    public void create(Cell cell, Database db) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        String sql = "insert into " + C.name +
                "(" +
                C.columns.NAME        + ", " +
                C.columns.REF_WING    + ", " +
                C.columns.STATE       +
                ")"+
                " values" +
                "( " +
                db.quote(cell.getName()) + ", " +
                cell.getWingId()         + ", " +
                cell.getState()          +
                " )";

        db.executeCommand(sql);
    }

    public void update(Cell cell, Database db) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        String sql = "update " + C.name + " set " +
                C.columns.NAME + " = " + db.quote(cell.getName()) + ", " +
                C.columns.REF_WING + " = " + cell.getWingId() +
                " where " + C.columns.ID + " = " + cell.getId();

        db.executeCommand(sql);
    }

    public void delete(Cell cell, Database db) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        String sql = "update " + C.name + " set " +
                C.columns.STATE + " = " + Cell.STATE_DELETED +
                " where " + C.columns.ID + " = " + cell.getId();

        db.executeCommand(sql);
    }

    public Cell getById(int id, Database db) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        String sql = C.select +
                " where " + C.columns.ID + " = " + id;

        return db.fetchOne(sql, C.fetcher);
    }

    public List<Cell> getAll(Database db) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        String sql = C.select +
                " where " + C.columns.STATE + " <> " + Cell.STATE_DELETED;

        return db.fetchMany(sql, C.fetcher);
    }
}
