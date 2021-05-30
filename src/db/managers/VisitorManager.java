package db.managers;

import db.Database;
import db.transactions.VisitorManagerTransactions;
import models.Visitor;

import java.util.List;

public class VisitorManager implements DefaultManager<Visitor> {
    private static VisitorManager instance;
    private static VisitorManagerTransactions transactions;

    public static VisitorManager getInstance() {
        if (instance == null) {
            instance = new VisitorManager();
        }

        return instance;
    }

    private VisitorManager() { transactions = new VisitorManagerTransactions(); }

    @Override
    public void create(Visitor value) throws Exception {

    }

    @Override
    public void update(Visitor value) throws Exception {

    }

    @Override
    public void delete(Visitor value) throws Exception {

    }

    @Override
    public Visitor getById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Visitor> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
