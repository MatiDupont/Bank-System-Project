package ar.edu.utn.tup.repository;

import ar.edu.utn.tup.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    @Query(value = "select * from movement where destination_account_id = :bankAccountId and (motive = 'Transfer' or motive = 'Deposit')", nativeQuery = true)
    List<Movement> getIncomingMovements(@Param("bankAccountId") Long id);
    @Query(value = "select * from movement where source_account_id = :bankAccountId", nativeQuery = true)
    List<Movement> getOutcomingMovements(@Param("bankAccountId") Long id);
}
