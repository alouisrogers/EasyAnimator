package model.comparators;

import java.util.Comparator;
import model.shapes.Shape;

/**
 * Compares the layers of two shapes for overlapping.
 */
public class LayerComparator implements Comparator<Shape> {

  /**
   * Compares two shapes based on the layer, Return
   * positive if shape1's first command runs after shape2's first command Return negative if
   * shape1's first command runs before shape2's first command Returns zero if both are equal.
   *
   * @param o1 The first model.shape to be compared.
   * @param o2 The second model.shape to be compared.
   * @return An integer representing which Shape is greater under this comparators conditions.
   */
  @Override
  public int compare(Shape o1, Shape o2) {
    return o1.getLevel() - o2.getLevel();
  }
}
