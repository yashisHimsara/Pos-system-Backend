package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.Connection;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(com.example.bootstrapposbackend.dto.CustomerDTO customerDTO, Connection connection);
    List<com.example.bootstrapposbackend.dto.CustomerDTO> getAllCustomer(Connection connection);
    boolean updateCustomer(com.example.bootstrapposbackend.dto.CustomerDTO customerDTO, String Id, Connection connection);
    boolean isExistsCustomer(String id,Connection connection);
    boolean deleteCustomer(String id,Connection connection);
    com.example.bootstrapposbackend.dto.CustomerDTO get(String id, Connection connection);
}
