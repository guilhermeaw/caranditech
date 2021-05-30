package db.managers;

import db.Database;
import db.transactions.OccurrenceManagerTransactions;
import models.Occurrence;

import java.util.List;

public class OccurrenceManager implements DefaultManager<Occurrence> {
    private static OccurrenceManager instance;
    private static OccurrenceManagerTransactions transactions;

    public static OccurrenceManager getInstance() {
        if (instance == null) {
            instance = new OccurrenceManager();
        }

        return instance;
    }

    private OccurrenceManager() { transactions = new OccurrenceManagerTransactions(); }

    @Override
    public void create(Occurrence value) throws Exception {
        //todo
    }

    @Override
    public void update(Occurrence value) throws Exception {
//todo
    }

    @Override
    public void delete(Occurrence value) throws Exception {
//todo
    }

    @Override
    public Occurrence getById(int id) throws Exception {
        //todo
        return null;
    }

    @Override
    public List<Occurrence> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
