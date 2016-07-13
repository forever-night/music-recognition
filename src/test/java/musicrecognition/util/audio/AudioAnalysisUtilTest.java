package musicrecognition.util.audio;

import musicrecognition.util.audio.audiotypes.AudioType;
import musicrecognition.util.audio.audiotypes.Mp3Type;
import musicrecognition.util.audio.audiotypes.WavType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static musicrecognition.util.audio.AudioAnalysisUtil.getSpectrumPeaks;


public class AudioAnalysisUtilTest {
    String file440mp3 = getClass().getResource("/440Hz_44100Hz_16bit_05sec.mp3").getFile();
    String file440wav = getClass().getResource("/440Hz_5s.wav").getFile();
    String file440NoiseWav = getClass().getResource("/440Hz_5s_noise_loud.wav").getFile();

    private AudioType wavType, mp3Type;
    private File mp3440, wav440;
    
    
    @Before
    public void setUp() {
        wavType = new WavType();
        mp3Type = new Mp3Type();

        mp3440 = new File(file440mp3);
        wav440 = new File(file440wav);
    }

    @Test
    public void audioResourceNotNull() {
        Assert.assertNotNull(wav440);
    }

    @Test
    public void getSpectrumPeaksMp3MonoOneFrequency() {
        int[][] actualPeaks = getSpectrumPeaks(mp3Type, mp3440);

        for (int[] peak : actualPeaks)
            Assert.assertEquals(440, peak[0], 1);
    }

    /**
     * Checks whether spectrum peaks for .wav and .mp3 files with the same recording are equal
     * */
    @Test
    public void getSpectrumPeaksWavMp3SameAudio() {
        int[][] actualWav = getSpectrumPeaks(wavType, wav440);
        int[][] actualMp3 = getSpectrumPeaks(mp3Type, mp3440);

        assert actualMp3.length == actualWav.length;


        for (int i = 0; i < actualMp3.length; i++)
            Assert.assertEquals(actualMp3[i][0], actualWav[i][0], 1);
    }

    @Test
    public void getSpectrumPeaks440Hz() {
        int[][] actualPeaks = getSpectrumPeaks(wavType, wav440);

        for (int[] peak : actualPeaks)
            Assert.assertEquals(440, peak[0], 1);
    }

    @Test
    public void getSpectrumPeaks440HzNoise() {
        File wav440Noise = new File(file440NoiseWav);

        int[][] actualPeaksNoNoise = getSpectrumPeaks(wavType, wav440);
        int[][] actualPeaksNoise = getSpectrumPeaks(wavType, wav440Noise);

        for (int i = 0; i < actualPeaksNoise.length; i++)
            Assert.assertEquals(actualPeaksNoNoise[i][0], actualPeaksNoise[i][0], 1);
    }
}
