/*
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

import java.util.EventObject;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 155 $
 */
public abstract class GuiEvent extends EventObject {
	private int m_nId;
	
	private boolean m_bConsumed;
	
	public GuiEvent(Widget _source) {
		this(_source, -1);
	}

	public GuiEvent(Widget _source, int _nId) {
		super(_source);
		m_nId = _nId;
		m_bConsumed = false;
	}

	public int getId() {
		return m_nId;
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
 * Revision 1.2  2003/11/24 19:19:32  tako
 * Added possibility for events to have an Id (like AWTEvents have) so
 * you can distinguish between the different event sub-types.
 *
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */