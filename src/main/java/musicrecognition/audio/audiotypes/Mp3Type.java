package musicrecognition.audio.audiotypes;

import musicrecognition.audio.AudioDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Mp3Type implements AudioType {
    private static final Logger LOGGER = LogManager.getLogger(Mp3Type.class);

    AudioDecoder decoder;

    public Mp3Type(AudioDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public AudioDecoder getDecoder() {
        return decoder;
    }

    @Override
    public void setDecoder(AudioDecoder decoder) {
        this.decoder = decoder;
    }

    public AudioInputStream getAudioInputStream(File file) throws IOException, UnsupportedAudioFileException {
        LOGGER.info(AudioSystem.getAudioFileFormat(file));

        AudioInputStream audioInputStream =
                AudioSystem.getAudioInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)));

        return audioInputStream;
    }

    @Override
    public double[] getSamples(File file) throws IOException, UnsupportedAudioFileException {
        // TODO enable read from mp3 file
        throw new UnsupportedOperationException();
    }
}
