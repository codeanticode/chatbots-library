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
import org.xml.sax.Attributes;
import codeanticode.chatbots.alice.AliceBot;
import codeanticode.chatbots.alice.Context;
import codeanticode.chatbots.alice.Match;
import codeanticode.chatbots.alice.text.Response;

public class That extends TemplateElement
{
  /*
  Attribute Section
  */
  
  private static final String[] STRING_ARRAY = {};

  private int responseIndex = 1, sentenceIndex = 1;

  /*
  Constructor Section
  */
  
  public That(Attributes attributes)
  {
    String value = attributes.getValue(0);
    if (value == null) return;
    
    String[] indexes = value.split(",");
    responseIndex = Integer.parseInt(indexes[0].trim());
    if (indexes.length > 1) sentenceIndex = Integer.parseInt(indexes[1].trim());
  }
  
  public That(Object... children)
  {
    super(children);
  }
  
  public That(int responseIndex, int sentenceIndex)
  {
    this.responseIndex = responseIndex;
    this.sentenceIndex = sentenceIndex;
  }
  
  /*
  Method Section
  */
  
  public String[] elements()
  {
    TemplateElement[] children = getChildren();
    List<String> elements = new LinkedList<String>();
    for (int i = 0, n = children.length; i < n; i++)
    {
      String text = children[i].toString();
      text = text.trim();
      elements.addAll(Arrays.asList(text.split(" ")));
    }

    return elements.toArray(STRING_ARRAY);
  }

  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof That)) return false;
    
    That compared = (That) obj;
    
    return (responseIndex  == compared.responseIndex &&
            sentenceIndex == compared.sentenceIndex);
  }

  public int hashCode()
  {
    return responseIndex + sentenceIndex;
  }

  public String process(Match match)
  {
    if (match == null)
      return "";
    
    AliceBot bot = match.getCallback();
    Context context = bot.getContext();
    Response response = context.getResponses(responseIndex - 1);
    return response.getSentences(sentenceIndex - 1).trimOriginal();
  }
  
  public String toString()
  {
    if (children().size() == 0)
      return "<that index=\"" + responseIndex + ", " + sentenceIndex + "\"/>";
    else
    {
      StringBuilder builder = new StringBuilder("<that>");
      for (TemplateElement element : children())
        builder.append(element);
      builder.append("</that>");

      return builder.toString();
    }
  }
}
