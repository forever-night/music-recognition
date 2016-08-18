package musicrecognition.services.interfaces;

import musicrecognition.util.audio.audiotypes.AudioType;

import java.io.File;
import java.util.Set;


public interface FingerprintService {
    Set<Integer> createFingerprints(File file, AudioType.Type type);
    
    /**
     * Saves fingerprints to database by a given trackId.
     *
     * @return number of fingerprints inserted.
     * */
    int insert(int trackId, Set<Integer> fingerprints);
}
