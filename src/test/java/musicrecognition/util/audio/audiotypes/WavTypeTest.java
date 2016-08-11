package musicrecognition.util.audio.audiotypes;

import musicrecognition.util.audio.AudioDecoderUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Arrays;


public class WavTypeTest {
    String file440wav = getClass().getResource("/440Hz_5s.wav").getFile();

    AudioDecoderUtil decoder;
    AudioType wavType;

    File file;

    @Before
    public void setUp() {
        decoder = new AudioDecoderUtil();
        wavType = new WavType();
        file = new File(file440wav);
    }

    @Test
    public void getSampleRateFileNull() throws IOException, UnsupportedAudioFileException {
        int actual = (int) wavType.getSampleRate((File) null);

        Assert.assertEquals(0, actual);
    }
    
    @Test
    public void getSampleRateNot0FromFile() throws IOException, UnsupportedAudioFileException {
        int actual = (int) wavType.getSampleRate(file);

        Assert.assertEquals(44100, actual);
    }

    @Test
    public void getAudioInputStreamFileNotNull() throws IOException, UnsupportedAudioFileException {
        AudioInputStream actual = wavType.getAudioInputStream(file);

        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesFileNotNull() throws IOException, UnsupportedAudioFileException {
        double[] actual = wavType.getSamples(file);

        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesFromFileWithLimit() throws IOException, UnsupportedAudioFileException {
        int maxSize = 225280;   // 220 kB, half of the file used
        
        double[] actualX2 = wavType.getSamples(file);
        double[] actual = wavType.getSamples(file, maxSize);
        
        Assert.assertEquals(actualX2.length / 2, actual.length, maxSize / 10);
        Assert.assertArrayEquals(Arrays.copyOfRange(actualX2, 0, actual.length), actual, 1);
    }
}