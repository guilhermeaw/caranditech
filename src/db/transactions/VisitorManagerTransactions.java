package db.transactions;

import db.Database;
import db.Schema;
import models.Visitor;

import java.util.List;

public class VisitorManagerTransactions {
    public List<Visitor> getAll(Database db) throws Exception {
        Schema.Visitors V = Schema.Visitors.table;

        String sql = V.select +
                " where " + V.columns.STATE + " <> " + Visitor.STATE_DELETED;;

        return db.fetchMany(sql, V.fetcher);
    }
}
