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
    
    public static final int MAX_FILE_SIZE = 5242880;       // 5 MB
    
    
    public enum Type {
        MP3(new Mp3Type()),
        WAV(new WavType());
        
        
        AudioType audioType;
        
        Type(AudioType audioType) {
            if (this.getAudioType() == null)
                this.audioType = audioType;
        }
        
        public AudioType getAudioType() {
            return this.audioType;
        }
    }
    
    
    /**
     * Extracts audio data from stream.<br>
     * Acceptable file formats:
     * <ul>
     *     <li>WAV</li>
     *     <li>MP3</li>
     * </ul>
     * */
    public abstract AudioInputStream getAudioInputStream(InputStream inputStream) throws IOException;
    
    /**
     * Extracts audio data from file.<br>
     * Acceptable file formats:
     * <ul>
     *     <li>WAV</li>
     *     <li>MP3</li>
     * </ul>
     * */
    public abstract AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException;
    
    public float getSampleRate(File file) {
        if (file == null)
            return 0;
        
        
        AudioFormat format = null;
        
        try {
            format = AudioSystem.getAudioFileFormat(file).getFormat();
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("unsupported audio file");
        } catch (IOException e) {
            LOGGER.error("io exception");
        }
        
        if (format == null)
            return 0;
        
        return format.getSampleRate();
    }

    public float getSampleRate(InputStream inputStream) {
        if (inputStream == null)
            return 0;
    
    
        AudioFormat format = null;
        
        try {
            format = AudioSystem.getAudioFileFormat(inputStream).getFormat();
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("unsupported audio file");
        } catch (IOException e) {
            LOGGER.error("io exception");
        }
        
        if (format == null)
            return 0;
    
        return format.getSampleRate();
    }
    
    /**
     * Extracts samples from input stream. If stream stores multi-channeled audio, downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     * */
    public double[] getSamples(InputStream inputStream) throws IOException {
        if (inputStream == null)
            return null;
                
        AudioInputStream audioInputStream = getAudioInputStream(inputStream);
        
        double[] result = null;
        
        try {
            result = getSamples(audioInputStream);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            audioInputStream.close();
            inputStream.close();
        }
    
        return result;
    }
    
    /**
     * Extracts samples from a limited number of bytes read from input stream. If stream stores multi-channeled audio,
     * downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     *
     * @param limit maximum number of bytes that can be read from stream
     * */
    public double[] getSamples(InputStream inputStream, int limit) throws IOException {
        if (inputStream == null)
            return null;
    
        AudioInputStream audioInputStream = getAudioInputStream(inputStream);
        
        double[] result = null;
        
        try {
            result = getSamples(audioInputStream, limit);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            audioInputStream.close();
            inputStream.close();
        }
        
        return result;
    }

    /**
     * Extracts samples from audio file. If file stores multi-channeled audio, downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     * */
    public double[] getSamples(File file) throws IOException {
        if (file == null)
            return null;
    
        AudioInputStream audioInputStream = null;
        
        try {
            audioInputStream = getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("unsupported audio file");
            audioInputStream.close();
        }
    
        
        double[] result = null;
        
        try {
            result = getSamples(audioInputStream);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            audioInputStream.close();
        }
        
        return result;
    }
    
    /**
     * Extracts samples from audio file with specified maximum size. If file stores multi-channeled audio,
     * downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     *
     * @param limit maximum number of bytes read from file
     * */
    public double[] getSamples(File file, int limit) throws IOException {
        if (file == null)
            return null;
    
        AudioInputStream audioInputStream = null;
        
        try {
            audioInputStream = getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("unsupported audio file");
            audioInputStream.close();
        }
    
        
        double[] result = null;
        
        try {
            result = getSamples(audioInputStream, limit);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            if (audioInputStream != null)
                audioInputStream.close();
        }
        
        return result;
    }
    
    /**
     * Get samples from an audio input stream with limited length.
     *
     * @param limit maximum size of bytes read from audio input stream
     * */
    private double[] getSamples(AudioInputStream audioInputStream, int limit) throws IOException {
        AudioFormat format = audioInputStream.getFormat();
        int sampleSizeInBits = format.getSampleSizeInBits();
        int channels = format.getChannels();
        boolean isBigEndian = format.isBigEndian();
    
    
        byte[] bytes = null;
    
        try {
            bytes = IOUtil.inputStreamToByteArray(audioInputStream, limit);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            audioInputStream.close();
        }
    
        return AudioDecoderUtil.getSamples(bytes, sampleSizeInBits, isBigEndian, channels);
    }
    
    private double[] getSamples(AudioInputStream audioInputStream) throws IOException {
        AudioFormat format = audioInputStream.getFormat();
        int sampleSizeInBits = format.getSampleSizeInBits();
        int channels = format.getChannels();
        boolean isBigEndian = format.isBigEndian();
    
    
        byte[] bytes = null;
    
        try {
            bytes = IOUtil.inputStreamToByteArray(audioInputStream);
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace());
        }
    
        return AudioDecoderUtil.getSamples(bytes, sampleSizeInBits, isBigEndian, channels);
    }
}
