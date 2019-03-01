package view.visitors;

import java.util.List;
import model.commands.Command;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;

/**
 * The SVGRenderVisitor visits a shape and creates a string representing the proper svg format of
 * that shape.
 */
public class SVGRenderVisitor implements ShapeVisitor<String> {

  /**
   * Visits a rectangle.
   *
   * @param rectangle A rectangle to be visited.
   * @return A type R.
   */
  @Override
  public String visitRectangle(Rectangle rectangle) {
    CommandVisitor<String> svgCreator = new SVGCommandRender(1);

    List<Command> commands = rectangle.getCommands();
    StringBuilder commandSvg = new StringBuilder();

    for (Command command : commands) {
      commandSvg.append(svgCreator.apply(command, rectangle));
    }

    return "<rect id=\"" + rectangle.getShapeName() + "\" x=\"" + rectangle.getLocation().getX()
        + "\" y=\"" + rectangle.getLocation().getY() + "\" width= \"" + rectangle.getWidth()
        + "\" height= \"" + rectangle.getHeight()
        + "\" fill=\"rgb(" + rectangle.getColor().getRed() + "," + rectangle.getColor().getGreen()
        + "," + rectangle.getColor().getBlue() + ")\" visibility=\"visible\">"
        + commandSvg.toString()
        + "\n</rect>\n";
  }

  /**
   * Visits an oval.
   *
   * @param oval An oval to be visited.
   * @return A type R.
   */
  @Override
  public String visitOval(Oval oval) {
    CommandVisitor<String> svgCreator = new SVGCommandRender(1);

    List<Command> commands = oval.getCommands();
    StringBuilder commandSvg = new StringBuilder();

    for (Command command : commands) {
      commandSvg.append(svgCreator.apply(command, oval));
    }

    return "<ellipse id=\"" + oval.getShapeName() + "\" cx=\"" + oval.getLocation().getX()
        + "\" cy=\"" + oval.getLocation().getY()
        + "\" rx=\"" + oval.getWidth() + "\" ry=\"" + oval.getHeight()
        + "\" fill=\"rgb(" + oval.getColor().getRed() + "," + oval.getColor().getGreen()
        + "," + oval.getColor().getBlue() + ")\" visibility=\"visible\">"
        + commandSvg.toString()
        + "\n</ellipse>\n";
  }

  /**
   * Apply a function to a shape.
   *
   * @param shape The shape that the function is being applied to.
   * @return A type R.
   */
  @Override
  public String apply(Shape shape) {
    return "\n" + shape.accept(this);
  }
}
