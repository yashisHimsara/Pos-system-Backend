package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.UserData;
import lk.ijse.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDataProcess implements UserData {

    static String SAVE_USER = "INSERT INTO user (username,id,email,password) VALUES (?,?,?,?)";
    static String GET_USER = "SELECT * FROM user WHERE email=? and password=?";

    @Override
    public boolean save(User entity, Connection connection) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(SAVE_USER);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getId());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());

            if (preparedStatement.executeUpdate() != 0) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<User> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean update(User entity, String Id, Connection connection) {
        return false;
    }

    @Override
    public boolean isExists(String id, Connection connection) {
        return false;
    }

    public boolean isExistsUser(String email, String password,Connection connection) {

        try {
            var ps = connection.prepareStatement(GET_USER);
            ps.setString(1, email);
            ps.setString(2, password);
            var resultSet = ps.executeQuery();


            if (resultSet.next()) {
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }

    @Override
    public User get(String id, Connection connection) {
        return null;
    }
}
