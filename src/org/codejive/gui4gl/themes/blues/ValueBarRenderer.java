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
 * @version $Revision: 77 $
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
		
		Rectangle barRect = new Rectangle(_widget.getInnerBounds());
		
		if(barRect.height > barRect.width) {
			int maxHeight = barRect.height;
			barRect.height = getPixelValueForBar(bar, maxHeight);
			barRect.y += (maxHeight - barRect.height);
		} else {
			barRect.width = getPixelValueForBar(bar, barRect.width);
		}

		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);

		GLColor barColor = bar.getBarColor();
		gl.glColor4f(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 1.0f);
		RenderHelper.drawRectangle(gl, barRect);
		
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
 * Revision 1.2  2003/11/19 00:11:19  tako
 * Simplyfied code a bit by making more use of exisiting options in the
 * Widget base class.
 *
 * Revision 1.1  2003/11/18 15:59:17  steven
 * Renders the ValueBar widget
 *
 *
 */
