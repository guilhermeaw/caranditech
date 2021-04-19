package db.fetchers;

import db.Schema;
import models.Wing;

import java.sql.ResultSet;

public class WingFetcher implements Fetcher<Wing> {
    @Override
    public Wing fetch(ResultSet resultSet) throws Exception {
        Schema.Wings W = Schema.Wings.table;

        Wing wing = new Wing();

        wing.setId(resultSet.getInt(W.columns.ID));
        wing.setName(resultSet.getString(W.columns.NAME));
        wing.setState(resultSet.getInt(W.columns.STATE));

        return wing;
    }
}
