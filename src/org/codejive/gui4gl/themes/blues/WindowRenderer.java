/*
 * Created on Oct 31, 2003
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
 * @version $Revision: 73 $
 */
public class WindowRenderer implements WidgetRendererModel {
	private Rectangle m_tmpBounds;
	
	public WindowRenderer() {
		m_tmpBounds = new Rectangle();
	}
	
	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Window.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Window.class, _widget, _context);
		
		GL gl = _context.getGl();

		Font captionFont;
		GLColor titlebarColor;
		float fTitlebarTransparancy;
		GLColor captionFontColor;
		int nCaptionXPadding, nCaptionYPadding;
		Window window = (Window)_widget;
		if (window.isActive()) {
			captionFont = window.getActiveCaptionFont();
			titlebarColor = window.getActiveTitlebarColor();
			fTitlebarTransparancy = window.getActiveTitlebarTransparancy();
			captionFontColor = window.getActiveCaptionFontColor();
			nCaptionXPadding = window.getActiveCaptionXPadding();
			nCaptionYPadding = window.getActiveCaptionYPadding();
		} else {
			captionFont = window.getCaptionFont();
			titlebarColor = window.getTitlebarColor();
			fTitlebarTransparancy = window.getTitlebarTransparancy();
			captionFontColor = window.getCaptionFontColor();
			nCaptionXPadding = window.getCaptionXPadding();
			nCaptionYPadding = window.getCaptionYPadding();
		}

		String sTitle = window.getTitle();
		if (sTitle != null) {
			gl.glDisable(GL.GL_TEXTURE_2D);
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			gl.glBegin(GL.GL_QUADS);

			// Title bar
			float fFontHeight = captionFont.getSize(_context);
			float fFontBaseLine = captionFont.getBaseLine(_context);
			float fFontPadding = fFontHeight - fFontBaseLine;
			gl.glColor4f(titlebarColor.getRed(), titlebarColor.getGreen(), titlebarColor.getBlue(), 1.0f - fTitlebarTransparancy);
			m_tmpBounds.setBounds(window.getBounds());
			m_tmpBounds.y -= fFontHeight + 2 * nCaptionYPadding + fFontPadding;
			m_tmpBounds.height = (int)fFontHeight + 2 * nCaptionYPadding + (int)fFontPadding;
			RenderHelper.drawRectangle(gl, m_tmpBounds);
	
			gl.glEnd();
			gl.glDisable(GL.GL_BLEND);
	
			// Title text
			int nCaptionAlignment = window.getCaptionAlignment();
			GLText.drawText(_context, m_tmpBounds, nCaptionXPadding, nCaptionYPadding, captionFont, captionFontColor, false, nCaptionAlignment, sTitle);

			gl.glEnable(GL.GL_TEXTURE_2D);
		}
	}
}

/*
 * $Log$
 * Revision 1.5  2003/11/19 00:11:54  tako
 * Added support for seperate X and Y padding.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
