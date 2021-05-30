package db.transactions;

import db.Database;
import db.Schema;
import models.Cell;
import models.PrisonerType;

import java.util.List;

public class PrisonerTypeManagerTransactions {
    public void create(PrisonerType prisonerType, Database db) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        String sql = "insert into " + PT.name +
                "(" +
                PT.columns.NAME        + ", " +
                PT.columns.PROFIT      + ", " +
                PT.columns.DESCRIPTION + ", " +
                PT.columns.STATE       +
                ")"+
                " values" +
                "( " +
                db.quote(prisonerType.getName())        + ", " +
                db.quote(prisonerType.getProfit())      + ", " +
                db.quote(prisonerType.getDescription()) + ", " +
                prisonerType.getState()                 +
                " )";

        db.executeCommand(sql);
    }

    public void update(PrisonerType prisonerType, Database db) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        String sql = "update " + PT.name + " set " +
                PT.columns.NAME + " = " + db.quote(prisonerType.getName()) + ", " +
                PT.columns.PROFIT + " = " + db.quote(prisonerType.getProfit()) + ", " +
                PT.columns.DESCRIPTION + " = " + db.quote(prisonerType.getDescription()) +
                " where " + PT.columns.ID + " = " + prisonerType.getId();

        db.executeCommand(sql);
    }

    public void delete(PrisonerType prisonerType, Database db) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        String sql = "update " + PT.name + " set " +
                PT.columns.STATE + " = " + PrisonerType.STATE_DELETED +
                " where " + PT.columns.ID + " = " + prisonerType.getId();

        db.executeCommand(sql);
    }

    public PrisonerType getById(int id, Database db) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        String sql = PT.select +
                " where " + PT.columns.ID + " = " + id;

        return db.fetchOne(sql, PT.fetcher);
    }

    public List<PrisonerType> getAll(Database db) throws Exception {
        Schema.PrisonerTypes PT = Schema.PrisonerTypes.table;

        String sql = PT.select +
                " where " + PT.columns.STATE + " <> " + PrisonerType.STATE_DELETED;

        return db.fetchMany(sql, PT.fetcher);
    }
}
