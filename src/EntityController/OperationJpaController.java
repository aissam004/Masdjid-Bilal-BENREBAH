/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.Operation;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.TypeTransaction;
import Entity.Personne;
import EntityController.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pc
 */
public class OperationJpaController implements Serializable {

    public OperationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operation operation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TypeTransaction idTypeOperation = operation.getIdTypeOperation();
            if (idTypeOperation != null) {
                idTypeOperation = em.getReference(idTypeOperation.getClass(), idTypeOperation.getIdTypeTransaction());
                operation.setIdTypeOperation(idTypeOperation);
            }
            Personne idPersonne = operation.getIdPersonne();
            if (idPersonne != null) {
                idPersonne = em.getReference(idPersonne.getClass(), idPersonne.getIdPersonne());
                operation.setIdPersonne(idPersonne);
            }
            em.persist(operation);
            if (idTypeOperation != null) {
                idTypeOperation.getOperationCollection().add(operation);
                idTypeOperation = em.merge(idTypeOperation);
            }
            if (idPersonne != null) {
                idPersonne.getOperationCollection().add(operation);
                idPersonne = em.merge(idPersonne);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operation operation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operation persistentOperation = em.find(Operation.class, operation.getIdOperation());
            TypeTransaction idTypeOperationOld = persistentOperation.getIdTypeOperation();
            TypeTransaction idTypeOperationNew = operation.getIdTypeOperation();
            Personne idPersonneOld = persistentOperation.getIdPersonne();
            Personne idPersonneNew = operation.getIdPersonne();
            if (idTypeOperationNew != null) {
                idTypeOperationNew = em.getReference(idTypeOperationNew.getClass(), idTypeOperationNew.getIdTypeTransaction());
                operation.setIdTypeOperation(idTypeOperationNew);
            }
            if (idPersonneNew != null) {
                idPersonneNew = em.getReference(idPersonneNew.getClass(), idPersonneNew.getIdPersonne());
                operation.setIdPersonne(idPersonneNew);
            }
            operation = em.merge(operation);
            if (idTypeOperationOld != null && !idTypeOperationOld.equals(idTypeOperationNew)) {
                idTypeOperationOld.getOperationCollection().remove(operation);
                idTypeOperationOld = em.merge(idTypeOperationOld);
            }
            if (idTypeOperationNew != null && !idTypeOperationNew.equals(idTypeOperationOld)) {
                idTypeOperationNew.getOperationCollection().add(operation);
                idTypeOperationNew = em.merge(idTypeOperationNew);
            }
            if (idPersonneOld != null && !idPersonneOld.equals(idPersonneNew)) {
                idPersonneOld.getOperationCollection().remove(operation);
                idPersonneOld = em.merge(idPersonneOld);
            }
            if (idPersonneNew != null && !idPersonneNew.equals(idPersonneOld)) {
                idPersonneNew.getOperationCollection().add(operation);
                idPersonneNew = em.merge(idPersonneNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operation.getIdOperation();
                if (findOperation(id) == null) {
                    throw new NonexistentEntityException("The operation with id " + id + " no longer exists.");
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
            Operation operation;
            try {
                operation = em.getReference(Operation.class, id);
                operation.getIdOperation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operation with id " + id + " no longer exists.", enfe);
            }
            TypeTransaction idTypeOperation = operation.getIdTypeOperation();
            if (idTypeOperation != null) {
                idTypeOperation.getOperationCollection().remove(operation);
                idTypeOperation = em.merge(idTypeOperation);
            }
            Personne idPersonne = operation.getIdPersonne();
            if (idPersonne != null) {
                idPersonne.getOperationCollection().remove(operation);
                idPersonne = em.merge(idPersonne);
            }
            em.remove(operation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operation> findOperationEntities() {
        return findOperationEntities(true, -1, -1);
    }

    public List<Operation> findOperationEntities(int maxResults, int firstResult) {
        return findOperationEntities(false, maxResults, firstResult);
    }

    private List<Operation> findOperationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operation.class));
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

    public Operation findOperation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operation.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operation> rt = cq.from(Operation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
