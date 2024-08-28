package lk.ijse.dao.custom.impl;

import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.dao.custom.OrderData;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class OrderDataProcess implements OrderData {

    public boolean save(Order order, Connection connection) {
        if (updateOrder(order,connection)){
            return true;
        }else {
            return false;
        }
    }

    public boolean updateOrder(Order order, Connection connection) {
        String UPDATE_ORDER = "UPDATE `order` SET order_date = ?, cus_id = ?, total = ?, cash = ?, discount = ? WHERE order_id = ?";
        String UPDATE_ORDER_DETAIL = "UPDATE `order_detail` SET order_qty = ?, item_price = ? WHERE order_id = ? AND item_id = ?";
        String UPDATE_ITEM = "UPDATE `item` SET qty = item.qty - ? + ? WHERE id = ?";

        PreparedStatement orderStmt = null;
        PreparedStatement orderDetailStmt = null;
        PreparedStatement itemStmt = null;

        try {
            connection.setAutoCommit(false);

            // Update Order
            orderStmt = connection.prepareStatement(UPDATE_ORDER);
            orderStmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            orderStmt.setString(2, order.getCusIdOption());
            orderStmt.setDouble(3, order.getTotal());
            orderStmt.setDouble(4, order.getTxtCash());
            orderStmt.setDouble(5, order.getTxtDiscount());
            orderStmt.setString(6, order.getOrderId());

            if (orderStmt.executeUpdate() == 0) {
                connection.rollback();
                return false;
            }

            // Update Order Details and Item Quantities
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                // Update Order Detail
                orderDetailStmt = connection.prepareStatement(UPDATE_ORDER_DETAIL);
                orderDetailStmt.setInt(1, orderDetail.getOrderQty());
                orderDetailStmt.setDouble(2, orderDetail.getItemPrice());
                orderDetailStmt.setString(3, order.getOrderId());
                orderDetailStmt.setString(4, orderDetail.getItem().getId());

                if (orderDetailStmt.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }

                // Update Item Quantity
                itemStmt = connection.prepareStatement(UPDATE_ITEM);
                itemStmt.setInt(1, orderDetail.getOrderQty());
                itemStmt.setInt(2, orderDetail.getOrderQty());
                itemStmt.setString(3, orderDetail.getItem().getId());

                if (itemStmt.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (orderStmt != null) orderStmt.close();
                if (orderDetailStmt != null) orderDetailStmt.close();
                if (itemStmt != null) itemStmt.close();
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }


    @Override
    public List<Order> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean update(Order entity, String Id, Connection connection) {
        return false;
    }

    @Override
    public boolean isExists(String id, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }

    @Override
    public Order get(String id, Connection connection) {
        return null;
    }
}
