package ar.edu.utn.tup.service;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.enums.AccountType;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.model.enums.CoinType;
import ar.edu.utn.tup.persistence.BankAccountDAO;
import ar.edu.utn.tup.service.BankAccountService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankAccountServiceTest {
    @Mock
    private BankAccountDAO bankAccountDAO;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test creating bank account for a new bank.")
    public void testCreateBankAccountSuccessful() {
        Customer customer = new Customer();

        BankAccount newAccount = bankAccountService.create(BankingEntities.HSBC, "alias.test.hsbc", "123456", AccountType.CURRENT_ACCOUNT, customer, CoinType.EUR);

        assertNotNull(newAccount);

        verify(bankAccountDAO, times(1)).create(any(BankAccount.class));
    }

    @Test
    @DisplayName("Fail to create bank account for an existing bank.")
    public void testCreateBankAccountUnsuccessful() {
        Customer customer = new Customer();

        BankAccount existingAccount = new BankAccount(BankingEntities.HSBC, "alias.test.hsbc", "1234", AccountType.SAVING_BANK, customer, CoinType.ARS);
        customer.addBankAccount(existingAccount);

        BankAccount newAccount = bankAccountService.create(BankingEntities.HSBC, "alias2.test.hsbc", "12345", AccountType.CURRENT_ACCOUNT, customer, CoinType.ARS);

        assertNull(newAccount);

        verify(bankAccountDAO, times(0)).create(any(BankAccount.class));
    }

    @Test
    @DisplayName("Test finding bank account by account number.")
    public void testFindBankAccountByAccountNumberSuccessful() {
        String numberAccount = "123456789";
        BankAccount expectedBankAccount = new BankAccount();

        when(bankAccountDAO.findByAccountNumber(numberAccount)).thenReturn(expectedBankAccount);

        BankAccount result = bankAccountService.findBank(numberAccount);

        assertEquals(expectedBankAccount, result);

        verify(bankAccountDAO, times(1)).findByAccountNumber(numberAccount);
    }

    @Test
    @DisplayName("Fail to find bank account by account number.")
    public void testFindBankAccountByAccountNumberUnsuccessful() {
        String numberAccount = "123456789";

        when(bankAccountDAO.findByAccountNumber(numberAccount)).thenReturn(null);

        BankAccount result = bankAccountService.findBank(numberAccount);

        assertNull(result);

        verify(bankAccountDAO, times(1)).findByAccountNumber(numberAccount);
    }

    @Test
    @DisplayName("Test retrieving bank account by customer.")
    public void testGetBankAccountsByCustomerSuccessful() {
        long customerID = 1L;

        List<BankAccount> expectedAccounts = Arrays.asList(new BankAccount(), new BankAccount());

        when(bankAccountDAO.findBankAccountsByCustomer(customerID)).thenReturn(expectedAccounts);

        List<BankAccount> result = bankAccountService.getBankAccountsByCustomer(customerID);

        assertEquals(expectedAccounts, result);

        verify(bankAccountDAO, times(1)).findBankAccountsByCustomer(customerID);
    }

    @Test
    @DisplayName("Fail to retrieve bank account by customer.")
    public void testGetBankAccountsByCustomerUnsuccessful() {
        long customerID = 1L;

        List<BankAccount> expectedAccounts = Collections.emptyList();

        when(bankAccountDAO.findBankAccountsByCustomer(customerID)).thenReturn(expectedAccounts);

        List<BankAccount> result = bankAccountService.getBankAccountsByCustomer(customerID);

        assertEquals(expectedAccounts, result);

        verify(bankAccountDAO, times(1)).findBankAccountsByCustomer(customerID);
    }

    @Test
    @DisplayName("Test retrieving bank account by CBU or alias.")
    public void testGetBankAccountsByCBUAliasSuccessful() {
        String alias = "alias.test.sr";
        BankAccount expectedBankAccount = new BankAccount();

        when(bankAccountDAO.findBankAccountByCBUAlias(alias)).thenReturn(expectedBankAccount);

        BankAccount result = bankAccountService.getBankAccountByCBUAlias(alias);

        assertEquals(expectedBankAccount, result);

        verify(bankAccountDAO, times(1)).findBankAccountByCBUAlias(alias);
    }

    @Test
    @DisplayName("Fail to retrieve bank account by CBU or alias.")
    public void testGetBankAccountByCBUAliasUnsuccessful() {
        String alias = "alias.test.bna";

        when(bankAccountDAO.findBankAccountByCBUAlias(alias)).thenReturn(null);

        BankAccount result = bankAccountService.getBankAccountByCBUAlias(alias);

        assertNull(result);

        verify(bankAccountDAO, times(1)).findBankAccountByCBUAlias(alias);
    }

    @Test
    @DisplayName("Test updating bank account successful.")
    public void testUpdateBankAccountSuccessful() throws Exception {
        BankAccount bankAccount = new BankAccount();

        doNothing().when(bankAccountDAO).edit(bankAccount);

        bankAccountService.update(bankAccount);

        verify(bankAccountDAO, times(1)).edit(bankAccount);
    }

    @Test
    @DisplayName("Fail to update bank account.")
    public void testUpdateBankAccountUnsuccessful() throws Exception {
        BankAccount bankAccount = new BankAccount();

        doThrow(new RuntimeException()).when(bankAccountDAO).edit(bankAccount);

        assertThrows(RuntimeException.class, () -> bankAccountService.update(bankAccount));

        verify(bankAccountDAO, times(1)).edit(bankAccount);
    }
}
