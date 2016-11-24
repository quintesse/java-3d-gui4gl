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
 * Created on Nov 3, 2003
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;
import org.codejive.utils4gl.textures.Texture;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.themes.RenderHelper;
import org.codejive.gui4gl.widgets.*;

import com.jogamp.opengl.GL2;

/**
 * @author tako
 * @version $Revision: 361 $
 */
public class WidgetRenderer implements WidgetRendererModel {

	private Widget m_widget;
	private boolean m_bReady;
	
	public WidgetRenderer(Widget _widget) {
		m_widget = _widget;
		m_bReady = false;
	}

	public boolean readyForRendering() {
		return m_bReady;
	}

	public void initRendering(RenderContext _context) {
		// No code necessary
		m_bReady = true;
	}

	public void render(RenderContext _context, RenderObserver _observer) {
		GL2 gl = _context.getGl();

		GLColor backgroundColor;
		float fTransparancy;
		Texture backgroundImage;

		if (m_widget.hasFocus()) {
			backgroundColor = (GLColor)m_widget.getAttribute("backgroundColor#focused");
			fTransparancy = m_widget.getFloatAttribute("transparancy#focused");
			backgroundImage = (Texture)m_widget.getAttribute("backgroundImage#focused");
		} else {
			if (m_widget.isEnabled()) {
				backgroundColor = (GLColor)m_widget.getAttribute("backgroundColor");
				fTransparancy = m_widget.getFloatAttribute("transparancy");
				backgroundImage = (Texture)m_widget.getAttribute("backgroundImage");
			} else {
				backgroundColor = (GLColor)m_widget.getAttribute("backgroundColor#disabled");
				fTransparancy = m_widget.getFloatAttribute("transparancy#disabled");
				backgroundImage = (Texture)m_widget.getAttribute("backgroundImage#disabled");
			}
		}
		
		if (backgroundImage == null) {
			gl.glDisable(GL2.GL_TEXTURE_2D);
		} else {
			gl.glEnable(GL2.GL_TEXTURE_2D);
			backgroundImage.bind();
		}
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_QUADS);

		// Widget background
		if (backgroundColor != null) {
			gl.glColor4f(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 1.0f - fTransparancy);
		} else {
			gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - fTransparancy);
		}
		RenderHelper.drawRectangle(gl, m_widget.getCurrentBounds());

		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_widget.getBounds();
	}

}

/*
 * $Log$
 * Revision 1.10  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.9  2004/05/04 21:59:24  tako
 * Now using the new attribute map instead of individual property getters and setters.
 *
 * Revision 1.8  2004/03/07 18:25:48  tako
 * Fixed problem that backgroundColor was not allowed to be null.
 * The backgroundImage is now properly bound.
 *
 * Revision 1.7  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.6  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.5  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
