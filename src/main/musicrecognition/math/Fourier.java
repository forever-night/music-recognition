package musicrecognition.math;

import static musicrecognition.math.Complex.*;

/**
 * Created by anna on 23/03/16.
 */
public class Fourier {
    /**
     * Computes Fast Fourier Transform using <i>Cooley Tukey radix-2 DIT algorithm</i>
     * and returns <b>magnitudes</b> of output numbers.
     *
     * Input length should be a power of 2.
     *
     * @param input array of real numbers
     * @return array of magnitudes of complex output numbers
     * @throws  RuntimeException    if <code>input.length</code> is not a power of 2
     * */
    public static double[] fft(double[] input) {
        int length = input.length;

        if ((length & (length - 1)) != 0)
            throw new RuntimeException("input length not power of 2");


        double[][] complex = toComplex(input);
        complex = fft(complex);

        for (int i = 0; i < length; i++)
            input[i] = complex[i][0];

        return input;
    }

    /**
     * Computes Fast Fourier Transform  using <i>Cooley Tukey radix-2 DIT algorithm</i>.<br>
     *
     * Every complex number is represented as <code>double[]{magnitude, theta}</code>.<br>
     * Input length should be a power of 2.
     *
     * @param input array of complex numbers
     * @return array of complex numbers
     * @throws  RuntimeException    if <code>input.length</code> is not a power of 2
     * */
    private static double[][] fft(double[][] input) {
        int length = input.length;

        if ((length & (length - 1)) != 0)
            throw new RuntimeException("input length not power of 2");


        if (length > 1) {
            double[][] even = new double[length / 2][2];
            double[][] odd = new double[length / 2][2];

            for (int i = 0; i < length / 2; i++) {
                even[i] = new double[] {input[2 * i][0], input[2 * i][1]};
                odd[i] = new double[] {input[2 * i + 1][0], input[2 * i + 1][1]};
            }

            double[][] fftEven = fft(even);
            double[][] fftOdd = fft(odd);


            double[] twiddle = new double[] {1, 0};
            double[] complex;

            for (int i = 0; i < length / 2; i++) {
                twiddle[1] = getTwiddleFactor(i, length);
                complex = multiplyComplex(fftOdd[i], twiddle);

                input[i] = addComplex(fftEven[i], complex);
                input[i + length / 2] = subComplex(fftEven[i], complex);
            }
        }

        return input;
    }

    public static double getTwiddleFactor(int iteration, int length) {
        return -2 * iteration * Math.PI / length;
    }
}
