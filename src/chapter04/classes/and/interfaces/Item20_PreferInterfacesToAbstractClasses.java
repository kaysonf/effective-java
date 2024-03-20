package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item20_PreferInterfacesToAbstractClasses implements ILesson {
    @Override
    public void doLesson() {
        Consumer c = new Consumer();

        assert (c.addOne(1) == 2);

        ConsumerThatOverwrites co = new ConsumerThatOverwrites();

        assert (co.addOne(1) == 3);

        Car car = new Car();
        Bike bike = new Bike();
        assert car.honk().equals("car");
        assert bike.honk().equals("bike");
        assert car.start().equals(bike.start());
        assert car.stop().equals(bike.stop());
    }
}

// skeletal implementation
interface ObviousImplementation {

    default int addOne(int i) {
        return i + 1;
    }
}

class Consumer implements ObviousImplementation {
}

class ConsumerThatOverwrites implements ObviousImplementation {
    @Override
    public int addOne(int i) {
        return i + 2;
    }
}

interface Vehicle {
    String start();

    String stop();

    String honk();
}
// skeletal implementation class
abstract class AbstractVehicle implements Vehicle {
    @Override
    public String start() {
        return "start";
    }

    @Override
    public String stop() {
        return "stop";
    }

    // The honk() method is left as abstract to be implemented by subclasses.
}

class Car extends AbstractVehicle {
    @Override
    public String honk() {
        return "car";
    }
}

class Bike extends AbstractVehicle {

    @Override
    public String honk() {
        return "bike";
    }
}
