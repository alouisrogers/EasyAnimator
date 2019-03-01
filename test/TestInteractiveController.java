
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.StringWriter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestInteractiveController {

  MockInteractiveController mockController = new MockInteractiveController(new StringWriter());

  @Test
  public void testInput() {
    KeyEvent space = new KeyEvent((Component) mockController.getView(), 401, 10, 0, 32, ' ', 1);
    KeyEvent pause = new KeyEvent((Component) mockController.getView(), 400, 10, 0, 0, 'p', 0);
    KeyEvent restart = new KeyEvent((Component) mockController.getView(), 400, 10000, 0, 0, 'r', 0);
    KeyEvent svg = new KeyEvent((Component) mockController.getView(), 400, 10, 0, 0, 's', 0);
    KeyEvent loop = new KeyEvent((Component)mockController.getView(), 400, 10, 0,0, 'l', 0);
    KeyEvent left = new KeyEvent((Component)mockController.getView(), 401, 10, 0, 37, '\uFFFF', 1);
    KeyEvent right = new KeyEvent((Component)mockController.getView(), 401, 10, 0, 39, '\uFFFF', 1);

    KeyListener kbd = mockController.getKeyBoardListener();
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyPressed(space);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyPressed(space);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(pause);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(pause);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(pause);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(restart);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    mockController.animate();
    mockController.animate();
    mockController.animate();
    mockController.animate();
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(restart);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(svg);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(loop);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: true\n",
        mockController.getAppendable().toString());
    kbd.keyTyped(loop);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: true\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyPressed(left);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: true\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 0, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyPressed(right);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: true\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 0, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
    kbd.keyPressed(right);
    mockController.animate();
    kbd.keyPressed(right);
    mockController.animate();
    kbd.keyPressed(right);
    mockController.animate();
    assertEquals("Frame: 0, TicksPerSecond: 0 , Pause: false, Play: false, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: false, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 5, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 6, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: true\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 0, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 1, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 2, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 3, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n"
            + "Frame: 4, TicksPerSecond: 0 , Pause: true, Play: true, Loop: false\n",
        mockController.getAppendable().toString());
  }
}
