package musicrecognition.util.audio.audiotypes;

import musicrecognition.util.IOUtil;
import musicrecognition.util.audio.AudioDecoderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;


public abstract class AudioType {
    private static final Logger LOGGER = LogManager.getLogger(AudioType.class);
    private static final int MAX_FILE_SIZE = 5242880;       // in bytes

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

    /**
     * Extracts samples from audio file. If file stores multi-channeled audio, downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     * If input file size exceeds <code>MAX_FILE_SIZE</code>, it is reduced to max size by cutting off the exceeding
     * bytes.
     * */
    public double[] getSamples(File file) throws UnsupportedAudioFileException, IOException {
        if (file == null)
            return null;

        
        if (file.length() > MAX_FILE_SIZE)
            file = reduceFileSize(file);

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

        return AudioDecoderUtil.getSamples(bytes, sampleSizeInBits, isBigEndian, channels);
    }
    
    /**
     * Reduces file size to <code>MAX_FILE_SIZE</code>
     * */
    private File reduceFileSize(File file) throws IOException {
        byte buffer[] = new byte[MAX_FILE_SIZE];
    
        BufferedInputStream bi = new BufferedInputStream(new FileInputStream(file));
    
        try {
            bi.read(buffer, 0, buffer.length);
        } catch (IOException e) {
            LOGGER.info("error reading into buffer");
        } finally {
            bi.close();
        }
        
        BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(file));
        
        try {
            bo.write(buffer, 0, buffer.length);
            bo.flush();
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            bo.close();
        }
        
        LOGGER.info("file reduced");
        return file;
    }
}
