/*
 * Created on Nov 3, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.codejive.gui4gl.events.GuiActionEvent;
import org.codejive.gui4gl.events.GuiActionListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 128 $
 */
public class Button extends Widget {
	private String m_sCaption;
	private Font m_captionFont;
	private GLColor m_captionFontColor;
	private int m_nCaptionAlignment;
	private Font m_focusedCaptionFont;
	private GLColor m_focusedCaptionFontColor;
	private Font m_selectedCaptionFont;
	private GLColor m_selectedCaptionFontColor;
	private int m_nSelectedXPadding;
	private int m_nSelectedYPadding;
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
		m_nCaptionAlignment = Theme.getIntegerValue(getClass(), "captionAlignment");
		m_focusedCaptionFont = (Font)Theme.getValue(getClass(), "focusedCaptionFont");
		m_focusedCaptionFontColor = (GLColor)Theme.getValue(getClass(), "focusedCaptionFontColor");
		m_selectedCaptionFont = (Font)Theme.getValue(getClass(), "selectedCaptionFont");
		m_selectedCaptionFontColor = (GLColor)Theme.getValue(getClass(), "selectedCaptionFontColor");
		m_nSelectedXPadding = Theme.getIntegerValue(getClass(), "selectedXPadding");
		m_nSelectedYPadding = Theme.getIntegerValue(getClass(), "selectedYPadding");
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
	
	public int getSelectedXPadding() {
		return m_nSelectedXPadding;
	}
	
	public void setSelectedXPadding(int _nPadding) {
		m_nSelectedXPadding = _nPadding;
	}
	
	public int getSelectedYPadding() {
		return m_nSelectedYPadding;
	}
	
	public void setSelectedYPadding(int _nPadding) {
		m_nSelectedYPadding = _nPadding;
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

	public void addActionListener(GuiActionListener _listener) {
		m_actionListeners.add(_listener);
	}
	
	protected void updateInnerBounds() {
		if (isSelected()) {
			int nXPad = getSelectedXPadding();
			int nYPad = getSelectedYPadding();
			Rectangle bounds = getInnerBounds();
			bounds.x += nXPad;
			bounds.y += nYPad;
			bounds.width -= 2 * nXPad;
			bounds.height -= 2 * nYPad;
		} else {
			super.updateInnerBounds();
		}
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
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
	
	protected void processKeyReleasedEvent(GuiKeyEvent _event) {
		m_bSelected = false;
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				if (!_event.isConsumed()) {
					GuiActionEvent e = new GuiActionEvent(this);
					GuiActionEvent.fireActionPerformed(m_actionListeners, e);
				}
				break;
			default:
				super.processKeyReleasedEvent(_event);
				break;
		}
	}

	protected void processMousePressedEvent(GuiMouseEvent _event) {
		m_bSelected = true;
		super.processMousePressedEvent(_event);
	}
	
	protected void processMouseReleasedEvent(GuiMouseEvent _event) {
		m_bSelected = false;
		super.processMouseReleasedEvent(_event);
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		if (!_event.isConsumed()) {
			GuiActionEvent e = new GuiActionEvent(this);
			GuiActionEvent.fireActionPerformed(m_actionListeners, e);
		}
	}
	
	public boolean isSelected() {
		return m_bSelected;
	}
}

/*
 * $Log$
 * Revision 1.6  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.5  2003/11/20 00:32:11  tako
 * Code change because of change from getPadding() to
 * updateInnerBounds().
 *
 * Revision 1.4  2003/11/19 11:19:41  tako
 * Implemented completely new event system because tryin to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.3  2003/11/19 00:12:31  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
