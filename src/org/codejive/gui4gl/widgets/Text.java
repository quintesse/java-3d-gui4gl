/*
 * Created on Nov 5, 2003
 */
package org.codejive.gui4gl.widgets;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 48 $
 */
public class Text extends Widget {
	private String m_sText;
	private Font m_textFont;
	private GLColor m_textFontColor;
	private int m_nTextPadding;
	private int m_nTextAlignment;
	
	public Text() {
		this(null);
	}

	public Text(String _sText) {
		m_sText = _sText;
		m_textFont = (Font)Theme.getValue(getClass(), "textFont");
		m_textFontColor = (GLColor)Theme.getValue(getClass(), "textFontColor");
		m_nTextPadding = Theme.getIntegerValue(getClass(), "textPadding");
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
	
	public int getTextPadding() {
		return m_nTextPadding;
	}
	
	public void setTextPadding(int _nPadding) {
		m_nTextPadding = _nPadding;
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
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
