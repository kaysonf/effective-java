package chapter03.methods.common.to.all.objects;

import interfaces.ILesson;

public class Item13_OverrideCloneJudiciously implements ILesson {
    @Override
    public void doLesson() {
        /*
         * So what does Cloneable do, given that it contains no methods?
         * It determines the behavior of Object’s protected clone implementation: if a class implements Cloneable, Object’s clone method returns a field-by-field copy of the object; otherwise it throws CloneNotSupportedException.
         *
         * Normally, implementing an interface says something about what a class can do for its clients. In this case, it modifies the behavior of a protected method on a superclass.
         *
         * Though the specification does not say it, in practice, a class implementing Cloneable is expected to provide a properly functioning public clone method.
         */

        /*
         * As a rule, copy functionality is best provided by constructors or factories. A notable exception to this rule is arrays, which are best copied with the clone method.
         */

        /*
         * x.clone() != x
         * will be true, and the expression
         * x.clone().getClass() == x.getClass()
         * will be true, but these are not absolute requirements. While it is typically the
         * case that
         * x.clone().equals(x)
         */

        MyClass mc = new MyClass(1, "two");
        try {
            MyClass mc2 = mc.clone();
            assert mc2.field1 == 1;
            assert mc2.field2.equals("two");
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("should not happen");
        }


        Carefully c1 = new Carefully(5);
        try {
            Carefully c2 = c1.clone();
            c1.arr[0] = 3;

            assert c2.arr[0] == 5 : "should not bee modified";
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("should not happen");
        }


    }
}

class Carefully implements Cloneable {
    public int[] arr = new int[1];

    Carefully(int i) {
        this.arr[0] = i;
    }

    @Override
    protected Carefully clone() throws CloneNotSupportedException {
        try {
            Carefully c = (Carefully) super.clone();
            c.arr = this.arr.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
class MyClass implements Cloneable {
    public final int field1;
    public final String field2;

    MyClass(int f1, String f2) {
        this.field1 = f1;
        this.field2 = f2;
    }

    /**
     * 1. Ensure that your class implements the Cloneable interface to indicate that it supports cloning.
     * 2, Override the clone() method in your class.
     * 3, Inside the clone() method, call super.clone() to obtain a shallow copy of the object.
     * 4. Cast the cloned object to the type of your class.
     * 5. If necessary, modify one or more fields of the cloned object to achieve independence from the original object.
     * 6. Return the modified cloned object.
     * 7. If all fields in your class contain primitive values or references to immutable objects, the cloned object obtained from super.clone() may be sufficient, and no further processing is required.
     * 8. Keep in mind that the object returned by clone() should be obtained by calling super.clone() to maintain consistency with the convention and ensure that x.clone().getClass() == x.getClass().
     * 9. If your class is final and does not have any subclasses, the convention of calling super.clone() may be safely ignored.
     * 10. However, if your class is final and has a clone() method that does not invoke super.clone(), there is no need to implement Cloneable since it doesn't rely on the behavior of Object's clone() implementation.
     */

    @Override
    protected MyClass clone() throws CloneNotSupportedException {
        try {
            return (MyClass) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();  // Can't happen
        }
    }

    @Override
    public String toString() {
        return String.format("%d-%s", this.field1, this.field2);
    }
}