/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;

/**
 *
 * @author diogo
 */
public abstract class AbstractFacade<T> {

    @EJB
    private DAO DAO;
     
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

  //  protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        //getEntityManager().persist(entity);
        DAO.create(entity);
    }

    public void edit(T entity) {
        //getEntityManager().merge(entity);
        DAO.edit(entity);
    }

    public void remove(T entity) {
       // getEntityManager().remove(getEntityManager().merge(entity));
         DAO.remove(entity);
    }

    public T find(Object id) {
       // return getEntityManager().find(entityClass, id);
        return (T) DAO.find(entityClass, id);
    }

    public List<T> findAll() {
      //  javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
      //  cq.select(cq.from(entityClass));
      //  return getEntityManager().createQuery(cq).getResultList();
       return (List<T>) DAO.findAll(entityClass);
    }

    public List<T> findRange(int[] range) {
        //javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        //cq.select(cq.from(entityClass));
        //javax.persistence.Query q = getEntityManager().createQuery(cq);
        //q.setMaxResults(range[1] - range[0] + 1);
        //q.setFirstResult(range[0]);
        //return q.getResultList();
        return (List<T>) DAO.findRange(entityClass, range);
    }

    public int count() {
       // javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
       // javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
       // cq.select(getEntityManager().getCriteriaBuilder().count(rt));
       // javax.persistence.Query q = getEntityManager().createQuery(cq);
       // return ((Long) q.getSingleResult()).intValue();
        
        return DAO.count(entityClass);
    }
    
}
