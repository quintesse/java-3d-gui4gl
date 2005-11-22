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
 * Created on Oct 6, 2004
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL;

import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 * @version $Revision:	$
 */
public class ListBoxRenderer implements WidgetRendererModel {
	private ListBox m_box;
	private WidgetRendererModel m_superRenderer;
	private boolean m_bReady;
	
	public ListBoxRenderer(Widget _widget) {
		m_box = (ListBox)_widget;
		m_superRenderer = RenderHelper.findSuperClassRenderer(ListBox.class, m_box);
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

		gl.glDisable(GL.GL_TEXTURE_2D);

		Iterator items = m_box.getItems();
		if (items != null) {
			Rectangle bounds = m_box.getInnerBounds();
			int x = (int)bounds.getX();
			int y = (int)bounds.getY();
			int w = (int)bounds.getWidth();
			while (items.hasNext()) {
				Widget item = (Widget)items.next();
				WidgetRendererModel renderer = item.getRenderer();
				Rectangle itemBounds = renderer.getMinimalBounds(_context);
				item.setBounds(0, 0, 100, 50);
				renderer.render(_context, _observer);
				y += itemBounds.height;
			}
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_superRenderer.getMinimalBounds(_context);
	}
}

/*
 * $Log:	$
 */
