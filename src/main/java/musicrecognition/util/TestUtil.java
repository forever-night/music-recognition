package musicrecognition.util;

import musicrecognition.entities.Track;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashSet;
import java.util.Set;


public class TestUtil {
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
    
    public static InternalResourceViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        viewResolver.setContentType("text/html;charset=UTF-8");
    
        return viewResolver;
    }
}
