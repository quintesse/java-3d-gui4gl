/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Tako Schotanus
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Created on Nov 22, 2003
 */
package org.codejive.gui4gl.events;

import java.util.Iterator;
import java.util.List;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 236 $
 */
public class GuiMouseEvent extends GuiEvent {
	private int m_nButton;
	private int m_nModifiers;
	private int m_nXPos;
	private int m_nYPos;
	private int m_nDeltaXPos;
	private int m_nDeltaYPos;
	private int m_nClickCount;
	
	public GuiMouseEvent(Widget _source, int _nId, int _nButton, int _nModifiers, int _nXPos, int _nYPos, int _nDeltaXPos, int _nDeltaYPos, int _nClickCount) {
		super(_source, _nId);
		m_nButton = _nButton;
		m_nModifiers = _nModifiers;
		m_nXPos = _nXPos;
		m_nYPos = _nYPos;
		m_nDeltaXPos = _nDeltaXPos;
		m_nDeltaYPos = _nDeltaYPos;
		m_nClickCount = _nClickCount;
	}

	public int getButton() {
		return m_nButton;
	}
	
	public int getModifiers() {
		return m_nModifiers;
	}
	
	public int getX() {
		return m_nXPos;
	}
	
	public int getY() {
		return m_nYPos;
	}
	
	public int getDeltaX() {
		return m_nDeltaXPos;
	}
	
	public int getDeltaY() {
		return m_nDeltaYPos;
	}
	
	public int getClickcount() {
		return m_nClickCount;
	}
	
	public String toString() {
		return super.toString() + " (modifiers=" + m_nModifiers + ", x=" + m_nXPos + ", y=" + m_nYPos + ", dx=" + m_nDeltaXPos + ", dy=" + m_nDeltaYPos + ", clicks=" + m_nClickCount + ")";
	}
	
	public static void fireMousePressed(List _listeners, GuiMouseEvent _event) {
		if ((_listeners != null) && !_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mousePressed(_event);
			}
		}
	}
	
	public static void fireMouseReleased(List _listeners, GuiMouseEvent _event) {
		if ((_listeners != null) && !_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseReleased(_event);
			}
		}
	}
	
	public static void fireMouseClicked(List _listeners, GuiMouseEvent _event) {
		if ((_listeners != null) && !_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseClicked(_event);
			}
		}
	}
	
	public static void fireMouseMoved(List _listeners, GuiMouseEvent _event) {
		if ((_listeners != null) && !_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseMoved(_event);
			}
		}
	}
	
	public static void fireMouseDragged(List _listeners, GuiMouseEvent _event) {
		if ((_listeners != null) && !_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseDragged(_event);
			}
		}
	}
}

/*
 * $Log$
 * Revision 1.6  2004/05/04 21:54:13  tako
 * Made sure that the fireEvent methods handle null listeners gracefully.
 *
 * Revision 1.5  2003/12/11 10:48:18  tako
 * Added the 1.4 getButton() method to the event.
 *
 * Revision 1.4  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.3  2003/11/24 19:19:32  tako
 * Added possibility for events to have an Id (like AWTEvents have) so
 * you can distinguish between the different event sub-types.
 *
 * Revision 1.2  2003/11/24 16:47:54  tako
 * Added delta X and Y coordinates and getters for them.
 * Added fireMouseMoved()/Dragged().
 * Added toString().
 *
 * Revision 1.1  2003/11/23 01:55:38  tako
 * First check-in of the mouse event classes.
 *
 */