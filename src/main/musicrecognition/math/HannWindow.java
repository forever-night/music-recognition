package musicrecognition.math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by anna on 30/03/16.
 */
public class HannWindow implements Window{
    private static final Logger LOGGER = LogManager.getLogger(HannWindow.class);

    public double[] apply(double[] samples) {
        int length = samples.length;

        if (length == 1)
            return samples;

        LOGGER.info("start hann window for length " + length);

        if ((length & (length - 1)) != 0)
            throw new RuntimeException("input length not power of 2");

        double temp,
                pi2 = 2 * Math.PI;

        for (int i = 0; i < length; i++) {
            temp = 0.5 * (1 - Math.cos((pi2 * i) / (length - 1)));

            LOGGER.info("hann value = " + temp);

            samples[i] *= temp;
        }

        return samples;
    }
}
