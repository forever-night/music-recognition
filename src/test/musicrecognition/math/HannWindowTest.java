package musicrecognition.math;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by anna on 20/04/16.
 */
public class HannWindowTest {
    @Test
    public void applyInput1() {
        double[] input = {1};
        double[] expected = {1};
        double[] actual = HannWindow.apply(input);

        Assert.assertArrayEquals(expected, actual, 0);
    }

    @Test(expected = RuntimeException.class)
    public void applyInputLengthNotPowerOf2() {
        double[] input = {1, 2, 3};
        HannWindow.apply(input);
    }

    @Test
    public void applyInputLength8ValueSame() {
        double[] input = new double[8];
        Arrays.fill(input, 1);

        double[] expecteds = {
                0, 0.18, 0.61, 0.95,
                0.95, 0.61, 0.18, 0
        };

        double[] actuals = HannWindow.apply(input);

        Assert.assertArrayEquals(expecteds, actuals, 0.01);
    }

    @Test
    public void applyInputLength8ValueIncrease() {
        double[] input = new double[8];

        for (int i = 0; i < input.length; i++)
            input[i] = i + 1;

        double[] expecteds = {
                0, 0.37, 1.83, 3.8,
                4.75, 3.66, 1.31, 0
        };

        double[] actuals = HannWindow.apply(input);

        Assert.assertArrayEquals(expecteds, actuals, 0.01);
    }
}