package musicrecognition.controllers.view;

import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class Error403ControllerTest {
    Error403Controller controller;
    InternalResourceViewResolver viewResolver;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        controller = new Error403Controller();
        viewResolver = TestUtil.configureViewResolver();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
    
    @Test
    public void return403View() throws Exception {
        String expectedViewName = "403";
        
        mockMvc.perform(get("/403"))
                .andExpect(view().name(expectedViewName));
    }
}