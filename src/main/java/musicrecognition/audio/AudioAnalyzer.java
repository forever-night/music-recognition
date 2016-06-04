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

        try {
            samples = type.getSamples(file);
        } catch (IOException | UnsupportedAudioFileException e) {
            LOGGER.error(e.getStackTrace());
        }


        // TODO get sample rate from file
        int sampleRate = 44100;
        int frameSize = audioMath.getFrameSize(sampleRate);
        int overlapSize = audioMath.getOverlapSize(frameSize);


        double[][] frames = audioDecoder.getFrames(samples, sampleRate, frameSize, overlapSize);
        frames = audioMath.applyWindowFunction(hannWindow, frames);
        frames = audioMath.applyFFT(frames, sampleRate);

        return audioMath.getSpectrumPeaks(frames, sampleRate, frameSize, overlapSize);
    }
}
