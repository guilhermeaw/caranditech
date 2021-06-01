package db.transactions;

import db.Database;
import db.Schema;
import models.Employee;

import java.util.List;

public class EmployeeManagerTransactions {
    public void create(Employee employee, Database db) throws Exception {
        Schema.Employees E = Schema.Employees.table;

        String sql = "insert into " + E.name +
                "(" +
                E.columns.NAME              + ", " +
                E.columns.CPF        + ", " +
                E.columns.PHONE         + ", " +
                E.columns.REF_OCCUPATION          + ", " +
                E.columns.REF_WING + ", " +
                E.columns.STATE             +
                ")"+
                " values" +
                "( " +
                db.quote(employee.getName())      + ", " +
                db.quote(employee.getCpf()) + ", " +
                db.quote(employee.getPhone())  + ", " +
                employee.getOccupationId()              + ", " +
                employee.getWingId()      + ", " +
                employee.getState()               +
                " )";

        db.executeCommand(sql);
    }

    public void update(Employee employee, Database db) throws Exception {
        Schema.Employees E = Schema.Employees.table;

        String sql = "update " + E.name + " set " +
                E.columns.NAME + " = " + db.quote(employee.getName()) + ", " +
                E.columns.CPF + " = " + db.quote(employee.getCpf()) + ", " +
                E.columns.PHONE + " = " + db.quote(employee.getPhone()) + ", " +
                E.columns.REF_OCCUPATION + " = " + employee.getOccupationId() + ", " +
                E.columns.REF_WING + " = " + employee.getWingId() +
                " where " + E.columns.ID + " = " + employee.getId();

        db.executeCommand(sql);
    }

    public void delete(Employee employee, Database db) throws Exception {
        Schema.Employees E = Schema.Employees.table;

        String sql = "update " + E.name + " set " +
                E.columns.STATE + " = " + Employee.STATE_DELETED +
                " where " + E.columns.ID + " = " + employee.getId();

        db.executeCommand(sql);
    }

    public Employee getById(int id, Database db) throws Exception {
        Schema.Employees P = Schema.Employees.table;

        String sql = P.select +
                " where " + P.columns.ID + " = " + id;

        return db.fetchOne(sql, P.fetcher);
    }

    public List<Employee> getAll(Database db) throws Exception {
        Schema.Employees E = Schema.Employees.table;

        String sql = E.select +
                " where " + E.columns.STATE + " <> " + Employee.STATE_DELETED;

        return db.fetchMany(sql, E.fetcher);
    }
}
