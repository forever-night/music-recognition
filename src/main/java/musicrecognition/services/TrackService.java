package musicrecognition.services;

import musicrecognition.entities.Track;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface TrackService {
    Integer insert(Track track);

    Track getById(Integer id);
    
    List<Map<Track, Integer>> getTracksByFingerprints(Set<Integer> fingerprints);
}
