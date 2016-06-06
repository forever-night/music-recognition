package musicrecognition.audio;

import musicrecognition.audio.audiotypes.AudioType;
import musicrecognition.math.HannWindow;
import musicrecognition.math.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class AudioAnalyzer {
    private static final Logger LOGGER = LogManager.getLogger(AudioAnalyzer.class);

    private Window hannWindow;
    private AudioDecoder audioDecoder;
    private AudioMath audioMath;


    public double[][] getSpectrumPeaks(AudioType type, File file) {
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
}
