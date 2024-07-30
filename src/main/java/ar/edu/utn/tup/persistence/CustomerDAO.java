package ar.edu.utn.tup.persistence;

import ar.edu.utn.tup.model.Customer;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.List;

public class CustomerDAO implements Serializable {
    private EntityManagerFactory emf = null;

    public CustomerDAO() {
        this.emf = Persistence.createEntityManagerFactory("banksystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(customer);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            if (findCustomer(customer.getNID()) == null) {
                throw new EntityNotFoundException("The customer with id " + customer.getNID() + " no longer exists.");
            }
            throw ex;
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Customer findCustomer(String NID) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, NID);
        }
        finally {
            em.close();
        }
    }

    public void destroy(long id) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("The customer with id " + id + " no longer exists.");
            }
            em.remove(customer);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(String NID, String password) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from customer where nid = ? and password = ?", Customer.class
            );
            query.setParameter(1, NID);
            query.setParameter(2, password);
            return (Customer) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Customer findCustomerByNID(String NID) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "select * from customer where nid = ?", Customer.class
            );
            query.setParameter(1, NID);
            return (Customer) query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
}
