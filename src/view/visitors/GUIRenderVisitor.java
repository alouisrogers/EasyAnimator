package view.visitors;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D.Float;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;

/**
 * The Gui render visitor visits a shape and render the shape using the java graphics library and
 * object based on the shape that is being visited.
 */
public class GUIRenderVisitor implements ShapeVisitor<Void> {

  Graphics2D graphics;

  /**
   * Constructs a new GUIRenderVisitor with the given graphics.
   */
  public GUIRenderVisitor(Graphics graphics) {
    this.graphics = (Graphics2D) graphics;
  }

  /**
   * Render a rectangle in the given state of the rectangle
   *
   * @param rectangle A rectangle to be visited.
   * @return A type R.
   */
  @Override
  public Void visitRectangle(Rectangle rectangle) {
    java.awt.geom.Rectangle2D rect = new java.awt.Rectangle((int) rectangle.getLocation().getX(),
        (int) rectangle.getLocation().getY(),
        (int) rectangle.getWidth(), (int) rectangle.getHeight());
    graphics.setColor(rectangle.getColor());
    graphics.fill(rect);
    graphics.rotate(Math.toRadians(rectangle.getAngle()));
    return null;
  }

  /**
   * Renders an oval at the given state of the oval.
   *
   * @param oval An oval to be visited.
   * @return A type R.
   */
  @Override
  public Void visitOval(Oval oval) {
    java.awt.geom.Ellipse2D.Float ovl = new Float((int) oval.getLocation().getX(),
        (int) oval.getLocation().getY(),
        (int) oval.getWidth(), (int) oval.getHeight());
    graphics.setColor(oval.getColor());
    graphics.fill(ovl);
    graphics.rotate(Math.toRadians(oval.getAngle()));
    return null;
  }

  /**
   * Apply the render to a shape.
   *
   * @param shape The shape that the function is being applied to.
   * @return A type R.
   */
  @Override
  public Void apply(Shape shape) {
    return shape.accept(this);
  }
}
