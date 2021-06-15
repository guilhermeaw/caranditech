package db.managers;

import db.Database;
import db.transactions.VisitManagerTransactions;
import filters.data.VisitFilter;
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
    public void create(Visit visit) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(visit, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Visit visit) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(visit, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Visit visit) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(visit, db);
        } finally {
            db.release();
        }
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

    public List<Visit> getByFilter(VisitFilter filter) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getByFilter(filter, db);
        } finally {
            db.release();
        }
    }

    public boolean hasVisit(Visit visit) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.hasVisit(visit, db);
        } finally {
            db.release();
        }
    }
}
