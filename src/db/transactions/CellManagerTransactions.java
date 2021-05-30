package db.transactions;

import db.Database;
import db.Schema;
import models.Cell;

import java.util.List;

public class CellManagerTransactions {
    public void create(Cell cell, Database db) throws Exception {
    //TODO
    }

    public void update(Cell cell, Database db) throws Exception {
//TODO
    }

    public void delete(Cell cell, Database db) throws Exception {
//TODO
    }

    public Cell getById(int id, Database db) throws Exception {
        //TODO
        return null;
    }

    public List<Cell> getAll(Database db) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        String sql = C.select +
                " where " + C.columns.STATE + " <> " + Cell.STATE_DELETED;

        return db.fetchMany(sql, C.fetcher);
    }
}
