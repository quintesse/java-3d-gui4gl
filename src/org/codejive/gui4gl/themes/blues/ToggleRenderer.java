/*
 * Created on Nov 21, 2003
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.*;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 * @version $Revision: 145 $
 */
public class ToggleRenderer implements WidgetRendererModel {

	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Toggle.class, _widget, _context);
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		RenderHelper.updateSuperClass(Toggle.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Toggle.class, _widget, _context);

		GL gl = _context.getGl();

		Font captionFont;
		GLColor captionFontColor;
		GLColor checkColor;
		GLColor checkBackgroundColor;
		float fcheckTransparancy;
		
		Toggle toggle = (Toggle)_widget;
		if (toggle.hasFocus()) {
			captionFont = toggle.getFocusedCaptionFont();
			captionFontColor = toggle.getFocusedCaptionFontColor();
			checkColor = toggle.getFocusedCheckColor();
			checkBackgroundColor = toggle.getFocusedCheckBackgroundColor();
			fcheckTransparancy = toggle.getFocusedCheckTransparancy();
		} else {
			captionFont = toggle.getCaptionFont();
			captionFontColor = toggle.getCaptionFontColor();
			checkColor = toggle.getCheckColor();
			checkBackgroundColor = toggle.getCheckBackgroundColor();
			fcheckTransparancy = toggle.getCheckTransparancy();
		}

		gl.glDisable(GL.GL_TEXTURE_2D);

		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL.GL_QUADS);

		gl.glColor4f(checkBackgroundColor.getRed(), checkBackgroundColor.getGreen(), checkBackgroundColor.getBlue(), 1.0f - fcheckTransparancy);
		Rectangle bounds = new Rectangle(toggle.getInnerBounds());
		RenderHelper.drawRectangle(gl, bounds.x, bounds.y + 1, bounds.height - 2, bounds.height - 2);
		
		if (toggle.isChecked()) {
			gl.glColor4f(checkColor.getRed(), checkColor.getGreen(), checkColor.getBlue(), 1.0f - fcheckTransparancy);
			RenderHelper.drawRectangle(gl, bounds.x + 1, bounds.y + 2, bounds.height - 4, bounds.height - 4);
		}

		gl.glEnd();
		gl.glDisable(GL.GL_BLEND);
		
		String sCaption = toggle.getCaption();
		if (sCaption != null) {
			// Caption text
			int nCaptionAlignment = toggle.getCaptionAlignment();
			bounds.x += bounds.height + 5;
			GLText.drawText(_context, bounds, 0, 0, captionFont, captionFontColor, true, nCaptionAlignment, sCaption, "...");
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}
}

/*
 * $Log$
 * Revision 1.2  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.1  2003/11/21 01:27:22  tako
 * First check-in of the renderer for the new Toggle widget.
 *
 */