package musicrecognition.services;

import musicrecognition.config.TestConfig;
import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;
import musicrecognition.services.interfaces.TrackService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class TrackServiceImplIT {
    @Autowired
    TrackService trackService;
    
    Track track;
    
    @Before
    public void setUp() {
        track = new Track();
        track.setTitle("title");
        track.setArtist("artist");
        track.setAlbumTitle("album");
        track.setYear(2000);
    }
    
    @Test
    public void insertTrackNull() {
        track = null;
        
        Integer actual = trackService.insert(null);
        Assert.assertNull(actual);
    }
    
    @Test
    public void insertTrackNotNull() {
        track.setFingerprints(generateFingerprints());
        
        Integer actualId = trackService.insert(track);
        Assert.assertTrue(actualId > 0);
    }
    
    @Test
    public void getTracksByFingerprintsNull() {
        List<TrackMatch> actual = trackService.getTracksByFingerprints(null);
        Assert.assertNull(actual);
    }
    
    @Test
    public void getTracksByFingerprintsEmpty() {
        Set<Integer> fingerprints = new HashSet<>();
        
        List<TrackMatch> actual = trackService.getTracksByFingerprints(fingerprints);
        Assert.assertNull(actual);
    }
    
    @Test
    public void getTracksByFingerprintsNotNull() {
        Set<Integer> fingerprints = generateFingerprints();
        
        track.setFingerprints(fingerprints);
        int expectedId = trackService.insert(track);
        track.setId(expectedId);
        
        List<TrackMatch> actual = trackService.getTracksByFingerprints(fingerprints);
        
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(track, actual.get(0).getTrack());
    }
    
    private Set<Integer> generateFingerprints() {
        Set<Integer> fingerprints = new HashSet<>();
    
        for (int i = 0; i < 5; i++)
            fingerprints.add(i);
        
        return fingerprints;
    }
}