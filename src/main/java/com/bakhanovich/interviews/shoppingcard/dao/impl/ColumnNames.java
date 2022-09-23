package com.bakhanovich.interviews.shoppingcard.dao.impl;

/**
 * Stores names of the database tables columns, which are used by {@link UserExtractor} and
 * {@link ArticleRowMapper} to fetch data from a ResultSet.
 *
 * @author Ihar Bakhanovich
 */
public class ColumnNames {

    public static final String TABLE_USER_COLUMN_ID = "userId";
    public static final String TABLE_USER_COLUMN_NICKNAME = "userNickname";
    public static final String TABLE_USER_COLUMN_ROLE = "userRole";
    public static final String TABLE_USER_COLUMN_PASSWORD = "userPassword";
    public static final String TABLE_ARTICLE_COLUMN_ID = "articleId";
    public static final String TABLE_ARTICLE_COLUMN_PREIS = "articlePreis";
    public static final String TABLE_ARTICLE_COLUMN_AMOUNT = "articleAmount";

    private ColumnNames() {
    }
}
