package db.managers;

import db.Database;
import db.transactions.OccurrenceTypeManagerTransactions;
import models.OccurrenceType;

import java.util.List;

public class OccurrenceTypeManager implements DefaultManager<OccurrenceType> {
    private static OccurrenceTypeManager instance;
    private static OccurrenceTypeManagerTransactions transactions;

    public static OccurrenceTypeManager getInstance() {
        if (instance == null) {
            instance = new OccurrenceTypeManager();
        }

        return instance;
    }

    private OccurrenceTypeManager() { transactions = new OccurrenceTypeManagerTransactions(); }

    @Override
    public void create(OccurrenceType occurrenceType) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(occurrenceType, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(OccurrenceType occurrenceType) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(occurrenceType, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(OccurrenceType occurrenceType) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(occurrenceType, db);
        } finally {
            db.release();
        }
    }

    @Override
    public OccurrenceType getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<OccurrenceType> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
