package com.bakhanovich.interviews.shoppingcart.dao.impl;

import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps values from {@link ResultSet} to {@link Article}.
 *
 * @author Ihar Bakhanovich
 */
@Component
public class ArticleRowMapper implements RowMapper<Article> {

    /**
     * Maps the row from {@link ResultSet} to the {@link Article}.
     *
     * @param resultSet is the {@link ResultSet} to map from.
     * @param rowNum    is the row to map.
     * @return {@link Article}.
     * @throws SQLException if something went wrong.
     */
    @Override
    public Article mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Article(
                resultSet.getLong(ColumnNames.TABLE_ARTICLE_COLUMN_ID),
                resultSet.getBigDecimal(ColumnNames.TABLE_ARTICLE_COLUMN_PREIS),
                resultSet.getInt(ColumnNames.TABLE_ARTICLE_COLUMN_AMOUNT),
                resultSet.getInt(ColumnNames.TABLE_ARTICLE_COLUMN_MIN_AMOUNT)
        );
    }
}
