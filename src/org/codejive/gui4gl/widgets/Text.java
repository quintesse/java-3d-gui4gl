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
 * Created on Nov 5, 2003
 */
package org.codejive.gui4gl.widgets;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 158 $
 */
public class Text extends Widget {
	private String m_sText;
	
	private Font m_textFont;
	private GLColor m_textFontColor;
	private int m_nTextAlignment;
	
	public Text() {
		this(null);
	}

	public Text(String _sText) {
		m_sText = _sText;
		m_textFont = (Font)Theme.getValue(getClass(), "textFont");
		m_textFontColor = (GLColor)Theme.getValue(getClass(), "textFontColor");
		m_nTextAlignment = Theme.getIntegerValue(getClass(), "textAlignment");
	}

	public String getText() {
		return m_sText;
	}
	
	public void setText(String _sText) {
		m_sText = _sText;
	}
	
	public Font getTextFont() {
		return m_textFont;
	}
	
	public void setTextFont(Font _font) {
		m_textFont = _font;
	}

	public GLColor getTextFontColor() {
		return m_textFontColor;
	}
	
	public void setTextFontColor(GLColor _color) {
		m_textFontColor = _color;
	}
	
	public int getTextAlignment() {
		return m_nTextAlignment;
	}
	
	public void setTextAlignment(int _nTextAlignment) {
		m_nTextAlignment = _nTextAlignment;
	}	
}

/*
 * $Log$
 * Revision 1.6  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.5  2003/11/21 10:00:38  steven
 * Moved editing support to a different class
 *
 * Revision 1.4  2003/11/19 17:11:54  steven
 * Preliminary check in of Text widget editing
 *
 * Revision 1.3  2003/11/19 00:13:28  tako
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
