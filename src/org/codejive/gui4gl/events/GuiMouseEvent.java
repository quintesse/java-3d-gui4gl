/*
 * Created on Nov 22, 2003
 */
package org.codejive.gui4gl.events;

import java.util.Iterator;
import java.util.List;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 155 $
 */
public class GuiMouseEvent extends GuiEvent {
	private int m_nModifiers;
	private int m_nXPos;
	private int m_nYPos;
	private int m_nDeltaXPos;
	private int m_nDeltaYPos;
	private int m_nClickCount;
	
	public GuiMouseEvent(Widget _source, int _nId, int _nModifiers, int _nXPos, int _nYPos, int _nDeltaXPos, int _nDeltaYPos, int _nClickCount) {
		super(_source, _nId);
		m_nModifiers = _nModifiers;
		m_nXPos = _nXPos;
		m_nYPos = _nYPos;
		m_nDeltaXPos = _nDeltaXPos;
		m_nDeltaYPos = _nDeltaYPos;
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
	
	public static void fireMouseMoved(List _listeners, GuiMouseEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiMouseListener listener = (GuiMouseListener)i.next();
				listener.mouseMoved(_event);
			}
		}
	}
	
	public static void fireMouseDragged(List _listeners, GuiMouseEvent _event) {
		if (!_listeners.isEmpty()) {
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