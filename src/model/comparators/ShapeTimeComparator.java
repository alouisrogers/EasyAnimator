package model.comparators;


import java.util.Comparator;
import model.shapes.Shape;

/**
 * The ShapeTimeComparator compares the shapes in terms of the time that they appear in the
 * animation.
 */
public class ShapeTimeComparator implements Comparator<Shape> {

  /**
   * The compare method compares the times that the shapes appear returns a positive number if
   * shape1 appears after shape2 returns a negative number if shape2 appears after shape2 returns
   * zero if both shapes appear at the same time.
   *
   * @param shape1 The first model.shape to be compared.
   * @param shape2 The second model.shape to be compared.
   * @return An integer representing which model.shape appears first.
   */
  @Override
  public int compare(Shape shape1, Shape shape2) {
    return shape1.getAppearsAt() - shape2.getAppearsAt();
  }

}
