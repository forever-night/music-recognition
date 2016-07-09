package musicrecognition.audio;

import musicrecognition.audio.audiotypes.AudioType;
import musicrecognition.math.HannWindow;
import musicrecognition.math.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class AudioAnalyzer {
    private static final Logger LOGGER = LogManager.getLogger(AudioAnalyzer.class);

    private Window hannWindow;
    private AudioDecoder audioDecoder;
    private AudioMath audioMath;


    /**
     * Extracts the frequency peaks in a music composition. <br>
     * Peak is a <code>int[]</code> where element at index 0 is a frequency, element at index 1 is a point in time,
     * both represented by integers.<br>
     * Typically there are 2-3 peaks per second.
     * */
    public int[][] getSpectrumPeaks(AudioType type, File file) {
        hannWindow = new HannWindow();
        audioMath = new AudioMath();
        audioDecoder = type.getDecoder();


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


        int frameSize = audioMath.getFrameSize(sampleRate);
        int overlapSize = audioMath.getOverlapSize(frameSize);

        LOGGER.info("sample rate " + sampleRate);
        LOGGER.info("frame size " + frameSize);
        LOGGER.info("overlap " + overlapSize);


        double[][] frames = audioDecoder.getFrames(samples, sampleRate, frameSize, overlapSize);
        frames = audioMath.applyWindowFunction(hannWindow, frames);

        LOGGER.info("frames " + frames.length + " x " + frames[0].length);

        frames = audioMath.applyFFT(frames, sampleRate);

        LOGGER.info("frames fft " + frames.length + " x " + frames[0].length);

        return audioMath.getSpectrumPeaks(frames, sampleRate, frameSize, overlapSize);
    }

    /**
     * Calculates a fingerprint of a single peak by hashing both values of the <code>int[]</code>
     * */
    public int fingerprint(int[] peak) {
        return Objects.hash(peak[0], peak[1]);
    }

    /**
     * Calculates fingerprints for all the peaks given.
     * */
    public int[] fingerprintAll(int[][] peaks) {
        int[] fingerprints = new int[peaks.length];

        for (int i = 0; i < fingerprints.length; i++)
            fingerprints[i] = fingerprint(peaks[i]);

        return fingerprints;
    }
}
