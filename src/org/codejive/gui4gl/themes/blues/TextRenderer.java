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
 */
public class TextRenderer implements AbstractWidgetRenderer {

	public void initRendering(AbstractWidget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Text.class, _widget, _context);
	}

	public void render(AbstractWidget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Text.class, _widget, _context);

		GL gl = _context.getGl();

		Text text = (Text)_widget;
		
		Font textFont = text.getTextFont();
		GLColor textFontColor = text.getTextFontColor();
		int nTextPadding = text.getTextPadding();
		int nTextAlignment = text.getTextAlignment();

		gl.glDisable(GL.GL_TEXTURE_2D);

		String sText = text.getText();
		if (sText != null) {
			// Caption text
			GLText.drawText(_context, _widget.getBounds(), nTextPadding, textFont, textFontColor, true, nTextAlignment, sText);
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}
}
