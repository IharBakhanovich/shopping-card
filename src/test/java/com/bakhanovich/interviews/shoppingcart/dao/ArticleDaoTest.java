package com.bakhanovich.interviews.shoppingcart.dao;

import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Contains {@link ArticleDao} tests.
 *
 * @author Ihar Bakhanovich.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleDaoTest extends ShoppingCartIntegrationTest {

    @Autowired
    private ArticleDao articleDao;

    /**
     * the test of the findAll() method.
     */
    @Test
    public void testFindAll() {
        List<Article> allArticles = articleDao.findAll();
        Assertions.assertEquals(5, allArticles.size());
    }

    /**
     * The test of the findById() method.
     */
    @Test
    public void testFindById() {
        Article article = articleDao.findById(1).get();
        Assertions.assertEquals(1, article.getId());
    }

    /**
     * The test of the update() method.
     */
    @Test
    public void testUpdate() {
        Article article = articleDao.findAll().get(0);
        long articleId = article.getId();
        article.setAmount(20);
        articleDao.update(article);
        Article articleAfterUpdate = articleDao.findById(article.getId()).get();
        Assertions.assertEquals(articleAfterUpdate.getAmount(), 20);
    }

    /**
     * The test of the delete() method.
     */
    @Test
    public void testDelete() {
        articleDao.delete(1);
        Optional<Article> article = articleDao.findById(1);
        Assertions.assertFalse(article.isPresent());
    }

    @Test
    public void testSave() {
        Article article = new Article(6L, BigDecimal.valueOf(1.22f), 100, 2);
        articleDao.save(article);
        Article addedArticle = articleDao.findById(6).get();
        Assertions.assertEquals(article.getAmount(), addedArticle.getAmount());
    }

}
