package musicrecognition;

import musicrecognition.config.TestConfig;
import musicrecognition.entities.Track;
import musicrecognition.services.FingerprintService;
import musicrecognition.services.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class MainTest {
    private static final Logger LOGGER = LogManager.getLogger(MainTest.class);
    
    String fileValkiries = getClass().getResource("/ride-of-the-valkiryes.mp3").getFile();
    String fileRequiem = getClass().getResource("/requiem-for-a-dream.mp3").getFile();
    String filePartialValkiries = getClass().getResource("/ride-of-the-valkiries-15s-less-noise.wav").getFile();
    String filePartialRequiem = getClass().getResource("/requiem_for_a_dream_15s_noise.wav").getFile();
    
    @Autowired
    FingerprintService fingerprintService;
    
    @Autowired
    TrackService trackService;
    
    @Test
    public void mainTest() {
        File valkiries = new File(fileValkiries);
        File requiem = new File(fileRequiem);
    
        Track trackValkiries = new Track();
        trackValkiries.setTitle("Ride of the Valkiries");
        trackValkiries.setArtist("Richard Wagner");
        trackValkiries.setYear(1921);
        
        Track trackRequiem = new Track();
        trackRequiem.setTitle("Requiem for a Dream");
        trackRequiem.setArtist("Hanz Zimmer");
        trackRequiem.setYear(2000);
        
        Set<Integer> fingerprints1 = fingerprintService.createFingerprints(valkiries);
        trackValkiries.setFingerprints(fingerprints1);
        
        Set<Integer> fingerprints2 = fingerprintService.createFingerprints(requiem);
        trackRequiem.setFingerprints(fingerprints2);
        
        trackService.insert(trackValkiries);
        trackService.insert(trackRequiem);
        
        LOGGER.info("tracks inserted");
        
        
        File partialValkiries = new File(filePartialValkiries);
        File partialRequiem = new File(filePartialRequiem);
        
        Set<Integer> fingerprintsValkiries = fingerprintService.createFingerprints(partialValkiries);
        Set<Integer> fingerprintsRequiem = fingerprintService.createFingerprints(partialRequiem);
        
        
        LOGGER.info("test1 - ride of the valkiries");
        List<Map<Track, Integer>> matchesValkiries = trackService.getTracksByFingerprints(fingerprintsValkiries);
        
        int fingerprintCount = fingerprintsValkiries.size();
        
        for (Map<Track, Integer> match : matchesValkiries)
            for (Map.Entry<Track, Integer> entry : match.entrySet())
                LOGGER.info(entry.getKey().getTitle() + " -- " + entry.getValue() + "/" + fingerprintCount);
        
            
        LOGGER.info("test2 - requiem for a dream");
        List<Map<Track, Integer>> matchesRequiem = trackService.getTracksByFingerprints(fingerprintsRequiem);
    
        fingerprintCount = fingerprintsValkiries.size();
    
        for (Map<Track, Integer> match : matchesRequiem)
            for (Map.Entry<Track, Integer> entry : match.entrySet())
                LOGGER.info(entry.getKey().getTitle() + " -- " + entry.getValue() + "/" + fingerprintCount);
    }
}
