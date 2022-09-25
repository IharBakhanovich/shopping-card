package com.bakhanovich.interviews.shoppingcart.service.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The class that implements the {@link ArticleService} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * Returns all {@link Article}s in the system.
     */
    @Override
    public List<Article> fetchAllArticles() {
        return articleDao.findAll();
    }

    /**
     * Returns a {@link Article} by its id.
     *
     * @param id is the id to find in the system.
     */
    @Override
    public Article fetchArticleById(long id) {
        //todo validation
        return articleDao.findById(id).get();
    }

    /**
     * Updates a {@link Article}.
     *
     * @param article is the {@link Article} to update.
     */
    @Override
    public Article updateArticle(Article article) {
        //todo validation
        articleDao.update(article);
        return articleDao.findById(article.getId()).get();
    }
}
