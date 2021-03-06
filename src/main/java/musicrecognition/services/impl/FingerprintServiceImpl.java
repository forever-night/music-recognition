package musicrecognition.services.impl;

import musicrecognition.dao.interfaces.FingerprintDao;
import musicrecognition.services.interfaces.FingerprintService;
import musicrecognition.util.audio.audiotypes.AudioType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static musicrecognition.util.audio.AudioAnalysisUtil.fingerprintAll;
import static musicrecognition.util.audio.AudioAnalysisUtil.getSpectrumPeaks;


@Service
public class FingerprintServiceImpl implements FingerprintService {
    private static final Logger LOGGER = LogManager.getLogger(FingerprintServiceImpl.class);
    
    @Autowired
    FingerprintDao fingerprintDao;
            
    @Override
    public Set<Integer> createFingerprints(File file, AudioType.Type type) throws IOException {
        int[][] peaks;
        
        if (type == null) {
            LOGGER.error("file not supported: " + file.getAbsolutePath());
            return null;
        }
        
        peaks = getSpectrumPeaks(file, type.getAudioType());
        
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
