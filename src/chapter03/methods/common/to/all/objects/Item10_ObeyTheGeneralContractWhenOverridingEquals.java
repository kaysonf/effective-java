package chapter03.methods.common.to.all.objects;

import interfaces.ILesson;

import java.util.Objects;

public class Item10_ObeyTheGeneralContractWhenOverridingEquals implements ILesson {
    @Override
    public void doLesson() {
        /*
         *  when is it appropriate to override equals? It is when a class has a notion of logical equality that differs from mere object identity and a superclass has not already overridden equals.
         * This is generally the case for value classes. A value class is simply a class that represents a value, such as Integer or String.
         * A programmer who compares references to value objects using the equals method expects to find out whether they are logically equivalent,
         * not whether they refer to the same object.
         */

        /*
         * The equals method implements an equivalence relation. It has these properties:
         * • Reflexive: For any non-null reference value x, x.equals(x) must return true.
         * • Symmetric: For any non-null reference values x and y, x.equals(y) must return true if and only if y.equals(x) returns true.
         * • Transitive: For any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true.
         * • Consistent: For any non-null reference values x and y, multiple invocations of x.equals(y) must consistently return true or consistently return false, provided no information used in equals comparisons is modified.
         * • For any non-null reference value x, x.equals(null) must return false.
         */

        SymmetricLesson sl = new SymmetricLesson();
        sl.doLesson();

        // Transitive
        // There is no way to extend an instantiable class and add a value component while preserving the equals contract
        Transitive t = new Transitive();
        t.doLesson();

        // non-nullity - don't actually have to check for null value since, Symmetric indicates performs this check:
        /*
         *  if (!(o instanceof MyType)) // null is already checked here, and this is more robust
         *                  return false;
         */

        /*
         * recipe:
         * 1. Use the == operator to check if the argument is a reference to this object. If so, return true. This is just a performance optimization but one that is worth doing if the comparison is potentially expensive.
         * 2. Use the instanceof operator to check if the argument has the correct type. If not, return false.
         * 3. Cast the argument to the correct type. Because this cast was preceded by an instanceof test, it is guaranteed to succeed.
         * 4. For each “significant” field in the class, check if that field of the argument matches the corresponding field of this object.
         */

        /*
         * When you are finished writing your equals method, ask yourself three questions: Is it symmetric? Is it transitive? Is it consistent?
         * write unit tests to check
         *
         * OR use AutoValue framework
         */

        /*
         * In summary, don’t override the equals method unless you have to: in many cases, the implementation inherited from Object does exactly what you want.
         */
    }
}

class SymmetricLesson implements ILesson {
    @Override
    public void doLesson() {
        //• Reflexive: For any non-null reference value x, x.equals(x) must return true.
        String caps = "caps";

        SymmetricViolation sv = new SymmetricViolation("CAPS");
        Boolean isSymmetrical1 = sv.equals(caps) == caps.equals(sv);
        System.out.println(isSymmetrical1); // false

        Symmetric s = new Symmetric("CAPS");
        Boolean isSymmetrical2 = s.equals(caps) == caps.equals(s);
        System.out.println(isSymmetrical2); // true
    }

    static class SymmetricViolation {
        private final String s;

        public SymmetricViolation(String s) {
            this.s = Objects.requireNonNull(s);
        }

        // Broken - violates symmetry!
        @Override
        public boolean equals(Object o) {
            if (o instanceof SymmetricViolation)
                return s.equalsIgnoreCase(
                        ((SymmetricViolation) o).s);

            if (o instanceof String)  // One-way interoperability! an instance of String s, can't perform s.equals(SymmetricViolation) with the same result
                return s.equalsIgnoreCase((String) o);
            return false;
        }
    }

    static class Symmetric {
        private final String s;

        public Symmetric(String s) {
            this.s = Objects.requireNonNull(s);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Symmetric &&
                    ((Symmetric) o).s.equalsIgnoreCase(s);
        }

    }
}

class Transitive implements ILesson {

    @Override
    public void doLesson() {
        /*
         * There are some classes in the Java platform libraries that do extend an instantiable class and add a value component.
         * For example, java.sql.Timestamp extends java.util.Date and adds a nanoseconds field.
         * The equals implementation for Timestamp does violate symmetry and can cause erratic behavior if Timestamp and Date objects are used in the same collection or are otherwise intermixed
         * This behavior of the Timestamp class was a mistake and should not be emulated.
         */
    }

    static class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point))
                return false;
            Point p = (Point) o;
            return p.x == x && p.y == y;
        }
    }

    // DO NOT DO INHERIT, NO WAY TO MAINTAIN TRANSITIVITY AND SYMMETRY
    static class ColorPointSubClass extends Point {
        private final String color;

        ColorPointSubClass(int x, int y, String color) {
            super(x, y);
            this.color = color;
        }
    }

    // INSTEAD, USE COMPOSITION
    static class ColorPoint {
        private Point point;
        private final String color;

        public ColorPoint(Point p, String color) {
            this.point = p;
            this.color = color;
        }

        /**
         * Returns the point-view of this color point.
         */
        public Point getPoint() {
            return point;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ColorPoint))
                return false;
            ColorPoint cp = (ColorPoint) o;
            return cp.point.equals(point) && cp.color.equals(color);
        }
    }
}