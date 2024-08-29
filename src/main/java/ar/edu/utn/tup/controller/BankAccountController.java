package ar.edu.utn.tup.controller;

import ar.edu.utn.tup.controller.dto.BankAccountDTO;
import ar.edu.utn.tup.controller.dto.BankAccountMovementsDTO;
import ar.edu.utn.tup.controller.dto.ResponseDTO;
import ar.edu.utn.tup.controller.validator.BankAccountDTOValidator;
import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankAccountDTOValidator bankAccountDTOValidator;

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();

        return ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBankAccountById(@PathVariable Long id) {
        Optional<BankAccount> bankAccount = bankAccountService.findBankAccountById(id);

        return bankAccount.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(bankAccountService.convertToDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account not found with ID: " + id));
    }

    @GetMapping("/entity/{urlDescription}")
    public ResponseEntity<Object> getBankAccountsByBankEntity(@PathVariable String urlDescription) {
        BankingEntities bankingEntity = BankingEntities.fromUrlDescription(urlDescription);

        List<BankAccountDTO> bankAccounts = bankAccountService.getBankAccountsByBankEntity(bankingEntity);

        if (bankAccounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bank accounts found for the specified entity.");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<Object> getMovementsByBankAccount(@PathVariable Long id) {
        BankAccountMovementsDTO bankAccountMovementsDTO = bankAccountService.getMovementsByBankAccount(id);

        return ResponseEntity.status(HttpStatus.OK).body(bankAccountMovementsDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createBankAccount(@RequestBody Map<String, Object> jsonMap) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            bankAccountDTOValidator.validateKeysForCreate(jsonMap);

            BankAccountDTO bankAccountDTO = bankAccountService.convertToDTO(jsonMap);

            BankAccount createdBankAccount = bankAccountService.createBankAccount(bankAccountDTO);

            if (createdBankAccount != null) {
                responseDTO.setStatus("SUCCESSFUL");
                responseDTO.setMessage("Bank account created successfully.");

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            }
            else {
                responseDTO.setStatus("FAILED");
                responseDTO.setMessage("Customer with NID: " + bankAccountDTO.getCustomerNID() + " not found.");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
        }
        catch (Exception ex) {
            responseDTO.setStatus("FAILED");
            responseDTO.setMessage("Failed to create bank account: " + ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBankAccount(@PathVariable Long id, @RequestBody Map<String, Object> jsonMap) {
        bankAccountDTOValidator.validateKeysForUpdate(jsonMap);

        BankAccountDTO bankAccountDTO = bankAccountService.convertToDTO(jsonMap);

        BankAccount createdBankAccount = bankAccountService.updateBankAccount(id, bankAccountDTO);

        if (createdBankAccount != null) {
            BankAccountDTO updatedBankAccountDTO = bankAccountService.convertToDTO(createdBankAccount);

            return ResponseEntity.status(HttpStatus.OK).body(updatedBankAccountDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account with id: " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankAccount(@PathVariable Long id) {
        boolean isDeleted = bankAccountService.deleteBankAccount(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer with id " + id + " deleted successfully.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found.");
        }
    }
}
