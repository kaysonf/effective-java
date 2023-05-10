package chapter02;

import interfaces.ILesson;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import static chapter02.Pizza.Topping.*;

/**
 * Consider the case of a class representing the Nutrition Facts label that appears on packaged foods.
 * These labels have a few required fields—serving size, servings per container, and calories per serving— and more than twenty optional fields—total fat,
 * saturated fat, trans fat, cholesterol, sodium, and so on. Most products have nonzero values for only a few of these optional fields.
 */
public class Item2_ConsiderABuilderWhenFacedWithManyConstructorParameters implements ILesson {
    @Override
    public void doLesson() {
        NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();

        System.out.printf("serving size: %d\n", cocaCola.getServingSize());

        try {
            NutritionFacts illegal = new NutritionFacts.Builder(-1, -1).build();

            System.out.printf("serving size: %d", illegal.getServingSize());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // abstract class
        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL).addTopping(SAUSAGE).addTopping(ONION).build();
        Calzone calzone = new Calzone.Builder().addTopping(HAM).sauceInside().build();

        /*
         * In summary, the Builder pattern is a good choice when designing classes whose constructors or static factories would have more than a handful of parameters,
         * especially if many of the parameters are optional or of identical type.
         */

    }
}

// Telescoping constructor pattern - does not scale well!
// very prone to mistakes - clients might set parameters wrongly, especially for longer sequences
class BadExampleNutritionFacts {
    private final int servingSize;  // (mL)            required
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate; // (g/serving)

    public BadExampleNutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public BadExampleNutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public BadExampleNutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public BadExampleNutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public BadExampleNutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}


class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    private NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public int getServingSize() {
        return servingSize;
    }


    public static class Builder {
        // Required parameters
        private final int servingSize;
        private final int servings;
        // Optional parameters - initialized to default values
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts build() throws IllegalArgumentException {
            if (servings <= 0 || servingSize <= 0) {
                throw new IllegalArgumentException("Invalid servings or serving size");
            }
            return new NutritionFacts(this);
        }
    }
}

abstract class Pizza {
    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // Subclasses must override this method to return "this"
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // See Item 50
    }
}

class NyPizza extends Pizza {
    public enum Size {SMALL, MEDIUM, LARGE}

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}

class Calzone extends Pizza {
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false; // Default

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        public Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }
}