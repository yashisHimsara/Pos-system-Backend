package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;

import java.sql.Connection;
import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO itemDTO, Connection connection);
    List<ItemDTO> getAllItem(Connection connection);
    boolean updateItem(ItemDTO itemDTO, String Id, Connection connection);
    boolean isExistsItem(String id,Connection connection);
    boolean deleteItem(String id,Connection connection);
    ItemDTO get(String id,Connection connection);
}
