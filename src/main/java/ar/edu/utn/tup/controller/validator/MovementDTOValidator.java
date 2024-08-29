package ar.edu.utn.tup.controller.validator;

import ar.edu.utn.tup.controller.dto.InvestDTO;
import ar.edu.utn.tup.controller.dto.MovementDTO;
import ar.edu.utn.tup.controller.dto.TransferDTO;
import ar.edu.utn.tup.controller.dto.WithdrawDTO;
import ar.edu.utn.tup.controller.exception.*;
import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MovementDTOValidator {
    @Autowired
    private Validator validator;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void validate(WithdrawDTO withdrawDTO) {
        Set<ConstraintViolation<WithdrawDTO>> violations = validator.validate(withdrawDTO);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validate(TransferDTO transferDTO) {
        Set<ConstraintViolation<TransferDTO>> violations = validator.validate(transferDTO);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validate(InvestDTO investDTO) {
        Set<ConstraintViolation<InvestDTO>> violations = validator.validate(investDTO);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validateKeysForWithdraw(Map<String, Object> jsonMap) {
        Set<String> keys = jsonMap.keySet();
        Set<String> validKeys = Set.of("cbu", "amount");

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

    public void validateKeysForTransfer(Map<String, Object> jsonMap) {
        Set<String> keys = jsonMap.keySet();
        Set<String> validKeys = Set.of("sourceCBU", "amount", "coinType", "destinationCBU");

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

    public void validateKeysForInvest(Map<String, Object> jsonMap) {
        Set<String> keys = jsonMap.keySet();
        Set<String> validKeys = Set.of("cbu", "amount", "fixedTerm");

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

    public BankAccount validateFields(WithdrawDTO withdrawDTO) {
        BankAccount existingBankAccount = bankAccountRepository.findByCBU(withdrawDTO.getCBU()).orElseThrow(() -> new EntityNotFoundException("Source account not found: " + withdrawDTO.getCBU()));

        if (withdrawDTO.getAmount() > existingBankAccount.getBalance()) {
            throw new InsufficientFundsException("Could not withdraw money due to insufficient funds.");
        }

        return existingBankAccount;
    }

    public List<BankAccount> validateFields(TransferDTO transferDTO) {
        BankAccount existingSourceBankAccount = bankAccountRepository.findByCBU(transferDTO.getSourceCBU()).orElseThrow(() -> new EntityNotFoundException("Source account not found: " + transferDTO.getSourceCBU()));
        BankAccount existingDestinationBankAccount = bankAccountRepository.findByCBU(transferDTO.getDestinationCBU()).orElseThrow(() -> new EntityNotFoundException("Destination account not found: " + transferDTO.getDestinationCBU()));

        if (!transferDTO.getCoinType().equals(String.valueOf(existingSourceBankAccount.getCoin()))) {
            throw new CurrencyMismatchException("The currency type in the request does not match the currency of the source account.");
        }
        if (transferDTO.getAmount() > existingSourceBankAccount.getBalance()) {
            throw new InsufficientFundsException("Could not transfer money due to insufficient funds.");
        }
        if (existingSourceBankAccount.getCoin() != existingDestinationBankAccount.getCoin()) {
            throw new CurrencyMismatchException("Cannot transfer money due to currency mismatch between source and destination accounts.");
        }
        if (transferDTO.getSourceCBU().equals(transferDTO.getDestinationCBU())) {
            throw new InvalidTransferException("Source and destination accounts cannot be the same.");
        }
        if (!existingSourceBankAccount.isAccountState()) {
            throw new AccountInactiveException("The source account is inactive and cannot process transactions.");
        }
        if (!existingDestinationBankAccount.isAccountState()) {
            throw new AccountInactiveException("The destination account is inactive and cannot process transactions.");
        }

        return List.of(existingSourceBankAccount, existingDestinationBankAccount);
    }

    public BankAccount validateFields(InvestDTO investDTO) {
        BankAccount existingBankAccount = bankAccountRepository.findByCBU(investDTO.getCBU()).orElseThrow(() -> new EntityNotFoundException("Source account not found: " + investDTO.getCBU()));

        Set<String> validValues = Set.of("1 month", "3 months", "6 months", "12 months");

        if (investDTO.getAmount() > existingBankAccount.getBalance()) {
            throw new InsufficientFundsException("Could not invest money due to insufficient funds.");
        }
        if (!validValues.contains(investDTO.getFixedTerm())) {
            throw new InvalidFixedTermException("Invalid fixed term value. Valid values are: '1 month', '3 months', '6 months', '12 months'.");
        }

        return existingBankAccount;
    }
}
