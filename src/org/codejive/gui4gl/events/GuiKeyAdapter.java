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

/**
 * @author Tako
 * @version $Revision: 158 $
 */
public class GuiKeyAdapter implements GuiKeyListener {

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiKeyListener#keyPressed(org.codejive.gui4gl.events.GuiKeyEvent)
	 */
	public void keyPressed(GuiKeyEvent _event) {
		// Ready to be overridden in subclass
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiKeyListener#keyReleased(org.codejive.gui4gl.events.GuiKeyEvent)
	 */
	public void keyReleased(GuiKeyEvent _event) {
		// Ready to be overridden in subclass
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiKeyListener#keyTyped(org.codejive.gui4gl.events.GuiKeyEvent)
	 */
	public void keyTyped(GuiKeyEvent _event) {
		// Ready to be overridden in subclass
	}

}

/*
 * $Log$
 * Revision 1.2  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */