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
 * @version $Revision: 93 $
 */
public class TextRenderer implements WidgetRendererModel {

	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Text.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Text.class, _widget, _context);

		GL gl = _context.getGl();

		Text text = (Text)_widget;
		
		Font textFont = text.getTextFont();
		GLColor textFontColor;
		if(text.hasFocus()) {
			textFontColor = text.getTextFontFocusedColor();
		} else {
			textFontColor = text.getTextFontColor();
		}
		int nTextAlignment = text.getTextAlignment();

		gl.glDisable(GL.GL_TEXTURE_2D);

		String sText = text.getText();
		if (sText != null) {
			// Caption text			
			GLText.drawText(_context, _widget.getInnerBounds(), 0, 0, textFont, textFontColor, true, nTextAlignment, sText);
		
			/*
			if(text.hasFocus()) {
				// show cursor
				// BUT WHERE???? TAKO! :)
			}*/
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}
}

/*
 * $Log$
 * Revision 1.6  2003/11/19 17:11:54  steven
 * Preliminary check in of Text widget editing
 *
 * Revision 1.5  2003/11/19 00:10:45  tako
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
