/*
 * Created on Nov 18, 2003
 */
package org.codejive.gui4gl.widgets;

import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author steven
 * @version $Revision: 82 $
 */
public class ValueBar extends Widget {
	private float m_fMin;
	private float m_fMax;
	private float m_fValue;

	private GLColor m_barColor;
	private float m_fBarTransparancy;
	private GLColor m_focusedBarColor;
	private float m_fFocusedBarTransparancy;
	
	public ValueBar(float _min, float _max) {
		m_fMin = _min;
		m_fMax = _max;
		m_barColor = (GLColor)Theme.getValue(getClass(), "barColor");
		m_fBarTransparancy = Theme.getFloatValue(getClass(), "barTransparancy");
		m_focusedBarColor = (GLColor)Theme.getValue(getClass(), "focusedBarColor");
		m_fFocusedBarTransparancy = Theme.getFloatValue(getClass(), "focusedBarTransparancy");
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
	
	public GLColor getBarColor() {
		return m_barColor;
	}
	
	public void setBarColor(GLColor _color) {
		m_barColor = _color;
	}
	
	public GLColor getFocusedBarColor() {
		return m_focusedBarColor;
	}
	
	public void setFocusedBarColor(GLColor _color) {
		m_focusedBarColor = _color;
	}
}
/*
 * $Log$
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
