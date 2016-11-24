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
 * Created on Oct 23, 2004
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;

import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiKeyListener;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.events.GuiMouseListener;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.utils4gl.Renderable;

/**
 * @author tako
 * @version $Revision: 314 $
 */
public interface Widget extends Renderable {
	public abstract String getName();

	public abstract void setName(String _sName);

	public abstract void setParent(CompoundWidget _parent);
	
	public abstract CompoundWidget getParent();

	public abstract Toplevel getToplevel();

	public abstract Screen getScreen();

	public abstract boolean isFocusable();

	public abstract void setFocusable(boolean _bCanHaveFocus);

	public abstract boolean hasFocus();

	public abstract void setFocus();

	public abstract Widget nextFocus();

	public abstract Widget previousFocus();

	public abstract Widget getWidgetUnderPoint(int _nXPos, int _nYPos);

	public abstract boolean isEnabled();

	public abstract void setEnabled(boolean _bEnabled);

	public abstract boolean isVisible();

	public abstract void setVisible(boolean _bVisible);

	public abstract void moveToFront();

	public abstract void moveToBack();

	public abstract int getLeft();

	public abstract void setLeft(int _nPos);

	public abstract int getTop();

	public abstract void setTop(int _nPos);

	public abstract int getWidth();

	public abstract void setWidth(int _nPos);

	public abstract int getHeight();

	public abstract void setHeight(int _nPos);

	public abstract Rectangle getBounds();

	public abstract void setBounds(Rectangle _rect);

	public abstract void setBounds(int _nLeft, int _nTop, int _nWidth, int _nHeight);

	public abstract Rectangle getCurrentBounds();

	public abstract Rectangle getInnerBounds();

	public abstract Rectangle getClippingBounds();

	public abstract Object getAttribute(String _sName);

	public abstract void setAttribute(String _sName, Object _value);

	public abstract int getIntegerAttribute(String _sName);

	public abstract void setIntegerAttribute(String _sName, int _nValue);

	public abstract float getFloatAttribute(String _sName);

	public abstract void setFloatAttribute(String _sName, float _fValue);

	public abstract boolean getBooleanAttribute(String _sName);

	public abstract void setBooleanAttribute(String _sName, boolean _bValue);

	public abstract void addKeyListener(GuiKeyListener _listener);

	public abstract void addMouseListener(GuiMouseListener _listener);

	public abstract void processKeyPressedEvent(GuiKeyEvent _event);
		
	public abstract void processKeyReleasedEvent(GuiKeyEvent _event);
		
	public abstract void processKeyTypedEvent(GuiKeyEvent _event);
	
	public abstract void processMousePressedEvent(GuiMouseEvent _event);
	
	public abstract void processMouseReleasedEvent(GuiMouseEvent _event);
	
	public abstract void processMouseClickedEvent(GuiMouseEvent _event);
	
	public abstract void processMouseMovedEvent(GuiMouseEvent _event);
	
	public abstract void processMouseDraggedEvent(GuiMouseEvent _event);

	public abstract WidgetRendererModel getRenderer();

	public abstract void setRenderer(WidgetRendererModel _renderer);
}

/*
 * $Log:	$
 */
