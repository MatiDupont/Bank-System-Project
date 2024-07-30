package ar.edu.utn.tup.service;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.model.enums.AccountType;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.model.enums.CoinType;
import ar.edu.utn.tup.persistence.BankAccountDAO;
import ar.edu.utn.tup.persistence.MovementsDAO;
import ar.edu.utn.tup.service.BankAccountService;
import ar.edu.utn.tup.service.MovementsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovementsServiceTest {
    @Mock
    private MovementsDAO movementsDAO;

    @InjectMocks
    private MovementsService movementsService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
   @DisplayName("Test for successful bank transfer.")
    public void testMakeATransferSuccessful() {
        BankAccount sourceAccount = new BankAccount();
        sourceAccount.setId(1);
        sourceAccount.setCoin(CoinType.EUR);
        sourceAccount.setAccountState(true);
        sourceAccount.setBalance(5000);

        BankAccount destinationAccount = new BankAccount();
        destinationAccount.setId(2);
        destinationAccount.setCoin(CoinType.EUR);
        destinationAccount.setBalance(1599.9);
        destinationAccount.setAccountState(true);

        double amount = 520;
        LocalDate date = LocalDate.now();

        double expectedBalanceSourceAccount = sourceAccount.getBalance() - amount;
        double expectedBalanceDestinationAccount = destinationAccount.getBalance() + amount;

        doNothing().when(movementsDAO).create(any(Movement.class));

        int result = movementsService.makeABankTransfer(sourceAccount, destinationAccount, date, amount);

        assertEquals(expectedBalanceSourceAccount, sourceAccount.getBalance());
        assertEquals(expectedBalanceDestinationAccount, destinationAccount.getBalance());
        assertEquals(1, result);

        verify(movementsDAO, times(1)).create(any(Movement.class));
    }

    @Test
    @DisplayName("Fail when trying to make a transfer due to insufficient funds.")
    public void testInsufficientBalance() {
        BankAccount sourceAccount = new BankAccount();
        sourceAccount.setId(1);
        sourceAccount.setCoin(CoinType.EUR);
        sourceAccount.setAccountState(true);
        sourceAccount.setBalance(100);

        BankAccount destinationAccount = new BankAccount();
        destinationAccount.setId(2);
        destinationAccount.setCoin(CoinType.EUR);
        destinationAccount.setBalance(300);
        destinationAccount.setAccountState(true);

        double amount = 500;
        LocalDate date = LocalDate.now();

        int result = movementsService.makeABankTransfer(sourceAccount, destinationAccount, date, amount);

        assertEquals(2, result);

        verify(movementsDAO, never()).create(any(Movement.class));
    }

    @Test
    @DisplayName("Fail when trying to make a transfer due to destination account inactive.")
    public void testAccountInactive() {
        BankAccount sourceAccount = new BankAccount();
        sourceAccount.setId(1);
        sourceAccount.setCoin(CoinType.EUR);
        sourceAccount.setAccountState(true);
        sourceAccount.setBalance(2000);

        BankAccount destinationAccount = new BankAccount();
        destinationAccount.setId(2);
        destinationAccount.setCoin(CoinType.EUR);
        destinationAccount.setBalance(10700);
        destinationAccount.setAccountState(false);

        double amount = 1200;
        LocalDate date = LocalDate.now();

        int result = movementsService.makeABankTransfer(sourceAccount, destinationAccount, date, amount);

        assertEquals(3, result);

        verify(movementsDAO, never()).create(any(Movement.class));
    }

    @Test
    @DisplayName("Fail when trying to make a transfer due to destination account has a different type of currency.")
    public void testDifferentTypeOfCurrency() {
        BankAccount sourceAccount = new BankAccount();
        sourceAccount.setId(1);
        sourceAccount.setCoin(CoinType.EUR);
        sourceAccount.setAccountState(true);
        sourceAccount.setBalance(9999);

        BankAccount destinationAccount = new BankAccount();
        destinationAccount.setId(2);
        destinationAccount.setCoin(CoinType.ARS);
        destinationAccount.setBalance(307009);
        destinationAccount.setAccountState(true);

        double amount = 50;
        LocalDate date = LocalDate.now();

        int result = movementsService.makeABankTransfer(sourceAccount, destinationAccount, date, amount);

        assertEquals(4, result);

        verify(movementsDAO, never()).create(any(Movement.class));
    }

    @Test
    @DisplayName("Test for successful withdraw.")
    public void testWithdrawSuccessful() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setCoin(CoinType.GBP);
        bankAccount.setAccountState(true);
        bankAccount.setBalance(230);

        double amount = 50;
        double expectedBalance = bankAccount.getBalance() - amount;

        doNothing().when(movementsDAO).create(any(Movement.class));

        boolean result = movementsService.withdraw(bankAccount, amount);

        assertTrue(result);
        assertEquals(expectedBalance, bankAccount.getBalance());

        verify(movementsDAO, times(1)).create(any(Movement.class));
    }

    @Test
    @DisplayName("Fail when trying to withdraw money from the account.")
    public void testWithdrawUnsuccessful() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(500);
        bankAccount.setCoin(CoinType.USD);
        bankAccount.setAccountState(true);
        bankAccount.setBalance(777.011);

        double amount = 800.5;

        boolean result = movementsService.withdraw(bankAccount, amount);

        assertFalse(result);
    }

    @Test
    @DisplayName("Test for successful invest.")
    public void testInvestSuccessful() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(89);
        bankAccount.setCoin(CoinType.GBP);
        bankAccount.setAccountState(true);
        bankAccount.setBalance(4600);

        double amount = 555;
        double interestRate = 3.22;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        double expectedBalance = bankAccount.getBalance() - amount;

        doNothing().when(movementsDAO).create(any(Movement.class));

        boolean result = movementsService.invest(bankAccount, amount, interestRate, startDate, endDate);

        assertEquals(expectedBalance, bankAccount.getBalance());
        assertTrue(result);

        verify(movementsDAO, times(1)).create(any(Movement.class));
    }

    @Test
    @DisplayName("Fail when trying to invest due to insufficient funds.")
    public void testInvestUnsuccessful() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(89);
        bankAccount.setCoin(CoinType.GBP);
        bankAccount.setAccountState(true);
        bankAccount.setBalance(4600);

        double amount = 5555;
        double interestRate = 3.22;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        boolean result = movementsService.invest(bankAccount, amount, interestRate, startDate, endDate);

        assertFalse(result);
    }

    @Test
    @DisplayName("Test for checking matured investments successfully.")
    public void testCheckMaturedInvestment() {
        Movement maturedInvestment1 = new Movement();
        maturedInvestment1.setId(1);
        maturedInvestment1.setDestinationAccount(new BankAccount());
        maturedInvestment1.getDestinationAccount().setId(1);
        maturedInvestment1.getDestinationAccount().setBalance(7000);
        maturedInvestment1.setMaturedAmount(320.29);
        maturedInvestment1.setStatus("In progress");

        double expectedMaturedAmount1 = maturedInvestment1.getDestinationAccount().getBalance() + maturedInvestment1.getMaturedAmount();

        Movement maturedInvestment2 = new Movement();
        maturedInvestment2.setId(2);
        maturedInvestment2.setDestinationAccount(new BankAccount());
        maturedInvestment2.getDestinationAccount().setId(2);
        maturedInvestment2.getDestinationAccount().setBalance(3200);
        maturedInvestment2.setMaturedAmount(139.3);
        maturedInvestment2.setStatus("In progress");

        double expectedMaturedAmount2 = maturedInvestment2.getDestinationAccount().getBalance() + maturedInvestment2.getMaturedAmount();

        List<Movement> maturedInvestments = Arrays.asList(maturedInvestment1, maturedInvestment2);

        LocalDate today = LocalDate.now();

        when(movementsDAO.findMaturedInvestments(today)).thenReturn(maturedInvestments);
        doNothing().when(movementsDAO).update(any(Movement.class));

        movementsService.checkMaturedInvestments();

        assertEquals(expectedMaturedAmount1, maturedInvestment1.getDestinationAccount().getBalance());
        assertEquals("Completed", maturedInvestment1.getStatus());
        assertEquals(expectedMaturedAmount2, maturedInvestment2.getDestinationAccount().getBalance());
        assertEquals("Completed", maturedInvestment2.getStatus());

        verify(movementsDAO, times(1)).findMaturedInvestments(today);
        verify(movementsDAO, times(2)).update(any(Movement.class));
    }

    @Test
    @DisplayName("Test for successful deposit.")
    public void testDepositSuccessful() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setCoin(CoinType.ARS);
        bankAccount.setAccountState(true);
        bankAccount.setBalance(0);

        double amount = 120000;
        double expectedBalance = bankAccount.getBalance() + amount;

        doNothing().when(movementsDAO).create(any(Movement.class));

        movementsService.deposit(bankAccount, amount);

        assertEquals(expectedBalance, bankAccount.getBalance());

        verify(movementsDAO, times(1)).create(any(Movement.class));
    }

    @Test
    @DisplayName("Test retrieving of movements received.")
    public void testGetMovementsReceivedSuccessful() {
        long id = 88L;

        List<Movement> expectedMovements = Arrays.asList(new Movement(), new Movement(), new Movement(), new Movement(), new Movement());

        when(movementsDAO.findIncomingMovementsByCustomer(id)).thenReturn(expectedMovements);

        List<Movement> result = movementsService.getMovementsReceived(id);

        assertEquals(expectedMovements, result);

        verify(movementsDAO, times(1)).findIncomingMovementsByCustomer(id);
    }

    @Test
    @DisplayName("Test retrieving of movements received empty.")
    public void testGetMovementsReceivedEmpty() {
        long id = 3423L;

        List<Movement> expectedMovements = Collections.emptyList();

        when(movementsDAO.findIncomingMovementsByCustomer(id)).thenReturn(expectedMovements);

        List<Movement> result = movementsService.getMovementsReceived(id);

        assertEquals(expectedMovements, result);

        verify(movementsDAO, times(1)).findIncomingMovementsByCustomer(id);
    }

    @Test
    @DisplayName("Test retrieving of movements made.")
    public void testGetMovementsMadeSuccessful() {
        long id = 166L;

        List<Movement> expectedMovements = Arrays.asList(new Movement(), new Movement(), new Movement());

        when(movementsDAO.findOutComingMovementsByBankAccount(id)).thenReturn(expectedMovements);

        List<Movement> result = movementsService.getMovementsMade(id);

        assertEquals(expectedMovements, result);

        verify(movementsDAO, times(1)).findOutComingMovementsByBankAccount(id);
    }

    @Test
    @DisplayName("Test retrieving of movements made empty.")
    public void testGetMovementsMadeEmpty() {
        long id = 720111L;

        List<Movement> expectedMovements = Collections.emptyList();

        when(movementsDAO.findOutComingMovementsByBankAccount(id)).thenReturn(expectedMovements);

        List<Movement> result = movementsService.getMovementsMade(id);

        assertEquals(expectedMovements, result);

        verify(movementsDAO, times(1)).findOutComingMovementsByBankAccount(id);
    }

    @Test
    @DisplayName("Test retrieving of movements investment.")
    public void testGetMovementsInvestmentSuccessful() {
        long id = 35L;

        List<Movement> expectedMovements = Arrays.asList(new Movement());

        when(movementsDAO.findInvestmentMovementsByBankAccount(id)).thenReturn(expectedMovements);

        List<Movement> result = movementsService.getMovementsInvestment(id);

        assertEquals(expectedMovements, result);

        verify(movementsDAO, times(1)).findInvestmentMovementsByBankAccount(id);
    }

    @Test
    @DisplayName("Test retrieving of movements investment empty.")
    public void testGetMovementsInvestmentEmpty() {
        long id = 9874L;

        List<Movement> expectedMovements = Collections.emptyList();

        when(movementsDAO.findInvestmentMovementsByBankAccount(id)).thenReturn(expectedMovements);

        List<Movement> result = movementsService.getMovementsInvestment(id);

        assertEquals(expectedMovements, result);

        verify(movementsDAO, times(1)).findInvestmentMovementsByBankAccount(id);
    }
}
