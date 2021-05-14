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
import Entity.Compte;
import Entity.CompteBancaire;
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
public class CompteBancaireJpaController implements Serializable {

    public CompteBancaireJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CompteBancaire compteBancaire) {
        if (compteBancaire.getCompteCollection() == null) {
            compteBancaire.setCompteCollection(new ArrayList<Compte>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Compte> attachedCompteCollection = new ArrayList<Compte>();
            for (Compte compteCollectionCompteToAttach : compteBancaire.getCompteCollection()) {
                compteCollectionCompteToAttach = em.getReference(compteCollectionCompteToAttach.getClass(), compteCollectionCompteToAttach.getIdCompte());
                attachedCompteCollection.add(compteCollectionCompteToAttach);
            }
            compteBancaire.setCompteCollection(attachedCompteCollection);
            em.persist(compteBancaire);
            for (Compte compteCollectionCompte : compteBancaire.getCompteCollection()) {
                CompteBancaire oldIdCompteBancaireOfCompteCollectionCompte = compteCollectionCompte.getIdCompteBancaire();
                compteCollectionCompte.setIdCompteBancaire(compteBancaire);
                compteCollectionCompte = em.merge(compteCollectionCompte);
                if (oldIdCompteBancaireOfCompteCollectionCompte != null) {
                    oldIdCompteBancaireOfCompteCollectionCompte.getCompteCollection().remove(compteCollectionCompte);
                    oldIdCompteBancaireOfCompteCollectionCompte = em.merge(oldIdCompteBancaireOfCompteCollectionCompte);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CompteBancaire compteBancaire) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CompteBancaire persistentCompteBancaire = em.find(CompteBancaire.class, compteBancaire.getIdCompteBancaire());
            Collection<Compte> compteCollectionOld = persistentCompteBancaire.getCompteCollection();
            Collection<Compte> compteCollectionNew = compteBancaire.getCompteCollection();
            Collection<Compte> attachedCompteCollectionNew = new ArrayList<Compte>();
            for (Compte compteCollectionNewCompteToAttach : compteCollectionNew) {
                compteCollectionNewCompteToAttach = em.getReference(compteCollectionNewCompteToAttach.getClass(), compteCollectionNewCompteToAttach.getIdCompte());
                attachedCompteCollectionNew.add(compteCollectionNewCompteToAttach);
            }
            compteCollectionNew = attachedCompteCollectionNew;
            compteBancaire.setCompteCollection(compteCollectionNew);
            compteBancaire = em.merge(compteBancaire);
            for (Compte compteCollectionOldCompte : compteCollectionOld) {
                if (!compteCollectionNew.contains(compteCollectionOldCompte)) {
                    compteCollectionOldCompte.setIdCompteBancaire(null);
                    compteCollectionOldCompte = em.merge(compteCollectionOldCompte);
                }
            }
            for (Compte compteCollectionNewCompte : compteCollectionNew) {
                if (!compteCollectionOld.contains(compteCollectionNewCompte)) {
                    CompteBancaire oldIdCompteBancaireOfCompteCollectionNewCompte = compteCollectionNewCompte.getIdCompteBancaire();
                    compteCollectionNewCompte.setIdCompteBancaire(compteBancaire);
                    compteCollectionNewCompte = em.merge(compteCollectionNewCompte);
                    if (oldIdCompteBancaireOfCompteCollectionNewCompte != null && !oldIdCompteBancaireOfCompteCollectionNewCompte.equals(compteBancaire)) {
                        oldIdCompteBancaireOfCompteCollectionNewCompte.getCompteCollection().remove(compteCollectionNewCompte);
                        oldIdCompteBancaireOfCompteCollectionNewCompte = em.merge(oldIdCompteBancaireOfCompteCollectionNewCompte);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compteBancaire.getIdCompteBancaire();
                if (findCompteBancaire(id) == null) {
                    throw new NonexistentEntityException("The compteBancaire with id " + id + " no longer exists.");
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
            CompteBancaire compteBancaire;
            try {
                compteBancaire = em.getReference(CompteBancaire.class, id);
                compteBancaire.getIdCompteBancaire();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compteBancaire with id " + id + " no longer exists.", enfe);
            }
            Collection<Compte> compteCollection = compteBancaire.getCompteCollection();
            for (Compte compteCollectionCompte : compteCollection) {
                compteCollectionCompte.setIdCompteBancaire(null);
                compteCollectionCompte = em.merge(compteCollectionCompte);
            }
            em.remove(compteBancaire);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CompteBancaire> findCompteBancaireEntities() {
        return findCompteBancaireEntities(true, -1, -1);
    }

    public List<CompteBancaire> findCompteBancaireEntities(int maxResults, int firstResult) {
        return findCompteBancaireEntities(false, maxResults, firstResult);
    }

    private List<CompteBancaire> findCompteBancaireEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CompteBancaire.class));
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

    public CompteBancaire findCompteBancaire(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CompteBancaire.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompteBancaireCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CompteBancaire> rt = cq.from(CompteBancaire.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
