package musicrecognition.util.audio.audiotypes;

import musicrecognition.util.audio.AudioDecoderUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class WavTypeTest {
    String file440wav = getClass().getResource("/440Hz_5s.wav").getFile();

    AudioDecoderUtil decoder;
    AudioType wavType;

    File wav440;

    @Before
    public void setUp() {
        decoder = new AudioDecoderUtil();
        wavType = new WavType();
        wav440 = new File(file440wav);
    }

    @Test
    public void getSampleRateNot0() throws IOException, UnsupportedAudioFileException {
        int actual = (int) wavType.getSampleRate(wav440);

        Assert.assertNotEquals(0, actual);
    }

    @Test
    public void getSampleRateIfFileNull() throws IOException, UnsupportedAudioFileException {
        int actual = (int) wavType.getSampleRate(null);

        Assert.assertEquals(0, actual);
    }

    @Test
    public void getAudioInputStreamNotNull() throws IOException, UnsupportedAudioFileException {
        AudioInputStream actual = wavType.getAudioInputStream(wav440);

        Assert.assertNotNull(actual);
    }

    @Test
    public void getSamplesNotNull() throws IOException, UnsupportedAudioFileException {
        double[] actual = wavType.getSamples(wav440);

        Assert.assertNotNull(actual);
    }
}