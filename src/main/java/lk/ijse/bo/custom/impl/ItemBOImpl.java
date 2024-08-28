package lk.ijse.bo.custom.impl;

import java.sql.Connection;
import java.util.ArrayList;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dao.custom.ItemData;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ItemBOImpl implements ItemBO {
    ItemData itemData= (ItemData) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ITEM);

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        return itemData.save(new Item(itemDTO.getId(),itemDTO.getName(),itemDTO.getPrice(),itemDTO.getQty()),connection);
    }

    @Override
    public List<ItemDTO> getAllItem(Connection connection) {
        List<Item> all = itemData.getAll(connection);
        List<ItemDTO> itemDTOS=new ArrayList<>();

        for (Item item: all) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setQty(item.getQty());

            itemDTOS.add(itemDTO);
        }

        return itemDTOS;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO, String Id, Connection connection) {
        return itemData.update(new Item(itemDTO.getId(),itemDTO.getName(),itemDTO.getPrice(),itemDTO.getQty()), Id, connection);
    }

    @Override
    public boolean isExistsItem(String id, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteItem(String id, Connection connection) {
        return itemData.delete(id, connection);
    }

    @Override
    public ItemDTO get(String id, Connection connection) {
        Item item = itemData.get(id, connection);
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setQty(item.getQty());

        return itemDTO;
    }
}

