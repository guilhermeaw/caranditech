package db.fetchers;

import java.sql.ResultSet;

public interface Fetcher<T> {
    public T fetch(ResultSet resultSet) throws Exception;
}
