package db.managers;

import db.Database;
import db.transactions.PrisonerTypeManagerTransactions;
import models.PrisonerType;

import java.util.List;

public class PrisonerTypeManager implements DefaultManager<PrisonerType> {
    private static PrisonerTypeManager instance;
    private static PrisonerTypeManagerTransactions transactions;

    public static PrisonerTypeManager getInstance() {
        if (instance == null) {
            instance = new PrisonerTypeManager();
        }

        return instance;
    }

    private PrisonerTypeManager() { transactions = new PrisonerTypeManagerTransactions(); }

    @Override
    public void create(PrisonerType prisonerType) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(prisonerType, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(PrisonerType prisonerType) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(prisonerType, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(PrisonerType prisonerType) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(prisonerType, db);
        } finally {
            db.release();
        }
    }

    @Override
    public PrisonerType getById(int id) throws Exception {
        //todo
        return null;
    }

    @Override
    public List<PrisonerType> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
