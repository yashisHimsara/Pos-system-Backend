package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;

import java.sql.Connection;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO userDTO, Connection connection);
    boolean isExistsUser(String id,String password,Connection connection);
}