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
 * @version $Revision: 266 $
 */
public class ButtonRenderer implements WidgetRendererModel {

	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Button.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Button.class, _widget, _context);

		GL gl = _context.getGl();

		Font captionFont;
		GLColor captionFontColor;
		
		Button button = (Button)_widget;
		if (button.isSelected()) {
			captionFont = (Font)button.getAttribute("textFont#selected");
			captionFontColor = (GLColor)button.getAttribute("textFontColor#selected");
		} else {
			if (button.hasFocus()) {
				captionFont = (Font)button.getAttribute("textFont#focused");
				captionFontColor = (GLColor)button.getAttribute("textFontColor#focused");
			} else {
				if (button.isEnabled()) {
					captionFont = (Font)button.getAttribute("textFont");
					captionFontColor = (GLColor)button.getAttribute("textFontColor");
				} else {
					captionFont = (Font)button.getAttribute("textFont#disabled");
					captionFontColor = (GLColor)button.getAttribute("textFontColor#disabled");
				}
			}
		}

		gl.glDisable(GL.GL_TEXTURE_2D);

		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL.GL_QUADS);

		if (button.isSelected()) {
			GLColor selectedBackgroundColor = (GLColor)button.getAttribute("backgroundColor#selected");
			float fselectedTransparancy = button.getFloatAttribute("transparancy#selected");
			gl.glColor4f(selectedBackgroundColor.getRed(), selectedBackgroundColor.getGreen(), selectedBackgroundColor.getBlue(), 1.0f - fselectedTransparancy);
			RenderHelper.drawRectangle(gl, _widget.getCurrentBounds());
		}

		gl.glEnd();
		gl.glDisable(GL.GL_BLEND);
		
		String sCaption = button.getCaption();
		if (sCaption != null) {
			// Caption text
			int nCaptionAlignment = button.getIntegerAttribute("textAlignment");
			GLText.drawText(_context, _widget.getInnerBounds(), 0, 0, captionFont, captionFontColor, true, nCaptionAlignment, sCaption, "...");
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	public Rectangle getMinimalBounds(Widget _widget, RenderContext _context) {
		return RenderHelper.getMinimalBoundsSuperClass(Button.class, _widget, _context);
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
