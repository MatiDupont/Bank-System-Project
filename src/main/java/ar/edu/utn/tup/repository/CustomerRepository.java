package ar.edu.utn.tup.repository;

import ar.edu.utn.tup.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByNID(String nid);

    boolean existsByEmail(String email);

    boolean existsByTelephoneNumber(String telephoneNumber);
}
