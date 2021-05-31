package db.managers;

import db.Database;
import db.transactions.EmployeeManagerTransactions;
import models.Employee;

import java.util.List;

public class EmployeeManager implements DefaultManager<Employee> {
    private static EmployeeManager instance;
    private static EmployeeManagerTransactions transactions;

    public static EmployeeManager getInstance() {
        if (instance == null) {
            instance = new EmployeeManager();
        }

        return instance;
    }

    private EmployeeManager() {
        transactions = new EmployeeManagerTransactions();
    }

    @Override
    public void create(Employee employee) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(employee, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(Employee employee) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(employee, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(Employee employee) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(employee, db);
        } finally {
            db.release();
        }
    }

    @Override
    public Employee getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<Employee> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }
}
