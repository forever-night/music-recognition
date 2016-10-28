package musicrecognition.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import musicrecognition.controllers.WebExceptionHandler;
import musicrecognition.dto.UserDto;
import musicrecognition.services.interfaces.UserService;
import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserRestControllerTest {
    UserService mockUserService;
    UserRestController controller;
    StringHttpMessageConverter stringHttpMessageConverter;
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        mockUserService = mock(UserService.class);
        controller = new UserRestController(mockUserService);
        
        stringHttpMessageConverter = TestUtil.stringHttpMessageConverter();
        jackson2HttpMessageConverter = TestUtil.jackson2HttpMessageConverter();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(stringHttpMessageConverter, jackson2HttpMessageConverter)
                .setControllerAdvice(new WebExceptionHandler())
                .build();
    }
    
    @Test
    public void getAll4Results() throws Exception {
        List<UserDto> expected = new ArrayList<>();
    
        for (int i = 0; i < 4; i++) {
            UserDto dto = TestUtil.createUserDto();
            dto.setUsername(dto.getUsername() + i);
            expected.add(dto);
        }
        
        String json = toJson(expected);
    
        when(mockUserService.getAll())
                .thenReturn(expected);
        
        mockMvc.perform(get("/rest/user"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(json))
                .andExpect(status().isOk());
    }
    
    private String toJson(Object object) throws JsonProcessingException {
        return jackson2HttpMessageConverter.getObjectMapper().writeValueAsString(object);
    }
}