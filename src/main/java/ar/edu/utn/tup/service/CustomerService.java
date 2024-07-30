package ar.edu.utn.tup.service;

import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.persistence.CustomerDAO;

import java.util.Date;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public boolean createCustomer(String name, String lastName, String address, String telephone, String email, String NID, Date birthday, String password, String imagePath) {
        Customer customer = new Customer(name, lastName, address, telephone, email, NID, birthday, password, imagePath);

        if (customer.getAge() < 18) {
            return false;
        }
        else {
            customerDAO.create(customer);
            return true;
        }
    }

    public Customer findCustomer(String nid, String password) {
        return customerDAO.findCustomer(nid, password);
    }

    public Customer findCustomerByNID(String nid) {
        return customerDAO.findCustomerByNID(nid);
    }

    public void update(Customer customer, String address, String telephone, String email, String password, String imagePath) {
        customer.setAddress(address);
        customer.setTelephoneNumber(telephone);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setProfilePicturePath(imagePath);

        try {
            customerDAO.edit(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
