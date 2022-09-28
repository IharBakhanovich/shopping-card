package com.bakhanovich.interviews.shoppingcart.service;

import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;

import java.util.List;

/**
 * The interface that defines the {@link ArticleService} api of the application.
 *
 * @author Ihar Bakhanovich.
 */
public interface ArticleService {

    /**
     * Returns all {@link Article}s in the system.
     */
    List<ArticleDto> fetchAllArticles();

    /**
     * Returns a {@link Article} by its id.
     *
     * @param id is the id to find in the system.
     */
    ArticleDto fetchArticleById(long id);

    /**
     * Updates a {@link Article}.
     *
     * @param article is the {@link Article} to update.
     */
    ArticleDto updateArticle(Article article);
}
