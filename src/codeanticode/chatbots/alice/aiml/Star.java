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

import static codeanticode.chatbots.alice.Match.Section.PATTERN;

public class Star extends TemplateElement
{
  /*
  Attributes
  */

  private int index;

  /*
  Constructor
  */
  
  public Star(Attributes attributes)
  {
    String value = attributes.getValue(0);
    index = (value != null ? Integer.parseInt(value) : 1);
  }
  
  public Star(int index)
  {
    this.index = index;
  }
  
  /*
  Methods
  */

  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof Star))
      return false;
    else
    {
      Star star = (Star) obj;
      return (index == star.index);
    }
  }

  public int hashCode()
  {
    return index;
  }
  
  public String toString()
  {
    return "<star index=\"" + index + "\"/>";
  }

  public String process(Match match)
  {
    String wildcard = match.wildcard(PATTERN, index);
    return (wildcard != null ? wildcard.trim() : "");
  }
}
