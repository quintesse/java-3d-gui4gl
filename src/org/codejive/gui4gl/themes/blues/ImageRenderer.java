/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003, 2004 Tako Schotanus, Gertjan Assies
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
 * Created on Jan 26, 2004
 */
package org.codejive.gui4gl.themes.blues;

import net.java.games.jogl.GL;

import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.gui4gl.widgets.Image;
import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.themes.RenderHelper;

/**
 * @author gertjan
 * @version $Revision: 225 $
 */
public class ImageRenderer implements WidgetRendererModel {

	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(Image.class, _widget, _context);
	}

	public void updateRendering(Widget _widget, RenderContext _context) {
		RenderHelper.updateSuperClass(Image.class, _widget, _context);
	}
	
	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(Image.class, _widget, _context);

		GL gl = _context.getGl();
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glBindTexture(GL.GL_TEXTURE_2D, ((Image)_widget).getTextureID());
		
		gl.glBegin(GL.GL_QUADS);

		RenderHelper.drawRectangle(gl, _widget.getCurrentBounds());

		gl.glEnd();
	}
}

/*
 * $Log$
 * Revision 1.2  2004/03/07 18:27:01  tako
 * The methods now properly call their "super class" implementations.
 *
 * Revision 1.1  2004/01/27 13:29:30  steven
 * image widget patch by gertjan
 *
 */
