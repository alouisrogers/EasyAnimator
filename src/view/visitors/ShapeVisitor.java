package view.visitors;

import java.util.function.Function;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;

/**
 * A Visitor visits a a shape and executes some method on it depending on the extending class. This
 * allows function to be created over union data.
 *
 * @param <R> The return type of the method.
 */
public interface ShapeVisitor<R> extends Function<Shape, R> {

  /**
   * Visits a rectangle.
   *
   * @param rectangle A rectangle to be visited.
   * @return A type R.
   */
  R visitRectangle(Rectangle rectangle);

  /**
   * Visits an oval.
   *
   * @param oval An oval to be visited.
   * @return A type R.
   */
  R visitOval(Oval oval);

  /**
   * Apply a function to a shape.
   *
   * @param shape The shape that the function is being applied to.
   * @return A type R.
   */
  R apply(Shape shape);
}
