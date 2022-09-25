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
     * Adds article to {@link User} and returns user with all his articles.
     *
     * @param userId is the id of the {@link User} to whom an {@link List<Article>} should be added.
     * @param articles is all the articles, that should be added to user
     * @return {@link UserDto}, i.e. a {@link User} with all his articles and with the total costs of all his articles.
     */
    UserDto orderArticle(long userId, List<Article> articles);

    /**
     * Returns a {@link User} by its id.
     *
     * @param userId is the id to find in the system.
     */
    UserDto fetchUserById(long userId);
}
