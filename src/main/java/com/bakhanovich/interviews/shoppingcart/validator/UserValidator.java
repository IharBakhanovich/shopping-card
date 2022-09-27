package com.bakhanovich.interviews.shoppingcart.validator;

import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;

public interface UserValidator {
    /**
     * Validates user.
     *
     * @param user
     */
    void validateUser(User user);

    /**
     * Chesks if {@link User} has an {@link Article} with id {@param articleId} in the cart.
     *
     * @param user      is the {@link User} to check.
     * @param articleId is the {@link Article} id to check.
     * @return {@link Article} with {@param articleId}, which contains user's cart.
     */
    Article checkIsUserHasSuchAnArticleInCart(User user, long articleId);

    /**
     * Check if {@link User} with the id {@param userId} exists in the system.
     *
     * @param userId is the {@link User} id by which is to check.
     * @return {@link User} if he exist in the system.
     */
    User checkIsUserExistsInTheSystem(long userId);
}
