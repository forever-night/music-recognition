package musicrecognition.dao;

import musicrecognition.config.TestConfig;
import musicrecognition.dao.interfaces.FingerprintDao;
import musicrecognition.dao.interfaces.TrackDao;
import musicrecognition.entities.Track;
import musicrecognition.util.TestUtil;
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
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class FingerprintDaoImplIT {
    @Autowired
    FingerprintDao fingerprintDao;
    
    @Autowired
    TrackDao trackDao;
    
    private Track track;
    
    @Before
    public void setUp() {
        track = TestUtil.createSimpleTrack();
    }
    
    @Test
    public void batchInsertFingerprintsByIdTrackNotExists() {
        Set<Integer> fingerprints = new HashSet<>();
        
        for (int i = 0; i < 5; i++)
            fingerprints.add(i);
        
        int affected = fingerprintDao.batchInsertFingerprintsById(1, fingerprints.toArray(new Integer[]{}));
        
        Assert.assertEquals(0, affected);
    }
    
    @Test
    public void batchInsertFingerprintsByIdFingerprintsNull() {
        int id = trackDao.insert(track);
        
        int affected = fingerprintDao.batchInsertFingerprintsById(id, null);
        Assert.assertEquals(0, affected);
    }
    
    @Test(expected = NullPointerException.class)
    public void batchInsertFingerprintsByIdElementsNull() {
        Set<Integer> fingerprints = new HashSet<>();
        
        for (int i = 0; i < 5; i++)
            fingerprints.add(null);
        
        int id = trackDao.insert(track);
        
        int affected = fingerprintDao.batchInsertFingerprintsById(id, fingerprints.toArray(new Integer[]{}));
        Assert.assertEquals(0, affected);
    }
    
    @Test
    public void batchInsertFingerprintsByIdNotNull() {
        Track[] tracks = TestUtil.createTracksWithFingerprints(2);
        
        int id1 = trackDao.insert(tracks[0]);
        int id2 = trackDao.insert(tracks[1]);
        
        int affected1 = fingerprintDao
                .batchInsertFingerprintsById(id1, tracks[0].getFingerprints().toArray(new Integer[]{}));
        
        int affected2 = fingerprintDao
                .batchInsertFingerprintsById(id2, tracks[1].getFingerprints().toArray(new Integer[]{}));
        
        Assert.assertEquals(10, affected1 + affected2);
    }
}