package ar.edu.utn.tup.persistence;

import ar.edu.utn.tup.model.BankAccount;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.List;

public class BankAccountDAO implements Serializable {
    private EntityManagerFactory emf = null;

    public BankAccountDAO() {
        this.emf = Persistence.createEntityManagerFactory("banksystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BankAccount bankAccount) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bankAccount);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BankAccount bankAccount) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(bankAccount);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            if (bankAccount.getId() == 0 || findBankAccount(bankAccount.getId()) == null) {
                throw new EntityNotFoundException("The bankAccount with id " + bankAccount.getId() + " no longer exists.");
            }
            throw ex;
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BankAccount bankAccount;
            try {
                bankAccount = em.getReference(BankAccount.class, id);
                bankAccount.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("The bankAccount with id " + id + " no longer exists.");
            }
            em.remove(bankAccount);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BankAccount> findBankAccountEntities() {
        return findBankAccountEntities(true, -1, -1);
    }

    public List<BankAccount> findBankAccountEntities(int maxResults, int firstResult) {
        return findBankAccountEntities(false, maxResults, firstResult);
    }

    private List<BankAccount> findBankAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BankAccount.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }

    public BankAccount findByAccountNumber(String accountNumber) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("select * from bank_account where account_number = ?", BankAccount.class);
            query.setParameter(1, accountNumber);
            return (BankAccount) query.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BankAccount findBankAccount(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BankAccount.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getBankAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<BankAccount> rt = cq.from(BankAccount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }

    public List<BankAccount> findBankAccountsByCustomer(long customerId) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from bank_account where customer_nid = ?", BankAccount.class
            );
            query.setParameter(1, customerId);
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public BankAccount findBankAccountByCBUAlias(String CBUAlias) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from bank_account where cbu = ? or alias = ?", BankAccount.class
            );
            query.setParameter(1, CBUAlias);
            query.setParameter(2, CBUAlias);
            return (BankAccount) query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }
}
