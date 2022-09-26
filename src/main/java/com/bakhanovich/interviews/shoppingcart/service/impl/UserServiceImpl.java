package com.bakhanovich.interviews.shoppingcart.service.impl;

import com.bakhanovich.interviews.shoppingcart.converter.UserToUserDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class that implements the {@link UserService} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    public static final String ERROR_CODE_ENTITY_NOT_FOUND = "404";

    private final UserDao userDao;
    ArticleDao articleDao;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final Translator translator;
    private final ArticleValidator articleValidator;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           ArticleDao articleDao,
                           UserToUserDtoConverter userToUserDtoConverter,
                           Translator translator,
                           ArticleValidator articleValidator) {
        this.userDao = userDao;
        this.articleDao = articleDao;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.translator = translator;
        this.articleValidator = articleValidator;
    }

    /**
     * Returns all {@link User}s in the system as {@link UserDto}s.
     */
    @Override
    public List<UserDto> fetchAllUsers() {
        return userToUserDtoConverter.convertList(userDao.findAll());
    }

    /**
     * Adds article to {@link User} and returns user with all his articles.
     *
     * @param userId   is the id of the {@link User} to whom an {@link List<Article>} should be added.
     * @param articles is all the articles, that should be added to user
     * @return {@link UserDto}, i.e. a {@link User} with all his articles and with the total costs of all his articles.
     */
    @Override
    public UserDto addArticlesInCart(long userId, List<Article> articles) {
        User userWhomAddArticles = checkIsUserExistsInTheSystem(userId);
        articleValidator.validateArticles(articles);
        List <Article> userArticles = userWhomAddArticles.getArticles();
        for (Article article : articles) {
            Article theSameArticleByUser = findArtickleByUser(userArticles, article.getId());
            Article articleFromTheSystem = articleDao.findById(article.getId()).get();
            article.setPreis(articleFromTheSystem.getPreis());
            if (theSameArticleByUser != null) {
                int articleAmountToAdd = theSameArticleByUser.getAmount() + article.getAmount();
                article.setAmount(articleAmountToAdd);
                userDao.updateArticleBookedByUser(userId, article);
            } else {
                userDao.addArticleToUserCart(userId, article);
            }
            updateArticleInTheSystem(article, articleFromTheSystem);
        }

        return userToUserDtoConverter.convert(userDao.findById(userId).get());
    }

    private void updateArticleInTheSystem(Article article, Article articleFromTheSystem) {
        int newAmount = articleFromTheSystem.getAmount()- article.getAmount();
        articleFromTheSystem.setAmount(newAmount);
        articleDao.update(articleFromTheSystem);
    }

    private Article findArtickleByUser(List<Article> userArticles, long id) {
        for (Article article : userArticles) {
            if(article.getId() == id) {
                return article;
            }
        }
        return null;
    }


    private User checkIsUserExistsInTheSystem(long userId) {
        List<String> errorMessages = new ArrayList<>();
        Optional<User> userToAddArticles = userDao.findById(userId);
        if(userToAddArticles.isEmpty()) {
            errorMessages.add(translator.toLocale("USER_NOT_FOUND_WITH_USERID") + userId);
            throw new EntityNotFoundException(ERROR_CODE_ENTITY_NOT_FOUND, errorMessages);
        }
        return userToAddArticles.get();
    }

    /**
     * Returns a {@link User} by its id.
     *
     * @param userId is the id to find in the system.
     */
    @Override
    public UserDto fetchUserById(long userId) {
        User user = userDao.findById(userId).get();
        return userToUserDtoConverter.convert(user);
    }
}
