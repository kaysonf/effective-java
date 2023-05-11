package chapter02.creating.and.destroying.objects;

import interfaces.ILesson;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.WeakHashMap;

public class Item7_EliminateObsoleteObjectReferences implements ILesson {
    @Override
    public void doLesson() {
        // the problem with the Stack class is that it manages its own memory.
        // from the developer's pov elements outside the active portion are unimportant,
        // but to the GC, it's still valid because the cells are being referenced

        // Generally speaking, whenever a class manages its own memory,
        // the programmer should be alert for memory leaks. Whenever an element is freed,
        // any object references contained in the element should be null-ed out.

        /*
         * Another common source of memory leaks is caches.
         * Once you put an object reference into a cache, it’s easy to forget that it’s there and leave it in the cache long after it becomes irrelevant
         */

        /*
         * A third common source of memory leaks is listeners and other callbacks.
         *
         * can use a weak reference hash map for call backs
         */

        CallbackExample.run();


    }
}

// Can you spot the "memory leak"?
class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object popWithMemoryLeak() {
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size]; // element is returned but the element still exists in `elements`
        /*
         * the objects that were popped off the stack will not be garbage collected,
         * even if the program using the stack has no more references to them.
         * This is because the stack maintains obsolete references to these objects.
         *
         *  In this case, any references outside the “active portion” of the element array are obsolete
         */
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // Eliminate obsolete reference return result;
        return result;

    }


    /**
     * Ensure space for at least one more element, roughly
     * doubling the capacity each time the array needs to grow.
     */
    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}

 class CacheExample {
    public static void main(String[] args) {
        Map<Key, Value> cache = new WeakHashMap<>();

        // Creating the keys
        Key key1 = new Key("key1");
        Key key2 = new Key("key2");

        // Creating the values
        Value value1 = new Value("value1");
        Value value2 = new Value("value2");

        // Adding entries to the cache
        cache.put(key1, value1);
        cache.put(key2, value2);

        // Retrieving values from the cache
        System.out.println(cache.get(key1)); // Output: value1
        System.out.println(cache.get(key2)); // Output: value2

        // Removing external references to key1
        key1 = null;

        // Performing garbage collection
        System.gc();

        // Waiting for a moment to allow garbage collection
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retrieving values from the cache again
        System.out.println(cache.get(key1)); // Output: null (key1 entry was automatically removed)
        System.out.println(cache.get(key2)); // Output: value2 (key2 entry is still present)
    }
}

class Key {
    private String key;

    public Key(String key) {
        this.key = key;
    }

    // Implement equals() and hashCode() methods
    // to ensure proper behavior of WeakHashMap
}

class Value {
    private String value;

    public Value(String value) {
        this.value = value;
    }
}


class CallbackExample {
    public static void run() {
        CallbackManager callbackManager = new CallbackManager();

        // Registering callbacks
        Callback callback1 = new CallbackImpl("Callback 1");
        Callback callback2 = new CallbackImpl("Callback 2");
        callbackManager.registerCallback(callback1);
        callbackManager.registerCallback(callback2);

        // Triggering some actions that may invoke the callbacks
        callbackManager.doAction();

        // Let's assume some time has passed or the callbacks are no longer needed

        // Callbacks will be eligible for garbage collection if there are no other references to them
        System.gc();

        // Waiting for a moment to allow garbage collection
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // The WeakHashMap will automatically remove the callbacks that have been garbage collected
        callbackManager.printCallbacks();
    }
}

interface Callback {
    void onCallback();
}

class CallbackImpl implements Callback {
    private String name;

    public CallbackImpl(String name) {
        this.name = name;
    }

    @Override
    public void onCallback() {
        System.out.println("Callback invoked: " + name);
    }
}

class CallbackManager {
    private Map<Callback, String> callbackMap;

    public CallbackManager() {
        callbackMap = new WeakHashMap<>();
    }

    public void registerCallback(Callback callback) {
        callbackMap.put(callback, "someValue");
    }

    public void doAction() {
        // Simulating an action that invokes the registered callbacks
        callbackMap.keySet().forEach(Callback::onCallback);
    }

    public void printCallbacks() {
        System.out.println("Callbacks:");
        for (Callback callback : callbackMap.keySet()) {
            System.out.println(callback);
        }
    }
}