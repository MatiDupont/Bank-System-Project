package ar.edu.utn.tup.controller.validator;

import ar.edu.utn.tup.controller.dto.CustomerDTO;
import ar.edu.utn.tup.controller.exception.InvalidFieldException;
import ar.edu.utn.tup.controller.exception.UniqueConstraintViolationException;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class CustomerDTOValidator {
    @Autowired
    private Validator validator;
    @Autowired
    private CustomerRepository customerRepository;

    public void validate(CustomerDTO customerDTO) {
        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customerDTO);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validateUniqueFieldsForCreate(CustomerDTO customerDTO) {
        if (customerRepository.existsByNID(customerDTO.getNID())) {
            throw new UniqueConstraintViolationException("NID already exists.");
        }
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new UniqueConstraintViolationException("Email already exists.");
        }
        if (customerRepository.existsByTelephoneNumber(customerDTO.getTelephoneNumber())) {
            throw new UniqueConstraintViolationException("Telephone number already exists.");
        }
    }

    public void validateUniqueFieldsForUpdate(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);

        if (existingCustomer != null) {
            if (customerRepository.existsByEmail(customerDTO.getEmail()) && !existingCustomer.getEmail().equals(customerDTO.getEmail())) {
                throw new UniqueConstraintViolationException("Email already exists.");
            }
            if (customerRepository.existsByTelephoneNumber(customerDTO.getTelephoneNumber()) && !existingCustomer.getTelephoneNumber().equals(customerDTO.getTelephoneNumber())) {
                throw new UniqueConstraintViolationException("Telephone number already exists.");
            }
        }
    }

    public void validateKeysForCreate(Map<String, Object> jsonMap) {
        Set<String> keys = jsonMap.keySet();
        Set<String> validKeys = Set.of("name", "lastName", "address", "telephoneNumber", "email", "nid", "birthday", "password");

        List<String> invalidKeys = new ArrayList<>();
        for (String key : keys) {
            if (!validKeys.contains(key)) {
                invalidKeys.add(key);
            }
        }

        if (!invalidKeys.isEmpty()) {
            throw new InvalidFieldException("Invalid field(s): " + invalidKeys);
        }
    }
    public void validateKeysForUpdate(Map<String, Object> jsonMap) {
        Set<String> keys = jsonMap.keySet();
        Set<String> validKeys = Set.of("address", "telephoneNumber", "email", "password");

        List<String> invalidKeys = new ArrayList<>();
        for (String key : keys) {
            if (!validKeys.contains(key)) {
                invalidKeys.add(key);
            }
        }

        if (!invalidKeys.isEmpty()) {
            throw new InvalidFieldException("Invalid field(s): " + invalidKeys + ". Unique required fields (they are not all mandatory): ['address', 'telephoneNumber', 'email', 'password']");
        }
    }
}
