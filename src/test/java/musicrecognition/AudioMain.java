package musicrecognition;

import musicrecognition.audiotypes.AudioType;
import musicrecognition.audiotypes.WavType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by anna on 21/03/16.
 */
public class AudioMain {
    private static final Logger LOGGER = LogManager.getLogger(AudioMain.class);

    public static void main(String[] args) {
        AudioType wavType = new WavType();

        File hz440wav = new File("/mnt/sda8/dev/java/music-recognition/src/main/resources" +
                "/440Hz_44100Hz_16bit_05sec.wav");

        File stereoWav = new File("/mnt/sda8/dev/java/music-recognition/src/main/resources/44100-stereo-16bit.wav");

        double[] samples = getSamplesTest(hz440wav, wavType);
        double[][] windows = getWindowsTest(samples, 44100);
//        LOGGER.info(Arrays.toString(samples));
    }

    private static double[] getSamplesTest(File file, AudioType audioType) {
//        LOGGER.info(file.getName());

        double[] decoded = null;

        try {
            decoded = audioType.getSamples(file);
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

//        LOGGER.info("byte array length = " + decoded.length);

        return decoded;
    }

    private static double[][] getWindowsTest(double[] samples, int sampleRate) {
        return AudioUtil.getFrames(samples, sampleRate);
    }
}
