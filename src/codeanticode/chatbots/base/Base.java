package codeanticode.chatbots.base;

import processing.core.*;

abstract class Base {
  PApplet parent;
  boolean finished = false;

  public Base(PApplet parent) {
    this.parent = parent;
    parent.registerDispose(this);
  }

  public void dispose() {
    // anything in here will be called automatically when
    // the parent applet shuts down. for instance, this might
    // shut down a thread used by this library.
    // note that this currently has issues, see bug #183
    // http://dev.processing.org/bugs/show_bug.cgi?id=183
  }

  public boolean finished() {
    return finished;
  }

  /**
   * Process a line of input.
   */
  abstract public String processInput(String s);
}
