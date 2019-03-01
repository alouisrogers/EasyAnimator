import model.AnimatorModel;
import model.AnimatorModelImpl;
import model.commands.Command;
import model.Point;
import java.awt.Color;
import model.commands.Move;
import model.commands.Scale;
import model.shapes.Shape;
import java.util.ArrayList;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

import model.commands.ColorFade;
import model.shapes.Oval;
import model.shapes.Rectangle;

/**
 * represents the test suite for the animator.
 */

public class TestAnimatorModel {

  @Test
  public void testAddShape() {
    AnimatorModel model = new AnimatorModelImpl();
    String output = "";
    for (Shape shape : model.getShapes()) {
      output += shape.toString(1);
    }
    assertEquals("", output);

    output = "";

    model.addShape(new Oval("C", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    for (Shape shape : model.getShapes()) {
      output += shape.toString(1);
    }
    assertEquals("Name: C\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\nDisappears at t=100.0s\n\n", output);

    output = "";

    model.addShape(new Rectangle("S", 10, 10, new Point(20, 80), 0,
        java.awt.Color.RED, 0, 2, 100));
    for (Shape shape : model.getShapes()) {
      output += shape.toString(1);
    }
    assertEquals("Name: S\nType: rectangle\n"
        + "Corner: (20.0, 80.0), Width: 10.0, Height: 10.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=2.0s\nDisappears at t=100.0s\n\n"
        + "Name: C\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\nDisappears at t=100.0s\n"
        + "\n", output);

    assertEquals(2, model.getShapes().size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddExistingShapes() {
    AnimatorModel model = new AnimatorModelImpl();
    model.addShape(new Oval("C", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    model.addShape(new Oval("C", 160, 10, new Point(150, 50), 0,
        java.awt.Color.BLUE, 0, 6, 56));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullShape() {
    AnimatorModel model = new AnimatorModelImpl();
    model.addShape(null);
  }

  @Test
  public void testAddCommand() {
    Command move = new Move(new Point(50, 50), new Point(50, 100), 0, 10);
    Command colorFade = new ColorFade(Color.BLUE, Color.GREEN, 10, 50);

    AnimatorModel model = new AnimatorModelImpl();
    model.addShape(new Oval("C", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 50.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getShapes().get(0).getShapeState(100).toString(1));

    model.addCommand("C", move);

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 100.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getShapes().get(0).getShapeState(100).toString(1));

    model.addCommand("C", colorFade);

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 100.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,1.0,0.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getShapes().get(0).getShapeState(100).toString(1));
  }

  @Test
  public void testRemoveShape() {
    Command move = new Move(new Point(50, 50), new Point(50, 100), 0, 10);
    Command colorFade = new ColorFade(Color.BLUE, Color.GREEN, 10, 50);

    AnimatorModel model = new AnimatorModelImpl();
    model.addShape(new Oval("C1", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    model.addShape(new Oval("C2", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    model.addShape(new Oval("C3", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    model.addShape(new Oval("C4", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    model.addShape(new Oval("C5", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));

    assertEquals(5, model.getShapes().size());
    model.removeShape("C1");
    assertEquals(4, model.getShapes().size());
    model.removeShape("C3");
    assertEquals(3, model.getShapes().size());
    model.removeShape("C5");
    assertEquals(2, model.getShapes().size());
  }

  @Test
  public void testMaxTick() {
    AnimatorModel model = new AnimatorModelImpl();
    model.addShape(new Oval("C1", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 65));
    assertEquals(65, model.getMaxTick());
    model.addShape(new Oval("C2", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 43));
    assertEquals(65, model.getMaxTick());
    model.addShape(new Oval("C3", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 32));
    assertEquals(65, model.getMaxTick());
    model.addShape(new Oval("C4", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 96));
    assertEquals(96, model.getMaxTick());
    model.addShape(new Oval("C5", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 106));
    assertEquals(106, model.getMaxTick());
  }

  @Test
  public void testGetShapes() {
    AnimatorModel model = new AnimatorModelImpl();
    model.addShape(new Oval("C1", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 65));
    model.addShape(new Oval("C2", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 43));

    String output = "";
    List<Shape> shapes = new ArrayList<>();
    for (Shape shape : shapes) {
      output += shape.toString(1);
    }
    assertEquals("", output);

    output = "";
    shapes = model.getShapes();
    for (Shape shape : shapes) {
      output += shape.toString(1);
    }
    assertEquals("Name: C1\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\n"
        + "Disappears at t=65.0s\n\nName: C2\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\nDisappears at t=43.0s\n\n", output);
  }

  @Test
  public void testAnimatorGetState() {
    AnimatorModel model = new AnimatorModelImpl();
    Command move = new Move(new Point(50, 50), new Point(50, 100), 0, 10);
    Command colorFade = new ColorFade(Color.BLUE, Color.GREEN, 10, 30);
    Command scale = new Scale(100, 100, 50, 50, 5, 15);
    model.addShape(new Oval("C", 100, 100, new Point(50, 50), 0,
        java.awt.Color.BLUE, 0, 6, 100));
    model.addCommand("C", move);
    model.addCommand("C", colorFade);
    model.addCommand("C", scale);

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 50.0), X radius: 100.0, Y radius: 100.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getAnimatorState(0).get(0).toString(1));

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 100.0), X radius: 75.0, Y radius: 75.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getAnimatorState(10).get(0).toString(1));

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 100.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,1.0,0.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getAnimatorState(30).get(0).toString(1));

    assertEquals("Name: C\nType: oval\n"
            + "Center: (50.0, 100.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.5,0.5)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n",
        model.getAnimatorState(20).get(0).toString(1));


  }


}
