/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.text;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Response extends Request
{
  /*
  Constructor
  */

  public Response()
  {
    super();
  }

  public Response(String original)
  {
    super(original);
  }
  
  public Response(String original, Sentence... sentences)
  {
    super(original, sentences);
  }

  /*
  Methods
  */

  public void append(String output)
  {
    StringBuilder builder = new StringBuilder();
    String original = getOriginal();
    if (original != null)
    {
      builder.append(original);
      if (builder.charAt(builder.length() - 1) != ' ')
        builder.append(' ');
    }
    
    builder.append(output);
    setOriginal(builder.toString());
  }
}
