package musicrecognition.math;

/**
 * Implements elementary operations on complex numbers.<br>
 * Representation of complex number is <code>double[] { magnitude, theta }</code>
 */
public class Complex {
    public static double getMagnitude(double real, double imag) {
        return Math.sqrt(real * real + imag * imag);
    }

    public static double getTheta(double real, double imag) {
        return Math.atan2(imag, real);
    }

    public static double getReal(double[] complex) {
        return complex[0] * Math.cos(complex[1]);
    }

    public static double getImag(double[] complex) {
        return complex[0] * Math.sin(complex[1]);
    }

    public static double[] addComplex(double[] complex1, double[] complex2) {
        double real = getReal(complex1) + getReal(complex2);
        double imag = getImag(complex1) + getImag(complex2);

        return new double[] {
                getMagnitude(real, imag),
                getTheta(real, imag)
        };
    }

    public static double[] subComplex(double[] complex1, double[] complex2) {
        double real = getReal(complex1) - getReal(complex2);
        double imag = getImag(complex1) - getImag(complex2);

        return new double[] {
                getMagnitude(real, imag),
                getTheta(real, imag)
        };
    }

    public static double[] multiplyComplex(double[] complex1, double[] complex2) {
        double magnitude = complex1[0] * complex2[0];
        double theta = complex1[1] + complex2[1];

        return new double[] { magnitude, theta };
    }

    /**
     * Converts an array of real numbers to an array of complex numbers,
     * where complex number is <code>double[] {magnitude, theta}</code>
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
