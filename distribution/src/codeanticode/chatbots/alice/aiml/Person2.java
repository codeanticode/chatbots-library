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
import codeanticode.chatbots.alice.text.Transformations;

public class Person2 extends TemplateElement
{
  /*
  Constructor Section
  */

  public Person2(Attributes attributes)
  {
  }

  public Person2(Object... children)
  {
    super(children);
  }

  /*
  Method Section
  */

  public String process(Match match)
  {
    String input = super.process(match);
    AliceBot bot = match.getCallback();
    Transformations transformations = bot.transformations();
    return transformations.person2(input);
  }
}
