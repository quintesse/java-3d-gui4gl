/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes;

import java.util.HashMap;

import org.codejive.gui4gl.themes.blues.BluesThemeConfig;

/**
 * @author tako
 */
public class Theme {
	private HashMap m_values;
	
	public Theme() {
		m_values = new HashMap();
	}
	
	private HashMap getValues() {
		return m_values;
	}
	
	private static Object getClassValue(Class _class, String _sKey) {
		return getTheme().getValues().get(_class.getName() + ":" + _sKey);
	}
	
	public static Object getValue(Class _class, String _sKey) {
		Object value = getClassValue(_class, _sKey);
		if ((value == null) && (_class.getSuperclass() != null)) {
			value = getValue(_class.getSuperclass(), _sKey);
		}
		return value;
	}
	
	public static void setValue(Class _class, String _sKey, Object _value) {
		getTheme().getValues().put(_class.getName() + ":" + _sKey, _value);
	}
	
	public static int getIntegerValue(Class _class, String _sKey) {
		Integer value = (Integer)getValue(_class, _sKey);
		return value.intValue();
	}
	
	public static void setIntegerValue(Class _class, String _sKey, int _nValue) {
		setValue(_class, _sKey, new Integer(_nValue));
	}
	
	public static float getFloatValue(Class _class, String _sKey) {
		Float value = (Float)getValue(_class, _sKey);
		return value.floatValue();
	}
	
	public static void setFloatValue(Class _class, String _sKey, float _fValue) {
		setValue(_class, _sKey, new Float(_fValue));
	}
	
	public static boolean getBooleanValue(Class _class, String _sKey) {
		Boolean value = (Boolean)getValue(_class, _sKey);
		return value.booleanValue();
	}
	
	public static void setBooleanValue(Class _class, String _sKey, boolean _bValue) {
		setValue(_class, _sKey, new Boolean(_bValue));
	}
	
	private static Theme m_theme = null;

	public static Theme getTheme() {
		if (m_theme == null) {
			m_theme = new Theme();
			BluesThemeConfig config = new BluesThemeConfig();
			config.configure();
		}
		return m_theme;
	}
}
