package ar.edu.utn.tup.service;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.enums.AccountType;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.model.enums.CoinType;
import ar.edu.utn.tup.persistence.BankAccountDAO;

import java.util.List;

public class BankAccountService {
    private BankAccountDAO bankAccountDAO;

    public BankAccountService() {
        this.bankAccountDAO = new BankAccountDAO();
    }

    public BankAccount create(BankingEntities bankEntity, String alias, String securityCode, AccountType accountType, Customer customer, CoinType coin) {
        BankAccount bankAccount = new BankAccount(bankEntity, alias, securityCode, accountType, customer, coin);

        if (customer.getBankAccounts().stream().anyMatch(account -> account.getBankingEntities().equals(bankEntity))) {
            return null;
        }
        else {
            customer.addBankAccount(bankAccount);
            bankAccountDAO.create(bankAccount);
            return bankAccount;
        }
    }

    public List<BankAccount> getBankAccountsByCustomer(long customerId) {
        return bankAccountDAO.findBankAccountsByCustomer(customerId);
    }

    public BankAccount findBank(String numberAccount) {
        return bankAccountDAO.findByAccountNumber(numberAccount);
    }

    public void update(BankAccount bankAccount) {
        try {
            bankAccountDAO.edit(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BankAccount getBankAccountByCBUAlias(String CBUAlias) {
        return bankAccountDAO.findBankAccountByCBUAlias(CBUAlias);
    }
}
