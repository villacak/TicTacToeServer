package com.server.tictactoe.persistence.daos;

import com.server.tictactoe.persistence.entities.PlayEntity;
import com.server.tictactoe.persistence.helper.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by klausvillaca on 1/15/16.
 */
public class PlayDAO {

    public static final String ID = "idgames";
    public static final String WON = "wonXOrY";


    private EntityManagerHelper emHelper;

    public PlayDAO() {
        emHelper = new EntityManagerHelper();
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
     * PlayEntity.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     *
     * @param entity
     *            User entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */

    public int save(PlayEntity entity) {
        emHelper.log("saving User instance", Level.INFO, null);
        int idToReturn = 0;
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
            idToReturn = entity.getPlayid();
            emHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            emHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
        return idToReturn;
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
     * PlayEntity.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     *
     * @param entity
     *            User entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(PlayEntity entity) {
        emHelper.log("deleting User instance", Level.INFO, null);
        try {
            getEntityManager().getTransaction().begin();
            entity = getEntityManager().getReference(PlayEntity.class, entity.getPlayid());
            getEntityManager().remove(entity);
            getEntityManager().getTransaction().commit();
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
     * entity = PlayEntity.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     *
     * @param entity
     *            User entity to update
     * @return PlayEntity the persisted User entity instance, may not be the
     *         same
     * @throws RuntimeException
     *             if the operation fails
     */
    public PlayEntity update(final PlayEntity entity) {
        emHelper.log("updating User instance", Level.INFO, null);
        try {
            getEntityManager().getTransaction().begin();
            final PlayEntity result = getEntityManager().merge(entity);
            getEntityManager().getTransaction().commit();
            emHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            emHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }


    /**
     * Find by PK
     * @param id
     * @return
     */
    public PlayEntity findById(final int id) {
        emHelper.log("finding User instance with id: " + id, Level.INFO, null);
        try {
            final PlayEntity instance = getEntityManager().find(PlayEntity.class, id);
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
     * @return List<PlayEntity> found by query
     */
    @SuppressWarnings("unchecked")
    public List<PlayEntity> findByProperty(final String propertyName, final Object value) {
        emHelper.log("finding User instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
        try {
            final String queryString = "select model from PlayEntity model where model." + propertyName
                    + "= :propertyValue";
            final Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            emHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<PlayEntity> findById(final Object id) {
        return findByProperty(ID, id);
    }

    public List<PlayEntity> findByName(final Object name) {
        return findByProperty(WON, name);
    }

    /**
     * Find all User entities.
     *
     * @return List<PlayEntity> all User entities
     */
    @SuppressWarnings("unchecked")
    public List<PlayEntity> findAll() {
        emHelper.log("finding all User instances", Level.INFO, null);
        try {
            final String queryString = "select model from PlayEntity model";
            final Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            emHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}
