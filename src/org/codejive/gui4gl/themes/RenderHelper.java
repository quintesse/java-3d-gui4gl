/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.themes;

import java.awt.Rectangle;

import org.codejive.gui4gl.widgets.AbstractWidget;
import org.codejive.utils4gl.RenderContext;

import net.java.games.jogl.GL;

/**
 * @author tako
 */
public class RenderHelper {
	
	public static void drawRectangle(GL _gl, Rectangle _rect) {
		_gl.glTexCoord2f(0.0f, 0.0f);
		_gl.glVertex2f(_rect.x, _rect.y);
		_gl.glTexCoord2f(0.0f, 1.0f);
		_gl.glVertex2f(_rect.x, _rect.y + _rect.height);
		_gl.glTexCoord2f(1.0f, 1.0f);
		_gl.glVertex2f(_rect.x + _rect.width, _rect.y + _rect.height);
		_gl.glTexCoord2f(1.0f, 0.0f);
		_gl.glVertex2f(_rect.x + _rect.width, _rect.y);
	}

	protected static AbstractWidgetRenderer findFirstSuperClassRenderer(Class _widgetClass) {
		AbstractWidgetRenderer renderer = null;
		Class superClass = _widgetClass.getSuperclass();
		if (superClass != null) {
			renderer = (AbstractWidgetRenderer)Theme.getValue(superClass, "renderer");
			if (renderer == null) {
				renderer = findFirstSuperClassRenderer(superClass);
			}
		}
		return renderer;
	}
	
	public static void initSuperClass(Class _widgetClass, AbstractWidget _widget, RenderContext _context) {
		AbstractWidgetRenderer renderer = findFirstSuperClassRenderer(_widgetClass);
		if (renderer != null) {
			renderer.initRendering(_widget, _context);
		}
	}

	public static void renderSuperClass(Class _widgetClass, AbstractWidget _widget, RenderContext _context) {
		AbstractWidgetRenderer renderer = findFirstSuperClassRenderer(_widgetClass);
		if (renderer != null) {
			renderer.render(_widget, _context);
		}
	}	
}
