/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003, 2004 Tako Schotanus
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
 * Created on Mar 31, 2004
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import net.java.games.jogl.GL;

import org.codejive.gui4gl.themes.RenderHelper;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.widgets.Orientation;
import org.codejive.gui4gl.widgets.ScrollBar;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

/**
 * @author Tako
 * @version $Revision: 266 $
 */
public class ScrollBarRenderer implements WidgetRendererModel {
	
	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(ScrollBar.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(ScrollBar.class, _widget, _context);

		GL gl = _context.getGl();

		ScrollBar.InnerBar bar = (ScrollBar.InnerBar)_widget;
		Rectangle handleRect = bar.getHandleBounds();
		
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);

		GLColor barColor;
		float barTransparancy;
		if (bar.hasFocus()) {
			barColor = (GLColor)bar.getAttribute("barColor#focused");
			barTransparancy = bar.getFloatAttribute("barTransparancy#focused");
		} else {
			if (bar.isEnabled()) {
				barColor = (GLColor)bar.getAttribute("barColor");
				barTransparancy = bar.getFloatAttribute("barTransparancy");
			} else {
				barColor = (GLColor)bar.getAttribute("barColor#disabled");
				barTransparancy = bar.getFloatAttribute("barTransparancy#disabled");
			}
		}
		gl.glColor4f(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 1.0f - barTransparancy);
		RenderHelper.drawRectangle(gl, handleRect);

		gl.glEnd();
		
		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	public Rectangle getMinimalBounds(Widget _widget, RenderContext _context) {
		return RenderHelper.getMinimalBoundsSuperClass(ScrollBar.class, _widget, _context);
	}
}


/*
 * $Log$
 * Revision 1.3  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.2  2004/05/07 20:46:04  tako
 * Removed handle rectangle calculations because that info can now be retrieved from the widget.
 *
 * Revision 1.1  2004/05/04 22:07:57  tako
 * First check-in of a new widget that implements a scroll bar.
 *
 */