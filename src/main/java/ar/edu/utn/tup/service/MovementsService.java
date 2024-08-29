package ar.edu.utn.tup.service;

import ar.edu.utn.tup.controller.dto.InvestDTO;
import ar.edu.utn.tup.controller.dto.MovementDTO;
import ar.edu.utn.tup.controller.dto.TransferDTO;
import ar.edu.utn.tup.controller.dto.WithdrawDTO;
import ar.edu.utn.tup.controller.validator.MovementDTOValidator;
import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.persistence.MovementsDAO;
import ar.edu.utn.tup.repository.MovementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovementsService {
    private MovementsDAO movementsDAO;
    private BankAccountService bankAccountService;
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private MovementDTOValidator movementDTOValidator;

    public MovementsService() {
        this.movementsDAO = new MovementsDAO();
        this.bankAccountService = new BankAccountService();
    }

    public Integer makeABankTransfer(BankAccount sourceAccount, BankAccount destinationAccount, LocalDate date, double amount) {
        if (sourceAccount.getBalance() >= amount && destinationAccount.isAccountState() && sourceAccount.getCoin() == destinationAccount.getCoin()) {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);

            bankAccountService.update(sourceAccount);
            bankAccountService.update(destinationAccount);

            Movement movement = new Movement(sourceAccount, destinationAccount, amount, 0, date, date, 0, "Transfer", "Successful");
            movementsDAO.create(movement);
            sourceAccount.addOutGoingMovement(movement);
            destinationAccount.addIncomingMovement(movement);

            return 1;
        }
        else if (sourceAccount.getBalance() < amount) {
            return 2;
        }
        else if (!destinationAccount.isAccountState()) {
            return 3;
        }
        else if (sourceAccount.getCoin() != destinationAccount.getCoin()) {
            return 4;
        }
        return 0;
    }

    public boolean withdraw(BankAccount bankAccount, double amount) {
        if (bankAccount.getBalance() >= amount) {
            bankAccount.setBalance(bankAccount.getBalance() - amount);

            bankAccountService.update(bankAccount);

            Movement movement = new Movement(bankAccount, bankAccount, amount, 0, LocalDate.now(), LocalDate.now(), 0, "Withdrawal", "Successful");
            movementsDAO.create(movement);
            bankAccount.addOutGoingMovement(movement);

            return true;
        }
        return false;
    }

    public boolean invest(BankAccount bankAccount, double amount, double interestRate, LocalDate startDate, LocalDate endDate) {
        if (bankAccount.getBalance() >= amount) {
            double maturedAmount = amount + (amount * (interestRate / 100));

            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountService.update(bankAccount);

            Movement movement = new Movement(bankAccount, bankAccount, amount, maturedAmount, startDate, endDate, interestRate, "Investment", "In progress");
            movementsDAO.create(movement);
            bankAccount.addOutGoingMovement(movement);

            return true;
        }
        return false;
    }

    public void checkMaturedInvestments() {
        LocalDate today = LocalDate.now();
        List<Movement> maturedInvestments = movementsDAO.findMaturedInvestments(today);

        for (Movement movement : maturedInvestments) {
            BankAccount bankAccount = movement.getDestinationAccount();
            double maturedAmount = movement.getMaturedAmount();

            bankAccount.setBalance(bankAccount.getBalance() + maturedAmount);

            movement.setStatus("Completed");

            bankAccountService.update(bankAccount);
            movementsDAO.update(movement);
        }
    }

    public void deposit(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);

        bankAccountService.update(bankAccount);

        Movement movement = new Movement(bankAccount, bankAccount, amount, 0, LocalDate.now(), LocalDate.now(), 0, "Deposit", "Successful");
        movementsDAO.create(movement);
        bankAccount.addIncomingMovement(movement);
    }

    public List<Movement> getMovementsReceived(Long id) {
        return movementsDAO.findIncomingMovementsByCustomer(id);
    }

    public List<Movement> getMovementsMade(Long id) {
        return movementsDAO.findOutComingMovementsByBankAccount(id);
    }

    public List<Movement> getMovementsInvestment(Long id) {
        return movementsDAO.findInvestmentMovementsByBankAccount(id);
    }

    // Metodos para el Controller REST
    public Optional<Movement> findMovementById(Long id) {
        return movementRepository.findById(id);
    }

    public Movement createWithdrawal(WithdrawDTO withdrawDTO) {
        movementDTOValidator.validate(withdrawDTO);
        BankAccount bankAccount = movementDTOValidator.validateFields(withdrawDTO);

        Movement movement = convertToEntity(withdrawDTO, bankAccount);

        bankAccount.setBalance(bankAccount.getBalance() - withdrawDTO.getAmount());

        bankAccount.addOutGoingMovement(movement);

        return movementRepository.save(movement);
    }

    public Movement createTransfer(TransferDTO transferDTO) {
        movementDTOValidator.validate(transferDTO);
        List<BankAccount> bankAccounts = movementDTOValidator.validateFields(transferDTO);

        BankAccount sourceBankAccount = bankAccounts.get(0);
        BankAccount destinationBankAccount = bankAccounts.get(1);

        Movement movement = convertToEntity(transferDTO, sourceBankAccount, destinationBankAccount);

        sourceBankAccount.setBalance(sourceBankAccount.getBalance() - transferDTO.getAmount());
        destinationBankAccount.setBalance(destinationBankAccount.getBalance() + transferDTO.getAmount());

        sourceBankAccount.addOutGoingMovement(movement);
        destinationBankAccount.addIncomingMovement(movement);

        return movementRepository.save(movement);
    }

    public Movement createInvest(InvestDTO investDTO) {
        movementDTOValidator.validate(investDTO);
        BankAccount bankAccount = movementDTOValidator.validateFields(investDTO);

        Movement movement = convertToEntity(investDTO, bankAccount);

        bankAccount.setBalance(bankAccount.getBalance() - investDTO.getAmount());

        bankAccount.addOutGoingMovement(movement);

        return movementRepository.save(movement);
    }

    public void createDeposit(BankAccount bankAccount) {
        Movement movement = convertToEntity(bankAccount);

        bankAccount.addIncomingMovement(movement);

        movementRepository.save(movement);
    }

    public MovementDTO convertToDTO(Movement movement) {
        MovementDTO movementDTO = new MovementDTO();

        movementDTO.setId(movement.getId());
        movementDTO.setAmount(movement.getAmount());
        movementDTO.setStartDate(String.valueOf(movement.getStartDate()));
        movementDTO.setEndDate(String.valueOf(movement.getEndDate()));
        movementDTO.setInterestRate(movement.getInterestRate());
        movementDTO.setMaturedAmount(movement.getMaturedAmount());
        movementDTO.setMotive(movement.getMotive());

        LocalDate endDate = movement.getEndDate();
        LocalDate today = LocalDate.now();
        if (today.isAfter(endDate) || today.isEqual(endDate)) {
            movementDTO.setMovementStatus("Completed");
            movement.getSourceAccount().setBalance(movement.getSourceAccount().getBalance() + movementDTO.getMaturedAmount());
        }
        else {
            movementDTO.setMovementStatus("In progress");
        }
        movementDTO.setDestinationAccount(movement.getDestinationAccount().getCBU());
        movementDTO.setSourceAccount(movement.getSourceAccount().getCBU());

        return movementDTO;
    }

    public WithdrawDTO convertToWDTO(Map<String, Object> jsonMap) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(jsonMap, WithdrawDTO.class);
    }

    public TransferDTO convertToTDTO(Map<String, Object> jsonMap) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(jsonMap, TransferDTO.class);
    }

    public InvestDTO convertToIDTO(Map<String, Object> jsonMap) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(jsonMap, InvestDTO.class);
    }

    public Movement convertToEntity(BankAccount bankAccount) {
        Movement movement = new Movement();

        movement.setAmount(bankAccount.getBankingEntities().getBonusInit());
        movement.setMotive("Deposit");
        movement.setStartDate(LocalDate.now());
        movement.setEndDate(LocalDate.now());
        movement.setStatus("Successful");
        movement.setDestinationAccount(bankAccount);
        movement.setSourceAccount(bankAccount);

        return movement;
    }

    public Movement convertToEntity(WithdrawDTO withdrawDTO, BankAccount bankAccount) {
        Movement movement = new Movement();

        movement.setAmount(withdrawDTO.getAmount());
        movement.setMotive("Withdrawal");
        movement.setStartDate(LocalDate.now());
        movement.setEndDate(LocalDate.now());
        movement.setStatus("Successful");
        movement.setDestinationAccount(bankAccount);
        movement.setSourceAccount(bankAccount);

        return movement;
    }

    public Movement convertToEntity(TransferDTO transferDTO, BankAccount sourceBankAccount, BankAccount destinationBankAccount) {
        Movement movement = new Movement();

        movement.setAmount(transferDTO.getAmount());
        movement.setMotive("Transfer");
        movement.setStartDate(LocalDate.now());
        movement.setEndDate(LocalDate.now());
        movement.setStatus("Successful");
        movement.setDestinationAccount(destinationBankAccount);
        movement.setSourceAccount(sourceBankAccount);

        return movement;
    }

    public Movement convertToEntity(InvestDTO investDTO, BankAccount bankAccount) {
        Movement movement = new Movement();

        movement.setAmount(investDTO.getAmount());
        movement.setMotive("Investment");
        movement.setStartDate(LocalDate.now());
        movement.setEndDate(calculateEndDate(LocalDate.now(), investDTO.getFixedTerm()));

        BankingEntities bankingEntity = bankAccount.getBankingEntities();
        Map<String, Double> interestRates = bankingEntity.getInterestRates();
        String fixedTermKey = investDTO.getFixedTerm();
        Double interestRate = interestRates.get(fixedTermKey);

        movement.setInterestRate(interestRate);

        double maturedAmount = investDTO.getAmount() + (investDTO.getAmount() * (interestRate / 100));

        movement.setMaturedAmount(maturedAmount);
        movement.setStatus("In progress");
        movement.setDestinationAccount(bankAccount);
        movement.setSourceAccount(bankAccount);
        return movement;
    }

    private LocalDate calculateEndDate(LocalDate startDate, String fixedTerm) {
        switch (fixedTerm) {
            case "1 month":
                return startDate.plus(1, ChronoUnit.MONTHS);
            case "3 months":
                return startDate.plus(3, ChronoUnit.MONTHS);
            case "6 months":
                return startDate.plus(6, ChronoUnit.MONTHS);
            case "12 months":
                return startDate.plus(12, ChronoUnit.MONTHS);
            default:
                return startDate;
        }
    }
}
