package db.transactions;

import db.Database;
import db.Schema;
import models.Prisoner;

import java.util.List;

public class PrisonerManagerTransactions {
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
