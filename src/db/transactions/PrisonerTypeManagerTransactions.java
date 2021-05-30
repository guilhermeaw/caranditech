package db.transactions;

import db.Database;
import db.Schema;
import models.PrisonerType;

import java.util.List;

public class PrisonerTypeManagerTransactions {
    public List<PrisonerType> getAll(Database db) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        String sql = PT.select +
                " where " + PT.columns.STATE + " <> " + PrisonerType.STATE_DELETED;;

        return db.fetchMany(sql, PT.fetcher);
    }
}
