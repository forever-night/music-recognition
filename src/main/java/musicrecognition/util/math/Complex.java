package musicrecognition.util.math;

/**
 * Implements elementary operations on complex numbers.<br>
 * Representation of complex number is <code>double[] { magnitude, theta }</code>.
 * Theta is expressed in radians. Radians with negative sign are represented as <code>2 * Math.PI + theta</code>.
 */
public class Complex {
    public static double getMagnitude(double real, double imag) {
        return Math.sqrt(real * real + imag * imag);
    }

    /**
     * Calculate theta (rotation angle) in radians.
     * */
    public static double getTheta(double real, double imag) {
        double result = Math.atan2(imag, real);

        while (result < 0)
            result += 2 * Math.PI;

        return result;
    }

    public static double getReal(double[] complex) {
        return complex[0] * Math.cos(complex[1]);
    }

    public static double getImag(double[] complex) {
        return complex[1] == 0 ? 0 : complex[0] * Math.sin(complex[1]);
    }

    public static double[] addComplex(double[] complex1, double[] complex2) {
        double real = getReal(complex1) + getReal(complex2);
        double imag = getImag(complex1) + getImag(complex2);

        double[] result = new double[] {
                getMagnitude(real, imag),
                getTheta(real, imag)
        };

        return simplify(result);
    }

    public static double[] subComplex(double[] complex1, double[] complex2) {
        double real = getReal(complex1) - getReal(complex2);
        double imag = getImag(complex1) - getImag(complex2);

        double[] result = new double[] {
                getMagnitude(real, imag),
                getTheta(real, imag)
        };

        return simplify(result);
    }

    public static double[] multiplyComplex(double[] complex1, double[] complex2) {
        double magnitude = complex1[0] * complex2[0];
        double theta = complex1[1] + complex2[1];

        double[] result = new double[] { magnitude, theta };

//        if (result[0] < 0 && result[1] > Math.PI) {
//            result[0] *= -1;
//            result[1] -= Math.PI;
//        }

        return simplify(result);
    }

    public static double[] simplify(double[] input) {
        while (input[1] < 0)
            input[1] += 2 * Math.PI;

        if (input[1] == Math.PI) {
            input[0] *= -1;
            input[1] = 0;
        }

        return input;
    }

    /**
     * Converts an array of real numbers to an array of complex numbers,
     * where complex number is <code>double[] {magnitude, theta}</code>. Theta - angle in radians.
     *
     * @param input array of real numbers
     * @return array of complex numbers
     * */
    public static double[][] toComplex(double[] input) {
        double[][] output = new double[input.length][2];

        for (int i = 0; i < input.length; i++) {
            output[i][0] = input[i];
            output[i][1] = 0;
        }

        return output;
    }

    public static String complexToString(double[] complex) {
        StringBuilder sb = new StringBuilder();

        sb.append("[ r= ");
        sb.append(complex[0]);
        sb.append(", theta= ");
        sb.append(complex[1]);
        sb.append(" ]");

        return sb.toString();
    }

    public static String complexArrayToString(double[][] complexArray) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < complexArray.length; i++) {
            sb.append("\t");

            sb.append("[ r= ");
            sb.append(complexArray[i][0]);
            sb.append(", theta= ");
            sb.append(complexArray[i][1]);
            sb.append(" ]");

            sb.append("\n");
        }

        sb.append("}");

        return sb.toString();
    }
}
