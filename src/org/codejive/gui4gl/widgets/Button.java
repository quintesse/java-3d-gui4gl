/*
 * Created on Nov 3, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 48 $
 */
public class Button extends Widget {
	private String m_sCaption;
	private Font m_captionFont;
	private GLColor m_captionFontColor;
	private int m_nCaptionPadding;
	private int m_nCaptionAlignment;
	private Font m_focusedCaptionFont;
	private GLColor m_focusedCaptionFontColor;
	private int m_nFocusedCaptionPadding;
	private Font m_selectedCaptionFont;
	private GLColor m_selectedCaptionFontColor;
	private int m_nSelectedCaptionPadding;
	private GLColor m_selectedBackgroundColor;
	private float m_fSelectedTransparancy;
	
	private List m_actionListeners;
	private boolean m_bSelected;
	
	public Button() {
		this(null);
	}

	public Button(String _sCaption) {
		m_sCaption = _sCaption;
		m_captionFont = (Font)Theme.getValue(getClass(), "captionFont");
		m_captionFontColor = (GLColor)Theme.getValue(getClass(), "captionFontColor");
		m_nCaptionPadding = Theme.getIntegerValue(getClass(), "captionPadding");
		m_nCaptionAlignment = Theme.getIntegerValue(getClass(), "captionAlignment");
		m_focusedCaptionFont = (Font)Theme.getValue(getClass(), "focusedCaptionFont");
		m_focusedCaptionFontColor = (GLColor)Theme.getValue(getClass(), "focusedCaptionFontColor");
		m_nFocusedCaptionPadding = Theme.getIntegerValue(getClass(), "focusedCaptionPadding");
		m_selectedCaptionFont = (Font)Theme.getValue(getClass(), "selectedCaptionFont");
		m_selectedCaptionFontColor = (GLColor)Theme.getValue(getClass(), "selectedCaptionFontColor");
		m_nSelectedCaptionPadding = Theme.getIntegerValue(getClass(), "selectedCaptionPadding");
		m_selectedBackgroundColor = (GLColor)Theme.getValue(getClass(), "selectedBackgroundColor");
		m_fSelectedTransparancy = Theme.getFloatValue(getClass(), "selectedTransparancy");
		setFocusable(true);
		
		m_actionListeners = new ArrayList();
		m_bSelected = false;
	}

	public String getCaption() {
		return m_sCaption;
	}
	
	public void setCaption(String _sCaption) {
		m_sCaption = _sCaption;
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
	
	public int getCaptionPadding() {
		return m_nCaptionPadding;
	}
	
	public void setCaptionPadding(int _nPadding) {
		m_nCaptionPadding = _nPadding;
	}
	
	public int getCaptionAlignment() {
		return m_nCaptionAlignment;
	}
	
	public void setCaptionAlignment(int _nCaptionAlignment) {
		m_nCaptionAlignment = _nCaptionAlignment;
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
	
	public int getFocusedCaptionPadding() {
		return m_nFocusedCaptionPadding;
	}
	
	public void setFocusedCaptionPadding(int _nPadding) {
		m_nFocusedCaptionPadding = _nPadding;
	}
	
	public Font getSelectedCaptionFont() {
		return m_selectedCaptionFont;
	}
	
	public void setSelectedCaptionFont(Font _font) {
		m_selectedCaptionFont = _font;
	}
	
	public GLColor getSelectedCaptionFontColor() {
		return m_selectedCaptionFontColor;
	}
	
	public void setSelectedCaptionFontColor(GLColor _color) {
		m_selectedCaptionFontColor = _color;
	}
	
	public int getSelectedCaptionPadding() {
		return m_nSelectedCaptionPadding;
	}
	
	public void setSelectedCaptionPadding(int _nPadding) {
		m_nSelectedCaptionPadding = _nPadding;
	}
	
	public GLColor getSelectedBackgroundColor() {
		return m_selectedBackgroundColor;
	}
	
	public void setSelectedBackgroundColor(float _fRed, float _fGreen, float _fBlue) {
		m_selectedBackgroundColor.set(_fRed, _fGreen, _fBlue);
	}
	
	public void setSelectedBackgroundColor(GLColor _color) {
		m_selectedBackgroundColor.set(_color);
	}
	
	public float getSelectedTransparancy() {
		return m_fSelectedTransparancy;
	}
	
	public void setSelectedTransparancy(float _fTransparancy) {
		m_fSelectedTransparancy = _fTransparancy;
	}

	public void addActionListener(ActionListener _listener) {
		m_actionListeners.add(_listener);
	}
	
	public void processKeyPressedEvent(KeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				m_bSelected = true;
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	public void processKeyReleasedEvent(KeyEvent _event) {
		m_bSelected = false;
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				ActionEvent e = new ActionEvent(this, 0, null);
				if (!_event.isConsumed() && !m_actionListeners.isEmpty()) {
					Iterator i = m_actionListeners.iterator();
					while (i.hasNext() && !_event.isConsumed()) {
						ActionListener listener = (ActionListener)i.next();
						listener.actionPerformed(e);
					}
				}
				break;
			default:
				super.processKeyReleasedEvent(_event);
				break;
		}
	}
	
	public boolean isSelected() {
		return m_bSelected;
	}
}

/*
 * $Log$
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
