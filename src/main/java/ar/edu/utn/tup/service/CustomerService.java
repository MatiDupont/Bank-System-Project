package ar.edu.utn.tup.service;

import ar.edu.utn.tup.controller.dto.BankAccountDTO;
import ar.edu.utn.tup.controller.dto.CustomerDTO;
import ar.edu.utn.tup.controller.dto.CustomerWithBankAccountDTO;
import ar.edu.utn.tup.controller.validator.CustomerDTOValidator;
import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.persistence.CustomerDAO;
import ar.edu.utn.tup.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerDTOValidator customerDTOValidator;
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

    // Metodos para el Controller REST.
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerWithBankAccountDTO> getBankAccountsByCustomer(Long id) {
        return findCustomerById(id).map(this::convertToDTOWithAccounts);
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        customerDTOValidator.validate(customerDTO);
        customerDTOValidator.validateUniqueFieldsForCreate(customerDTO);

        Customer customer = convertToEntity(customerDTO);

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        customerDTOValidator.validateUniqueFieldsForUpdate(id, customerDTO);

        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setAddress(customerDTO.getAddress());
                    existingCustomer.setTelephoneNumber(customerDTO.getTelephoneNumber());
                    existingCustomer.setEmail(customerDTO.getEmail());
                    existingCustomer.setPassword(customerDTO.getPassword());

                    return customerRepository.save(existingCustomer);
                })
                .orElse(null);
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);

            return true;
        }
        else {
            return false;
        }
    }

    public CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setTelephoneNumber(customer.getTelephoneNumber());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setNID(customer.getNID());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setBirthday(String.valueOf(customer.getBirthday()));
        customerDTO.setProfilePicturePath(customer.getProfilePicturePath());

        return customerDTO;
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAddress(customerDTO.getAddress());
        customer.setTelephoneNumber(customerDTO.getTelephoneNumber());
        customer.setEmail(customerDTO.getEmail());
        customer.setNID(customerDTO.getNID());
        customer.setPassword(customerDTO.getPassword());

        // Convertir String a LocalDate y luego a Date

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate birthday = LocalDate.parse(customerDTO.getBirthday(), formatter);
        customer.setBirthday(Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        customer.setProfilePicturePath(customerDTO.getProfilePicturePath());

        return customer;
    }

    public CustomerDTO convertToDTO(Map<String, Object> jsonMap) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(jsonMap, CustomerDTO.class);
    }

    public CustomerWithBankAccountDTO convertToDTOWithAccounts(Customer customer) {
        CustomerWithBankAccountDTO customerDTO = new CustomerWithBankAccountDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setNID(customer.getNID());

        Set<BankAccountDTO> bankAccountDTOs = customer.getBankAccounts().stream().map(this::convertToDTO).collect(Collectors.toSet());

        customerDTO.setBankAccounts(bankAccountDTOs);

        return customerDTO;
    }

    public BankAccountDTO convertToDTO(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setBankEntity(bankAccount.getBankingEntities().getDescription());
        bankAccountDTO.setAlias(bankAccount.getAlias());
        bankAccountDTO.setAccountType(bankAccount.getAccountType().getDescription());
        bankAccountDTO.setCoinType(bankAccount.getCoin().getDescription());
        bankAccountDTO.setCustomerNID(bankAccount.getCustomer().getNID());
        bankAccountDTO.setCBU(bankAccount.getCBU());
        bankAccountDTO.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDTO.setSecurityCode(bankAccount.getSecurityCode());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setOpeningDate(String.valueOf(bankAccount.getOpeningDate()));
        bankAccountDTO.setAccountState(String.valueOf(bankAccount.isAccountState()));

        return bankAccountDTO;
    }
}
