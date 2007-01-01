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

/**
 * This widget implements an editable text field. When the widget
 * has the keyboard focus a cursor will appear indicating where
 * new letters will apear while typing. The cursor keys will
 * work as expected, moving the cursor from left to right.
 * 
 * @author steven
 * @version $Revision: 361 $
 */
public class TextField extends WidgetBase {
	private String m_sText;
	
	private List<GuiChangeListener> m_changeListeners;

	private int m_nCursorPos;
	private int m_nViewOffset;
	
	/**
	 * Creates a new TextField without any actual text in it yet.
	 */
	public TextField() {
		this("");
	}

	/**
	 * Creates a new TextField with the given string as its content
	 * @param _sText The text to use as the contents for the new widget
	 */
	public TextField(String _sText) {
		m_changeListeners = new LinkedList<GuiChangeListener>();
		setFocusable(true);
		setText(_sText);
	}

	/**
	 * Returns the current contents of the Text.
	 * @return The current text
	 */
	public String getText() {
		return m_sText;
	}
	
	/**
	 * Sets a new text for the widget
	 * @param _sText The new text to use as the contents
	 */
	public void setText(String _sText) {
		m_sText = _sText;
		int l = m_sText.length();
		if (m_nCursorPos > l) {
			m_nCursorPos = l;
		}
		if (m_nViewOffset > l) {
			m_nViewOffset = l;
		}
		fireChangeEvent();
	}
	
	/**
	 * Returns the index of the first character that gets displayed in the field.
	 * This is needed for strings that are to large to fit entirely into the
	 * field. When that happens and the cursor would pass beyond the start or end
	 * of the field it will stay in its place and the text will scroll left or
	 * right.
	 * @return
	 */
	public int getViewOffset() {
		return m_nViewOffset;
	}
	
	/**
	 * Sets the index of the first character that should be displayed in the field.
	 * @param _nViewOffset The index of the first character to display.
	 */
	public void setViewOffset(int _nViewOffset) {
		m_nViewOffset = _nViewOffset;
	}
	
	/**
	 * Returns the index of the current cursor position within the text.
	 * @return The cursor position
	 */
	public int getCursorPos() {
		return m_nCursorPos;
	}
	
	/**
	 * Sets the index of the new cursor position within the text.
	 * @param _nCursorOffset The new cursor position
	 */
	public void setCursorPos(int _nCursorOffset) {
		m_nCursorPos = _nCursorOffset;
		if(m_nCursorPos < 0) {
			m_nCursorPos = 0;
		}
		if(m_nCursorPos > m_sText.length()) {
			m_nCursorPos = m_sText.length();
		}
	}
	
	/**
	 * Adds a listener for the GuiChange event that will be
	 * fired when the user changes the contents of the text field.
	 * @param _listener The listener to add to the list of listeners
	 */
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	@Override
	public void processKeyPressedEvent(GuiKeyEvent _event) {
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
						setText(m_sText.substring(0, m_nCursorPos) + m_sText.substring(m_nCursorPos + 1));
					}
					// Move this break into the if above to have DELETE work as backspace when at end of text
					break;
					
				case KeyEvent.VK_BACK_SPACE :
					_event.consume();
					if(m_nCursorPos > 0) {
						int p = m_nCursorPos;
						setText(m_sText.substring(0, m_nCursorPos-1) + m_sText.substring(m_nCursorPos));
						m_nCursorPos = p - 1;
					}
					break;
					
				default:				
					char cKeyChar = _event.getKeyChar();
					
					if((cKeyChar != KeyEvent.CHAR_UNDEFINED) && (_event.getKeyCode() != KeyEvent.VK_ESCAPE) && (_event.getKeyCode() != KeyEvent.VK_ENTER)) {
						_event.consume();
						int p = m_nCursorPos;
						setText(m_sText.substring(0, m_nCursorPos) + String.valueOf(cKeyChar) + m_sText.substring(m_nCursorPos));
						m_nCursorPos = p + 1;
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
	
	@Override
	public void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		if (!_event.isConsumed()) {
			// TODO Handling clicks to position the cursor will have to wait until
			// we have a way to do some font calculations in this part of the code
		}
	}
	
	protected void fireChangeEvent() {
		GuiChangeEvent e = new GuiChangeEvent(this, getText());
		GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
	}
}

/*
 * $Log$
 * Revision 1.11  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.10  2004/05/04 22:05:43  tako
 * Now using the new attribute map instead of individual property getters and setters.
 * Consolidated event firing code into separate methods.
 *
 * Revision 1.9  2004/03/17 00:50:46  tako
 * Colors are now cloned during initialization to prevent others from messing
 * up the Themes.
 *
 * Revision 1.8  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.6  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
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
