package com.bakhanovich.interviews.shoppingcart.controller;

import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * API to work with {@link User}s of the ShoppingCart.
 *
 * @author Ihar Bakhanovich
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructs the {@link UserController}.
     *
     * @param userService is the service to inject.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * The method that realises the 'POST /users/{userId{}/articles}' query.
     *
     * @param article is the {@link Article} to order.
     * @return the created {@link User}.
     */
    @PostMapping(value = "/{userId}/articles")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addNewUser(@RequestBody List<Article> article, @PathVariable("userId") long userId) {
        return userService.orderArticle(userId, article);
    }

    /**
     * The method that realises the 'GET /users/{userId}' query i.e. returns user by id.
     *
     * @param userId is the ID of the {@link User} to find.
     * @return {@link UserDto} with the {@param id} if such an id exists in the application.
     */
    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> getUserById(@PathVariable("userId") long userId) {
        UserDto userDto = userService.fetchUserById(userId);
        EntityModel<UserDto> userEntityModel = EntityModel.of(userDto);
//        userEntityModel.add(linkTo(methodOn(UserController.class).fetchAllUsers(new HashMap<>()))
//                .withRel(translator.toLocale("FETCHES_ALL_USERS_HATEOAS_LINK_MESSAGE")));
//        userEntityModel.add(linkTo(methodOn(UserController.class).getUserById(userId)).withSelfRel());
        return userEntityModel;
    }
}

