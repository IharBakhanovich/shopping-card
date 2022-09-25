package com.bakhanovich.interviews.shoppingcart.service;

import com.bakhanovich.interviews.shoppingcart.model.impl.Article;

import java.util.List;
import java.util.Map;

/**
 * The interface that defines the {@link ArticleService} api of the application.
 *
 * @author Ihar Bakhanovich.
 */
public interface ArticleService {

    /**
     * Returns all {@link Article}s in the system.
     */
    List<Article> fetchAllArticles();

    /**
     * Returns a {@link Article} by its id.
     *
     * @param id is the id to find in the system.
     */
    Article fetchArticleById(long id);

    /**
     * Updates a {@link Article}.
     *
     * @param article is the {@link Article} to update.
     */
    Article updateArticle(Article article);
}
