package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.ItemData;
import lk.ijse.entity.Item;
import lk.ijse.dao.custom.ItemData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDataProcess implements ItemData {

    static String SAVE_ITEM = "INSERT INTO item (id,name,price,qty) VALUES (?,?,?,?)";
    static String GET_ITEM = "SELECT * FROM item WHERE id=?";
    static String GET_ALL_ITEM = "SELECT * FROM item";
    static String UPDATE_ITEM = "UPDATE item SET name=?,price=?,qty=? WHERE id=?";
    static String DELETE_ITEM= "DELETE FROM item WHERE id=?";




    @Override
    public boolean save(Item entity, Connection connection) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(SAVE_ITEM);
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2,entity.getName());
            preparedStatement.setDouble(3,entity.getPrice());
            preparedStatement.setInt(4,entity.getQty());

            if (preparedStatement.executeUpdate() != 0) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Item> getAll(Connection connection) {
        List<Item> customerList = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_ALL_ITEM);
            var resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getString("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setQty(resultSet.getInt("qty"));

                customerList.add(item);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public boolean update(Item entity, String Id, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ITEM);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setDouble(2,entity.getPrice());
            preparedStatement.setInt(3,entity.getQty());
            preparedStatement.setString(4,Id);

            if (preparedStatement.executeUpdate() !=0){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isExists(String id, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_ITEM);
            preparedStatement.setString(1,id);

            if (preparedStatement.executeUpdate() !=0){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item get(String id, Connection connection) {
        var item = new Item();
        try {
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                item.setId(resultSet.getString("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setQty(resultSet.getInt("qty"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
