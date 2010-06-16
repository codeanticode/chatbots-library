/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.util;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
Synchronized linear integer generator.
*/
public class Sequence
{
  /*
  Attributes
  */
  
  private File backup, file;
  
  /*
  Constructor
  */
  
  public Sequence(File file)
  {
    this.file = file;
    backup = new File(file.getAbsolutePath() + ".backup");
  }

  public Sequence(String path)
  {
    file = new File(path);
    backup = new File(path + ".backup");
  }
  
  /*
  Methods
  */
  
  private long loadNext(File file) throws IOException
  {
    String line = "";

    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(file));

      line = reader.readLine();

      long next = Long.parseLong(line);

      reader.close();

      return next;
    }
    catch (NumberFormatException e)
    {
      throw new IOException("Illegal value on persistence file: " + line);
    }
    catch (FileNotFoundException e)
    {
      return 0;
    }
  }
  
  private void saveNext(File file, long next) throws IOException
  {
    PrintWriter writer = new PrintWriter(new FileWriter(file, false), true);

    writer.println(Long.toString(next + 1));
    
    writer.close();
  }

  /**
  Return the next number in the sequence.
  */
  public synchronized long getNext() throws IOException
  {
    long next = 0;

    try
    {
      next = loadNext(file.exists() ? file : backup);
    }
    catch (IOException e)
    {
      next = loadNext(backup);
    }

    saveNext(backup, next);
    saveNext(file, next);

    return next;
  }
}
