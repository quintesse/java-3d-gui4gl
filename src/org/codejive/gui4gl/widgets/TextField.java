/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Steven Lagerweij
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

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author steven
 * @version $Revision: 184 $
 */
public class TextField extends Widget {
	private String m_sText;
	
	private GLColor m_textCursorColor;
	private int m_nCursorBlinkSpeed;

	private List m_changeListeners;

	private int m_nCursorPos;
	private int m_nViewOffset;
	
	public TextField() {
		this("");
	}

	public TextField(String _sText) {
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
	
	public GLColor getTextCursorColor() {
		return m_textCursorColor;
	}
	
	public void setTextCursorColor(GLColor _color) {
		m_textCursorColor = _color;
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
	
	public void setCursorPos(int _nCursorOffset) {
		m_nCursorPos = _nCursorOffset;
		if(m_nCursorPos < 0) {
			m_nCursorPos = 0;
		}
		if(m_nCursorPos > m_sText.length()) {
			m_nCursorPos = m_sText.length();
		}
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
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		if (!_event.isConsumed()) {
			// TODO Handling clicks to position the cursor will have to wait until
			// we have a way to do some font calculations in this part of the code
		}
	}
}

/*
 * $Log$
 * Revision 1.5  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.4  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.3  2003/11/23 02:03:55  tako
 * Added new property focusedFont.
 * Renamed some existing properties to be more like the standard.
 *
 * Revision 1.2  2003/11/21 10:40:55  steven
 * update processing of events to actually consume when responded
 *
 * Revision 1.1  2003/11/21 10:01:12  steven
 * A single line textfield with editing support
 *
 *
 */
