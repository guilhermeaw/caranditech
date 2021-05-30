package db.transactions;

import db.Database;
import db.Schema;
import models.Visit;

import java.util.List;

public class VisitManagerTransactions {
    public List<Visit> getAll(Database db) throws Exception {
        Schema.Visits V = Schema.Visits.table;

        String sql = V.select +
                " where " + V.columns.STATE + " <> " + Visit.STATE_DELETED;;

        return db.fetchMany(sql, V.fetcher);
    }
}
