package musicrecognition.services.impl;

import musicrecognition.dto.TrackMatchDto;
import musicrecognition.services.interfaces.FingerprintService;
import musicrecognition.services.interfaces.IdentificationService;
import musicrecognition.services.interfaces.TrackService;
import musicrecognition.util.audio.audiotypes.AudioType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@Service
public class IdentificationServiceImpl implements IdentificationService {
    @Autowired
    FingerprintService fingerprintService;
    
    @Autowired
    TrackService trackService;
    
    @Override
    public List<TrackMatchDto> identify(File file, AudioType.Type type) throws IOException {
        Set<Integer> fingerprints = fingerprintService.createFingerprints(file, type);
    
        return getTracksByFingerprints(fingerprints);
    }
    
    private List<TrackMatchDto> getTracksByFingerprints(Set<Integer> fingerprints) {
        List<TrackMatchDto> trackMatches = trackService.getTracksByFingerprints(fingerprints);
    
        if (trackMatches == null || trackMatches.isEmpty())
            return null;
        
        for (TrackMatchDto trackMatchDto : trackMatches)
            trackMatchDto.setFingerprintCount(fingerprints.size());
    
        return trackMatches;
    }
}
