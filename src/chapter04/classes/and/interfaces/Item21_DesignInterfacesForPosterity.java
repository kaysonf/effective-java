package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item21_DesignInterfacesForPosterity implements ILesson {
    @Override
    public void doLesson() {
        /*
         * Using default methods to add new methods to existing interfaces should be avoided unless the need is critical,
         * in which case you should think long and hard about whether an existing interface implementation might be broken by your default method implementation.
         * Default methods are, however, extremely useful for providing standard method implementations when an interface is created, to ease the task of implementing the interface
         */
    }
}
