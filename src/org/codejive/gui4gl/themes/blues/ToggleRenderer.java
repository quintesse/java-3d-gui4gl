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
			captionFont = (Font)toggle.getAttribute("textFont#focused");
			captionFontColor = (GLColor)toggle.getAttribute("textFontColor#focused");
			checkColor = (GLColor)toggle.getAttribute("checkColor#focused");
			checkBackgroundColor = (GLColor)toggle.getAttribute("checkBackgroundColor#focused");
			fcheckTransparancy = toggle.getFloatAttribute("checkTransparancy#focused");
		} else {
			if (toggle.isEnabled()) {
				captionFont = (Font)toggle.getAttribute("textFont");
				captionFontColor = (GLColor)toggle.getAttribute("textFontColor");
				checkColor = (GLColor)toggle.getAttribute("checkColor");
				checkBackgroundColor = (GLColor)toggle.getAttribute("checkBackgroundColor");
				fcheckTransparancy = toggle.getFloatAttribute("checkTransparancy");
			} else {
				captionFont = (Font)toggle.getAttribute("textFont#disabled");
				captionFontColor = (GLColor)toggle.getAttribute("textFontColor#disabled");
				checkColor = (GLColor)toggle.getAttribute("checkColor#disabled");
				checkBackgroundColor = (GLColor)toggle.getAttribute("checkBackgroundColor#disabled");
				fcheckTransparancy = toggle.getFloatAttribute("checkTransparancy#disabled");
			}
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
			int nCaptionAlignment = toggle.getIntegerAttribute("textAlignment");
			bounds.x += bounds.height + 5;
			GLText.drawText(_context, bounds, 0, 0, captionFont, captionFontColor, true, nCaptionAlignment, sCaption, "...");
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}
}

/*
 * $Log$
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