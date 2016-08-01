package musicrecognition.services;

import musicrecognition.dao.FingerprintDao;
import musicrecognition.util.IOUtil;
import musicrecognition.util.audio.audiotypes.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static musicrecognition.util.audio.AudioAnalysisUtil.fingerprintAll;
import static musicrecognition.util.audio.AudioAnalysisUtil.getSpectrumPeaks;


@Service
public class FingerprintServiceImpl implements FingerprintService {
    private static final Logger LOGGER = LogManager.getLogger(FingerprintServiceImpl.class);
    
    @Autowired
    FingerprintDao fingerprintDao;
            
    @Override
    public Set<Integer> createFingerprints(File file) {
        String extension = IOUtil.getExtension(file);
    
        int[][] peaks;
        
        AudioType.Type audioType;
    
        if (extension.equalsIgnoreCase("mp3"))
            audioType = AudioType.Type.MP3;
        else if (extension.equalsIgnoreCase("wav"))
            audioType = AudioType.Type.WAV;
        else {
            LOGGER.error("file extension not supported: " + extension);
            return null;
        }
    
        peaks = getSpectrumPeaks(audioType.getAudioType(), file);
        
        return fingerprintAll(peaks);
    }
    
    @Override
    public Set<Integer> createFingerprints(InputStream inputStream, AudioType.Type type) throws IOException {
        int[][] peaks = getSpectrumPeaks(type.getAudioType(), inputStream);
        return fingerprintAll(peaks);
    }
    
    @Override
    public int insert(int trackId, Set<Integer> fingerprints) {
        if (trackId > 0 && fingerprints != null && fingerprints.size() > 0)
            return fingerprintDao.batchInsertFingerprintsById(trackId, fingerprints.toArray(new Integer[]{}));
        else
            return 0;
    }
}
