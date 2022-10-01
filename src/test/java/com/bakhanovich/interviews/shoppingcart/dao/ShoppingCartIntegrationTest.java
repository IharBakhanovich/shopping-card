package com.bakhanovich.interviews.shoppingcart.dao;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Contains settings for all appIntegrationTests. Test runs on docker MySQL database, wich was specified as following:
 * * docker run -p 3307:3306 --name testsqldatabase -e MYSQL_DATABASE=testsqldb -e MYSQL_ROOT_PASSWORD=root mysql
 *
 * @author Ihar Bakhanovich on 9/29/2022
 * @project ShoppingCart
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql({"classpath:sql/create_db.sql", "classpath:sql/insert_data.sql"})
public class ShoppingCartIntegrationTest {
//    private static MySQLContainer container;
//
//    static {
//        (MySQLContainer) new MySQLContainer("mysql:8.0.30")
//                .withDatabaseName("testsqldb")
//                .withUsername("root")
//                .withPassword("root")
//                .withReuse(true);
//        container.start();
//    }

    // 'static' allow us to create a one DB for all tests in this class.
    // Remove '@Container' and '@Testcontainers' to not allow testcontainers to manage container.
//    @Container
    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:8.0.30")
            .withDatabaseName("testsqldb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    //The port number is generated randomly, that is why we need to ask on which port to listen
    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeAll
    public static void setup() {
        container.start();
    }
}
