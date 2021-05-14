/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.EtatMateriel;
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
public class EtatMaterielJpaController implements Serializable {

    public EtatMaterielJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EtatMateriel etatMateriel) {
        if (etatMateriel.getMaterielCollection() == null) {
            etatMateriel.setMaterielCollection(new ArrayList<Materiel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Materiel> attachedMaterielCollection = new ArrayList<Materiel>();
            for (Materiel materielCollectionMaterielToAttach : etatMateriel.getMaterielCollection()) {
                materielCollectionMaterielToAttach = em.getReference(materielCollectionMaterielToAttach.getClass(), materielCollectionMaterielToAttach.getIdMateriel());
                attachedMaterielCollection.add(materielCollectionMaterielToAttach);
            }
            etatMateriel.setMaterielCollection(attachedMaterielCollection);
            em.persist(etatMateriel);
            for (Materiel materielCollectionMateriel : etatMateriel.getMaterielCollection()) {
                EtatMateriel oldIdEtatMaterielOfMaterielCollectionMateriel = materielCollectionMateriel.getIdEtatMateriel();
                materielCollectionMateriel.setIdEtatMateriel(etatMateriel);
                materielCollectionMateriel = em.merge(materielCollectionMateriel);
                if (oldIdEtatMaterielOfMaterielCollectionMateriel != null) {
                    oldIdEtatMaterielOfMaterielCollectionMateriel.getMaterielCollection().remove(materielCollectionMateriel);
                    oldIdEtatMaterielOfMaterielCollectionMateriel = em.merge(oldIdEtatMaterielOfMaterielCollectionMateriel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EtatMateriel etatMateriel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EtatMateriel persistentEtatMateriel = em.find(EtatMateriel.class, etatMateriel.getIdEtatMateriel());
            Collection<Materiel> materielCollectionOld = persistentEtatMateriel.getMaterielCollection();
            Collection<Materiel> materielCollectionNew = etatMateriel.getMaterielCollection();
            Collection<Materiel> attachedMaterielCollectionNew = new ArrayList<Materiel>();
            for (Materiel materielCollectionNewMaterielToAttach : materielCollectionNew) {
                materielCollectionNewMaterielToAttach = em.getReference(materielCollectionNewMaterielToAttach.getClass(), materielCollectionNewMaterielToAttach.getIdMateriel());
                attachedMaterielCollectionNew.add(materielCollectionNewMaterielToAttach);
            }
            materielCollectionNew = attachedMaterielCollectionNew;
            etatMateriel.setMaterielCollection(materielCollectionNew);
            etatMateriel = em.merge(etatMateriel);
            for (Materiel materielCollectionOldMateriel : materielCollectionOld) {
                if (!materielCollectionNew.contains(materielCollectionOldMateriel)) {
                    materielCollectionOldMateriel.setIdEtatMateriel(null);
                    materielCollectionOldMateriel = em.merge(materielCollectionOldMateriel);
                }
            }
            for (Materiel materielCollectionNewMateriel : materielCollectionNew) {
                if (!materielCollectionOld.contains(materielCollectionNewMateriel)) {
                    EtatMateriel oldIdEtatMaterielOfMaterielCollectionNewMateriel = materielCollectionNewMateriel.getIdEtatMateriel();
                    materielCollectionNewMateriel.setIdEtatMateriel(etatMateriel);
                    materielCollectionNewMateriel = em.merge(materielCollectionNewMateriel);
                    if (oldIdEtatMaterielOfMaterielCollectionNewMateriel != null && !oldIdEtatMaterielOfMaterielCollectionNewMateriel.equals(etatMateriel)) {
                        oldIdEtatMaterielOfMaterielCollectionNewMateriel.getMaterielCollection().remove(materielCollectionNewMateriel);
                        oldIdEtatMaterielOfMaterielCollectionNewMateriel = em.merge(oldIdEtatMaterielOfMaterielCollectionNewMateriel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = etatMateriel.getIdEtatMateriel();
                if (findEtatMateriel(id) == null) {
                    throw new NonexistentEntityException("The etatMateriel with id " + id + " no longer exists.");
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
            EtatMateriel etatMateriel;
            try {
                etatMateriel = em.getReference(EtatMateriel.class, id);
                etatMateriel.getIdEtatMateriel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etatMateriel with id " + id + " no longer exists.", enfe);
            }
            Collection<Materiel> materielCollection = etatMateriel.getMaterielCollection();
            for (Materiel materielCollectionMateriel : materielCollection) {
                materielCollectionMateriel.setIdEtatMateriel(null);
                materielCollectionMateriel = em.merge(materielCollectionMateriel);
            }
            em.remove(etatMateriel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EtatMateriel> findEtatMaterielEntities() {
        return findEtatMaterielEntities(true, -1, -1);
    }

    public List<EtatMateriel> findEtatMaterielEntities(int maxResults, int firstResult) {
        return findEtatMaterielEntities(false, maxResults, firstResult);
    }

    private List<EtatMateriel> findEtatMaterielEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EtatMateriel.class));
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

    public EtatMateriel findEtatMateriel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EtatMateriel.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtatMaterielCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EtatMateriel> rt = cq.from(EtatMateriel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
