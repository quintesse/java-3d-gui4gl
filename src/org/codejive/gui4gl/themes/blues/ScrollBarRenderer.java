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
 * @version $Revision: 240 $
 */
public class ScrollBarRenderer implements WidgetRendererModel {
	
	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(ScrollBar.class, _widget, _context);
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		RenderHelper.updateSuperClass(ScrollBar.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(ScrollBar.class, _widget, _context);

		GL gl = _context.getGl();

		ScrollBar.InnerBar bar = (ScrollBar.InnerBar)_widget;
		
		Rectangle barRect = _widget.getInnerBounds();
		
		int left = barRect.x;
		int top = barRect.y;
		int height = barRect.height;
		int width = barRect.width;

		int orientation = bar.getActualOrientation();
		if (orientation == Orientation.VERTICAL) {
			top = barRect.y + (int)(bar.getStartValue() * barRect.height);
			height = (int)((bar.getEndValue() - bar.getStartValue()) * barRect.height);
		} else {
			left = barRect.x + (int)(bar.getStartValue() * barRect.width);
			width = (int)((bar.getEndValue() - bar.getStartValue()) * barRect.width);
		}

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
		RenderHelper.drawRectangle(gl, left, top, width, height);

		gl.glEnd();
		
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
}


/*
 * $Log$
 * Revision 1.1  2004/05/04 22:07:57  tako
 * First check-in of a new widget that implements a scroll bar.
 *
 */