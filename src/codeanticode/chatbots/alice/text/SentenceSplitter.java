/*
Copyleft (C) 2006 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.UNICODE_CASE;
import static codeanticode.chatbots.alice.util.Escaper.escapeRegex;

public class SentenceSplitter
{
  /*
  Attribute Section
  */
  
  /** Map of sentence-protection substitution patterns. */
  private final Map<String, String> protection;
  
  /** List of sentence-spliting patterns. */
  private final List<String> splitters;
  
  /** The regular expression which will split entries by sentence splitters. */
  private final Pattern pattern;
  
  /*
  Constructor Section
  */
  
  public SentenceSplitter(Map<String, String> protection, List<String> splitters)
  {
    this.protection = protection;
    this.splitters = splitters;

    String splitPattern = "\\s*(";
    for (Iterator<String> i = splitters.iterator();;)
    {
      splitPattern += escapeRegex(i.next());
      if (!i.hasNext())
        break;
      splitPattern += "|";
    }
    splitPattern += ")\\s*";

    this.pattern = Pattern.compile(splitPattern);
  }
  
  /*
  Method Section
  */

  private String protect(String input)
  {
    for (String find : protection.keySet())
    {
      Pattern pattern = Pattern.compile(find, CASE_INSENSITIVE | UNICODE_CASE);
      Matcher matcher = pattern.matcher(input);
      String replace = protection.get(find);
      input = matcher.replaceAll(replace);
    }

    return input;
  }
  
  private String[] split(String original, String prepared)
  {
    /* See the description of java.util.regex.Matcher.appendReplacement() in the Javadocs to understand this code. */
    Matcher matcher = pattern.matcher(prepared);
    List<String> sentences = new LinkedList<String>();
    int beginIndex = 0;

    while (matcher.find())
    {
      int endIndex = matcher.start();
      String sentence = original.substring(beginIndex, endIndex) + matcher.group(1);
      if (!splitters.contains(sentence.trim()))
        sentences.add(sentence);
      beginIndex = endIndex + matcher.group().length();
    }

    String[] splitted;
    if (sentences.size() > 0)
    {
      splitted = new String[sentences.size()];
      sentences.toArray(splitted);
    }
    else
    {
      splitted = new String[] {original};
    }

    return splitted;
  }
  
  public String[] split(String original)
  {
    return split(original, protect(original));
  }
  
}
