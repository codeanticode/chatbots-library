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

import java.io.InputStream;
import java.io.Reader;
import codeanticode.chatbots.alice.AliceBot;
import codeanticode.chatbots.alice.Context;
import codeanticode.chatbots.alice.Graphmaster;
import codeanticode.chatbots.alice.text.Transformations;
import codeanticode.chatbots.alice.aiml.AIMLParser;

public class AliceBotParser
{
  /*
  Attributes
  */
  
  private Class<? extends Context> contextClass = Context.class;
  
  private Class<? extends Graphmaster> graphmasterClass = Graphmaster.class;

  private AIMLParser aimlParser;

  private ContextParser contParser;

  private TransformationsParser normParser;
  
  /*
  Constructor
  */
  
  public AliceBotParser() throws AliceBotParserConfigurationException
  {
    try
    {
      aimlParser = new AIMLParser();
      contParser = new ContextParser();
      normParser = new TransformationsParser();
    }
    catch (Exception e)
    {
      throw new AliceBotParserConfigurationException(e);
    }
  }
  
  /*
  Methods
  */
  
  private Context newContext(InputStream defaults, InputStream splitters, InputStream substitutions) throws Exception 
  {
    Context context = (Context) contextClass.newInstance();
    contParser.parse(context, defaults);
    Transformations transformations = normParser.parse(splitters, substitutions);
    context.setTransformations(transformations);
    return context;
  }

  private Graphmaster newGraphmaster(InputStream... aiml) throws Exception 
  {
    Graphmaster graphmaster = (Graphmaster) graphmasterClass.newInstance();
    aimlParser.parse(graphmaster, aiml);
    return graphmaster;
  }
  
  public void parse(AliceBot bot, InputStream defaults, InputStream splitters, InputStream substitutions, InputStream... aiml)
    throws AliceBotParserException
  {
    try
    {
      Context context = newContext(defaults, splitters, substitutions);
      Graphmaster graphmaster = newGraphmaster(aiml);

      bot.setContext(context);      
      bot.setGraphmaster(graphmaster);
    }
    catch (Exception e)
    {
      throw new AliceBotParserException(e);
    }
  }

  public AliceBot parse(InputStream defaults, InputStream splitters, InputStream substitutions, InputStream... aiml)
    throws AliceBotParserException
  {
    try
    {
      AliceBot bot = new AliceBot();
      parse(bot, defaults, splitters, substitutions, aiml);
      return bot;
    }
    catch (AliceBotParserException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new AliceBotParserException(e);
    }
  }
  
  /*
  Accessor Section
  */
  
  public <C extends Context> void contextClass(Class<C> contextClass)
  {
    this.contextClass = contextClass;
  }
  
  public <M extends Graphmaster> void graphmasterClass(Class<M> graphmasterClass)
  {
    this.graphmasterClass = graphmasterClass;
  }
}
