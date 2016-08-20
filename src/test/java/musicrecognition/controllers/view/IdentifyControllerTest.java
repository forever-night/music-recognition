package musicrecognition.controllers.view;

import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class IdentifyControllerTest {
    IdentifyController controller;
    InternalResourceViewResolver viewResolver;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        controller = new IdentifyController();
        viewResolver = TestUtil.configureViewResolver();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
    
    @Test
    public void returnIdentifyView() throws Exception {
        String expectedView = "identify";
        
        mockMvc.perform(get("/identify"))
                .andExpect(view().name(expectedView));
    }
}