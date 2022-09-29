package com.bakhanovich.interviews.shoppingcart.dao;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

/**
 * Contains settings for all appIntegrationTests. Test runs on docker MySQL database, wich was specified as following:
 *  * docker run -p 3307:3306 --name testsqldatabase -e MYSQL_DATABASE=testsqldb -e MYSQL_ROOT_PASSWORD=root mysql
 *
 * @author Ihar Bakhanovich on 9/29/2022
 * @project ShoppingCart
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ShoppingCartIntegrationTest {

    // 'static' allow us to create a one DB for all tests in this class.
    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:8.0.30")
            .withDatabaseName("testsqldb")
            .withReuse(true);

    //The port number is generated randomly, that is why we need to ask on which port to listen
    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", container::getUsername);
    }

    @BeforeAll
    public static void setup() {
        container.start();
    }
}
