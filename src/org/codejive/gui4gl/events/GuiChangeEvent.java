/*
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

import java.util.Iterator;
import java.util.List;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 90 $
 */
public class GuiChangeEvent extends GuiEvent {
	private Object m_value;
		
	public GuiChangeEvent(Widget _source, Object _value) {
		super(_source);
		m_value = _value;
	}
		
	public Object getValue() {
		return m_value;
	}
	
	public static void fireChangeEvent(List _listeners, GuiChangeEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiChangeListener listener = (GuiChangeListener)i.next();
				listener.stateChanged(_event);
			}
		}
	}
}	

/*
 * $Log$
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */