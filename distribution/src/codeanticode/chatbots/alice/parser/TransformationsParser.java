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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import codeanticode.chatbots.alice.config.ConfigException;
import codeanticode.chatbots.alice.config.TokenizerConfig;
import codeanticode.chatbots.alice.config.TokenizerConfigStream;
import codeanticode.chatbots.alice.text.Tokenizer;
import codeanticode.chatbots.alice.text.Transformations;

public class TransformationsParser
{
  /*
  Attribute Section
  */

  private final SubstitutionBuilder substBuilder = new SubstitutionBuilder();
  private final ReflectionHandler substHandler = new ReflectionHandler(substBuilder);
  private final SplitterHandler splitHandler = new SplitterHandler();
  
  private SAXParser parser;

  /*
  Constructor Section
  */
  
  public TransformationsParser() throws ParserConfigurationException, SAXException
  {
    parser = SAXParserFactory.newInstance().newSAXParser();
  }

  /*
  Method Section
  */

  private List<String> parseSplitters(InputStream splitters) throws IOException, SAXException
  {
    splitHandler.clear();
    parser.parse(splitters, splitHandler);
    return splitHandler.parsed();
  }

  private Map<String, Map<String, String>> parseSubstitutions(InputStream substitutions) throws IOException, SAXException
  {
    substBuilder.clear();
    parser.parse(substitutions, substHandler);
    return substBuilder.parsed();
  }
  
  private byte[] toByteArray(InputStream input) throws IOException
  {
    List<Byte> list = new LinkedList<Byte>();
    for (int i = 0; (i = input.read()) > -1;)
      list.add((byte) i);

    int i = 0;
    byte[] bytes = new byte[list.size()];
    for (byte b : list)
      bytes[i++] = b;
    return bytes;
  }
  
  public Transformations parse(InputStream splitters, InputStream substitutions)
    throws ConfigException, IOException, SAXException
  {
    byte[] bytes = toByteArray(splitters);
    
    TokenizerConfig config = new TokenizerConfigStream(new ByteArrayInputStream(bytes));
    Tokenizer tokenizer = new Tokenizer(config);

    List<String> splitChars = parseSplitters(new ByteArrayInputStream(bytes));

    Map<String, Map<String, String>> maps = parseSubstitutions(substitutions);
    return new Transformations(splitChars, maps, tokenizer);
  }
}
