import chapter02.creating.and.destroying.objects.Item5_PreferDependencyInjectionToHardwiringResources;
import interfaces.ILesson;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ILesson[] lessons = {
//                new Item1_ConsiderStaticFactoryMethodsInsteadOfConstructors(),
//                new Item2_ConsiderABuilderWhenFacedWithManyConstructorParameters(),

                new Item5_PreferDependencyInjectionToHardwiringResources()
        };


        Arrays.stream(lessons).forEach(ILesson::doLesson);
    }
}