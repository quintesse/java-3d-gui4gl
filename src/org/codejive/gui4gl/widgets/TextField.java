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
 * @author steven
 * @version $Revision: 119 $
 */
public class TextField extends Widget {
	private String m_sText;
	
	private Font m_textFont;
	private GLColor m_textCursorColor;
	private GLColor m_textFontColor;
	private GLColor m_textFontFocusedColor;
	private int m_nTextAlignment;
	private List m_changeListeners;

	private int m_nCursorPos;
	private int m_nViewOffset;
	private int m_nCursorBlinkSpeed;
	
	public TextField() {
		this("");
	}

	public TextField(String _sText) {
		m_textFont = (Font)Theme.getValue(getClass(), "textFont");
		m_textFontColor = (GLColor)Theme.getValue(getClass(), "textFontColor");
		m_textFontFocusedColor = (GLColor)Theme.getValue(getClass(), "textFontFocusedColor");
		m_textCursorColor = (GLColor)Theme.getValue(getClass(), "textCursorColor");
		m_nCursorBlinkSpeed = Theme.getIntegerValue(getClass(), "textCursorBlinkSpeed");
		
		m_changeListeners = new LinkedList();
		setFocusable(true);
		setText(_sText);
	}

	public String getText() {
		return m_sText;
	}
	
	public void setText(String _sText) {
		m_sText = _sText;
		m_nCursorPos = m_sText.length();
		m_nViewOffset = 0;
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

	public int getViewOffset() {
		return m_nViewOffset;
	}
	
	public void setViewOffset(int _nViewOffset) {
		m_nViewOffset = _nViewOffset;
	}
	
	public int getCursorBlinkSpeed() {
		return m_nCursorBlinkSpeed;
	}
	
	public void setCursorBlinkSpeed(int _nCursorBlinkSpeed) {
		m_nCursorBlinkSpeed = _nCursorBlinkSpeed;
	}
	
	public int getCursorPos() {
		return m_nCursorPos;
	}
	
	public int setCursorPos(int _nCursorOffset) {
		return m_nCursorPos;
	}
	
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		GuiChangeEvent e;
		if (!_event.isConsumed()) {
			switch (_event.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					_event.consume();
					m_nCursorPos--;
					if(m_nCursorPos < 0) {
						m_nCursorPos = 0;
					}					
					break;
				case KeyEvent.VK_RIGHT:
					_event.consume();
					m_nCursorPos++;
					if(m_nCursorPos > m_sText.length()) {
						m_nCursorPos = m_sText.length();
					}
					break;
	
				case KeyEvent.VK_HOME :
					_event.consume();
					m_nCursorPos=0;
					break;
					
				case KeyEvent.VK_END :
					_event.consume();
					m_nCursorPos=m_sText.length();
					m_nViewOffset = 0;
					break;
					
					
				case KeyEvent.VK_DELETE :
					_event.consume();
					if(m_nCursorPos < m_sText.length()) {
						m_sText = m_sText.substring(0, m_nCursorPos) + m_sText.substring(m_nCursorPos + 1);
						e = new GuiChangeEvent(this, m_sText);
						GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
						break;
					}
					// fallthrough to work as backspace when at end of text
				case KeyEvent.VK_BACK_SPACE :
					_event.consume();
					if(m_nCursorPos > 0) {
						m_sText = m_sText.substring(0, m_nCursorPos-1) + m_sText.substring(m_nCursorPos);
						m_nCursorPos--;
						e = new GuiChangeEvent(this, m_sText);
						GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
					}
					break;
					
				default:				
					char cKeyChar = _event.getKeyChar();
					
					if((cKeyChar != KeyEvent.CHAR_UNDEFINED) && (_event.getKeyCode() != KeyEvent.VK_ESCAPE) && (_event.getKeyCode() != KeyEvent.VK_ENTER)) {
						_event.consume();
						m_sText = m_sText.substring(0, m_nCursorPos) + String.valueOf(cKeyChar) + m_sText.substring(m_nCursorPos);
						m_nCursorPos++;
						e = new GuiChangeEvent(this, m_sText);
						GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
					} else {
						super.processKeyPressedEvent(_event);
					}
					break;
			}
		}
		if(m_nViewOffset > m_nCursorPos) {
			m_nViewOffset = m_nCursorPos;
		}
	}	
	
}

/*
 * $Log$
 * Revision 1.2  2003/11/21 10:40:55  steven
 * update processing of events to actually consume when responded
 *
 * Revision 1.1  2003/11/21 10:01:12  steven
 * A single line textfield with editing support
 *
 *
 */
