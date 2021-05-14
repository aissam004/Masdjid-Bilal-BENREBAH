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
import Entity.TypePersonne;
import Entity.Cotisation;
import java.util.ArrayList;
import java.util.Collection;
import Entity.Operation;
import Entity.Compte;
import Entity.Personne;
import EntityController.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pc
 */
public class PersonneJpaController implements Serializable {

    public PersonneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personne personne) {
        if (personne.getCotisationCollection() == null) {
            personne.setCotisationCollection(new ArrayList<Cotisation>());
        }
        if (personne.getOperationCollection() == null) {
            personne.setOperationCollection(new ArrayList<Operation>());
        }
        if (personne.getCompteCollection() == null) {
            personne.setCompteCollection(new ArrayList<Compte>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TypePersonne idTypePersonne = personne.getIdTypePersonne();
            if (idTypePersonne != null) {
                idTypePersonne = em.getReference(idTypePersonne.getClass(), idTypePersonne.getIdTypePersonne());
                personne.setIdTypePersonne(idTypePersonne);
            }
            Collection<Cotisation> attachedCotisationCollection = new ArrayList<Cotisation>();
            for (Cotisation cotisationCollectionCotisationToAttach : personne.getCotisationCollection()) {
                cotisationCollectionCotisationToAttach = em.getReference(cotisationCollectionCotisationToAttach.getClass(), cotisationCollectionCotisationToAttach.getIdCotisation());
                attachedCotisationCollection.add(cotisationCollectionCotisationToAttach);
            }
            personne.setCotisationCollection(attachedCotisationCollection);
            Collection<Operation> attachedOperationCollection = new ArrayList<Operation>();
            for (Operation operationCollectionOperationToAttach : personne.getOperationCollection()) {
                operationCollectionOperationToAttach = em.getReference(operationCollectionOperationToAttach.getClass(), operationCollectionOperationToAttach.getIdOperation());
                attachedOperationCollection.add(operationCollectionOperationToAttach);
            }
            personne.setOperationCollection(attachedOperationCollection);
            Collection<Compte> attachedCompteCollection = new ArrayList<Compte>();
            for (Compte compteCollectionCompteToAttach : personne.getCompteCollection()) {
                compteCollectionCompteToAttach = em.getReference(compteCollectionCompteToAttach.getClass(), compteCollectionCompteToAttach.getIdCompte());
                attachedCompteCollection.add(compteCollectionCompteToAttach);
            }
            personne.setCompteCollection(attachedCompteCollection);
            em.persist(personne);
            if (idTypePersonne != null) {
                idTypePersonne.getPersonneCollection().add(personne);
                idTypePersonne = em.merge(idTypePersonne);
            }
            for (Cotisation cotisationCollectionCotisation : personne.getCotisationCollection()) {
                Personne oldIdPersonneOfCotisationCollectionCotisation = cotisationCollectionCotisation.getIdPersonne();
                cotisationCollectionCotisation.setIdPersonne(personne);
                cotisationCollectionCotisation = em.merge(cotisationCollectionCotisation);
                if (oldIdPersonneOfCotisationCollectionCotisation != null) {
                    oldIdPersonneOfCotisationCollectionCotisation.getCotisationCollection().remove(cotisationCollectionCotisation);
                    oldIdPersonneOfCotisationCollectionCotisation = em.merge(oldIdPersonneOfCotisationCollectionCotisation);
                }
            }
            for (Operation operationCollectionOperation : personne.getOperationCollection()) {
                Personne oldIdPersonneOfOperationCollectionOperation = operationCollectionOperation.getIdPersonne();
                operationCollectionOperation.setIdPersonne(personne);
                operationCollectionOperation = em.merge(operationCollectionOperation);
                if (oldIdPersonneOfOperationCollectionOperation != null) {
                    oldIdPersonneOfOperationCollectionOperation.getOperationCollection().remove(operationCollectionOperation);
                    oldIdPersonneOfOperationCollectionOperation = em.merge(oldIdPersonneOfOperationCollectionOperation);
                }
            }
            for (Compte compteCollectionCompte : personne.getCompteCollection()) {
                Personne oldIdPersonneOfCompteCollectionCompte = compteCollectionCompte.getIdPersonne();
                compteCollectionCompte.setIdPersonne(personne);
                compteCollectionCompte = em.merge(compteCollectionCompte);
                if (oldIdPersonneOfCompteCollectionCompte != null) {
                    oldIdPersonneOfCompteCollectionCompte.getCompteCollection().remove(compteCollectionCompte);
                    oldIdPersonneOfCompteCollectionCompte = em.merge(oldIdPersonneOfCompteCollectionCompte);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personne personne) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personne persistentPersonne = em.find(Personne.class, personne.getIdPersonne());
            TypePersonne idTypePersonneOld = persistentPersonne.getIdTypePersonne();
            TypePersonne idTypePersonneNew = personne.getIdTypePersonne();
            Collection<Cotisation> cotisationCollectionOld = persistentPersonne.getCotisationCollection();
            Collection<Cotisation> cotisationCollectionNew = personne.getCotisationCollection();
            Collection<Operation> operationCollectionOld = persistentPersonne.getOperationCollection();
            Collection<Operation> operationCollectionNew = personne.getOperationCollection();
            Collection<Compte> compteCollectionOld = persistentPersonne.getCompteCollection();
            Collection<Compte> compteCollectionNew = personne.getCompteCollection();
            if (idTypePersonneNew != null) {
                idTypePersonneNew = em.getReference(idTypePersonneNew.getClass(), idTypePersonneNew.getIdTypePersonne());
                personne.setIdTypePersonne(idTypePersonneNew);
            }
            Collection<Cotisation> attachedCotisationCollectionNew = new ArrayList<Cotisation>();
            for (Cotisation cotisationCollectionNewCotisationToAttach : cotisationCollectionNew) {
                cotisationCollectionNewCotisationToAttach = em.getReference(cotisationCollectionNewCotisationToAttach.getClass(), cotisationCollectionNewCotisationToAttach.getIdCotisation());
                attachedCotisationCollectionNew.add(cotisationCollectionNewCotisationToAttach);
            }
            cotisationCollectionNew = attachedCotisationCollectionNew;
            personne.setCotisationCollection(cotisationCollectionNew);
            Collection<Operation> attachedOperationCollectionNew = new ArrayList<Operation>();
            for (Operation operationCollectionNewOperationToAttach : operationCollectionNew) {
                operationCollectionNewOperationToAttach = em.getReference(operationCollectionNewOperationToAttach.getClass(), operationCollectionNewOperationToAttach.getIdOperation());
                attachedOperationCollectionNew.add(operationCollectionNewOperationToAttach);
            }
            operationCollectionNew = attachedOperationCollectionNew;
            personne.setOperationCollection(operationCollectionNew);
            Collection<Compte> attachedCompteCollectionNew = new ArrayList<Compte>();
            for (Compte compteCollectionNewCompteToAttach : compteCollectionNew) {
                compteCollectionNewCompteToAttach = em.getReference(compteCollectionNewCompteToAttach.getClass(), compteCollectionNewCompteToAttach.getIdCompte());
                attachedCompteCollectionNew.add(compteCollectionNewCompteToAttach);
            }
            compteCollectionNew = attachedCompteCollectionNew;
            personne.setCompteCollection(compteCollectionNew);
            personne = em.merge(personne);
            if (idTypePersonneOld != null && !idTypePersonneOld.equals(idTypePersonneNew)) {
                idTypePersonneOld.getPersonneCollection().remove(personne);
                idTypePersonneOld = em.merge(idTypePersonneOld);
            }
            if (idTypePersonneNew != null && !idTypePersonneNew.equals(idTypePersonneOld)) {
                idTypePersonneNew.getPersonneCollection().add(personne);
                idTypePersonneNew = em.merge(idTypePersonneNew);
            }
            for (Cotisation cotisationCollectionOldCotisation : cotisationCollectionOld) {
                if (!cotisationCollectionNew.contains(cotisationCollectionOldCotisation)) {
                    cotisationCollectionOldCotisation.setIdPersonne(null);
                    cotisationCollectionOldCotisation = em.merge(cotisationCollectionOldCotisation);
                }
            }
            for (Cotisation cotisationCollectionNewCotisation : cotisationCollectionNew) {
                if (!cotisationCollectionOld.contains(cotisationCollectionNewCotisation)) {
                    Personne oldIdPersonneOfCotisationCollectionNewCotisation = cotisationCollectionNewCotisation.getIdPersonne();
                    cotisationCollectionNewCotisation.setIdPersonne(personne);
                    cotisationCollectionNewCotisation = em.merge(cotisationCollectionNewCotisation);
                    if (oldIdPersonneOfCotisationCollectionNewCotisation != null && !oldIdPersonneOfCotisationCollectionNewCotisation.equals(personne)) {
                        oldIdPersonneOfCotisationCollectionNewCotisation.getCotisationCollection().remove(cotisationCollectionNewCotisation);
                        oldIdPersonneOfCotisationCollectionNewCotisation = em.merge(oldIdPersonneOfCotisationCollectionNewCotisation);
                    }
                }
            }
            for (Operation operationCollectionOldOperation : operationCollectionOld) {
                if (!operationCollectionNew.contains(operationCollectionOldOperation)) {
                    operationCollectionOldOperation.setIdPersonne(null);
                    operationCollectionOldOperation = em.merge(operationCollectionOldOperation);
                }
            }
            for (Operation operationCollectionNewOperation : operationCollectionNew) {
                if (!operationCollectionOld.contains(operationCollectionNewOperation)) {
                    Personne oldIdPersonneOfOperationCollectionNewOperation = operationCollectionNewOperation.getIdPersonne();
                    operationCollectionNewOperation.setIdPersonne(personne);
                    operationCollectionNewOperation = em.merge(operationCollectionNewOperation);
                    if (oldIdPersonneOfOperationCollectionNewOperation != null && !oldIdPersonneOfOperationCollectionNewOperation.equals(personne)) {
                        oldIdPersonneOfOperationCollectionNewOperation.getOperationCollection().remove(operationCollectionNewOperation);
                        oldIdPersonneOfOperationCollectionNewOperation = em.merge(oldIdPersonneOfOperationCollectionNewOperation);
                    }
                }
            }
            for (Compte compteCollectionOldCompte : compteCollectionOld) {
                if (!compteCollectionNew.contains(compteCollectionOldCompte)) {
                    compteCollectionOldCompte.setIdPersonne(null);
                    compteCollectionOldCompte = em.merge(compteCollectionOldCompte);
                }
            }
            for (Compte compteCollectionNewCompte : compteCollectionNew) {
                if (!compteCollectionOld.contains(compteCollectionNewCompte)) {
                    Personne oldIdPersonneOfCompteCollectionNewCompte = compteCollectionNewCompte.getIdPersonne();
                    compteCollectionNewCompte.setIdPersonne(personne);
                    compteCollectionNewCompte = em.merge(compteCollectionNewCompte);
                    if (oldIdPersonneOfCompteCollectionNewCompte != null && !oldIdPersonneOfCompteCollectionNewCompte.equals(personne)) {
                        oldIdPersonneOfCompteCollectionNewCompte.getCompteCollection().remove(compteCollectionNewCompte);
                        oldIdPersonneOfCompteCollectionNewCompte = em.merge(oldIdPersonneOfCompteCollectionNewCompte);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personne.getIdPersonne();
                if (findPersonne(id) == null) {
                    throw new NonexistentEntityException("The personne with id " + id + " no longer exists.");
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
            Personne personne;
            try {
                personne = em.getReference(Personne.class, id);
                personne.getIdPersonne();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personne with id " + id + " no longer exists.", enfe);
            }
            TypePersonne idTypePersonne = personne.getIdTypePersonne();
            if (idTypePersonne != null) {
                idTypePersonne.getPersonneCollection().remove(personne);
                idTypePersonne = em.merge(idTypePersonne);
            }
            Collection<Cotisation> cotisationCollection = personne.getCotisationCollection();
            for (Cotisation cotisationCollectionCotisation : cotisationCollection) {
                cotisationCollectionCotisation.setIdPersonne(null);
                cotisationCollectionCotisation = em.merge(cotisationCollectionCotisation);
            }
            Collection<Operation> operationCollection = personne.getOperationCollection();
            for (Operation operationCollectionOperation : operationCollection) {
                operationCollectionOperation.setIdPersonne(null);
                operationCollectionOperation = em.merge(operationCollectionOperation);
            }
            Collection<Compte> compteCollection = personne.getCompteCollection();
            for (Compte compteCollectionCompte : compteCollection) {
                compteCollectionCompte.setIdPersonne(null);
                compteCollectionCompte = em.merge(compteCollectionCompte);
            }
            em.remove(personne);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Personne personne) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            TypePersonne idTypePersonne = personne.getIdTypePersonne();
            if (idTypePersonne != null) {
                idTypePersonne.getPersonneCollection().remove(personne);
                idTypePersonne = em.merge(idTypePersonne);
            }
            Collection<Cotisation> cotisationCollection = personne.getCotisationCollection();
            for (Cotisation cotisationCollectionCotisation : cotisationCollection) {
                cotisationCollectionCotisation.setIdPersonne(null);
                cotisationCollectionCotisation = em.merge(cotisationCollectionCotisation);
            }
            Collection<Operation> operationCollection = personne.getOperationCollection();
            for (Operation operationCollectionOperation : operationCollection) {
                operationCollectionOperation.setIdPersonne(null);
                operationCollectionOperation = em.merge(operationCollectionOperation);
            }
            Collection<Compte> compteCollection = personne.getCompteCollection();
            for (Compte compteCollectionCompte : compteCollection) {
                compteCollectionCompte.setIdPersonne(null);
                compteCollectionCompte = em.merge(compteCollectionCompte);
            }
            if (!em.contains(personne)) {
                personne = em.merge(personne);
            }

            em.remove(personne);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personne> findPersonneEntities() {
        return findPersonneEntities(true, -1, -1);
    }

    public List<Personne> findPersonneEntities(int maxResults, int firstResult) {
        return findPersonneEntities(false, maxResults, firstResult);
    }

    private List<Personne> findPersonneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personne.class));
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

    public Personne findPersonne(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personne.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personne> rt = cq.from(Personne.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
