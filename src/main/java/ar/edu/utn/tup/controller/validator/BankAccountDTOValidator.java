package ar.edu.utn.tup.controller.validator;

import ar.edu.utn.tup.controller.dto.BankAccountDTO;
import ar.edu.utn.tup.controller.exception.InvalidFieldException;
import ar.edu.utn.tup.controller.exception.UniqueConstraintViolationException;
import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.repository.BankAccountRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class BankAccountDTOValidator {
    @Autowired
    private Validator validator;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void validate(BankAccountDTO bankAccountDTO) {
        Set<ConstraintViolation<BankAccountDTO>> violations = validator.validate(bankAccountDTO);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validateUniqueFieldsForCreate(BankAccountDTO bankAccountDTO) {
        if (bankAccountRepository.existsByAlias(bankAccountDTO.getAlias())) {
            throw new UniqueConstraintViolationException("Alias already exists.");
        }
    }

    public void validateUniqueFieldsForUpdate(Long id, BankAccountDTO bankAccountDTO) {
        BankAccount existingBankAccount = bankAccountRepository.findById(id).orElse(null);

        if (existingBankAccount != null) {
            if (bankAccountRepository.existsByAlias(bankAccountDTO.getAlias()) && !existingBankAccount.getAlias().equals(bankAccountDTO.getAlias())) {
                throw new UniqueConstraintViolationException("Alias already exists.");
            }
        }
    }

    public void validateKeysForCreate(Map<String, Object> jsonMap) {
        Set<String> keys = jsonMap.keySet();
        Set<String> validKeys = Set.of("bankEntity", "alias", "accountType", "coinType", "customerNID", "securityCode");

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
        Set<String> validKeys = Set.of("alias", "securityCode");

        List<String> invalidKeys = new ArrayList<>();
        for (String key : keys) {
            if (!validKeys.contains(key)) {
                invalidKeys.add(key);
            }
        }

        if (!invalidKeys.isEmpty()) {
            throw new InvalidFieldException("Invalid field(s): " + invalidKeys + ". Unique required fields (they are not all mandatory): ['alias', 'securityCode']");
        }
    }
}
