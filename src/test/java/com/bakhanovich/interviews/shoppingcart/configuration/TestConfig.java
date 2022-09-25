package com.bakhanovich.interviews.shoppingcart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Configuration for the DAO testing.
 *
 * @author Ihar Bakhanovich.
 */
@Configuration
@ComponentScan("com.bakhanovich.interviews.shoppingcart")
public class TestConfig implements WebMvcConfigurer {
    private final Environment environment;

    @Autowired
    public TestConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * the Embedded {@link javax.sql.DataSource} that is used by the DAO testing.
     *
     * @return {@link javax.sql.DataSource}.
     */
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql/create_db.sql")
                .addScript("sql/insert_data.sql")
                .build();
    }
}
