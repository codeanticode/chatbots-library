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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import codeanticode.chatbots.alice.Match;

public class Formal extends TemplateElement
{
  /*
  Constructors
  */

  public Formal(Attributes attributes)
  {
  }

  public Formal(Object... children)
  {
    super(children);
  }

  /*
  Methods
  */
  
  public String process(Match match)
  {
    String result = super.process(match);
    if (result == null || "".equals(result.trim())) return "";

    /* See the description of java.util.regex.Matcher.appendReplacement() in the Javadocs to understand this code. */    
    Pattern p = Pattern.compile("(^\\s*[a-z]|\\s+[a-z])");
    Matcher m = p.matcher(result);
    StringBuffer buffer = new StringBuffer();
    while (m.find())
      m.appendReplacement(buffer, m.group().toUpperCase());
    m.appendTail(buffer);
    return buffer.toString();
  }
}
