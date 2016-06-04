package musicrecognition.audio.audiotypes;

import musicrecognition.IOUtil;
import musicrecognition.audio.AudioDecoder;
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


public class WavType implements AudioType {
    private static final Logger LOGGER = LogManager.getLogger(WavType.class);

    AudioDecoder decoder;

    public WavType(AudioDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public AudioDecoder getDecoder() { return decoder; }

    @Override
    public void setDecoder(AudioDecoder decoder) { this.decoder = decoder; }

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

        return decoder.getSamples(bytes, sampleSizeInBits, isBigEndian, channels);
    }
}
