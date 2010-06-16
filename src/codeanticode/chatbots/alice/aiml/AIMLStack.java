/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@bol.com.br
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.aiml;

import java.util.LinkedList;
import java.util.List;

public class AIMLStack
{
  /*
  Attributes
  */
  
  private final List<Object> stack = new LinkedList<Object>();
  
  /*
  Methods
  */
  
  public Object peek()
  {
    return stack.get(0);
  }
  
  public Object pop()
  {
    return (stack.size() > 0 ? stack.remove(0) : null);
  }
  
  public void push(Object element)
  {
    stack.add(0, element);
  }
}
