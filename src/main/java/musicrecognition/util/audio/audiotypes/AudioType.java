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
    
    
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    
    /**
     * Extracts audio data from file.<br>
     * Acceptable file formats:
     * <ul>
     *     <li>WAV</li>
     *     <li>MP3</li>
     * </ul>
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
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
    
    /**
     * Extracts samples from audio file. If file stores multi-channeled audio, downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     *
     * @throws IOException
     * */
    public double[] getSamples(File file) throws IOException {
        if (file == null)
            return null;
    
        AudioInputStream audioInputStream = null;
        
        try {
            audioInputStream = getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("unsupported audio file");
            
            if (audioInputStream != null)
                audioInputStream.close();
        }
    
        
        double[] result = null;
        
        try {
            result = getSamples(audioInputStream);
        } catch (IOException e) {
            LOGGER.error("io exception", e);
        } finally {
            if (audioInputStream != null)
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
     * @throws IOException
     * */
    public double[] getSamples(File file, int limit) throws IOException {
        if (file == null)
            return null;
    
        AudioInputStream audioInputStream = null;
        
        try {
            audioInputStream = getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("unsupported audio file");
            
            if (audioInputStream != null)
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
     * @throws IOException
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
            if (audioInputStream != null)
                audioInputStream.close();
        }
    
        return AudioDecoderUtil.getSamples(bytes, sampleSizeInBits, isBigEndian, channels);
    }
    
    /**
     * @throws IOException
     * */
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
