package com.bakhanovich.interviews.shoppingcart.converter.impl;

import com.bakhanovich.interviews.shoppingcart.converter.ArticleToArticleDtoConverter;
import com.bakhanovich.interviews.shoppingcart.converter.UserToUserDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * The implementation of the {@link UserToUserDtoConverter} interface.
 *
 * @author Ihar Bakhanovich.
 */
@Component
public class ArticleToArticleDtoConverterImpl implements ArticleToArticleDtoConverter {
    /**
     * Converts {@link Article} to {@link ArticleDto}.
     *
     * @param article is the {@link Article} to convert.
     * @return {@link ArticleDto} that contains all the {@link Article} data.
     */
    @Override
    public ArticleDto convert(Article article) {
        return new ArticleDto(article.getId(), article.getPreis(), article.getAmount());
    }

    /**
     * Converts {@link List <Article>} to {@link List<ArticleDto>}.
     *
     * @param articles is the {@link Article} collection to convert.
     * @return {@link List<ArticleDto>} that contains all converted {@link Article}s.
     */
    @Override
    public List<ArticleDto> convertList(List<Article> articles) {
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            articleDtos.add(convert(article));
        }
        return articleDtos;
    }
}
