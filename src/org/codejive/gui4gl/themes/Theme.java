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
import org.codejive.gui4gl.widgets.Container;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 252 $
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
	
	private static Object getClassesValue(Class _class, String _sKey) {
		// First try to find an exact match for class and key
		Object value = getClassValue(_class, _sKey);
		if ((value == null) && (_class.getSuperclass() != null)) {
			// Then try all the super classes
			value = getClassesValue(_class.getSuperclass(), _sKey);
		}
		return value;
	}
	
	public static Object getValue(Widget _widget, Class _class, String _sKey) {
		// First try to find an exact match for the key
		Object value = getClassesValue(_class, _sKey);
		if (value == null) {
			// Then see if the key contains a # and try
			// again using only the part before the #
			int p = _sKey.lastIndexOf('#');
			if (p > 0) {
				String sName = _sKey.substring(0, p);
				value = getClassesValue(_class, sName);
			}
		}
		return value;
	}
	
	private static Object getValueByName(Widget _widget, String _sKey) {
		Object value = null;
		// First see if the widget has a parent
		Widget parent = _widget.getParent();
		if (parent != null) {
			// Then see if the widget has a name and its parent is not a Container
			String sName = _widget.getName(); 
			if ((sName != null) && !(parent instanceof Container)) {
				// Let's see if out parent stores settings for us because they will override any local settings
				value = getValueByName(parent, sName + "." + _sKey);
			}
		}
		// If the parent didn't supply a value we try local settings
		if (value == null) {
			Class cls = _widget.getClass();
			value = getClassesValue(cls, _sKey);
		}
		return value;
	}
	
	public static Object getValue(Widget _widget, String _sKey) {
		// First try to find an exact match for the key
		Object value = getValueByName(_widget, _sKey);
		if (value == null) {
			// Then see if the key contains a # and try
			// again using only the part before the #
			int p = _sKey.lastIndexOf('#');
			if (p > 0) {
				String sName = _sKey.substring(0, p);
				value = getValueByName(_widget, sName);
			}
		}
		return value;
	}
	
	public static void setValue(Class _class, String _sKey, Object _value) {
		getTheme().getValues().put(_class.getName() + ":" + _sKey, _value);
	}
	
	public static int getIntegerValue(Widget _widget, String _sKey) {
		Integer value = (Integer)getValue(_widget, _sKey);
		return value.intValue();
	}

	public static void setIntegerValue(Class _class, String _sKey, int _nValue) {
		setValue(_class, _sKey, new Integer(_nValue));
	}
	
	public static float getFloatValue(Widget _widget, String _sKey) {
		Float value = (Float)getValue(_widget, _sKey);
		return value.floatValue();
	}
	
	public static void setFloatValue(Class _class, String _sKey, float _fValue) {
		setValue(_class, _sKey, new Float(_fValue));
	}
	
	public static boolean getBooleanValue(Widget _widget, String _sKey) {
		Boolean value = (Boolean)getValue(_widget, _sKey);
		return value.booleanValue();
	}
	
	public static void setBooleanValue(Class _class, String _sKey, boolean _bValue) {
		setValue(_class, _sKey, new Boolean(_bValue));
	}
	
	private static Theme m_theme = null;

	public static Theme getTheme() {
		return m_theme;
	}
	
	public static void setTheme(Theme _theme) {
		m_theme = _theme;
	}
	
	public static void setDefaultTheme(RenderContext _context) {
		BluesThemeConfig config = new BluesThemeConfig();
		loadTheme(_context, config);
	}
	
	public static void loadTheme(RenderContext _context, ThemeConfig _config) {
		m_theme = new Theme();
		_config.configure(_context);
	}
}

/*
 * $Log$
 * Revision 1.10  2004/05/04 23:58:21  tako
 * Theme will now try for an exact match first in parent and local widget before
 * stripping off any state modifiers and trying again.
 *
 * Revision 1.9  2004/05/04 22:30:23  tako
 * Theme attribute getters and setters now also take a Widget as argument.
 * This to support the Widget's new attribute maps.
 *
 * Revision 1.8  2004/03/07 18:16:42  tako
 * ThemeConfig now needs a RenderContext to function. Because of that
 * it is not possible anymore to select a default theme anymore!
 * Use Theme.setDefaultConfig() first when starting to use use gui4gl.
 *
 * Revision 1.7  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.6  2003/12/05 01:07:40  tako
 * Implemented property classes.
 *
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
