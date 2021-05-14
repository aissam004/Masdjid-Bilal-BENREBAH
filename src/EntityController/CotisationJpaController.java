/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.Cotisation;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Personne;
import EntityController.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pc
 */
public class CotisationJpaController implements Serializable {

    public CotisationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cotisation cotisation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personne idPersonne = cotisation.getIdPersonne();
            if (idPersonne != null) {
                idPersonne = em.getReference(idPersonne.getClass(), idPersonne.getIdPersonne());
                cotisation.setIdPersonne(idPersonne);
            }
            em.persist(cotisation);
            if (idPersonne != null) {
                idPersonne.getCotisationCollection().add(cotisation);
                idPersonne = em.merge(idPersonne);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cotisation cotisation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cotisation persistentCotisation = em.find(Cotisation.class, cotisation.getIdCotisation());
            Personne idPersonneOld = persistentCotisation.getIdPersonne();
            Personne idPersonneNew = cotisation.getIdPersonne();
            if (idPersonneNew != null) {
                idPersonneNew = em.getReference(idPersonneNew.getClass(), idPersonneNew.getIdPersonne());
                cotisation.setIdPersonne(idPersonneNew);
            }
            cotisation = em.merge(cotisation);
            if (idPersonneOld != null && !idPersonneOld.equals(idPersonneNew)) {
                idPersonneOld.getCotisationCollection().remove(cotisation);
                idPersonneOld = em.merge(idPersonneOld);
            }
            if (idPersonneNew != null && !idPersonneNew.equals(idPersonneOld)) {
                idPersonneNew.getCotisationCollection().add(cotisation);
                idPersonneNew = em.merge(idPersonneNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cotisation.getIdCotisation();
                if (findCotisation(id) == null) {
                    throw new NonexistentEntityException("The cotisation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cotisation cotisation;
            try {
                cotisation = em.getReference(Cotisation.class, id);
                cotisation.getIdCotisation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cotisation with id " + id + " no longer exists.", enfe);
            }
            Personne idPersonne = cotisation.getIdPersonne();
            if (idPersonne != null) {
                idPersonne.getCotisationCollection().remove(cotisation);
                idPersonne = em.merge(idPersonne);
            }
            em.remove(cotisation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cotisation> findCotisationEntities() {
        return findCotisationEntities(true, -1, -1);
    }

    public List<Cotisation> findCotisationEntities(int maxResults, int firstResult) {
        return findCotisationEntities(false, maxResults, firstResult);
    }

    private List<Cotisation> findCotisationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cotisation.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cotisation findCotisation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cotisation.class, id);
        } finally {
            em.close();
        }
    }

    public int getCotisationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cotisation> rt = cq.from(Cotisation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
