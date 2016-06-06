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
import java.io.IOException;


public abstract class AudioType {
    private static final Logger LOGGER = LogManager.getLogger(AudioType.class);

    protected AudioDecoder decoder;


    public AudioType(AudioDecoder decoder) {
        this.decoder = decoder;
    }

    public AudioDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(AudioDecoder decoder) {
        this.decoder = decoder;
    }

    /**
     * Extracts audio data from file.<br>
     * Acceptable file formats:
     * <ul>
     *     <li>WAV</li>
     *     <li>MP3</li>
     * </ul>
     * */
    public abstract AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException;

    public float getSampleRate(File file) throws IOException, UnsupportedAudioFileException {
        if (file == null)
            return 0;


        AudioFormat format = AudioSystem.getAudioFileFormat(file).getFormat();

        return format.getSampleRate();
    }

    public double[] getSamples(File file) throws IOException, UnsupportedAudioFileException {
        return getSamples(file, decoder);
    }

    /**
     * Extracts samples from audio file. If file stores multi-channeled audio, downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     * */
    protected double[] getSamples(File file, AudioDecoder decoder) throws UnsupportedAudioFileException, IOException {
        if (file == null)
            return null;


        AudioInputStream audioInputStream = getAudioInputStream(file);

        AudioFormat format = audioInputStream.getFormat();
        int sampleSizeInBits = format.getSampleSizeInBits();
        int channels = format.getChannels();
        boolean isBigEndian = format.isBigEndian();


        byte[] bytes = null;

        try {
            bytes = IOUtil.inputStreamToByteArray(audioInputStream);
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace());
        } finally {
            audioInputStream.close();
        }

        return decoder.getSamples(bytes, sampleSizeInBits, isBigEndian, channels);
    }
}
