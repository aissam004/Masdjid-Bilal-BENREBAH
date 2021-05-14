/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.Emplacement;
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
public class EmplacementJpaController implements Serializable {

    public EmplacementJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Emplacement emplacement) {
        if (emplacement.getMaterielCollection() == null) {
            emplacement.setMaterielCollection(new ArrayList<Materiel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Materiel> attachedMaterielCollection = new ArrayList<Materiel>();
            for (Materiel materielCollectionMaterielToAttach : emplacement.getMaterielCollection()) {
                materielCollectionMaterielToAttach = em.getReference(materielCollectionMaterielToAttach.getClass(), materielCollectionMaterielToAttach.getIdMateriel());
                attachedMaterielCollection.add(materielCollectionMaterielToAttach);
            }
            emplacement.setMaterielCollection(attachedMaterielCollection);
            em.persist(emplacement);
            for (Materiel materielCollectionMateriel : emplacement.getMaterielCollection()) {
                Emplacement oldIdEmplacementOfMaterielCollectionMateriel = materielCollectionMateriel.getIdEmplacement();
                materielCollectionMateriel.setIdEmplacement(emplacement);
                materielCollectionMateriel = em.merge(materielCollectionMateriel);
                if (oldIdEmplacementOfMaterielCollectionMateriel != null) {
                    oldIdEmplacementOfMaterielCollectionMateriel.getMaterielCollection().remove(materielCollectionMateriel);
                    oldIdEmplacementOfMaterielCollectionMateriel = em.merge(oldIdEmplacementOfMaterielCollectionMateriel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Emplacement emplacement) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emplacement persistentEmplacement = em.find(Emplacement.class, emplacement.getIdEmplacement());
            Collection<Materiel> materielCollectionOld = persistentEmplacement.getMaterielCollection();
            Collection<Materiel> materielCollectionNew = emplacement.getMaterielCollection();
            Collection<Materiel> attachedMaterielCollectionNew = new ArrayList<Materiel>();
            for (Materiel materielCollectionNewMaterielToAttach : materielCollectionNew) {
                materielCollectionNewMaterielToAttach = em.getReference(materielCollectionNewMaterielToAttach.getClass(), materielCollectionNewMaterielToAttach.getIdMateriel());
                attachedMaterielCollectionNew.add(materielCollectionNewMaterielToAttach);
            }
            materielCollectionNew = attachedMaterielCollectionNew;
            emplacement.setMaterielCollection(materielCollectionNew);
            emplacement = em.merge(emplacement);
            for (Materiel materielCollectionOldMateriel : materielCollectionOld) {
                if (!materielCollectionNew.contains(materielCollectionOldMateriel)) {
                    materielCollectionOldMateriel.setIdEmplacement(null);
                    materielCollectionOldMateriel = em.merge(materielCollectionOldMateriel);
                }
            }
            for (Materiel materielCollectionNewMateriel : materielCollectionNew) {
                if (!materielCollectionOld.contains(materielCollectionNewMateriel)) {
                    Emplacement oldIdEmplacementOfMaterielCollectionNewMateriel = materielCollectionNewMateriel.getIdEmplacement();
                    materielCollectionNewMateriel.setIdEmplacement(emplacement);
                    materielCollectionNewMateriel = em.merge(materielCollectionNewMateriel);
                    if (oldIdEmplacementOfMaterielCollectionNewMateriel != null && !oldIdEmplacementOfMaterielCollectionNewMateriel.equals(emplacement)) {
                        oldIdEmplacementOfMaterielCollectionNewMateriel.getMaterielCollection().remove(materielCollectionNewMateriel);
                        oldIdEmplacementOfMaterielCollectionNewMateriel = em.merge(oldIdEmplacementOfMaterielCollectionNewMateriel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = emplacement.getIdEmplacement();
                if (findEmplacement(id) == null) {
                    throw new NonexistentEntityException("The emplacement with id " + id + " no longer exists.");
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
            Emplacement emplacement;
            try {
                emplacement = em.getReference(Emplacement.class, id);
                emplacement.getIdEmplacement();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emplacement with id " + id + " no longer exists.", enfe);
            }
            Collection<Materiel> materielCollection = emplacement.getMaterielCollection();
            for (Materiel materielCollectionMateriel : materielCollection) {
                materielCollectionMateriel.setIdEmplacement(null);
                materielCollectionMateriel = em.merge(materielCollectionMateriel);
            }
            em.remove(emplacement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Emplacement> findEmplacementEntities() {
        return findEmplacementEntities(true, -1, -1);
    }

    public List<Emplacement> findEmplacementEntities(int maxResults, int firstResult) {
        return findEmplacementEntities(false, maxResults, firstResult);
    }

    private List<Emplacement> findEmplacementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Emplacement.class));
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

    public Emplacement findEmplacement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Emplacement.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmplacementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Emplacement> rt = cq.from(Emplacement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
