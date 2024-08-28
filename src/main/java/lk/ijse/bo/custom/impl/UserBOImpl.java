package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserData;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.sql.Connection;
import java.util.UUID;

public class UserBOImpl implements UserBO {

    UserData userData= (UserData) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.USER);

    @Override
    public boolean saveUser(UserDTO userDTO, Connection connection) {
        String id= UUID.randomUUID().toString();
        return userData.save(new User(id,userDTO.getUsername(),userDTO.getEmail(),userDTO.getPassword()),connection);
    }

    @Override
    public boolean isExistsUser(String email, String password, Connection connection) {
        return userData.isExistsUser(email, password, connection);
    }
}
