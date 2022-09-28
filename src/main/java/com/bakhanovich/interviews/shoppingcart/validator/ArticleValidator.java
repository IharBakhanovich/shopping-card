package com.bakhanovich.interviews.shoppingcart.validator;

import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
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

    /**
     * Checks if the article exist in the system.
     *
     * @param articleId is the id by which the {@link Article} is to find.
     * @return {@link Article} with the id {@param articleId} if it exist in the system.
     * @throws EntityNotFoundException if there is no {@link Article with such an id.}
     */
    Article checkIsArticleExistInTheSystem(long articleId);
}
