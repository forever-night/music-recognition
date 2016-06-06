package musicrecognition.audio.audiotypes;

import musicrecognition.audio.AudioDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Mp3Type extends AudioType {
    private static final Logger LOGGER = LogManager.getLogger(Mp3Type.class);

    public Mp3Type(AudioDecoder decoder) {
        super(decoder);
    }

    @Override
    public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException {
        if (file == null)
            return null;


        AudioInputStream inputStream = null;
        AudioFormat inputFormat, decodedFormat;

        try {
            inputStream = AudioSystem.getAudioInputStream(file);
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace());
        }


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
