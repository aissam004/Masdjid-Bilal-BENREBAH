/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.CategorieMateriel;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Materiel;
import EntityController.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pc
 */
public class CategorieMaterielJpaController implements Serializable {

    public CategorieMaterielJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategorieMateriel categorieMateriel) {
        if (categorieMateriel.getMaterielCollection() == null) {
            categorieMateriel.setMaterielCollection(new ArrayList<Materiel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Materiel> attachedMaterielCollection = new ArrayList<Materiel>();
            for (Materiel materielCollectionMaterielToAttach : categorieMateriel.getMaterielCollection()) {
                materielCollectionMaterielToAttach = em.getReference(materielCollectionMaterielToAttach.getClass(), materielCollectionMaterielToAttach.getIdMateriel());
                attachedMaterielCollection.add(materielCollectionMaterielToAttach);
            }
            categorieMateriel.setMaterielCollection(attachedMaterielCollection);
            em.persist(categorieMateriel);
            for (Materiel materielCollectionMateriel : categorieMateriel.getMaterielCollection()) {
                CategorieMateriel oldIdCategorieMaterielOfMaterielCollectionMateriel = materielCollectionMateriel.getIdCategorieMateriel();
                materielCollectionMateriel.setIdCategorieMateriel(categorieMateriel);
                materielCollectionMateriel = em.merge(materielCollectionMateriel);
                if (oldIdCategorieMaterielOfMaterielCollectionMateriel != null) {
                    oldIdCategorieMaterielOfMaterielCollectionMateriel.getMaterielCollection().remove(materielCollectionMateriel);
                    oldIdCategorieMaterielOfMaterielCollectionMateriel = em.merge(oldIdCategorieMaterielOfMaterielCollectionMateriel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategorieMateriel categorieMateriel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategorieMateriel persistentCategorieMateriel = em.find(CategorieMateriel.class, categorieMateriel.getIdCategorieMateriel());
            Collection<Materiel> materielCollectionOld = persistentCategorieMateriel.getMaterielCollection();
            Collection<Materiel> materielCollectionNew = categorieMateriel.getMaterielCollection();
            Collection<Materiel> attachedMaterielCollectionNew = new ArrayList<Materiel>();
            for (Materiel materielCollectionNewMaterielToAttach : materielCollectionNew) {
                materielCollectionNewMaterielToAttach = em.getReference(materielCollectionNewMaterielToAttach.getClass(), materielCollectionNewMaterielToAttach.getIdMateriel());
                attachedMaterielCollectionNew.add(materielCollectionNewMaterielToAttach);
            }
            materielCollectionNew = attachedMaterielCollectionNew;
            categorieMateriel.setMaterielCollection(materielCollectionNew);
            categorieMateriel = em.merge(categorieMateriel);
            for (Materiel materielCollectionOldMateriel : materielCollectionOld) {
                if (!materielCollectionNew.contains(materielCollectionOldMateriel)) {
                    materielCollectionOldMateriel.setIdCategorieMateriel(null);
                    materielCollectionOldMateriel = em.merge(materielCollectionOldMateriel);
                }
            }
            for (Materiel materielCollectionNewMateriel : materielCollectionNew) {
                if (!materielCollectionOld.contains(materielCollectionNewMateriel)) {
                    CategorieMateriel oldIdCategorieMaterielOfMaterielCollectionNewMateriel = materielCollectionNewMateriel.getIdCategorieMateriel();
                    materielCollectionNewMateriel.setIdCategorieMateriel(categorieMateriel);
                    materielCollectionNewMateriel = em.merge(materielCollectionNewMateriel);
                    if (oldIdCategorieMaterielOfMaterielCollectionNewMateriel != null && !oldIdCategorieMaterielOfMaterielCollectionNewMateriel.equals(categorieMateriel)) {
                        oldIdCategorieMaterielOfMaterielCollectionNewMateriel.getMaterielCollection().remove(materielCollectionNewMateriel);
                        oldIdCategorieMaterielOfMaterielCollectionNewMateriel = em.merge(oldIdCategorieMaterielOfMaterielCollectionNewMateriel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorieMateriel.getIdCategorieMateriel();
                if (findCategorieMateriel(id) == null) {
                    throw new NonexistentEntityException("The categorieMateriel with id " + id + " no longer exists.");
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
            CategorieMateriel categorieMateriel;
            try {
                categorieMateriel = em.getReference(CategorieMateriel.class, id);
                categorieMateriel.getIdCategorieMateriel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorieMateriel with id " + id + " no longer exists.", enfe);
            }
            Collection<Materiel> materielCollection = categorieMateriel.getMaterielCollection();
            for (Materiel materielCollectionMateriel : materielCollection) {
                materielCollectionMateriel.setIdCategorieMateriel(null);
                materielCollectionMateriel = em.merge(materielCollectionMateriel);
            }
            em.remove(categorieMateriel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategorieMateriel> findCategorieMaterielEntities() {
        return findCategorieMaterielEntities(true, -1, -1);
    }

    public List<CategorieMateriel> findCategorieMaterielEntities(int maxResults, int firstResult) {
        return findCategorieMaterielEntities(false, maxResults, firstResult);
    }

    private List<CategorieMateriel> findCategorieMaterielEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategorieMateriel.class));
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

    public CategorieMateriel findCategorieMateriel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategorieMateriel.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategorieMaterielCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategorieMateriel> rt = cq.from(CategorieMateriel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
