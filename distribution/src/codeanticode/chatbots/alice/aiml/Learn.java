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

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import org.xml.sax.Attributes;

import codeanticode.chatbots.alice.AliceBot;
import codeanticode.chatbots.alice.Context;
import codeanticode.chatbots.alice.Match;
import codeanticode.chatbots.alice.Graphmaster;

public class Learn extends TemplateElement
{
  /*
  Constructors
  */

  public Learn(Attributes attributes)
  {
  }

  public Learn(Object... children)
  {
    super(children);
  }

  /*
  Methods
  */

  public String process(Match match)
  {
    AliceBot bot = null;
    try
    {
      bot = match.getCallback();
      Graphmaster graphmaster = bot.getGraphmaster();

      String address = super.process(match);
      URL url = new URL(address);

      AIMLParser parser = new AIMLParser();
      parser.parse(graphmaster, url.openStream());
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }

    return "";
  }
}