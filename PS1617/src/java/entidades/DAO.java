
package entidades;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@Singleton
public class DAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PS1617PU");


    private EntityManager em = null;//emf.createEntityManager();

//    @Resource
//    private UserTransaction utx;
  
    public EntityManager getEntityManager() {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "getEntityManager from DAO");
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("PS1617PU");
        }
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }


    public void create(Object entity) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Create Entity " + entity);
        getEntityManager().persist(entity);
    }


    public void edit(Object entity) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Edit Entity " + entity);
        getEntityManager().merge(entity);
    }


    public void remove(Object entity) {
        Logger.getLogger(getClass().getName()).log(Level.FINE, "Remove Entity " + entity);
        getEntityManager().remove(getEntityManager().merge(entity));
    }


    public Object find(Class s, Object id) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find Entity " + s.getName() + " -> " + id);
        return getEntityManager().find(s, id);
    }


    public List<Object> findAll(Class s) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find All Entity" + s.getName());
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(s));
        return getEntityManager().createQuery(cq).getResultList();
    }


    public List<Object> findByNamedQuery(Class s, String nameQuery, int maxResult) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find All Entity by namedQuery 1 arg" + s.getName() + " -> " + nameQuery);
        TypedQuery q = getEntityManager().createNamedQuery(nameQuery, s);
        q.setMaxResults(maxResult);
        return q.getResultList();
    }


    public List<Object> findByNamedQuery(Class s, String nameQuery, String nameParam, Object valeu) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find All Entity by namedQuery 1 arg" + s.getName() + " -> " + nameQuery);
        TypedQuery q = getEntityManager().createNamedQuery(nameQuery, s);
        if (!nameParam.isEmpty()) //Por enquando suporta um parametro
        {
            q.setParameter(nameParam, valeu);
        }
        return q.getResultList();
    }

 
    public List<Object> findByNamedQuery(Class s, String nameQuery, String nameParam1, Object valeu1, String nameParam2, Object valeu2) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find All Entity by namedQuery 2 args" + s.getName() + " -> " + nameQuery);
        TypedQuery q = getEntityManager().createNamedQuery(nameQuery, s);
        if (!nameParam1.isEmpty()) //Por enquando suporta um parametro
        {
            q.setParameter(nameParam1, valeu1);
        }
        if (!nameParam2.isEmpty()) //Por enquando suporta um parametro
        {
            q.setParameter(nameParam2, valeu2);
        }
        return q.getResultList();
    }


    public List<Object> findRange(Class s, int[] range) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find Range of Entity " + s.getName());
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(s));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }


    public List<Object> findRangeByNamedQuery(Class s, int[] range, String nameQuery, String nameParam, Object valeu) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Find Range of Entity by named query " + s.getName() + " -> " + nameQuery);
        TypedQuery q = getEntityManager().createNamedQuery(nameQuery, s);
        if (!nameParam.isEmpty()) //Por enquando suporta um parametro
        {
            q.setParameter(nameParam, valeu);
        }
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }


    public int count(Class s) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Count Entity " + s.getName());
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Object> rt = cq.from(s);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }


    public int countByNamedQuery(Class s, String nameQuery, String nameParam, Object valeu) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Count Entity by named query " + s.getName() + " -> " + nameQuery);
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        javax.persistence.criteria.Root<Object> rt = cq.from(s);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
        TypedQuery q = getEntityManager().createNamedQuery(nameQuery, s);
        if (!nameParam.isEmpty()) //Por enquando suporta um parametro
        {
            q.setParameter(nameParam, valeu);
        }        
        return ((Long) q.getSingleResult()).intValue();
    }

    @PostConstruct
    public void loadstate() {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "A abrir as ligações");
        this.getEntityManager();
    }

    @PreDestroy
    public void destruct() {
//        Logger.getLogger(getClass().getName()).log(Level.INFO, "Fechar as ligações");
        //em.getTransaction().commit();
        em.close();
        emf.close();
        em = null;
        emf = null;

    }


    public void editWithCommit(Object entity) {
        /* Add this to the deployment descriptor of this module (e.g. web.xml, ejb-jar.xml):
         * <persistence-context-ref>
         * <persistence-context-ref-name>persistence/LogicalName</persistence-context-ref-name>
         * <persistence-unit-name>WebApplication2PU</persistence-unit-name>
         * </persistence-context-ref>
         * <resource-ref>
         * <res-ref-name>UserTransaction</res-ref-name>
         * <res-type>javax.transaction.UserTransaction</res-type>
         * <res-auth>Container</res-auth>
         * </resource-ref> */
        try {
//            Logger.getLogger(getClass().getName()).log(Level.INFO, "Alterar e faz commmit ao registo " + entity);
            EntityTransaction trans = getEntityManager().getTransaction();
            trans.begin();
            em.merge(entity);
            trans.commit();

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

  
    public void createWithCommit(Object entity) {
        try {
//            Logger.getLogger(getClass().getName()).log(Level.INFO, "Cria e faz commmit ao registo " + entity);
            EntityTransaction trans = getEntityManager().getTransaction();
            trans.begin();
            em.persist(entity);
            trans.commit();

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }


    public void removeWithCommit(Object entity) {
        try {
//            Logger.getLogger(getClass().getName()).log(Level.INFO, "elimina e faz commmit ao registo " + entity);
            EntityTransaction trans = getEntityManager().getTransaction();
            trans.begin();
            em.remove(getEntityManager().merge(entity));
            trans.commit();

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
