package db.fetchers;

import db.Schema;
import models.Cell;

import java.sql.ResultSet;

public class CellFetcher implements Fetcher<Cell> {
    @Override
    public Cell fetch(ResultSet resultSet) throws Exception {
        Schema.Cells C = Schema.Cells.table;

        Cell cell = new Cell();

        cell.setId(resultSet.getInt(C.columns.ID));
        cell.setName(resultSet.getString(C.columns.NAME));
        cell.setWingId(resultSet.getInt(C.columns.REF_WING));
        cell.setState(resultSet.getInt(C.columns.STATE));

        return cell;
    }
}
