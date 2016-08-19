package musicrecognition.dao.impl;

import musicrecognition.config.TestConfig;
import musicrecognition.dao.interfaces.UserDao;
import musicrecognition.entities.User;
import musicrecognition.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class UserDaoImplIT {
    @Autowired
    UserDao userDao;
    
    private User user;
            
    @Before
    public void setUp() {
        user = TestUtil.createUser();
    }
    
    @Test
    public void insertUserNull() {
        user = null;
        Integer id = userDao.insert(user);
        
        Assert.assertNull(id);
    }
    
    @Test
    public void insertUserUsernameEmpty() {
        user.setUsername("");
        Integer id = userDao.insert(user);
        
        Assert.assertNull(id);
    }
    
    @Test
    public void insertUserPasswordEmpty() {
        user.setPassword("");
        Integer id = userDao.insert(user);
        
        Assert.assertNull(id);
    }
    
    @Test
    public void insertUserNotNull() {
        Integer id = userDao.insert(user);
        Assert.assertTrue(id > 0);
        Assert.assertEquals(User.Role.USER, user.getRole());
    }
    
    @Test
    public void getByUsernameNotFound() {
        User actual = userDao.getByUsername("test");
        Assert.assertNull(actual);
    }
    
    @Test
    public void getByUsernameFound() {
        userDao.insert(user);
        User actual = userDao.getByUsername(user.getUsername());
        
        Assert.assertEquals(user, actual);
    }
}