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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;
import org.codejive.utils4gl.Renderable;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiKeyListener;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.events.GuiMouseListener;
import org.codejive.gui4gl.themes.*;

/**
 * @author tako
 * @version $Revision: 263 $
 */
public class Widget implements Renderable {
	private Screen m_screen;
	private Toplevel m_toplevel;
	private CompoundWidget m_parent;
	private String m_sName;
	private Rectangle m_bounds;
	private boolean m_bEnabled;
	private boolean m_bVisible;
	private boolean m_bCanHaveFocus;

	private Map m_attributes;
	
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
	private Rectangle m_clippingBounds;
	protected Padding m_padding;
	
	public Widget() {
		m_screen = null;
		m_toplevel = null;
		m_parent = null;
		m_sName = null;
		m_bounds = new Rectangle();
		m_bEnabled = true;
		m_bVisible = true;
		m_bCanHaveFocus = false;
		
		m_attributes = new HashMap();
		
		m_keyListeners = new ArrayList();
		m_mouseListeners = new ArrayList();

		m_currentBounds = new Rectangle();
		m_innerBounds = new Rectangle();
		m_clippingBounds = new Rectangle();
		m_padding = new Padding();
	}
	
	public String getName() {
		return m_sName;
	}
	
	public void setName(String _sName) {
		if (getParent() != null) {
			throw new RuntimeException("Widget name can't be changed anymore after it has been added to a CompoundWidget");
		}
		m_sName = _sName;
	}
	
	protected void setParent(CompoundWidget _parent) {
		m_parent = _parent;
		m_toplevel = _parent.getToplevel();
		m_screen = _parent.getScreen();
	}
	
	public CompoundWidget getParent() {
		return m_parent;
	}
	
	public Toplevel getToplevel() {
		if ((m_toplevel == null) && (getParent() != null)) {
			m_toplevel = getParent().getToplevel();
		}
		return m_toplevel;
	}
	
	public Screen getScreen() {
		if (m_screen == null && (getParent() != null)) {
			m_screen = getParent().getScreen();
		}
		return m_screen;
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

	public Rectangle getClippingBounds() {
		return m_clippingBounds;
	}

	protected void calculateBounds(RenderContext _context) {
		Rectangle r = getBounds();
		if (m_parent != null) {
			m_currentBounds.setBounds(m_parent.getInnerBounds());
			updateBounds(_context);
			m_currentBounds.setBounds(m_currentBounds.x + r.x, m_currentBounds.y + r.y, r.width, r.height);
		} else {
			updateBounds(_context);
			m_currentBounds.setBounds(r);
		}
		
		// Calculate inner bounds
		m_innerBounds.setBounds(m_currentBounds);
		updateInnerBounds(_context);
	}
	
	protected void updateBounds(RenderContext _context) {
		// Override in subclass if needed
	}
	
	protected void updateInnerBounds(RenderContext _context) {
		// Substract the padding from the inner bounds
		int nXPad, nYPad;
		if (hasFocus()) {
// FIXME: should handle padding!!
			nXPad = getIntegerAttribute("xPadding#focused");
			nYPad = getIntegerAttribute("yPadding#focused");
		} else {
			nXPad = getIntegerAttribute("xPadding");
			nYPad = getIntegerAttribute("yPadding");
		}
		m_innerBounds.x += nXPad;
		m_innerBounds.y += nYPad;
		m_innerBounds.width -= 2 * nXPad;
		m_innerBounds.height -= 2 * nYPad;
		
		if (getParent() != null) {
			Rectangle.intersect(m_innerBounds, getParent().getClippingBounds(), m_clippingBounds);
		} else {
			m_clippingBounds.setBounds(m_innerBounds);
		}
	}
	
	public Object getAttribute(String _sName) {
		Object value;
		if (!m_attributes.containsKey(_sName)) {
			value = Theme.getValue(this, _sName);
			m_attributes.put(_sName, value);
		} else {
			value = m_attributes.get(_sName);
		}
		return value;
	}
	
	public void setAttribute(String _sName, Object _value) {
		m_attributes.put(_sName, _value);
	}
	
	public int getIntegerAttribute(String _sName) {
		Integer value = (Integer)getAttribute(_sName);
		return value.intValue();
	}
	
	public void setIntegerAttribute(String _sName, int _nValue) {
		setAttribute(_sName, new Integer(_nValue));
	}
	
	public float getFloatAttribute(String _sName) {
		Float value = (Float)getAttribute(_sName);
		return value.floatValue();
	}
	
	public void setFloatAttribute(String _sName, float _fValue) {
		setAttribute(_sName, new Float(_fValue));
	}
	
	public boolean getBooleanAttribute(String _sName) {
		Boolean value = (Boolean)getAttribute(_sName);
		return value.booleanValue();
	}
	
	public void setBooleanAttribute(String _sName, boolean _bValue) {
		setAttribute(_sName, new Boolean(_bValue));
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

	protected void initWidget(RenderContext _context) {
		calculateBounds(_context);
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(this, "renderer");
		if (renderer != null) {
			renderer.initRendering(this, _context);
		}
	}
	
	public void render(RenderContext _context, RenderObserver _observer) {
		if (isVisible()) {
			calculateBounds(_context);
			Widget parent = getParent();
			// Make sure at least part of the widget is visible within the parent's clipping bounds
			if ((parent == null) || parent.getClippingBounds().intersects(getCurrentBounds())) {
				if (parent != null) {
					Rectangle rect = new Rectangle(parent.getClippingBounds());
					rect.y = getScreen().getHeight() - rect.y - rect.height;
					_context.pushClippingRegion(rect);
				}

				renderWidget(_context, _observer);

				if (parent != null) {
					_context.popClippingRegion();
				}
			}
		}
	}
	
	protected void renderWidget(RenderContext _context, RenderObserver _observer) {
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(this, "renderer");
		if (renderer != null) {
			renderer.render(this, _context);
		}
	}
}

/*
 * $Log$
 * Revision 1.23  2004/10/17 11:06:22  tako
 * Removed updateRendering() and updateWidget().
 *
 * Revision 1.22  2004/05/04 23:56:41  tako
 * Removed feature where negative x/y positions would be offsets from the opposite side.
 * Negative positions are not specail cases anymore and will move the widget outside the parent boundaries.
 *
 * Revision 1.21  2004/05/04 22:26:26  tako
 * Removed all visually oriented attribute getters and setters and implemented a dynamic attribute map.
 * Removed the widget name from the constructor, if you want a named widget just set it explicitly with setName().
 * Removed now unnecessary getFullName().
 * Tried to make getScreen() and getToplevel() more efficient.
 * Added getClippingBounds().
 * Added methods for getting and setting attribute values in the attribute map.
 * Clipping is now enabled before a widget is rendered to make sure it doesn't draw outside its bounds.
 * renderWidget() now also needs a RenderObserver argument.
 *
 * Revision 1.20  2004/03/17 00:50:46  tako
 * Colors are now cloned during initialization to prevent others from messing
 * up the Themes.
 *
 * Revision 1.19  2004/03/07 18:22:58  tako
 * Bounds calculations and render functions now all have a RenderContext argument.
 * The methods getFullName() now won't return illegal names anymore if the
 * widget has no name.
 *
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
