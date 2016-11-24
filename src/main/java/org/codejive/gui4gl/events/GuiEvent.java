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
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

import java.util.EventObject;

import org.codejive.gui4gl.widgets.Widget;

/**
 * @author Tako
 * @version $Revision: 158 $
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
 * Revision 1.3  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.2  2003/11/24 19:19:32  tako
 * Added possibility for events to have an Id (like AWTEvents have) so
 * you can distinguish between the different event sub-types.
 *
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */