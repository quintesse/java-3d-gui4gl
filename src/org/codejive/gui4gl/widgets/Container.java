/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 */
public class Container extends Widget {
	private List m_children;
	private Map m_childNames;
	private Widget m_focusWidget;

	private static int m_nWidgetNr = 0;
	
	public Container() {
		m_children = new LinkedList();
		m_childNames = new HashMap();
		m_focusWidget = null;
	}
	
	public void add(Widget _child) {
		add(_child, "widget" + m_nWidgetNr++);
	}
	
	public void add(Widget _child, String _sName) {
		m_children.add(_child);
		m_childNames.put(_sName, _child);
		_child.setParent(this);
	}
	
	public Widget getChild(String _sName) {
		return (Widget)m_childNames.get(_sName);
	}

	public Iterator getChildren() {
		return m_children.iterator();
	}
	
	public Widget findChild(String _sName) {
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

	public boolean isFocusable() {
		return false;
	}
	
	public Widget getFocusWidget() {
		return m_focusWidget;
	}
	
	public void setFocusWidget(Widget _widget) {
		m_focusWidget = _widget;
		if (getParent() != null) {
			getParent().setFocusWidget(_widget);
		}
	}

	public Widget getPreviousFocusWidget(Widget _widget) {
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
				} else if (w instanceof Container) {
					Container cw = (Container)w;
					w = cw.getPreviousFocusWidget(null);
					if (w != null) {
						prevWidget = w;
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

	public Widget getNextFocusWidget(Widget _widget) {
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
				} else if (w instanceof Container) {
					Container cw = (Container)w;
					w = cw.getNextFocusWidget(null);
					if (w != null) {
						nextWidget = w;
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
	
	public void processKeyPressedEvent(KeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_UP:
				getFocusWidget().previousFocus().setFocus();
				break;
			case KeyEvent.VK_DOWN:
				getFocusWidget().nextFocus().setFocus();
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	public void initRendering(RenderContext _context) {
		super.initRendering(_context);
		if (isVisible()) {
			initChildren(_context);
		}
	}
	
	public void initChildren(RenderContext _context) {
		Iterator i = getChildren();
		while (i.hasNext()) {
			Widget w = (Widget)i.next();
			w.initRendering(_context);
		}
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#render(org.codejive.world3d.RenderContext)
	 */
	public void render(RenderContext _context) {
		super.render(_context);
		if (isVisible()) {
			renderChildren(_context);
		}
	}
	
	public void renderChildren(RenderContext _context) {
		Iterator i = getChildren();
		while (i.hasNext()) {
			Widget w = (Widget)i.next();
			w.render(_context);
		}
	}
}
