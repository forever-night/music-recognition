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
    public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException {
        if (file == null)
            return null;


        AudioInputStream inputStream = null;
        AudioFormat inputFormat, decodedFormat;

        try {
            inputStream = AudioSystem.getAudioInputStream(file);
        } catch (IOException e) {
            LOGGER.error("couldn't get audio input stream from file " + file.getName(), e);
        }
        
        if (inputStream == null)
            return null;


        inputFormat = inputStream.getFormat();
        decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                inputFormat.getSampleRate(),
                16,
                inputFormat.getChannels(),
                inputFormat.getChannels() * 2,
                inputFormat.getSampleRate(),
                false);

        return AudioSystem.getAudioInputStream(decodedFormat, inputStream);
    }
}
