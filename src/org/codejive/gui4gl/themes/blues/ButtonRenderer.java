/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes.blues;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.*;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 * @version $Revision: 144 $
 */
public class ButtonRenderer implements WidgetRendererModel {

	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Button.class, _widget, _context);
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		RenderHelper.updateSuperClass(Button.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Button.class, _widget, _context);

		GL gl = _context.getGl();

		Font captionFont;
		GLColor captionFontColor;
		
		Button button = (Button)_widget;
		if (button.isSelected()) {
			captionFont = button.getSelectedCaptionFont();
			captionFontColor = button.getSelectedCaptionFontColor();
		} else {
			if (button.hasFocus()) {
				captionFont = button.getFocusedCaptionFont();
				captionFontColor = button.getFocusedCaptionFontColor();
			} else {
				captionFont = button.getCaptionFont();
				captionFontColor = button.getCaptionFontColor();
			}
		}

		gl.glDisable(GL.GL_TEXTURE_2D);

		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL.GL_QUADS);

		if (button.isSelected()) {
			GLColor selectedBackgroundColor = button.getSelectedBackgroundColor();
			float fselectedTransparancy = button.getSelectedTransparancy();
			gl.glColor4f(selectedBackgroundColor.getRed(), selectedBackgroundColor.getGreen(), selectedBackgroundColor.getBlue(), 1.0f - fselectedTransparancy);
			RenderHelper.drawRectangle(gl, _widget.getCurrentBounds());
		}

		gl.glEnd();
		gl.glDisable(GL.GL_BLEND);
		
		String sCaption = button.getCaption();
		if (sCaption != null) {
			// Caption text
			int nCaptionAlignment = button.getCaptionAlignment();
			GLText.drawText(_context, _widget.getInnerBounds(), 0, 0, captionFont, captionFontColor, true, nCaptionAlignment, sCaption, "...");
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}
}

/*
 * $Log$
 * Revision 1.8  2003/11/24 16:50:35  tako
 * Implemented updateRendering().
 * Renamed getBounds() to getCurrentBounds().
 *
 * Revision 1.7  2003/11/21 01:26:37  tako
 * Removed padding code, now using getInnerBounds().
 *
 * Revision 1.6  2003/11/21 00:19:18  tako
 * Minor code change because GLText signature was changed.
 *
 * Revision 1.5  2003/11/19 00:10:14  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
