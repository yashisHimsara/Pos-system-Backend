package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.Connection;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(lk.ijse.dto.CustomerDTO customerDTO, Connection connection);
    List<lk.ijse.dto.CustomerDTO> getAllCustomer(Connection connection);
    boolean updateCustomer(lk.ijse.dto.CustomerDTO customerDTO, String Id, Connection connection);
    boolean isExistsCustomer(String id,Connection connection);
    boolean deleteCustomer(String id,Connection connection);
    lk.ijse.dto.CustomerDTO get(String id, Connection connection);
}
