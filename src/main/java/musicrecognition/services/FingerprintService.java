package musicrecognition.services;

import musicrecognition.util.audio.audiotypes.AudioType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;


public interface FingerprintService {
    Set<Integer> createFingerprints(File file);
    
    Set<Integer> createFingerprints(InputStream inputStream, AudioType.Type type) throws IOException;
    
    /**
     * Saves fingerprints to database by a given trackId.
     *
     * @return number of fingerprints inserted.
     * */
    int insert(int trackId, Set<Integer> fingerprints);
}
