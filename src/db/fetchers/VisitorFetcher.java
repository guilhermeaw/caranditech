package db.fetchers;

import db.Schema;
import models.Visitor;

import java.sql.ResultSet;

public class VisitorFetcher implements Fetcher<Visitor> {
    @Override
    public Visitor fetch(ResultSet resultSet) throws Exception {
        Schema.Visitors V = Schema.Visitors.table;

        Visitor visitor = new Visitor();

        visitor.setId(resultSet.getInt(V.columns.ID));
        visitor.setName(resultSet.getString(V.columns.NAME));
        visitor.setCpf(resultSet.getString(V.columns.CPF));
        visitor.setPhone(resultSet.getString(V.columns.PHONE));
        visitor.setState(resultSet.getInt(V.columns.STATE));

        return visitor;
    }
}
