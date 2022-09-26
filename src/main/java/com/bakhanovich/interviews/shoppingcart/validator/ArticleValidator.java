package com.bakhanovich.interviews.shoppingcart.validator;

import com.bakhanovich.interviews.shoppingcart.model.impl.Article;

import java.util.List;

/**
 * Validator for the {@link Article} entity.
 */
public interface ArticleValidator {

    /**
     * Validates {@link Article}'s property by adding new {@link Article} in the application.
     *
     * @param article is the {@link Article} to validate.
     */
    void validateArticle(Article article);

    /**
     * Validates {@link Article}s when they are added in the cart.
     *
     * @param articles is the {@link List<Article>} to validate.
     */
    void validateArticles(List<Article> articles);

//    /**
//     * Validates articles stock.
//     * @param articles is the {@link List<Article>} to check for out of the stock.
//     * @throws ArticleValidationException if the amount of ordered articles more than the amount in the stock.
//     */
//    void validateArticlesStock(List<Article> articles);
}
