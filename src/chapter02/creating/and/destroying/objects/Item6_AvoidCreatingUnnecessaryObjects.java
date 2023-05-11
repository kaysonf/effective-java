package chapter02.creating.and.destroying.objects;

import interfaces.ILesson;

import java.util.regex.Pattern;

public class Item6_AvoidCreatingUnnecessaryObjects implements ILesson {
    @Override
    public void doLesson() {
        for (int i = 0; i < 3; i++) {
            String s = new String("waste"); // don't do this
            System.out.println(s);
        }

        String another = "not_waste";

        for (int i = 0; i < 3; i++) {
            System.out.println(another);
        }

        PoorPerformance pp = new PoorPerformance();
        GoodPerformance gp = new GoodPerformance();

        String numeral = "X";
        System.out.printf("%b is faster than %b\n", gp.isRomanNumeral(numeral), pp.isRomanNumeral(numeral));

    }
}


interface IsRoman {
    boolean isRomanNumeral(String s);
}

class PoorPerformance implements IsRoman {

    private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    @Override
    public boolean isRomanNumeral(String s) {
        /*
         * While String.matches is the easiest way to check if a string matches a regular expression,
         * it’s not suitable for repeated use in performance-critical situations.
         * The problem is that it internally creates a Pattern instance for the regular expression and uses it only once, after which it becomes eligible for garbage collection
         */
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }


}

class GoodPerformance implements IsRoman {
    private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    @Override
    public boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }
}

class SubtleObjectCreation {
    /**
     * Another way to create unnecessary objects is autoboxing
     * @return sum
     */
    public static long sumWithUnnecessaryObjectCreation() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i; // auto boxing
        return sum;
    }

    /**
     * prefer primitives to boxed primitives, and watch out for unintentional autoboxing.
     * @return sum
     */
    public static long sum() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        return sum;
    }
}
