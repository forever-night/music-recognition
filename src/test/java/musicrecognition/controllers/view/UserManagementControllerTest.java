package musicrecognition.controllers.view;

import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class UserManagementControllerTest {
    UserManagementController controller;
    InternalResourceViewResolver viewResolver;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        controller = new UserManagementController();
        viewResolver = TestUtil.configureViewResolver();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
    
    @Test
    public void returnManagementView() throws Exception {
        String expectedView = "management";
        
        mockMvc.perform(get("/management"))
                .andExpect(view().name(expectedView));
    }
}