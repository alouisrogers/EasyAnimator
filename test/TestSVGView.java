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
import view.SVGView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestSVGView {

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
  SVGView svgView;

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

    rect1 = new Rectangle("R", 25, 30, new Point(50, 50), 0,
        Color.blue, 0, 20, 100, rect1Commands);
    oval1 = new Oval("O", 50, 20, new Point(40, 10), 0,
        Color.yellow, 0, 16, 100, oval1Commands);

    shapes = new ArrayList<Shape>();
    shapes.add(rect1);
    shapes.add(oval1);

    ap1 = new StringBuffer();

    svgView = new SVGView();
  }

  @Test
  public void testTimerBased() {
    //assertFalse(svgView.timerBased());
  }

  @Test
  public void testShow() {
    //svgView.show(shapes, 1, "out", ap1);
    //assertEquals("", ap1.toString());
  }
}
