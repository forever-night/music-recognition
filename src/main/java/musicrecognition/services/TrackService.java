package musicrecognition.services;

import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;

import java.util.List;
import java.util.Set;


public interface TrackService {
    Integer insert(Track track);

    Track getById(Integer id);
    
    List<TrackMatch> getTracksByFingerprints(int maxMatches, Set<Integer> fingerprints);
    
    List<TrackMatch> getTracksByFingerprints(Set<Integer> fingerprints);
}
