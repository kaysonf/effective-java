package chapter02.creating.and.destroying.objects;

import interfaces.ILesson;

public class Item4_EnforceNonInstantiabilityWithPrivateConstructor implements ILesson {
    @Override
    public void doLesson() {
        int ans = UtilityClass.someFunc(1);
        System.out.println(ans);
    }
}

// Noninstantiable utility class
class UtilityClass {
    // Suppress default constructor for noninstantiability
    private UtilityClass() {
        throw new AssertionError();
    }

    public static int someFunc(int i) {
        return i + 1;
    }
}
