package musicrecognition.audio;

import musicrecognition.audio.audiotypes.AudioType;
import musicrecognition.audio.audiotypes.Mp3Type;
import musicrecognition.audio.audiotypes.WavType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;


public class AudioAnalyzerTest {
    private static final Logger LOGGER = LogManager.getLogger(AudioAnalyzerTest.class);

    String wav440mono = getClass().getResource("/440Hz_44100Hz_16bit_05sec.wav").getFile();
    String wavStereoString = getClass().getResource("/44100-stereo-16bit.wav").getFile();
    String mp3440mono = getClass().getResource("/440Hz_44100Hz_16bit_05sec.mp3").getFile();

    AudioAnalyzer analyzer;
    AudioDecoder decoder;
    AudioType wavType, mp3Type;
    File wavMono, wavStereo, mp3Mono;


    @Before
    public void setUp() {
        analyzer = new AudioAnalyzer();
        decoder = new AudioDecoder();
        wavType = new WavType(decoder);
        mp3Type = new Mp3Type(decoder);

        wavMono = new File(wav440mono);
        wavStereo = new File(wavStereoString);
        mp3Mono = new File(mp3440mono);
    }

    @Test
    public void audioResourceNotNull() {
        Assert.assertNotNull(wavMono);
    }

    @Test
    public void getSpectrumPeaksWavMonoOneFrequency() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(wavType, wavMono);
        double expectedFrequency = actualPeaks[0][0];

        for (double[] peak : actualPeaks)
            Assert.assertEquals(peak[0], expectedFrequency, 0.1);
    }

    @Test
    public void getSpectrumPeaksMp3MonoOneFrequency() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(mp3Type, mp3Mono);
        double expectedFrequency = actualPeaks[0][0];

        for (double[] peak : actualPeaks)
            Assert.assertEquals(peak[0], expectedFrequency, 0.1);
    }

    /**
     * Checks whether spectrum peaks for .wav and .mp3 files with the same recording are equal
     * */
    @Test
    public void getSpectrumPeaksWavMp3SameAudio() {
        double[][] actualWav = analyzer.getSpectrumPeaks(wavType, wavMono);
        double[][] actualMp3 = analyzer.getSpectrumPeaks(mp3Type, mp3Mono);

        assert actualMp3.length == actualWav.length;


        for (int i = 0; i < actualMp3.length; i++)
            Assert.assertEquals(actualMp3[i][0], actualWav[i][0], 0.1);
    }

    @Test
    public void getSpectrumPeaksWavStereo() {
        double[][] peaks = analyzer.getSpectrumPeaks(wavType, wavStereo);

        for (double[] peak : peaks)
            LOGGER.info(Arrays.toString(peak));
    }
}