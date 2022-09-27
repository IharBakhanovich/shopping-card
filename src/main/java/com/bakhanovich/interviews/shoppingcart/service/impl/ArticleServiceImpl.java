package com.bakhanovich.interviews.shoppingcart.service.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.ArticleService;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class that implements the {@link ArticleService} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    public static final String ERROR_CODE_ENTITY_NOT_FOUND = "404";

    private final ArticleDao articleDao;
    private final ArticleValidator articleValidator;
    private final Translator translator;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao,
                              ArticleValidator articleValidator,
                              Translator translator) {
        this.articleDao = articleDao;
        this.articleValidator = articleValidator;
        this.translator = translator;
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
        return articleValidator.checkIsArticleExistInTheSystem(id);
    }

    /**
     * Updates a {@link Article}.
     *
     * @param article is the {@link Article} to update.
     */
    @Override
    public Article updateArticle(Article article) {
        articleValidator.validateArticle(article);
        articleDao.update(article);
        return articleDao.findById(article.getId()).get();
    }
}
