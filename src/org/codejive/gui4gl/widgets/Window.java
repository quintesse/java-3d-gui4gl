/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;

/**
 * @author tako
 * @version $Revision: 112 $
 */
public class Window extends Toplevel {
	private String m_sTitle;
	private int m_nTitlebarHeight;
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
		m_nTitlebarHeight = Theme.getIntegerValue(getClass(), "titlebarHeight");
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
	
	public int getTitlebarHeight() {
		return m_nTitlebarHeight;
	}
	
	public void setTitlebarHeight(int _nHeight) {
		m_nTitlebarHeight = _nHeight;
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
		if (!_bVisible && (getParent() != null) && (getToplevel() == getScreen().getActiveToplevel())) {
			getParent().setFocusWidget(null);
		}
	}
	
	public void updateBounds() {
		super.updateBounds();
		if (isCenterParent()) {
			Rectangle inner = getParent().getInnerBounds();
			Rectangle rect = getRectangle();
			rect.x = (inner.width - rect.width) / 2;
			rect.y = (inner.height - rect.height) / 2;
		}
	}

	protected void updateInnerBounds() {
		super.updateInnerBounds();
		if (getTitle() != null) {
			Rectangle rect = getInnerBounds();
			int nTitleBarHeight = getTitlebarHeight();
			rect.y += nTitleBarHeight;
			rect.height -= nTitleBarHeight;
		}
	}
}

/*
 * $Log$
 * Revision 1.7  2003/11/21 01:32:56  tako
 * Window now extends Toplevel instead of Container.
 * Moved isActive() and activate() methods to the Toplevel class.
 * Making the window invisible doesn't take the focus away from other
 * windows anymore.
 *
 * Revision 1.6  2003/11/20 00:34:19  tako
 * Code change because of change from getPadding() to
 * updateInnerBounds().
 * Because of that change it is no longer possible to automatically adjust
 * the size of the title bar to the font size so a new property was added
 * to get/set the height of the title bar.
 *
 * Revision 1.5  2003/11/19 00:17:42  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
