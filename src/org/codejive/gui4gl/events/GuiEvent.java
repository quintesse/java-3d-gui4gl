/*
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

import java.util.EventObject;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 90 $
 */
public abstract class GuiEvent extends EventObject {
	private boolean m_bConsumed;
	
	public GuiEvent(Widget _source) {
		super(_source);
		m_bConsumed = false;
	}

	public boolean isConsumed() {
		return m_bConsumed;
	}

	public void consume() {
		m_bConsumed = true;
	}
}

/*
 * $Log$
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */