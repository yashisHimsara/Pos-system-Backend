package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderData;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;

import java.sql.Connection;

public class OrderBOImpl implements OrderBO {

    OrderData orderData= (OrderData) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER);

    @Override
    public boolean saveOrder(OrderDTO orderDTO, Connection connection) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setCusIdOption(orderDTO.getCusIdOption());
        order.setItemIdOption(orderDTO.getItemIdOption());
        order.setOrderQty(orderDTO.getOrderQty());
        order.setTotal(orderDTO.getTotal());
        order.setTxtCash(orderDTO.getTxtCash());
        order.setTxtDiscount(orderDTO.getTxtDiscount());

        for (ItemDTO itemDTO : orderDTO.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setItem(new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice(), itemDTO.getQty()));
            orderDetail.setOrderQty(orderDTO.getOrderQty());
            orderDetail.setItemPrice(itemDTO.getPrice());
            order.getOrderDetails().add(orderDetail);
        }

        boolean save = orderData.save(order, connection);
        if (save) {
            return true;
        }else {
            return false;
        }
    }
}
