import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Point;
import model.commands.ColorFade;
import model.commands.Command;
import model.commands.Move;
import model.commands.Scale;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;
import view.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestTextView {

  Shape rect1;
  Shape oval1;
  Command move1;
  Command scale1;
  Command changeColor1;
  Command move2;
  LinkedList rect1Commands;
  LinkedList oval1Commands;
  List shapes;
  Appendable ap1;
  TextView textView1;

  /**
   * Initializes data for testing.
   */
  @Before
  public void init() {
    move1 = new Move(new Point(50, 50), new Point(75, 75), 0, 10);
    scale1 = new Scale(25, 30, 50, 30, 7, 15);
    changeColor1 = new ColorFade(Color.yellow, Color.green, 5, 10);
    move2 = new Move(new Point(40, 10), new Point(30, 30), 6, 14);
    rect1Commands = new LinkedList<Command>();
    rect1Commands.add(move1);
    rect1Commands.add(scale1);
    oval1Commands = new LinkedList<Command>();
    oval1Commands.add(changeColor1);
    oval1Commands.add(move2);

    rect1 = new Rectangle("R", 25, 30, new Point(50, 50), 0, Color.blue, 0,
        0, 20, rect1Commands);
    oval1 = new Oval("O", 50, 20, new Point(40, 10), 0, Color.yellow, 0,
        0, 16, oval1Commands);

    shapes = new ArrayList<Shape>();
    shapes.add(rect1);
    shapes.add(oval1);

    ap1 = new StringBuffer();

    textView1 = new TextView();
  }

  @Test
  public void testTimeBasedView() {
    // assertFalse(textView1.timerBased());
  }

  @Test
  public void testShow() {
    //textView1.show(shapes, 5, "out", ap1);
    assertEquals("Name: R\n" +
            "Type: rectangle\n" +
            "Corner: (50.0, 50.0), Width: 25.0, Height: 25.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=0.0s\n" +
            "Disappears at t=4.0s\n" +
            "\n" +
            "Name: O\n" +
            "Type: oval\n" +
            "Center: (40.0, 10.0), X radius: 50.0, Y radius: 20.0, Color: (1.0,1.0,0.0)\n" +
            "Appears at t=0.0s\n" +
            "Disappears at t=3.0s\n" +
            "\n" +
            "Shape R moves from (50.0, 50.0) to (75.0, 75.0) from t=0.0s to t=2.0s\n" +
            "Shape R scales from width: 25.0, length: 30.0 to width: 50.0, length: 30.0 from" +
            " t=1.4s to t=3.0s\n" +
            "Shape O changes color from (255,255,0) to (0,255,0) from t=1.0s to t=2.0s\n" +
            "Shape O moves from (40.0, 10.0) to (30.0, 30.0) from t=1.2s to t=2.8s\n",
        ap1.toString());
  }
}
