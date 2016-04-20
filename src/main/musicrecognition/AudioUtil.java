package musicrecognition;

import musicrecognition.math.Fourier;
import musicrecognition.math.HannWindow;
import musicrecognition.math.Window;

import java.util.Arrays;

/**
 * Created by anna on 18/03/16.
 */
public class AudioUtil {
    private static final int BITS_IN_BYTE = 8;

    /**
     * Applies FFT to each window in a set.
     * */
    public static double[][] applyFFT(double[][] windows) {
        for (int i = 0; i < windows.length; i++)
            windows[i] = Fourier.transform(windows[i]);

        return windows;
    }

    /**
     * Applies a window function to each window in a set.
     * */
    public static double[][] applyWindow(Window windowFunction, double[][] windows) {
        for (int i = 0; i < windows.length; i++)
            windows[i] = windowFunction.apply(windows[i]);

        return windows;
    }

    /**
     * Divides samples into windows, where size of a window is a power of 2 and close or equal to the sample rate.
     * */
    public static double[][] getWindows(double[] samples, int sampleRate) {
        int logarithm = (int)(Math.log(sampleRate) / Math.log(2));
        int windowSize = (int) Math.pow(2, logarithm);

        int windowCount = samples.length / windowSize;
        double[][] windows = new double[windowCount][windowSize];

        for (int i = 0, j = 0; i < samples.length && j < windowCount; i += windowSize, j++)
            windows[j] = Arrays.copyOfRange(samples, i, i + windowSize);

        return windows;
    }

    /**
     * Gets samples of a given size from a byte array.
     *
     * @param audioData bytes from audio file in PCM (pulse-code modulation) format
     * @param sampleSizeInBits sample size in bits, should be a multiple of 8
     *
     * @throws RuntimeException if sample size is not a multiple of 8
     * */
    public static double[] getSamples(byte[] audioData, int sampleSizeInBits, boolean isBigEndian) {
        if (sampleSizeInBits % 8 != 0)
            throw new RuntimeException("sample size is not a multiple of 8");


        int sampleSizeInBytes = sampleSizeInBits / BITS_IN_BYTE;
        int sampleCount = audioData.length / sampleSizeInBytes;

        double[] samples = new double[sampleCount];
        long[] temp = new long[sampleSizeInBytes];


        // if sample is of size more than 8 bits - combine the bits into one number
        for (int i = 0; i < audioData.length; i += sampleSizeInBytes) {
            if (sampleSizeInBytes == 1)
                samples[i] = (double) audioData[i];
            else {
                for (int j = 0; j < temp.length; j++)
                    temp[j] = audioData[i + j];

                // order depends on encoding
                if (isBigEndian)
                    for (int j = 0, bytesToShift = sampleSizeInBytes - 1; j < sampleSizeInBytes; j++, bytesToShift--)
                        temp[j] <<= BITS_IN_BYTE * (bytesToShift);
                else
                    for (int j = sampleSizeInBytes - 1; j > 0; j--)
                        temp[j] <<= BITS_IN_BYTE * (j - 1);


                for (int j = 0; j < temp.length; j++)
                    samples[i / sampleSizeInBytes] += (double) temp[j];
            }
        }

        return samples;
    }

    /**
     * Average multi-channeled audio samples into mono-channeled.
     * */
    public static double[] downsampleToMono(double[] samples, int channels) {
        if (channels == 1)
            return samples;

        double[] downsampled = new double[samples.length / channels];

        for (int i = 0; i < samples.length; i += channels) {
            int monoIndex = i / channels;

            for (int j = 0; j < channels; j++)
                downsampled[monoIndex] += samples[i + j];

            downsampled[monoIndex] /= channels;
        }

        return downsampled;
    }
}
