/*
 * Created on Nov 18, 2003
 */
package org.codejive.gui4gl.widgets;

import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author steven
 * @version $Revision: 81 $
 */
public class ValueBar extends Widget {
	float m_min;
	float m_max;
	float m_value;

	GLColor m_barColor;
	float m_fBarTransparancy;
	GLColor m_focusedBarColor;
	float m_fFocusedBarTransparancy;
	
	public ValueBar(float _min, float _max) {
		m_min = _min;
		m_max = _max;
		m_barColor = (GLColor)Theme.getValue(getClass(), "barColor");
		m_fBarTransparancy = Theme.getFloatValue(getClass(), "barTransparancy");
		m_focusedBarColor = (GLColor)Theme.getValue(getClass(), "focusedBarColor");
		m_fFocusedBarTransparancy = Theme.getFloatValue(getClass(), "focusedBarTransparancy");
	}
	
	public void setValue(float _value) {
		m_value = _value;
	}

	public float getValue() {
		return m_value;
	}
	public float getMinValue() {
		return m_min;
	}
	public float getMaxValue() {
		return m_max;
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
