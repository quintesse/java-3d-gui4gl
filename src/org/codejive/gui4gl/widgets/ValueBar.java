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

import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author steven
 * @version $Revision: 184 $
 */
public class ValueBar extends Widget {
	private float m_fMin;
	private float m_fMax;
	private float m_fValue;
	private float m_fStepSize;

	private GLColor m_barColor;
	private float m_fBarTransparancy;
	private GLColor m_focusedBarColor;
	private float m_fFocusedBarTransparancy;
	private GLColor m_disabledBarColor;
	private float m_fDisabledBarTransparancy;
	
	private List m_changeListeners;
	
	public ValueBar(float _fMin, float _fMax) {
		this(_fMin, _fMax, 1.0f);
	}
	
	public ValueBar(float _fMin, float _fMax, float _fStepSize) {
		m_fMin = _fMin;
		m_fMax = _fMax;
		m_fStepSize = _fStepSize;
		m_barColor = (GLColor)Theme.getValue(getClass(), "barColor");
		m_fBarTransparancy = Theme.getFloatValue(getClass(), "barTransparancy");
		m_focusedBarColor = (GLColor)Theme.getValue(getClass(), "barColor#focused");
		m_fFocusedBarTransparancy = Theme.getFloatValue(getClass(), "barTransparancy#focused");
		m_disabledBarColor = (GLColor)Theme.getValue(getClass(), "barColor#disabled");
		m_fDisabledBarTransparancy = Theme.getFloatValue(getClass(), "barTransparancy#disabled");
		m_changeListeners = new LinkedList();
		setFocusable(true);
	}
	
	public float getValue() {
		return m_fValue;
	}
	
	public void setValue(float _value) {
		m_fValue = _value;
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
	
	public GLColor getBarColor() {
		return m_barColor;
	}
	
	public void setBarColor(GLColor _color) {
		m_barColor = _color;
	}
	
	public float getBarTransparancy() {
		return m_fBarTransparancy;
	}
	
	public void setBarTransparancy(float _fTransparancy) {
		m_fBarTransparancy = _fTransparancy;
	}
	
	public GLColor getFocusedBarColor() {
		return m_focusedBarColor;
	}
	
	public void setFocusedBarColor(GLColor _color) {
		m_focusedBarColor = _color;
	}

	public float getFocusedBarTransparancy() {
		return m_fFocusedBarTransparancy;
	}
	
	public void setFocusedBarTransparancy(float _fTransparancy) {
		m_fFocusedBarTransparancy = _fTransparancy;
	}	
	
	public GLColor getDisabledBarColor() {
		return m_disabledBarColor;
	}
	
	public void setDisabledBarColor(GLColor _color) {
		m_disabledBarColor = _color;
	}

	public float getDisabledBarTransparancy() {
		return m_fDisabledBarTransparancy;
	}
	
	public void setDisabledBarTransparancy(float _fTransparancy) {
		m_fDisabledBarTransparancy = _fTransparancy;
	}	
	
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
			m_fValue = m_fMin + fRelVal;
			GuiChangeEvent e = new GuiChangeEvent(this, new Float(m_fValue));
			GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
		}
	}
}
/*
 * $Log$
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
