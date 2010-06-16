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
import codeanticode.chatbots.alice.text.Request;
import codeanticode.chatbots.alice.Match;

public class Input extends TemplateElement
{
  /*
  Attributes
  */

  private int requestIndex = 1, sentenceIndex = 1;

  /*
  Constructors
  */

  public Input(Attributes attributes)
  {
    String value = attributes.getValue(0);
    if (value == null) return;
    
    String[] indexes = value.split(",");
    requestIndex = Integer.parseInt(indexes[0].trim());
    if (indexes.length > 1) sentenceIndex = Integer.parseInt(indexes[1].trim());
  }
  
  public Input(int requestIndex, int sentenceIndex)
  {
    this.requestIndex = requestIndex;
    this.sentenceIndex = sentenceIndex;
  }
  
  /*
  Methods
  */
  
  public boolean equals(Object obj)
  {
    if (!super.equals(obj)) return false;

    Input compared = (Input) obj;
    return (requestIndex == compared.requestIndex &&
            sentenceIndex == compared.sentenceIndex);
  }
  
  public String toString()
  {
    return "<input index=\"" + requestIndex + ", " + sentenceIndex + "\"/>";
  }
  
  public String process(Match match)
  {
    if (match == null) return "";
    Request request = match.getCallback().getContext().getRequests(requestIndex - 1);
    return request.lastSentence(sentenceIndex - 1).trimOriginal();
  }
}
