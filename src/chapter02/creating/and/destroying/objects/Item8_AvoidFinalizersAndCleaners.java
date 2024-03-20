package chapter02.creating.and.destroying.objects;

import interfaces.ILesson;

public class Item8_AvoidFinalizersAndCleaners implements ILesson {

    @Override
    public void doLesson() {
        // instead of finalizers and closers to "clean up" resources
        // get classes to implement AutoCloseable and invoke them with try-with-resource
        try (Room room = new Room(5); Room room2 = new Room(5)) {
            System.out.println(Room.getNumRooms());
        }

        Room room3 = new Room(3);
        System.out.println(Room.getNumRooms());
        // notice close method is not called

    }
}


// An autocloseable class
class Room implements AutoCloseable {
    private static final Object lock = new Object();
    private static int numRooms = 0;
    private int numJunkPiles;

    public Room(int numJunkPiles) {
        synchronized (lock) {
            numRooms++;
        }
        this.numJunkPiles = numJunkPiles;
    }

    @Override
    public void close() {
        synchronized (lock) {
            numRooms--;
        }
        System.out.println("Cleaning room");
        numJunkPiles = 0;
    }

    public static int getNumRooms() {
        synchronized (lock) {
            return numRooms;
        }
    }
}







