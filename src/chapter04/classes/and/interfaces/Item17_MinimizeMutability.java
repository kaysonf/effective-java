package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item17_MinimizeMutability implements ILesson {
    @Override
    public void doLesson() {
        /*
         * Immutable objects are inherently thread-safe; they require no synchronization.
         */
        Complex a = Complex.valueOf(1, 2);
        Complex b = Complex.valueOf(3, 4);
        Complex c = a.plus(b);
        System.out.println(c);

        /*
         * Classes should be immutable unless there’s a very good reason to make them mutable
         */
    }
}


final class Complex {
    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * to guarantee immutability, a class must not permit itself to be subclassed
     * @param re - real number
     * @param im - imaginary number
     * @return Complex
     */
    public static Complex valueOf(double re, double im) {

        return new Complex(re, im);
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex times(Complex c) {
        return new Complex(re * c.re - im * c.im,
                re * c.im + im * c.re);
    }

    public Complex dividedBy(Complex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new Complex((re * c.re + im * c.im) / tmp,
                (im * c.re - re * c.im) / tmp);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}