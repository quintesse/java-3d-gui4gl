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

/**
 * @author Tako
 * @version $Revision: 158 $
 */
public class GuiMouseAdapter implements GuiMouseListener {

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiMouseListener#mousePressed(org.codejive.gui4gl.events.GuiMouseEvent)
	 */
	public void mousePressed(GuiMouseEvent _event) {
		// Ready to be overridden in subclass
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiMouseListener#mouseReleased(org.codejive.gui4gl.events.GuiMouseEvent)
	 */
	public void mouseReleased(GuiMouseEvent _event) {
		// Ready to be overridden in subclass
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiMouseListener#mouseClicked(org.codejive.gui4gl.events.GuiMouseEvent)
	 */
	public void mouseClicked(GuiMouseEvent _event) {
		// Ready to be overridden in subclass
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiMouseListener#mouseDragged(org.codejive.gui4gl.events.GuiMouseEvent)
	 */
	public void mouseDragged(GuiMouseEvent _event) {
		// Ready to be overridden in subclass
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.events.GuiMouseListener#mouseMoved(org.codejive.gui4gl.events.GuiMouseEvent)
	 */
	public void mouseMoved(GuiMouseEvent _event) {
		// Ready to be overridden in subclass
	}
}

/*
 * $Log$
 * Revision 1.2  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.1  2003/11/23 01:55:38  tako
 * First check-in of the mouse event classes.
 *
 */