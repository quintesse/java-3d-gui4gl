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

import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL2;

/**
 * This class represents the entire visible screen estate that can be used to
 * display widgets. Any widget that needs to be display must either directly
 * or indirectly be a child of a Screen object. The Screen is de root of the
 * entire widget tree that must be displayed. A Screen is normally supposed
 * to cover the entire OpenGL viewport that it will displayed in (although it
 * is not actually required to be that exact size).
 * The Screen is also responsible for translating and dispatching all the
 * system-events to the widgets that want/need them.
 * 
 * @author tako
 * @version $Revision: 361 $
 */
public class Screen extends Container implements KeyListener, MouseListener {
	private Widget m_widgetUnderMouse;
	private Widget m_widgetPressed;
	private int m_nLastXPos, m_nLastYPos;
	
	private static final int EVENT_KEY_TYPED = 333;
	
	/**
	 * Creates a new Screen.
	 */
	public Screen() {
		m_widgetUnderMouse = null;
		m_widgetPressed = null;
		m_nLastXPos = m_nLastYPos = -1;
	}
	
	@Override
	public Screen getScreen() {
		return this;
	}
	
	@Override
	public Toplevel getToplevel() {
		return null;
	}
	
	/**
	 * Returns the TopLevel object that has the current focus.
	 * @return The currently focused TopLevel object
	 */
	public Toplevel getActiveToplevel() {
		Toplevel top;
		if (getFocusWidget() != null) {
			top = getFocusWidget().getToplevel();
		} else {
			top = null;
		}
		return top;
	}
	
	@Override
	public void add(Widget _child) {
		if (_child instanceof Toplevel) {
			super.add(_child);
		} else {
			throw new RuntimeException("Screen only accepts Toplevel widgets as it children");
		}
	}
	
	
	@Override
	public void setFocus() {
		if (isFocusable()) {
			setFocusWidget(null);
		}
	}
	
	
	@Override
	public Widget getPreviousFocusWidget(Widget _widget) {
		return null;
	}
	

	@Override
	public Widget getNextFocusWidget(Widget _widget) {
		return null;
	}
	
	@Override
	public Widget nextFocus() {
		return null;
	}
	
	
	@Override
	public Widget previousFocus() {
		return null;
	}
	
	
	protected void resize(RenderContext _context) {
		GL2 gl = _context.getGl();
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		setBounds(viewport[0], viewport[1], viewport[2], viewport[3]);
	}

	
	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	@Override
	public void initRendering(RenderContext _context) {
		resize(_context);
		super.initRendering(_context);
	}
	

	@Override
	public void render(RenderContext _context, RenderObserver _observer) {
		GL2 gl = _context.getGl();

		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix ();
		gl.glLoadIdentity();
 
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		_context.getGlu().gluOrtho2D(0, viewport[2], viewport[3], 0);
		gl.glDepthFunc(GL2.GL_ALWAYS);

		super.render(_context, _observer);

		gl.glDepthFunc(GL2.GL_LESS);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPopMatrix (); 		
	}

	public void keyPressed(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			GuiKeyEvent e = new GuiKeyEvent(w, KeyEvent.EVENT_KEY_PRESSED, _event.getModifiers(), _event.getKeyCode(), _event.getKeyChar());
			w.processKeyPressedEvent(e);
			if (_event.isPrintableKey()) {
				GuiKeyEvent ep = new GuiKeyEvent(w, EVENT_KEY_TYPED, _event.getModifiers(), _event.getKeyCode(), _event.getKeyChar());
				w.processKeyTypedEvent(ep);
			}
		} else {
			GuiKeyEvent e = new GuiKeyEvent(this, KeyEvent.EVENT_KEY_PRESSED, _event.getModifiers(), _event.getKeyCode(), _event.getKeyChar());
			processKeyPressedEvent(e);
			if (_event.isPrintableKey()) {
				GuiKeyEvent ep = new GuiKeyEvent(w, EVENT_KEY_TYPED, _event.getModifiers(), _event.getKeyCode(), _event.getKeyChar());
				w.processKeyTypedEvent(ep);
			}
		}
	}
		
	public void keyReleased(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			GuiKeyEvent e = new GuiKeyEvent(w, KeyEvent.EVENT_KEY_RELEASED, _event.getModifiers(), _event.getKeyCode(), _event.getKeyChar());
			w.processKeyReleasedEvent(e);
		} else {
			GuiKeyEvent e = new GuiKeyEvent(this, KeyEvent.EVENT_KEY_RELEASED, _event.getModifiers(), _event.getKeyCode(), _event.getKeyChar());
			processKeyReleasedEvent(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		handleMouseHover(w);
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent _event) {
		handleMouseHover(null);
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		m_widgetPressed = w;
		handleMouseHover(w);
		if (w != null) {
			GuiMouseEvent e = new GuiMouseEvent(w, MouseEvent.EVENT_MOUSE_PRESSED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
			w.processMousePressedEvent(e);
		} else {
			GuiMouseEvent e = new GuiMouseEvent(this, MouseEvent.EVENT_MOUSE_PRESSED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
			processMousePressedEvent(e);
		}
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		handleMouseHover(w);
		if (m_widgetPressed != null) {
			GuiMouseEvent e = new GuiMouseEvent(m_widgetPressed, MouseEvent.EVENT_MOUSE_RELEASED, _event.getButton(), _event.getModifiers(), _event.getX(), -1, -1, _event.getY(), _event.getClickCount());
			m_widgetPressed.processMouseReleasedEvent(e);
		} else {
			GuiMouseEvent e = new GuiMouseEvent(this, MouseEvent.EVENT_MOUSE_RELEASED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
			processMouseReleasedEvent(e);
		}
		if (w == m_widgetPressed) {
			if (w != null) {
				GuiMouseEvent e = new GuiMouseEvent(w, MouseEvent.EVENT_MOUSE_CLICKED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
				w.processMouseClickedEvent(e);
			} else {
				GuiMouseEvent e = new GuiMouseEvent(this, MouseEvent.EVENT_MOUSE_CLICKED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
				processMouseClickedEvent(e);
			}
		}
		m_widgetPressed = null;
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent _event) {
		// We do our own click handling
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent _event) {
		handleMouseMove(_event);
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent _event) {
		handleMouseMove(_event);
	}
	
	
	protected void handleMouseHover(Widget _widget) {
		if (_widget != m_widgetUnderMouse) {
			if (m_widgetUnderMouse != null) {
				// TODO: Decide if we want to send MouseLeave events
			}
			m_widgetUnderMouse = _widget;
			if (_widget != null) {
				// TODO: Decide if we want to send MouseEnter events
			}
		}
	}

	protected void handleMouseMove(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		handleMouseHover(w);
		if (m_nLastXPos == -1) {
			m_nLastXPos = _event.getX();
			m_nLastYPos = _event.getY();
		}
		int nDeltaX = _event.getX() - m_nLastXPos;
		int nDeltaY = _event.getY() - m_nLastYPos;
		if (m_widgetPressed == null) {
			GuiMouseEvent e = new GuiMouseEvent(this, MouseEvent.EVENT_MOUSE_MOVED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), nDeltaX, nDeltaY, _event.getClickCount());
			processMouseMovedEvent(e);
		} else {
			GuiMouseEvent e = new GuiMouseEvent(m_widgetPressed, MouseEvent.EVENT_MOUSE_DRAGGED, _event.getButton(), _event.getModifiers(), _event.getX(), _event.getY(), nDeltaX, nDeltaY, _event.getClickCount());
			m_widgetPressed.processMouseDraggedEvent(e);
		}
		m_nLastXPos = _event.getX();
		m_nLastYPos = _event.getY();
	}

	public void mouseWheelMoved(MouseEvent e) {
		// Did I even have a mouse with a scroll wheel back then? ;)
	}
}

/*
 * $Log$
 * Revision 1.20  2004/10/17 11:07:33  tako
 * Removed updateRendering().
 *
 * Revision 1.19  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.18  2004/05/04 23:54:02  tako
 * Fixed typo in method name.
 *
 * Revision 1.17  2004/05/04 22:14:58  tako
 * Tried to make the getScreen() a bit more efficient.
 *
 * Revision 1.16  2004/03/07 18:21:29  tako
 * Bounds calculations and render functions now all have a RenderContext argument.
 *
 * Revision 1.15  2003/12/15 17:28:21  tako
 * Clicking in a Screen will now deactivate the active window.
 *
 * Revision 1.14  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.13  2003/12/11 10:49:25  tako
 * All mouse events now include information about which button changed
 * state.
 * Fixed null pointer exception in getActiveToplevel().
 *
 * Revision 1.12  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.11  2003/11/24 19:19:32  tako
 * Added possibility for events to have an Id (like AWTEvents have) so
 * you can distinguish between the different event sub-types.
 *
 * Revision 1.10  2003/11/24 17:17:17  tako
 * Got rid of warning.
 *
 * Revision 1.9  2003/11/24 16:54:19  tako
 * Implemented mouse move and drag event handling.
 * Updated existing mouse event handling because of the new delta X and Y.
 * Implemented updateRendering().
 *
 * Revision 1.8  2003/11/23 02:04:27  tako
 * Added mouse support.
 *
 * Revision 1.7  2003/11/21 01:29:15  tako
 * Added getActiveToplevel() method.
 * Overriden getToplevel().
 * Adding a non-toplevel widget now throws an exception.
 *
 * Revision 1.6  2003/11/19 11:19:41  tako
 * Implemented completely new event system because tryin to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.5  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
