import java.awt.Color;
import javafx.scene.shape.Circle;
import model.commands.ColorFade;
import model.commands.Command;
import model.Point;
import model.commands.Move;
import model.commands.Scale;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCommand {

  Shape circle = new Oval("C", 100, 100, new Point(50, 50), 0,
      java.awt.Color.BLUE, 0, 6, 100);
  Command move = new Move(new Point(50, 50), new Point(50, 100), 0, 10);
  Command scale = new Scale(50, 50, 25, 25, 5, 15);
  Command colorFade = new ColorFade(Color.BLUE, Color.GREEN, 10, 50);

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithNegativeDuration() {
    move = new Move(new Point(-50, 50), new Point(50, 100), 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithNegativeStartTime() {
    move = new Move(new Point(50, 50), new Point(50, 100), -10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithNegativeEndTime() {
    move = new Move(new Point(50, 50), new Point(50, 100), -20, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithNegativeFromPoint() {
    move = new Move(new Point(-50, -50), new Point(50, 100), -20, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithNegativeToPoint() {
    move = new Move(new Point(50, 50), new Point(-50, -100), -20, -10);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFromColorFade() {
    new ColorFade(null, Color.GREEN, 0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToColorFade() {
    new ColorFade(Color.GREEN, null, 0, 10);
  }


  @Test
  public void testGetStartTime() {
    assertEquals(0, move.getStartTime());
    assertEquals(5, scale.getStartTime());
    assertEquals(10, colorFade.getStartTime());
  }

  @Test
  public void testGetEndTime() {
    assertEquals(10, move.getEndTime());
    assertEquals(15, scale.getEndTime());
    assertEquals(50, colorFade.getEndTime());
  }

  @Test
  public void testToStringFull() {
    assertEquals("Shape C moves from (50.0, 50.0) to (50.0, 100.0)"
            + " from t=0.0s to t=10.0s\n",
        move.toString(circle, 1));
    assertEquals("Shape C scales from X Radius: 50.0, Y Radius: 50.0 to "
            + "X Radius: 25.0, Y Radius: 25.0 from t=5.0s to t=15.0s\n",
        scale.toString(circle, 1));
    assertEquals("Shape C changes color from (0.0,0.0,1.0) to "
            + "(0.0,1.0,0.0) from t=10.0s to t=50.0s\n",
        colorFade.copy().toString(circle, 1));
  }

  @Test
  public void testCopy() {
    assertEquals(move.toString(circle, 10), move.copy().toString(circle, 10));
    assertEquals(scale.toString(circle, 15), scale.copy().toString(circle, 15));
    assertEquals(colorFade.copy().toString(circle, 16),
        colorFade.copy().toString(circle, 16));
  }

  @Test
  public void testExecuteMove() {
    circle = new Oval("C", 50, 50, new Point(50, 50), 0,
        Color.BLUE, 0, 6, 100);
    assertEquals("Name: C\n"
        + "Type: oval\n"
        + "Center: (50.0, 50.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\n"
        + "Disappears at t=100.0s\n\n", circle.toString(1));
    Shape newShape = move.execute(circle, 50);
    assertEquals(
        "Name: C\n"
            + "Type: oval\n"
            + "Center: (50.0, 100.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\n"
            + "Disappears at t=100.0s\n\n", newShape.toString(1));
  }

  @Test
  public void testExecutePartialMove() {
    circle = new Oval("C", 50, 50, new Point(50, 50), 0,
        Color.BLUE, 0, 6, 100);
    assertEquals("Name: C\n"
        + "Type: oval\n"
        + "Center: (50.0, 50.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\n"
        + "Disappears at t=100.0s\n\n", circle.toString(1));
    Shape newShape = move.execute(circle, 5);
    assertEquals(
        "Name: C\n"
            + "Type: oval\n"
            + "Center: (50.0, 75.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\n"
            + "Disappears at t=100.0s\n\n", newShape.toString(1));
  }

  @Test
  public void testExecuteScale() {
    circle = new Oval("C", 50, 50, new Point(50, 50), 0,
        Color.BLUE, 0, 6, 100);
    assertEquals("Name: C\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\nDisappears at t=100.0s\n\n", circle.toString(1));
    Shape newCircle = scale.execute(circle, 100);
    assertEquals(
        "Name: C\nType: oval\n"
            + "Center: (50.0, 50.0), X radius: 25.0, Y radius: 25.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6.0s\nDisappears at t=100.0s\n\n", newCircle.toString(1));
    Shape square = new Rectangle("S", 10, 10, new Point(20, 80), 0,
        java.awt.Color.RED, 0, 2, 100);
    assertEquals("Name: S\nType: rectangle\n"
        + "Corner: (20.0, 80.0), Width: 10.0, Height: 10.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=2.0s\nDisappears at t=100.0s\n\n", square.toString(1));
    Shape newSquare = scale.execute(square, 100);
    assertEquals("Name: S\nType: rectangle\n"
        + "Corner: (20.0, 80.0), Width: 25.0, Height: 25.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=2.0s\nDisappears at t=100.0s\n\n", newSquare.toString(1));
  }

  @Test
  public void testExecuteColorFade() {
    circle = new Oval("C", 50, 50, new Point(50, 50), 0,
        Color.BLUE, 0, 6, 100);
    assertEquals("Name: C\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6.0s\nDisappears at t=100.0s\n\n", circle.toString(1));
    Shape newCircle = colorFade.execute(circle, 100);
    assertEquals("Name: C\nType: oval\n"
        + "Center: (50.0, 50.0), X radius: 50.0, Y radius: 50.0, Color: (0.0,1.0,0.0)\n"
        + "Appears at t=6.0s\nDisappears at t=100.0s\n\n", newCircle.toString(1));
  }

}
