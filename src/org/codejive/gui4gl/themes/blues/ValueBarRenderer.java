/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Steven Lagerweij
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
 * Created on Nov 18, 2003
 * 
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import net.java.games.jogl.GL;

import org.codejive.gui4gl.themes.RenderHelper;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.widgets.ValueBar;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

/**
 * @author steven
 * @version $Revision: 183 $
 *
 */
public class ValueBarRenderer implements WidgetRendererModel {

	public void initRendering(Widget _widget, RenderContext _context) {
		// nothing to do.
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		RenderHelper.updateSuperClass(ValueBar.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(ValueBar.class, _widget, _context);

		GL gl = _context.getGl();

		ValueBar bar = (ValueBar)_widget;
		
		Rectangle barRect = _widget.getInnerBounds();
		
		int left = barRect.x;
		int top = barRect.y;
		int height = barRect.height;
		int width = barRect.width;
		
		if(barRect.height > barRect.width) {
			height = getPixelValueForBar(bar, barRect.height);
			top += (barRect.height - height);
		} else {
			width = getPixelValueForBar(bar, barRect.width);
		}

		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);

		GLColor barColor;
		float barTransparancy;
		if (bar.hasFocus()) {
			barColor = bar.getFocusedBarColor();
			barTransparancy = bar.getFocusedBarTransparancy();
		} else {
			if (bar.isEnabled()) {
				barColor = bar.getBarColor();
				barTransparancy = bar.getBarTransparancy();
			} else {
				barColor = bar.getDisabledBarColor();
				barTransparancy = bar.getDisabledBarTransparancy();
			}
		}
		gl.glColor4f(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 1.0f - barTransparancy);
		RenderHelper.drawRectangle(gl, left, top, width, height);
		
		gl.glEnd();
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
	
	private int getPixelValueForBar(ValueBar _bar, int _maxWidth) {
		float v = _bar.getValue() - _bar.getMinValue();
		float w = _bar.getMaxValue() - _bar.getMinValue();
		
		int o = (int)(_maxWidth / w * v);

		return o >= 0 ? (o <= _maxWidth ? o:_maxWidth) : 0;
	}

}
/*
 * $Log$
 * Revision 1.8  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.7  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.6  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.5  2003/11/19 10:02:15  steven
 * No longer creates a rectangle every render operation
 *
 * Revision 1.4  2003/11/19 00:49:49  tako
 * Now using correct transparancy properties for the bar.
 *
 * Revision 1.3  2003/11/19 00:29:18  tako
 * Added support to render the bar with transparancy.
 *
 * Revision 1.2  2003/11/19 00:11:19  tako
 * Simplyfied code a bit by making more use of exisiting options in the
 * Widget base class.
 *
 * Revision 1.1  2003/11/18 15:59:17  steven
 * Renders the ValueBar widget
 *
 *
 */
