package musicrecognition.services;

import musicrecognition.dao.FingerprintDao;
import musicrecognition.util.IOUtil;
import musicrecognition.util.audio.audiotypes.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;

import static musicrecognition.util.audio.AudioAnalysisUtil.fingerprintAll;
import static musicrecognition.util.audio.AudioAnalysisUtil.getSpectrumPeaks;


@Service
public class FingerprintServiceImpl implements FingerprintService {
    private static final Logger LOGGER = LogManager.getLogger(FingerprintServiceImpl.class);
    
    @Autowired
    FingerprintDao fingerprintDao;
        
    AudioType mp3Type, wavType;
    
    public FingerprintServiceImpl() {
        mp3Type = new Mp3Type();
        wavType = new WavType();
    }
    
    @Override
    public Set<Integer> createFingerprints(File file) {
        String extension = IOUtil.getExtension(file);
    
        int[][] peaks;
    
        if (extension.equalsIgnoreCase("mp3"))
            peaks = getSpectrumPeaks(mp3Type, file);
        else if (extension.equalsIgnoreCase("wav"))
            peaks = getSpectrumPeaks(wavType, file);
        else {
            LOGGER.error("file extension not supported: " + extension);
            return null;
        }
        
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
