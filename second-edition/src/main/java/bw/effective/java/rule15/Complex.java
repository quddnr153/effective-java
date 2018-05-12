package bw.effective.java.rule15;

import java.util.Objects;

/**
 * @author Byungwook Lee on 2018-04-16
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public final class Complex {
    private final double re;
    private final double im;

    public Complex(final double re, final double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public Complex add(final Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex substract(final Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex multiply(final Complex c) {
        return new Complex(re * c.re - im * c.im,re * c.im + im * c.re);
    }

    public Complex divide(final Complex c) {
        double tmp = c.re * c.re + c.im * c.im;

        return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complex)) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.re, re) == 0 &&
                Double.compare(complex.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}
