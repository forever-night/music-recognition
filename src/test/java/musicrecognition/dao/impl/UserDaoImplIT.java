package musicrecognition.dao.impl;

import musicrecognition.config.TestConfig;
import musicrecognition.dao.interfaces.UserDao;
import musicrecognition.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static musicrecognition.util.TestUtil.createUser;
import static org.junit.Assert.*;


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
        user = createUser();
    }
    
    @Test
    public void insertUserNull() {
        user = null;
        Integer id = userDao.insert(user);
        
        assertNull(id);
    }
    
    @Test
    public void insertUserUsernameEmpty() {
        user.setUsername("");
        Integer id = userDao.insert(user);
        
        assertNull(id);
    }
    
    @Test
    public void insertUserPasswordEmpty() {
        user.setPassword("");
        Integer id = userDao.insert(user);
        
        assertNull(id);
    }
    
    @Test
    public void insertUserNotNull() {
        Integer id = userDao.insert(user);
        assertTrue(id > 0);
        assertEquals(User.Role.USER, user.getRole());
    }
    
    @Test(expected = DuplicateKeyException.class)
    public void insertUserDuplicate() {
        userDao.insert(user);
        userDao.insert(user);
    }
    
    @Test
    public void updateUsernameNotExists() {
        User user = createUser();
        user.setUsername("notexists");
        
        User actual = userDao.update(user);
        assertNull(actual);
    }
    
    @Test
    public void getByUsernameNotFound() {
        User actual = userDao.getByUsername("test");
        assertNull(actual);
    }
    
    @Test
    public void getByUsernameFound() {
        userDao.insert(user);
        User actual = userDao.getByUsername(user.getUsername());
        
        assertEquals(user, actual);
    }
    
    @Test
    public void checkIfExistsFalse() {
        boolean actual = userDao.checkIfExists(user);
        assertFalse(actual);
    }
    
    @Test
    public void checkIfExistsTrue() {
        userDao.insert(user);
        boolean actual = userDao.checkIfExists(user);
        
        assertTrue(actual);
    }
}