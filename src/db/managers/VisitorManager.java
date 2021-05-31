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
    public void create(Visitor visitor) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(visitor, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Visitor visitor) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(visitor, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Visitor visitor) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(visitor, db);
        } finally {
            db.release();
        }
    }

    @Override
    public Visitor getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
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
