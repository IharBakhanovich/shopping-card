package com.bakhanovich.interviews.shoppingcart.model.impl;


import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.model.DatabaseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Implements roles of the {@link User}.
 *
 * @author Ihar Bakhanovich
 */
public enum Role implements DatabaseEntity {
    ROLE_UNAUTHORIZED(0L),
    ROLE_ADMIN(1L),
    ROLE_USER(2L);
    public static final String ROLE_NOT_VALID = "05";
    /**
     * creates the {@link List <Role>} with all the values of the {@link Role}.
     */
    public static final List<Role> ALL_AVAILABLE_ROLES = Arrays.asList(values());
    public static final String THERE_IS_NO_ROLE_WITH_THE_ID = "There is no Role with the id = %s";
    private final Long id;

    /**
     * Constructs a new {@link Role}.
     *
     * @param id is the value of the id of the new {@link Role}
     */
    Role(Long id) {
        this.id = id;
    }

    /**
     * Returns the List of all the Role values.
     *
     * @return {@link List<Role>}.
     */
    public static List<Role> valuesAsList() {
        return ALL_AVAILABLE_ROLES;
    }

    /**
     * Returns Role by id.
     *
     * @param id is the id to search.
     * @return {@link Role} with the id equals the {@param id}.
     * @throws EntityNotFoundException if such id does not exist.
     */
    public static Role resolveRoleById(Long id) {
        for (Role role : Role.values()
        ) {
            if (role.id.equals(id)) {
                return role;
            }
        }
        throw new EntityNotFoundException(ROLE_NOT_VALID, String.format(THERE_IS_NO_ROLE_WITH_THE_ID, id));
    }

    /**
     * A Getter for the name.
     *
     * @return The name
     */
    public String getName() {
        return this.name();
    }

    /**
     * A Getter for the id.
     *
     * @return The id.
     */
    public Long getId() {
        return this.id;
    }
}
