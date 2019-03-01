package view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import model.shapes.Shape;
import view.visitors.SVGRenderVisitor;


/**
 * The SVG view is the view that will take in the list of shapes from the controller as data and
 * turn that data in to an animatable SVG file.
 */
public class SVGView implements ITextView {

  /**
   * Sets the view to be visible. If the outputMode is "out" it prints to System.out, if the
   * outputMode is an .svg file than it writes to the given file. If it exists, it overwrites the
   * file.
   */
  @Override
  public void renderText(List<Shape> shapes, int ticksPerSecond, String outputMode,
      Appendable appendable, boolean hasLoop)
      throws IllegalArgumentException {
    if (shapes == null || ticksPerSecond < -1 || outputMode == null) {
      throw new IllegalArgumentException("Invalid parameter.");
    }
    StringBuilder svg = new StringBuilder();
    svg.append("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n\t\txmlns=\"" +
        "http://www.w3.org/2000/svg\">");
    if (hasLoop) {
      svg.append("<rect>\n" + "\t<animate id=\"base\" begin=\"0;base.end\" dur=\""
          + getLoopDuration(shapes, ticksPerSecond) + "ms\" attributeName=\"visibility\" from"
          + "=\"hide\" to=\"hide\"/>\n" + "</rect>");
    }
    for (Shape shape : shapes) {
      svg.append(new SVGRenderVisitor().apply(shape));
    }
    svg.append("\n</svg>");

    if (outputMode.equals("out")) {
      IView.appendTry(appendable, svg.toString());
      System.out.print(appendable);
    } else if (outputMode.endsWith(".svg")) {
      File svgFile = new File(outputMode);
      try {
        FileWriter fileWriter = new FileWriter(svgFile);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(svg.toString());
        writer.close();
      } catch (IOException e) {
        IView.appendTry(appendable, "Input output error.");
        System.out.print(appendable);
      }
    } else {
      throw new IllegalArgumentException("Invalid output mode.");
    }
  }

  /**
   * Method which gets the loop duration of an animation in milliseconds with the given list of
   * shapes and ticks per second. The loop duration is longer than the animation duration by 1000
   * milliseconds.
   *
   * @param shapeList The list of shapes that are in an animation that the duration is calculated
   *      for.
   * @param ticksPerSecond The ticks per second in the animation for calculating the time of the
   *      duration.
   * @return float representing the milliseconds of loop duration
   */
  private static float getLoopDuration(List<Shape> shapeList, int ticksPerSecond) {
    int maxTick = 0;
    for (Shape s : shapeList) {
      if (s.getDisappearsAt() > maxTick) {
        maxTick = s.getDisappearsAt();
      }
    }

    // add 1000 to ensure that the loop time is longer than the end of one animation
    return 1000 + (maxTick * ticksPerSecond * 1000);
  }
}
