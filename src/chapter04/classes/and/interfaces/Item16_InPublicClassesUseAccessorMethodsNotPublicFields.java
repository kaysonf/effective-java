package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item16_InPublicClassesUseAccessorMethodsNotPublicFields implements ILesson {
    @Override
    public void doLesson() {

    }
}

class Wrong {
    public int field1;

    Wrong(int i) {
        if (i < 5) {
            throw new IllegalArgumentException("cannot be less than 5");
        }
        this.field1 = i;
    }
}

class Correct {
    private int field1;

    Correct(int i) {
        if (i < 5) {
            throw new IllegalArgumentException("cannot be less than 5");
        }
        this.field1 = i;
    }

    public int getField1() {
        return field1;
    }

    public void setField1(int i) {
        if (i < 5) {
            throw new IllegalArgumentException("cannot be less than 5");
        }
        this.field1 = i;
    }
}