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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import codeanticode.chatbots.alice.Match;

public class TemplateElement implements AIMLElement
{
  /*
  Attribute Section
  */
  
  private static final TemplateElement[] TEMPLATE_ELEMENT_ARRAY = {};

  private final List<TemplateElement> children = new LinkedList<TemplateElement>();
  
  /*
  Constructor Section
  */
  
  public TemplateElement(Object... elements)
  {
    for (Object child : elements)
    {
      if (child instanceof AIMLElement)
        children.add((TemplateElement) child);
      else
        children.add(new Text(child.toString()));
    }
  }
  
  /*
  Method Section
  */
  
  public void appendChild(AIMLElement element)
  {
    children.add((TemplateElement) element);
  }
  
  public void appendChildren(List<AIMLElement> elements)
  {
    for (AIMLElement element : elements)
      children.add((TemplateElement) element);
  }
  
  public List<TemplateElement> children()
  {
    return children;
  }

  public boolean equals(Object object)
  {
    if (object == null || !(object instanceof TemplateElement))
      return false;
    
    TemplateElement that = (TemplateElement) object;
    return children.equals(that.children);
  }

  public int hashCode()
  {
    return children.hashCode();
  }

  public String process(Match match)
  {
    StringBuilder value = new StringBuilder();
    for (TemplateElement i : children) 
    {
      String s = i.process(match);
      value.append(s);  
    }
      
      
    
    return value.toString();
  }

  /*
  Property Section
  */
  
  public TemplateElement[] getChildren()
  {
    return children.toArray(TEMPLATE_ELEMENT_ARRAY);
  }
  
  public TemplateElement getChildren(int index)
  {
    return children.get(index);
  }
  
  public void setChildren(TemplateElement[] elements)
  {
    children.clear();
    children.addAll(Arrays.asList(elements));
  }
}
