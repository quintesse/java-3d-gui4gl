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
 */
public class WindowRenderer extends DefaultWidgetRendererImpl {
	private Rectangle m_tmpBounds;
	
	public WindowRenderer() {
		m_tmpBounds = new Rectangle();
	}
	
	public void initRendering(AbstractWidget _widget, RenderContext _context) {
		super.initRendering(_widget, _context);
	}

	public void render(AbstractWidget _widget, RenderContext _context) {
		super.render(_widget, _context);
		
		GL gl = _context.getGl();

		Font captionFont;
		GLColor titlebarColor;
		float fTitlebarTransparancy;
		GLColor captionFontColor;
		int nCaptionPadding;
		Window window = (Window)_widget;
		if (window.isActive()) {
			captionFont = window.getActiveCaptionFont();
			titlebarColor = window.getActiveTitlebarColor();
			fTitlebarTransparancy = window.getActiveTitlebarTransparancy();
			captionFontColor = window.getActiveCaptionFontColor();
			nCaptionPadding = window.getActiveCaptionPadding();
		} else {
			captionFont = window.getCaptionFont();
			titlebarColor = window.getTitlebarColor();
			fTitlebarTransparancy = window.getTitlebarTransparancy();
			captionFontColor = window.getCaptionFontColor();
			nCaptionPadding = window.getCaptionPadding();
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
			m_tmpBounds.y -= fFontHeight + 2 * nCaptionPadding + fFontPadding;
			m_tmpBounds.height = (int)fFontHeight + 2 * nCaptionPadding + (int)fFontPadding;
			RenderHelper.drawRectangle(gl, m_tmpBounds);
	
			gl.glEnd();
			gl.glDisable(GL.GL_BLEND);
	
			// Title text
			int nCaptionAlignment = window.getCaptionAlignment();
			GLText.drawText(_context, m_tmpBounds, nCaptionPadding, captionFont, captionFontColor, false, nCaptionAlignment, sTitle);

			gl.glEnable(GL.GL_TEXTURE_2D);
		}
	}
}
