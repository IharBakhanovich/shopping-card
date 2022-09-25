package com.bakhanovich.interviews.shoppingcart.dao;

import com.bakhanovich.interviews.shoppingcart.configuration.TestConfig;
import com.bakhanovich.interviews.shoppingcart.model.impl.Role;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.Optional;

/**
 * Contains {@link ArticleDao} tests.
 *
 * @author Ihar Bakhanovich.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    /**
     * the test of the findAll() method.
     */
    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        Assertions.assertEquals(5, users.size());
    }

    /**
     * The test of the findById() method.
     */
    @Test
    public void testFindById() {
        User user = userDao.findById(1).get();
        Assertions.assertEquals(1, user.getId());
    }

    /**
     * The test of the update() method.
     */
    @Test
    public void testUpdate() {
        User user = userDao.findAll().get(0);
        user.setRole(Role.ROLE_USER);
        userDao.update(user);
        User userAfterUpdate = userDao.findById(user.getId()).get();
        Assertions.assertEquals(userAfterUpdate.getRole(), Role.ROLE_USER);
    }

    /**
     * The test of the delete() method.
     */
    @Test
    public void testDelete() {
        userDao.delete(1);
        Optional<User> user = userDao.findById(1);
        Assertions.assertFalse(user.isPresent());
    }

    @Test
    public void testSave() {
        User article = new User(6L, "User6", "***", Role.ROLE_USER, null);
        userDao.save(article);
        User addedUser = userDao.findById(6).get();
        Assertions.assertEquals(addedUser.getNickName(), addedUser.getNickName());
    }
}
