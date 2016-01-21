package com.server.tictactoe.persistence.daos;

import com.server.tictactoe.persistence.entities.UserEntity;
import com.server.tictactoe.persistence.helper.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by klausvillaca on 1/15/16.
 */
public class UserDAO {

    public static final String ID = "iduser";
    public static final String NAME = "userName";

    private EntityManagerHelper emHelper;

    public UserDAO() {
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
     * {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     *
     * <pre>
     *
     * EntityManagerHelper.beginTransaction();
     * UserEntity.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     *
     * @param entity
     *            User entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */

    public int save(UserEntity entity) {
        emHelper.log("saving User instance", Level.INFO, null);
        int idToReturn = 0;
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
            idToReturn = entity.getIduser();
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
     * method uses the {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     *
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * UserEntity.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     *
     * @param entity
     *            User entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(UserEntity entity) {
        emHelper.log("deleting User instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(UserEntity.class, entity.getIduser());
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
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     *
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = UserEntity.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     *
     * @param entity
     *            User entity to update
     * @return UserEntity the persisted User entity instance, may not be the
     *         same
     * @throws RuntimeException
     *             if the operation fails
     */
    public UserEntity update(final UserEntity entity) {
        emHelper.log("updating User instance", Level.INFO, null);
        try {
            final UserEntity result = getEntityManager().merge(entity);
            emHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            emHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public UserEntity findById(final Integer id) {
        emHelper.log("finding User instance with id: " + id, Level.INFO, null);
        try {
            final UserEntity instance = getEntityManager().find(UserEntity.class, id);
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
     * @return List<UserEntity> found by query
     */
    @SuppressWarnings("unchecked")
    public List<UserEntity> findByProperty(final String propertyName, final Object value) {
        emHelper.log("finding User instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
        try {
            final String queryString = "select model from UserEntity model where model." + propertyName
                    + "= :propertyValue";
            final Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            emHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<UserEntity> findById(final Object id) {
        return findByProperty(ID, id);
    }

    public List<UserEntity> findByName(final Object name) {
        return findByProperty(NAME, name);
    }

    /**
     * Find all User entities.
     *
     * @return List<UserEntity> all User entities
     */
    @SuppressWarnings("unchecked")
    public List<UserEntity> findAll() {
        emHelper.log("finding all User instances", Level.INFO, null);
        try {
            final String queryString = "select model from UserEntity model";
            final Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            emHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}
