/*
 * Created on Dec 14, 2003
 */
package org.codejive.gui4gl.widgets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;

/**
 * @author tako
 * @version $Revision: 222 $
 */
public class CompoundWidget extends Widget {
	protected LinkedList m_children;
	protected Map m_childNames;
	protected Widget m_focusWidget;

	public CompoundWidget() {
		this(null);
	}
	
	public CompoundWidget(String _sName) {
		super(_sName);
		m_children = new LinkedList();
		m_childNames = new HashMap();
		m_focusWidget = null;
		setFocusable(true);
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

	public void render(RenderContext _context, RenderObserver _observer) {
		super.render(_context, _observer);
		if (isVisible()) {
			renderChildren(_context, _observer);
		}
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