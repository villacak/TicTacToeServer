package com.server.tictactoe.persistence.daos;

import com.server.tictactoe.persistence.entities.GamesEntity;
import com.server.tictactoe.persistence.helper.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by klausvillaca on 1/15/16.
 */
public class GamesDAO {

    public static final String ID = "idgames";
    public static final String WON = "wonXOrY";


    private EntityManagerHelper emHelper;

    public GamesDAO() {
        emHelper = new EntityManagerHelper();
    }

    // Just for tests
    public GamesDAO(EntityManagerHelper emHelper) {
        this.emHelper = emHelper;
    }


    private EntityManager getEntityManager() {
        return emHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved User entity. All
     * subsequent persist actions of this entity should use the #update()
     * method. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link EntityManager#persist(Object)
     * EntityManager#persist} operation.
     *
     * <pre>
     *
     * EntityManagerHelper.beginTransaction();
     * GamesEntity.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     *
     * @param entity
     *            User entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */

    public void save(GamesEntity entity) {
        emHelper.log("saving User instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            emHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            emHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent User entity. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently deleted from the persistence store, i.e., database. This
     * method uses the {@link EntityManager#remove(Object)
     * EntityManager#delete} operation.
     *
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * GamesEntity.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     *
     * @param entity
     *            User entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(GamesEntity entity) {
        emHelper.log("deleting User instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(GamesEntity.class, entity.getIdgames());
            getEntityManager().remove(entity);
            emHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            emHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved User entity and return it or a copy of it
     * to the sender. A copy of the User entity parameter is returned when
     * the JPA persistence mechanism has not previously been tracking the
     * updated entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link EntityManager#merge(Object) EntityManager#merge}
     * operation.
     *
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = GamesEntity.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     *
     * @param entity
     *            User entity to update
     * @return GamesEntity the persisted User entity instance, may not be the
     *         same
     * @throws RuntimeException
     *             if the operation fails
     */
    public GamesEntity update(GamesEntity entity) {
        emHelper.log("updating User instance", Level.INFO, null);
        try {
            GamesEntity result = getEntityManager().merge(entity);
            emHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            emHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public GamesEntity findById(Integer id) {
        emHelper.log("finding User instance with id: " + id, Level.INFO, null);
        try {
            GamesEntity instance = getEntityManager().find(GamesEntity.class, id);
            return instance;
        } catch (RuntimeException re) {
            emHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all User entities with a specific property value.
     *
     * @param propertyName
     *            the name of the User property to query
     * @param value
     *            the property value to match
     * @return List<GamesEntity> found by query
     */
    @SuppressWarnings("unchecked")
    public List<GamesEntity> findByProperty(String propertyName, final Object value) {
        emHelper.log("finding User instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
        try {
            final String queryString = "select model from GamesEntity model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            emHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<GamesEntity> findById(Object id) {
        return findByProperty(ID, id);
    }

    public List<GamesEntity> findByName(Object name) {
        return findByProperty(WON, name);
    }

    /**
     * Find all User entities.
     *
     * @return List<GamesEntity> all User entities
     */
    @SuppressWarnings("unchecked")
    public List<GamesEntity> findAll() {
        emHelper.log("finding all User instances", Level.INFO, null);
        try {
            final String queryString = "select model from GamesEntity model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            emHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}
