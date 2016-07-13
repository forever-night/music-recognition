package musicrecognition.util.math;

import org.junit.Assert;
import org.junit.Test;


public class FourierTest {
    @Test
    public void fftInput2() {
        double[][] input = { {3, 0}, {3, 0} };

        double[][] actuals = Fourier.transform(input);
        double[][] expecteds = { {6, 0}, {0, 0} };

        for (int i = 0; i < actuals.length; i++) {
            Assert.assertArrayEquals(expecteds[i], actuals[i], 0.001);
        }
    }
    
    @Test
    public void fftInput8() {
        double[][] input = {
                {1, 0}, {2, 0}, {3, 0}, {4, 0},
                {1, 0}, {2, 0}, {3, 0}, {4, 0}
        };

        double[][] actuals = Fourier.transform(input);
        double[][] expecteds = {
                {20, 0}, {0, 0}, {5.6, 2.36}, {0, 0},
                {-4, 0}, {0, 0}, {5.66, 3.93}, {0, 0}
        };

        for (int i = 0; i < actuals.length; i++)
            Assert.assertArrayEquals(expecteds[i], actuals[i], 0.1);
    }

    @Test
    public void fftInput16() {
        double[][] input = {
                {1, 0}, {2, 0}, {3, 0}, {4, 0},
                {1, 0}, {2, 0}, {3, 0}, {4, 0},
                {1, 0}, {2, 0}, {3, 0}, {4, 0},
                {1, 0}, {2, 0}, {3, 0}, {4, 0}
        };

        double[][] actuals = Fourier.transform(input);
        double[][] expecteds = {
                {40, 0}, {0, 0}, {0, 0}, {0, 0},
                {11.31, 2.36}, {0, 0}, {0, 0}, {0, 0},
                {-8, 0}, {0, 0}, {0, 0}, {0, 0},
                {11.31, 3.93}, {0, 0}, {0, 0}, {0, 0}
        };

        for (int i = 0; i < actuals.length; i++)
            Assert.assertArrayEquals(expecteds[i], actuals[i], 0.1);
    }

    @Test
    public void fftInput16Real() {
        double[] input = {
                1, 1, 1, 5,
                1, 1, 1, 5,
                1, 1, 1, 5,
                1, 1, 1, 5
        };

        double[] actuals = Fourier.transform(input);

        double[] expecteds = {
                32, 0, 0, 0,
                16, 0, 0, 0,
                -16, 0, 0, 0,
                16, 0, 0, 0
        };

        Assert.assertArrayEquals(expecteds, actuals, 0.1);
    }
}