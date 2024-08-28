package lk.ijse.dao.custom;

import lk.ijse.entity.User;
import lk.ijse.dao.CrudDAO;

import java.sql.Connection;

public interface UserData extends CrudDAO<User> {
    boolean isExistsUser(String email, String password, Connection connection);
}
