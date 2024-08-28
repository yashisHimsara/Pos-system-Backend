package lk.ijse.controller;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dto.OrderDTO;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    Connection connection;

    private OrderBO orderBO= (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.ORDER);


    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
            this.connection = pool.getConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!"application/json".equalsIgnoreCase(req.getContentType())) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected content type: application/json");
                return;
            }
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            String orderId= UUID.randomUUID().toString();
            orderDTO.setOrderId(orderId);
            boolean saveCustomer = orderBO.saveOrder(orderDTO,connection);
            System.out.println(orderDTO.getItems());
            if (!saveCustomer) {
                resp.getWriter().write("order not saved");
            }else {
                resp.getWriter().write("order saved successfully");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
