package com.bakhanovich.interviews.shoppingcart.dao.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.exception.DuplicateException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * The class that implements the {@link ArticleDao} interface.
 *
 * @author Ihar Bakhanovich
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {
    private static final String DELETE_VALUES_IN_UDER_ORDERED_ARTICLE_TABLE_SQL
            = "delete from shopping_card.user_ordered_article where articleId = ?";

    private EntityManager entityManager;

    /**
     * constructs the ArticleDao Bean implementation
     *
     * @param entityManager
     */
    @Autowired
    public ArticleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves {@link Article} in the database.
     *
     * @param entity is the {@link Article} to save.
     * @throws DuplicateException if a SQLException with the state 23505 or the state 23000 is thrown.
     */
    @Override
    public void save(Article entity) throws DuplicateException {
        entityManager.persist(entity);
    }

    /**
     * Finds all {@link Article} entity in the database.
     *
     * @return List of the {@link Article} objects.
     */
    @Override
    public List<Article> findAll() {
        return entityManager.createQuery("select a from article a order by a.id", Article.class)
                .getResultList();
    }

    /**
     * Finds {@link Optional <Article>} in the database by the id of the {@link Article}.
     *
     * @param id is the {@link long} to find.
     * @return {@link Optional<Article>}.
     */
    @Override
    public Optional<Article> findById(long id) {
        return entityManager
                .createQuery("select a from article a where a.id = :id", Article.class)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    /**
     * Updates the {@link Article}.
     *
     * @param entity is the value of the {@link Article} to update.
     */
    @Override
    public void update(Article entity) {
        Article article = entityManager.find(Article.class, entity.getId());
        entityManager.createQuery("UPDATE article a set a.amount = :amount where a.id = :id")
                .setParameter("amount", entity.getAmount()).setParameter("id", entity.getId()).executeUpdate();
        entityManager.refresh(article);
    }

    /**
     * Removes the {@link Article} object from the database.
     *
     * @param id is the id of the entity to be deleted.
     */
    @Override
    public void delete(long id) {
        entityManager
                .createQuery("delete from article a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Removes records from 'user_order_article' table by articleId.
     *
     * @param articleId is the id to remove by.
     */
    @Override
    public void deleteFromUserOrderArticleByArticleId(long articleId) {
        List resultList = entityManager.createNativeQuery(
                        "select userId as uId, articleId as aId, amount as amount from shopping_card.user_ordered_article where articleId = ?")
                .setParameter(1, articleId).getResultList();
        if (!resultList.isEmpty()) {
            entityManager.createNativeQuery(DELETE_VALUES_IN_UDER_ORDERED_ARTICLE_TABLE_SQL)
                    .setParameter(1, articleId).executeUpdate();
        }
    }

    //todo make a method that reduced the amount of the article when the article is booked by user

    //todo: make a method that returns amount of articles into database, if user rejected from the article or user deleted from db

//    /**
//     * Finds the most popular {@link Article} of the {@link User}
//     * with the biggest sum of order price.
//     *
//     * @return {@link Optional<Article>}.
//     */
//    @Override
//    public Optional<Article> findTheMostPopularArticleOfTheUser() {
//        return Optional.empty();
//    }
}
