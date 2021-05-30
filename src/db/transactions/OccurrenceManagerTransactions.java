package db.transactions;

import db.Database;
import db.Schema;
import models.Occurrence;

import java.util.List;

public class OccurrenceManagerTransactions {
    public List<Occurrence> getAll(Database db) throws Exception {
        Schema.Occurrences OC = Schema.Occurrences.table;

        String sql = OC.select +
                " where " + OC.columns.STATE + " <> " + Occurrence.STATE_DELETED;;

        return db.fetchMany(sql, OC.fetcher);
    }
}
