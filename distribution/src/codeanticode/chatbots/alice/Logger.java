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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
Log file generator.
*/
public class Logger
{
  /*
  Attributes
  */

  private static final DateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  private PrintWriter writer;

  /*
  Constructor
  */

  /**
  Constructs a new Log on the given directory.
  */
  public Logger(Writer writer)
  {
    this.writer =  new PrintWriter(writer, true);
  }
  
  /*
  Methods
  */
  
  /**
  Adds an entry to this Log.
  */
  public void append(String request, String response) throws IOException
  {
    String now = date.format(new Date());
  
    writer.println("[" + now + "][" + request + "][" + response + "]");
  }
}
