package com.bakhanovich.interviews.shoppingcart.dao.impl;

/**
 * Noninstantiable utility class. Stores names of the database tables columns,
 * which are used by {@link UserExtractor} and {@link ArticleRowMapper} to fetch data from a ResultSet
 * and other application constants.
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
    public static final String TABLE_ARTICLE_COLUMN_MIN_AMOUNT = "articleMinAmount";

    public static final String ERROR_CODE_ENTITY_NOT_FOUND = "404";

    // Suppress default constructor for noninstantiability. Also prevents the class from being subclassed.
    // All constructors must invoke a superclass constructor, explicitly or implicitly,
    // and a subclass would have no accessible superclass constructor to invoke.
    private ColumnNames() {
        throw new AssertionError();
    }
}
