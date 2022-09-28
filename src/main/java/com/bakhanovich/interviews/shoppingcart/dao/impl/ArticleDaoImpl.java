package com.bakhanovich.interviews.shoppingcart.dao.impl;

import com.bakhanovich.interviews.shoppingcart.dao.ArticleDao;
import com.bakhanovich.interviews.shoppingcart.exception.DuplicateException;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

//    private static final String DELETE_VALUES_IN_UDER_ORDERED_ARTICLE_TABLE_SQL
//            = "delete from shopping_card.user_ordered_article where articleId = ?";
    private static final String FIND_ENTITY_BY_ID_SQL
            = "select article.id as articleId, article.preis as articlePreis, article.amount as articleAmount, article.min_amount as articleMinAmount from article where id = ?";
    private static final String FIND_ALL_ENTITIES_SQL
            = "select article.id as articleId, article.preis as articlePreis, article.amount as articleAmount, article.min_amount as articleMinAmount from article";
    private static final String UPDATE_ENTITY_SQL = "update article set preis = ?, amount = ?, min_amount = ? where id = ?";
    private static final String DELETE_ENTITY_BY_ID_SQL = "delete from article where id = ?";
    private static final String INSERT_ENTITY_SQL = "insert into article (preis, amount, min_amount) values (?, ?, ?)";

//    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Article> articleRowMapper;

    @Autowired
    public ArticleDaoImpl(JdbcTemplate jdbcTemplate,
                          RowMapper<Article> articleRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleRowMapper = articleRowMapper;
    }

    /**
     * Saves {@link Article} in the database.
     *
     * @param entity is the {@link Article} to save.
     * @throws DuplicateException if a SQLException with the state 23505 or the state 23000 is thrown.
     */
    @Override
    public void save(Article entity) throws DuplicateException {
//        entityManager.persist(entity);
        jdbcTemplate.update(INSERT_ENTITY_SQL,
                entity.getPreis(),
                entity.getAmount(),
                entity.getMinAmount());
    }

    /**
     * Finds all {@link Article} entity in the database.
     *
     * @return List of the {@link Article} objects.
     */
    @Override
    public List<Article> findAll() {
//        return entityManager.createQuery("select a from article a order by a.id", Article.class)
//                .getResultList();
        return jdbcTemplate.query(FIND_ALL_ENTITIES_SQL, articleRowMapper);
    }

    /**
     * Finds {@link Optional <Article>} in the database by the id of the {@link Article}.
     *
     * @param id is the {@link long} to find.
     * @return {@link Optional<Article>}.
     */
    @Override
    public Optional<Article> findById(long id) {
        return jdbcTemplate.query(FIND_ENTITY_BY_ID_SQL, articleRowMapper, id).stream().findFirst();
    }

    /**
     * Updates the {@link Article}.
     *
     * @param entity is the value of the {@link Article} to update.
     */
    @Override
    public void update(Article entity) {

        jdbcTemplate.update(UPDATE_ENTITY_SQL,
                entity.getPreis(),
                entity.getAmount(),
                entity.getMinAmount(),
                entity.getId());
    }

    /**
     * Removes the {@link Article} object from the database.
     *
     * @param id is the id of the entity to be deleted.
     */
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_ENTITY_BY_ID_SQL, id);
    }

//    /**
//     * Removes records from 'user_order_article' table by articleId.
//     *
//     * @param articleId is the id to remove by.
//     */
//    @Override
//    public void deleteFromUserOrderedArticleByArticleId(long articleId) {
//        List resultList = entityManager.createNativeQuery(
//                        "select userId as uId, articleId as aId, amount as amount from shopping_card.user_ordered_article where articleId = ?")
//                .setParameter(1, articleId).getResultList();
//        if (!resultList.isEmpty()) {
//            entityManager.createNativeQuery(DELETE_VALUES_IN_UDER_ORDERED_ARTICLE_TABLE_SQL)
//                    .setParameter(1, articleId).executeUpdate();
//        }
//    }
}
