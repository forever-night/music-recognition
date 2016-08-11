package musicrecognition.util.audio.audiotypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class WavType extends AudioType {
    private static final Logger LOGGER = LogManager.getLogger(WavType.class);
        
    @Override
    public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException{
        if (file == null)
            return null;


        InputStream inputStream = null;
        AudioFileFormat fileFormat = null;

        try {
            inputStream = new FileInputStream(file);
            fileFormat = AudioSystem.getAudioFileFormat(file);
        } catch (IOException e) {
            LOGGER.error("couldn't get audio input stream from file " + file.getName(), e);
        }
        
        if (fileFormat == null)
            return null;


        AudioFormat format = fileFormat.getFormat();
        long length = fileFormat.getFrameLength();

        return new AudioInputStream(inputStream, format, length);
    }
}
