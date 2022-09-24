package com.bakhanovich.interviews.shoppingcart.dao;


import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface, that implements DAO for the {@link User} entity.
 *
 * @author Ihar Bakhanovich
 */
public interface UserDao extends Dao<User>{
    /**
     * Saves {@link User} in the database.
     *
     * @param user is the {@link User} to save.
     * @throws DuplicateException if a SQLException with the state 23505 or the state 23000 is thrown.
     */
    @Override
    void save(User user);

    /**
     * Finds all {@link User} entity in the database.
     *
     * @return List of the {@link User} objects.
     */
    @Override
    List<User> findAll();

    /**
     * Finds {@link Optional <User>} in the database by the id of the {@link User}.
     *
     * @param id is the {@link long} to find.
     * @return {@link Optional<User>}.
     */
    @Override
    Optional<User> findById(long id);

    /**
     * Updates the {@link User}.
     *
     * @param user is the value of the {@link User} to update.
     */
    @Override
    void update(User user);

    /**
     * adds new {@link Article} to user's cart.
     *
     * @param userId is the id of the user, whom new {@link Article} is to be added.
     * @param article is the {@link Article} to be added.
     */
    void addArticleToUserCart(Long userId, Article article);

    /**
     * Removes {@link Article} from user's cart.
     *
     * @param userId is the id of the user, from whom an {@link Article} with {@param articleId} to be removed.
     * @param articleId is the {@link Article} to be removed.
     */
    void deleteArticleFromUserCart(Long userId, Long articleId);

    /**
     * Updates {@link Article} in user's cart.
     * @param userId is the user's id, whom is to update the {@link Article} in his cart.
     * @param article is the article, which should be in the cart as the result.
     */
    void updateArticleBookedByUser(Long userId, Article article);

    /**
     * Deletes the {@link User} object from the database.
     *
     * @param id is the value of the {@link long} to find.
     */
    @Override
    void delete(long id);

    /**
     * Finds {@link Optional<User>} in the database by the id of the {@link User}.
     *
     * @param nickName is the {@link long} to find.
     * @return {@link Optional<User>}.
     */
    Optional<User> findByName(String nickName);

//    /**
//     * Finds all {@link User} entity in the database.
//     *
//     * @param pageNumber              is the pageNumber query parameter.
//     * @param amountEntitiesOnThePage is the amountEntitiesOnThePage query parameter.
//     * @return List of the {@link User} objects.
//     */
//    List<User> findAllPagination(int pageNumber, int amountEntitiesOnThePage);
}
