/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.aiml;

import org.xml.sax.Attributes;
import codeanticode.chatbots.alice.Match;

import static codeanticode.chatbots.alice.Match.Section.TOPIC;

public class Topicstar extends TemplateElement
{
  /*
  Attributes
  */
  
  private int index;

  /*
  Constructor
  */
  
  public Topicstar(Attributes attributes)
  {
    String value = attributes.getValue(0);
    if (value == null)
      index = 1;
    else
      index = Integer.parseInt(value);
  }
  
  public Topicstar(int index)
  {
    this.index = index;
  }
  
  /*
  Methods
  */
  
  public boolean equals(Object obj)
  {
    if (!super.equals(obj)) return false;
    Topicstar compared = (Topicstar) obj;
    
    return (index == compared.index);
  }
  
  public String toString()
  {
    return "<topicstar index=\"" + index + "\">";
  }
  
  public String process(Match match)
  {
    String wildcard = match.wildcard(TOPIC, index);
    return (wildcard != null ? wildcard.trim() : "");
  }
}
