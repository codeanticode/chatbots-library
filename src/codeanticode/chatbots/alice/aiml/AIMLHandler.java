/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@bol.com.br
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.aiml;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.lang.reflect.Constructor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AIMLHandler extends DefaultHandler
{
  /*
  Attribute Section
  */
  
  private final Set<String> ignored = new HashSet<String>();
  final StringBuilder text = new StringBuilder();
  
  private boolean ignoreWhitespace = true;

  /** The stack of AIML objects is used to build the Categories as AIML documents are parsed. The scope is defined as package for testing purposes. */
  final AIMLStack stack = new AIMLStack();

  /*
  Constructor Section
  */
  
  public AIMLHandler(String... ignore)
  {
    ignored.addAll(Arrays.asList(ignore));
  }
  
  /*
  Method Section
  */

  private String buildClassName(String tag)
  {
    return "codeanticode.chatbots.alice.aiml." +
           tag.substring(0, 1).toUpperCase() +
           tag.substring(1).toLowerCase();
  }
  
  private void pushTextNode()
  {
    String pushed = text.toString();
    text.delete(0, text.length());
    if (ignoreWhitespace)
      pushed = pushed.replaceAll("^[\\s\n]+|[\\s\n]{2,}|\n", " ");

    if(!"".equals(pushed.trim()))
      stack.push(new Text(pushed));
  }
  
  private void updateIgnoreWhitespace(Attributes attributes)
  {
    try
    {
      ignoreWhitespace = !"preserve".equals(attributes.getValue("xml:space"));
    }
    catch (NullPointerException e)
    {
    }
  }
  
  public List<Category> unload()
  {
    List<Category> result = new LinkedList<Category>();
    
    Object poped;
    while ((poped = stack.pop()) != null)
      if (poped instanceof Aiml)
        result.addAll(((Aiml) poped).children());
    
    return result;
  }
  
  /*
  Event Handling Section
  */
  
  public void characters(char[] chars, int start, int length)
  {
    text.append(chars, start, length);
  }

  public void startElement(String namespace, String name, String qname, Attributes attributes) throws SAXException
  {
    if (ignored.contains(qname)) return;
    updateIgnoreWhitespace(attributes);
    pushTextNode();
    String className = buildClassName(qname);
    try
    {
      Class tagClass = Class.forName(className);
      Constructor constructor = tagClass.getConstructor(Attributes.class);
      Object tag = constructor.newInstance(attributes);
      stack.push(tag);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Cannot instantiate class " + className, e);
    }
  }
  
  public void endElement(String namespace, String name, String qname) throws SAXException
  {
    if (ignored.contains(qname)) return;
    pushTextNode();
    ignoreWhitespace = true;
    String className = buildClassName(qname);
    for (List<AIMLElement> children = new LinkedList<AIMLElement>();;)
    {
      Object tag = stack.pop();
      if (tag == null)
        throw new SAXException("No matching start tag found for " + qname);
      else if (!className.equals(tag.getClass().getName()))
        children.add(0, (AIMLElement) tag);
      else try
      {
        if (children.size() > 0)
          ((AIMLElement) tag).appendChildren(children);
        stack.push(tag);
        return;
      }
      catch (ClassCastException e)
      {
        throw new RuntimeException("Tag <" + qname + "> used as node, but implementing " +
                                   "class does not implement the AIMLElement interface", e);
      }
      catch (Exception e)
      {
        throw new SAXException(e);
      }
    }
  }
}
