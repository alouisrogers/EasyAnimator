package view.visitors;

import java.util.Map;
import model.commands.ColorFade;
import model.commands.Command;
import model.commands.Move;
import model.commands.Rotation;
import model.commands.Scale;
import model.shapes.Shape;

/**
 * The SVG CommandRender Visitor visits the appropriate commands and then creates a string based
 * output in the format matching the syntax expected for a jar file.
 */
public class SVGCommandRender implements CommandVisitor<String> {

  private int ticksPerSecond;

  /**
   * Constructs a new SVGCommandRender with the given ticks per second.
   */
  public SVGCommandRender(int ticksPerSecond) {
    this.ticksPerSecond = ticksPerSecond;
  }

  /**
   * Returns an SVG formatted command with the given attributeName, fromAttribute, toAttribute,
   * startTime and endTime.
   *
   * @param attributeName attribute name being animated
   * @param fromAttribute what the attribute begins at
   * @param toAttribute what the attribute becomes
   * @param startTime when the animation begins
   * @param endTime when the animation ends
   * @return String command formatted.
   */
  private String svgString(String attributeName, float fromAttribute, float toAttribute,
      double startTime, double endTime) {
    return "\t\t<animate attributeType=\"xml\" begin=\"" + (startTime * 1000) + "ms\" dur=\""
        + (endTime - startTime) * 1000
        + "ms\" " + "attributeName=\"" + attributeName +
        "\" from=\"" + fromAttribute + "\" to=\"" + toAttribute + "\" fill=\"freeze\"/>";
  }

  @Override
  public String visitColorFade(ColorFade colorFade, Shape shape) {
    Map<String, Float> data = colorFade.getData();
    return this.svgString("fill", data.get("oldColor"), data.get("newColor"),
        (double) colorFade.getStartTime() / (double) this.ticksPerSecond,
        (double) colorFade.getEndTime() / (double) this.ticksPerSecond);
  }

  @Override
  public String visitMove(Move move, Shape shape) {
    Map<String, Float> data = move.getData();
    Map<String, String> attributes = shape.fieldToSvg();
    return svgString(attributes.get("xLocation"), data.get("fromX"), data.get("toX"),
        (double) move.getStartTime() / (double) this.ticksPerSecond,
        (double) move.getEndTime() / (double) this.ticksPerSecond)
        + "\n" + svgString(attributes.get("yLocation"), data.get("fromY"), data.get("toY"),
        (double) move.getStartTime() / (double) this.ticksPerSecond,
        (double) move.getEndTime() / (double) this.ticksPerSecond);
  }

  @Override
  public String visitScale(Scale scale, Shape shape) {
    Map<String, Float> data = scale.getData();
    Map<String, String> attributes = shape.fieldToSvg();
    return svgString(attributes.get("width"), data.get("fromSx"), data.get("toSx"),
        (double) scale.getStartTime() / (double) this.ticksPerSecond,
        (double) scale.getEndTime() / (double) this.ticksPerSecond)
        + "\n" + svgString(attributes.get("height"), data.get("fromSy"), data.get("toSy"),
        (double) scale.getStartTime() / (double) this.ticksPerSecond,
        (double) scale.getEndTime() / (double) this.ticksPerSecond);
  }

  /**
   * The visitor rotation visits a rotation object and applies the functionality.
   */
  @Override
  public String visitRotation(Rotation rotation, Shape shape) {
    return null;
  }


  /**
   * Applies this function to the given arguments.
   *
   * @param command the first function argument
   * @param shape the second function argument
   * @return the function result
   */
  @Override
  public String apply(Command command, Shape shape) {
    return "\n" + command.accept(this, shape);
  }
}
