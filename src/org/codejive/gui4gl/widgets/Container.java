/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 */
public class Container extends Widget implements AbstractContainer {
	private LinkedList m_children;
	private AbstractWidget m_focusWidget;

	public Container() {
		m_children = new LinkedList();
		m_focusWidget = null;
	}
	
	public void add(Widget _child) {
		m_children.add(_child);
		_child.setParent(this);
	}
	
	public List getChildren() {
		return m_children;
	}
	
	public boolean isFocusable() {
		return false;
	}
	
	public AbstractWidget getFocusWidget() {
		return m_focusWidget;
	}
	
	public void setFocusWidget(Widget _widget) {
		m_focusWidget = _widget;
		if (getParent() != null) {
			getParent().setFocusWidget(_widget);
		}
	}

	public AbstractWidget getPreviousFocusWidget(AbstractWidget _widget) {
		AbstractWidget prevWidget = null;
		int p;
		if (_widget != null) {
			p = m_children.indexOf(_widget);
		} else {
			p = m_children.size();
		}
		if (p >= 0) {
			ListIterator i = m_children.listIterator(p);
			while (i.hasPrevious() && (prevWidget == null)) {
				AbstractWidget w = (AbstractWidget)i.previous();
				if (w.isFocusable()) {
					prevWidget = w;
				} else if (w instanceof Container) {
					AbstractContainer cw = (AbstractContainer)w;
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

	public AbstractWidget getNextFocusWidget(AbstractWidget _widget) {
		AbstractWidget nextWidget = null;
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
				AbstractWidget w = (AbstractWidget)i.next();
				if (w.isFocusable()) {
					nextWidget = w;
				} else if (w instanceof Container) {
					AbstractContainer cw = (AbstractContainer)w;
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
		Iterator i = getChildren().iterator();
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
		Iterator i = getChildren().iterator();
		while (i.hasNext()) {
			Widget w = (Widget)i.next();
			w.render(_context);
		}
	}
}
