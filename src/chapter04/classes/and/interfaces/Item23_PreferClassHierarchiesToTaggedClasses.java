package chapter04.classes.and.interfaces;

import interfaces.ILesson;

public class Item23_PreferClassHierarchiesToTaggedClasses implements ILesson {
    @Override
    public void doLesson() {
        Rectangle r = new Rectangle(1, 2);
        assert r.area() == 2;

        BadExampleOfTaggedClass badRect = new BadExampleOfTaggedClass(1 ,2);
        assert badRect.area() == 2;
    }
}

interface Figure {
    double area();
}
// Tagged class - BAD! vastly inferior to a class hierarchy!
class BadExampleOfTaggedClass implements Figure {
    enum Shape {RECTANGLE, CIRCLE}

    ;
    // Tag field - the shape of this figure
    final Shape shape;
    // These fields are used only if shape is RECTANGLE
    double length;
    double width;
    // This field is used only if shape is CIRCLE
    double radius;

    // Constructor for circle
    BadExampleOfTaggedClass(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // Constructor for rectangle
    BadExampleOfTaggedClass(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    public double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}

class Rectangle implements Figure {

    private final double width;
    private final double length;
    Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
    }

    @Override
    public double area() {
        return this.width * this.length;
    }
}