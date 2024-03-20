package chapter03.methods.common.to.all.objects;

import interfaces.ILesson;

public class Item12_AlwaysOverrideToString implements ILesson {
    @Override
    public void doLesson() {
        /*
         * The general contract for toString says that the returned string should be “a concise but informative representation that is easy for a person to read.”
         *
         * When practical, the toString method should return all the interesting information contained in the object
         */

        Example e = new Example(1, "e.g.");
        System.out.println(e);
    }
}

class Example {
    int someValue;
    String name;

    Example(int i, String n) {
        this.someValue=i;
        this.name = n;
    }

    @Override
    public String toString() {
        return String.format("%s - %d", this.name, this.someValue);
    }
}
