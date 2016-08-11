package musicrecognition.controllers.view;

import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class ResultControllerTest {
    ResultController controller;
    InternalResourceViewResolver viewResolver;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        controller = new ResultController();
        viewResolver = TestUtil.configureViewResolver();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
    
    @Test
    public void returnResultView() throws Exception {
        String expectedView = "result";
            
        mockMvc.perform(get("/result"))
                .andExpect(view().name(expectedView));
    }
}