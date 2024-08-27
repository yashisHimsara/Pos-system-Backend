package lk.ijse.controller;

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
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@WebServlet("/customer")
public class CustomerController extends HttpServlet {
    Connection connection;

    private CustomerBO customerBO= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.CUSTOMER);


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
            com.example.bootstrapposbackend.dto.CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), com.example.bootstrapposbackend.dto.CustomerDTO.class);
            String CusId= UUID.randomUUID().toString();
            customerDTO.setId(CusId);
            boolean saveCustomer = customerBO.saveCustomer(customerDTO, connection);
            if (!saveCustomer) {
                resp.getWriter().write("Customer not saved");
            }else {
                resp.getWriter().write("Customer saved successfully");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id == null) {
            GetAllCustomer(req, resp);
        }

        try {
            if (!"application/json".equalsIgnoreCase(req.getContentType())) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected content type: application/json");
                return;
            }

            com.example.bootstrapposbackend.dto.CustomerDTO customer = customerBO.get(id, connection);
            PrintWriter writer = resp.getWriter();
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(customer,writer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!"application/json".equalsIgnoreCase(req.getContentType())) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected content type: application/json");
                return;
            }
            Jsonb jsonb = JsonbBuilder.create();
            com.example.bootstrapposbackend.dto.CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), com.example.bootstrapposbackend.dto.CustomerDTO.class);
            boolean updateCustomer = customerBO.updateCustomer(customerDTO, String.valueOf(customerDTO.getId()),connection);
            if (updateCustomer) {
                resp.getWriter().write("Customer update saved");
            }else {
                resp.getWriter().write("Customer update successfully");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!"application/json".equalsIgnoreCase(req.getContentType())) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected content type: application/json");
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            com.example.bootstrapposbackend.dto.CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), com.example.bootstrapposbackend.dto.CustomerDTO.class);
            if (customerBO.deleteCustomer(String.valueOf(customerDTO.getId()), connection)) {
                resp.getWriter().write("Delete success");
            }else {
                resp.getWriter().write("Delete not success");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void GetAllCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            if (!"application/json".equalsIgnoreCase(req.getContentType())) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected content type: application/json");
                return;
            }

            List<com.example.bootstrapposbackend.dto.CustomerDTO> allCustomer = customerBO.getAllCustomer(connection);
            PrintWriter writer = resp.getWriter();
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allCustomer,writer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

