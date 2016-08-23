package musicrecognition.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import musicrecognition.controllers.WebExceptionHandler;
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
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
public class RegisterRestControllerTest {
    UserService mockUserService;
    RegisterRestController controller;
    StringHttpMessageConverter stringHttpMessageConverter;
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
    MockMvc mockMvc;
    
    private musicrecognition.dto.User userDto;
    
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
    
        userDto = TestUtil.createUserDto();
    }
    
    @Test
    public void registerUserNotNull() throws Exception {
        musicrecognition.entities.User userEntity = TestUtil.dtoToEntity(userDto);

        when(mockUserService.dtoToEntity(userDto))
                .thenReturn(userEntity);
        
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
        musicrecognition.entities.User userEntity = TestUtil.dtoToEntity(userDto);
    
        when(mockUserService.dtoToEntity(userDto))
                .thenReturn(userEntity);
    
        when(mockUserService.insert(userEntity))
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