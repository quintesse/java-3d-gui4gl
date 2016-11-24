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
 * Created on Nov 21, 2003
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
public class ToggleRenderer implements WidgetRendererModel {

	private Toggle m_toggle;
	private WidgetRendererModel m_superRenderer;
	private boolean m_bReady;
	
	public ToggleRenderer(Widget _widget) {
		m_toggle = (Toggle)_widget;
		m_superRenderer = RenderHelper.findSuperClassRenderer(Toggle.class, m_toggle);
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

		Font captionFont;
		GLColor captionFontColor;
		GLColor checkColor;
		GLColor checkBackgroundColor;
		float fcheckTransparancy;
		
		if (m_toggle.hasFocus()) {
			captionFont = (Font)m_toggle.getAttribute("textFont#focused");
			captionFontColor = (GLColor)m_toggle.getAttribute("textFontColor#focused");
			checkColor = (GLColor)m_toggle.getAttribute("checkColor#focused");
			checkBackgroundColor = (GLColor)m_toggle.getAttribute("checkBackgroundColor#focused");
			fcheckTransparancy = m_toggle.getFloatAttribute("checkTransparancy#focused");
		} else {
			if (m_toggle.isEnabled()) {
				captionFont = (Font)m_toggle.getAttribute("textFont");
				captionFontColor = (GLColor)m_toggle.getAttribute("textFontColor");
				checkColor = (GLColor)m_toggle.getAttribute("checkColor");
				checkBackgroundColor = (GLColor)m_toggle.getAttribute("checkBackgroundColor");
				fcheckTransparancy = m_toggle.getFloatAttribute("checkTransparancy");
			} else {
				captionFont = (Font)m_toggle.getAttribute("textFont#disabled");
				captionFontColor = (GLColor)m_toggle.getAttribute("textFontColor#disabled");
				checkColor = (GLColor)m_toggle.getAttribute("checkColor#disabled");
				checkBackgroundColor = (GLColor)m_toggle.getAttribute("checkBackgroundColor#disabled");
				fcheckTransparancy = m_toggle.getFloatAttribute("checkTransparancy#disabled");
			}
		}

		gl.glDisable(GL2.GL_TEXTURE_2D);

		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_QUADS);

		gl.glColor4f(checkBackgroundColor.getRed(), checkBackgroundColor.getGreen(), checkBackgroundColor.getBlue(), 1.0f - fcheckTransparancy);
		Rectangle bounds = new Rectangle(m_toggle.getInnerBounds());
		RenderHelper.drawRectangle(gl, bounds.x, bounds.y + 1, bounds.height - 2, bounds.height - 2);
		
		if (m_toggle.isChecked()) {
			gl.glColor4f(checkColor.getRed(), checkColor.getGreen(), checkColor.getBlue(), 1.0f - fcheckTransparancy);
			RenderHelper.drawRectangle(gl, bounds.x + 1, bounds.y + 2, bounds.height - 4, bounds.height - 4);
		}

		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		
		String sCaption = m_toggle.getCaption();
		if (sCaption != null) {
			// Caption text
			int nCaptionAlignment = m_toggle.getIntegerAttribute("textAlignment");
			bounds.x += bounds.height + 5;
			GLText.drawText(_context, bounds, 0, 0, captionFont, captionFontColor, true, nCaptionAlignment, sCaption, "...");
		}

		gl.glEnable(GL2.GL_TEXTURE_2D);
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_superRenderer.getMinimalBounds(_context);
	}
}

/*
 * $Log$
 * Revision 1.6  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.5  2004/05/04 21:59:24  tako
 * Now using the new attribute map instead of individual property getters and setters.
 *
 * Revision 1.4  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.3  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.2  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.1  2003/11/21 01:27:22  tako
 * First check-in of the renderer for the new Toggle widget.
 *
 */
