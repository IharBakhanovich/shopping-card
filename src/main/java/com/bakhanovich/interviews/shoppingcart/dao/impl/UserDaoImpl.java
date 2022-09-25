package com.bakhanovich.interviews.shoppingcart.dao.impl;

import com.bakhanovich.interviews.shoppingcart.dao.UserDao;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The class, that implements {@link UserDao} interface.
 *
 * @author Ihar Bakhanovich
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    private static final String FIND_ALL_ENTITIES_SQL
            = "select u.id as userId, u.nickName as userNickname," +
            " u.role as userRole, u.password as userPassword," +
            " a.id as articleId, a.preis as articlePreis, uoa.amount as articleAmount" +
            " from app_user as u LEFT OUTER JOIN" +
            " (user_ordered_article as uoa LEFT OUTER JOIN article as a ON a.id = uoa.articleId)" +
            " ON u.id = uoa.userId";

    private static final String FIND_ENTITY_BY_ID_SQL
            = "select u.id as userId, u.nickName as userNickname," +
            " u.role as userRole, u.password as userPassword," +
            " a.id as articleId, a.preis as articlePreis, uoa.amount as articleAmount" +
            " from app_user as u LEFT OUTER JOIN" +
            " (user_ordered_article as uoa LEFT OUTER JOIN article as a ON a.id = uoa.articleId)" +
            " ON u.id = uoa.userId where u.id = ?";

    private static final String FIND_ENTITY_BY_NAME_SQL
            = "select u.id as userId, u.nickName as userNickname," +
            " u.role as userRole, u.password as userPassword," +
            " a.id as articleId, a.preis as articlePreis, uoa.amount as articleAmount" +
            " from app_user as u LEFT OUTER JOIN" +
            " (user_ordered_article as uoa LEFT OUTER JOIN article as a ON a.id = uoa.articleId)" +
            " ON u.id = uoa.userId where u.nickName = ?";

    private static final String UPDATE_ENTITY_SQL
            = "update app_user" +
            " set nickName = ?, role = ?" +
            " where id = ?";
    private static final String UPDATE_USER_ORDERED_ARTICLE_SQL
            = "update user_ordered_article" +
            " set amount = ?" +
            " where userId = ? and articleId = ?";
    private static final String INSERT_IN_USER_ORDERED_ARTICLE_SQL
            = "insert into user_ordered_article (userId, articleId, amount) values (?, ?, ?)";
    private static final String DELETE_RECORD_FROM_USER_ORDERED_ARTICLE_TABLE_SQL =
            "delete from user_ordered_article where userId = ? and articleId = ?";
    private static final String INSERT_ENTITY_SQL
            = "insert into app_user (nickName, role, password) values (?, ?, ?)";
    private static final String DELETE_ENTITY_BY_ID_SQL = "delete from app_user where id = ?";

    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    private UserExtractor userExtractor;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, JdbcTemplate jdbcTemplate, UserExtractor userExtractor) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
        this.userExtractor = userExtractor;
    }

    /**
     * Saves {@link User} in the database.
     *
     * @param user is the {@link User} to save.
     * @throws DuplicateException if a SQLException with the state 23505 or the state 23000 is thrown.
     */
    @Override
    public void save(User user) {
//        entityManager.persist(user);
        jdbcTemplate.update(INSERT_ENTITY_SQL,
                user.getNickName(),
                user.getRole(),
                user.getPassword());
        for (Article article : user.getArticles()) {
            updateArticleBookedByUser(user.getId(), article);
        }
    }

    /**
     * Finds all {@link User} entity in the database.
     *
     * @return List of the {@link User} objects.
     */
    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_ENTITIES_SQL, userExtractor);
    }

    /**
     * Finds {@link Optional <User>} in the database by the id of the {@link User}.
     *
     * @param id is the {@link long} to find.
     * @return {@link Optional<User>}.
     */
    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate
                .query(FIND_ENTITY_BY_ID_SQL, userExtractor, id)
                .stream()
                .findFirst();
    }

    /**
     * Updates the {@link User}.
     *
     * @param user is the value of the {@link User} to update.
     */
    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_ENTITY_SQL,
                user.getNickName(),
                user.getRole(),
                user.getId());
        updateUserArticles(user);
    }

    private void updateUserArticles(User user) {
        List<Article> existingUserArticles = findById(user.getId()).get().getArticles();
        for (Article article : user.getArticles()) {
            if (isUserContainsArticleWithSuchId(existingUserArticles, article.getId())) {
                updateArticleBookedByUser(user.getId(), article);
            } else {
                addArticleToUserCart(user.getId(), article);
            }
        }
    }

    /**
     * adds new {@link Article} to user cart.
     *
     * @param userId  is the id of the user, whom new {@link Article} is to be added.
     * @param article is the {@link Article} to be added.
     */
    @Override
    public void addArticleToUserCart(Long userId, Article article) {
        jdbcTemplate.update(INSERT_IN_USER_ORDERED_ARTICLE_SQL,
                userId,
                article.getId(),
                article.getAmount()
        );
    }

    /**
     * Removes {@link Article} from user's cart.
     *
     * @param userId    is the id of the user, from whom an {@link Article} with {@param articleId} to be removed.
     * @param articleId is the {@link Article} to be removed.
     */
    @Override
    public void deleteArticleFromUserCart(Long userId, Long articleId) {
        jdbcTemplate.update(DELETE_RECORD_FROM_USER_ORDERED_ARTICLE_TABLE_SQL, userId, articleId);
    }

    /**
     * Updates {@link Article} in user's cart.
     *
     * @param userId  is the user's id, whom is to update the {@link Article} in his cart.
     * @param article is the article, which should be in the cart as the result.
     */
    @Override
    public void updateArticleBookedByUser(Long userId, Article article) {
        jdbcTemplate.update(UPDATE_USER_ORDERED_ARTICLE_SQL, article.getAmount(), userId, article.getId());
    }

    private boolean isUserContainsArticleWithSuchId(List<Article> existingUserArticles, long id) {
        for (Article article : existingUserArticles) {
            if (article.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the {@link User} object from the database.
     *
     * @param id is the value of the {@link long} to find.
     */
    @Override
    public void delete(long id) {
//        entityManager
//                .createQuery("delete from user u where u.id = :id")
//                .setParameter("id", id)
//                .executeUpdate();
        jdbcTemplate.update(DELETE_ENTITY_BY_ID_SQL, id);
    }

    /**
     * Finds {@link Optional<User>} in the database by the id of the {@link User}.
     *
     * @param nickName is the {@link long} to find.
     * @return {@link Optional<User>}.
     */
    @Override
    public Optional<User> findByName(String nickName) {
        return jdbcTemplate
                .query(FIND_ENTITY_BY_NAME_SQL, userExtractor, nickName)
                .stream()
                .findFirst();
    }
}
