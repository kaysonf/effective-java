package chapter02.creating.and.destroying.objects;

import interfaces.ILesson;

public class Item3_EnforceSingletonPropertyWithPrivateConstructorOrEnumType implements ILesson {
    @Override
    public void doLesson() {
        SingletonClass.INSTANCE.doSomething();

        SingletonEnum.INSTANCE.doAThing();
    }
}

class SingletonClass {
    // Singleton with public final field
    public static final SingletonClass INSTANCE = new SingletonClass();

    private SingletonClass() {
    }

    public void doSomething() {
        System.out.println("something done");
    }

}

enum SingletonEnum {
    INSTANCE;

    public void doAThing() {
        System.out.println("a thing done");
    }
}