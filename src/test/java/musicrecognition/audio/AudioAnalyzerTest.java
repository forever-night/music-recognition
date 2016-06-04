package musicrecognition.audio;

import musicrecognition.audio.audiotypes.AudioType;
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
    String wavStereo = getClass().getResource("/44100-stereo-16bit.wav").getFile();
    AudioAnalyzer analyzer;
    AudioDecoder decoder;
    AudioType wavType;


    @Before
    public void setUp() {
        analyzer = new AudioAnalyzer();
        decoder = new AudioDecoder();
        wavType = new WavType(decoder);
    }


    @Test
    public void audioResourceNotNull() {
        File file = new File(wav440mono);

        Assert.assertNotNull(file);
    }

    @Test
    public void getSpectrumPeaksFor440HzMonoWav() {
        File file = new File(wav440mono);

        double[][] peaks = analyzer.getSpectrumPeaks(wavType, file);
        double frequency = peaks[0][0];

        for (double[] peak : peaks)
            Assert.assertEquals(peak[0], frequency, 0.1);
    }

    @Test
    public void getSpectrumPeaksForStereoWav() {
        // TODO check downsampling to mono
        File file = new File(wavStereo);

        double[][] peaks = analyzer.getSpectrumPeaks(wavType, file);

        for (double[] peak : peaks)
            LOGGER.info(Arrays.toString(peak));
    }
}