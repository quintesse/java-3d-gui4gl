/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.themes;

import java.awt.Rectangle;

import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.RenderContext;

import net.java.games.jogl.GL;

/**
 * @author tako
 * @version $Revision: 88 $
 */
public class RenderHelper {
	
	public static void drawRectangle(GL _gl, Rectangle _rect) {
		drawRectangle(_gl, _rect.x, _rect.y, _rect.width, _rect.height);
	}
	
	public static void drawRectangle(GL _gl, int _left, int _top, int _width, int _height) {
		_gl.glTexCoord2f(0.0f, 0.0f);
		_gl.glVertex2f(_left, _top);
		_gl.glTexCoord2f(0.0f, 1.0f);
		_gl.glVertex2f(_left, _top + _height);
		_gl.glTexCoord2f(1.0f, 1.0f);
		_gl.glVertex2f(_left + _width, _top + _height);
		_gl.glTexCoord2f(1.0f, 0.0f);
		_gl.glVertex2f(_left + _width, _top);
	}

	protected static WidgetRendererModel findFirstSuperClassRenderer(Class _widgetClass) {
		WidgetRendererModel renderer = null;
		Class superClass = _widgetClass.getSuperclass();
		if (superClass != null) {
			renderer = (WidgetRendererModel)Theme.getValue(superClass, "renderer");
			if (renderer == null) {
				renderer = findFirstSuperClassRenderer(superClass);
			}
		}
		return renderer;
	}
	
	public static void initSuperClass(Class _widgetClass, Widget _widget, RenderContext _context) {
		WidgetRendererModel renderer = findFirstSuperClassRenderer(_widgetClass);
		if (renderer != null) {
			renderer.initRendering(_widget, _context);
		}
	}

	public static void renderSuperClass(Class _widgetClass, Widget _widget, RenderContext _context) {
		WidgetRendererModel renderer = findFirstSuperClassRenderer(_widgetClass);
		if (renderer != null) {
			renderer.render(_widget, _context);
		}
	}	
}

/*
 * $Log$
 * Revision 1.5  2003/11/19 10:01:55  steven
 * added a drawRectangle with primitive arguments to prevent unnecessary Rectangle creation in render methods
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
