/*
 * Created on Nov 18, 2003
 */
package org.codejive.gui4gl.widgets;

import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author steven
 * @version $Revision: 61 $
 */
public class ValueBar extends Widget {
	float m_min;
	float m_max;
	float m_value;
	GLColor m_fillColor;
	GLColor m_valueColor;
	
	int m_xPad;
	int m_yPad;
	
	public ValueBar(float _min, float _max) {
		m_min = _min;
		m_max = _max;
		m_fillColor = (GLColor)Theme.getValue(getClass(), "barFillColor");
		m_valueColor = (GLColor)Theme.getValue(getClass(), "barValueColor");
		m_xPad = ((Integer)Theme.getValue(getClass(), "barXPadding")).intValue();
		m_yPad = ((Integer)Theme.getValue(getClass(), "barYPadding")).intValue();
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
	
	public GLColor getValueColor() {
		return m_valueColor;
	}
	
	public GLColor getFillColor() {
		return m_fillColor;
	}
	
	public void setFillColor(GLColor _aColor) {
		m_fillColor = _aColor;
	}
	public void setValueColor(GLColor _aColor) {
		m_valueColor = _aColor;
	}
	
	public int getXPadding() {
		return m_xPad;
	}
	public int getYPadding() {
		return m_yPad;
	}
}
/*
 * $Log$
 * Revision 1.1  2003/11/18 15:58:59  steven
 * Widget that shows a progressbar, within a fixed 
 * min, max range
 *
 *
 */
