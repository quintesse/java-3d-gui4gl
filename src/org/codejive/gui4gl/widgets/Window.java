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
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;

import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 205 $
 */
public class Window extends Toplevel {
	private String m_sTitle;
	private int m_nTitlebarHeight;
	private GLColor m_titlebarColor;
	private float m_fTitlebarTransparancy;
	private int m_nCaptionXPadding;
	private int m_nCaptionYPadding;
	private GLColor m_activeTitlebarColor;
	private float m_fActiveTitlebarTransparancy;
	private int m_nActiveCaptionXPadding;
	private int m_nActiveCaptionYPadding;
	private GLColor m_disabledTitlebarColor;
	private float m_fDisabledTitlebarTransparancy;
	private int m_nDisabledCaptionXPadding;
	private int m_nDisabledCaptionYPadding;
	private boolean m_bCenterParent;
	private boolean m_bDragging;
	
	public Window() {
		this(null, null);
	}

	public Window(String _sTitle) {
		this(null, _sTitle);
	}
	
	public Window(String _sName, String _sTitle) {
		super(_sName);
		m_sTitle = _sTitle;
		m_nTitlebarHeight = Theme.getIntegerValue(getClass(), getFullName(), "titlebarHeight");
		m_titlebarColor = (GLColor)Theme.getValue(getClass(), getFullName(), "titlebarColor");
		m_fTitlebarTransparancy = Theme.getFloatValue(getClass(), getFullName(), "titlebarTransparancy");
		m_nCaptionXPadding = Theme.getIntegerValue(getClass(), getFullName(), "captionXPadding");
		m_nCaptionYPadding = Theme.getIntegerValue(getClass(), getFullName(), "captionYPadding");
		m_activeTitlebarColor = (GLColor)Theme.getValue(getClass(), getFullName(), "titlebarColor#active");
		m_fActiveTitlebarTransparancy = Theme.getFloatValue(getClass(), getFullName(), "titlebarTransparancy#active");
		m_nActiveCaptionXPadding = Theme.getIntegerValue(getClass(), getFullName(), "captionXPadding#active");
		m_nActiveCaptionYPadding = Theme.getIntegerValue(getClass(), getFullName(), "captionYPadding#active");
		m_disabledTitlebarColor = (GLColor)Theme.getValue(getClass(), getFullName(), "titlebarColor#disabled");
		m_fDisabledTitlebarTransparancy = Theme.getFloatValue(getClass(), getFullName(), "titlebarTransparancy#disabled");
		m_nDisabledCaptionXPadding = Theme.getIntegerValue(getClass(), getFullName(), "captionXPadding#disabled");
		m_nDisabledCaptionYPadding = Theme.getIntegerValue(getClass(), getFullName(), "captionYPadding#disabled");
		m_bCenterParent = false;
		setVisible(false);
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
	
	public GLColor getDisabledTitlebarColor() {
		return m_disabledTitlebarColor;
	}
	
	public void setDisabledTitlebarColor(GLColor _color) {
		m_disabledTitlebarColor = _color;
	}
	
	public float getDisabledTitlebarTransparancy() {
		return m_fDisabledTitlebarTransparancy;
	}
	
	public void setDisabledTitlebarTransparancy(float _fTransparancy) {
		m_fDisabledTitlebarTransparancy = _fTransparancy;
	}
	
	public int getDisabledCaptionXPadding() {
		return m_nDisabledCaptionXPadding;
	}
	
	public void setDisabledCaptionXPadding(int _nPadding) {
		m_nDisabledCaptionXPadding = _nPadding;
	}
	
	public int getDisabledCaptionYPadding() {
		return m_nDisabledCaptionYPadding;
	}
	
	public void setDisabledCaptionYPadding(int _nPadding) {
		m_nDisabledCaptionYPadding = _nPadding;
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
	
	protected void processMousePressedEvent(GuiMouseEvent _event) {
		super.processMousePressedEvent(_event);
		if (!_event.isConsumed()) {
			Rectangle r = new Rectangle();
			r.setBounds(getCurrentBounds());
			r.height = getTitlebarHeight();
			if (isDraggable() && r.contains(_event.getX(), _event.getY())) {
				m_bDragging = true;
			}
		}
	}

	protected void processMouseReleasedEvent(GuiMouseEvent _event) {
		super.processMouseReleasedEvent(_event);
		m_bDragging = false;
	}

	protected void processMouseDraggedEvent(GuiMouseEvent _event) {
		super.processMouseDraggedEvent(_event);
		if (!_event.isConsumed()) {
			if (m_bDragging) {
				Rectangle rect = getBounds();
				int x = rect.x + _event.getDeltaX();
				int y = rect.y + _event.getDeltaY();
				setBounds(x, y, rect.width, rect.height);
			}
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

	protected void initWidget(RenderContext _context) {
		super.initWidget(_context);
		if (isCenterParent()) {
			centerParent();
		}
	}
	
	protected void centerParent() {
		Rectangle inner = getParent().getInnerBounds();
		Rectangle rect = getBounds();
		int x = (inner.width - rect.width) / 2;
		int y = (inner.height - rect.height) / 2;
		setBounds(x, y, rect.width, rect.height);
	}
}

/*
 * $Log$
 * Revision 1.13  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.11  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.10  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.9  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.8  2003/11/24 17:24:14  tako
 * Implemented window dragging.
 *
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
