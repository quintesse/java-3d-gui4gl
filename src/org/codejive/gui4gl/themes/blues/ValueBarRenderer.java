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

import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.RenderHelper;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.widgets.ValueBar;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

/**
 * @author steven
 * @version $Revision: 203 $
 *
 */
public class ValueBarRenderer implements WidgetRendererModel {
	private Font m_textFont;
	private GLColor m_textFontColor;
	
	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(ValueBar.class, _widget, _context);
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

		
		int textXPos = 0;
		int textYPos = 0;
		
		String sValueAsString = "";
		if(bar.isShowValue()) {
			setFontAndFontColor(bar);
			
			sValueAsString = String.valueOf(bar.getValue());
		}
		
		
		if(barRect.height > barRect.width) {
			
			if(bar.isShowValue()) {
				int h = (int)m_textFont.getSize(_context);
				switch(bar.getAlignment()) {
					case GLText.ALIGN_LEFT : // = top
						textYPos = top + h;
						height -= h;
						break;
					case GLText.ALIGN_RIGHT : // = bottom
						textYPos = top + height;
						top -= h;
						height -= h;
						break;
					case GLText.ALIGN_CENTER : // equals to top
						textYPos = top + height / 2 + h/2;
						break;
				}
				textXPos = left + width/2 - (int)(m_textFont.getTextWidth(_context, sValueAsString) / 2);
			}
			
			
			height = getPixelValueForBar(bar, height);
			top += (barRect.height - height);
		} else {
			
			if(bar.isShowValue()) {
				int w = Math.max(
					(int)m_textFont.getTextWidth(_context, String.valueOf(bar.getMinValue())),
					(int)m_textFont.getTextWidth(_context, String.valueOf(bar.getMaxValue()))
				);
				
				switch(bar.getAlignment()) {					
					case GLText.ALIGN_LEFT :
						textXPos = left + w - (int)m_textFont.getTextWidth(_context, String.valueOf(bar.getValue()));
						width -= w;
						left += w;
						break;
					case GLText.ALIGN_RIGHT :
						textXPos = left + width - w;
						width -= w;
						break;
					case GLText.ALIGN_CENTER :
						textXPos = left + width/2 - (int)(m_textFont.getTextWidth(_context, String.valueOf(bar.getValue())) / 2);
						break;
				}
				textYPos = top - 1 + height / 2 + (int)(m_textFont.getSize(_context)/2f);
			}
			
			width = getPixelValueForBar(bar, width);
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
		
		gl.glColor3fv(m_textFontColor.toArray3f());
		
		if(bar.isShowValue()) {
			gl.glRasterPos2i(textXPos, textYPos);
			m_textFont.renderText(_context, sValueAsString);
		}
		
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
	
	private int getPixelValueForBar(ValueBar _bar, int _maxWidth) {
		float v = _bar.getValue() - _bar.getMinValue();
		float w = _bar.getMaxValue() - _bar.getMinValue();
		
		int o = (int)(_maxWidth / w * v);

		return o >= 0 ? (o <= _maxWidth ? o:_maxWidth) : 0;
	}
	
	private void setFontAndFontColor(ValueBar _bar) {

		if (_bar.hasFocus()) {
			m_textFont = _bar.getFocusedTextFont();
			m_textFontColor = _bar.getFocusedTextFontColor();
		} else {
			if (_bar.isEnabled()) {
				m_textFont = _bar.getTextFont();
				m_textFontColor = _bar.getTextFontColor();
			} else {
				m_textFont = _bar.getDisabledTextFont();
				m_textFontColor = _bar.getDisabledTextFontColor();
			}
		}		
	}

}
/*
 * $Log$
 * Revision 1.10  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.9  2003/12/14 00:28:10  steven
 * added some very basic support for showing the bar value in both horiz and vert. mode.
 * Still to be done is rendering the background for the bar ourselves.
 *
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
