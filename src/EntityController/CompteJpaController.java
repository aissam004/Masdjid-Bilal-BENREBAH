/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.Compte;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.CompteBancaire;
import Entity.Personne;
import EntityController.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pc
 */
public class CompteJpaController implements Serializable {

    public CompteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compte compte) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CompteBancaire idCompteBancaire = compte.getIdCompteBancaire();
            if (idCompteBancaire != null) {
                idCompteBancaire = em.getReference(idCompteBancaire.getClass(), idCompteBancaire.getIdCompteBancaire());
                compte.setIdCompteBancaire(idCompteBancaire);
            }
            Personne idPersonne = compte.getIdPersonne();
            if (idPersonne != null) {
                idPersonne = em.getReference(idPersonne.getClass(), idPersonne.getIdPersonne());
                compte.setIdPersonne(idPersonne);
            }
            em.persist(compte);
            if (idCompteBancaire != null) {
                idCompteBancaire.getCompteCollection().add(compte);
                idCompteBancaire = em.merge(idCompteBancaire);
            }
            if (idPersonne != null) {
                idPersonne.getCompteCollection().add(compte);
                idPersonne = em.merge(idPersonne);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compte compte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compte persistentCompte = em.find(Compte.class, compte.getIdCompte());
            CompteBancaire idCompteBancaireOld = persistentCompte.getIdCompteBancaire();
            CompteBancaire idCompteBancaireNew = compte.getIdCompteBancaire();
            Personne idPersonneOld = persistentCompte.getIdPersonne();
            Personne idPersonneNew = compte.getIdPersonne();
            if (idCompteBancaireNew != null) {
                idCompteBancaireNew = em.getReference(idCompteBancaireNew.getClass(), idCompteBancaireNew.getIdCompteBancaire());
                compte.setIdCompteBancaire(idCompteBancaireNew);
            }
            if (idPersonneNew != null) {
                idPersonneNew = em.getReference(idPersonneNew.getClass(), idPersonneNew.getIdPersonne());
                compte.setIdPersonne(idPersonneNew);
            }
            compte = em.merge(compte);
            if (idCompteBancaireOld != null && !idCompteBancaireOld.equals(idCompteBancaireNew)) {
                idCompteBancaireOld.getCompteCollection().remove(compte);
                idCompteBancaireOld = em.merge(idCompteBancaireOld);
            }
            if (idCompteBancaireNew != null && !idCompteBancaireNew.equals(idCompteBancaireOld)) {
                idCompteBancaireNew.getCompteCollection().add(compte);
                idCompteBancaireNew = em.merge(idCompteBancaireNew);
            }
            if (idPersonneOld != null && !idPersonneOld.equals(idPersonneNew)) {
                idPersonneOld.getCompteCollection().remove(compte);
                idPersonneOld = em.merge(idPersonneOld);
            }
            if (idPersonneNew != null && !idPersonneNew.equals(idPersonneOld)) {
                idPersonneNew.getCompteCollection().add(compte);
                idPersonneNew = em.merge(idPersonneNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compte.getIdCompte();
                if (findCompte(id) == null) {
                    throw new NonexistentEntityException("The compte with id " + id + " no longer exists.");
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
            Compte compte;
            try {
                compte = em.getReference(Compte.class, id);
                compte.getIdCompte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compte with id " + id + " no longer exists.", enfe);
            }
            CompteBancaire idCompteBancaire = compte.getIdCompteBancaire();
            if (idCompteBancaire != null) {
                idCompteBancaire.getCompteCollection().remove(compte);
                idCompteBancaire = em.merge(idCompteBancaire);
            }
            Personne idPersonne = compte.getIdPersonne();
            if (idPersonne != null) {
                idPersonne.getCompteCollection().remove(compte);
                idPersonne = em.merge(idPersonne);
            }
            em.remove(compte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compte> findCompteEntities() {
        return findCompteEntities(true, -1, -1);
    }

    public List<Compte> findCompteEntities(int maxResults, int firstResult) {
        return findCompteEntities(false, maxResults, firstResult);
    }

    private List<Compte> findCompteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compte.class));
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

    public Compte findCompte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compte.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compte> rt = cq.from(Compte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
