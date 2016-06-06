package musicrecognition.audio.audiotypes;

import musicrecognition.audio.AudioDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;


public class WavTypeTest {
    String wav440mono = getClass().getResource("/440Hz_44100Hz_16bit_05sec.wav").getFile();

    AudioDecoder decoder;
    AudioType wavType;

    File file;

    @Before
    public void setUp() {
        decoder = new AudioDecoder();
        wavType = new WavType(decoder);
        file = new File(wav440mono);
    }

    @Test
    public void getSampleRateNot0() throws IOException, UnsupportedAudioFileException {
        int actual = (int) wavType.getSampleRate(file);

        Assert.assertNotEquals(0, actual);
    }

    @Test
    public void getAudioInputStreamNotNull() throws IOException, UnsupportedAudioFileException {
        AudioInputStream actual = wavType.getAudioInputStream(file);

        Assert.assertNotNull(actual);
    }

    @Test
    public void getSamplesNotNull() throws IOException, UnsupportedAudioFileException {
        double[] actual = wavType.getSamples(file);

        Assert.assertNotNull(actual);
    }
}