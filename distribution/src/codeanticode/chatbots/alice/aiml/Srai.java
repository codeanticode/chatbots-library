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
import codeanticode.chatbots.alice.AliceBot;
import codeanticode.chatbots.alice.Match;

public class Srai extends TemplateElement
{
  /*
  Constructor Section
  */

  public Srai(Attributes attributes)
  {
  }
  
  public Srai(Object... children)
  {
    super(children);
  }

  public Srai(int index)
  {
    super(new Star(index));
  }
  
  /*
  Method Section
  */
  
  public String process(Match match)
  {
    String request = super.process(match);

    try
    {
      AliceBot bot = (match != null ? match.getCallback() : null);
      return (bot != null ? bot.respond(request) : "");
    }
    catch (Exception e)
    {
      throw new RuntimeException("While trying to respond \"" + request + "\"", e);
    }
  }
  
  public String toString()
  {
    return "<srai>" + super.toString() + "</srai>";
  }
}
