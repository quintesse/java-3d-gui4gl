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
 * Created on Nov 3, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.codejive.gui4gl.events.GuiActionEvent;
import org.codejive.gui4gl.events.GuiActionListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.utils4gl.RenderContext;

/**
 * This is a very standard button-like widget that can either be activated
 * by clicking on it with the mouse or by putting the keyboard focus on it
 * and pressing the selection key (by default this would be something like
 * SPACE or ENTER). When activated the widget will fire a GuiAction event.
 * 
 * @author tako
 * @version $Revision: 261 $
 */
public class Button extends Widget {
	private String m_sCaption;
	
	private List m_actionListeners;
	private boolean m_bSelected;
	
	/**
	 * Creates a new button without a caption.
	 */
	public Button() {
		this(null);
	}

	/**
	 * Creates a new button with the given caption.
	 * @param _sCaption Caption to display in the button
	 */
	public Button(String _sCaption) {
		m_sCaption = _sCaption;
		setFocusable(true);
		
		m_actionListeners = new ArrayList();
		m_bSelected = false;
	}

	/**
	 * Returns the button's caption
	 * @return The current caption of the button
	 */
	public String getCaption() {
		return m_sCaption;
	}
	
	/**
	 * Sets the new caption for the button
	 * @param _sCaption The new caption
	 */
	public void setCaption(String _sCaption) {
		m_sCaption = _sCaption;
	}
	
	/**
	 * Adds a listener for the GuiAction event that  will be
	 * fired when the user selects the button (eg by clicking it).
	 * @param _listener The listener to add to the list of listeners
	 */
	public void addActionListener(GuiActionListener _listener) {
		m_actionListeners.add(_listener);
	}
	
	/**
	 * Acts like the user selected the button.
	 */
	public void click() {
		fireActionEvent();
	}
	
	/**
	 * Returns if the button is currently being selected.
	 * This means for example that the user is currently holding down
	 * the left mouse button over the button.
	 * @return The current selection state of the button
	 */
	public boolean isSelected() {
		return m_bSelected;
	}
	
	protected void updateInnerBounds(RenderContext _context) {
		if (isSelected()) {
//			 FIXME: should handle padding!!
			int nXPad = getIntegerAttribute("xPadding#selected");
			int nYPad = getIntegerAttribute("yPadding#selected");
			Rectangle bounds = getInnerBounds();
			bounds.x += nXPad;
			bounds.y += nYPad;
			bounds.width -= 2 * nXPad;
			bounds.height -= 2 * nYPad;
		} else {
			super.updateInnerBounds(_context);
		}
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				m_bSelected = true;
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	protected void processKeyReleasedEvent(GuiKeyEvent _event) {
		m_bSelected = false;
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				if (!_event.isConsumed()) {
					GuiActionEvent e = new GuiActionEvent(this);
					GuiActionEvent.fireActionPerformed(m_actionListeners, e);
				}
				break;
			default:
				super.processKeyReleasedEvent(_event);
				break;
		}
	}

	protected void processMousePressedEvent(GuiMouseEvent _event) {
		m_bSelected = true;
		super.processMousePressedEvent(_event);
	}
	
	protected void processMouseReleasedEvent(GuiMouseEvent _event) {
		m_bSelected = false;
		super.processMouseReleasedEvent(_event);
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		if (!_event.isConsumed()) {
			click();
		}
	}
	
	protected void fireActionEvent() {
		GuiActionEvent e = new GuiActionEvent(this);
		GuiActionEvent.fireActionPerformed(m_actionListeners, e);
	}
}

/*
 * $Log$
 * Revision 1.15  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.14  2004/05/04 22:05:43  tako
 * Now using the new attribute map instead of individual property getters and setters.
 * Consolidated event firing code into separate methods.
 *
 * Revision 1.13  2004/03/17 00:50:46  tako
 * Colors are now cloned during initialization to prevent others from messing
 * up the Themes.
 *
 * Revision 1.12  2004/03/07 18:21:29  tako
 * Bounds calculations and render functions now all have a RenderContext argument.
 *
 * Revision 1.11  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.9  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.8  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.7  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.6  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.5  2003/11/20 00:32:11  tako
 * Code change because of change from getPadding() to
 * updateInnerBounds().
 *
 * Revision 1.4  2003/11/19 11:19:41  tako
 * Implemented completely new event system because tryin to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.3  2003/11/19 00:12:31  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
