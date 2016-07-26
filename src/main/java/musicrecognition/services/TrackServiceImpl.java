package musicrecognition.services;

import musicrecognition.dao.TrackDao;
import musicrecognition.entities.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TrackServiceImpl implements TrackService {
    private static final Logger LOGGER = LogManager.getLogger(TrackServiceImpl.class);
    private static final int MAX_MATCHES = 5;

    @Autowired
    TrackDao trackDao;
    
    @Autowired
    FingerprintService fingerprintService;

    @Override
    @Transactional
    public Integer insert(Track track){
        if (track != null && track.getTitle() != null && track.getArtist() != null &&
            track.getFingerprints() != null && !track.getFingerprints().isEmpty()) {
            
            boolean exists = trackDao.checkIfExists(track);

            if (exists) {
                LOGGER.error("track already exists: " + track.getArtist() + " - " + track.getTitle());
            } else {
                int id = trackDao.insert(track);
                track.setId(id);

                int insertCount = fingerprintService.insert(track.getId(), track.getFingerprints());
                
                if (insertCount > 0)
                    LOGGER.info(insertCount + " fingerprints inserted");

                LOGGER.info("track " + id + " saved");
                return track.getId();
            }
        }

        return null;
    }

    @Override
    public Track getById(Integer id) {
        if (id != null && id > 0)
            return trackDao.getById(id);
        else
            return null;
    }
    
    @Override
    @Transactional
    public List<Map<Track, Integer>> getTracksByFingerprints(Set<Integer> fingerprints) {
        if (fingerprints == null || fingerprints.isEmpty())
            return null;
        
        
        List<Map<String, Integer>> matches =
                trackDao.getTracksByFingerprints(
                        MAX_MATCHES,
                        fingerprints.toArray(new Integer[]{}));
                
        
        List<Map<Track, Integer>> trackMatches = new ArrayList<>();
        
        for (Map<String, Integer> match : matches) {
            Track track = trackDao.getById(match.get("trackId"));
            int matchCount = match.get("matchCount");
            
            trackMatches.add(Collections.singletonMap(track, matchCount));
        }
        
        return trackMatches;
    }
}
