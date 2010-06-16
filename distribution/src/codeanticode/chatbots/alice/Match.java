/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeanticode.chatbots.alice.text.Sentence;

import static codeanticode.chatbots.alice.text.Sentence.ASTERISK;

/**
Contains information about a match operation, which is needed by the classes of the <code>codeanticode.chatbots.alice.aiml</code> to produce a proper response.
*/
public class Match implements Serializable
{
  /*
  Inner Classes
  */
  
  public enum Section {PATTERN, THAT, TOPIC;}
  
  /*
  Attributes
  */

  /** Version class identifier for the serialization engine. Matches the number of the last revision where the class was created / modified. */
  private static final long serialVersionUID = 8L;

  private final Map<Section, List<String>> sections = new HashMap<Section, List<String>>();
  
  private AliceBot callback;

  private Sentence input;

  private Sentence that;

  private Sentence topic;

  private String[] matchPath;
  
  {
    sections.put(Section.PATTERN, new ArrayList<String>(2)); // Pattern wildcards
    sections.put(Section.THAT, new ArrayList<String>(2)); // That wildcards
    sections.put(Section.TOPIC, new ArrayList<String>(2)); // Topic wildcards
  }
  
  /*
  Constructor
  */

  public Match()
  {
  }

  public Match(AliceBot callback, Sentence input, Sentence that, Sentence topic)
  {
    this.callback = callback;
    this.input = input;
    this.that = that;
    this.topic = topic;
    setUpMatchPath(input.normalized(), that.normalized(), topic.normalized());
  }

  public Match(Sentence input)
  {
    this(null, input, ASTERISK, ASTERISK);
  }

  /*
  Methods
  */
  
  private void appendWildcard(List<String> section, Sentence source, int beginIndex, int endIndex) 
  {
    if (beginIndex == endIndex)
      section.add(0, "");
    else try
    {
      section.add(0, source.original(beginIndex, endIndex));
    }
    catch (Exception e)
    {
      throw new RuntimeException("Source: {\"" + source.getOriginal() + "\", \"" + source.getNormalized() + "\"}\n" +
                                 "Begin Index: " + beginIndex + "\n" +
                                 "End Index: " + endIndex, e);
    }
  }

  private void setUpMatchPath(String[] pattern, String[] that, String[] topic)
  {
    int m = pattern.length, n = that.length, o = topic.length;
    matchPath = new String[m + 1 + n + 1 + o];
    matchPath[m] = "<THAT>";
    matchPath[m + 1 + n] = "<TOPIC>";

    System.arraycopy(pattern, 0, matchPath, 0, m);
    System.arraycopy(that, 0, matchPath, m + 1, n);
    System.arraycopy(topic, 0, matchPath, m + 1 + n + 1, o);
  }
  
  public void appendWildcard(int beginIndex, int endIndex)
  {
    int inputLength = input.length();
    if (beginIndex <= inputLength)
    {
      appendWildcard(sections.get(Section.PATTERN), input, beginIndex, endIndex);
      return;
    }
    
    beginIndex = beginIndex - (inputLength + 1);
    endIndex   = endIndex   - (inputLength + 1);

    int thatLength = that.length();    
    if (beginIndex <= thatLength)
    {
      appendWildcard(sections.get(Section.THAT), that, beginIndex, endIndex);
      return;
    }
    
    beginIndex = beginIndex - (thatLength + 1);
    endIndex   = endIndex   - (thatLength + 1);

    int topicLength = topic.length();    
    if (beginIndex < topicLength)
      appendWildcard(sections.get(Section.TOPIC), topic, beginIndex, endIndex);
  }
  
  /**
  Gets the contents for the (index)th wildcard in the matched section.
  */
  public String wildcard(Section section, int index)
  {
    List<String> wildcards = sections.get(section);
    return wildcards.get(index - 1);
  }
  
  /*
  Properties
  */
  
  public AliceBot getCallback()
  {
    return callback;
  }
  
  public void setCallback(AliceBot callback)
  {
    this.callback = callback;
  }
  
  public String[] getMatchPath()
  {
    return matchPath;
  }

  public String getMatchPath(int index)
  {
    return matchPath[index];
  }
  
  public int getMatchPathLength()
  {
    return matchPath.length;
  }
}
