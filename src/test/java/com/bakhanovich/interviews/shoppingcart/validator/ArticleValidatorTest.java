package com.bakhanovich.interviews.shoppingcart.validator;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.exception.MethodArgumentNotValidException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.impl.ArticleValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ArticleValidatorTest {
    @Mock
    ArticleDao articleDao;
    @Mock
    Translator translator;

    @Spy
    @InjectMocks
    ArticleValidatorImpl articleValidator;

    /**
     * The test of the validateArticle() method.
     */
    @Test
    public void shouldThrowExceptionByValidateArticleCheckEmptyFieldsTest() {
        final Article article = new Article(1L, null, 1, 2);
        given(translator.toLocale(any())).willReturn("test");
        Assertions.assertThrows(MethodArgumentNotValidException.class,
                () -> articleValidator.validateArticle(article));
    }

    /**
     * The test of the validateArticle() method.
     */
    @Test
    public void shouldThrowExceptionByValidateArticleCheckValueFieldIsMoreThanZeroTest() {
        final Article article = new Article(1L, BigDecimal.valueOf(1.99), 0, 2);
        given(translator.toLocale(any())).willReturn("test");
        Assertions.assertThrows(MethodArgumentNotValidException.class,
                () -> articleValidator.validateArticle(article));
    }

    /**
     * The test of the validateArticle() method.
     */
    @Test
    public void shouldThrowExceptionByValidateArticleCheckMinAmountFieldIsMoreThanZeroTest() {
        final Article article = new Article(1L, BigDecimal.valueOf(1.99), 1, 0);
        given(translator.toLocale(any())).willReturn("test");
        Assertions.assertThrows(MethodArgumentNotValidException.class,
                () -> articleValidator.validateArticle(article));
    }

    /**
     * The test of the validateArticle() method.
     */
    @Test
    public void shouldDoNotThrowExceptionByValidateArticleTest() {
        final Article article = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        Assertions.assertDoesNotThrow(() -> articleValidator.validateArticle(article));
    }

    /**
     * The test of the checkIsArticleExistInTheSystem() method.
     */
    @Test
    public void shouldThrowExceptionByCheckIsArticleExistsInTheSystemTest() {
        int articleId = 1;
        given(translator.toLocale(any())).willReturn("test");
        given(articleDao.findById(articleId)).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> articleValidator.checkIsArticleExistInTheSystem(articleId));
    }

    /**
     * The test of the checkIsArticleExistInTheSystem() method.
     */
    @Test
    public void checkIsArticleExistsInTheSystemTest() {
        final Article article = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        given(articleDao.findById(article.getId())).willReturn(Optional.of(article));
        Optional<Article> expectedArticle
                = Optional.ofNullable(articleValidator.checkIsArticleExistInTheSystem(article.getId()));
        Assertions.assertEquals(expectedArticle.get(), article);
    }

    /**
     * The test of the validateArticles() method.
     */
    @Test
    public void shouldThrowExceptionByCheckValidateArticlesIfArticleIdIsZeroTest() {
        final Article article1 = new Article(0L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article3 = new Article(1L, BigDecimal.valueOf(1.99), 100, 2);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        given(translator.toLocale(any())).willReturn("test");
        given(articleDao.findById(article1.getId())).willReturn(Optional.of(article3));
        given(articleDao.findById(article2.getId())).willReturn(Optional.of(article3));
        Assertions.assertThrows(MethodArgumentNotValidException.class,
                () -> articleValidator.validateArticles(articles));
    }

    /**
     * The test of the validateArticles() method.
     */
    @Test
    public void shouldThrowExceptionByCheckValidateArticlesCheckAmountFieldNotEmptyTest() {
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 0, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article3 = new Article(1L, BigDecimal.valueOf(1.99), 100, 2);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        given(translator.toLocale(any())).willReturn("test");
        given(articleDao.findById(article1.getId())).willReturn(Optional.of(article3));
        given(articleDao.findById(article2.getId())).willReturn(Optional.of(article3));
        Assertions.assertThrows(MethodArgumentNotValidException.class,
                () -> articleValidator.validateArticles(articles));
    }

    /**
     * The test of the validateArticles() method.
     */
    @Test
    public void shouldThrowExceptionByCheckValidateArticlesCheckAmountFieldMoreThanZeroTest() {
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), -1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article3 = new Article(1L, BigDecimal.valueOf(1.99), 100, 2);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        given(translator.toLocale(any())).willReturn("test");
        given(articleDao.findById(article1.getId())).willReturn(Optional.of(article3));
        given(articleDao.findById(article2.getId())).willReturn(Optional.of(article3));
        Assertions.assertThrows(MethodArgumentNotValidException.class,
                () -> articleValidator.validateArticles(articles));
    }

    /**
     * The test of the validateArticles() method.
     */
    @Test
    public void checkValidateArticlesTest() {
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article3 = new Article(1L, BigDecimal.valueOf(1.99), 100, 2);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        given(articleDao.findById(article1.getId())).willReturn(Optional.of(article3));
        given(articleDao.findById(article2.getId())).willReturn(Optional.of(article3));
        Assertions.assertDoesNotThrow(() -> articleValidator.validateArticles(articles));
    }
}
