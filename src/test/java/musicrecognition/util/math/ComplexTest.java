package musicrecognition.util.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class ComplexTest {
    double[] real;
    double[] complex1, complex2;

    @Before
    public void setUp() {
        real = new double[] {5, 0};

        complex1 = new double[] {Math.sqrt(74), Math.atan2(5, 7)};         // real 7, imag 5
        complex2 = new double[] {Math.sqrt(73), Math.atan2(-3, 8)};       // real 8, imag -3

        complex2[1] += 2 * Math.PI;
    }

    @Test
    public void getMagnitudeFromReal() {
        double expected = real[0];
        double actual = Complex.getMagnitude(5, 0);

        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void getMagnitudeFromComplex1() {
        double expected = 8.60233;
        double actual = Complex.getMagnitude(7, 5);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getThetaFromReal() {
        double expected = 0;
        double actual = Complex.getTheta(5, 0);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getThetaFromComplex1() {
        double expected = 0.62;
        double actual = Complex.getTheta(7, 5);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getRealFromReal() {
        double expected = 5;
        double actual = Complex.getReal(real);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getRealFromComplex() {
        double expected = 7;
        double actual = Complex.getReal(complex1);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getImagFromReal() {
        double expected = 0;
        double actual = Complex.getImag(real);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getImagFromComplex() {
        double expected = 5;
        double actual = Complex.getImag(complex1);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void addComplex1ToReal() {
        double[] expected = {13, 0.39};
        double[] actual = Complex.addComplex(complex1, real);

        Assert.assertArrayEquals(expected, actual, 0.01);
    }

    @Test
    public void addComplex1Complex2() {
        double[] expected = {15.13, 0.13};
        double[] actual = Complex.addComplex(complex1, complex2);

        Assert.assertArrayEquals(expected, actual, 0.01);
    }

    @Test
    public void subComplex1Complex2() {
        double[] expected = {8.06, 1.7};
        double[] actual = Complex.subComplex(complex1, complex2);

        Assert.assertArrayEquals(expected, actual, 0.01);
    }

    @Test
    public void subComplex2Complex1() {
        double[] expected = {8.06, 4.84};
        double[] actual = Complex.subComplex(complex2, complex1);

        Assert.assertArrayEquals(expected, actual, 0.01);
    }

    @Test
    public void multiplyComplex1ByReal() {
        double[] expected = Arrays.copyOf(complex1, complex1.length);
        expected[0] *= real[0];

        double[] actual = Complex.multiplyComplex(complex1, real);

        Assert.assertArrayEquals(expected, actual, 0.001);
    }

    @Test
    public void multiplyComplex2ByNegativeReal() {
        double[] expected = Arrays.copyOf(complex2, complex2.length);
        expected[0] *= real[0];

        double[] actual = Complex.multiplyComplex(complex2, real);

        Assert.assertArrayEquals(expected, actual, 0.001);
    }

    @Test
    public void multiplyComplex1Complex2() {
        double[] expected = {complex1[0] * complex2[0], complex1[1] + complex2[1]};
        double[] actual = Complex.multiplyComplex(complex1, complex2);

        Assert.assertArrayEquals(expected, actual, 0.001);
    }
}