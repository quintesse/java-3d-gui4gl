/*
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
 * @version $Revision: 62 $
 *
 */
public class ValueBarRenderer implements WidgetRendererModel {

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.themes.WidgetRendererModel#initRendering(org.codejive.gui4gl.widgets.Widget, org.codejive.utils4gl.RenderContext)
	 */
	public void initRendering(Widget _widget, RenderContext _context) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.codejive.gui4gl.themes.WidgetRendererModel#render(org.codejive.gui4gl.widgets.Widget, org.codejive.utils4gl.RenderContext)
	 */
	public void render(Widget _widget, RenderContext _context) {
		// TODO Auto-generated method stub
		RenderHelper.renderSuperClass(ValueBar.class, _widget, _context);

		GL gl = _context.getGl();

		ValueBar bar = (ValueBar)_widget;
		
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		GLColor bg = bar.getFillColor();
		gl.glBegin(GL.GL_QUADS);

		if(bg != null) {
			gl.glColor4f(bg.getRed(), bg.getGreen(), bg.getBlue(), 1.0f);			
			RenderHelper.drawRectangle(gl, _widget.getBounds());
		}
			
		Rectangle barRect = new Rectangle(_widget.getBounds());
		
		barRect.y += bar.getYPadding();
		barRect.x += bar.getXPadding();
		if(barRect.height > barRect.width) {
			int maxHeight = barRect.height - bar.getYPadding() * 2;
			barRect.width -= bar.getXPadding() * 2;
			barRect.height = getPixelValueForBar(bar, maxHeight);
			
			barRect.y += (maxHeight - barRect.height);
			
		} else {
			barRect.height -= bar.getYPadding() * 2;
			barRect.width = getPixelValueForBar(bar, barRect.width - bar.getXPadding() * 2);
		}

		GLColor fg = bar.getValueColor();
		gl.glColor4f(fg.getRed(), fg.getGreen(), fg.getBlue(), 1.0f);
		RenderHelper.drawRectangle(gl, barRect);
		
		
		gl.glEnd();
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
	
	private int getPixelValueForBar(ValueBar _bar, int _maxWidth) {
		float v = _bar.getValue() - _bar.getMinValue();
		float w = _bar.getMaxValue() - _bar.getMinValue();
		
		int o = (int) ( ((float)_maxWidth) / w * v);

		return o >= 0 ? (o <= _maxWidth ? o:_maxWidth) : 0;
	}

}
/*
 * $Log$
 * Revision 1.1  2003/11/18 15:59:17  steven
 * Renders the ValueBar widget
 *
 *
 */
