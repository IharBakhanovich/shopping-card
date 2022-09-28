package com.bakhanovich.interviews.shoppingcart.service.impl;

import com.bakhanovich.interviews.shoppingcart.converter.ArticleToArticleDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.service.ArticleService;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
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
    public static final String ERROR_CODE_ENTITY_NOT_FOUND = "404";

    private final ArticleDao articleDao;
    private final ArticleValidator articleValidator;
    private final Translator translator;
    private final ArticleToArticleDtoConverter articleToArticleDtoConverter;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao,
                              ArticleValidator articleValidator,
                              Translator translator,
                              ArticleToArticleDtoConverter articleToArticleDtoConverter) {
        this.articleDao = articleDao;
        this.articleValidator = articleValidator;
        this.translator = translator;
        this.articleToArticleDtoConverter = articleToArticleDtoConverter;
    }

    /**
     * Returns all {@link ArticleDto}s in the system.
     */
    @Override
    public List<ArticleDto> fetchAllArticles() {
        return articleToArticleDtoConverter.convertList(articleDao.findAll());
    }

    /**
     * Returns a {@link Article} by its id.
     *
     * @param id is the id to find in the system.
     */
    @Override
    public ArticleDto fetchArticleById(long id) {
        return articleToArticleDtoConverter.convert(articleValidator.checkIsArticleExistInTheSystem(id));
    }

    /**
     * Updates a {@link Article}.
     *
     * @param article is the {@link Article} to update.
     */
    @Override
    public ArticleDto updateArticle(Article article) {
        articleValidator.validateArticle(article);
        articleDao.update(article);
        return articleToArticleDtoConverter.convert(articleDao.findById(article.getId()).get());
    }
}
