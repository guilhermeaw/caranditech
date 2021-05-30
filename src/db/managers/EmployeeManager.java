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
    public void create(Employee value) throws Exception {
        //todo
    }

    @Override
    public void update(Employee value) throws Exception {
//todo
    }

    @Override
    public void delete(Employee value) throws Exception {
//todo
    }

    @Override
    public Employee getById(int id) throws Exception {
        //todo
        return null;
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
