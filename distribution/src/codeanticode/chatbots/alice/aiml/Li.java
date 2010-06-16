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

public class Li extends TemplateElement
{
  /*
  Attributes
  */
  
  private String name;
  private String value;
  
  
  /*
  Constructors
  */

  public Li()
  {
  }

  public Li(Attributes attributes)
  {
    name = attributes.getValue("name");
    value = attributes.getValue("value");
  }

  public Li(String name, String value, Object... children)
  {
    super(children);
    this.name = name;
    this.value = value;
  }
  
  /*
  Methods
  */
  
  private boolean isEquals(Object comparing, Object compared)
  {
    return (comparing == null ? compared == null : comparing.equals(compared));
  }
  
  public boolean equals(Object obj)
  {
    if (!super.equals(obj)) return false;
    Li compared = (Li) obj;
    return (isEquals(name, compared.name) && isEquals(value, compared.value));
  }
  
  /*
  Properties
  */
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getValue()
  {
    return value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}
