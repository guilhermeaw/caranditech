package db.managers;

import java.util.List;

public interface DefaultManager<T> {
    public void create( T value );

    public void update( T value );

    public void delete( T value );

    public T getById( int id );

    public List<T> getAll();
}
