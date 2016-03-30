package musicrecognition.audiotypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by anna on 21/03/16.
 */
public class Mp3Type implements AudioType {
    private static final Logger LOGGER = LogManager.getLogger(Mp3Type.class);

    public AudioInputStream getAudioInputStream(File file) throws IOException, UnsupportedAudioFileException {
        LOGGER.info(AudioSystem.getAudioFileFormat(file));

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));

        return audioInputStream;
    }

    @Override
    public double[] getSamples(File file) throws IOException, UnsupportedAudioFileException {
        throw new UnsupportedOperationException();
    }
}
