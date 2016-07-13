package musicrecognition.dao;

import musicrecognition.entities.Track;

import java.util.List;
import java.util.Map;


public interface TrackDao {
    Integer insert(Track track);

    Track getById(int id);

    /**
     * Checks whether a track with such a title and artist is already present id DB.
     * Necessary to use with insert method to ensure that a new object does not violate unique constraint.
     * */
    boolean checkIfExists(Track track);

    /**
     * @param limit number of pairs to return
     * @return list of pairs {track id, match count} with greatest match count for a given list of fingerprints.
     *      Sorted by match_count decreasing.
     * */
    List<Map<String, Integer>> getTracksByFingerprints(int limit, Integer[] fingerprints);
}
