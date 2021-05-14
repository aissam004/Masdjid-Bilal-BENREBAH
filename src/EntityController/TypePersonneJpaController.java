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
import Entity.Personne;
import Entity.TypePersonne;
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
public class TypePersonneJpaController implements Serializable {

    public TypePersonneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypePersonne typePersonne) {
        if (typePersonne.getPersonneCollection() == null) {
            typePersonne.setPersonneCollection(new ArrayList<Personne>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Personne> attachedPersonneCollection = new ArrayList<Personne>();
            for (Personne personneCollectionPersonneToAttach : typePersonne.getPersonneCollection()) {
                personneCollectionPersonneToAttach = em.getReference(personneCollectionPersonneToAttach.getClass(), personneCollectionPersonneToAttach.getIdPersonne());
                attachedPersonneCollection.add(personneCollectionPersonneToAttach);
            }
            typePersonne.setPersonneCollection(attachedPersonneCollection);
            em.persist(typePersonne);
            for (Personne personneCollectionPersonne : typePersonne.getPersonneCollection()) {
                TypePersonne oldIdTypePersonneOfPersonneCollectionPersonne = personneCollectionPersonne.getIdTypePersonne();
                personneCollectionPersonne.setIdTypePersonne(typePersonne);
                personneCollectionPersonne = em.merge(personneCollectionPersonne);
                if (oldIdTypePersonneOfPersonneCollectionPersonne != null) {
                    oldIdTypePersonneOfPersonneCollectionPersonne.getPersonneCollection().remove(personneCollectionPersonne);
                    oldIdTypePersonneOfPersonneCollectionPersonne = em.merge(oldIdTypePersonneOfPersonneCollectionPersonne);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TypePersonne typePersonne) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TypePersonne persistentTypePersonne = em.find(TypePersonne.class, typePersonne.getIdTypePersonne());
            Collection<Personne> personneCollectionOld = persistentTypePersonne.getPersonneCollection();
            Collection<Personne> personneCollectionNew = typePersonne.getPersonneCollection();
            Collection<Personne> attachedPersonneCollectionNew = new ArrayList<Personne>();
            for (Personne personneCollectionNewPersonneToAttach : personneCollectionNew) {
                personneCollectionNewPersonneToAttach = em.getReference(personneCollectionNewPersonneToAttach.getClass(), personneCollectionNewPersonneToAttach.getIdPersonne());
                attachedPersonneCollectionNew.add(personneCollectionNewPersonneToAttach);
            }
            personneCollectionNew = attachedPersonneCollectionNew;
            typePersonne.setPersonneCollection(personneCollectionNew);
            typePersonne = em.merge(typePersonne);
            for (Personne personneCollectionOldPersonne : personneCollectionOld) {
                if (!personneCollectionNew.contains(personneCollectionOldPersonne)) {
                    personneCollectionOldPersonne.setIdTypePersonne(null);
                    personneCollectionOldPersonne = em.merge(personneCollectionOldPersonne);
                }
            }
            for (Personne personneCollectionNewPersonne : personneCollectionNew) {
                if (!personneCollectionOld.contains(personneCollectionNewPersonne)) {
                    TypePersonne oldIdTypePersonneOfPersonneCollectionNewPersonne = personneCollectionNewPersonne.getIdTypePersonne();
                    personneCollectionNewPersonne.setIdTypePersonne(typePersonne);
                    personneCollectionNewPersonne = em.merge(personneCollectionNewPersonne);
                    if (oldIdTypePersonneOfPersonneCollectionNewPersonne != null && !oldIdTypePersonneOfPersonneCollectionNewPersonne.equals(typePersonne)) {
                        oldIdTypePersonneOfPersonneCollectionNewPersonne.getPersonneCollection().remove(personneCollectionNewPersonne);
                        oldIdTypePersonneOfPersonneCollectionNewPersonne = em.merge(oldIdTypePersonneOfPersonneCollectionNewPersonne);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = typePersonne.getIdTypePersonne();
                if (findTypePersonne(id) == null) {
                    throw new NonexistentEntityException("The typePersonne with id " + id + " no longer exists.");
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
            TypePersonne typePersonne;
            try {
                typePersonne = em.getReference(TypePersonne.class, id);
                typePersonne.getIdTypePersonne();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typePersonne with id " + id + " no longer exists.", enfe);
            }
            Collection<Personne> personneCollection = typePersonne.getPersonneCollection();
            for (Personne personneCollectionPersonne : personneCollection) {
                personneCollectionPersonne.setIdTypePersonne(null);
                personneCollectionPersonne = em.merge(personneCollectionPersonne);
            }
            em.remove(typePersonne);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TypePersonne> findTypePersonneEntities() {
        return findTypePersonneEntities(true, -1, -1);
    }

    public List<TypePersonne> findTypePersonneEntities(int maxResults, int firstResult) {
        return findTypePersonneEntities(false, maxResults, firstResult);
    }

    private List<TypePersonne> findTypePersonneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypePersonne.class));
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

    public TypePersonne findTypePersonne(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypePersonne.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypePersonneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypePersonne> rt = cq.from(TypePersonne.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
