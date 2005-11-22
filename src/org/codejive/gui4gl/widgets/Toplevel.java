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
 * Created on Nov 21, 2003
 */
package org.codejive.gui4gl.widgets;

import java.util.Iterator;

import org.codejive.gui4gl.events.GuiMouseEvent;

/**
 * TopLevel objects are the only objwcts that can appear directly as children
 * of the Screen object and are the base class for widgets like windows and
 * menu pop-ups. TopLevel widgets are also containers and can therefore contain
 * other widgets.
 * 
 * @author Tako
 * @version $Revision: 312 $
 */
public class Toplevel extends Container {
	private boolean m_bDraggable;
	
	/**
	 * Creates a new TopLevel.
	 */
	public Toplevel() {
		m_bDraggable = true;
	}
	
	public Toplevel getToplevel() {
		return this;
	}
	
	/**
	 * Indicates if the toplevel widget is draggable or not.
	 * @return A boolean indicating if the widget can be dragged
	 */
	public boolean isDraggable() {
		return m_bDraggable;
	}
	
	/**
	 * Sets the fact that the widget is draggable or not.
	 * @param _bDraggable A boolean indicating if it should be possible to drag the widget
	 */
	public void setDraggable(boolean _bDraggable) {
		m_bDraggable = _bDraggable;
	}
	
	/**
	 * Returns true if and only if the widget is enabled and one of its children
	 * (or grandchildren etc) has the keyboard focus.
	 * @return A boolean indicating if the window is active
	 */
	public boolean isActive() {
		return isEnabled() && (getFocusWidget() != null) && getFocusWidget().hasFocus();
	}

	/**
	 * Activates the toplevel widget if it is enabled, visible and focusable.
	 * Activating the toplevel means setting the focus the last child widget
	 * that had the focus or to the first focusable child widget if no previous
	 * focus existed.
	 */
	public void activate() {
		if (isEnabled() && isVisible() && isFocusable()) {
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

	public void processMousePressedEvent(GuiMouseEvent _event) {
		activate();
		super.processMousePressedEvent(_event);
	}
}

/*
 * $Log$
 * Revision 1.7  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.6  2004/05/04 22:15:26  tako
 * Removed unnecessary constructors.
 *
 * Revision 1.5  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.4  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.3  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
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
