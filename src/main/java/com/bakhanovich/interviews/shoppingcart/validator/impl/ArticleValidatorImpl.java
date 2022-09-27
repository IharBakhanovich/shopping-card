package com.bakhanovich.interviews.shoppingcart.validator.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.impl.ColumnNames;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.exception.MethodArgumentNotValidException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of the {@link ArticleValidator}.
 *
 * @author Ihar Bakhanovich.
 */
@Component
public class ArticleValidatorImpl implements ArticleValidator {

    public static final String ERROR_CODE_METHOD_ARGUMENT_NOT_VALID = "400";
    public static final String ERROR_CODE_ARTICLE_NOT_VALID = "02";

    private final Translator translator;
    private final ArticleDao articleDao;

    @Autowired
    public ArticleValidatorImpl(Translator translator,
                                ArticleDao articleDao) {
        this.translator = translator;
        this.articleDao = articleDao;
    }

    /**
     * Validates {@link Article}'s property by adding new {@link Article} in the application.
     *
     * @param article is the {@link Article} to validate.
     */
    @Override
    public void validateArticle(Article article) {
        List<String> errorMessages = new ArrayList<>();

        checkEmptyFields(article, errorMessages);

        checkFieldIsMoreThanZero(article.getId(), errorMessages,
                article.getPreis().floatValue(), "ARTICLE_PRICE_SHOULD_BE_MORE_THAN_ZERO");

        checkFieldIsMoreThanZero(article.getId(), errorMessages,
                article.getAmount(), "ARTICLE_AMOUNT_SHOULD_BE_MORE_THAN_ZERO");

        checkFieldIsMoreThanZero(article.getId(), errorMessages,
                article.getMinAmount(), "ARTICLE_MIN_AMOUNT_SHOULD_BE_MORE_THAN_ZERO");

        if (!errorMessages.isEmpty()) {
            throw new MethodArgumentNotValidException(
                    ERROR_CODE_METHOD_ARGUMENT_NOT_VALID + ERROR_CODE_ARTICLE_NOT_VALID, errorMessages);
        }
    }

    private void checkFieldIsMoreThanZero(long articleId, List<String> errorMessages, float value, String errorMessage) {
        if (value < 0 || value == 0) {
            errorMessages.add("articleId " + articleId + ": " + translator.toLocale(errorMessage));
        }
    }

    private void checkEmptyFields(Article article, List<String> errorMessages) {
        if (article.getPreis() == null) {
            errorMessages.add("articleId " + article.getId() + ": "
                    + translator.toLocale("THE_PRICE_FIELD_SHOULD_NOT_BE_EMPTY"));
        }
        if (article.getMinAmount() == 0) {
            errorMessages.add("articleId " + article.getId() + ": "
                    + translator.toLocale("THE_MIN_AMOUNT_FIELD_SHOULD_NOT_BE_EMPTY"));
        }
        checkAmountFieldIsNotEmpty(article, errorMessages);
    }

    private void checkAmountFieldIsNotEmpty(Article article, List<String> errorMessages) {
        if (article.getAmount() == 0) {
            errorMessages.add("articleId " + article.getId() + ": "
                    + translator.toLocale("THE_AMOUNT_FIELD_SHOULD_NOT_BE_EMPTY"));
        }
    }

    /**
     * Validates {@link Article}s when they are added in the cart.
     *
     * @param articles is the {@link List<Article>} to validate.
     */
    @Override
    public void validateArticles(List<Article> articles) {
        List<String> errorMessages = new ArrayList<>();
        //todo: if articles is null?
        for (Article article : articles) {
            checkIdFieldIsMoreThanZero(article, errorMessages);
            checkAmountFieldIsNotEmpty(article, errorMessages);
            checkFieldIsMoreThanZero(article.getId(), errorMessages,
                    article.getAmount(), "ARTICLE_AMOUNT_SHOULD_BE_MORE_THAN_ZERO");
            checkArticleExistInTheSystem(article, errorMessages);
            checkArticleIsOutOfStock(article, errorMessages);
        }
        if (!errorMessages.isEmpty()) {
            throw new MethodArgumentNotValidException(
                    ERROR_CODE_METHOD_ARGUMENT_NOT_VALID + ERROR_CODE_ARTICLE_NOT_VALID, errorMessages);
        }
    }

    /**
     * Checks if the article exist in the system.
     *
     * @param articleId is the id by which the {@link Article} is to find.
     * @return {@link Article} with the id {@param articleId} if it exist in the system.
     * @throws EntityNotFoundException if there is no {@link Article with such an id.}
     */
    @Override
    public Article checkIsArticleExistInTheSystem(long articleId) {
        List<String> errorMessages = new ArrayList<>();
        Optional<Article> articleToReturn = articleDao.findById(articleId);
        if (articleToReturn.isEmpty()) {
            errorMessages.add(translator.toLocale("ARTICLE_NOT_FOUND_WITH_ARTICLEID") + ": " + articleId);
            throw new EntityNotFoundException(ColumnNames.ERROR_CODE_ENTITY_NOT_FOUND, errorMessages);
        }
        return articleToReturn.get();
    }

    private void checkArticleIsOutOfStock(Article article, List<String> errorMessages) {
        Optional<Article> articleInSystem = articleDao.findById(article.getId());
        if (articleInSystem.isPresent()) {
            // check two cases: amount to book should be less than the existing amount in stock
            // and the existing amount should be not less than minAmount to book.
            if (article.getAmount() > articleInSystem.get().getAmount()
                    || articleInSystem.get().getMinAmount() > articleInSystem.get().getAmount()) {
                errorMessages.add("articleId " + article.getId() + ": "
                        + translator.toLocale("ARTICLE_IS_OUT_OF_STOCK"));
            }
        }
    }

    private void checkArticleExistInTheSystem(Article article, List<String> errorMessages) {
        if (articleDao.findById(article.getId()).isEmpty()) {
            errorMessages.add("articleId " + article.getId() + ": "
                    + translator.toLocale("ARTICLE_DOES_NOT_EXIST_IN_SYSTEM"));
        }
    }

    private void checkIdFieldIsMoreThanZero(Article article, List<String> errorMessages) {
        if (article.getId() < 0) {
            errorMessages.add("articleId " + article.getId() + ": "
                    + translator.toLocale("THE_AMOUNT_FIELD_SHOULD_NOT_BE_EMPTY"));
        }
    }

//    /**
//     * Validates articles stock.
//     *
//     * @param articles is the {@link List <Article>} to check for out of the stock.
//     * @throws ArticleValidationException if the amount of ordered articles more than the amount in the stock.
//     */
//    @Override
//    public void validateArticlesStock(List<Article> articles) {
//
//    }
}
