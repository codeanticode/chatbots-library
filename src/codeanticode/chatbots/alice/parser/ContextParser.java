/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.parser;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import codeanticode.chatbots.alice.Context;

public class ContextParser
{
  /*
  Inner Classes
  */
  
  private class ContextHandler extends DefaultHandler
  {
    public void startElement(String namespace, String name, String qname, Attributes attributes) throws SAXException
    {
      if (qname.equals("set"))
        context.property("predicate." + attributes.getValue("name"), attributes.getValue("value"));
      else if (qname.equals("bot"))
        context.property("bot." + attributes.getValue("name"), attributes.getValue("value"));
    }
  }
  
  /*
  Attributes
  */
  
  private final ContextHandler handler = new ContextHandler();
  private SAXParser parser;

  private Context context;
  
  /*
  Constructor
  */

  public ContextParser() throws ParserConfigurationException, SAXException
  {
    parser = SAXParserFactory.newInstance().newSAXParser();
  }

  /*
  Methods
  */

  public Context parse(InputStream input) throws IOException, SAXException
  {
    parse(new Context(), input);
    return context;
  }
  
  public void parse(Context context, InputStream input) throws IOException, SAXException
  {
    this.context = context;
    parser.parse(input, handler);
  }
}
