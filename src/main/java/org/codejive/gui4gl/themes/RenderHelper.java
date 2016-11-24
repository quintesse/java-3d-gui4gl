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
import org.codejive.gui4gl.widgets.WidgetBase;

import com.jogamp.opengl.GL2;

/**
 * @author tako
 * @version $Revision: 361 $
 */
public class RenderHelper {
	
	public static void drawRectangle(GL2 _gl, Rectangle _rect) {
		drawRectangle(_gl, _rect.x, _rect.y, _rect.width, _rect.height);
	}
	
	public static void drawRectangle(GL2 _gl, int _left, int _top, int _width, int _height) {
		_gl.glTexCoord2f(0.0f, 1.0f);
		_gl.glVertex2f(_left, _top);
		_gl.glTexCoord2f(0.0f, 0.0f);
		_gl.glVertex2f(_left, _top + _height);
		_gl.glTexCoord2f(1.0f, 0.0f);
		_gl.glVertex2f(_left + _width, _top + _height);
		_gl.glTexCoord2f(1.0f, 1.0f);
		_gl.glVertex2f(_left + _width, _top);
	}

	@SuppressWarnings("unchecked")
	protected static Class<WidgetRendererModel> findSuperClassRendererClass(Class<? extends Widget> _widgetClass, Widget _widget) {
		Class<WidgetRendererModel> rendererClass = null;
		Class superClass = _widgetClass.getSuperclass();
		if (superClass != null) {
			rendererClass = (Class<WidgetRendererModel>)Theme.getValue(_widget, superClass, "renderer");
			if (rendererClass == null) {
				rendererClass = findSuperClassRendererClass(superClass, _widget);
			}
		}
		return rendererClass;
	}

	public static WidgetRendererModel findSuperClassRenderer(Class<? extends Widget> _widgetClass, Widget _widget) {
		WidgetRendererModel renderer = null;
		Class<WidgetRendererModel> rendererClass = findSuperClassRendererClass(_widgetClass, _widget);
		if (rendererClass != null) {
			renderer = WidgetBase.createRenderer(rendererClass, _widget);
		}
		return renderer;
	}
}

/*
 * $Log$
 * Revision 1.11  2004/10/17 11:10:46  tako
 * Removed updateRenderingSuperClass().
 * Added getMinimalBoundsSuperClass().
 *
 * Revision 1.10  2004/05/04 22:30:23  tako
 * Theme attribute getters and setters now also take a Widget as argument.
 * This to support the Widget's new attribute maps.
 *
 * Revision 1.9  2004/01/27 13:29:51  steven
 * image widget patch by gertjan
 *
 * Revision 1.8  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
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
