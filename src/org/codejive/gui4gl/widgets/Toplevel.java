/*
 * Created on Nov 21, 2003
 */
package org.codejive.gui4gl.widgets;

import java.util.Iterator;

import org.codejive.gui4gl.events.GuiMouseEvent;

/**
 * @author Tako
 * @version $Revision: 150 $
 */
public class Toplevel extends Container {
	private boolean m_bDraggable;
	
	public Toplevel() {
		m_bDraggable = true;
	}
	
	public Toplevel getToplevel() {
		return this;
	}
	
	public boolean isDraggable() {
		return m_bDraggable;
	}
	
	public void setDraggable(boolean _bDraggable) {
		m_bDraggable = _bDraggable;
	}
	
	public boolean isActive() {
		return (getFocusWidget() != null) && getFocusWidget().hasFocus();
	}

	public void activate() {
		if (isVisible() && isFocusable()) {
			if (getFocusWidget() == null) {
				Iterator i = getChildren();
				while (i.hasNext()) {
					Widget w = (Widget)i.next();
					if (w.isFocusable()) {
						w.setFocus();
						break;
					}
				}
			} else {
				getFocusWidget().setFocus();
			}
			moveToFront();
		}
	}

	protected void processMousePressedEvent(GuiMouseEvent _event) {
		activate();
		super.processMousePressedEvent(_event);
	}
}

/*
 * $Log$
 * Revision 1.2  2003/11/24 17:19:02  tako
 * Added draggable property plus getter and setter.
 * activate() will now bring the window to the front.
 * Clicking a toplevel widget will now activate it.
 *
 * Revision 1.1  2003/11/21 01:30:39  tako
 * First check-in of a new container class that is a child of the Screen
 * widget. From now on Screen only accepts widgets inheriting from this
 * class.
 *
 */