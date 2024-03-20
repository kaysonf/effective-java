package chapter03.methods.common.to.all.objects;

import interfaces.ILesson;

import java.util.Objects;

public class Item11_AlwaysOverrideHasCodeWhenYouOverrideEquals implements ILesson {

    @Override
    public void doLesson() {
        /*
         * 1. When the hashCode method is invoked on an object repeatedly during an execution of an application,
         * it must consistently return the same value, provided no information used in equals comparisons is modified
         *
         * 2. If two objects are equal according to the equals(Object) method, then calling hashCode on the two objects must produce the same integer result.
         *
         * 3. If two objects are unequal according to the equals(Object) method, it is not required that calling hashCode on each of the objects must produce distinct results.
         * However, the programmer should be aware that producing distinct results for unequal objects may improve the performance of hash tables.
         */

        short areaCode = 1;
        short prefix = 2;
        short lineNumber = 3;

        PhoneNumber pn = new PhoneNumber(areaCode, prefix, lineNumber);
    }
}


class PhoneNumber {

    short areaCode;
    short prefix;
    short lineNum;

    PhoneNumber(short ac, short pf, short ln) {
        this.areaCode = ac;
        this.prefix = pf;
        this.lineNum = ln;

    }
    @Override
    public int hashCode() {
        int result = 31 * Short.hashCode(this.areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }

    private int fastest() {
        /*
         * The value 31 was chosen because it is an odd prime. If it were even and the multiplication overflowed, information would be lost, because multiplication by 2 is equivalent to shifting.
         * The advantage of using a prime is less clear, but it is traditional. A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction for better performance on some architectures: 31 * i == (i << 5) - i.
         */
        int result = 31 * Short.hashCode(this.areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }

    private int mediocre() {
        /*
         * entail array creation to pass a variable number of arguments, as well as boxing and unboxing if any of the arguments are of primitive type.
         * This style of hash function is recommended for use only in situations where performance is not critical
         */
        return Objects.hash(lineNum, prefix, areaCode);
    }
}