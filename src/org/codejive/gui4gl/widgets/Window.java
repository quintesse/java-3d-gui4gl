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
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;

import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.utils4gl.RenderContext;

/**
 * This class implements a Window widget that can be used as a child
 * widget for any Screen widgets. The Window acts quite similar to
 * windows in any kind of graphical user interface: it can be dragged
 * (if allowed by its settings), it can be activated and deactivated
 * and it can be layered and the order of the windows within their
 * parent Screen can be changed. And all this by usning the mouse
 * and/or keyboard.
 * 
 * @author tako
 * @version $Revision: 261 $
 */
public class Window extends Toplevel {
	private String m_sTitle;
	private boolean m_bCenterParent;
	private boolean m_bDragging;
	
	/**
	 * Creates a new Window without a title (and therefore also without a title bar).
	 */
	public Window() {
		this(null);
	}

	/**
	 * Creates a new Window with the given text in the title bar.
	 * @param _sTitle
	 */
	public Window(String _sTitle) {
		m_sTitle = _sTitle;
		m_bCenterParent = false;
		setVisible(false);
	}

	/**
	 * Returns the current title text.
	 * @return The current title
	 */
	public String getTitle() {
		return m_sTitle;
	}
	
	/**
	 * Sets the new title text for the widget
	 * @param _sTitle The new title to use
	 */
	public void setTitle(String _sTitle) {
		m_sTitle = _sTitle;
	}
	
	/**
	 * Indicates if the window should be displayed in the center of its parent or not.
	 * @return A boolean indicating if the window should be centered
	 */
	public boolean isCenterParent() {
		return m_bCenterParent;
	}
	
	/**
	 * Sets the fact that the window should be centered within its parent ot not.
	 * @param _bCenter A boolean indicating if the window should be centered
	 */
	public void setCenterParent(boolean _bCenter) {
		m_bCenterParent = _bCenter;
	}
	
	public void setVisible(boolean _bVisible) {
		super.setVisible(_bVisible);
		if (!_bVisible && (getParent() != null) && (getToplevel() == getScreen().getActiveToplevel())) {
			getParent().setFocusWidget(null);
		}
	}
	
	protected void processMousePressedEvent(GuiMouseEvent _event) {
		super.processMousePressedEvent(_event);
		if (!_event.isConsumed()) {
			Rectangle r = new Rectangle();
			r.setBounds(getCurrentBounds());
			// TODO Think carefully if this really is how we want to do this?
			r.height = getIntegerAttribute("titlebarHeight");
			if (isDraggable() && r.contains(_event.getX(), _event.getY())) {
				m_bDragging = true;
			}
		}
	}

	protected void processMouseReleasedEvent(GuiMouseEvent _event) {
		super.processMouseReleasedEvent(_event);
		m_bDragging = false;
	}

	protected void processMouseDraggedEvent(GuiMouseEvent _event) {
		super.processMouseDraggedEvent(_event);
		if (!_event.isConsumed()) {
			if (m_bDragging) {
				Rectangle rect = getBounds();
				int x = rect.x + _event.getDeltaX();
				int y = rect.y + _event.getDeltaY();
				setBounds(x, y, rect.width, rect.height);
			}
		}
	}
	
	protected void updateInnerBounds(RenderContext _context) {
		super.updateInnerBounds(_context);
		if (getTitle() != null) {
			Rectangle rect = getInnerBounds();
			// TODO Think carefully if this really is how we want to do this?
			int nTitleBarHeight = getIntegerAttribute("titlebarHeight");
			rect.y += nTitleBarHeight;
			rect.height -= nTitleBarHeight;
		}
	}

	protected void initWidget(RenderContext _context) {
		super.initWidget(_context);
		if (isCenterParent()) {
			centerParent();
		}
	}
	
	protected void centerParent() {
		Rectangle inner = getParent().getInnerBounds();
		Rectangle rect = getBounds();
		int x = (inner.width - rect.width) / 2;
		int y = (inner.height - rect.height) / 2;
		setBounds(x, y, rect.width, rect.height);
	}
}

/*
 * $Log$
 * Revision 1.17  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.16  2004/05/04 22:15:59  tako
 * Now using the new attribute map instead of individual property getters and setters.
 *
 * Revision 1.15  2004/03/17 00:50:46  tako
 * Colors are now cloned during initialization to prevent others from messing
 * up the Themes.
 *
 * Revision 1.14  2004/03/07 18:21:29  tako
 * Bounds calculations and render functions now all have a RenderContext argument.
 *
 * Revision 1.13  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.11  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.10  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.9  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.8  2003/11/24 17:24:14  tako
 * Implemented window dragging.
 *
 * Revision 1.7  2003/11/21 01:32:56  tako
 * Window now extends Toplevel instead of Container.
 * Moved isActive() and activate() methods to the Toplevel class.
 * Making the window invisible doesn't take the focus away from other
 * windows anymore.
 *
 * Revision 1.6  2003/11/20 00:34:19  tako
 * Code change because of change from getPadding() to
 * updateInnerBounds().
 * Because of that change it is no longer possible to automatically adjust
 * the size of the title bar to the font size so a new property was added
 * to get/set the height of the title bar.
 *
 * Revision 1.5  2003/11/19 00:17:42  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
