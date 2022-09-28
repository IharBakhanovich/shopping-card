package com.bakhanovich.interviews.shoppingcart.controller;

import com.bakhanovich.interviews.shoppingcart.dto.ArticleDto;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.service.ArticleService;
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
 * API to work with {@link Article}s of the ShoppingCart.
 *
 * @author Ihar Bakhanovich
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final Translator translator;

    /**
     * Constructs the {@link ArticleController}.
     *
     * @param articleService is the service to inject.
     */
    @Autowired
    public ArticleController(ArticleService articleService, Translator translator) {
        this.articleService = articleService;
        this.translator = translator;
    }

    /**
     * The method that realises the 'GET /articles' query.
     *
     * @return {@link List<Article>} - all the {@link Article} in the system.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<ArticleDto>> articles() {

        List<ArticleDto> articles = articleService.fetchAllArticles();
        List<EntityModel<ArticleDto>> modelFromArticles = articles.stream().map(articleDto -> EntityModel.of(articleDto,
                        linkTo(methodOn(ArticleController.class).article(articleDto.getArticleId()))
                                .withRel(translator.toLocale("FETCHES_ARTICLE_BY_ID_HATEOAS_LINK_MESSAGE")),
                        linkTo(methodOn(ArticleController.class).updateArticle(articleDto.getArticleId(), new Article()))
                                .withRel(translator.toLocale("UPDATES_ARTICLE_HATEOAS_LINK_MESSAGE"))))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<ArticleDto>> collectionModel = CollectionModel.of(modelFromArticles);
        return collectionModel;
    }

    /**
     * The method that realises the 'GET /articles/{articleId}' query.
     *
     * @param articleId is the ID of the {@link Article} to find.
     * @return {@link EntityModel<Article>} that contains {@link Article}
     * with the tagId if such an id exists in the system.
     */
    @GetMapping(value = "/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ArticleDto> article(@PathVariable("articleId") long articleId) {
        ArticleDto articleDto = articleService.fetchArticleById(articleId);
        EntityModel<ArticleDto> orderEntityModel
                = EntityModel.of(articleDto, linkTo(methodOn(ArticleController.class).articles())
                .withRel(translator.toLocale("FETCHES_ALL_ARTICLES_HATEOAS_LINK_MESSAGE")));
        orderEntityModel.add(linkTo(methodOn(ArticleController.class).updateArticle(articleDto.getArticleId(), new Article()))
                .withRel(translator.toLocale("UPDATES_ARTICLE_HATEOAS_LINK_MESSAGE")));
        return orderEntityModel.add(linkTo(methodOn(ArticleController.class).article(articleDto.getArticleId())).withSelfRel());
    }

    /**
     * The method that realises the 'PUT /tags/{articleId}' query and updates the {@link Article}
     * with the id equals {@param articleId}.
     *
     * @param article   is the {@link Article} to update.
     * @param articleId is the id of the {@link Article}, which is to update.
     * @return the updated {@link Article}.
     */
    @PutMapping(value = "/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ArticleDto> updateArticle(@PathVariable("articleId") long articleId,
                                                 @RequestBody Article article) {

        ArticleDto updatedArticle = articleService.updateArticle(article);
        EntityModel<ArticleDto> orderEntityModel
                = EntityModel.of(updatedArticle, linkTo(methodOn(ArticleController.class)
                .article(articleId)).withRel(translator.toLocale("FETCHES_ARTICLE_BY_ID_HATEOAS_LINK_MESSAGE")));
        orderEntityModel.add(linkTo(methodOn(ArticleController.class).articles())
                .withRel(translator.toLocale("FETCHES_ALL_ARTICLES_HATEOAS_LINK_MESSAGE")));
        return orderEntityModel.add(linkTo(methodOn(ArticleController.class)
                .updateArticle(articleId, article)).withSelfRel());
    }
}
