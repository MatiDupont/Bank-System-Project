package ar.edu.utn.tup.service;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.persistence.MovementsDAO;

import java.time.LocalDate;
import java.util.List;

public class MovementsService {
    private MovementsDAO movementsDAO;
    private BankAccountService bankAccountService;

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

            Movement movement = new Movement(sourceAccount, destinationAccount, amount, 0, date, date, 0, "Transfer", "Success");
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

            Movement movement = new Movement(bankAccount, bankAccount, amount, 0, LocalDate.now(), LocalDate.now(), 0, "Withdrawal", "Success");
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

        Movement movement = new Movement(bankAccount, bankAccount, amount, 0, LocalDate.now(), LocalDate.now(), 0, "Deposit", "Success");
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
}
