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

    private static final String PU = "ttt_pu";

    protected EntityManagerFactory emf;
    protected ThreadLocal<EntityManager> threadLocal;
    protected Logger logger;


    public EntityManagerHelper() {
        emf = Persistence.createEntityManagerFactory(PU);
        threadLocal = new ThreadLocal<EntityManager>();
        logger = Logger.getLogger(PU);
        logger.setLevel(Level.ALL);
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