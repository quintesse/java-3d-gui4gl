/*
 * Created on Nov 22, 2003
 */
package org.codejive.gui4gl.events;

/**
 * @author Tako
 * @version $Revision: 122 $
 */
public interface GuiMouseListener {
	public void mousePressed(GuiMouseEvent _event);
	public void mouseReleased(GuiMouseEvent _event);
	public void mouseClicked(GuiMouseEvent _event);
	public void mouseDragged(GuiMouseEvent _event);
	public void mouseMoved(GuiMouseEvent _event);
}

/*
 * $Log$
 * Revision 1.1  2003/11/23 01:55:38  tako
 * First check-in of the mouse event classes.
 *
 */