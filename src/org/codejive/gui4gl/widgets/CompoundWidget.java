/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003, 2004 Tako Schotanus
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
 * Created on Dec 14, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.java.games.jogl.GL;

import org.codejive.gui4gl.layouts.Layouter;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;

/**
 * @author tako
 * @version $Revision: 241 $
 */
public class CompoundWidget extends Widget {
	protected LinkedList m_children;
	protected Map m_childNames;
	protected Widget m_focusWidget;
	protected Layouter m_layouter;

	public CompoundWidget() {
		m_children = new LinkedList();
		m_childNames = new HashMap();
		m_focusWidget = null;
		m_layouter = null;
		setFocusable(true);
	}

	/**
	 * Returns the current layouter for this widget.
	 * @return A reference to the current Layouter.
	 */
	public Layouter getLayouter() {
		return m_layouter;
	}
	
	/**
	 * Sets a new layouter for this widget.
	 * Setting this to null will revert to using the child widget's absolute bounds.
	 * @param _layouter
	 */
	public void setLayouter(Layouter _layouter) {
		m_layouter = _layouter;
	}
	
	protected void add(Widget _child) {
		m_children.add(_child);
		if (_child.getName() != null) {
			m_childNames.put(_child.getName(), _child);
		}
		_child.setParent(this);
	}

	protected void remove(Widget _child) {
		m_children.remove(_child);
		m_childNames.remove(_child);
		_child.setParent(null);
	}
	
	protected void remove(String _sChildName) {
		Widget child = getChild(_sChildName);
		remove(child);
	}
	
	protected Widget getChild(String _sName) {
		return (Widget)m_childNames.get(_sName);
	}

	protected Iterator getChildren() {
		List x = new LinkedList(m_children);
		return x.iterator();
	}

	protected Widget findChild(String _sName) {
		Widget found = null;
		// First see if the current container contains a widget with the given name
		Widget child = getChild(_sName);
		if (child == null) {
			Iterator i = getChildren();
			while ((found == null) && i.hasNext()) {
				child = (Widget)i.next();
				if (child instanceof Container) {
					found = ((Container)child).findChild(_sName);
				}
			}
		} else {
			found = child;
		}
		// Now see if any of our children is a container and has 
		return found;
	}

	protected Widget getWidgetUnderPoint(int _nXPos, int _nYPos) {
		Widget result = null;
		if (isVisible()) {
			if (getClippingBounds().contains(_nXPos, _nYPos)) {
				Iterator i = getChildren();
				while (i.hasNext()) {
					Widget w = (Widget)i.next();
					Widget under = w.getWidgetUnderPoint(_nXPos, _nYPos);
					if (under != null) {
						result = under;
					}
				}
			}
			if (result == null) {
				result = super.getWidgetUnderPoint(_nXPos, _nYPos);
			}
		}
		return result;
	}

	protected void moveChildToFront(Widget _child) {
		m_children.remove(_child);
		m_children.addLast(_child);
	}

	protected void moveChildToBack(Widget _child) {
		m_children.remove(_child);
		m_children.addFirst(_child);
	}

	protected Widget getFocusWidget() {
		return m_focusWidget;
	}
	
	protected void setFocusWidget(Widget _widget) {
		m_focusWidget = _widget;
		if (getParent() != null) {
			getParent().setFocusWidget(_widget);
		}
	}

	protected Widget getPreviousFocusWidget(Widget _widget) {
		Widget prevWidget = null;
		int p;
		if (_widget != null) {
			p = m_children.indexOf(_widget);
		} else {
			p = m_children.size();
		}
		if (p >= 0) {
			ListIterator i = m_children.listIterator(p);
			while (i.hasPrevious() && (prevWidget == null)) {
				Widget w = (Widget)i.previous();
				if (w.isFocusable()) {
					prevWidget = w;
					if (w instanceof CompoundWidget) {
						CompoundWidget cw = (CompoundWidget)w;
						w = cw.getPreviousFocusWidget(null);
						if (w != null) {
							prevWidget = w;
						}
					}
				}
			}
			if (prevWidget == null) {
				// No focusable widget was found, let's ask our parent
				prevWidget = getParent().getPreviousFocusWidget(this);
			}
			if ((prevWidget == null) && (_widget != null)) {
				// No focusable widget was found, let's try again from the end of the list
				prevWidget = getPreviousFocusWidget(null);
			}
		}
		if (prevWidget == null) {
			// No previous widget could be determined, return current value
			prevWidget = m_focusWidget;
		}
		return prevWidget;
	}

	protected Widget getNextFocusWidget(Widget _widget) {
		Widget nextWidget = null;
		int p;
		if (_widget != null) {
			p = m_children.indexOf(_widget);
			if (p >= 0) {
				p++;
			}
		} else {
			p = 0;
		}
		if (p >= 0) {
			ListIterator i = m_children.listIterator(p);
			while (i.hasNext() && (nextWidget == null)) {
				Widget w = (Widget)i.next();
				if (w.isFocusable()) {
					nextWidget = w;
					if (w instanceof CompoundWidget) {
						CompoundWidget cw = (CompoundWidget)w;
						w = cw.getNextFocusWidget(null);
						if (w != null) {
							nextWidget = w;
						}
					}
				}
			}
			if (nextWidget == null) {
				// No focusable widget was found, let's ask our parent
				nextWidget = getParent().getNextFocusWidget(this);
			}
			if ((nextWidget == null) && (_widget != null)) {
				// No focusable widget was found, let's try again from the beginning of the list
				nextWidget = getNextFocusWidget(null);
			}
		}
		if (nextWidget == null) {
			// No next widget could be determined, return current value
			nextWidget = m_focusWidget;
		}
		return nextWidget;
	}

	protected void calculateBounds(RenderContext _context) {
		super.calculateBounds(_context);
		if (m_layouter != null) {
			m_layouter.layoutChildren(this);
		}
	}
	
	public void initRendering(RenderContext _context) {
		super.initRendering(_context);
		if (isVisible()) {
			initChildren(_context);
		}
	}

	protected void initChildren(RenderContext _context) {
		Iterator i = getChildren();
		while (i.hasNext()) {
			Widget w = (Widget)i.next();
			w.initRendering(_context);
		}
	}

	protected void renderWidget(RenderContext _context, RenderObserver _observer) {
		super.renderWidget(_context, _observer);
		renderChildren(_context, _observer);
	}

	protected void renderChildren(RenderContext _context, RenderObserver _observer) {
		Iterator i = getChildren();
		while (i.hasNext()) {
			Widget w = (Widget)i.next();
			w.render(_context, _observer);
		}
	}
}

/*
 * $Log$
 * Revision 1.5  2004/05/04 22:12:54  tako
 * Added license.
 * Added support for layouters.
 * Fixed getWidgetUnderPoint() that used to return the bottommost widget instead of the topmost.
 * Focus switching is now properly supported for CompoundWidgets.
 * Will now only render children if visible itself.
 *
 * Revision 1.4  2004/03/07 18:21:29  tako
 * Bounds calculations and render functions now all have a RenderContext argument.
 *
 * Revision 1.3  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.2  2003/12/14 03:09:55  tako
 * Adding a widget without a name will no longer give it an internally generated
 * name, it will just be nameless and Themes properties won't be able to
 * refer to it.
 *
 * Revision 1.1  2003/12/14 02:36:26  tako
 * CompoundWidget added that has almost all the functionality of Container
 * but with all protected methods meant for complex widgets. Container has
 * become little more than a public version of CompoundWidget.
 *
 */