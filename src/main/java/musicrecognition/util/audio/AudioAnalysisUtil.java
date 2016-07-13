package musicrecognition.util.audio;

import musicrecognition.util.audio.audiotypes.AudioType;
import musicrecognition.util.math.HannWindow;
import musicrecognition.util.math.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static musicrecognition.util.audio.AudioDecoderUtil.getFrames;
import static musicrecognition.util.audio.AudioMathUtil.*;


public class AudioAnalysisUtil {
    private static final Logger LOGGER = LogManager.getLogger(AudioAnalysisUtil.class);
    private static final int MAX_FINGERPRINT_NEIGHBORS = 10;
    
    private static Window hannWindow;
    
    
    /**
     * Extracts the frequency peaks in a music composition. <br>
     * Peak is a <code>int[]</code> where element at index 0 is a frequency, element at index 1 is a point in time,
     * both represented by integers.<br>
     * Typically there are 2-3 peaks per second.
     * */
    public static int[][] getSpectrumPeaks(AudioType type, File file) {
        hannWindow = new HannWindow();


        double[] samples = null;
        int sampleRate = 0;

        try {
            samples = type.getSamples(file);
            sampleRate = (int) type.getSampleRate(file);
        } catch (IOException | UnsupportedAudioFileException e) {
            LOGGER.error(e.getStackTrace());
        }

        if (sampleRate == 0 || samples == null)
            return null;


        int frameSize = getFrameSize(sampleRate);
        int overlapSize = getOverlapSize(frameSize);

        LOGGER.info("sample rate " + sampleRate);
        LOGGER.info("frame size " + frameSize);
        LOGGER.info("overlap " + overlapSize);


        double[][] frames = getFrames(samples, sampleRate, frameSize, overlapSize);
        frames = applyWindowFunction(hannWindow, frames);

        LOGGER.info("frames " + frames.length + " x " + frames[0].length);

        frames = applyFFT(frames, sampleRate);

        LOGGER.info("frames fft " + frames.length + " x " + frames[0].length);

        return AudioMathUtil.getSpectrumPeaks(frames, sampleRate, frameSize, overlapSize);
    }
        
    public static Set<Integer> fingerprintAll(int[][] peaks) {
        if (peaks == null || peaks.length == 0)
            return null;
        
        
        Set<Integer> fingerprints = new HashSet<>();
    
        for (int i = 0; i < peaks.length; i++) {
            if (i == peaks.length - MAX_FINGERPRINT_NEIGHBORS)
                break;
            
            int[] current = peaks[i];
            int[] next;
            int hash;
    
            for (int j = 1; j <= MAX_FINGERPRINT_NEIGHBORS; j++) {
                next = peaks[i + j];
                hash = Objects.hash(current[0], next[0], next[1] - current[1]);
                
                fingerprints.add(hash);
            }
        }

        return fingerprints;
    }
}
