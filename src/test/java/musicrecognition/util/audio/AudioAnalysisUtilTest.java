package musicrecognition.util.audio;

import musicrecognition.util.audio.audiotypes.AudioType;
import musicrecognition.util.audio.audiotypes.Mp3Type;
import musicrecognition.util.audio.audiotypes.WavType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static musicrecognition.util.audio.AudioAnalysisUtil.*;


public class AudioAnalysisUtilTest {
    private static final Logger LOGGER = LogManager.getLogger(AudioAnalysisUtilTest.class);

    String fileStereoWav = getClass().getResource("/44100-stereo-16bit.wav").getFile();
    String file440mp3 = getClass().getResource("/440Hz_44100Hz_16bit_05sec.mp3").getFile();
    String file440wav = getClass().getResource("/440Hz_5s.wav").getFile();
    String file440NoiseWav = getClass().getResource("/440Hz_5s_noise_loud.wav").getFile();
    String fileMusicWav = getClass().getResource("/ride-of-the-valkiries-15s.wav").getFile();
    String fileMusicLessNoiseWav = getClass().getResource("/ride-of-the-valkiries-15s-less-noise.wav").getFile();
    String fileMusicMoreNoiseWav =  getClass().getResource("/ride-of-the-valkiries-15s-noise.wav").getFile();
    String fileMusicWav2 = getClass().getResource("/ride-of-the-valkiries-15s-2.wav").getFile();

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

//    @Test
    public void getSpectrumPeaksWavStereo() {
        File wavStereo = new File(fileStereoWav);

        int[][] peaks = getSpectrumPeaks(wavType, wavStereo);

        for (int[] peak : peaks)
            LOGGER.info(Arrays.toString(peak));
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

    @Test
    public void getSpectrumPeaksFromMusicComposition() {
        File wavMusic = new File(fileMusicWav);
        File wavMusicLessNoise = new File(fileMusicLessNoiseWav);
        File wavMusicMoreNoise = new File(fileMusicMoreNoiseWav);

        int[][] actualPeaksNoNoise = getSpectrumPeaks(wavType, wavMusic);
        int[][] actualPeaksLessNoise = getSpectrumPeaks(wavType, wavMusicLessNoise);
        int[][] actualPeaksMoreNoise = getSpectrumPeaks(wavType, wavMusicMoreNoise);


        LOGGER.info("no noise --- less noise --- more noise");


        int lessNoiseMatches = 0,
                moreNoiseMatches = 0;

        int[] expected, actualLessNoise, actualMoreNoise;

        for (int i = 0; i < actualPeaksMoreNoise.length; i++) {
            expected = actualPeaksNoNoise[i];
            actualLessNoise = actualPeaksLessNoise[i];
            actualMoreNoise = actualPeaksMoreNoise[i];

            LOGGER.info(Arrays.toString(expected) + " -- " +
                    Arrays.toString(actualLessNoise) + " -- " + Arrays.toString(actualMoreNoise));

            if (actualLessNoise[0] == expected[0])
                lessNoiseMatches++;

            if (actualMoreNoise[0] == expected[0])
                moreNoiseMatches++;
        }

        LOGGER.info("less noise matches: " + lessNoiseMatches);
        LOGGER.info("more noise matches: " + moreNoiseMatches);
    }

    @Test
    public void identifyComposition() {
        File composition1 = new File(fileMusicWav);
        File composition2 = new File(fileMusicWav2);
        File sampleComposition = new File(fileMusicLessNoiseWav);

        identify(composition1, composition2, sampleComposition);
    }

    private void identify(File file1, File file2, File fileSample) {
        int[][] peaks1 = getSpectrumPeaks(wavType, file1);
        int[][] peaks2 = getSpectrumPeaks(wavType, file2);

        Set<Integer> fingerprints1 = fingerprintAll(peaks1);
        Set<Integer> fingerprints2 = fingerprintAll(peaks2);

        LOGGER.info("fingerprints 1");
        LOGGER.info(fingerprints1);
        LOGGER.info("fingerprints 2");
        LOGGER.info(fingerprints2);


        HashMap<Integer, Integer> fprintMap = new HashMap<>();

        for (int fprint : fingerprints1)
            fprintMap.put(fprint, 1);

        for (int fprint : fingerprints2)
            fprintMap.put(fprint, 2);

        LOGGER.info("data saved, " + fprintMap.size() + " fingerprints");


        int[][] samplePeaks = getSpectrumPeaks(wavType, fileSample);
        Set<Integer> sampleFingerprints = fingerprintAll(samplePeaks);

        Integer fprint, count;
        HashMap<Integer, Integer> valueOccurenceCountMap = new HashMap<>();

        for (Integer sampleFingerprint : sampleFingerprints) {
            fprint = fprintMap.get(sampleFingerprint);

            if (fprint == null)
                continue;

            count = valueOccurenceCountMap.get(fprint);

            if (count == null)
                valueOccurenceCountMap.put(fprint, 1);
            else
                valueOccurenceCountMap.put(fprint, count + 1);
        }

        LOGGER.info("matches");
        LOGGER.info("composition ID");
        LOGGER.info(Arrays.toString(valueOccurenceCountMap.keySet().toArray()));
        LOGGER.info("occurence count");
        LOGGER.info(Arrays.toString(valueOccurenceCountMap.values().toArray()));


        int maxCount = 0,
            maxCountId = 0;

        for (Map.Entry<Integer, Integer> entry : valueOccurenceCountMap.entrySet())
            if (entry.getValue() > maxCount) {
                maxCountId = entry.getKey();
                maxCount = entry.getValue();
            }


        LOGGER.info("it is composition #" + maxCountId + " accuracy " + maxCount + "/" + sampleFingerprints.size());
    }
}
