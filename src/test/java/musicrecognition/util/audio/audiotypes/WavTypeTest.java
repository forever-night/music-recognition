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
    public void getSampleRateStreamNull() {
        int actual = (int) wavType.getSampleRate((InputStream) null);
        
        Assert.assertEquals(0, actual);
    }
    
    @Test
    public void getSampleRateNot0FromFile() throws IOException, UnsupportedAudioFileException {
        int actual = (int) wavType.getSampleRate(file);

        Assert.assertEquals(44100, actual);
    }

    @Test
    public void getSampleRateNot0FromStream() throws FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        int actual = (int) wavType.getSampleRate(inputStream);
        
        Assert.assertEquals(44100, actual);
    }

    @Test
    public void getAudioInputStreamFileNotNull() throws IOException, UnsupportedAudioFileException {
        AudioInputStream actual = wavType.getAudioInputStream(file);

        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getAudioInputStreamFromStreamNotNull() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        AudioInputStream actual = wavType.getAudioInputStream(inputStream);
        
        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesFileNotNull() throws IOException, UnsupportedAudioFileException {
        double[] actual = wavType.getSamples(file);

        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesStreamNotNull() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        double[] actual = wavType.getSamples(inputStream);
        
        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesFromStreamEqualsGetSamplesFromFile() throws IOException, UnsupportedAudioFileException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    
        double[] actualFile = wavType.getSamples(file);
        double[] actualStream = wavType.getSamples(inputStream);
        
        inputStream.close();
        
        Assert.assertArrayEquals(actualFile, actualStream, 1);
    }
    
    @Test
    public void getSamplesFromFileWithLimit() throws IOException, UnsupportedAudioFileException {
        int maxSize = 225280;   // 220 kB, half of the file used
        
        double[] actualX2 = wavType.getSamples(file);
        double[] actual = wavType.getSamples(file, maxSize);
        
        Assert.assertEquals(actualX2.length / 2, actual.length, maxSize / 10);
        Assert.assertArrayEquals(Arrays.copyOfRange(actualX2, 0, actual.length), actual, 1);
    }
    
    @Test
    public void getSamplesFromStreamWithLimit() throws IOException {
        int maxSize = 225280;   // 220 kB, half of the file used
        
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        double[] actualX2 = wavType.getSamples(inputStream);
        
        inputStream = new BufferedInputStream(new FileInputStream(file));
        double[] actual = wavType.getSamples(inputStream, maxSize);
        
        Assert.assertEquals(actualX2.length / 2, actual.length, maxSize / 10);
        Assert.assertArrayEquals(Arrays.copyOfRange(actualX2, 0, actual.length), actual, 1);
    }
}