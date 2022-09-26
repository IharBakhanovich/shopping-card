package com.bakhanovich.interviews.shoppingcart.converter;

import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Converts {@link Article} to {@link ArticleDto}
 *
 * @author Ihar Bakhanovich.
 */
public interface ArticleToArticleDtoConverter extends Converter<Article, ArticleDto> {
    /**
     * Converts {@link Article} to {@link ArticleDto}.
     *
     * @param article is the {@link Article} to convert.
     * @return {@link ArticleDto} that contains all the {@link Article} data.
     */
    @Override
    ArticleDto convert(Article article);

    /**
     * Converts {@link List<Article>} to {@link List<ArticleDto>}.
     *
     * @param articles is the {@link Article} collection to convert.
     * @return {@link List<ArticleDto>} that contains all converted {@link Article}s.
     */
    List<ArticleDto> convertList(List<Article> articles);
}
