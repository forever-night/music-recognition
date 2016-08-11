package musicrecognition.controllers.json;

import musicrecognition.config.MultipartTestConfig;
import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;
import musicrecognition.services.FingerprintService;
import musicrecognition.services.IdentificationService;
import musicrecognition.util.TestUtil;
import musicrecognition.util.audio.AudioAnalysisUtil;
import musicrecognition.util.audio.audiotypes.AudioType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        Assert.assertTrue(context.containsBean("multipartResolver"));
    }
    
    @Test
    public void identifyUnacceptableFile() throws Exception {
        String fileName = "file.txt";
        MockMultipartFile fileMock = new MockMultipartFile("file", fileName, null, fileName.getBytes());
         
        mockMvc.perform(fileUpload("/upload").file(fileMock).param("identify",""))
                .andExpect(status().isUnsupportedMediaType());
    }
}