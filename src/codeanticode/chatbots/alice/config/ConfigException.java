/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package codeanticode.chatbots.alice.config;

public class ConfigException extends Exception
{
  /*
  Attribute Section
  */
  
  private static final long serialVersionUID = 7L;

  /*
  Constructor Section
  */
  
  /**
  Constructs a new exception with <code>null</code> as its detail message. The cause is not initialized, and may subsequently be initialized by a call to <code>initCause()</code>.
  */  
  public ConfigException()
  {
    super("Configuration failed");
  }

  /**
  Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to <code>initCause()</code>.
  
  @param message The detail message. The detail message is saved for later retrieval by the <code>getMessage()</code> method.
  */
  public ConfigException(String message)
  {
    super(message);
  }
  
  /**
  Constructs a new exception with the specified detail message and cause. Note that the detail message associated with cause is <u>not</u> automatically incorporated in this exception's detail message.

  @param message The detail message, which is saved for later retrieval by the <code>getMessage()</code> method.
  @param cause The cause, which is saved for later retrieval by the <code>getCause()</code> method. A <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
  */
  public ConfigException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  /**
  Constructs a new exception with the specified cause and a detail message of <code>(cause == null ? null : cause.toString())</code>.

  @param cause The cause, which is saved for later retrieval by the <code>getCause()</code> method. A <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
  */
  public ConfigException(Throwable cause)
  {
    super(cause);
  }
  
  /**
  Constructs a new exception with the specified object as its cause. If the object is a <code>Throwable</code>, it will be incorporated as this exception's cause; otherwise, the result of calling th method <code>toString()</code> on it will be incorporated as the exception's message.
  
  @param cause The cause of the exception.
  */
  public ConfigException(Object cause)
  {
    this();
    if (cause instanceof Throwable)
      initCause((Throwable) cause);
    else
      initCause(new Exception(cause.toString()));
  }
}
