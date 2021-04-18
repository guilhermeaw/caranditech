package db.managers;

import db.Database;
import db.transactions.OccupationManagerTransactions;
import db.transactions.WingManagerTransactions;
import models.Wing;

import java.util.List;

public class WingManager implements DefaultManager<Wing> {
    private static WingManager instance;
    private static WingManagerTransactions transactions;

    public static WingManager getInstance() {
        if (instance == null) {
            instance = new WingManager();
        }

        return instance;
    }

    private WingManager() {
        transactions = new WingManagerTransactions();
    }

    @Override
    public void create(Wing wing) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(wing, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Wing wing) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(wing, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Wing wing) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(wing, db);
        } finally {
            db.release();
        }
    }

    @Override
    public Wing getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<Wing> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
