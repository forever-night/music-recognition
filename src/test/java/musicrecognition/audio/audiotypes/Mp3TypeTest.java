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
import java.util.Arrays;

import static org.junit.Assert.*;


public class Mp3TypeTest {
    String wav440mono = getClass().getResource("/440Hz_44100Hz_16bit_05sec.mp3").getFile();

    AudioDecoder decoder;
    AudioType mp3type;
    File file;


    @Before
    public void setUp() {
        decoder = new AudioDecoder();
        mp3type = new Mp3Type(decoder);
        file = new File(wav440mono);
    }

    @Test
    public void getSampleRateNot0() throws IOException, UnsupportedAudioFileException {
        int actual = (int) mp3type.getSampleRate(file);

        Assert.assertNotEquals(0, actual);
    }

    @Test
    public void getSampleRateIfFileNull() throws IOException, UnsupportedAudioFileException {
        int actual = (int) mp3type.getSampleRate(null);

        Assert.assertEquals(0, actual);
    }

    @Test
    public void getAudioInputStreamNotNull() throws IOException, UnsupportedAudioFileException {
        AudioInputStream actual = mp3type.getAudioInputStream(file);

        Assert.assertNotNull(actual);
    }

    @Test
    public void getSamplesNotNull() throws IOException, UnsupportedAudioFileException {
        double[] actual = mp3type.getSamples(file);

        Assert.assertNotNull(actual);
    }
}