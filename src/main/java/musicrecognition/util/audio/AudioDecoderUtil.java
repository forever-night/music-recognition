package musicrecognition.util.audio;

import java.util.Arrays;


public class AudioDecoderUtil {
    private static final int BITS_IN_BYTE = 8;
    
    
    /**
     * Average multi-channeled audio samples into mono-channeled.
     * */
    public static double[] toMono(double[] samples, int channels) {
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
     * Gets samples of a given size from a byte array.
     *
     * @param audioData bytes from audio file in PCM (pulse-code modulation) format
     * @param sampleSizeInBits sample size in bits, should be a multiple of 8
     *
     * @throws RuntimeException if sample size is not a multiple of 8
     * */
    public static double[] getSamples(byte[] audioData, int sampleSizeInBits, boolean isBigEndian, int channels) {
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

                // order depends on endianness
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


        if (channels > 1)
            return toMono(samples, channels);
        else
            return samples;
    }
    
    /**
     * Groups samples into frames, where size of a frame is a power of 2 and close or equal to the sample rate.
     *
     * @param sampleRate actual sample rate
     *
     * @throws RuntimeException if frame size or overlap size is not a power of 2
     * @throws RuntimeException if overlap size or overlap size is not a power of 2
     * */
    public static double[][] getFrames(double[] samples, int sampleRate, int frameSize, int overlapSize)
            throws RuntimeException {
        if ((frameSize & (frameSize - 1)) != 0)
            throw new RuntimeException("frame size not a power of 2");

        if ((overlapSize & (overlapSize - 1)) != 0)
            throw new RuntimeException("overlap size not a power of 2");

        if (samples == null)
            return null;


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
}
