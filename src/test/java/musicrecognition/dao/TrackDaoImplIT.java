package musicrecognition.dao;

import musicrecognition.dao.interfaces.FingerprintDao;
import musicrecognition.dao.interfaces.TrackDao;
import musicrecognition.util.TestUtil;
import musicrecognition.config.TestConfig;
import musicrecognition.entities.Track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class TrackDaoImplIT {
    @Autowired
    TrackDao trackDao;
    
    @Autowired
    FingerprintDao fingerprintDao;

    private Track track;

    @Before
    public void setUp() {
        track = TestUtil.createSimpleTrack();
    }

    @Test
    public void insertTrackDetailsIdGreaterThanZero() {
        int id = trackDao.insert(track);
        Assert.assertNotEquals(0, id);
    }

    @Test
    public void insertTrackDetailsNull() {
        track = new Track();

        Integer id = trackDao.insert(track);
        Assert.assertNull(id);
    }

    @Test
    public void checkIfExistsFalse() {
        boolean exists = trackDao.checkIfExists(track);
        Assert.assertFalse(exists);
    }

    @Test
    public void checkIfExistsTrue() {
        trackDao.insert(track);

        boolean exists = trackDao.checkIfExists(track);
        Assert.assertTrue(exists);
    }

    @Test
    public void getByIdNotFound() {
        track = trackDao.getById(5);

        Assert.assertNull(track);
    }

    @Test
    public void getByIdNotNull() {
        int id = trackDao.insert(track);

        Track actual = trackDao.getById(id);
        Assert.assertEquals(track, actual);
    }
    
    @Test
    public void getTopTrackMatchesCount2() {
        Track[] tracks = TestUtil.createTracksWithFingerprints(3);

        for (Track track : tracks) {
            int id = trackDao.insert(track);
            fingerprintDao.batchInsertFingerprintsById(id, track.getFingerprints().toArray(new Integer[]{}));
        }

        List<Map<String, Integer>> matches = trackDao.getTracksByFingerprints(2, new Integer[]{2, 4, 6});

        Assert.assertNotNull(matches);
        Assert.assertTrue(matches.size() == 2);
    }
    
    @Test
    public void getTopTrackMatchesNoResult() {
        Track[] tracks = TestUtil.createTracksWithFingerprints(3);
    
        for (Track track : tracks) {
            int id = trackDao.insert(track);
            fingerprintDao.batchInsertFingerprintsById(id, track.getFingerprints().toArray(new Integer[]{}));
        }
    
        List<Map<String, Integer>> matches = trackDao.getTracksByFingerprints(2, new Integer[]{10, 120});
        
        Assert.assertNull(matches);
    }
}