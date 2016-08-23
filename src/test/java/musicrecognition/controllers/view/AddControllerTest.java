package musicrecognition.controllers.view;

import musicrecognition.util.Global;
import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class AddControllerTest {
    AddController controller;
    InternalResourceViewResolver viewResolver;
    MockMvc mockMvc;
    
    private String addView = "add";
    private String addStatusView = "addStatus";
    
    @Before
    public void setUp() {
        controller = new AddController();
        viewResolver = TestUtil.configureViewResolver();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
    
    @Test
    public void returnAddView() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(view().name(addView));
    }
    
    @Test
    public void returnAddStatusViewCode201() throws Exception {
        mockMvc.perform(get("/add").param("status", "201"))
                .andExpect(view().name(addStatusView))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", Global.UIMessage.SUCCESS.getMessage()));
    }
    
    @Test
    public void returnAddStatusViewCode202() throws Exception {
        mockMvc.perform(get("/add").param("status", "202"))
                .andExpect(view().name(addStatusView))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", Global.UIMessage.ERROR.getMessage() + " 202"));
    }
}