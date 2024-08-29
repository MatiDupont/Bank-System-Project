package ar.edu.utn.tup.repository;

import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.model.enums.BankingEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
        boolean existsByAlias(String alias);
        Optional<BankAccount> findByCBU(String CBU);
        @Query(value = "select * from bank_account where banking_entity = :bankingEntity", nativeQuery = true)
        List<BankAccount> findByBankingEntities(@Param("bankingEntity") String bankingEntity);
    }
