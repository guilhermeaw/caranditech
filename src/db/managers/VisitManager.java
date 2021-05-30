package db.managers;

import db.Database;
import db.transactions.VisitManagerTransactions;
import models.Visit;

import java.util.List;

public class VisitManager implements DefaultManager<Visit> {
    private static VisitManager instance;
    private static VisitManagerTransactions transactions;

    public static VisitManager getInstance() {
        if (instance == null) {
            instance = new VisitManager();
        }

        return instance;
    }

    private VisitManager() { transactions = new VisitManagerTransactions(); }

    @Override
    public void create(Visit value) throws Exception {

    }

    @Override
    public void update(Visit value) throws Exception {

    }

    @Override
    public void delete(Visit value) throws Exception {

    }

    @Override
    public Visit getById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Visit> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
