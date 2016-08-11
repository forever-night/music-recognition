package musicrecognition.util.audio.audiotypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class Mp3Type extends AudioType {
    private static final Logger LOGGER = LogManager.getLogger(Mp3Type.class);
        
    @Override
    public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
        if (file == null)
            return null;


        AudioInputStream audioInputStream = null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (IOException e) {
            LOGGER.error("couldn't get audio input stream from file " + file.getName(), e);
        }
        

        return decodeStream(audioInputStream);
    }
    
    private AudioInputStream decodeStream(AudioInputStream inputStream) throws IOException {
        if (inputStream == null)
            return null;
        
        AudioFormat inputFormat = inputStream.getFormat();
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                inputFormat.getSampleRate(),
                16,
                inputFormat.getChannels(),
                inputFormat.getChannels() * 2,
                inputFormat.getSampleRate(),
                false);
    
        AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, inputStream);
        
        return decodedStream;
    }
}
