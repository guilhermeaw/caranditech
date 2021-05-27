package db.fetchers;

import db.Schema;
import models.Employee;

import java.sql.ResultSet;

public class EmployeesFetcher implements Fetcher<Employee> {
    @Override
    public Employee fetch(ResultSet resultSet) throws Exception {
        Schema.Employees E = Schema.Employees.table;

        Employee employee = new Employee();

        employee.setId(resultSet.getInt(E.columns.ID));
        employee.setName(resultSet.getString(E.columns.NAME));
        employee.setCpf(resultSet.getString(E.columns.CPF));
        employee.setPhone(resultSet.getString(E.columns.PHONE));
        employee.setOccupationId(resultSet.getInt(E.columns.REF_OCCUPATION));
        employee.setWingId(resultSet.getInt(E.columns.REF_WING));
        employee.setState(resultSet.getInt(E.columns.STATE));

        return employee;
    }
}
