/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Tako Schotanus
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
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
 * @version $Revision: 237 $
 */
public class WindowRenderer implements WidgetRendererModel {
	private Rectangle m_tmpBounds;
	
	public WindowRenderer() {
		m_tmpBounds = new Rectangle();
	}
	
	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Window.class, _widget, _context);
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		RenderHelper.updateSuperClass(Window.class, _widget, _context);
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
			captionFont = (Font)window.getAttribute("textFont#active");
			captionFontColor = (GLColor)window.getAttribute("textFontColor#active");
			titlebarColor = (GLColor)window.getAttribute("titlebarColor#active");
			fTitlebarTransparancy = window.getFloatAttribute("titlebarTransparancy#active");
			nCaptionXPadding = window.getIntegerAttribute("captionXPadding#active");
			nCaptionYPadding = window.getIntegerAttribute("captionYPadding#active");
		} else {
			if (window.isEnabled()) {
				captionFont = (Font)window.getAttribute("textFont");
				captionFontColor = (GLColor)window.getAttribute("textFontColor");
				titlebarColor = (GLColor)window.getAttribute("titlebarColor");
				fTitlebarTransparancy = window.getFloatAttribute("titlebarTransparancy");
				nCaptionXPadding = window.getIntegerAttribute("captionXPadding");
				nCaptionYPadding = window.getIntegerAttribute("captionYPadding");
			} else {
				captionFont = (Font)window.getAttribute("textFont#disabled");
				captionFontColor = (GLColor)window.getAttribute("textFontColor#disabled");
				titlebarColor = (GLColor)window.getAttribute("titlebarColor#disabled");
				fTitlebarTransparancy = window.getFloatAttribute("titlebarTransparancy#disabled");
				nCaptionXPadding = window.getIntegerAttribute("captionXPadding#disabled");
				nCaptionYPadding = window.getIntegerAttribute("captionYPadding#disabled");
			}
		}

		String sTitle = window.getTitle();
		if (sTitle != null) {
			gl.glDisable(GL.GL_TEXTURE_2D);
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			gl.glBegin(GL.GL_QUADS);

			// Title bar
			gl.glColor4f(titlebarColor.getRed(), titlebarColor.getGreen(), titlebarColor.getBlue(), 1.0f - fTitlebarTransparancy);
			m_tmpBounds.setBounds(window.getCurrentBounds());
			m_tmpBounds.height = window.getIntegerAttribute("titlebarHeight");
			RenderHelper.drawRectangle(gl, m_tmpBounds);
	
			gl.glEnd();
			gl.glDisable(GL.GL_BLEND);
	
			// Title text
			int nCaptionAlignment = window.getIntegerAttribute("textAlignment");
			GLText.drawText(_context, m_tmpBounds, nCaptionXPadding, nCaptionYPadding, captionFont, captionFontColor, false, nCaptionAlignment, sTitle, "...");

			gl.glEnable(GL.GL_TEXTURE_2D);
		}
	}
}

/*
 * $Log$
 * Revision 1.11  2004/05/04 21:59:24  tako
 * Now using the new attribute map instead of individual property getters and setters.
 *
 * Revision 1.10  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.9  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.8  2003/11/24 16:50:35  tako
 * Implemented updateRendering().
 * Renamed getBounds() to getCurrentBounds().
 *
 * Revision 1.7  2003/11/21 00:19:18  tako
 * Minor code change because GLText signature was changed.
 *
 * Revision 1.6  2003/11/20 00:39:12  tako
 * Changes to title bar size calculations because the height of the bar is
 * now a property of the Window class.
 * The title bar is now drawn inside the bounds of the widget as opposed
 * to outside like it was before.
 *
 * Revision 1.5  2003/11/19 00:11:54  tako
 * Added support for seperate X and Y padding.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
