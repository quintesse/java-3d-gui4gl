/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Steven Lagerweij, Tako Schotanus
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
 * Created on Nov 18, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;

/**
 * @author steven
 * @version $Revision: 239 $
 */
public class ValueBar extends Widget {
	private float m_fMin;
	private float m_fMax;
	private float m_fValue;
	private float m_fStepSize;
	private int m_nOrientation;
	private boolean m_bShowValue = false;
	private int m_lAlignment = GLText.ALIGN_CENTER;

	private List m_changeListeners;
	
	public ValueBar(float _fMin, float _fMax) {
		this(_fMin, _fMax, 1.0f, Orientation.DEFAULT, false, GLText.ALIGN_CENTER);
	}
	
	public ValueBar(float _fMin, float _fMax, float _fStepSize) {
		this(_fMin, _fMax, _fStepSize, Orientation.DEFAULT, false, GLText.ALIGN_CENTER);
	}
	
	public ValueBar(float _fMin, float _fMax, float _fStepSize, boolean _bShowValue, int _lAlignment) {
		this(_fMin, _fMax, _fStepSize, Orientation.DEFAULT, _bShowValue, _lAlignment);
	}
	
	public ValueBar(float _fMin, float _fMax, int _nOrientation) {
		this(_fMin, _fMax, 1.0f, _nOrientation, false, GLText.ALIGN_CENTER);
	}
	
	public ValueBar(float _fMin, float _fMax, float _fStepSize, int _nOrientation) {
		this(_fMin, _fMax, _fStepSize, _nOrientation, false, GLText.ALIGN_CENTER);
	}
	
	/**
	 * @param _lAlignment see GLText for values.
	 */
	public ValueBar(float _fMin, float _fMax, float _fStepSize, int _nOrientation, boolean _bShowValue, int _lAlignment) {
		setMinValue(_fMin);
		setMaxValue(_fMax);
		setStepSize(_fStepSize);
		setOrientation(_nOrientation);
		setAlignment(_lAlignment);
		setShowValue(_bShowValue);
		
		m_changeListeners = new LinkedList();
		setFocusable(true);
	}
	
	public float getValue() {
		return m_fValue;
	}
	
	public void setValue(float _value) {
		m_fValue = _value;
		fireChangeEvent();
	}

	public float getMinValue() {
		return m_fMin;
	}
	
	public void setMinValue(float _fValue) {
		m_fMin = _fValue;
	}
	
	public float getMaxValue() {
		return m_fMax;
	}
	
	public void setMaxValue(float _fValue) {
		m_fMax = _fValue;
	}
	
	public float getStepSize() {
		return m_fStepSize;
	}
	
	public void setStepSize(float _fStepSize) {
		m_fStepSize = _fStepSize;
	}
	
	/**
	 * Returns the current orientation of the scroll bar.
	 * @return The orientation of the scroll bar
	 */
	public int getOrientation() {
		return m_nOrientation;
	}
	
	/**
	 * Sets the orientation of the scroll bar.
	 * @param _nOrientation The new orientation of the scroll bar
	 */
	public void setOrientation(int _nOrientation) {
		m_nOrientation = _nOrientation;
	}
	
	public boolean isShowValue() {
		return m_bShowValue;
	}

	public void setShowValue(boolean _bShowValue) {
		m_bShowValue = _bShowValue;
	}
	
	public void setAlignment(int _lAlignment) {
		if(m_lAlignment == GLText.ALIGN_CENTER || m_lAlignment == GLText.ALIGN_LEFT || m_lAlignment == GLText.ALIGN_RIGHT) {
			m_lAlignment = _lAlignment;
		} else {
			throw new RuntimeException("Invalid alignment specified. See GLText for valid alignments");
		}
	}

	public int getAlignment() {
		return m_lAlignment;
	}

	/**
	 * Returns the actual orientation for this widget.
	 * If the orientation property is either HORIZONTAL or VERTICAL
	 * this will just return the same value as getOrientation() but
	 * if the value is DEFAULT this method will try to determine the
	 * orientation by looking at the widget's bounds.
	 * @return The actual orientation of this widget
	 */
	public int getActualOrientation() {
		int orientation = getOrientation();
		if (orientation == Orientation.DEFAULT) {
			Rectangle barRect = getBounds();
			if (barRect.height > barRect.width) {
				orientation = Orientation.VERTICAL;
			} else {
				orientation = Orientation.HORIZONTAL;
			}
		}
		return orientation;
	}
	
	/**
	 * Adds a listener for the change events that get fired when the user adjusts the value bar
	 * @param _listener The GuiChangeListener to add
	 */
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		GuiChangeEvent e;
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!_event.isConsumed()) {
					m_fValue -= m_fStepSize;
					if (m_fValue < m_fMin) {
						m_fValue = m_fMin;
					}
					e = new GuiChangeEvent(this, new Float(m_fValue));
					GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!_event.isConsumed()) {
					m_fValue += m_fStepSize;
					if (m_fValue > m_fMax) {
						m_fValue = m_fMax;
					}
					e = new GuiChangeEvent(this, new Float(m_fValue));
					GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
				}
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		handleBarChangeEvent(_event);
	}
	
	protected void processMouseDraggedEvent(GuiMouseEvent _event) {
		super.processMouseDraggedEvent(_event);
		handleBarChangeEvent(_event);
	}
	
	protected void handleBarChangeEvent(GuiMouseEvent _event) {
		if (!_event.isConsumed()) {
			Rectangle bounds = getInnerBounds();
			float fPct = (float)(_event.getX() - bounds.x) / bounds.width;
			float fRelVal = fPct * (m_fMax - m_fMin);
			fRelVal = (int)((fRelVal + m_fStepSize / 2) / m_fStepSize) * m_fStepSize;
			float fNewVal = getMinValue() + fRelVal;
			if (fNewVal < m_fMin) {
				fNewVal = m_fMin;
			} else if (fNewVal > m_fMax) {
				fNewVal = m_fMax;
			}
			setValue(fNewVal);
		}
	}
	
	protected void fireChangeEvent() {
		GuiChangeEvent e = new GuiChangeEvent(this, new Float(getValue()));
		GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
	}
}
/*
 * $Log$
 * Revision 1.17  2004/05/04 22:05:43  tako
 * Now using the new attribute map instead of individual property getters and setters.
 * Consolidated event firing code into separate methods.
 *
 * Revision 1.16  2004/03/17 00:50:46  tako
 * Colors are now cloned during initialization to prevent others from messing
 * up the Themes.
 *
 * Revision 1.15  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.13  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.12  2003/12/14 00:28:10  steven
 * added some very basic support for showing the bar value in both horiz and vert. mode.
 * Still to be done is rendering the background for the bar ourselves.
 *
 * Revision 1.11  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.10  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.9  2003/11/24 17:19:33  tako
 * Dragging the bar is now also supported.
 *
 * Revision 1.8  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.7  2003/11/19 11:19:41  tako
 * Implemented completely new event system because tryin to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.6  2003/11/19 09:09:31  tako
 * Implemented user interaction to be able to use the bar as a slider.
 * Support ChangeListeners.
 *
 * Revision 1.5  2003/11/19 00:48:46  tako
 * Had forgotten the getters and setters for the bar transparancy.
 *
 * Revision 1.4  2003/11/19 00:46:35  tako
 * Added setters for min/max and value properties.
 * Adjusted some names to be more consistent with the used coding style.
 *
 * Revision 1.3  2003/11/19 00:29:29  tako
 * Added support to render the bar with transparancy.
 *
 * Revision 1.2  2003/11/19 00:15:53  tako
 * Simplyfied code a bit by making more use of exisiting options in the
 * Widget base class.
 *
 * Revision 1.1  2003/11/18 15:58:59  steven
 * Widget that shows a progressbar, within a fixed 
 * min, max range
 *
 *
 */
