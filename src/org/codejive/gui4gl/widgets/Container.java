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

import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 146 $
 */
public class Container extends Widget {
	private LinkedList m_children;
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
		List x = new LinkedList(m_children);
		return x.iterator();
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
	
	protected Widget getWidgetUnderPoint(int _nXPos, int _nYPos) {
		Widget result = null;
		if (isVisible()) {
			Iterator i = getChildren();
			while ((result == null) && i.hasNext()) {
				Widget w = (Widget)i.next();
				result = w.getWidgetUnderPoint(_nXPos, _nYPos);
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
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		if (getFocusWidget() != null) {
			Widget w;
			switch (_event.getKeyCode()) {
				case KeyEvent.VK_UP:
					w = getFocusWidget().previousFocus();
					if (w != null) {
						w.setFocus();
					}
					break;
				case KeyEvent.VK_DOWN:
					w = getFocusWidget().nextFocus();
					if (w != null) {
						w.setFocus();
					}
					break;
				default:
					super.processKeyPressedEvent(_event);
					break;
			}
		} else {
			super.processKeyPressedEvent(_event);
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
	
	protected void initChildren(RenderContext _context) {
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
	
	protected void renderChildren(RenderContext _context) {
		Iterator i = getChildren();
		while (i.hasNext()) {
			Widget w = (Widget)i.next();
			w.render(_context);
		}
	}
}

/*
 * $Log$
 * Revision 1.10  2003/11/24 16:52:25  tako
 * Added moveChildToFront() and moveChildToBack().
 * getWidgetUnderPoint() no longer returns references to invisible widgets.
 *
 * Revision 1.9  2003/11/23 02:02:33  tako
 * Added getWidgetUnderPoint(int, int), necessary for the mouse support.
 *
 * Revision 1.8  2003/11/20 14:20:58  tako
 * Fixed problem with unfocused containers throwing away their key events.
 *
 * Revision 1.7  2003/11/20 13:12:23  tako
 * Made focus changes a bit more robust.
 *
 * Revision 1.6  2003/11/19 11:19:41  tako
 * Implemented completely new event system because trying to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.5  2003/11/19 00:12:57  tako
 * Made several methods protected instead of public.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
