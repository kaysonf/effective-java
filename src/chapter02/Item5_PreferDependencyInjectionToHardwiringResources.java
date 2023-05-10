package chapter02;

import interfaces.ILesson;

import java.util.Objects;
import java.util.function.Supplier;

public class Item5_PreferDependencyInjectionToHardwiringResources implements ILesson {
    @Override
    public void doLesson() {
        new SpellChecker(new InjectedLexicon());

        /*
         * The Supplier Interface is a part of the java.util.function package which has been introduced since Java 8,
         * to implement functional programming in Java. It represents a function which does not take in any argument but produces a value of type T.
         */
        Supplier<String> injectedFactory = () -> "hello world";
        ResourceFactoryExample<String> exampleResourceF = new ResourceFactoryExample<>(injectedFactory);

        String resource = exampleResourceF.create();
        System.out.println(resource);
    }
}

class InjectedLexicon implements Lexicon {

    @Override
    public void doLexiconThing() {
        System.out.println("I am injected");
    }
}

// Dependency injection provides flexibility and testability
class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
        dictionary.doLexiconThing();
    }

}

interface Lexicon {
    void doLexiconThing();
}

class ResourceFactoryExample<T> {
    private final Supplier<T> fac;

    public ResourceFactoryExample(Supplier<T> rf) {
        this.fac = rf;
    }

    public T create() {
        return this.fac.get();
    }
}