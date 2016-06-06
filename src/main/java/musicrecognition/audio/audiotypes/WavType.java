package musicrecognition.audio.audiotypes;

import musicrecognition.audio.AudioDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.*;


public class WavType extends AudioType {
    private static final Logger LOGGER = LogManager.getLogger(WavType.class);

    public WavType(AudioDecoder decoder) {
        super(decoder);
    }

    @Override
    public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException{
        if (file == null)
            return null;


        InputStream in = null;
        AudioFileFormat fileFormat = null;

        try {
            in = new FileInputStream(file);
            fileFormat = AudioSystem.getAudioFileFormat(file);
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace());
        }


        AudioFormat format = fileFormat.getFormat();
        long length = fileFormat.getFrameLength();

        return new AudioInputStream(in, format, length);
    }

    @Override
    public float getSampleRate(File file) throws IOException, UnsupportedAudioFileException {
        AudioFormat format = AudioSystem.getAudioFileFormat(file).getFormat();

        return format.getSampleRate();
    }

    @Override
    public double[] getSamples(File file) throws IOException, UnsupportedAudioFileException {
        return getSamples(file, decoder);
    }
}
