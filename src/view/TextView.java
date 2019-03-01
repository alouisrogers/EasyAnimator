package view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import model.comparators.ShapeCommandComparator;
import model.shapes.Shape;

/**
 * the textview creates a text description of the animation that can be saved to a .txt file or can
 * be printed to the system.
 */
public class TextView implements ITextView {

  @Override
  public void renderText(List<Shape> shapes, int ticksPerSecond, String outputMode,
      Appendable appendable, boolean hasLoop) {
    if (outputMode.equals("out")) {
      IView.appendTry(appendable, this.getAnimatorDescription(ticksPerSecond, shapes));
      System.out.print(appendable);
    } else if (outputMode.endsWith(".txt")) {
      File textFile = new File(outputMode);
      try {
        FileWriter fileWriter = new FileWriter(textFile);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(this.getAnimatorDescription(ticksPerSecond, shapes));
        fileWriter.close();
      } catch (IOException e) {
        IView.appendTry(appendable, "Input output error.");
        System.out.print(appendable);
      }
    }
  }

  /**
   * Gets a string description of the initial state of the shapes and then all the commands that
   * have been run up to the given time specified.
   *
   * @param ticksPerSecond The time for which all the commands are going to be shown up until.
   * @return A String representing the initial state of the shape after commands up to the time
   */
  private String getAnimatorDescription(int ticksPerSecond, List<Shape> shapeList)
      throws IllegalArgumentException {
    Comparator shapeComp = new ShapeCommandComparator();
    LinkedList<Shape> descriptions = new LinkedList<Shape>();
    StringBuilder gameState = new StringBuilder();

    for (Shape shape : shapeList) {
      gameState.append(shape.toString(ticksPerSecond));
      descriptions.add(shape.copy());
    }

    while (descriptions.size() > 0) {
      try {
        descriptions.sort(shapeComp);
        Shape shape = descriptions.peek();
        gameState.append(shape.nextCommand().toString(shape, ticksPerSecond));
      } catch (IllegalStateException e) {
        descriptions.poll();
      }
    }

    return gameState.toString() + "\n";
  }
}
