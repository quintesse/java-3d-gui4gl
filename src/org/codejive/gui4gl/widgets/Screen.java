/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.java.games.jogl.GL;

import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 109 $
 */
public class Screen extends Container implements KeyListener {
	
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
 * Revision 1.7  2003/11/21 01:29:15  tako
 * Added getActiveToplevel() method.
 * Overriden getToplevel().
 * Adding a non-toplevel widget non throws an exception.
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
