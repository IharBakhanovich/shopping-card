package com.bakhanovich.interviews.shoppingcart.validator.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.dao.impl.ColumnNames;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class that implements the {@link UserValidator} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Component
public class UserValidatorImpl implements UserValidator {
    private final Translator translator;
    private final ArticleDao articleDao;
    private final UserDao userDao;

    @Autowired
    public UserValidatorImpl(Translator translator,
                             ArticleDao articleDao,
                             UserDao userDao) {
        this.translator = translator;
        this.articleDao = articleDao;
        this.userDao = userDao;
    }

    /**
     * Validates user.
     *
     * @param user
     */
    @Override
    public void validateUser(User user) {

    }

    /**
     * Chesks if {@link User} has an {@link Article} with id {@param articleId} in the cart.
     *
     * @param user      is the {@link User} to check.
     * @param articleId is the {@link Article} id to check.
     */
    @Override
    public void checkIsUserHasSuchAnArticleInCart(User user, long articleId) {
        List<String> errorMessages = new ArrayList<>();
        boolean hasSuchAnArticle = false;
        for (Article article: user.getArticles()) {
            if (article.getId() == articleId) {
                hasSuchAnArticle = true;
                break;
            }
        }
        if (!hasSuchAnArticle) {
            errorMessages.add(translator.toLocale("USER_HAS_NOT_SUCH_AN_ARTICLE")
                    + " user id: " + user.getId() + " article id: " + articleId);
            throw new EntityNotFoundException(ColumnNames.ERROR_CODE_ENTITY_NOT_FOUND, errorMessages);
        }
    }

    /**
     * Check if {@link User} with the id {@param userId} exists in the system.
     *
     * @param userId is the {@link User} id by which is to check.
     * @return {@link User} if he exist in the system.
     */
    @Override
    public User checkIsUserExistsInTheSystem(long userId) {
        List<String> errorMessages = new ArrayList<>();
        Optional<User> userToAddArticles = userDao.findById(userId);
        if (userToAddArticles.isEmpty()) {
            errorMessages.add(translator.toLocale("USER_NOT_FOUND_WITH_USERID") + " userId: "+ userId);
            throw new EntityNotFoundException(ColumnNames.ERROR_CODE_ENTITY_NOT_FOUND, errorMessages);
        }
        return userToAddArticles.get();
    }
}
