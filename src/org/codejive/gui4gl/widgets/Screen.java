/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import net.java.games.jogl.GL;

import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 147 $
 */
public class Screen extends Container implements KeyListener, MouseInputListener {
	private Widget m_widgetUnderMouse;
	private Widget m_widgetPressed;
	private int m_nLastXPos, m_nLastYPos;
	
	public Screen() {
		m_widgetUnderMouse = null;
		m_widgetPressed = null;
		m_nLastXPos = m_nLastYPos = -1;
	}
	
	public Toplevel getToplevel() {
		return null;
	}
	
	public Toplevel getActiveToplevel() {
		return getFocusWidget().getToplevel();
	}
	
	public void add(Widget _child, String _sName) {
		if (_child instanceof Toplevel) {
			super.add(_child, _sName);
		} else {
			throw new RuntimeException("Screen only accepts Toplevel widgets as it children");
		}
	}
	
	public Widget getPreviousFocusWidget(Widget _widget) {
		return null;
	}

	public Widget getNextFocusWidget(Widget _widget) {
		return null;
	}
	
	public Widget nextFocus() {
		return null;
	}
	
	public Widget previousFocus() {
		return null;
	}
	
	protected void resize(RenderContext _context) {
		GL gl = _context.getGl();
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		setBounds(viewport[0], viewport[1], viewport[2], viewport[3]);
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	public void initRendering(RenderContext _context) {
		resize(_context);
		super.initRendering(_context);
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#updateRendering(org.codejive.world3d.RenderContext)
	 */
	public void updateRendering(RenderContext _context) {
		resize(_context);
		super.updateRendering(_context);
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#render(org.codejive.world3d.RenderContext)
	 */
	public void render(RenderContext _context) {
		GL gl = _context.getGl();

		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix ();
		gl.glLoadIdentity();
 
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		_context.getGlu().gluOrtho2D(0, viewport[2], viewport[3], 0);
		gl.glDepthFunc(GL.GL_ALWAYS);

		super.render(_context);

		gl.glDepthFunc(GL.GL_LESS);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPopMatrix (); 		
	}

	public void keyPressed(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			GuiKeyEvent e = new GuiKeyEvent(w, _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar());
			w.processKeyPressedEvent(e);
		} else {
			GuiKeyEvent e = new GuiKeyEvent(this, _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar());
			processKeyPressedEvent(e);
		}
	}
		
	public void keyReleased(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			GuiKeyEvent e = new GuiKeyEvent(w, _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar());
			w.processKeyReleasedEvent(e);
		} else {
			GuiKeyEvent e = new GuiKeyEvent(this, _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar());
			processKeyReleasedEvent(e);
		}
	}
		
	public void keyTyped(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			GuiKeyEvent e = new GuiKeyEvent(w, _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar());
			w.processKeyTypedEvent(e);
		} else {
			GuiKeyEvent e = new GuiKeyEvent(this, _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar());
			processKeyTypedEvent(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		handleMouseHoover(w);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent _event) {
		handleMouseHoover(null);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		m_widgetPressed = w;
		handleMouseHoover(w);
		if (w != null) {
			GuiMouseEvent e = new GuiMouseEvent(w, _event.getModifiersEx(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
			w.processMousePressedEvent(e);
		} else {
			GuiMouseEvent e = new GuiMouseEvent(this, _event.getModifiersEx(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
			processMousePressedEvent(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent _event) {
		Widget w = getWidgetUnderPoint(_event.getX(), _event.getY());
		handleMouseHoover(w);
		if (m_widgetPressed != null) {
			GuiMouseEvent e = new GuiMouseEvent(m_widgetPressed, _event.getModifiersEx(), _event.getX(), -1, -1, _event.getY(), _event.getClickCount());
			m_widgetPressed.processMouseReleasedEvent(e);
		} else {
			GuiMouseEvent e = new GuiMouseEvent(this, _event.getModifiersEx(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
			processMouseReleasedEvent(e);
		}
		if (w == m_widgetPressed) {
			if (w != null) {
				GuiMouseEvent e = new GuiMouseEvent(w, _event.getModifiersEx(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
				w.processMouseClickedEvent(e);
			} else {
				GuiMouseEvent e = new GuiMouseEvent(this, _event.getModifiersEx(), _event.getX(), _event.getY(), -1, -1, _event.getClickCount());
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
	
	protected void handleMouseHoover(Widget _widget) {
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
		handleMouseHoover(w);
		if (m_nLastXPos == -1) {
			m_nLastXPos = _event.getX();
			m_nLastYPos = _event.getY();
		}
		int nDeltaX = _event.getX() - m_nLastXPos;
		int nDeltaY = _event.getY() - m_nLastYPos;
		if (m_widgetPressed == null) {
			GuiMouseEvent e = new GuiMouseEvent(this, _event.getModifiersEx(), _event.getX(), _event.getY(), nDeltaX, nDeltaY, _event.getClickCount());
			processMouseMovedEvent(e);
		} else {
			GuiMouseEvent e = new GuiMouseEvent(m_widgetPressed, _event.getModifiersEx(), _event.getX(), _event.getY(), nDeltaX, nDeltaY, _event.getClickCount());
			m_widgetPressed.processMouseDraggedEvent(e);
		}
		m_nLastXPos = _event.getX();
		m_nLastYPos = _event.getY();
	}
}

/*
 * $Log$
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
