package musicrecognition.audio;

import musicrecognition.math.Fourier;
import musicrecognition.math.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class AudioMath {
    private static final Logger LOGGER = LogManager.getLogger(AudioMath.class);

    private static final int FREQUENCY_LOW = 32,
                            FREQUENCY_HIGH = 16384;


    /**
     * Calculates a frame size, which is the largest number with a power of 2, less or equal to the given sample
     * rate.
     *
     * @return number of samples per frame
     * */
    public int getFrameSize(int sampleRate) {
        double logarithm = Math.log(sampleRate) / Math.log(2);
        logarithm = Math.round(logarithm);

        return (int) Math.pow(2.0, logarithm);
    }

    /**
     * @return a number of samples in a frame that will overlap, according to given percentage
     * */
    public int getOverlapSize(int frameSize, double percentage) {
        return (int) (frameSize * percentage / 100);
    }

    /**
     * @return 50% of a frame size that will overlap
     * */
    public int getOverlapSize(int frameSize) {
        return getOverlapSize(frameSize, 50);
    }

    /**
     * @return resolution rounded to 1 digit after zero.
     * */
    public double getFrequencyResolution(int sampleRate, int frameSize) {
        double resolution = sampleRate / frameSize;

        resolution *= 10;
        resolution = Math.round(resolution);
        resolution /= 10;

        return resolution;
    }

    /**
     * Applies a window function to each window in a set.
     * */
    public double[][] applyWindowFunction(Window windowFunction, double[][] frames) {
        if (frames == null)
            return null;

        for (int i = 0; i < frames.length; i++)
            frames[i] = windowFunction.apply(frames[i]);

        return frames;
    }

    /**
     * Applies FFT to each window in a set and outputs amplitudes for frequencies in the range of
     * <code>AudioUtil.FREQUENCY_LOW</code> - <code>AudioUtil.FREQUENCY_HIGH</code> Hz.
     * */
    public double[][] applyFFT(double[][] frames, int sampleRate) {
        if (frames == null)
            return null;


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
     * Finds peak frequencies and their positions on the time axis from a set of frames.<br>
     * <b>Peak</b> is represented as <code>double[]</code>, where element at index 0 represents frequency in Hz and
     * element at index 1 represents time in seconds.
     * */
    public double[][] getSpectrumPeaks(double[][] frames, int sampleRate, int frameSize, int overlapSize) {
        if (frames == null)
            return null;


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
     * Calculates position of a sample on the time axis (in seconds).
     * */
    private double getSampleAtTimeAxis(int index, int sampleRate) {
        return index  / sampleRate;
    }

    private Double[] toDoubleArray(double[] input) {
        Double[] output = new Double[input.length];

        for (int i = 0; i < input.length; i++)
            output[i] = input[i];

        return output;
    }
}
