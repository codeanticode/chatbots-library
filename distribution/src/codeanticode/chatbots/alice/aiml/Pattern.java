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

import java.lang.System;
import java.util.Arrays;
import java.util.List;
import org.xml.sax.Attributes;

import codeanticode.chatbots.alice.AliceBot;
import codeanticode.chatbots.alice.text.Sentence;

public class Pattern implements AIMLElement
{
  /*
  Attribute Section
  */

  private String[] pattern;

  private int hashCode;
  
  /*
  Constructor Section
  */
  
  public Pattern()
  {
  }
  
  public Pattern(String pattern)
  {
    this.pattern = pattern.trim().split(" ");
    hashCode = Arrays.hashCode(this.pattern);
  }
  
  public Pattern(Attributes attributes)
  {
  }
  
  /*
  Method Section
  */
  
  public void appendChild(AIMLElement child)
  {
    String text = child.toString();
    if (pattern == null)
      pattern = new String[] {text};
    else
    {
      int length = pattern.length;
      String[] larger = new String[length + 1];
      System.arraycopy(pattern, 0, larger, 0, length);
      larger[length] = text;
      pattern = larger;
    }
  }
  
  public void appendChildren(List<AIMLElement> children)
  {
    StringBuilder builder = new StringBuilder();
    for (AIMLElement child : children)
      builder.append(child);
    
    String text = builder.toString().trim();
    pattern = text.split(" ");
    hashCode = Arrays.hashCode(pattern);
  }

  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof Pattern)) return false;
    Pattern compared = (Pattern) obj;
    return Arrays.equals(pattern, compared.pattern);
  }

  public int hashCode()
  {
    return hashCode;
  }

  public String toString()
  {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0, n = pattern.length;;)
    {
      buffer.append(pattern[i]);
      if (++i >= n) break;
      buffer.append(" ");
    }
    
    return buffer.toString();
  }
  
  /*
  Property Section
  */

  public String[] getElements()
  {
    return pattern;
  }

  public void setElements(String[] pattern)
  {
    this.pattern = pattern;
    hashCode = Arrays.hashCode(pattern);
  }
}
