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

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;
import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.*;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

import com.jogamp.opengl.GL2;

/**
 * @author tako
 * @version $Revision: 322 $
 */
public class TextRenderer implements WidgetRendererModel {

	private Text m_text;
	private WidgetRendererModel m_superRenderer;
	private boolean m_bReady;
	
	public TextRenderer(Widget _widget) {
		m_text = (Text)_widget;
		m_superRenderer = RenderHelper.findSuperClassRenderer(Text.class, m_text);
		assert(m_superRenderer != null);
		m_bReady = false;
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

		GL2 gl = _context.getGl();

		Font textFont;
		GLColor textFontColor;
		if(m_text.hasFocus()) {
			textFont = (Font)m_text.getAttribute("textFont#focused");
			textFontColor = (GLColor)m_text.getAttribute("textFontColor#focused");
		} else {
			if (m_text.isEnabled()) {
				textFont = (Font)m_text.getAttribute("textFont");
				textFontColor = (GLColor)m_text.getAttribute("textFontColor");
			} else {
				textFont = (Font)m_text.getAttribute("textFont#disabled");
				textFontColor = (GLColor)m_text.getAttribute("textFontColor#disabled");
			}
		}
		int nTextAlignment = m_text.getIntegerAttribute("textAlignment");
		// TODO Decide if this should be a widget property instead of a render attribute
		//int nTextAlignment = text.getTextAlignment();

		gl.glDisable(GL2.GL_TEXTURE_2D);

		String sText = m_text.getText();
		if (sText != null) {
			// Caption text			
			GLText.drawText(_context, m_text.getInnerBounds(), 0, 0, textFont, textFontColor, true, nTextAlignment, sText, "...");
		}

		gl.glEnable(GL2.GL_TEXTURE_2D);
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_superRenderer.getMinimalBounds(_context);
	}
}

/*
 * $Log$
 * Revision 1.13  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.12  2004/05/04 21:59:24  tako
 * Now using the new attribute map instead of individual property getters and setters.
 *
 * Revision 1.11  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.10  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.9  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.8  2003/11/21 10:00:51  steven
 * Moved editing support to a different class
 *
 * Revision 1.7  2003/11/21 00:19:18  tako
 * Minor code change because GLText signature was changed.
 *
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
