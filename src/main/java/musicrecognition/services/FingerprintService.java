package musicrecognition.services;

import java.io.File;
import java.util.Set;


public interface FingerprintService {
    Set<Integer> createFingerprints(File file);
    
    /**
     * Saves fingerprints to database by a given trackId.
     *
     * @return number of fingerprints inserted.
     * */
    int insert(int trackId, Set<Integer> fingerprints);
}
