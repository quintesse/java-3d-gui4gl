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
import java.util.ArrayList;
import java.util.List;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.Renderable;
import org.codejive.utils4gl.Texture;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiKeyListener;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.events.GuiMouseListener;
import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.*;

/**
 * @author tako
 * @version $Revision: 205 $
 */
public class Widget implements Renderable {
	private CompoundWidget m_parent;
	private String m_sName;
	private Rectangle m_bounds;
	private GLColor m_backgroundColor;
	private float m_fTransparancy;
	private Texture m_backgroundImage;
	private int m_nXPadding, m_nYPadding;
	private Font m_textFont;
	private GLColor m_textFontColor;
	private int m_nTextAlignment;	
	private GLColor m_focusedBackgroundColor;
	private float m_fFocusedTransparancy;
	private Texture m_focusedBackgroundImage;
	private int m_nFocusedXPadding, m_nFocusedYPadding;
	private Font m_focusedTextFont;
	private GLColor m_focusedTextFontColor;
	private GLColor m_disabledBackgroundColor;
	private float m_fDisabledTransparancy;
	private Texture m_disabledBackgroundImage;
	private int m_nDisabledXPadding, m_nDisabledYPadding;
	private Font m_disabledTextFont;
	private GLColor m_disabledTextFontColor;
	private boolean m_bEnabled;
	private boolean m_bVisible;
	private boolean m_bCanHaveFocus;

	private List m_keyListeners;
	private List m_mouseListeners;

	public class Padding {
		public int xPadding, yPadding;
		public Padding() {
			xPadding = yPadding = 0;
		}
		public Padding(int _nXPadding, int _nYPadding) {
			xPadding = _nXPadding;
			yPadding = _nYPadding;
		}
	}
	
	private Rectangle m_currentBounds;
	private Rectangle m_innerBounds;
	protected Padding m_padding;
	
	public Widget() {
		this(null);
	}
	
	public Widget(String _sName) {
		m_parent = null;
		m_sName = _sName;
		m_bounds = new Rectangle();
		m_backgroundColor = (GLColor)Theme.getValue(getClass(), getFullName(), "backgroundColor");
		m_fTransparancy = Theme.getFloatValue(getClass(), getFullName(), "transparancy");
		m_backgroundImage = (Texture)Theme.getValue(getClass(), getFullName(), "backgroundImage");
		m_nXPadding = Theme.getIntegerValue(getClass(), getFullName(), "xPadding");
		m_nYPadding = Theme.getIntegerValue(getClass(), getFullName(), "yPadding");
		m_textFont = (Font)Theme.getValue(getClass(), getFullName(), "textFont");
		m_textFontColor = (GLColor)Theme.getValue(getClass(), getFullName(), "textFontColor");
		m_nTextAlignment = Theme.getIntegerValue(getClass(), getFullName(), "textAlignment");
		m_focusedBackgroundColor = (GLColor)Theme.getValue(getClass(), getFullName(), "backgroundColor#focused");
		m_fFocusedTransparancy = Theme.getFloatValue(getClass(), getFullName(), "transparancy#focused");
		m_focusedBackgroundImage = (Texture)Theme.getValue(getClass(), getFullName(), "backgroundImage#focused");
		m_nFocusedXPadding = Theme.getIntegerValue(getClass(), getFullName(), "xPadding#focused");
		m_nFocusedYPadding = Theme.getIntegerValue(getClass(), getFullName(), "yPadding#focused");
		m_focusedTextFont = (Font)Theme.getValue(getClass(), getFullName(), "textFont#focused");
		m_focusedTextFontColor = (GLColor)Theme.getValue(getClass(), getFullName(), "textFontColor#focused");
		m_disabledBackgroundColor = (GLColor)Theme.getValue(getClass(), getFullName(), "backgroundColor#disabled");
		m_fDisabledTransparancy = Theme.getFloatValue(getClass(), getFullName(), "transparancy#disabled");
		m_disabledBackgroundImage = (Texture)Theme.getValue(getClass(), getFullName(), "backgroundImage#disabled");
		m_nDisabledXPadding = Theme.getIntegerValue(getClass(), getFullName(), "xPadding#disabled");
		m_nDisabledYPadding = Theme.getIntegerValue(getClass(), getFullName(), "yPadding#disabled");
		m_disabledTextFont = (Font)Theme.getValue(getClass(), getFullName(), "textFont#disabled");
		m_disabledTextFontColor = (GLColor)Theme.getValue(getClass(), getFullName(), "textFontColor#disabled");
		m_bEnabled = true;
		m_bVisible = true;
		m_bCanHaveFocus = false;
		
		m_keyListeners = new ArrayList();
		m_mouseListeners = new ArrayList();

		m_currentBounds = new Rectangle();
		m_innerBounds = new Rectangle();
		m_padding = new Padding();
	}
	
	public String getName() {
		return m_sName;
	}
	
	public String getFullName() {
		String sName;
		if (getParent() != null) {
			String sFullParentName = getParent().getFullName();
			if (sFullParentName.length() > 0) {
				sName = sFullParentName + "." + getName();
			} else {
				sName = getName();
			}
		} else {
			sName = getName();
		}
		return (sName != null) ? sName : "";
	}
	
	protected void setParent(CompoundWidget _parent) {
		m_parent = _parent;
	}
	
	public CompoundWidget getParent() {
		return m_parent;
	}
	
	public Toplevel getToplevel() {
		return getParent().getToplevel();
	}
	
	public Screen getScreen() {
		Screen screen;
		CompoundWidget parent = getParent();
		if (parent != null) {
			screen = parent.getScreen();
		} else {
			if (this instanceof Screen) {
				screen = (Screen)this;
			} else {
				throw new RuntimeException("Widget not part of a Screen's widget tree");
			}
		}
		return screen;
	}
	
	public boolean isFocusable() {
		return m_bCanHaveFocus && m_bEnabled && m_bVisible && ((getParent() == null) || getParent().isFocusable());
	}
	
	public void setFocusable(boolean _bCanHaveFocus) {
		m_bCanHaveFocus = _bCanHaveFocus;
	}
	
	public boolean hasFocus() {
		return (this == getScreen().getFocusWidget());
	}
	
	public void setFocus() {
		if (isFocusable()) {
			getParent().setFocusWidget(this);
		}
	}
	
	public Widget nextFocus() {
		return getParent().getNextFocusWidget(this);
	}
	
	public Widget previousFocus() {
		return getParent().getPreviousFocusWidget(this);
	}
	
	protected Widget getWidgetUnderPoint(int _nXPos, int _nYPos) {
		Widget result;
		if (isEnabled() && isVisible() && getCurrentBounds().contains(_nXPos, _nYPos)) {
			result = this;
		} else {
			result = null;
		}
		return result;
	}
	
	public int getLeft() {
		return m_bounds.x;
	}
	
	public void setLeft(int _nPos) {
		m_bounds.x = _nPos;
	}
	
	public int getTop() {
		return m_bounds.y;
	}
	
	public void setTop(int _nPos) {
		m_bounds.y = _nPos;
	}
	
	public int getWidth() {
		return m_bounds.width;
	}
	
	public void setWidth(int _nPos) {
		m_bounds.width = _nPos;
	}
	
	public int getHeight() {
		return m_bounds.height;
	}
	
	public void setHeight(int _nPos) {
		m_bounds.height = _nPos;
	}
	
	public Rectangle getBounds() {
		return m_bounds;
	}
	
	public void setBounds(Rectangle _rect) {
		m_bounds.setRect(_rect);
	}
	
	public void setBounds(int _nLeft, int _nTop, int _nWidth, int _nHeight) {
		m_bounds.setBounds(_nLeft, _nTop, _nWidth, _nHeight);
	}
	
	public GLColor getBackgroundColor() {
		return m_backgroundColor;
	}
	
	public void setBackgroundColor(float _fRed, float _fGreen, float _fBlue) {
		m_backgroundColor.set(_fRed, _fGreen, _fBlue);
	}
	
	public void setBackgroundColor(GLColor _color) {
		m_backgroundColor.set(_color);
	}
	
	public float getTransparancy() {
		return m_fTransparancy;
	}
	
	public void setTransparancy(float _fTransparancy) {
		m_fTransparancy = _fTransparancy;
	}
	
	public Texture getBackgroundImage() {
		return m_backgroundImage;
	}
	
	public void setBackgroundImage(Texture _image) {
		m_backgroundImage = _image;
	}
	
	public int getXPadding() {
		return m_nXPadding;
	}
	
	public void setXPadding(int _nPadding) {
		m_nXPadding = _nPadding;
	}
	
	public int getYPadding() {
		return m_nYPadding;
	}
	
	public void setYPadding(int _nPadding) {
		m_nYPadding = _nPadding;
	}
	
	public Font getTextFont() {
		return m_textFont;
	}
	
	public void setTextFont(Font _font) {
		m_textFont = _font;
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
	
	public GLColor getFocusedBackgroundColor() {
		return m_focusedBackgroundColor;
	}
	
	public void setFocusedBackgroundColor(float _fRed, float _fGreen, float _fBlue) {
		m_focusedBackgroundColor.set(_fRed, _fGreen, _fBlue);
	}
	
	public void setFocusedBackgroundColor(GLColor _color) {
		m_focusedBackgroundColor.set(_color);
	}
	
	public float getFocusedTransparancy() {
		return m_fFocusedTransparancy;
	}
	
	public void setFocusedTransparancy(float _fTransparancy) {
		m_fFocusedTransparancy = _fTransparancy;
	}
	
	public Texture getFocusedBackgroundImage() {
		return m_focusedBackgroundImage;
	}
	
	public void setFocusedBackgroundImage(Texture _image) {
		m_focusedBackgroundImage = _image;
	}
	
	public int getFocusedXPadding() {
		return m_nFocusedXPadding;
	}
	
	public void setFocusedXPadding(int _nPadding) {
		m_nFocusedXPadding = _nPadding;
	}
	
	public int getFocusedYPadding() {
		return m_nFocusedYPadding;
	}
	
	public void setFocusedYPadding(int _nPadding) {
		m_nFocusedYPadding = _nPadding;
	}

	public Font getFocusedTextFont() {
		return m_focusedTextFont;
	}
	
	public void setFocusedTextFont(Font _font) {
		m_focusedTextFont = _font;
	}

	public GLColor getFocusedTextFontColor() {
		return m_focusedTextFontColor;
	}
	
	public void setFocusedTextFontColor(GLColor _color) {
		m_focusedTextFontColor = _color;
	}
	
	public GLColor getDisabledBackgroundColor() {
		return m_disabledBackgroundColor;
	}
	
	public void setDisabledBackgroundColor(float _fRed, float _fGreen, float _fBlue) {
		m_disabledBackgroundColor.set(_fRed, _fGreen, _fBlue);
	}
	
	public void setDisabledBackgroundColor(GLColor _color) {
		m_disabledBackgroundColor.set(_color);
	}
	
	public float getDisabledTransparancy() {
		return m_fDisabledTransparancy;
	}
	
	public void setDisabledTransparancy(float _fTransparancy) {
		m_fDisabledTransparancy = _fTransparancy;
	}
	
	public Texture getDisabledBackgroundImage() {
		return m_disabledBackgroundImage;
	}
	
	public void setDisabledBackgroundImage(Texture _image) {
		m_disabledBackgroundImage = _image;
	}
	
	public int getDisabledXPadding() {
		return m_nDisabledXPadding;
	}
	
	public void setDisabledXPadding(int _nPadding) {
		m_nDisabledXPadding = _nPadding;
	}
	
	public int getDisabledYPadding() {
		return m_nDisabledYPadding;
	}
	
	public void setDisabledYPadding(int _nPadding) {
		m_nDisabledYPadding = _nPadding;
	}

	public Font getDisabledTextFont() {
		return m_disabledTextFont;
	}
	
	public void setDisabledTextFont(Font _font) {
		m_disabledTextFont = _font;
	}

	public GLColor getDisabledTextFontColor() {
		return m_disabledTextFontColor;
	}
	
	public void setDisabledTextFontColor(GLColor _color) {
		m_disabledTextFontColor = _color;
	}
	
	public boolean isEnabled() {
		return m_bEnabled && ((getParent() == null) || getParent().isEnabled());
	}
	
	public void setEnabled(boolean _bEnabled) {
		m_bEnabled = _bEnabled;
	}
	
	public boolean isVisible() {
		return m_bVisible && ((getParent() == null) || getParent().isVisible());
	}
	
	public void setVisible(boolean _bVisible) {
		m_bVisible = _bVisible;
	}
	
	public void moveToFront() {
		getParent().moveChildToFront(this);
	}
	
	public void moveToBack() {
		getParent().moveChildToBack(this);
	}
	
	public Rectangle getCurrentBounds() {
		return m_currentBounds;
	}
	
	public Rectangle getInnerBounds() {
		return m_innerBounds;
	}

	protected void calculateBounds() {
		Rectangle r = getBounds();
		if (m_parent != null) {
			m_currentBounds.setBounds(m_parent.getInnerBounds());
			updateBounds();
			int x, y;
			if (r.x >= 0) {
				x = m_currentBounds.x + r.x;
			} else {
				x = m_currentBounds.x + m_currentBounds.width + r.x;
			}
			if (r.y >= 0) {
				y = m_currentBounds.y + r.y;
			} else {
				y = m_currentBounds.y + m_currentBounds.height + r.y;
			}
			m_currentBounds.setBounds(x, y, r.width, r.height);
		} else {
			updateBounds();
			m_currentBounds.setBounds(r);
		}
		
		// Calculate inner bounds
		m_innerBounds.setBounds(m_currentBounds);
		updateInnerBounds();
	}
	
	protected void updateBounds() {
		// Override in subclass if needed
	}
	
	protected void updateInnerBounds() {
		// Substract the padding from the inner bounds
		int nXPad, nYPad;
		if (hasFocus()) {
			nXPad = getFocusedXPadding();
			nYPad = getFocusedYPadding();
		} else {
			nXPad = getXPadding();
			nYPad = getYPadding();
		}
		m_innerBounds.x += nXPad;
		m_innerBounds.y += nYPad;
		m_innerBounds.width -= 2 * nXPad;
		m_innerBounds.height -= 2 * nYPad;
	}
	
	public void addKeyListener(GuiKeyListener _listener) {
		m_keyListeners.add(_listener);
	}
	
	public void addMouseListener(GuiMouseListener _listener) {
		m_mouseListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		GuiKeyEvent.fireKeyPressed(m_keyListeners, _event);
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyPressedEvent(_event);
		}
	}
		
	protected void processKeyReleasedEvent(GuiKeyEvent _event) {
		GuiKeyEvent.fireKeyReleased(m_keyListeners, _event);
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyReleasedEvent(_event);
		}
	}
		
	protected void processKeyTypedEvent(GuiKeyEvent _event) {
		GuiKeyEvent.fireKeyTyped(m_keyListeners, _event);
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyTypedEvent(_event);
		}
	}
	
	protected void processMousePressedEvent(GuiMouseEvent _event) {
		setFocus();
		GuiMouseEvent.fireMousePressed(m_mouseListeners, _event);
	}
	
	protected void processMouseReleasedEvent(GuiMouseEvent _event) {
		GuiMouseEvent.fireMouseReleased(m_mouseListeners, _event);
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		GuiMouseEvent.fireMouseClicked(m_mouseListeners, _event);
	}
	
	protected void processMouseMovedEvent(GuiMouseEvent _event) {
		GuiMouseEvent.fireMouseMoved(m_mouseListeners, _event);
	}
	
	protected void processMouseDraggedEvent(GuiMouseEvent _event) {
		GuiMouseEvent.fireMouseDragged(m_mouseListeners, _event);
	}
	
	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#readyForRendering()
	 */
	public boolean readyForRendering() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	public void initRendering(RenderContext _context) {
		initWidget(_context);
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	public void updateRendering(RenderContext _context) {
		updateWidget(_context);
	}
	
	protected void initWidget(RenderContext _context) {
		calculateBounds();
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(getClass(), getFullName(), "renderer");
		if (renderer != null) {
			renderer.initRendering(this, _context);
		}
	}
	
	protected void updateWidget(RenderContext _context) {
		calculateBounds();
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(getClass(), getFullName(), "renderer");
		if (renderer != null) {
			renderer.updateRendering(this, _context);
		}
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#render(org.codejive.world3d.RenderContext)
	 */
	public void render(RenderContext _context) {
		if (isVisible()) {
			calculateBounds();
			renderWidget(_context);
		}
	}
	
	protected void renderWidget(RenderContext _context) {
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(getClass(), getFullName(), "renderer");
		if (renderer != null) {
			renderer.render(this, _context);
		}
	}
}

/*
 * $Log$
 * Revision 1.18  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.16  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.15  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.14  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.13  2003/11/25 00:39:21  tako
 * Prevented mouse events from bubbling up the hierarchy. Not sure what
 * the proper way of handling this should be though.
 *
 * Revision 1.12  2003/11/25 00:29:39  tako
 * Removed visibility check for ini- and updatetRendering() because they
 * messed up the GUI layout for Windows that were invisible when the
 * GUI got initialized.
 *
 * Revision 1.11  2003/11/24 17:23:37  tako
 * Added moveToBack() and moveToFront().
 * Renamed get/setRectangle() to get/setBounds() and renamed getBounds()
 * to getCurrentBounds().
 * isFocusable() and isVisible() now both check their ancesters as well.
 * Added processMouseMoved/DraggedEvent().
 * Implemented updateRendering().
 *
 * Revision 1.10  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.9  2003/11/21 01:31:05  tako
 * Added getToplevel().
 *
 * Revision 1.8  2003/11/20 00:36:07  tako
 * Code change because of change from getPadding() to
 * updateInnerBounds(). This makes it possible for widgets to adjust the
 * amount of inner space to make available for their "content", which is
 * much more flexible than the previous padding system.
 *
 * Revision 1.7  2003/11/19 11:19:41  tako
 * Implemented completely new event system because tryin to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.6  2003/11/19 00:18:44  tako
 * Added support for seperate X and Y padding.
 * Made several methods protected instead of public.
 *
 * Revision 1.5  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
