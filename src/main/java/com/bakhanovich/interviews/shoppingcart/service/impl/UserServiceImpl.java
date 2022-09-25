package com.bakhanovich.interviews.shoppingcart.service.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The class that implements the {@link UserService} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Adds article to {@link User} and returns user with all his articles.
     *
     * @param userId is the id of the {@link User} to whom an {@link List<Article>} should be added.
     * @param articles is all the articles, that should be added to user
     * @return {@link UserDto}, i.e. a {@link User} with all his articles and with the total costs of all his articles.
     */
    @Override
    public UserDto orderArticle(long userId, List<Article> articles) {
        return null;
    }

    /**
     * Returns a {@link User} by its id.
     *
     * @param userId is the id to find in the system.
     */
    @Override
    public UserDto fetchUserById(long userId) {
        return null;
    }
}
