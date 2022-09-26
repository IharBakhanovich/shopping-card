package com.bakhanovich.interviews.shoppingcart.converter;

import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Converts {@link User} to {@link UserDto}
 *
 * @author Ihar Bakhanovich.
 */
public interface UserToUserDtoConverter extends Converter<User, UserDto> {

    /**
     * Converts {@link User} to {@link UserDto}.
     *
     * @param user is the {@link User} to convert.
     * @return {@link UserDto} that contains all the {@link User} data.
     */
    @Override
    UserDto convert(User user);

    /**
     * Converts {@link List<User>} to {@link List<UserDto>}.
     *
     * @param users is the {@link User} collection to convert.
     * @return {@link List<UserDto>} that contains all converted {@link User}s.
     */
    List<UserDto> convertList(List<User> users);
}
