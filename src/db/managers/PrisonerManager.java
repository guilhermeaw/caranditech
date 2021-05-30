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
    public void create(Prisoner prisoner) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(prisoner, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Prisoner prisoner) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(prisoner, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Prisoner prisoner) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(prisoner, db);
        } finally {
            db.release();
        }
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
