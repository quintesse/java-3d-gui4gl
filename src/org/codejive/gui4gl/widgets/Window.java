/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.util.Iterator;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 78 $
 */
public class Window extends Container {
	private String m_sTitle;
	private GLColor m_titlebarColor;
	private float m_fTitlebarTransparancy;
	private Font m_captionFont;
	private GLColor m_captionFontColor;
	private int m_nCaptionXPadding;
	private int m_nCaptionYPadding;
	private int m_nCaptionAlignment;
	private GLColor m_activeTitlebarColor;
	private float m_fActiveTitlebarTransparancy;
	private Font m_activeCaptionFont;
	private GLColor m_activeCaptionFontColor;
	private int m_nActiveCaptionXPadding;
	private int m_nActiveCaptionYPadding;
	private boolean m_bCenterParent;
	
	public Window() {
		this(null);
	}

	public Window(String _sTitle) {
		m_sTitle = _sTitle;
		m_titlebarColor = (GLColor)Theme.getValue(getClass(), "titlebarColor");
		m_fTitlebarTransparancy = Theme.getFloatValue(getClass(), "titlebarTransparancy");
		m_captionFont = (Font)Theme.getValue(getClass(), "captionFont");
		m_captionFontColor = (GLColor)Theme.getValue(getClass(), "captionFontColor");
		m_nCaptionXPadding = Theme.getIntegerValue(getClass(), "captionXPadding");
		m_nCaptionYPadding = Theme.getIntegerValue(getClass(), "captionYPadding");
		m_nCaptionAlignment = Theme.getIntegerValue(getClass(), "captionAlignment");
		m_activeTitlebarColor = (GLColor)Theme.getValue(getClass(), "activeTitlebarColor");
		m_fActiveTitlebarTransparancy = Theme.getFloatValue(getClass(), "activeTitlebarTransparancy");
		m_activeCaptionFont = (Font)Theme.getValue(getClass(), "activeCaptionFont");
		m_activeCaptionFontColor = (GLColor)Theme.getValue(getClass(), "activeCaptionFontColor");
		m_nActiveCaptionXPadding = Theme.getIntegerValue(getClass(), "activeCaptionXPadding");
		m_nActiveCaptionYPadding = Theme.getIntegerValue(getClass(), "activeCaptionYPadding");
		m_bCenterParent = false;
		setVisible(false);
		setFocusable(true);
	}

	public String getTitle() {
		return m_sTitle;
	}
	
	public void setTitle(String _sTitle) {
		m_sTitle = _sTitle;
	}
	
	public GLColor getTitlebarColor() {
		return m_titlebarColor;
	}
	
	public void setTitlebarColor(GLColor _color) {
		m_titlebarColor = _color;
	}
	
	public float getTitlebarTransparancy() {
		return m_fTitlebarTransparancy;
	}
	
	public void setTitlebarTransparancy(float _fTransparancy) {
		m_fTitlebarTransparancy = _fTransparancy;
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
	
	public int getCaptionXPadding() {
		return m_nCaptionXPadding;
	}
	
	public void setCaptionXPadding(int _nPadding) {
		m_nCaptionXPadding = _nPadding;
	}
	
	public int getCaptionYPadding() {
		return m_nCaptionYPadding;
	}
	
	public void setCaptionYPadding(int _nPadding) {
		m_nCaptionYPadding = _nPadding;
	}
	
	public int getCaptionAlignment() {
		return m_nCaptionAlignment;
	}
	
	public void setCaptionAlignment(int _nAlignment) {
		m_nCaptionAlignment = _nAlignment;
	}
	
	public GLColor getActiveTitlebarColor() {
		return m_activeTitlebarColor;
	}
	
	public void setActiveTitlebarColor(GLColor _color) {
		m_activeTitlebarColor = _color;
	}
	
	public float getActiveTitlebarTransparancy() {
		return m_fActiveTitlebarTransparancy;
	}
	
	public void setActiveTitlebarTransparancy(float _fTransparancy) {
		m_fActiveTitlebarTransparancy = _fTransparancy;
	}
	
	public Font getActiveCaptionFont() {
		return m_activeCaptionFont;
	}
	
	public void setActiveCaptionFont(Font _font) {
		m_activeCaptionFont = _font;
	}
	
	public GLColor getActiveCaptionFontColor() {
		return m_activeCaptionFontColor;
	}
	
	public void setActiveCaptionFontColor(GLColor _color) {
		m_activeCaptionFontColor = _color;
	}
	
	public int getActiveCaptionXPadding() {
		return m_nActiveCaptionXPadding;
	}
	
	public void setActiveCaptionXPadding(int _nPadding) {
		m_nActiveCaptionXPadding = _nPadding;
	}
	
	public int getActiveCaptionYPadding() {
		return m_nActiveCaptionYPadding;
	}
	
	public void setActiveCaptionYPadding(int _nPadding) {
		m_nActiveCaptionYPadding = _nPadding;
	}
	
	public boolean isCenterParent() {
		return m_bCenterParent;
	}
	
	public void setCenterParent(boolean _bCenter) {
		m_bCenterParent = _bCenter;
	}
	
	public void setVisible(boolean _bVisible) {
		super.setVisible(_bVisible);
		if (!_bVisible && (getParent() != null)) {
			getParent().setFocusWidget(null);
		}
	}
	
	public boolean isActive() {
		return (getFocusWidget() != null) && getFocusWidget().hasFocus();
	}

	public void activate() {
		if (isVisible()) {
			if (getFocusWidget() == null) {
				Iterator i = getChildren();
				while (i.hasNext()) {
					Widget w = (Widget)i.next();
					if (w.isFocusable()) {
						w.setFocus();
						break;
					}
				}
			} else {
				getFocusWidget().setFocus();
			}
		}
	}
	
	public void updateBounds(int _parentWidth, int _parentHeight) {
		if (isCenterParent()) {
			Rectangle rect = getRectangle();
			rect.x = (_parentWidth - rect.width) / 2;
			rect.y = (_parentHeight - rect.height) / 2;
		}
	}
}

/*
 * $Log$
 * Revision 1.5  2003/11/19 00:17:42  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
