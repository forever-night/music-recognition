package musicrecognition.util.audio.audiotypes;

import musicrecognition.util.audio.AudioDecoderUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Arrays;


public class Mp3TypeTest {
    String wav440mono = getClass().getResource("/440Hz_44100Hz_16bit_05sec.mp3").getFile();

    AudioDecoderUtil decoder;
    AudioType mp3type;
    File file;


    @Before
    public void setUp() {
        decoder = new AudioDecoderUtil();
        mp3type = new Mp3Type();
        file = new File(wav440mono);
    }

    @Test
    public void getSampleRateFileNull() {
        int actual = (int) mp3type.getSampleRate((File) null);

        Assert.assertEquals(0, actual);
    }
    
    @Test
    public void getSampleRateStreamNull() {
        int actual = (int) mp3type.getSampleRate((InputStream) null);
        
        Assert.assertEquals(0, actual);
    }
    
    @Test
    public void getSampleRateNot0FromFile() {
        int actual = (int) mp3type.getSampleRate(file);

        Assert.assertEquals(44100, actual);
    }
    
    @Test
    public void getSampleRateNot0FromStream() throws FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        int actual = (int) mp3type.getSampleRate(inputStream);
        
        Assert.assertEquals(44100, actual);
    }
    
    @Test
    public void getAudioInputStreamFileNotNull() throws IOException, UnsupportedAudioFileException {
        AudioInputStream actual = mp3type.getAudioInputStream(file);

        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getAudioInputStreamFromStreamNotNull() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        AudioInputStream actual = mp3type.getAudioInputStream(inputStream);
        
        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesFileNotNull() throws IOException, UnsupportedAudioFileException {
        double[] actual = mp3type.getSamples(file);

        Assert.assertNotNull(actual);
        System.out.println(actual.length);
    }
    
    @Test
    public void getSamplesStreamNotNull() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        double[] actual = mp3type.getSamples(inputStream);
        
        Assert.assertNotNull(actual);
    }
    
    @Test
    public void getSamplesFromStreamEqualsGetSamplesFromFile() throws IOException, UnsupportedAudioFileException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        
        double[] actualFile = mp3type.getSamples(file);
        double[] actualStream = mp3type.getSamples(inputStream);
        
        inputStream.close();
        
        Assert.assertArrayEquals(actualFile, actualStream, 1);
    }
    
    @Test
    public void getSamplesFromFileWithLimit() throws IOException, UnsupportedAudioFileException {
        int maxSize = 225280;   // 220 kB, half of the file used
        
        double[] actualX2 = mp3type.getSamples(file);
        double[] actual = mp3type.getSamples(file, maxSize);
        
        Assert.assertEquals(actualX2.length / 2, actual.length, maxSize / 10);
        Assert.assertArrayEquals(Arrays.copyOfRange(actualX2, 0, actual.length), actual, 1);
    }
    
    @Test
    public void getSamplesFromStreamWithLimit() throws IOException {
        int maxSize = 225280;   // 220 kB, half of the file used
    
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        double[] actualX2 = mp3type.getSamples(inputStream);
    
        inputStream = new BufferedInputStream(new FileInputStream(file));
        double[] actual = mp3type.getSamples(inputStream, maxSize);
        
        Assert.assertEquals(actualX2.length / 2, actual.length, maxSize / 10);
        Assert.assertArrayEquals(Arrays.copyOfRange(actualX2, 0, actual.length), actual, 1);
    }
}