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
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 222 $
 */
public class Button extends Widget {
	private String m_sCaption;
	private Font m_selectedTextFont;
	private GLColor m_selectedTextFontColor;
	private int m_nSelectedXPadding;
	private int m_nSelectedYPadding;
	private GLColor m_selectedBackgroundColor;
	private float m_fSelectedTransparancy;
	
	private List m_actionListeners;
	private boolean m_bSelected;
	
	public Button() {
		this(null, null);
	}

	public Button(String _sCaption) {
		this(null, _sCaption);
	}
	
	public Button(String _sName, String _sCaption) {
		super(_sName);
		m_sCaption = _sCaption;
		m_selectedTextFont = (Font)Theme.getValue(getClass(), getFullName(), "textFont#selected");
		m_selectedTextFontColor = (GLColor)Theme.getValue(getClass(), getFullName(), "textFontColor#selected");
		m_nSelectedXPadding = Theme.getIntegerValue(getClass(), getFullName(), "xPadding#selected");
		m_nSelectedYPadding = Theme.getIntegerValue(getClass(), getFullName(), "yPadding#selected");
		m_selectedBackgroundColor = (GLColor)Theme.getValue(getClass(), getFullName(), "backgroundColor#selected");
		m_fSelectedTransparancy = Theme.getFloatValue(getClass(), getFullName(), "transparancy#selected");
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
	
	public Font getSelectedTextFont() {
		return m_selectedTextFont;
	}
	
	public void setSelectedTextFont(Font _font) {
		m_selectedTextFont = _font;
	}
	
	public GLColor getSelectedTextFontColor() {
		return m_selectedTextFontColor;
	}
	
	public void setSelectedTextFontColor(GLColor _color) {
		m_selectedTextFontColor = _color;
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
	
	protected void updateInnerBounds(RenderContext _context) {
		if (isSelected()) {
			int nXPad = getSelectedXPadding();
			int nYPad = getSelectedYPadding();
			Rectangle bounds = getInnerBounds();
			bounds.x += nXPad;
			bounds.y += nYPad;
			bounds.width -= 2 * nXPad;
			bounds.height -= 2 * nYPad;
		} else {
			super.updateInnerBounds(_context);
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
 * Revision 1.12  2004/03/07 18:21:29  tako
 * Bounds calculations and render functions now all have a RenderContext argument.
 *
 * Revision 1.11  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.9  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.8  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.7  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
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
