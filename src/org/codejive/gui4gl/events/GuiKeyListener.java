/*
 * Created on Nov 19, 2003
 */
package org.codejive.gui4gl.events;

/**
 * @author Tako
 * @version $Revision: 90 $
 */
public interface GuiKeyListener {
	public void keyPressed(GuiKeyEvent _event);
	public void keyReleased(GuiKeyEvent _event);
	public void keyTyped(GuiKeyEvent _event);
}

/*
 * $Log$
 * Revision 1.1  2003/11/19 11:15:45  tako
 * First checkin of the event classes needed for the new Gui event system.
 *
 */