package com.bakhanovich.interviews.shoppingcart.service;

import com.bakhanovich.interviews.shoppingcart.converter.ArticleToArticleDtoConverter;
import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.service.impl.ArticleServiceImpl;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import com.bakhanovich.interviews.shoppingcart.validator.ArticleValidator;
import com.bakhanovich.interviews.shoppingcart.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains {@link ArticleService} tests.
 *
 * @author Ihar Bakhanovich.
 */
@ExtendWith({MockitoExtension.class})
public class ArticleServiceTest {
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

    @Spy
    @InjectMocks
    ArticleServiceImpl articleService;

    /**
     * The test of the findAll() method.
     */
    @Test
    public void findAllTest() {
        List<Article> articles = new ArrayList<>();
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final Article article2 = new Article(2L, BigDecimal.valueOf(2.99), 20, 2);
        articles.add(article1);
        articles.add(article2);
        List<ArticleDto> articlesDto = new ArrayList<>();
        final ArticleDto articleDto1 = new ArticleDto(1L, BigDecimal.valueOf(1.99), 1);
        final ArticleDto articleDto2 = new ArticleDto(2L, BigDecimal.valueOf(2.99), 20);
        articlesDto.add(articleDto1);
        articlesDto.add(articleDto2);
        given(articleDao.findAll()).willReturn(articles);
        given(articleToArticleDtoConverter.convertList(articles)).willReturn(articlesDto);
        List<ArticleDto> expectedArticleDtos = articleService.fetchAllArticles();
        Assertions.assertEquals(articlesDto, expectedArticleDtos);
    }

    /**
     * The test of the findArticleById() method.
     */
    @Test
    public void findByIdTest() {
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final ArticleDto articleDto1 = new ArticleDto(1L, BigDecimal.valueOf(1.99), 1);
        given(articleToArticleDtoConverter.convert(article1)).willReturn(articleDto1);
        given(articleValidator.checkIsArticleExistInTheSystem(article1.getId())).willReturn(article1);
        Optional<ArticleDto> expectedArticle1Dto
                = Optional.ofNullable(articleService.fetchArticleById(article1.getId()));
        Assertions.assertEquals(Optional.of(articleDto1), expectedArticle1Dto);
    }

    /**
     * The test of the findArticleById() method.
     */
    @Test
    public void updateArticleTest() {
        final Article article1 = new Article(1L, BigDecimal.valueOf(1.99), 1, 2);
        final ArticleDto articleDto1 = new ArticleDto(1L, BigDecimal.valueOf(1.99), 1);
        //todo write ArticleValidatorTest
        Mockito.doNothing().when(articleValidator).validateArticle(article1);
        when(articleDao.findById(article1.getId())).thenReturn(Optional.of(article1));
        given(articleToArticleDtoConverter.convert(article1)).willReturn(articleDto1);
        given(articleDao.findById(article1.getId())).willReturn(Optional.of(article1));
        final ArticleDto expectedArticleDto = articleService.updateArticle(article1);
        Assertions.assertNotNull(expectedArticleDto);
        verify(articleDao).update(any(Article.class));
    }
}
