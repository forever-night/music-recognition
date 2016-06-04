package musicrecognition;

import musicrecognition.math.Fourier;
import musicrecognition.math.Window;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class AudioUtil {
    public static final int BITS_IN_BYTE = 8,
                            FREQUENCY_LOW = 32,
                            FREQUENCY_HIGH = 16384;

    /**
     * Finds peak frequencies and their positions on the time axis from a set of frames.<br>
     * <b>Peak</b> is a <code>double[]</code>, where element at index 0 represents frequency in Hz and element at index 1
     * represents time in seconds.
     * */
    public static double[][] getSpectrumPeaks(double[][] frames, int sampleRate, int frameSize, int overlapSize) {
        double[][] peaks = new double[frames.length][2];
        double resolution = getFrequencyResolution(sampleRate, frameSize);


        int frameMinusOverlap = frameSize - overlapSize;
        Double[] frame;
        List<Double> temp;

        for (int i = 0; i < frames.length; i++) {
            frame = toDoubleArray(frames[i]);

            temp = Arrays.asList(frame);

            double peakPower = Collections.max(temp);
            double peakIndex = temp.indexOf(peakPower);

            peaks[i][0] = peakIndex * resolution;
            peaks[i][1] = getSampleAtTimeAxis(i * frameMinusOverlap, sampleRate);
        }

        return peaks;
    }

    /**
     * Applies FFT to each window in a set and outputs frequencies in the rangle of 32 - 16384 Hz.
     * */
    public static double[][] applyFFT(double[][] frames, int sampleRate) {
        for (int i = 0; i < frames.length; i++)
            frames[i] = Fourier.transform(frames[i]);


        int frameSize = frames[0].length,
                startIndex,
                endIndex;

        double resolution = getFrequencyResolution(sampleRate, frameSize);

        startIndex = (int) (FREQUENCY_LOW / resolution);
        startIndex = startIndex == 0 ? 1 : startIndex;      // frequency 0Hz has amplitude 0, so we don't need it

        endIndex = (int) (FREQUENCY_HIGH / resolution);


        double[] temp;

        for (int i = 0; i < frames.length; i++) {
            temp = Arrays.copyOfRange(frames[i], startIndex, endIndex);
            frames[i] = temp;
        }

        return frames;
    }

    /**
     * Applies a window function to each window in a set.
     * */
    public static double[][] applyWindowFunction(Window windowFunction, double[][] frames) {
        for (int i = 0; i < frames.length; i++)
            frames[i] = windowFunction.apply(frames[i]);

        return frames;
    }

    /**
     * Divides samples into frames, where size of a frame is a power of 2 and close or equal to the sample rate.
     *
     * @param frameSize desired frame size in number of samples, has to be a power of 2
     * @param overlapSize desired overlap size in number of samples, has to be a power of 2
     *
     * @throws RuntimeException if frame size or overlap size is not a power of 2
     * */
    public static double[][] getFrames(double[] samples, int sampleRate, int frameSize, int overlapSize)
        throws RuntimeException {
        if ((frameSize & (frameSize - 1)) != 0)
            throw new RuntimeException("frame size not power of 2");

        if ((overlapSize & (overlapSize - 1)) != 0)
            throw new RuntimeException("overlap size not power of 2");


        int frameMinusOverlap = frameSize - overlapSize;
        int frameCount = samples.length / frameMinusOverlap;

        double[][] frames = new double[frameCount][frameSize];


        int frameStart = 0,
            frameEnd = frameSize;

        for (int i = 0; i < frameCount; i++) {
            frames[i] = Arrays.copyOfRange(samples, frameStart, frameEnd);

            frameStart += frameMinusOverlap;
            frameEnd += frameMinusOverlap;
        }

        return frames;
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
        if (sampleSizeInBits % BITS_IN_BYTE != 0)
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

    /**
     * @return resolution rounded to 1 digit after zero.
     * */
    public static double getFrequencyResolution(int sampleRate, int frameSize) {
        double resolution = sampleRate / frameSize;

        resolution *= 10;
        resolution = Math.round(resolution);
        resolution /= 10;

        return resolution;
    }

    /**
     * Calculates position of a sample on the time axis (in seconds).
     * */
    private static double getSampleAtTimeAxis(int index, int sampleRate) {
        return index  / sampleRate;
    }

    private static Double[] toDoubleArray(double[] input) {
        Double[] output = new Double[input.length];

        for (int i = 0; i < input.length; i++)
            output[i] = input[i];

        return output;
    }
}
