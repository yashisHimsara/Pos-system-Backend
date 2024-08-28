package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.custom.CustomerData;
import lk.ijse.dao.DAOFactory;
import lk.ijse.entity.Customer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerData customerData= (CustomerData) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.CUSTOMER);


    @Override
    public boolean saveCustomer(lk.ijse.dto.CustomerDTO customerDTO, Connection connection) {
        return customerData.save(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getSalary()),connection);
    }

    @Override
    public List<lk.ijse.dto.CustomerDTO> getAllCustomer(Connection connection) {
        List<Customer> all = customerData.getAll(connection);
        List<lk.ijse.dto.CustomerDTO> customerDTOList=new ArrayList<>();
        for (Customer customer : all) {
            lk.ijse.dto.CustomerDTO customerDTO = new lk.ijse.dto.CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setAddress(customer.getAddress());
            customerDTO.setSalary(customer.getSalary());

            customerDTOList.add(customerDTO);
        }

        return customerDTOList;
    }

    @Override
    public boolean updateCustomer(lk.ijse.dto.CustomerDTO customerDTO, String Id, Connection connection) {
        return customerData.update(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getSalary()),Id,connection);
    }

    @Override
    public boolean isExistsCustomer(String id, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) {
        return customerData.delete(id, connection);
    }

    @Override
    public lk.ijse.dto.CustomerDTO get(String id, Connection connection) {
        Customer customer = customerData.get(id, connection);
        lk.ijse.dto.CustomerDTO customerDTO = new lk.ijse.dto.CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setSalary(customer.getSalary());

        return customerDTO;
    }
//    @Override
//    public lk.ijse.dto.CustomerDTO get(String id, Connection connection) {
//        Customer customer = customerData.get(id, connection);
//        lk.ijse.dto.CustomerDTO customerDTO = new lk.ijse.dto.CustomerDTO();
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setAddress(customer.getAddress());
//        customerDTO.setSalary(customer.getSalary());
//
//        return customerDTO;
//    }

}


