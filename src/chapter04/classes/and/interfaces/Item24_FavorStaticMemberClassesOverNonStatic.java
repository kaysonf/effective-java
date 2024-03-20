package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item24_FavorStaticMemberClassesOverNonStatic implements ILesson {
    @Override
    public void doLesson() {
        /*
         * A nested class is a class defined within another class. A nested class should exist only to serve its enclosing class.
         * If a nested class would be useful in some other context, then it should be a top-level class
         */

        /*
         * Static member classes
         * Nonstatic member classes
         * Anonymous classes
         * Local classes
         */

        OuterClass oc = new OuterClass();
        assert oc.accessOuter() == 10;
        assert oc.accessInner() == 20;
    }
}

class OuterClass {
    private static final int outerStaticVariable = 10;
    private final int innerStaticVar = 20;

    // Static member class
    // If you declare a member class that does not require access to an enclosing instance, always put the static modifier in its declaration
    // If you omit this modifier, each instance will have a hidden extraneous reference to its enclosing instance.
    // possible memory leak
    private static class StaticMemberClass {
        public int accessOuterClassMembers() {
            // cant access innerStaticVar
            return outerStaticVariable;
        }
    }

    private class InnerClass {
        public int innerAccess() {
            return innerStaticVar;
        }
    }


    public int accessOuter() {
        return new StaticMemberClass().accessOuterClassMembers();
    }

    public int accessInner() {
        return new InnerClass().innerAccess();
    }
}

