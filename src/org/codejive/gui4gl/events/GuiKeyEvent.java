/*
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

import java.util.Iterator;
import java.util.List;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 155 $
 */
public class GuiKeyEvent extends GuiEvent {
	private int m_nModifiers;
	private int m_nKeyCode;
	private char m_cKeyChar;
	
	public GuiKeyEvent(Widget _source, int _nId, int _nModifiers, int _nKeyCode, char _cKeyChar) {
		super(_source, _nId);
		m_nModifiers = _nModifiers;
		m_nKeyCode = _nKeyCode;
		m_cKeyChar = _cKeyChar;
	}

	public int getModifiers() {
		return m_nModifiers;
	}
	
	public int getKeyCode() {
		return m_nKeyCode;
	}
	
	public char getKeyChar() {
		return m_cKeyChar;
	}
	
	public String toString() {
		return super.toString() + " (modifiers=" + m_nModifiers + ", keycode=" + m_nKeyCode + ", keychar=" + m_cKeyChar + ")";
	}
	
	public static void fireKeyPressed(List _listeners, GuiKeyEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiKeyListener listener = (GuiKeyListener)i.next();
				listener.keyPressed(_event);
			}
		}
	}
	
	public static void fireKeyReleased(List _listeners, GuiKeyEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiKeyListener listener = (GuiKeyListener)i.next();
				listener.keyReleased(_event);
			}
		}
	}
	
	public static void fireKeyTyped(List _listeners, GuiKeyEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiKeyListener listener = (GuiKeyListener)i.next();
				listener.keyTyped(_event);
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
 * Revision 1.2  2003/11/24 16:46:34  tako
 * Added toString().
 *
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */