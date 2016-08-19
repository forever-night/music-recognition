package musicrecognition.controllers.json;

import musicrecognition.config.MultipartTestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MultipartTestConfig.class})
@WebAppConfiguration
public class JsonFileUploadControllerIT {
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext context;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void multipartResolverNotNull() {
        Assert.assertTrue(context.containsBean("filterMultipartResolver"));
    }
}