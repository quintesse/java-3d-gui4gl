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
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.themes;

import java.awt.Rectangle;

import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.RenderContext;

import net.java.games.jogl.GL;

/**
 * @author tako
 * @version $Revision: 158 $
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

	public static void updateSuperClass(Class _widgetClass, Widget _widget, RenderContext _context) {
		WidgetRendererModel renderer = findFirstSuperClassRenderer(_widgetClass);
		if (renderer != null) {
			renderer.updateRendering(_widget, _context);
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
 * Revision 1.7  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.6  2003/11/24 16:48:14  tako
 * Added updateSuperClass().
 *
 * Revision 1.5  2003/11/19 10:01:55  steven
 * added a drawRectangle with primitive arguments to prevent unnecessary Rectangle creation in render methods
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
