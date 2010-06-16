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
import java.beans.PropertyChangeListener;
import codeanticode.chatbots.alice.text.Sentence;
import codeanticode.chatbots.alice.text.Transformations;

import static codeanticode.chatbots.alice.text.Sentence.ASTERISK;

/**
Property change listener for the <code>predicate.topic</code> property. Updates the Context with the new Topic value.
*/
public class ContextTopicChangeListener extends ContextPropertyChangeListener
{
  /*
  Constructor Section
  */
  
  /**
  Default class constructor.
  */
  public ContextTopicChangeListener()
  {
    super("predicate.topic");
  }
  
  /*
  Method Section
  */

  // Fired when the predicate.topic property changes.  
  public void propertyChange(PropertyChangeEvent event)
  {
    Object oldTopic = event.getOldValue();
    Object newTopic = event.getNewValue();
    Context context = (Context) event.getSource();
    Transformations transformations = context.getTransformations();
    
    if (oldTopic == null ? newTopic == null : oldTopic.equals(newTopic))
      return;
    
    String input = newTopic.toString().trim();
    if ("".equals(input) || "*".equals(input))
      context.setTopic(ASTERISK);
    else
    {
      Sentence topic = new Sentence(input);
      transformations.normalization(topic);
      context.setTopic(topic);
    }
  }
}
