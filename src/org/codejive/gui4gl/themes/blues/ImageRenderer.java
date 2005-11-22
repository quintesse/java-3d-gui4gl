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

import java.awt.Rectangle;

import javax.media.opengl.GL;

import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.widgets.Image;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;
import org.codejive.gui4gl.themes.RenderHelper;

/**
 * @author gertjan
 * @version $Revision: 322 $
 */
public class ImageRenderer implements WidgetRendererModel {

	private Image m_image;
	private WidgetRendererModel m_superRenderer;
	private boolean m_bReady;
	
	public ImageRenderer(Widget _widget) {
		m_image = (Image)_widget;
		m_superRenderer = RenderHelper.findSuperClassRenderer(Image.class, m_image);
		assert(m_superRenderer != null);
		m_bReady = false;
	}

	public boolean readyForRendering() {
		return m_bReady;
	}

	public void initRendering(RenderContext _context) {
		m_superRenderer.initRendering(_context);
		m_bReady = true;
	}

	public void render(RenderContext _context, RenderObserver _observer) {
		m_superRenderer.render(_context, _observer);

		GL gl = _context.getGl();
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_TEXTURE_2D);

		if (m_image.getImage() != null) {
			m_image.getImage().bind();
		}
		
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		gl.glBegin(GL.GL_QUADS);
		RenderHelper.drawRectangle(gl, m_image.getInnerBounds());
		gl.glEnd();
		
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_BLEND);
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_superRenderer.getMinimalBounds(_context);
	}
}

/*
 * $Log$
 * Revision 1.4  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.3  2004/03/17 00:45:55  tako
 * Now uses the new Texture system.
 *
 * Revision 1.2  2004/03/07 18:27:01  tako
 * The methods now properly call their "super class" implementations.
 *
 * Revision 1.1  2004/01/27 13:29:30  steven
 * image widget patch by gertjan
 *
 */
