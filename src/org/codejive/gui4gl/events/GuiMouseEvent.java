/*
 * Created on Nov 22, 2003
 */
package org.codejive.gui4gl.events;

import java.util.Iterator;
import java.util.List;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 122 $
 */
public class GuiMouseEvent extends GuiEvent {
	private int m_nModifiers;
	private int m_nXPos;
	private int m_nYPos;
	private int m_nClickCount;
	
	public GuiMouseEvent(Widget _source, int _nModifiers, int _nXPos, int _nYPos, int _nClickCount) {
		super(_source);
		m_nModifiers = _nModifiers;
		m_nXPos = _nXPos;
		m_nYPos = _nYPos;
		m_nClickCount = _nClickCount;
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
	
	public int getClickcount() {
		return m_nClickCount;
	}
	
	public static void fireMousePressed(List _listeners, GuiMouseEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mousePressed(_event);
			}
		}
	}
	
	public static void fireMouseReleased(List _listeners, GuiMouseEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseReleased(_event);
			}
		}
	}
	
	public static void fireMouseClicked(List _listeners, GuiMouseEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseClicked(_event);
			}
		}
	}
}

/*
 * $Log$
 * Revision 1.1  2003/11/23 01:55:38  tako
 * First check-in of the mouse event classes.
 *
 */