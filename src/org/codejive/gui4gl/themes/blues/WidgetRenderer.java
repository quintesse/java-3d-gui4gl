/*
 * Created on Nov 3, 2003
 */
package org.codejive.gui4gl.themes.blues;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.Texture;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.themes.RenderHelper;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 * @version $Revision: 145 $
 */
public class WidgetRenderer implements WidgetRendererModel {

	/* (non-Javadoc)
	 * @see org.codejive.world3d.gui.WidgetRenderer#initRendering(org.codejive.world3d.gui.Widget, org.codejive.world3d.RenderContext)
	 */
	public void initRendering(Widget _widget, RenderContext _context) {
		// No code necessary
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		// No code necessary
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.themes.WidgetRenderer#render(org.codejive.gui4gl.widgets.AbstractWidget, org.codejive.world3d.RenderContext)
	 */
	public void render(Widget _widget, RenderContext _context) {
		GL gl = _context.getGl();

		GLColor backgroundColor;
		float fTransparancy;
		Texture backgroundImage;

		if (_widget.hasFocus()) {
			backgroundColor = _widget.getFocusedBackgroundColor();
			fTransparancy = _widget.getFocusedTransparancy();
			backgroundImage = _widget.getFocusedBackgroundImage();
		} else {
			backgroundColor = _widget.getBackgroundColor();
			fTransparancy = _widget.getTransparancy();
			backgroundImage = _widget.getBackgroundImage();
		}
		
		if (backgroundImage == null) {
			gl.glDisable(GL.GL_TEXTURE_2D);
		} else {
			gl.glEnable(GL.GL_TEXTURE_2D);
		}
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL.GL_QUADS);

		// Widget background
		gl.glColor4f(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 1.0f - fTransparancy);
		RenderHelper.drawRectangle(gl, _widget.getCurrentBounds());

		gl.glEnd();
		gl.glDisable(GL.GL_BLEND);
	}

}

/*
 * $Log$
 * Revision 1.5  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
