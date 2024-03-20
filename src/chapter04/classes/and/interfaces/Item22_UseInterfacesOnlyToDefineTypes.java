package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item22_UseInterfacesOnlyToDefineTypes implements ILesson {
    @Override
    public void doLesson() {
    // In summary, interfaces should be used only to define types. They should not be used merely to export constants
    }
}

// Constant interface anti pattern - do not use!
interface PhysicalConstantsI {
    // Avogadro's number (1/mol)
    static final double AVOGADROS_NUMBER   = 6.022_140_857e23;
    // Boltzmann constant (J/K)
    static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    // Mass of the electron (kg)
    static final double ELECTRON_MASS      = 9.109_383_56e-31;
}



// Use of static import to avoid qualifying constants in consumers
//   import static com.etc.whatever.PhysicalConstants.*;
class PhysicalConstants {
    private PhysicalConstants() { }  // Prevents instantiation
    // Avogadro's number (1/mol)
    static final double AVOGADROS_NUMBER   = 6.022_140_857e23;
    // Boltzmann constant (J/K)
    static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    // Mass of the electron (kg)
    static final double ELECTRON_MASS      = 9.109_383_56e-31;
}
