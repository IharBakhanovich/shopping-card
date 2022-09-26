package com.bakhanovich.interviews.shoppingcart.controller;

import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.service.UserService;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final Translator translator;

    /**
     * Constructs the {@link UserController}.
     *
     * @param userService is the service to inject.
     */
    @Autowired
    public UserController(UserService userService, Translator translator) {
        this.userService = userService;
        this.translator = translator;
    }

    /**
     * The method that realises the 'POST /users/{userId{}/articles}' query.
     *
     * @param articles is the {@link Article} to order.
     * @return the created {@link User}.
     */
    @PostMapping(value = "/{userId}/articles")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> addArticlesInCart(@RequestBody List<Article> articles,
                                                  @PathVariable("userId") long userId) {
        UserDto userDto = userService.addArticlesInCart(userId, articles);
        EntityModel<UserDto> userEntityModel = EntityModel.of(userDto);
        userEntityModel.add(linkTo(methodOn(UserController.class).users())
                .withRel(translator.toLocale("FETCHES_ALL_USERS_HATEOAS_LINK_MESSAGE")));
        userEntityModel.add(linkTo(methodOn(UserController.class).users())
                .withRel(translator.toLocale("FETCHES_USER_BY_ID_HATEOAS_LINK_MESSAGE")));
        userEntityModel.add(linkTo(methodOn(UserController.class).getUserById(userId)).withSelfRel());
        return userEntityModel;
    }

    /**
     * The method that realises the 'GET /users' query.
     *
     * @return {@link List<UserDto>} - all the {@link User}s in the system.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<UserDto>> users() {

        List<UserDto> users = userService.fetchAllUsers();
        List<Article> articles = null;

        List<EntityModel<UserDto>> modelFromArticles = users.stream().map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getUserId()))
                                .withRel(translator.toLocale("FETCHES_USER_BY_ID_HATEOAS_LINK_MESSAGE")),
                        linkTo(methodOn(UserController.class).addArticlesInCart(articles, user.getUserId()))
                                .withRel(translator.toLocale("ADDS_ARTICLES_IN_CART_HATEOAS_LINK_MESSAGE"))))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<UserDto>> collectionModel = CollectionModel.of(modelFromArticles);
//        collectionModel.add(linkTo(methodOn(CertificateTagController.class).tags(paramsNext)).
//                        withRel(translator.toLocale("FETCHES_NEXT_PAGE_TAG_HATEOAS_LINK_MESSAGE")),
//                linkTo(methodOn(CertificateTagController.class).tags(paramsPrev)).
//                        withRel(translator.toLocale("FETCHES_PREVIOUS_PAGE_TAG_HATEOAS_LINK_MESSAGE")),
//                linkTo(methodOn(CertificateTagController.class).tags(parameters)).withSelfRel());
        return collectionModel;
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
        userEntityModel.add(linkTo(methodOn(UserController.class).users())
                .withRel(translator.toLocale("FETCHES_ALL_USERS_HATEOAS_LINK_MESSAGE")));
        userEntityModel.add(linkTo(methodOn(UserController.class).getUserById(userId)).withSelfRel());
        return userEntityModel;
    }
}

