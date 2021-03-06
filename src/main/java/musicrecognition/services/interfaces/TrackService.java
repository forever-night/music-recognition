package musicrecognition.services.interfaces;

import musicrecognition.dto.TrackMatchDto;
import musicrecognition.entities.Track;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Set;


public interface TrackService {
    /**
     * @return <ul>
     *     <li>id generated by database</li>
     *     <li>null if track is empty or one of the not null fields is empty</li>
     * </ul>
     * @throws DuplicateKeyException if entity violates unique constraint
     * */
    Integer insert(Track track) throws DuplicateKeyException;

    Track getById(Integer id);
    
    boolean checkIfExists(Track track);
    
    List<TrackMatchDto> getTracksByFingerprints(int maxMatches, Set<Integer> fingerprints);
    
    List<TrackMatchDto> getTracksByFingerprints(Set<Integer> fingerprints);
}
