package codeanticode.chatbots.rebecca;

import processing.core.*;

import rebecca.NetworkAimlFacade;
import rebecca.GraphBuilder;
import rebecca.NetworkException;


 public class Rebecca {
    public Rebecca(PApplet parent, String aimDir) {
		this.parent = parent;
		parent.registerDispose(this);
		try {
	        aiml = new NetworkAimlFacade(new String[]{"rebecca"});
	        builder = aiml.getNetworkGraphBuilder();
	        builder.addDirectoryUnlessAlreadyAdded(parent.dataPath(aimDir));
	        builder.createGraph();
	        botName = builder.getBotPredicate("name");
	        initialResponse = builder.getResponse("connect");	        
        } catch(NetworkException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void dispose() {
        try {		
		    aiml.destroy();
        } catch(NetworkException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }		    
    } 

    public boolean finished() {
        return finished;
    }

    /**
     *  Process a line of input.
     */
    public String processInput(String s) {
		try {    	
            return builder.getResponse(s);
        } catch(NetworkException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ahhhh!";
    }
    
    public String initialResponse;
    
    NetworkAimlFacade aiml = null;
    GraphBuilder builder;
    String botName;
    
    PApplet parent;
    boolean finished = false;       
 }
