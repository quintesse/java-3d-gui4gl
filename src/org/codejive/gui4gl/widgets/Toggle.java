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

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;

/**
 * This class implements a toggle/checkbox field. Its function is
 * quite similar to that of a button with the exception that a
 * toggle changes back and forth between its selected and unselected
 * state whenever it gets activated.
 *  
 * @author tako
 * @version $Revision: 261 $
 */
public class Toggle extends Widget {
	private String m_sCaption;
	private boolean m_bChecked;
	
	private List m_changeListeners;
	
	/**
	 * Creates a new unselected Toggle without a caption.  
	 */
	public Toggle() {
		this(null, false);
	}

	/**
	 * Creates a new Toggle with the given caption and selection state.
	 * @param _sCaption The caption for the new toggle widget
	 * @param _bChecked The begin state of the new widget
	 */
	public Toggle(String _sCaption, boolean _bChecked) {
		m_sCaption = _sCaption;
		m_bChecked = _bChecked;
		setFocusable(true);
		
		m_changeListeners = new ArrayList();
	}

	/**
	 * Returns the toggle's caption
	 * @return The current caption of the toggle
	 */
	public String getCaption() {
		return m_sCaption;
	}
	
	/**
	 * Sets the new caption for the toggle
	 * @param _sCaption The new caption
	 */
	public void setCaption(String _sCaption) {
		m_sCaption = _sCaption;
	}
	
	/**
	 * Returns if the toggle is currently in its checked state or not.
	 * @return The current checked state of the toggle
	 */
	public boolean isChecked() {
		return m_bChecked;
	}
	
	/**
	 * Sets the new checked state for the toggle.
	 * @param _bChecked The new state
	 */
	public void setChecked(boolean _bChecked) {
		m_bChecked = _bChecked;
		fireChangeEvent();
	}
	
	/**
	 * Adds a listener for the GuiChange event that will be
	 * fired when the user changes the state of the toggle.
	 * @param _listener The listener to add to the list of listeners
	 */
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				m_bChecked = !m_bChecked;
				GuiChangeEvent e = new GuiChangeEvent(this, new Boolean(m_bChecked));
				GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		if (!_event.isConsumed()) {
			setChecked(!isChecked());
		}
	}
	
	protected void fireChangeEvent() {
		GuiChangeEvent e = new GuiChangeEvent(this, new Boolean(isChecked()));
		GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
	}
}

/*
 * $Log$
 * Revision 1.10  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.9  2004/05/04 22:05:43  tako
 * Now using the new attribute map instead of individual property getters and setters.
 * Consolidated event firing code into separate methods.
 *
 * Revision 1.8  2004/03/17 00:50:46  tako
 * Colors are now cloned during initialization to prevent others from messing
 * up the Themes.
 *
 * Revision 1.7  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.5  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
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
 * Revision 1.2  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.1  2003/11/21 01:33:30  tako
 * First check-in of the new toggle widget.
 *
 */