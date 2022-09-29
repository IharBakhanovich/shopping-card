package com.bakhanovich.interviews.shoppingcart.service.impl;

import com.bakhanovich.interviews.shoppingcart.converter.UserToUserDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
import com.bakhanovich.interviews.shoppingcart.validator.UserValidator;
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
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final ArticleValidator articleValidator;
    private final UserValidator userValidator;
    private final ArticleDao articleDao;


    @Autowired
    public UserServiceImpl(UserDao userDao,
                           ArticleDao articleDao,
                           UserToUserDtoConverter userToUserDtoConverter,
                           ArticleValidator articleValidator,
                           UserValidator userValidator) {
        this.userDao = userDao;
        this.articleDao = articleDao;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.articleValidator = articleValidator;
        this.userValidator = userValidator;
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
     * @param userId        is the id of the {@link User} to whom an {@link List<Article>} should be added.
     * @param articlesToAdd is all the articles, that should be added to user
     * @return {@link UserDto}, i.e. a {@link User} with all his articles and with the total costs of all his articles.
     */
    @Override
    public UserDto addArticlesInCart(long userId, List<Article> articlesToAdd) {
        List<Article> userArticles = checkUserAndArticlesAndGetUserArticles(userId, articlesToAdd);
        addArticlesToUser(userId, articlesToAdd, userArticles);
        return userToUserDtoConverter.convert(userDao.findById(userId).get());
    }

    private List<Article> checkUserAndArticlesAndGetUserArticles(long userId, List<Article> articlesToAdd) {
        User userWhomAddArticles = userValidator.checkIsUserExistsInTheSystem(userId);
        articleValidator.validateArticles(articlesToAdd);
        List<Article> userArticles = userWhomAddArticles.getArticles();
        return userArticles;
    }

    private void addArticlesToUser(long userId, List<Article> articlesToAdd, List<Article> userArticles) {
        for (Article article : articlesToAdd) {
            Article theSameArticleByUser = findArticleByUser(userArticles, article.getId());
            Article articleFromTheSystem = articleDao.findById(article.getId()).get();
            article.setPreis(articleFromTheSystem.getPreis());
            if (article.getAmount() < articleFromTheSystem.getMinAmount()) {
                article.setAmount(articleFromTheSystem.getMinAmount());
            }
            int initialArticleAmountToAdd = article.getAmount();
            if (theSameArticleByUser != null) {
                updateExistingUserArticle(userId, article, theSameArticleByUser);
            } else {
                userDao.addArticleToUserCart(userId, article);
            }
            updateArticleInTheSystem(initialArticleAmountToAdd, articleFromTheSystem);
        }
    }

    private void updateExistingUserArticle(long userId, Article article, Article theSameArticleByUser) {
        int articleAmountToAdd = theSameArticleByUser.getAmount() + article.getAmount();
        article.setAmount(articleAmountToAdd);
        userDao.updateArticleBookedByUser(userId, article);
    }

    private void updateArticleInTheSystem(int initialAmountToAdd, Article articleFromTheSystem) {
        int newAmount = articleFromTheSystem.getAmount() - initialAmountToAdd;
        articleFromTheSystem.setAmount(newAmount);
        articleDao.update(articleFromTheSystem);
    }

    private Article findArticleByUser(List<Article> userArticles, long id) {
        for (Article article : userArticles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    /**
     * Returns a {@link User} by its id.
     *
     * @param userId is the id to find in the system.
     */
    @Override
    public UserDto fetchUserById(long userId) {
        userValidator.checkIsUserExistsInTheSystem(userId);
        User user = userDao.findById(userId).get();
        return userToUserDtoConverter.convert(user);
    }

    /**
     * Removes {@link Article} with the articleId from the cart of the {@link User} with userId.
     *
     * @param userId    is the {@link User}, which {@link Article} with articleId is to remove.
     * @param articleId is the {@link Article} to be removed.
     */
    @Override
    public void deleteUserArticle(long userId, long articleId) {
        User user = userValidator.checkIsUserExistsInTheSystem(userId);
        Article article = articleValidator.checkIsArticleExistInTheSystem(articleId);
        Article userArticle = userValidator.checkIsUserHasSuchAnArticleInCart(user, articleId);

        userDao.deleteArticleFromUserCart(userId, articleId);
        returnArticleToStock(userArticle);
    }

    /**
     * Removes all {@link Article}s from the cart of the {@link User} with userId.
     *
     * @param userId is the {@link User}, which {@link Article}s are to remove.
     */
    @Override
    public void deleteAllUserArticles(long userId) {
        User user = userValidator.checkIsUserExistsInTheSystem(userId);
        for (Article article : user.getArticles()) {
            userDao.deleteArticleFromUserCart(userId, article.getId());
            returnArticleToStock(article);
        }
    }

    private void returnArticleToStock(Article article) {
        Article articleInSystem = articleDao.findById(article.getId()).get();
        articleInSystem.setAmount(articleInSystem.getAmount() + article.getAmount());
        articleDao.update(articleInSystem);
    }
}
