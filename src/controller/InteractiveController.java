package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import model.AnimatorModel;
import view.HybridView;

/**
 * Controller which uses a HybridView which the user can interact with. This controller uses the
 * actions from the user to display the actions to the user as they occur.
 */
public class InteractiveController implements Controller, ActionListener {

  private HybridView view;
  private AnimatorModel model;
  private boolean pause;
  private boolean loop;
  private int frameCount;
  private int ticksPerSecond;
  private Timer timer;
  private SliderListener sliderListener;
  private boolean play = false;

  /**
   * Constructs a new InteractiveController with the given model and a hybrid view.
   */
  public InteractiveController(AnimatorModel model, HybridView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null field givne.");
    }
    this.model = model;
    this.view = view;
    this.pause = false;
    this.loop = false;
    this.view.addStatusBar(pause, loop, ticksPerSecond);

    this.configureKeyBoardListener();
    this.configureMouseListener();
    this.configureSliderListener();
  }

  @Override
  public void setFrameRate(int frameRate) {
    this.ticksPerSecond = frameRate;
  }

  public void animate() {
    this.view.addStatusBar(pause, loop, ticksPerSecond);
    timer = new javax.swing.Timer(1000 / ticksPerSecond
        // performs every 1000 miliseconds / 1 per second
        , new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.renderImage(model.getAnimatorState(frameCount), ticksPerSecond);
        if (!play) {
          frameCount = 0;
        }
        if (sliderListener.isAdjusting()) {
          frameCount = sliderListener.getFrame();
        } else if (frameCount >= model.getMaxTick() && loop) {
          frameCount = 1;
        } else if (pause || frameCount >= model.getMaxTick()) {
          frameCount = frameCount;
        } else {
          frameCount++;
        }
        ;
        view.resetFocus();
      }
    });
    timer.start();
  }

  @Override
  public void setOutput(String output) {
    if (!output.equals("")) {
      throw new IllegalArgumentException("Interactive output mode can only be visual.");
    }
  }

  /**
   * Configures the slide listener.
   */
  private void configureSliderListener() {
    this.sliderListener = new SliderListener();
    view.addSlideListener(sliderListener, model.getMaxTick());
  }

  /**
   * Configures the mouse click options.
   */
  private void configureMouseListener() {
    MouseInput mouseListener = new MouseInput();
    Map<Integer, Runnable> mouseClickedMap = new HashMap<>();
    Map<Integer, Runnable> scrollMap = new HashMap<>();
    Map<Integer, Runnable> mousePressedMap = new HashMap<>();
    Map<Integer, Runnable> mouseReleasedMap = new HashMap<>();

    InteractiveController that = this;

    mouseClickedMap.put(1, new Runnable() {
      @Override
      public void run() {
        if (play) {
          pause = !pause;
          that.view.addStatusBar(pause, that.loop, that.ticksPerSecond);
        } else {
          return;
        }
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
        that.view.addStatusBar(pause, loop, ticksPerSecond);
      }
    });

    mouseListener.setMouseClickedMap(mouseClickedMap);
    mouseListener.setMousePressedMap(mousePressedMap);
    mouseListener.setMouseReleasedMap(mouseReleasedMap);
    mouseListener.setScrollMap(scrollMap);

    view.addMouseListener(mouseListener);
  }

  /**
   * Creates and sets a keyboard listener for the view. In effect it creates snippets of code as
   * Runnable object, one for each time a key is typed, pressed and released, only for those that
   * the program needs.
   *
   * <p></p>In this example, we need to toggle color when user TYPES 'd', make the message all caps when
   * the user PRESSES 'c' and reverts to the original string when the user RELEASES 'c'. Thus we
   * create three snippets of code (ToggleColor,MakeCaps and MakeOriginalCase) and put them in the
   * appropriate map.
   *
   * <p></p>Last we create our KeyboardListener object, set all its maps and then give it to the view.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    InteractiveController that = this;

    keyTypes.put('p', new Runnable() {
      public void run() {
        if (play) {
          pause = !pause;
          that.view.addStatusBar(pause, that.loop, that.ticksPerSecond);
        } else {
          return;
        }
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
        that.view.addStatusBar(pause, loop, ticksPerSecond);
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
        int frame = that.ticksPerSecond;
        frame += 5;
        if (frame == 6) {
          frame = 5;
        }
        if (frame > 120) {
          frame = 120;
        }
        that.ticksPerSecond = frame;
        that.timer.setDelay(1000 / ticksPerSecond);
        that.view.addStatusBar(that.pause, that.loop, ticksPerSecond);
      }
    });

    keyPresses.put(KeyEvent.VK_DOWN, new Runnable() {
      @Override
      public void run() {
        int frame = that.ticksPerSecond;
        frame -= 5;
        if (frame <= 0) {
          frame = 1;
        }
        that.ticksPerSecond = frame;
        that.timer.setDelay(1000 / that.ticksPerSecond);
        that.view.addStatusBar(that.pause, that.loop, ticksPerSecond);
      }
    });

    keyPresses.put(KeyEvent.VK_SPACE, new Runnable() {
      @Override
      public void run() {
        play = true;
      }
    });

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

  @Override
  public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
    switch (e.getActionCommand()) {
      case "SVG Export":
        if (this.view.getSVGString().length() == 0) {
          this.view.resetSVGString();
          return;
        }
        this.view.svgExport(model.getShapes(), this.ticksPerSecond,
            this.view.getSVGString() + ".svg", this.loop);
        this.view.resetSVGString();
      default:
        throw new IllegalArgumentException("Invalid command");
    }
  }
}
