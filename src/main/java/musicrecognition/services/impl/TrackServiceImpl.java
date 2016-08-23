package musicrecognition.services.impl;

import musicrecognition.dao.interfaces.TrackDao;
import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;
import musicrecognition.services.interfaces.FingerprintService;
import musicrecognition.services.interfaces.TrackService;
import musicrecognition.util.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TrackServiceImpl implements TrackService {
    private static final Logger LOGGER = LogManager.getLogger(TrackServiceImpl.class);
    
    @Autowired
    TrackDao trackDao;
    
    @Autowired
    FingerprintService fingerprintService;

    @Override
    @Transactional
    public Integer insert(Track track) throws DuplicateKeyException {
        if (track == null || track.getTitle() == null || track.getArtist() == null ||
            track.getFingerprints() == null || track.getFingerprints().isEmpty())
            return null;
        
        Integer id = trackDao.insert(track);
        
        track.setId(id);

        int insertCount = fingerprintService.insert(track.getId(), track.getFingerprints());
        
        if (insertCount > 0)
            LOGGER.info(insertCount + " fingerprints inserted");

        LOGGER.info("track " + id + " saved");
        return track.getId();
    }

    @Override
    public Track getById(Integer id) {
        if (id != null && id > 0)
            return trackDao.getById(id);
        else
            return null;
    }
    
    @Override
    public boolean checkIfExists(Track track) {
        return trackDao.checkIfExists(track);
    }
    
    @Override
    @Transactional
    public List<TrackMatch> getTracksByFingerprints(int maxMatches, Set<Integer> fingerprints) {
        if (fingerprints == null || fingerprints.isEmpty())
            return null;
        
        
        List<Map<String, Integer>> matches =
                trackDao.getTracksByFingerprints(maxMatches, fingerprints.toArray(new Integer[]{}));
        
        if (matches == null || matches.isEmpty())
            return null;
        
        
        List<TrackMatch> trackMatches = new ArrayList<>();
        
        for (Map<String, Integer> match : matches) {
            TrackMatch trackMatch = new TrackMatch();
            
            trackMatch.setTrack(trackDao.getById(match.get("trackId")));
            trackMatch.setMatchCount(match.get("matchCount"));
            trackMatches.add(trackMatch);
        }
        
        return trackMatches;
    }
    
    @Override
    public List<TrackMatch> getTracksByFingerprints(Set<Integer> fingerprints) {
        return getTracksByFingerprints(Global.MAX_FINGERPRINT_MATCHES, fingerprints);
    }
}
