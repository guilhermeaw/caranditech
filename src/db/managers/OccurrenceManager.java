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
    public void create(Occurrence occurrence) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(occurrence, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Occurrence occurrence) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(occurrence, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Occurrence occurrence) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(occurrence, db);
        } finally {
            db.release();
        }
    }

    @Override
    public Occurrence getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
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
