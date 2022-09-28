package com.bakhanovich.interviews.shoppingcart.converter.impl;

import com.bakhanovich.interviews.shoppingcart.converter.ArticleToArticleDtoConverter;
import com.bakhanovich.interviews.shoppingcart.converter.UserToUserDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.dto.UserDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the {@link UserToUserDtoConverter} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Component
public class UserToUserDtoConverterImpl implements UserToUserDtoConverter {

    private final ArticleToArticleDtoConverter articleToArticleDtoConverter;

    @Autowired
    public UserToUserDtoConverterImpl(ArticleToArticleDtoConverter articleToArticleDtoConverter) {
        this.articleToArticleDtoConverter = articleToArticleDtoConverter;
    }

    /**
     * Converts {@link User} to {@link UserDto}.
     *
     * @param user is the {@link User} to convert.
     * @return {@link UserDto} that contains all the {@link User} data.
     */
    @Override
    public UserDto convert(User user) {
        BigDecimal costsOfAllUserArticles = BigDecimal.ZERO;
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : user.getArticles()) {
            costsOfAllUserArticles
                    = costsOfAllUserArticles.add(article.getPreis().multiply(BigDecimal.valueOf(article.getAmount())));
            articleDtos.add(articleToArticleDtoConverter.convert(article));
        }
        return new UserDto(user.getId(), user.getNickName(), costsOfAllUserArticles, user.getRole(), articleDtos);
    }

    /**
     * Converts {@link List<User>} to {@link List<UserDto>}.
     *
     * @param users is the {@link User} collection to convert.
     * @return {@link List <UserDto>} that contains all converted {@link User}s.
     */
    @Override
    public List<UserDto> convertList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convert(user));
        }
        return userDtos;
    }
}


