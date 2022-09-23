package com.bakhanovich.interviews.shoppingcard.dao.impl;

import com.bakhanovich.interviews.shoppingcard.model.impl.Article;
import com.bakhanovich.interviews.shoppingcard.model.impl.Role;
import com.bakhanovich.interviews.shoppingcard.model.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts {@link List<User>} from the {@link ResultSet}.
 *
 * @author Ihar Bakhanovich
 */
@Component
public class UserExtractor implements ResultSetExtractor<List<User>> {

    private RowMapper<Article> articleRowMapper;

    @Autowired
    public UserExtractor(RowMapper<Article> articleRowMapper) {
        this.articleRowMapper = articleRowMapper;
    }


    /**
     * Extract {@link List<User>} from the {@link ResultSet}.
     *
     * @param resultSet is the {@link ResultSet} to map.
     * @return {@link List<User>}.
     * @throws SQLException        when something went wrong.
     * @throws DataAccessException when the datasource is not available.
     */
    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            final long currentId = resultSet.getLong(ColumnNames.TABLE_USER_COLUMN_ID);
            if (users.stream().anyMatch(user -> user.getId() == currentId)) {
                if (resultSet.getLong(ColumnNames.TABLE_ARTICLE_COLUMN_ID) != 0) {
                    Article article = articleRowMapper.mapRow(resultSet, resultSet.getRow());
                    users.stream()
                            .filter(user -> user.getId() == currentId)
                            .findAny().ifPresent(user -> user.getArticles().add(article));
                }
            } else {
                List<Article> allArticlesThisUser = new ArrayList<Article>();
                User user = new User(
                        resultSet.getLong(ColumnNames.TABLE_USER_COLUMN_ID),
                        resultSet.getString(ColumnNames.TABLE_USER_COLUMN_NICKNAME),
                        resultSet.getString(ColumnNames.TABLE_USER_COLUMN_PASSWORD),
                        Role.resolveRoleById(resultSet.getLong(ColumnNames.TABLE_USER_COLUMN_ROLE)),
                        allArticlesThisUser
                );
                users.add(user);
                if (resultSet.getLong(ColumnNames.TABLE_ARTICLE_COLUMN_ID) != 0) {
                    Article article = articleRowMapper.mapRow(resultSet, resultSet.getRow());
                    users.stream()
                            .filter(user1 -> user1.getId() == currentId)
                            .findAny().ifPresent(user1 -> user1.getArticles().add(article));
                }
            }
        }
        return users;
    }
}
