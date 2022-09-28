package com.bakhanovich.interviews.shoppingcart.service;

import com.bakhanovich.interviews.shoppingcart.converter.ArticleToArticleDtoConverter;
import com.bakhanovich.interviews.shoppingcart.converter.UserToUserDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Role;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.impl.UserServiceImpl;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
import com.bakhanovich.interviews.shoppingcart.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

/**
 * Contains {@link UserService} tests.
 *
 * @author Ihar Bakhanovich.
 */
@ExtendWith({MockitoExtension.class})
public class UserServiceTest {
    @Mock
    ArticleDao articleDao;
    @Mock
    UserDao userDao;
    @Mock
    ArticleValidator articleValidator;
    @Mock
    UserValidator userValidator;
    @Mock
    Translator translator;
    @Mock
    ArticleToArticleDtoConverter articleToArticleDtoConverter;
    @Mock
    UserToUserDtoConverter userToUserDtoConverter;

    @Spy
    @InjectMocks
    UserServiceImpl userService;

    /**
     * The test of the findAll() method.
     */
    @Test
    public void findAllTest() {
        List<User> users = new ArrayList<>();
        final User user1 = new User(1L, "User1", "***", Role.ROLE_ADMIN, new ArrayList<>());
        final User user2 = new User(2L, "User2", "***", Role.ROLE_ADMIN, new ArrayList<>());
        users.add(user1);
        users.add(user2);
        List<UserDto> userDtos = new ArrayList<>();
        final UserDto userDto1 = new UserDto(1L, "User1", BigDecimal.valueOf(2.99), Role.ROLE_ADMIN, new ArrayList<>());
        final UserDto userDto2 = new UserDto(2L, "User2", BigDecimal.valueOf(4.99), Role.ROLE_ADMIN, new ArrayList<>());
        userDtos.add(userDto1);
        userDtos.add(userDto2);
        given(userDao.findAll()).willReturn(users);
        given(userToUserDtoConverter.convertList(users)).willReturn(userDtos);
        List<UserDto> expectedUserDtos = userService.fetchAllUsers();
        Assertions.assertEquals(userDtos, expectedUserDtos);
    }

    /**
     * The test of the findUserById() method.
     */
    @Test
    public void findByIdTest() {
        final User user1 = new User(1L, "User1", "***", Role.ROLE_ADMIN, new ArrayList<>());
        final UserDto userDto1 = new UserDto(1L, "User1", BigDecimal.valueOf(2.99), Role.ROLE_ADMIN, new ArrayList<>());
        given(userToUserDtoConverter.convert(user1)).willReturn(userDto1);
        given(userValidator.checkIsUserExistsInTheSystem(user1.getId())).willReturn(user1);
        given(userDao.findById(user1.getId())).willReturn(Optional.of(user1));
        Optional<UserDto> expectedUser1Dto = Optional.ofNullable(userService.fetchUserById(user1.getId()));
        Assertions.assertEquals(Optional.of(userDto1), expectedUser1Dto);
    }
}
