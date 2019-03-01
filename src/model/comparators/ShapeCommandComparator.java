package model.comparators;


import java.util.Comparator;
import model.shapes.Shape;

/**
 * The ShapeCommandComparator compares the shapes based on the time of their first executable
 * command.
 */
public class ShapeCommandComparator implements Comparator<Shape> {

  /**
   * Compares two shapes based on the time of their first executable command's start time Return
   * positive if shape1's first command runs after shape2's first command Return negative if
   * shape1's first command runs before shape2's first command Returns zero if both are equal.
   *
   * @param shape1 The first model.shape to be compared.
   * @param shape2 The second model.shape to be compared.
   * @return An integer representing which Shape is greater under this comparators conditions.
   */
  @Override
  public int compare(Shape shape1, Shape shape2) {
    return shape1.animatesAt() - shape2.animatesAt();
  }
}
