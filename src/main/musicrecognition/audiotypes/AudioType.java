package musicrecognition.audiotypes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by anna on 21/03/16.
 */
public interface AudioType {
    /**
     * Extracts audio data from file.<br>
     * Acceptable file formats:
     * <ul>
     *     <li>WAV</li>
     *     <li>MP3</li>
     * </ul>
     * */
    AudioInputStream getAudioInputStream(File file) throws IOException, UnsupportedAudioFileException;

    /**
     * Extracts samples from audio file. If file stores multi-channeled audio, downsamples it to mono-channeled.<br>
     * Acceptable audio bit rate - multiples of 8.
     * */
    double[] getSamples(File file) throws IOException, UnsupportedAudioFileException;
}
