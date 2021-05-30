package db.managers;

import db.Database;
import db.transactions.PrisonerManagerTransactions;
import models.Prisoner;

import java.util.List;

public class PrisonerManager implements DefaultManager<Prisoner> {
    private static PrisonerManager instance;
    private static PrisonerManagerTransactions transactions;

    public static PrisonerManager getInstance() {
        if (instance == null) {
            instance = new PrisonerManager();
        }

        return instance;
    }

    private PrisonerManager() { transactions = new PrisonerManagerTransactions(); }

    @Override
    public void create(Prisoner value) throws Exception {
//todo
    }

    @Override
    public void update(Prisoner value) throws Exception {
//todo
    }

    @Override
    public void delete(Prisoner value) throws Exception {
//todo
    }

    @Override
    public Prisoner getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<Prisoner> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
