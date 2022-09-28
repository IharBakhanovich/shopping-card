package com.bakhanovich.interviews.shoppingcart.validator;

import com.bakhanovich.interviews.shoppingcart.converter.ArticleToArticleDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.Role;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.impl.UserValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Contains {@link UserService} tests.
 *
 * @author Ihar Bakhanovich.
 */
@ExtendWith({MockitoExtension.class})
public class UserValidatorTest {
    @Mock
    UserDao userDao;
    @Mock
    Translator translator;
    @Mock
    ArticleToArticleDtoConverter articleToArticleDtoConverter;

    @Spy
    @InjectMocks
    UserValidatorImpl userValidator;

    /**
     * The test of the checkIsUserExistsInTheSystem() method.
     */
    @Test
    public void shouldThrowExceptionByCheckIsUserExistsInTheSystemTest() {
        int userId = 1;
        given(translator.toLocale(any())).willReturn("test");
        given(userDao.findById(userId)).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userValidator.checkIsUserExistsInTheSystem(userId));
    }

    /**
     * The test of the checkIsUserExistsInTheSystem() method.
     */
    @Test
    public void checkIsUserExistsInTheSystemTest() {
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(0.99), 2, 4);
        List<Article> articleList = List.of(article1, article2);
        User user = new User(6L, "User6", "***", Role.ROLE_USER, articleList);
        given(userDao.findById(user.getId())).willReturn(Optional.of(user));
        Optional<User> expectedUser
                = Optional.ofNullable(userValidator.checkIsUserExistsInTheSystem(user.getId()));
        Assertions.assertEquals(expectedUser.get(), user);
    }

    /**
     * The test of the checkIsUserHasSuchAnArticleInCart() method.
     */
    @Test
    public void shouldThrowExceptionBycheckIsUserHasSuchAnArticleInCartTest() {
        int userId = 1;
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(0.99), 2, 4);
        List<Article> articleList = List.of(article1);
        User user = new User(6L, "User6", "***", Role.ROLE_USER, articleList);
        given(translator.toLocale(any())).willReturn("test");
//        given(userDao.findById(userId)).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userValidator.checkIsUserHasSuchAnArticleInCart(user, article2.getId()));
    }

    /**
     * The test of the checkIsUserHasSuchAnArticleInCart() method.
     */
    @Test
    public void checkIsUserHasSuchAnArticleInCartTest() {
        int userId = 1;
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(0.99), 2, 4);
        List<Article> articleList = List.of(article1, article2);
        User user = new User(6L, "User6", "***", Role.ROLE_USER, articleList);
        Article expectedArticle
                = userValidator.checkIsUserHasSuchAnArticleInCart(user, article2.getId());
        Assertions.assertEquals(expectedArticle, article2);
    }
}
