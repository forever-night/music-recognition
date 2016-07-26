package musicrecognition.services;

import musicrecognition.config.TestConfig;
import musicrecognition.entities.Track;
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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class FingerprintServiceImplIT {
    @Autowired
    FingerprintService fingerprintService;
    
    @Autowired
    TrackService trackService;
    
    Track track;
    
    @Before
    public void setUp() {
        track = new Track();
        track.setTitle("test");
        track.setArtist("test");
        track.setAlbumTitle("test");
        track.setYear(3000);
    }
    
    @Test
    public void insertTrackId0() {
        int actual = fingerprintService.insert(0, new HashSet<>());
    
        Assert.assertEquals(0, actual);
    }
    
    @Test
    public void insertFingerprintsNull() {
        Integer actual = trackService.insert(track);
        
        Assert.assertNull(actual);
    }
    
    @Test
    public void insertFingerprintsEmpty() {
        track.setFingerprints(new HashSet<>());
        
        Integer actual = trackService.insert(track);
        
        Assert.assertNull(actual);
    }
}