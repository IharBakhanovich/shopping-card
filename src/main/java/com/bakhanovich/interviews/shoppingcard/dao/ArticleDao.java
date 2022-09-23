package com.bakhanovich.interviews.shoppingcard.dao;

import com.bakhanovich.interviews.shoppingcard.exception.DuplicateException;
import com.bakhanovich.interviews.shoppingcard.model.impl.Article;

import java.util.List;
import java.util.Optional;

/**
 * The interface, that implements DAO for the {@link Article} entity.
 *
 * @author Ihar Bakhanovich
 */
public interface ArticleDao extends Dao<Article> {

    /**
     * Saves {@link Article} in the database.
     *
     * @param entity is the {@link Article} to save.
     * @throws DuplicateException if a SQLException with the state 23505 or the state 23000 is thrown.
     */
    @Override
    void save(Article entity) throws DuplicateException;

//    /**
//     * Finds all {@link Article} entity in the database.
//     *
//     * @param pageNumber              is the number of the page.
//     * @param amountEntitiesOnThePage is the value of the records,
//     *                                which should be fetched from the database and showed on the page.
//     * @return List of the {@link Article} objects.
//     */
//    List<Article> findAllPagination(int pageNumber, int amountEntitiesOnThePage);

    /**
     * Finds all {@link Article} entity in the database.
     *
     * @return List of the {@link Article} objects.
     */
    @Override
    List<Article> findAll();

    /**
     * Finds {@link Optional <Article>} in the database by the id of the {@link Article}.
     *
     * @param id is the {@link long} to find.
     * @return {@link Optional<Article>}.
     */
    @Override
    Optional<Article> findById(long id);

    /**
     * Updates the {@link Article}.
     *
     * @param entity is the value of the {@link Article} to update.
     */
    @Override
    void update(Article entity);

    /**
     * Removes the {@link Article} object from the database.
     *
     * @param id is the id of the entity to be deleted.
     */
    @Override
    void delete(long id);

//    /**
//     * Finds {@link Optional<Article>} in the database by the article name.
//     *
//     * @param name is the {@link String} to find.
//     * @return {@link Optional<Article>}.
//     */
//    Optional<Article> findByName(String name);

    /**
     * Removes records from 'user_order_article' table by articleId.
     *
     * @param articleId is the id to remove by.
     */
    void deleteFromUserOrderArticleByArticleId(long articleId);

//    /**
//     * Finds the most popular {@link Article} of the {@link com.bakhanovich.interviews.shoppingcard.model.impl.User}
//     * with the biggest sum of order price.
//     *
//     * @return {@link Optional<Article>}.
//     */
//    Optional<Article> findTheMostPopularArticleOfTheUser();
}
