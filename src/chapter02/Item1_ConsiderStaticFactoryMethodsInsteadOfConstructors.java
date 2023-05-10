package chapter02;

import interfaces.ILesson;

import java.util.HashMap;

/**
 * A class can provide a public static factory method, which is simply a static method that returns an instance of the class
 * Note that a static factory method is not the same as the Factory Method pattern
 */
public class Item1_ConsiderStaticFactoryMethodsInsteadOfConstructors implements ILesson {

    @Override
    public void doLesson() {
        // non-static factory constructor
        String value = new String("constructor");

        // 1. static factory methods have verbose constructor names
        String numeric = String.valueOf(10);

        // 2. not required to create a new object (green car is a singleton, and naturally it should be immutable)
        Car greenCar = Car.greenCar();
        System.out.println(greenCar.getColor());

        // 3. return subtypes, which hide implementation details
        Car racingCar = Car.getRacingCar();
        System.out.println(racingCar.getColor());

        Car anotherRacingCar = CarCreator.blueRacingCar();
        System.out.println(anotherRacingCar.getColor());

        // 4. return DIFFERENT subtypes, based on parameters
        Car merc = BrandedCarCreator.brandedCar(Brands.MERCEDES);
        Car maz = BrandedCarCreator.brandedCar(Brands.MAZDA);

        // 5. the returned object need not exist at compile time
        Service nonExistentService = ServiceProvider.getService("hello");
        if (nonExistentService == null) {
            System.out.println("hello service is null");
        }

        // pretend this is loaded during run time
        ServiceProvider.registerService("hello", new HelloService());

        Service helloService = ServiceProvider.getService("hello");
        helloService.execute();
    }

}
interface Service {
    void execute();
}

class HelloService implements Service {

    @Override
    public void execute() {
        System.out.println("hello service");
    }
}
class ServiceProvider {
    private static final HashMap<String, Service> services = new HashMap<>();

    public static void registerService(String name, Service service) {
        services.put(name, service);
    }

    public static Service getService(String name) {
        return services.get(name);
    }
}

interface CarCreator {
    static Car blueRacingCar() {
        RacingCar rc = new RacingCar("BLUE");
        rc.makeSound("FROM INTERFACE");
        return rc;
    }
}

enum Brands {
    MERCEDES,
    MAZDA
}
interface BrandedCarCreator {
    static Car brandedCar(Brands brand) {
       switch (brand) {
           case MERCEDES: {
               Mercedes m = new Mercedes("BLACK");
               m.printBrand();
               return m;
           }

           case MAZDA: {
               Mazda m = new Mazda("BLACK");
               m.checkMirror();
               return m;
           }
       }

       return new Car("non branded");
    }
}
class Car {
    private final String color;
    private static final Car IMMUTABLE_CAR = new Car("GREEN");

    Car(String color) {
        this.color = color;
    }

    String getColor() {
        return this.color;
    }

    static Car greenCar() {
        return IMMUTABLE_CAR;
    }

    static Car getRacingCar() {
        RacingCar rc = new RacingCar("RED");
        rc.makeSound("FROM CLASS");
        return rc;
    }

}

class RacingCar extends Car {

    RacingCar(String color) {
        super(color);
    }

    public void makeSound(String sound) {
        System.out.printf("VROOM: %s\n", sound);
    }
}

class Mercedes extends Car {

    Mercedes(String color) {
        super(color);
    }

    public void printBrand() {
        System.out.println("C180");
    }
}

class Mazda extends Car {

    Mazda(String color) {
        super(color);
    }

    public void checkMirror() {
        System.out.println("mirror ok");
    }
}