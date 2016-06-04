package musicrecognition.audio.audiotypes;

import musicrecognition.AudioUtil;
import musicrecognition.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anna on 21/03/16.
 */
public class WavType implements AudioType {
    private static final Logger LOGGER = LogManager.getLogger(WavType.class);

    @Override
    public AudioInputStream getAudioInputStream(File file) throws IOException, UnsupportedAudioFileException {
        LOGGER.info(AudioSystem.getAudioFileFormat(file));

        InputStream in = new FileInputStream(file);
        AudioFormat format = AudioSystem.getAudioFileFormat(file).getFormat();
        long length = AudioSystem.getAudioFileFormat(file).getFrameLength();

        return new AudioInputStream(in, format, length);
    }

    @Override
    public double[] getSamples(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = null;
        audioInputStream = getAudioInputStream(file);

        AudioFormat format = AudioSystem.getAudioFileFormat(file).getFormat();
        int sampleSizeInBits = format.getSampleSizeInBits();
        int channels = format.getChannels();
        boolean isBigEndian = format.isBigEndian();


        byte[] bytes = IOUtil.inputStreamToByteArray(audioInputStream);
        double[] samples = AudioUtil.getSamples(bytes, sampleSizeInBits, isBigEndian);

        if (channels > 1)
            samples = AudioUtil.downsampleToMono(samples, channels);

        return samples;
    }
}
