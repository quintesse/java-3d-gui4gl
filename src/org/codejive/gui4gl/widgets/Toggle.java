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
 * Created on Nov 21, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 158 $
 */
public class Toggle extends Widget {
	private String m_sCaption;
	private boolean m_bChecked;
	private Font m_captionFont;
	private GLColor m_captionFontColor;
	private int m_nCaptionAlignment;
	private GLColor m_checkColor;
	private GLColor m_checkBackgroundColor;
	private float m_fCheckTransparancy;
	private Font m_focusedCaptionFont;
	private GLColor m_focusedCaptionFontColor;
	private GLColor m_focusedCheckColor;
	private GLColor m_focusedCheckBackgroundColor;
	private float m_fFocusedCheckTransparancy;
	
	private List m_changeListeners;
	
	public Toggle() {
		this(null, false);
	}

	public Toggle(String _sCaption, boolean _bChecked) {
		m_sCaption = _sCaption;
		m_bChecked = _bChecked;
		m_captionFont = (Font)Theme.getValue(getClass(), "captionFont");
		m_captionFontColor = (GLColor)Theme.getValue(getClass(), "captionFontColor");
		m_nCaptionAlignment = Theme.getIntegerValue(getClass(), "captionAlignment");
		m_checkColor = (GLColor)Theme.getValue(getClass(), "checkColor");
		m_checkBackgroundColor = (GLColor)Theme.getValue(getClass(), "checkBackgroundColor");
		m_fCheckTransparancy = Theme.getFloatValue(getClass(), "checkTransparancy");
		m_focusedCaptionFont = (Font)Theme.getValue(getClass(), "focusedCaptionFont");
		m_focusedCaptionFontColor = (GLColor)Theme.getValue(getClass(), "focusedCaptionFontColor");
		m_focusedCheckColor = (GLColor)Theme.getValue(getClass(), "focusedCheckColor");
		m_focusedCheckBackgroundColor = (GLColor)Theme.getValue(getClass(), "focusedCheckBackgroundColor");
		m_fFocusedCheckTransparancy = Theme.getFloatValue(getClass(), "focusedCheckTransparancy");
		setFocusable(true);
		
		m_changeListeners = new ArrayList();
	}

	public String getCaption() {
		return m_sCaption;
	}
	
	public void setCaption(String _sCaption) {
		m_sCaption = _sCaption;
	}
	
	public boolean isChecked() {
		return m_bChecked;
	}
	
	public void setChecked(boolean _bChecked) {
		m_bChecked = _bChecked;
	}
	
	public Font getCaptionFont() {
		return m_captionFont;
	}
	
	public void setCaptionFont(Font _font) {
		m_captionFont = _font;
	}
	
	public GLColor getCaptionFontColor() {
		return m_captionFontColor;
	}
	
	public void setCaptionFontColor(GLColor _color) {
		m_captionFontColor = _color;
	}
	
	public int getCaptionAlignment() {
		return m_nCaptionAlignment;
	}
	
	public void setCaptionAlignment(int _nCaptionAlignment) {
		m_nCaptionAlignment = _nCaptionAlignment;
	}	
	
	public GLColor getCheckColor() {
		return m_checkColor;
	}
	
	public void setCheckColor(GLColor _color) {
		m_checkColor = _color;
	}
	
	public GLColor getCheckBackgroundColor() {
		return m_checkBackgroundColor;
	}
	
	public void setCheckBackgroundColor(GLColor _color) {
		m_checkBackgroundColor = _color;
	}
	
	public float getCheckTransparancy() {
		return m_fCheckTransparancy;
	}
	
	public void setCheckTransparancy(float _fTransparancy) {
		m_fCheckTransparancy = _fTransparancy;
	}
	
	public Font getFocusedCaptionFont() {
		return m_focusedCaptionFont;
	}
	
	public void setFocusedCaptionFont(Font _font) {
		m_focusedCaptionFont = _font;
	}
	
	public GLColor getFocusedCaptionFontColor() {
		return m_focusedCaptionFontColor;
	}
	
	public void setFocusedCaptionFontColor(GLColor _color) {
		m_focusedCaptionFontColor = _color;
	}
	
	public GLColor getFocusedCheckColor() {
		return m_focusedCheckColor;
	}
	
	public void setFocusedCheckColor(GLColor _color) {
		m_focusedCheckColor = _color;
	}
	
	public GLColor getFocusedCheckBackgroundColor() {
		return m_focusedCheckBackgroundColor;
	}
	
	public void setFocusedCheckBackgroundColor(GLColor _color) {
		m_focusedCheckBackgroundColor = _color;
	}
	
	public float getFocusedCheckTransparancy() {
		return m_fFocusedCheckTransparancy;
	}
	
	public void setFocusedCheckTransparancy(float _fTransparancy) {
		m_fFocusedCheckTransparancy = _fTransparancy;
	}
	
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				m_bChecked = !m_bChecked;
				GuiChangeEvent e = new GuiChangeEvent(this, new Boolean(m_bChecked));
				GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		if (!_event.isConsumed()) {
			m_bChecked = !m_bChecked;
			GuiChangeEvent e = new GuiChangeEvent(this, new Boolean(m_bChecked));
			GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
		}
	}
}

/*
 * $Log$
 * Revision 1.3  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.2  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.1  2003/11/21 01:33:30  tako
 * First check-in of the new toggle widget.
 *
 */