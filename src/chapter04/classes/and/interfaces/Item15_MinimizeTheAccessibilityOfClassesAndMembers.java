package chapter04.classes.and.interfaces;

import com.sun.javafx.collections.UnmodifiableListSet;
import interfaces.ILesson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Item15_MinimizeTheAccessibilityOfClassesAndMembers implements ILesson {

    @Override
    public void doLesson() {
        /*
         * For members (fields, methods, nested classes, and nested interfaces), there are four possible access levels, listed here in order of increasing accessibility:
         * • private—The member is accessible only from the top-level class where it is declared.
         * • package-private—The member is accessible from any class in the package where it is declared. Technically known as default access, this is the access level you get if no access modifier is specified (except for interface members, which are public by default).
         * • protected—The member is accessible from subclasses of the class where it is declared (subject to a few restrictions [JLS, 6.6.2]) and from any class in the package where it is declared.
         * • public—The member is accessible from anywhere.
         */

        try {
            WithArrayField.VALUES.set(0, 5);
        } catch (UnsupportedOperationException e) {
            assert WithArrayField.VALUES.get(0) == 1;
        }

    }
}

class WithArrayField  {
    private static final int[] arr = {1,2,3};
    public static final List<Integer> VALUES = Collections.unmodifiableList(Arrays.asList(1,2,3));
    public static int[] getArr() {
        // do not just return arr
        return arr.clone();
    }
}