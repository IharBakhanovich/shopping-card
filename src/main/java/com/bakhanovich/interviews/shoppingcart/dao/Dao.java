package com.bakhanovich.interviews.shoppingcart.dao;

import com.bakhanovich.interviews.shoppingcart.exception.DuplicateException;
import com.bakhanovich.interviews.shoppingcart.model.DatabaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * The main application DAO interface,
 * that defines the main operation on the DB (CRUD).
 *
 * @author Ihar Bakhanovich
 */
public interface Dao <T extends DatabaseEntity> {

    /**
     * Saves {@link T} in the database.
     *
     * @param entity is the {@link T} to save.
     * @throws DuplicateException if a SQLException with the state 23505 or the state 23000 is thrown.
     */
    void save(T entity) throws DuplicateException;

    /**
     * Finds all {@link T} entity in the database.
     *
     * @return List of the {@link T} objects.
     */
    List<T> findAll();

    /**
     * Finds {@link Optional <T>} in the database by the id of the {@link T}.
     *
     * @param id is the {@link long} to find.
     * @return {@link Optional<T>}.
     */
    Optional<T> findById(long id);

    /**
     * Updates the {@link T}.
     *
     * @param entity is the value of the {@link T} to update.
     */
    void update(T entity);

    /**
     * Deletes the {@link T} object from the database.
     *
     * @param id is the id of the entity to be deleted.
     */
    void delete(long id);
}
