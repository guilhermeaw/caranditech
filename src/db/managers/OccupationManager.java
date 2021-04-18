package db.managers;

import db.Database;
import db.transactions.OccupationManagerTransactions;
import db.transactions.UserManagerTransactions;
import models.Occupation;

import java.util.List;

public class OccupationManager implements DefaultManager<Occupation> {
    private static OccupationManager instance;
    private static OccupationManagerTransactions transactions;

    public static OccupationManager getInstance() {
        if (instance == null) {
            instance = new OccupationManager();
        }

        return instance;
    }

    private OccupationManager() {
        transactions = new OccupationManagerTransactions();
    }

    @Override
    public void create(Occupation occupation) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(occupation, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Occupation occupation) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(occupation, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Occupation occupation) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(occupation, db);
        } finally {
            db.release();
        }
    }

    @Override
    public Occupation getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<Occupation> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
