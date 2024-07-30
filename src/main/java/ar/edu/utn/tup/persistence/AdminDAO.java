package ar.edu.utn.tup.persistence;

import ar.edu.utn.tup.model.Admin;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.List;

public class AdminDAO implements Serializable {
    private EntityManagerFactory emf = null;

    public AdminDAO() {
        this.emf = Persistence.createEntityManagerFactory("banksystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Admin admin) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Admin admin) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(admin);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            if (admin.getId() == 0 || findAdmin(admin.getId()) == null) {
                throw new EntityNotFoundException("The admin with id " + admin.getId() + " no longer exists.");
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
            Admin admin;
            try {
                admin = em.getReference(Admin.class, id);
                admin.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("The admin with id " + id + " no longer exists.");
            }
            em.remove(admin);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Admin> findAdminEntities() {
        return findAdminEntities(true, -1, -1);
    }

    public List<Admin> findAdminEntities(int maxResults, int firstResult) {
        return findAdminEntities(false, maxResults, firstResult);
    }

    private List<Admin> findAdminEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Admin.class));
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

    public Admin findAdmin(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Admin.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getAdminCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Admin> rt = cq.from(Admin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
}
