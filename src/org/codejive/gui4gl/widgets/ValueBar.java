/*
 * Created on Nov 18, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author steven
 * @version $Revision: 86 $
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
		m_focusedBarColor = (GLColor)Theme.getValue(getClass(), "focusedBarColor");
		m_fFocusedBarTransparancy = Theme.getFloatValue(getClass(), "focusedBarTransparancy");
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
	
	public class ValueChangeEvent extends ChangeEvent {
		private Object m_value;
		
		public ValueChangeEvent(Object _source, Object _value) {
			super(_source);
			m_value = _value;
		}
		
		public Object getValue() {
			return m_value;
		}
	}
	
	public void addChangeListener(ChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(KeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				m_fValue -= m_fStepSize;
				if (m_fValue > m_fMax) {
					m_fValue = m_fMax;
				}
				break;
			case KeyEvent.VK_RIGHT:
				m_fValue += m_fStepSize;
				if (m_fValue < m_fMin) {
					m_fValue = m_fMin;
				}
				ValueChangeEvent e = new ValueChangeEvent(this, new Float(m_fValue));
				if (!_event.isConsumed() && !m_changeListeners.isEmpty()) {
					Iterator i = m_changeListeners.iterator();
					while (i.hasNext() && !_event.isConsumed()) {
						ChangeListener listener = (ChangeListener)i.next();
						listener.stateChanged(e);
					}
				}
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
}
/*
 * $Log$
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
