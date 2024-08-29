package ar.edu.utn.tup.controller;

import ar.edu.utn.tup.controller.dto.CustomerDTO;
import ar.edu.utn.tup.controller.dto.CustomerWithBankAccountDTO;
import ar.edu.utn.tup.controller.dto.ResponseDTO;
import ar.edu.utn.tup.controller.validator.CustomerDTOValidator;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.service.BankAccountService;
import ar.edu.utn.tup.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private CustomerDTOValidator customerDTOValidator;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers()
                .stream()
                .map(customerService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findCustomerById(id);

        return customer.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(customerService.convertToDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with ID: " + id));
    }

   @GetMapping("/{id}/bank-account")
    public ResponseEntity<Object> getBankAccounts(@PathVariable Long id) {
        Optional<CustomerWithBankAccountDTO> customerDTO = customerService.getBankAccountsByCustomer(id);

        if (customerDTO.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody Map<String, Object> jsonMap) {
        try {
            customerDTOValidator.validateKeysForCreate(jsonMap);

            CustomerDTO customerDTO = customerService.convertToDTO(jsonMap);

            Customer createdCustomer = customerService.createCustomer(customerDTO);

            ResponseDTO responseDTO = new ResponseDTO(
                    "SUCCESSFUL",
                    "Customer created successfully."
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
        catch (Exception ex) {
            ResponseDTO responseDTO = new ResponseDTO(
                    "FAILED",
                    "Failed to create customer: " + ex.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody Map<String, Object> jsonMap) {
        customerDTOValidator.validateKeysForUpdate(jsonMap);

        CustomerDTO customerDTO = customerService.convertToDTO(jsonMap);

        Customer updatedCustomer = customerService.updateCustomer(id, customerDTO);

        if (updatedCustomer != null) {
            CustomerDTO updatedCustomerDTO = customerService.convertToDTO(updatedCustomer);

            return ResponseEntity.status(HttpStatus.OK).body(updatedCustomerDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        boolean isDeleted = customerService.deleteCustomer(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer with id " + id + " deleted successfully.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found.");
        }
    }
}
