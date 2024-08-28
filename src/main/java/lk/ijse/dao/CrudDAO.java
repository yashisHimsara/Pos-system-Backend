package lk.ijse.dao;

import java.sql.Connection;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    boolean save(T entity, Connection connection);
    List<T> getAll(Connection connection);
    boolean update(T entity, String Id, Connection connection);
    boolean isExists(String id,Connection connection);
    boolean delete(String id,Connection connection);
    T get(String id,Connection connection);
}
