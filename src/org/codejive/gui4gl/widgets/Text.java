/*
 * Created on Nov 5, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 93 $
 */
public class Text extends Widget {
	private String m_sText;
	
	private Font m_textFont;
	private GLColor m_textCursorColor;
	private GLColor m_textFontColor;
	private GLColor m_textFontFocusedColor;
	private int m_nTextAlignment;
	private List m_changeListeners;

	private int m_nEditingPos;
	
	public Text() {
		this(null);
	}

	public Text(String _sText) {
		m_sText = _sText;
		m_textFont = (Font)Theme.getValue(getClass(), "textFont");
		m_textFontColor = (GLColor)Theme.getValue(getClass(), "textFontColor");
		m_textFontFocusedColor = (GLColor)Theme.getValue(getClass(), "textFontFocusedColor");
		m_textCursorColor = (GLColor)Theme.getValue(getClass(), "textCursorColor");
		m_nTextAlignment = Theme.getIntegerValue(getClass(), "textAlignment");
		
		m_changeListeners = new LinkedList();
		setFocusable(false);
		m_nEditingPos = _sText.length();
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

	public GLColor getTextCursorColor() {
		return m_textCursorColor;
	}
	
	public void setTextCursorColor(GLColor _color) {
		m_textCursorColor = _color;
	}
	
	public GLColor getTextFontFocusedColor() {
		return m_textFontFocusedColor;
	}
	
	public void setTextFontFocusedColor(GLColor _color) {
		m_textFontFocusedColor = _color;
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

	
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		GuiChangeEvent e;
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!_event.isConsumed()) {
					m_nEditingPos--;
					if(m_nEditingPos < 0) {
						m_nEditingPos = 0;
					}
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!_event.isConsumed()) {
					m_nEditingPos++;
					if(m_nEditingPos > m_sText.length()) {
						m_nEditingPos = m_sText.length();
					}
				}
				break;
				
			case KeyEvent.VK_DELETE :
				if(m_nEditingPos < m_sText.length()) {
					m_sText = m_sText.substring(0, m_nEditingPos) + m_sText.substring(m_nEditingPos + 1);
					e = new GuiChangeEvent(this, m_sText);
					GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
					break;
				}
				// fallthrough to work as backspace when at end of text
			case KeyEvent.VK_BACK_SPACE :
				if(m_nEditingPos > 0) {
					m_sText = m_sText.substring(0, m_nEditingPos-1) + m_sText.substring(m_nEditingPos);
					m_nEditingPos--;
					e = new GuiChangeEvent(this, m_sText);
					GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
				}
				break;
				
			default:				
				char cKeyChar = _event.getKeyChar();
				
				// mmm escape seems to be a valid character...
				if((cKeyChar != KeyEvent.CHAR_UNDEFINED) && (_event.getKeyCode() != KeyEvent.VK_ESCAPE)) {
					m_sText = m_sText.substring(0, m_nEditingPos) + String.valueOf(cKeyChar) + m_sText.substring(m_nEditingPos);
					m_nEditingPos++;
					e = new GuiChangeEvent(this, m_sText);
					GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
				} else {
					super.processKeyPressedEvent(_event);
				}
				break;
		}
	}	
	
}

/*
 * $Log$
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
