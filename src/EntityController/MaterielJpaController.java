/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.CategorieMateriel;
import Entity.EtatMateriel;
import Entity.Emplacement;
import Entity.Materiel;
import EntityController.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pc
 */
public class MaterielJpaController implements Serializable {

    public MaterielJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materiel materiel) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategorieMateriel idCategorieMateriel = materiel.getIdCategorieMateriel();
            if (idCategorieMateriel != null) {
                idCategorieMateriel = em.getReference(idCategorieMateriel.getClass(), idCategorieMateriel.getIdCategorieMateriel());
                materiel.setIdCategorieMateriel(idCategorieMateriel);
            }
            EtatMateriel idEtatMateriel = materiel.getIdEtatMateriel();
            if (idEtatMateriel != null) {
                idEtatMateriel = em.getReference(idEtatMateriel.getClass(), idEtatMateriel.getIdEtatMateriel());
                materiel.setIdEtatMateriel(idEtatMateriel);
            }
            Emplacement idEmplacement = materiel.getIdEmplacement();
            if (idEmplacement != null) {
                idEmplacement = em.getReference(idEmplacement.getClass(), idEmplacement.getIdEmplacement());
                materiel.setIdEmplacement(idEmplacement);
            }
            em.persist(materiel);
            if (idCategorieMateriel != null) {
                idCategorieMateriel.getMaterielCollection().add(materiel);
                idCategorieMateriel = em.merge(idCategorieMateriel);
            }
            if (idEtatMateriel != null) {
                idEtatMateriel.getMaterielCollection().add(materiel);
                idEtatMateriel = em.merge(idEtatMateriel);
            }
            if (idEmplacement != null) {
                idEmplacement.getMaterielCollection().add(materiel);
                idEmplacement = em.merge(idEmplacement);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materiel materiel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materiel persistentMateriel = em.find(Materiel.class, materiel.getIdMateriel());
            CategorieMateriel idCategorieMaterielOld = persistentMateriel.getIdCategorieMateriel();
            CategorieMateriel idCategorieMaterielNew = materiel.getIdCategorieMateriel();
            EtatMateriel idEtatMaterielOld = persistentMateriel.getIdEtatMateriel();
            EtatMateriel idEtatMaterielNew = materiel.getIdEtatMateriel();
            Emplacement idEmplacementOld = persistentMateriel.getIdEmplacement();
            Emplacement idEmplacementNew = materiel.getIdEmplacement();
            if (idCategorieMaterielNew != null) {
                idCategorieMaterielNew = em.getReference(idCategorieMaterielNew.getClass(), idCategorieMaterielNew.getIdCategorieMateriel());
                materiel.setIdCategorieMateriel(idCategorieMaterielNew);
            }
            if (idEtatMaterielNew != null) {
                idEtatMaterielNew = em.getReference(idEtatMaterielNew.getClass(), idEtatMaterielNew.getIdEtatMateriel());
                materiel.setIdEtatMateriel(idEtatMaterielNew);
            }
            if (idEmplacementNew != null) {
                idEmplacementNew = em.getReference(idEmplacementNew.getClass(), idEmplacementNew.getIdEmplacement());
                materiel.setIdEmplacement(idEmplacementNew);
            }
            materiel = em.merge(materiel);
            if (idCategorieMaterielOld != null && !idCategorieMaterielOld.equals(idCategorieMaterielNew)) {
                idCategorieMaterielOld.getMaterielCollection().remove(materiel);
                idCategorieMaterielOld = em.merge(idCategorieMaterielOld);
            }
            if (idCategorieMaterielNew != null && !idCategorieMaterielNew.equals(idCategorieMaterielOld)) {
                idCategorieMaterielNew.getMaterielCollection().add(materiel);
                idCategorieMaterielNew = em.merge(idCategorieMaterielNew);
            }
            if (idEtatMaterielOld != null && !idEtatMaterielOld.equals(idEtatMaterielNew)) {
                idEtatMaterielOld.getMaterielCollection().remove(materiel);
                idEtatMaterielOld = em.merge(idEtatMaterielOld);
            }
            if (idEtatMaterielNew != null && !idEtatMaterielNew.equals(idEtatMaterielOld)) {
                idEtatMaterielNew.getMaterielCollection().add(materiel);
                idEtatMaterielNew = em.merge(idEtatMaterielNew);
            }
            if (idEmplacementOld != null && !idEmplacementOld.equals(idEmplacementNew)) {
                idEmplacementOld.getMaterielCollection().remove(materiel);
                idEmplacementOld = em.merge(idEmplacementOld);
            }
            if (idEmplacementNew != null && !idEmplacementNew.equals(idEmplacementOld)) {
                idEmplacementNew.getMaterielCollection().add(materiel);
                idEmplacementNew = em.merge(idEmplacementNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materiel.getIdMateriel();
                if (findMateriel(id) == null) {
                    throw new NonexistentEntityException("The materiel with id " + id + " no longer exists.");
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
            Materiel materiel;
            try {
                materiel = em.getReference(Materiel.class, id);
                materiel.getIdMateriel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materiel with id " + id + " no longer exists.", enfe);
            }
            CategorieMateriel idCategorieMateriel = materiel.getIdCategorieMateriel();
            if (idCategorieMateriel != null) {
                idCategorieMateriel.getMaterielCollection().remove(materiel);
                idCategorieMateriel = em.merge(idCategorieMateriel);
            }
            EtatMateriel idEtatMateriel = materiel.getIdEtatMateriel();
            if (idEtatMateriel != null) {
                idEtatMateriel.getMaterielCollection().remove(materiel);
                idEtatMateriel = em.merge(idEtatMateriel);
            }
            Emplacement idEmplacement = materiel.getIdEmplacement();
            if (idEmplacement != null) {
                idEmplacement.getMaterielCollection().remove(materiel);
                idEmplacement = em.merge(idEmplacement);
            }
            em.remove(materiel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
     public void destroy(Materiel materiel) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            CategorieMateriel idCategorieMateriel = materiel.getIdCategorieMateriel();
            if (idCategorieMateriel != null) {
                idCategorieMateriel.getMaterielCollection().remove(materiel);
                idCategorieMateriel = em.merge(idCategorieMateriel);
            }
            EtatMateriel idEtatMateriel = materiel.getIdEtatMateriel();
            if (idEtatMateriel != null) {
                idEtatMateriel.getMaterielCollection().remove(materiel);
                idEtatMateriel = em.merge(idEtatMateriel);
            }
            Emplacement idEmplacement = materiel.getIdEmplacement();
            if (idEmplacement != null) {
                idEmplacement.getMaterielCollection().remove(materiel);
                idEmplacement = em.merge(idEmplacement);
            }
            if (!em.contains(materiel)) {
                materiel = em.merge(materiel);
            }
            em.remove(materiel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materiel> findMaterielEntities() {
        return findMaterielEntities(true, -1, -1);
    }

    public List<Materiel> findMaterielEntities(int maxResults, int firstResult) {
        return findMaterielEntities(false, maxResults, firstResult);
    }

    private List<Materiel> findMaterielEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materiel.class));
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

    public Materiel findMateriel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materiel.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterielCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materiel> rt = cq.from(Materiel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
