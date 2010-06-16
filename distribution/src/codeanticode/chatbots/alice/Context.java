/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codeanticode.chatbots.alice.text.Request;
import codeanticode.chatbots.alice.text.Response;
import codeanticode.chatbots.alice.text.Sentence;
import codeanticode.chatbots.alice.text.Transformations;

import static java.net.InetAddress.getLocalHost;
import static codeanticode.chatbots.alice.text.Sentence.ASTERISK;

/**
A conversational context. This class stores information such as the history of a conversation and predicate values, which the Alice Bot can refer to while responding user requests.
*/
public class Context
{
  
  String rootPath;
  /*
  Attribute Section
  */

  /** Map of context properties. */
  private final Map<String, Object> properties = new HashMap<String, Object>();
  
  /** Map of property change listeners. */
  private final Map<String, ContextPropertyChangeListener> listeners = new HashMap<String, ContextPropertyChangeListener>();

  private final List<Request>  requests = new LinkedList<Request>();
  private final List<Response> responses = new LinkedList<Response>();
  
  private final Random random = new Random();
  private long seed = 0;

  private OutputStream output;

  private Sentence that;
  private Sentence topic;

  /** Set of normalizing transformations applied to unstructured text. */
  private Transformations transformations;
  
  /*
  Constructor Section
  */
  
  /**
  Default Constructor.
  */
  public Context()
  {
    //property("beanshell.interpreter", new BeanshellInterpreter());

    addContextPropertyChangeListener(new ContextRandomSeedChangeListener());
    addContextPropertyChangeListener(new ContextTopicChangeListener());
  }
  
  /**
  Creates a new Context object with the given set of normalizing transformations.
  
  @param transformations A set of normalizing transformations.
  */
  public Context(Transformations transformations)
  {
    this();
    this.transformations = transformations;
  }
  
  /*
  Event Section
  */

  /**
  Adds a property change listener to this context object.
  
  @param listener A property change listener. If there already is a listener with the same name of this one, the old listener will be discarded.
  */  
  public void addContextPropertyChangeListener(ContextPropertyChangeListener listener)
  {
    listeners.put(listener.name(), listener);
  }
  
  /**
  Removes a property change listener to this context object. Although listeners are stored by name, for the removing to actually occur it is not enough to simply pass a listener with the same name; the same <i>object</i> must be passed, otherwise this method does nothing.
  
  @param listener A property change listener.
  */  
  public void removeContextPropertyChangeListener(ContextPropertyChangeListener listener)
  {
    ContextPropertyChangeListener listening = listeners.get(listener.name());
    if (listening == listener)
      listeners.remove(listener.name());
  }
  
  /*
  Method Section
  */

  public void appendRequest(Request request)
  {
    requests.add(0, request);
  }

  public void appendResponse(Response response)
  {
    transformations.normalization(response);
    responses.add(0, response);

    that = response.lastSentence(0);
    transformations.normalization(that);
  }

  public void print(String output) throws IOException
  {
    outputStream().write(output.getBytes());
    outputStream().write('\n');
  }
  
  /*
  Accessor Section
  */
                       
  public Object property(String name)
  {
    return properties.get(name);
  }
  
  public void property(String name, Object value)
  {
    ContextPropertyChangeListener listener = listeners.get(name);
    if (listener != null)
    {
      Object oldValue = properties.get(name);
      PropertyChangeEvent event = new PropertyChangeEvent(this, name, oldValue, value);
      listener.propertyChange(event);
    }

    properties.put(name, value);
  }

  public OutputStream outputStream() throws IOException
  {
    if (output == null)
    {
      String path = (String) property("bot.output");
      path = rootPath + path;
      
      File file = new File(path);
      if (file.isDirectory())
        path = file.getPath() + "/gossip-" + id() + ".txt";
  
      outputStream(new FileOutputStream(path));
    }
    
    return output;
  }

  public void outputStream(OutputStream output)
  {
    this.output = output;
  }
  
  public String id()
  {
    String id = (String) property("bot.id");
    if ("".equals(id))
      return Integer.toString(hashCode());
    else
      return id;
  }
  
  public Random random()
  {
    return random;
  }
  
  /**
  Sets the value of the seed used by the internal random number generator.
  
  @param seed The seed used by the internal random number generator.
  */
  public void random(long seed)
  {
    random.setSeed(seed);
  }

  /*
  Property Section
  */

  public Sentence getThat()
  {
    if (that == null)
      that = ASTERISK;
    return that;
  }

  public Sentence getTopic()
  {
    if (topic == null)
      topic = ASTERISK;
    return topic;
  }
  
  public void setTopic(Sentence topic)
  {
    if (topic == null)
      this.topic = ASTERISK;
    this.topic = topic;
  }

  public Request getRequests(int index)
  {
    return requests.get(index);
  }

  public Response getResponses(int index)
  {
    return responses.get(index);
  }
  
  public Transformations getTransformations()
  {
    return transformations;
  }
  
  public void setTransformations(Transformations transformations)
  {
    this.transformations = transformations;
  }
}
