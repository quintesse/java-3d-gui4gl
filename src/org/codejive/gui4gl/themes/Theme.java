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
package org.codejive.gui4gl.themes;

import java.util.HashMap;

import org.codejive.gui4gl.themes.blues.BluesThemeConfig;

/**
 * @author tako
 * @version $Revision: 158 $
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
			BluesThemeConfig config = new BluesThemeConfig();
			loadTheme(config);
		}
		return m_theme;
	}
	
	public static void setTheme(Theme _theme) {
		m_theme = _theme;
	}
	
	public static void loadTheme(ThemeConfig _config) {
		m_theme = new Theme();
		_config.configure();
	}
}

/*
 * $Log$
 * Revision 1.5  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.4  2003/11/18 12:52:47  tako
 * Added loadTheme(ThemeConfig _config) to easily set an alternative
 * global theme.
 *
 * Revision 1.3  2003/11/18 11:58:53  tako
 * Added setTheme() on request.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
