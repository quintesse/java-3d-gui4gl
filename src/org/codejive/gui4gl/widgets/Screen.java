/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 48 $
 */
public class Screen extends Container implements KeyListener {
	private List m_keyListeners;
	
	public Screen() {
		m_keyListeners = new LinkedList();
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
	
	public void addKeyListener(KeyListener _listener) {
		m_keyListeners.add(_listener);
	}
	
	public void removeKeyListener(KeyListener _listener) {
		m_keyListeners.remove(_listener);
	}
	
	public void keyPressed(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			KeyEvent e = new KeyEvent(_event.getComponent(), _event.getID(), _event.getWhen(), _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar(), _event.getKeyLocation());
			w.processKeyPressedEvent(e);
		} else {
			Iterator i = m_keyListeners.iterator();
			while (i.hasNext()) {
				((KeyListener)i.next()).keyPressed(_event);
			}
		}
	}
		
	public void keyReleased(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			KeyEvent e = new KeyEvent(_event.getComponent(), _event.getID(), _event.getWhen(), _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar(), _event.getKeyLocation());
			w.processKeyReleasedEvent(e);
		} else {
			Iterator i = m_keyListeners.iterator();
			while (i.hasNext()) {
				((KeyListener)i.next()).keyReleased(_event);
			}
		}
	}
		
	public void keyTyped(KeyEvent _event) {
		Widget w = getFocusWidget();
		if (w != null) {
			KeyEvent e = new KeyEvent(_event.getComponent(), _event.getID(), _event.getWhen(), _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar(), _event.getKeyLocation());
			w.processKeyTypedEvent(e);
		} else {
			Iterator i = m_keyListeners.iterator();
			while (i.hasNext()) {
				((KeyListener)i.next()).keyTyped(_event);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	public void initRendering(RenderContext _context) {
		GL gl = _context.getGl();
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		Rectangle rect = getRectangle();
		rect.setBounds(viewport[0], viewport[1], viewport[2], viewport[3]);
		super.initRendering(_context);
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
}

/*
 * $Log$
 * Revision 1.5  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
