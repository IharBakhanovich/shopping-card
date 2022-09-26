package com.bakhanovich.interviews.shoppingcart.validator;

import com.bakhanovich.interviews.shoppingcart.model.impl.User;

public interface UserValidator {
    /**
     * Validates user.
     *
     * @param user
     */
    void validateUser(User user);
}
