package com.server.tictactoe.persistence.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;



/**
 * EntityManager Helper
 *
 * @author Klaus Villaca
 */
public class EntityManagerHelper {

    protected EntityManagerFactory emf;
    protected ThreadLocal<EntityManager> threadLocal;
    protected Logger logger;


    public EntityManagerHelper() {
        emf = Persistence.createEntityManagerFactory("FirstProject");
        threadLocal = new ThreadLocal<EntityManager>();
        logger = Logger.getLogger("FirstProject");
        logger.setLevel(Level.ALL);
    }


    // Constructor just for tests
    public EntityManagerHelper(EntityManagerFactory emf, ThreadLocal<EntityManager> threadLocal, Logger logger) {
        this.emf = emf;
        this.threadLocal = threadLocal;
        this.logger = logger;
    }

    public EntityManager getEntityManager() {
        EntityManager manager = threadLocal.get();
        if (manager == null || manager.isOpen() == false) {
            manager = emf.createEntityManager();
            threadLocal.set(manager);
        }
        return manager;
    }

    public void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        if (em != null)
            em.close();
    }

    public void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public void log(String info, Level level, Throwable ex) {
        logger.log(level, info, ex);
    }
}