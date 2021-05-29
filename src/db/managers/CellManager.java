package db.managers;

import db.Database;
import db.transactions.CellManagerTransactions;
import models.Cell;

import java.util.List;

public class CellManager implements DefaultManager<Cell> {
    private static CellManager instance;
    private static CellManagerTransactions transactions;

    public static CellManager getInstance() {
        if (instance == null) {
            instance = new CellManager();
        }

        return instance;
    }

    private CellManager() {
        transactions = new CellManagerTransactions();
    }

    @Override
    public void create(Cell cell) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(cell, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Cell cell) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(cell, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Cell cell) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(cell, db);
        } finally {
            db.release();
        }
    }

    @Override
    public Cell getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<Cell> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
