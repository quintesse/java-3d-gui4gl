/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 */
public class Screen extends Container implements KeyListener {
	private KeyListener m_keyListener;
	
	public Screen() {
		m_keyListener = null;
	}
	
	public AbstractWidget getPreviousFocusWidget(AbstractWidget _widget) {
		return null;
	}

	public AbstractWidget getNextFocusWidget(AbstractWidget _widget) {
		return null;
	}
	
	public AbstractWidget nextFocus() {
		return null;
	}
	
	public AbstractWidget previousFocus() {
		return null;
	}
	
	public void setKeyListener(KeyListener _listener) {
		m_keyListener = _listener;
	}
	
	public void keyPressed(KeyEvent _event) {
		AbstractWidget w = getFocusWidget();
		if (w != null) {
			KeyEvent e = new KeyEvent(_event.getComponent(), _event.getID(), _event.getWhen(), _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar(), _event.getKeyLocation());
			w.processKeyPressedEvent(e);
		} else {
			m_keyListener.keyPressed(_event);
		}
	}
		
	public void keyReleased(KeyEvent _event) {
		AbstractWidget w = getFocusWidget();
		if (w != null) {
			KeyEvent e = new KeyEvent(_event.getComponent(), _event.getID(), _event.getWhen(), _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar(), _event.getKeyLocation());
			w.processKeyReleasedEvent(e);
		} else {
			m_keyListener.keyReleased(_event);
		}
	}
		
	public void keyTyped(KeyEvent _event) {
		AbstractWidget w = getFocusWidget();
		if (w != null) {
			KeyEvent e = new KeyEvent(_event.getComponent(), _event.getID(), _event.getWhen(), _event.getModifiersEx(), _event.getKeyCode(), _event.getKeyChar(), _event.getKeyLocation());
			w.processKeyTypedEvent(e);
		} else {
			m_keyListener.keyTyped(_event);
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

	public void renderWidget(RenderContext _context) {
	}
}
