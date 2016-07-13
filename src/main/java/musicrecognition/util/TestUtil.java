package musicrecognition.util;

import musicrecognition.entities.Track;

import java.util.HashSet;
import java.util.Set;


public class TestUtil {
    public static Track[] createTracksWithFingerprints(int count) {
        Track[] tracks = new Track[count];
        
        for (int i = 0; i < count; i++) {
            Track temp = new Track();
            
            temp.setTitle(String.valueOf(i));
            temp.setArtist(String.valueOf(i));
            temp.setYear(2000 + i);
            
            Set<Integer> fingerprints = new HashSet<>();
            
            for (int j = 0; j < 5; j++)
                fingerprints.add(i + j + 1);
            
            temp.setFingerprints(fingerprints);
            
            
            tracks[i] = temp;
        }
        
        return tracks;
    }
}
