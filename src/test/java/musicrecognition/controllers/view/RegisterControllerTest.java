package musicrecognition.controllers.view;

import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class RegisterControllerTest {
    RegisterController controller;
    InternalResourceViewResolver viewResolver;
    MockMvc mockMvc;
    
    private String registerView = "register";
    
    @Before
    public void setUp() {
        controller = new RegisterController();
        viewResolver = TestUtil.configureViewResolver();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
    
    @Test
    public void returnRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(view().name(registerView));
    }
}