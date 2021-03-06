package musicrecognition.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import musicrecognition.controllers.WebExceptionHandler;
import musicrecognition.dto.UserDto;
import musicrecognition.entities.User;
import musicrecognition.services.interfaces.UserService;
import musicrecognition.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static musicrecognition.util.TestUtil.createUserDto;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RegisterRestControllerTest {
    UserService mockUserService;
    RegisterRestController controller;
    StringHttpMessageConverter stringHttpMessageConverter;
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
    MockMvc mockMvc;
    
    private UserDto userDto;
    
    @Before
    public void setUp() {
        mockUserService = mock(UserService.class);
        controller = new RegisterRestController(mockUserService);
        
        stringHttpMessageConverter = TestUtil.stringHttpMessageConverter();
        jackson2HttpMessageConverter = TestUtil.jackson2HttpMessageConverter();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(stringHttpMessageConverter, jackson2HttpMessageConverter)
                .setControllerAdvice(new WebExceptionHandler())
                .build();
    
        userDto = createUserDto();
    }
    
    @Test
    public void registerUserNotNull() throws Exception {
        User userEntity = TestUtil.dtoToEntity(userDto);

        when(mockUserService.insert(userEntity))
                .thenReturn(1);
        
        
        String json = toJson(userDto);
    
        mockMvc.perform(post("/rest/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes()))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void registerUserPasswordEmpty() throws Exception {
        userDto.setPassword("");
        
        String json = toJson(userDto);
    
        mockMvc.perform(post("/rest/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes()))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void registerUserDuplicate() throws Exception {
        when(mockUserService.insert(any(UserDto.class)))
                .thenThrow(DuplicateKeyException.class);
        
        String json = toJson(userDto);
        
        mockMvc.perform(post("/rest/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes()))
                .andExpect(status().isUnprocessableEntity());
    }
    
    private String toJson(Object object) throws JsonProcessingException {
        return jackson2HttpMessageConverter.getObjectMapper().writeValueAsString(object);
    }
}