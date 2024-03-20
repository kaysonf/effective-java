package chapter03.methods.common.to.all.objects;

import interfaces.ILesson;

import java.util.*;

public class Item14_ConsideringImplementingComparable implements ILesson {
    @Override
    public void doLesson() {
        /*
         * By implementing Comparable, a class indicates that its instances have a natural ordering.
         * Arrays.sort(a);
         */

        /*
         * Because the Comparable interface is parameterized, the compareTo method is statically typed,
         * so you don’t need to type check or cast its argument. If the argument is of the wrong type, the invocation won’t even compile
         */

        /*
         * When comparing field values in the implementations of the compareTo methods, avoid the use of the < and > operators.
         * Instead, use the static compare methods in the boxed primitive classes or the comparator construction methods in the Comparator interface.
         */

    }


}

class HasOrder implements Comparable<HasOrder> {

    private int field1;
    private int field2;
    private int field3;

    /*
     * concise approach, at the cost of performance
     */
    private static final Comparator<HasOrder> COMPARATOR = Comparator
            .comparingInt((HasOrder ho) -> ho.field1)
            .thenComparingInt(ho -> ho.field2)
            .thenComparingInt(ho -> ho.field3);

    @Override
    public int compareTo(HasOrder o) {
        /*
         * Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
         */
        return COMPARATOR.compare(this, o);
    }
}
