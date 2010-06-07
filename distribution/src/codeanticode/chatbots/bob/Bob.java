package codeanticode.chatbots.bob;

import processing.core.*;

 public class Bob {
    public Bob(PApplet parent) {
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
     *  Process a line of input.
     */
    public String processInput(String s) {
        return s;
    }
    
    PApplet parent;
    boolean finished = false;       
 }
