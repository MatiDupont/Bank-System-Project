package ar.edu.utn.tup.persistence;

import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.service.MovementsService;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MovementsDAO implements Serializable {
    private EntityManagerFactory emf = null;

    public MovementsDAO() {
        this.emf = Persistence.createEntityManagerFactory("banksystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movement movement) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(movement);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movement> findIncomingMovementsByCustomer(Long bankAccountId) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from movement where destinationAccountId = ? and (motive = 'Transfer' or motive = 'Deposit')", Movement.class
            );
            query.setParameter(1, bankAccountId);
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public List<Movement> findOutComingMovementsByBankAccount(Long bankAccountId) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from movement where sourceAccountId = ? and (motive = 'Transfer' or motive = 'Withdrawal')", Movement.class
            );
            query.setParameter(1, bankAccountId);
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public List<Movement> findInvestmentMovementsByBankAccount(Long bankAccountId) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from movement where motive = 'Investment' and sourceAccountId = ?", Movement.class
            );
            query.setParameter(1, bankAccountId);
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public List<Movement> findMaturedInvestments(LocalDate today) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from movement where endDate = ? and motive = 'Investment' and movementStatus = 'In progress'", Movement.class
            );
            query.setParameter(1, today);
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public void update(Movement movement) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(movement);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
