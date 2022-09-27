package com.bakhanovich.interviews.shoppingcart.service;

import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;

import java.util.List;

/**
 * The interface that defines the {@link UserService} api of the application.
 *
 * @author Ihar Bakhanovich.
 */
public interface UserService {

    /**
     * Returns all {@link User}s in the system as {@link UserDto}s.
     */
    List<UserDto> fetchAllUsers();

    /**
     * Adds article to {@link User} and returns user with all his articles.
     *
     * @param userId   is the id of the {@link User} to whom an {@link List<Article>} should be added.
     * @param articles is all the articles, that should be added to user
     * @return {@link UserDto}, i.e. a {@link User} with all his articles and with the total costs of all his articles.
     */
    UserDto addArticlesInCart(long userId, List<Article> articles);

    /**
     * Returns a {@link User} by its id.
     *
     * @param userId is the id to find in the system.
     */
    UserDto fetchUserById(long userId);

    /**
     * Removes {@link Article} with the articleId from the cart of the {@link User} with userId.
     *
     * @param userId is the {@link User}, which {@link Article} with articleId is to remove.
     * @param articleId is the {@link Article} to be removed.
     */
    void deleteUserArticle(long userId, long articleId);

    /**
     * Removes all {@link Article}s from the cart of the {@link User} with userId.
     *
     * @param userId is the {@link User}, which {@link Article}s are to remove.
     */
    void deleteAllUserArticles(long userId);
}
