import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.util.LinkedList;
import model.Point;
import model.commands.ColorFade;
import model.commands.Command;
import model.commands.Move;
import model.commands.Scale;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;
import view.visitors.SVGRenderVisitor;
import view.visitors.ShapeVisitor;

import static junit.framework.TestCase.assertEquals;

public class TestShape {

  Shape rect1;
  Shape oval1;
  Command move1;
  Command scale1;
  Command changeColor1;
  Command move2;
  LinkedList rect1Commands;
  LinkedList oval1Commands;

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

    rect1 = new Rectangle("R", 25, 30, new Point(50, 50), 0, Color.blue,
        0, 0, 20, rect1Commands);
    oval1 = new Oval("O", 50, 20, new Point(40, 10), 0, Color.yellow,
        0, 0, 16, oval1Commands);

  }

  // Tests rectangle with negative height and width.
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor() {
    new Rectangle("R", -4, 40, new Point(40, 40), 0,
        Color.blue, 0, 0, 20, rect1Commands);
  }

  // Tests oval with negative x and y radius.
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor2() {
    new Oval("Oval", -10, -20, new Point(3, 4), 0,
        Color.red, 0, 0, 20, oval1Commands);
  }

  // Tests rectangle with null id given.
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor3() {
    new Rectangle(null, 4, 40, new Point(40, 40), 0,
        Color.blue, 0, 0, 20, rect1Commands);
  }

  // Tests oval with null point given.
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor4() {
    new Oval("Oval", 4, 5, null, 0,
        Color.red, 0, 0, 20, oval1Commands);
  }

  // Tests null color.
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor5() {
    new Rectangle("REct", 5, 10, new Point(0, 0), 0,
        null, 0, 0, 10, rect1Commands);
  }

  // Tests negative appears and disappears times
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor6() {
    new Oval("O", 10, 20, new Point(3, 4), 0,
        Color.red, 0, -5, -10, oval1Commands);
  }

  // Tests null commands
  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor7() {
    new Rectangle("Rectangle", 10, 20, new Point(3, 4), 0,
        Color.red, 0, 0, 20, null);
  }

  @Test
  public void testCopy() {
    LinkedList rect1CommandsCopy = new LinkedList<Command>();
    Command changeColor1Copy = new ColorFade(Color.yellow, Color.green, 5, 10);
    Command move2Copy = new Move(new Point(40, 10), new Point(30, 30), 6, 14);
    rect1CommandsCopy.add(changeColor1Copy);
    rect1CommandsCopy.add(move2Copy);
    assertEquals(new Rectangle("R", 25, 30, new Point(50, 50), 0,
        Color.blue, 0,
        0, 20, rect1CommandsCopy), rect1.copy());
  }

  @Test
  public void testGetShapeName() {
    assertEquals("R", rect1.getShapeName());
    assertEquals("O", oval1.getShapeName());
  }

  @Test
  public void testGetWidth() {
    assertEquals(25.0, rect1.getWidth());
    assertEquals(50.0, oval1.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(30.0, rect1.getHeight());
    assertEquals(10.0, oval1.getHeight());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.blue, rect1.getColor());
    assertEquals(Color.yellow, oval1.getColor());
  }

  @Test
  public void testGetLocation() {
    assertEquals(new Point(50, 50), rect1.getLocation());
    assertEquals(new Point(40, 10), oval1.getLocation());
  }

  @Test
  public void testGetAppearsAt() {
    assertEquals(0, rect1.getAppearsAt());
    assertEquals(0, oval1.getAppearsAt());
  }

  @Test
  public void testGetDisappearsAt() {
    assertEquals(20, rect1.getDisappearsAt());
    assertEquals(16, oval1.getDisappearsAt());
  }

  @Test
  public void testGetCommands() {
    assertEquals(rect1Commands, rect1.getCommands());
    assertEquals(oval1Commands, oval1.getCommands());
  }

  @Test
  public void testGetShapeState() {
    assertEquals(rect1, rect1.getShapeState(0));
  }

  @Test
  public void testToString() {
    assertEquals("Name: R\n" +
        "Type: rectangle\n" +
        "Corner: (50.0, 50.0), Width: 25.0, Height: 25.0, Color: (0.0,0.0,1.0)\n" +
        "Appears at t=0.0s\n" +
        "Disappears at t=4.0s\n\n", rect1.toString(5));
    assertEquals("Name: O\n" +
        "Type: oval\n" +
        "Center: (40.0, 10.0), X radius: 50.0, Y radius: 20.0, Color: (1.0,1.0,0.0)\n" +
        "Appears at t=0\n" +
        "Disappears at t=16\n\n", oval1.toString(-1));
  }

  @Test
  public void testAnimatesAt() {
    assertEquals(0, rect1.animatesAt());
    assertEquals(5, oval1.animatesAt());
  }

  @Test
  public void testCreateShape() {
    assertEquals(new Rectangle("R", 20, 20, new Point(20, 20), 0, Color.blue,
            0, 0, 20, rect1Commands),
        rect1.createShape(20, 20, new Point(20, 20), 0, Color.blue, 0));
    assertEquals(new Oval("O", 20, 20, new Point(10, 10), 0, Color.pink,
            0, 0, 15, oval1Commands),
        oval1.createShape(20, 20, new Point(10, 10), 0, Color.pink, 0));
  }

  @Test
  public void testAccept() {
    ShapeVisitor<String> textRender = new SVGRenderVisitor();
    assertEquals(
        "\t<rect id=\"R\" x=\"50.0\" y=\"50.0\" width= \"25.0\" height= \"30.0\" "
            + "fill=\"rgb(0,0,255)\" visibility=\"visible\" >\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"10000.0ms\""
            + " attributeName=\"location\" from=\"50.0\" to=\"75.0\" fill=\"remove\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"10000.0ms\" "
            + "attributeName=\"location\" from=\"50.0\" to=\"75.0\" fill=\"remove\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"8000.0ms\" "
            + "attributeName=\"width\" from=\"25.0\" to=\"50.0\" fill=\"remove\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"7000.0ms\" dur=\"8000.0ms\" "
            + "attributeName=\"length\" from=\"30.0\" to=\"30.0\" fill=\"remove\" />\n"
            + "\t</rect>", rect1.accept(textRender));
    assertEquals(
        "\t<ellipse id=\"O\" cx=\"40.0\" cy=\"10.0\" rx=\"50.0\" ry=\"20.0\""
            + " fill=\"rgb(255,255,0)\" visibility=\"visible\" >\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"5000.0ms\" "
            + "attributeName=\"fill\" from=\"-256.0\" to=\"-1.6711936E7\" fill=\"remove\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"6000.0ms\" dur=\"8000.0ms\" "
            + "attributeName=\"location\" from=\"40.0\" to=\"30.0\" fill=\"remove\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"6000.0ms\" dur=\"8000.0ms\""
            + " attributeName=\"location\" from=\"10.0\" to=\"30.0\" fill=\"remove\" />\n"
            + "\t</ellipse>", oval1.accept(textRender));
  }
}
