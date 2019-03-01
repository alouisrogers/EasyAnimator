

import controller.Controller;
import controller.InteractiveController;
import controller.KeyboardListener;
import controller.MouseInput;
import controller.SliderListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import java.util.Scanner;
import javax.swing.Timer;

import model.AnimatorModel;
import model.AnimatorModelImpl;
import view.HybridView;
import view.ITextView;
import view.IView;
import view.SVGView;

public class MockInteractiveController implements Controller {

  private HybridView view;
  private AnimatorModel model;
  private boolean pause;
  private boolean loop;
  private int frameCount;
  private int ticksPerSecond;
  private Timer timer;
  private MouseInput mouseListener;
  private SliderListener sliderListener;
  private boolean play = false;
  private KeyboardListener kbd;

  Appendable ap;

  public MockInteractiveController(Appendable ap) {
    this.model = new AnimatorModelImpl();
    this.view = new HybridView();
    this.pause = false;
    this.loop = false;
    this.view.addStatusBar(pause, loop, ticksPerSecond);

    this.ap = ap;

    this.configureKeyBoardListener();
    this.configureMouseListener();
    this.configureSliderListener();

  }

  public void triggerKeyEvent(KeyEvent keyEvent) {
    this.view.dispatchEvent(keyEvent);
  }

  public void triggerMouseEvent(MouseEvent mouseEvent) {
    this.view.dispatchEvent(mouseEvent);
  }

  public IView getView() {
    return this.view;
  }

  public KeyboardListener getKeyBoardListener() {
    return this.kbd;
  }

  public Appendable getAppendable() {
    return this.ap;
  }

  @Override
  public void setFrameRate(int frameRate) {
    this.ticksPerSecond = frameRate;
  }

  @Override
  public void animate() {
    try {
      ap.append("Frame: " + this.frameCount + ", TicksPerSecond: " +
          this.ticksPerSecond + " , Pause: "
          + this.pause + ", Play: " + this.play + ", Loop: " + this.loop + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    frameCount++;
  }

  @Override
  public void setOutput(String output) {
    if (!output.equals("")) {
      throw new IllegalArgumentException("Interactive output mode can only be visual.");
    }
  }

  /**
   * Sets the status of the view according to the given pause status, loop status, and speed.
   *
   * @param pauseStatus If the animation is paused or not.
   * @param loopStatus If the animation is in a loop or not.
   * @param ticksPerSecond The speed of the animation.
   */
  private void setViewStatus(boolean pauseStatus, boolean loopStatus, int ticksPerSecond) {
    try {
      ap.append("The status bar is set");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Configures the slide listener
   */
  private void configureSliderListener() {
    this.sliderListener = new SliderListener();
    view.addSlideListener(sliderListener, model.getMaxTick());
  }

  /**
   * Configures the mouse click options.
   */
  private void configureMouseListener() {
    this.mouseListener = new MouseInput();
    Map<Integer, Runnable> mouseClickedMap = new HashMap<>();
    Map<Integer, Runnable> scrollMap = new HashMap<>();
    Map<Integer, Runnable> mousePressedMap = new HashMap<>();
    Map<Integer, Runnable> mouseReleasedMap = new HashMap<>();

    mouseClickedMap.put(1, new Runnable() {
      @Override
      public void run() {
        pause = !pause;
      }
    });

    mouseClickedMap.put(3, new Runnable() {
      @Override
      public void run() {
        frameCount = 1;
      }
    });

    mouseClickedMap.put(2, new Runnable() {
      @Override
      public void run() {
        loop = !loop;
      }
    });

    mouseListener.setMouseClickedMap(mouseClickedMap);
    mouseListener.setMousePressedMap(mousePressedMap);
    mouseListener.setMouseReleasedMap(mouseReleasedMap);
    mouseListener.setScrollMap(scrollMap);

    view.addMouseListener(this.mouseListener);
  }

  /**
   * Creates and sets a keyboard listener for the view. In effect it creates snippets of code as
   * Runnable object, one for each time a key is typed, pressed and released, only for those that
   * the program needs.
   *
   * In this example, we need to toggle color when user TYPES 'd', make the message all caps when
   * the user PRESSES 'c' and reverts to the original string when the user RELEASES 'c'. Thus we
   * create three snippets of code (ToggleColor,MakeCaps and MakeOriginalCase) and put them in the
   * appropriate map.
   *
   * Last we create our KeyboardListener object, set all its maps and then give it to the view.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyTypes.put('s', new Runnable() {
      public void run() {
        ITextView svg = new SVGView();

        svg.renderText(model.getShapes(), ticksPerSecond, ".svg",
            new StringWriter(), loop);
      }
    });

    keyTypes.put('p', new Runnable() {
      public void run() {
        pause = !pause;
      }
    });

    keyTypes.put('r', new Runnable() {
      public void run() {
        frameCount = 1;
      }
    });

    keyTypes.put('l', new Runnable() {
      public void run() {
        loop = !loop;
      }
    });

    keyPresses.put(KeyEvent.VK_RIGHT, new Runnable() {
      @Override
      public void run() {
        int speedFactor = 5;
        if (frameCount + speedFactor < model.getMaxTick()) {
          frameCount += speedFactor;
        }
      }
    });

    keyPresses.put(KeyEvent.VK_LEFT, new Runnable() {
      @Override
      public void run() {
        int speedFactor = 5;
        if (frameCount - speedFactor >= 0) {
          frameCount -= speedFactor;
        }
      }
    });

    keyPresses.put(KeyEvent.VK_UP, new Runnable() {
      @Override
      public void run() {
        int frame = ticksPerSecond;
        frame += 5;
        if (frame > 500) {
          frame = 500;
        }
        ticksPerSecond = frame;
        timer.setDelay(1000 / ticksPerSecond);
      }
    });

    keyPresses.put(KeyEvent.VK_DOWN, new Runnable() {
      @Override
      public void run() {
        int frame = ticksPerSecond;
        frame -= 5;
        if (frame <= 0) {
          frame = 1;
        }
        ticksPerSecond = frame;
        timer.setDelay(1000 / ticksPerSecond);
      }
    });

    keyPresses.put(KeyEvent.VK_SPACE, new Runnable() {
      @Override
      public void run() {
        play = true;
      }
    });

    kbd = new KeyboardListener();
    this.kbd.setKeyTypedMap(keyTypes);
    this.kbd.setKeyPressedMap(keyPresses);
    this.kbd.setKeyReleasedMap(keyReleases);
    view.addKeyListener(kbd);
  }
}
