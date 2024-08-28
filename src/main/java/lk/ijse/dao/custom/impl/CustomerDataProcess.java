package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.CustomerData;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDataProcess implements CustomerData {

    static String SAVE_CUSTOMER = "INSERT INTO customer (id,name,address,salary) VALUES (?,?,?,?)";
    static String GET_CUSTOMER = "SELECT * FROM customer WHERE id=?";
    static String GET_ALL_CUSTOMER = "SELECT * FROM customer";
    static String UPDATE_CUSTOMER = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
    static String DELETE_CUSTOMER= "DELETE FROM customer WHERE id=?";



    @Override
    public boolean save(Customer entity, Connection connection) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(SAVE_CUSTOMER);
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getAddress());
            preparedStatement.setString(4, String.valueOf(entity.getSalary()));

            if (preparedStatement.executeUpdate() != 0) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        List<Customer> customerList = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_ALL_CUSTOMER);
            var resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAddress(resultSet.getString("address"));
                customer.setSalary(resultSet.getDouble("salary"));

                customerList.add(customer);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public boolean update(Customer entity, String Id, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getAddress());
            preparedStatement.setDouble(3,entity.getSalary());
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
            preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
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
    public Customer get(String id, Connection connection) {
        var customer = new Customer();
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customer.setId(resultSet.getString("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAddress(resultSet.getString("address"));
                customer.setSalary(resultSet.getDouble("salary"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

}
