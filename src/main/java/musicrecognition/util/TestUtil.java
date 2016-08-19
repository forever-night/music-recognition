package musicrecognition.util;

import musicrecognition.entities.Track;
import musicrecognition.entities.User;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashSet;
import java.util.Set;


public class TestUtil {
    public static User createUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        
        return user;
    }
    
    /**
     * Creates Track for testing without fingerprints.
     * */
    public static Track createSimpleTrack() {
        Track track = new Track();
        track.setTitle("test");
        track.setArtist("artist");
        track.setAlbumTitle("album");
        track.setYear(3000);
        
        return track;
    }
    
    public static Track[] createTracksWithFingerprints(int count) {
        Track[] tracks = new Track[count];
        
        for (int i = 0; i < count; i++) {
            Track temp = new Track();
            
            temp.setTitle(String.valueOf(i));
            temp.setArtist(String.valueOf(i));
            temp.setAlbumTitle(String.valueOf(i));
            temp.setYear(2000 + i);
            
            Set<Integer> fingerprints = new HashSet<>();
            
            for (int j = 0; j < 5; j++)
                fingerprints.add(i + j + 1);
            
            temp.setFingerprints(fingerprints);
            
            
            tracks[i] = temp;
        }
        
        return tracks;
    }
    
    public static musicrecognition.dto.User createUserDto() {
        musicrecognition.dto.User userDto = new musicrecognition.dto.User();
        userDto.setUsername("user");
        userDto.setPassword("password");
        
        return userDto;
    }
    
    public static User dtoToEntity(musicrecognition.dto.User userDto) {
        musicrecognition.entities.User userEntity = new musicrecognition.entities.User();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        
        return userEntity;
    }
    
    public static InternalResourceViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        viewResolver.setContentType("text/html;charset=UTF-8");
    
        return viewResolver;
    }
    
    public static StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter();
    }
    
    public static MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
