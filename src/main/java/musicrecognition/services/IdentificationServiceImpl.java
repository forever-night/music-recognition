package musicrecognition.services;

import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;
import musicrecognition.util.audio.audiotypes.AudioType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class IdentificationServiceImpl implements IdentificationService {
    @Autowired
    FingerprintService fingerprintService;
    
    @Autowired
    TrackService trackService;
    
    @Override
    public List<TrackMatch> identify(File file) {
        Set<Integer> fingerprints = fingerprintService.createFingerprints(file);
    
        return getTracksByFingerprints(fingerprints);
    }
    
    @Override
    public List<TrackMatch> identify(InputStream inputStream, AudioType.Type type) throws IOException {
        Set<Integer> fingerprints = fingerprintService.createFingerprints(inputStream, type);
        
        return getTracksByFingerprints(fingerprints);
    }
    
    private List<TrackMatch> getTracksByFingerprints(Set<Integer> fingerprints) {
        List<TrackMatch> trackMatches = trackService.getTracksByFingerprints(fingerprints);
    
        for (TrackMatch trackMatch : trackMatches)
            trackMatch.setFingerprintCount(fingerprints.size());
    
        return trackMatches;
    }
}
