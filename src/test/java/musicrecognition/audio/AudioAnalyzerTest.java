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

    String wavStereoString = getClass().getResource("/44100-stereo-16bit.wav").getFile();
    String mp3440mono = getClass().getResource("/440Hz_44100Hz_16bit_05sec.mp3").getFile();

    String file220wav = getClass().getResource("/220Hz_5s.wav").getFile();
    String file440wav = getClass().getResource("/440Hz_5s.wav").getFile();
    String file660wav = getClass().getResource("/660Hz_5s.wav").getFile();


    AudioAnalyzer analyzer;
    AudioDecoder decoder;
    AudioType wavType, mp3Type;
    File wavStereo, mp3Mono, wav220, wav440, wav660;


    @Before
    public void setUp() {
        analyzer = new AudioAnalyzer();
        decoder = new AudioDecoder();
        wavType = new WavType(decoder);
        mp3Type = new Mp3Type(decoder);

        wavStereo = new File(wavStereoString);
        mp3Mono = new File(mp3440mono);
        wav220 = new File(file220wav);
        wav440 = new File(file440wav);
        wav660 = new File(file660wav);
    }

    @Test
    public void audioResourceNotNull() {
        Assert.assertNotNull(wav440);
    }

    @Test
    public void getSpectrumPeaksWavMonoOneFrequency() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(wavType, wav440);
        double expectedFrequency = actualPeaks[0][0];

        for (double[] peak : actualPeaks)
            Assert.assertEquals(peak[0], expectedFrequency, 0.1);
    }

    @Test
    public void getSpectrumPeaksMp3MonoOneFrequency() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(mp3Type, mp3Mono);

        for (double[] peak : actualPeaks)
            Assert.assertEquals(440, peak[0], 2);
    }

    /**
     * Checks whether spectrum peaks for .wav and .mp3 files with the same recording are equal
     * */
    @Test
    public void getSpectrumPeaksWavMp3SameAudio() {
        double[][] actualWav = analyzer.getSpectrumPeaks(wavType, wav440);
        double[][] actualMp3 = analyzer.getSpectrumPeaks(mp3Type, mp3Mono);

        assert actualMp3.length == actualWav.length;


        for (int i = 0; i < actualMp3.length; i++)
            Assert.assertEquals(actualMp3[i][0], actualWav[i][0], 2);
    }

    @Test
    public void getSpectrumPeaksWavStereo() {
        double[][] peaks = analyzer.getSpectrumPeaks(wavType, wavStereo);

        for (double[] peak : peaks)
            LOGGER.info(Arrays.toString(peak));
    }

    @Test
    public void getSpectrumPeaks220Hz() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(wavType, wav220);

        for (double[] peak : actualPeaks)
            Assert.assertEquals(220, peak[0], 2);
    }

    @Test
    public void getSpectrumPeaks440Hz() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(wavType, wav440);

        for (double[] peak : actualPeaks)
            Assert.assertEquals(440, peak[0], 2);
    }

    @Test
    public void getSpectrumPeaks660Hz() {
        double[][] actualPeaks = analyzer.getSpectrumPeaks(wavType, wav660);

        for (double[] peak : actualPeaks)
            Assert.assertEquals(660, peak[0], 2);
    }
}