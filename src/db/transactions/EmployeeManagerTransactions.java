package db.transactions;

import db.Database;
import db.Schema;
import models.Employee;

import java.util.List;

public class EmployeeManagerTransactions {
    public List<Employee> getAll(Database db) throws Exception {
        Schema.Employees E = Schema.Employees.table;

        String sql = E.select +
                " where " + E.columns.STATE + " <> " + Employee.STATE_DELETED;;

        return db.fetchMany(sql, E.fetcher);
    }
}
