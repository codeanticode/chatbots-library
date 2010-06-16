package codeanticode.chatbots.alice;

import processing.core.*;

public class Alice {
  PApplet parent;
  boolean finished = false;
  AliceBotMother mother;
  AliceBot bot;

  public Alice(PApplet parent, String botDir) {
    this.parent = parent;
    parent.registerDispose(this);
   
    String dir = parent.dataPath(botDir);
    PApplet.println(dir);
    mother = new AliceBotMother();
    try {    
      bot = mother.newInstance(dir);
    } catch (Exception e) {
      e.printStackTrace();
    }    
  }

  public void dispose() {
  }

  public boolean finished() {
    return finished;
  }

  /**
   * Process a line of input.
   */
  public String processInput(String s) {
    String response = bot.respond(s);
    return response;
  }
}


/*
ChatterBean

package codeanticode.chatbots.alice;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

import codeanticode.chatbots.alice.AliceBot;
import codeanticode.chatbots.alice.Context;
import codeanticode.chatbots.alice.parser.AliceBotParser;
import codeanticode.chatbots.alice.parser.ChatterBeanParser;
import codeanticode.chatbots.alice.script.JavascriptInterpreter;
import codeanticode.chatbots.alice.script.Interpreter;
import codeanticode.chatbots.alice.util.Searcher;

public class ChatterBean extends JApplet
{
  
  // Version class identifier for the serialization engine. Matches the number of the last revision where the class was created / modified.
  private static final long serialVersionUID = 8L;

  private static final InputStream[] INPUT_STREAM_ARRAY = {};
  
  // Javascript interpreter used by the underlying chatterbot.
  private final Interpreter javascript = new JavascriptInterpreter(this);

  // The component container for this object.  
  private final Container container = getContentPane();

  // Input text control.
  private final JTextField input = new JTextField(30)
  {
    // Version class identifier for the serialization engine.
    private static final long serialVersionUID = 7L;

    // Object initialization
    {
      ActionListener listener = new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          String request = getText();
          String response = aliceBot.respond(request);
  
          setText("");
          output.append("> " + request + "\n");
          output.append(response + "\n");
        }
      };
      
      addActionListener(listener);
      container.add(this);
    }
  };

  // Output text control.
  private final JTextArea output = new JTextArea(10, 30)
  {
    // Version class identifier for the serialization engine.
    private static final long serialVersionUID = 7L;
    {
      setEditable(false);
      setLineWrap(true);
      setWrapStyleWord(true);
      container.add(new JScrollPane(this));
    }
  };
  
  // The underlying AliceBot used to produce responses to user queries.
  private AliceBot aliceBot;
  
  // Logger object used to keep track of this bot's conversations.
  private Logger logger;
  
  public ChatterBean()
  {
    container.setLayout(new FlowLayout());
  }
  
  public ChatterBean(String path)
  {
    this();
    configure(path);
  }
  
  private void beforeConfigure()
  {
    if (getAliceBot() == null)
      setAliceBot(new AliceBot());
  }
  
  private void afterConfigure()
  {
    Context context = aliceBot.getContext();
    context.property("javascript.interpreter", javascript);
  }

  public void init()
  {
    try
    {
      beforeConfigure();

      AliceBotParser parser = new AliceBotParser();
      parser.parse(getAliceBot(),
        openStream("context"),
        openStream("splitters"),
        openStream("substitutions"),
        openStreams("aiml"));

      afterConfigure();
    }
    catch (ChatterBeanException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new ChatterBeanException(e);
    }
  }

  private InputStream openStream(String name)
  {
    String value = getParameter(name);
    if (value == null || "".equals(value.trim()))
      throw new ChatterBeanException("Invalid value for parameter \"" + name + "\": " + value);

    return openURLStream(value);
  }
  
  private InputStream openURLStream(String path)
  {
    try
    {
      URL url = new URL(getDocumentBase(), path);
      return url.openStream();
    }
    catch (Exception e)
    {
      throw new ChatterBeanException(e);
    }
  }
  
  private InputStream[] openStreams(String name)
  {
    String value = getParameter(name);
    if (value == null || "".equals(value.trim()))
      throw new ChatterBeanException("Invalid value for parameter \"" + name + "\": " + value);
    else if (value.endsWith(".aiml"))
      return new InputStream[] {openURLStream(value)};
    else if (value.endsWith(".txt"))
      return openAIMLStreams(value);
    else
      return searchAIMLStreams(value);
  }
  
  private InputStream[] openAIMLStreams(String path)
  {
    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(openURLStream(path)));
      List<InputStream> streams = new LinkedList<InputStream>();
      for (String fileName = ""; (fileName = reader.readLine()) != null;)
        streams.add(openURLStream(fileName));
      
      return streams.toArray(INPUT_STREAM_ARRAY);
    }
    catch (Exception e)
    {
      throw new ChatterBeanException(e);
    }
  }

  private InputStream[] searchAIMLStreams(String path)
  {
    try
    {
      Searcher searcher = new Searcher();
      return searcher.search(getDocumentBase(), path, ".+\\.aiml");
    }
    catch (Exception e)
    {
      throw new ChatterBeanException(e);
    }
  }
  
  Configures this object with a set of properties.  
  @param path Path of the properties file.
  public void configure(String path)
  {
    try
    {
      beforeConfigure();
      ChatterBeanParser parser = new ChatterBeanParser();
      parser.parse(this, path);
    }
    catch (Exception e)
    {
      throw new ChatterBeanException(e);
    }
  }
  
  public String respond(String request)
  {
    String response = "";
    if(request != null && !"".equals(request.trim())) try
    {
      response = aliceBot.respond(request);
      if (logger != null)
        logger.append(request, response);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }

    return response;
  }
  
  //Main entry point.
  public static void main(String[] args) throws Exception
  {
    ChatterBean applet = new ChatterBean(args[0]);
    
    if (args.length > 1 && "gui".equals(args[1]))
    {
      JFrame frame = new JFrame("ChatterBean GUI Window");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(applet);
      frame.setSize(350, 210);
      frame.setVisible(true);
    }
    else
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      for(;;)
      {
        String input = reader.readLine();
        if (input == null || "".equals(input.trim())) break;
        System.out.println(applet.respond(input));
      }
    }
  }
  
  //Gets the AliceBot encapsulated by this bot.
  public AliceBot getAliceBot()
  {
    return aliceBot;
  }
  
  //Sets the AliceBot encapsulated by this bot.
  public void setAliceBot(AliceBot aliceBot)
  {
    this.aliceBot = aliceBot;
  }
  
  //Gets the logger object used by this bot.
  public Logger getLogger()
  {
    return logger;
  }
  
  //Sets the logger object used by this bot.
  public void setLogger(Logger logger)
  {
    this.logger = logger;
  }
}
*/


/*
ChatterBean parser

public class ChatterBeanParser
{
  private AliceBotParser botParser;
  
  private Class<? extends Logger> loggerClass = Logger.class;
  
  public ChatterBeanParser() throws AliceBotParserConfigurationException
  {
    try
    {
      botParser = new AliceBotParser();
    }
    catch (AliceBotParserConfigurationException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new AliceBotParserConfigurationException(e);
    }
  }
  
  private Logger newLogger(String root, String dir) throws Exception
  {
    if (dir == null) return null;
    String path = root + dir;
    
    Sequence sequence = new Sequence(path + "sequence.txt");
    File file = new File(path + "log" + sequence.getNext() + ".txt");
    return loggerClass.getConstructor(Writer.class).newInstance(new FileWriter(file));
  }
  
  private InputStream newResourceStream(String resource, String root, String path) throws Exception
  {
    if (root == null || path == null)
      throw new IllegalArgumentException(
        "Invalid path elements for retrieving " + resource + ": root(" + root + "), path(" + path + ")");
    
    path = root + path;
    
    try
    {
      return new FileInputStream(path);
    }
    catch (Exception e)
    {
      throw new Exception("Error while retrieving " + resource + ": " + path, e);
    }
  }
  
  public void parse(ChatterBean bot, String path) throws AliceBotParserException
  {
    try
    {
      Properties properties = new Properties();
      properties.loadFromXML(new FileInputStream(path));

      String root = path.substring(0, path.lastIndexOf('/') + 1);
      String categories = root + properties.getProperty("categories");
      String logs = properties.getProperty("logs");

      InputStream context = newResourceStream("context", root, properties.getProperty("context"));
      InputStream splitters = newResourceStream("splitters", root, properties.getProperty("splitters"));
      InputStream substitutions = newResourceStream("substitutions", root, properties.getProperty("substitutions"));

      Searcher searcher = new Searcher();
      bot.setLogger(newLogger(root, logs));
      botParser.parse(bot.getAliceBot(), context, splitters, substitutions, searcher.search(categories, ".*\\.aiml"));
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
  
  public <C extends Context> void contextClass(Class<C> contextClass)
  {
    botParser.contextClass(contextClass);
  }

  public <L extends Logger> void loggerClass(Class<L> loggerClass)
  {
    this.loggerClass = loggerClass;
  }
  
  public <M extends Graphmaster> void graphmasterClass(Class<M> matcherClass)
  {
    botParser.graphmasterClass(matcherClass);
  }
}
*/