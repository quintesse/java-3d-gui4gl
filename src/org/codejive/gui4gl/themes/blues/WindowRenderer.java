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

import javax.media.opengl.GL;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;
import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.*;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 * @version $Revision: 322 $
 */
public class WindowRenderer implements WidgetRendererModel {
	private Window m_window;
	private WidgetRendererModel m_superRenderer;
	private boolean m_bReady;
	private Rectangle m_tmpBounds;
	
	public WindowRenderer(Widget _widget) {
		m_window = (Window)_widget;
		m_superRenderer = RenderHelper.findSuperClassRenderer(Window.class, m_window);
		assert(m_superRenderer != null);
		m_bReady = false;
		m_tmpBounds = new Rectangle();
	}
	
	public boolean readyForRendering() {
		return m_bReady;
	}
	
	public void initRendering(RenderContext _context) {
		m_superRenderer.initRendering(_context);
		m_bReady = true;
	}

	public void render(RenderContext _context, RenderObserver _observer) {
		m_superRenderer.render(_context, _observer);
		
		GL gl = _context.getGl();

		Font captionFont;
		GLColor titlebarColor;
		float fTitlebarTransparancy;
		GLColor captionFontColor;
		int nCaptionXPadding, nCaptionYPadding;
		if (m_window.isActive()) {
			captionFont = (Font)m_window.getAttribute("textFont#active");
			captionFontColor = (GLColor)m_window.getAttribute("textFontColor#active");
			titlebarColor = (GLColor)m_window.getAttribute("titlebarColor#active");
			fTitlebarTransparancy = m_window.getFloatAttribute("titlebarTransparancy#active");
			nCaptionXPadding = m_window.getIntegerAttribute("captionXPadding#active");
			nCaptionYPadding = m_window.getIntegerAttribute("captionYPadding#active");
		} else {
			if (m_window.isEnabled()) {
				captionFont = (Font)m_window.getAttribute("textFont");
				captionFontColor = (GLColor)m_window.getAttribute("textFontColor");
				titlebarColor = (GLColor)m_window.getAttribute("titlebarColor");
				fTitlebarTransparancy = m_window.getFloatAttribute("titlebarTransparancy");
				nCaptionXPadding = m_window.getIntegerAttribute("captionXPadding");
				nCaptionYPadding = m_window.getIntegerAttribute("captionYPadding");
			} else {
				captionFont = (Font)m_window.getAttribute("textFont#disabled");
				captionFontColor = (GLColor)m_window.getAttribute("textFontColor#disabled");
				titlebarColor = (GLColor)m_window.getAttribute("titlebarColor#disabled");
				fTitlebarTransparancy = m_window.getFloatAttribute("titlebarTransparancy#disabled");
				nCaptionXPadding = m_window.getIntegerAttribute("captionXPadding#disabled");
				nCaptionYPadding = m_window.getIntegerAttribute("captionYPadding#disabled");
			}
		}

		String sTitle = m_window.getTitle();
		if (sTitle != null) {
			gl.glDisable(GL.GL_TEXTURE_2D);
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			gl.glBegin(GL.GL_QUADS);

			// Title bar
			gl.glColor4f(titlebarColor.getRed(), titlebarColor.getGreen(), titlebarColor.getBlue(), 1.0f - fTitlebarTransparancy);
			m_tmpBounds.setBounds(m_window.getCurrentBounds());
			m_tmpBounds.height = m_window.getIntegerAttribute("titlebarHeight");
			RenderHelper.drawRectangle(gl, m_tmpBounds);
	
			gl.glEnd();
			gl.glDisable(GL.GL_BLEND);
	
			// Title text
			int nCaptionAlignment = m_window.getIntegerAttribute("textAlignment");
			GLText.drawText(_context, m_tmpBounds, nCaptionXPadding, nCaptionYPadding, captionFont, captionFontColor, false, nCaptionAlignment, sTitle, "...");

			gl.glEnable(GL.GL_TEXTURE_2D);
		}
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_superRenderer.getMinimalBounds(_context);
	}
}

/*
 * $Log$
 * Revision 1.12  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
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
