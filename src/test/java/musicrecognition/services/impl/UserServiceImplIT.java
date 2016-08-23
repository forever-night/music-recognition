package musicrecognition.services.impl;

import musicrecognition.config.TestConfig;
import musicrecognition.entities.User;
import musicrecognition.services.interfaces.UserService;
import musicrecognition.util.TestUtil;
import org.junit.Assert;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class UserServiceImplIT {
    @Autowired
    UserService userService;
    
    private User user;
            
    @Before
    public void setUp() {
        user = TestUtil.createUser();
    }
    
    @Test
    public void insertNotNull() {
        String textPassword = user.getPassword();
        Integer id = userService.insert(user);
        
        Assert.assertTrue(id > 0);
        Assert.assertNotEquals(textPassword, user.getPassword());
    }
    
    @Test(expected = DuplicateKeyException.class)
    public void insertUserDuplicate() {
        userService.insert(user);
        userService.insert(user);
    }
    
    @Test
    public void dtoToEntity() {
        String expectedUsername = "test";
        String expectedPassword = "testpassword";
        
        musicrecognition.dto.User userDto = new musicrecognition.dto.User();
        userDto.setUsername(expectedUsername);
        userDto.setPassword(expectedPassword);
        
        User userEntity = userService.dtoToEntity(userDto);
        
        Assert.assertEquals(expectedUsername, userEntity.getUsername());
        Assert.assertEquals(expectedPassword, userEntity.getPassword());
        Assert.assertEquals(User.Role.USER, userEntity.getRole());
        Assert.assertTrue(userEntity.getEnabled());
    }
}