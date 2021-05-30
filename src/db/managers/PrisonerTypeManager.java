package db.managers;

import db.Database;
import db.transactions.PrisonerTypeManagerTransactions;
import models.PrisonerType;

import java.util.List;

public class PrisonerTypeManager implements DefaultManager<PrisonerType> {
    private static PrisonerTypeManager instance;
    private static PrisonerTypeManagerTransactions transactions;

    public static PrisonerTypeManager getInstance() {
        if (instance == null) {
            instance = new PrisonerTypeManager();
        }

        return instance;
    }

    private PrisonerTypeManager() { transactions = new PrisonerTypeManagerTransactions(); }

    @Override
    public void create(PrisonerType value) throws Exception {
//todo
    }

    @Override
    public void update(PrisonerType value) throws Exception {
//todo
    }

    @Override
    public void delete(PrisonerType value) throws Exception {
//todo
    }

    @Override
    public PrisonerType getById(int id) throws Exception {
        //todo
        return null;
    }

    @Override
    public List<PrisonerType> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
