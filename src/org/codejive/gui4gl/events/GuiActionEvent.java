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
public class GuiActionEvent extends GuiEvent {

	public GuiActionEvent(Widget _source) {
		super(_source);
	}

	public static void fireActionPerformed(List _listeners, GuiActionEvent _event) {
		if (!_listeners.isEmpty()) {
			Iterator i = _listeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				GuiActionListener listener = (GuiActionListener)i.next();
				listener.actionPerformed(_event);
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