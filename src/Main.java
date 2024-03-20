import chapter04.classes.and.interfaces.Item20_PreferInterfacesToAbstractClasses;
import chapter04.classes.and.interfaces.Item24_FavorStaticMemberClassesOverNonStatic;
import interfaces.ILesson;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ILesson[] lessons = {
//                new Item1_ConsiderStaticFactoryMethodsInsteadOfConstructors(),
//                new Item2_ConsiderABuilderWhenFacedWithManyConstructorParameters(),

//                new Item5_PreferDependencyInjectionToHardwiringResources(),
//                new Item8_AvoidFinalizersAndCleaners(),
//                new Item10_ObeyTheGeneralContractWhenOverridingEquals(),
//                new Item12_AlwaysOverrideToString(),
//                new Item13_OverrideCloneJudiciously(),
//                new Item14_ConsideringImplementingComparable(),
//                new Item15_MinimizeTheAccessibilityOfClassesAndMembers(),
//                new Item20_PreferInterfacesToAbstractClasses(),
                new Item24_FavorStaticMemberClassesOverNonStatic(),
        };


        Arrays.stream(lessons).forEach(ILesson::doLesson);
    }
}