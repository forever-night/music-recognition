package musicrecognition.util.math;

public class HannWindow implements Window{
    public double[] apply(double[] samples) throws RuntimeException {
        int length = samples.length;

        if (length == 1)
            return samples;

        if ((length & (length - 1)) != 0)
            throw new RuntimeException("input length not power of 2");


        double temp,
                pi2 = 2 * Math.PI;

        for (int i = 0; i < length; i++) {
            temp = 0.5 * (1 - Math.cos((pi2 * i) / (length - 1)));
            samples[i] *= temp;
        }

        return samples;
    }
}
