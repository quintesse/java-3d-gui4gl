/*
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

/**
 * @author Tako
 * @version $Revision: 90 $
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
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */