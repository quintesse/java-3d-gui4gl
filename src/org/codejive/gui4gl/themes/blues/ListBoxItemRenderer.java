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
 * Created on Oct 19, 2004
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.BitmapFont;
import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.WidgetRendererModel;
import org.codejive.gui4gl.widgets.ListBox;
import org.codejive.gui4gl.widgets.Widget;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;


public class ListBoxItemRenderer implements WidgetRendererModel {
	private ListBox.StringListBoxItem item;
	private boolean m_bReady;
	
	public ListBoxItemRenderer(Widget _widget) {
		item = (ListBox.StringListBoxItem)_widget;
		m_bReady = false;
	}

	public boolean readyForRendering() {
		return m_bReady;
	}
	
	public void initRendering(RenderContext _context) {
		// No initialization necessary
		m_bReady = true;
	}

	public void render(RenderContext _context, RenderObserver _observer) {
		GL gl = _context.getGl();
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		Font textFont = new BitmapFont(GLUT.BITMAP_HELVETICA_12);
		GLColor textFontColor = new GLColor(0.0f, 0.0f, 0.0f);
		int nTextAlignment = GLText.ALIGN_LEFT;
/*		if(text.hasFocus()) {
			textFont = (Font)text.getAttribute("textFont#focused");
			textFontColor = (GLColor)text.getAttribute("textFontColor#focused");
		} else {
			if (text.isEnabled()) {
				textFont = (Font)text.getAttribute("textFont");
				textFontColor = (GLColor)text.getAttribute("textFontColor");
			} else {
				textFont = (Font)text.getAttribute("textFont#disabled");
				textFontColor = (GLColor)text.getAttribute("textFontColor#disabled");
			}
		}
*/
		String sText = item.getText();
		if (sText != null) {
			// Caption text			
			GLText.drawText(_context, item.getInnerBounds(), 0, 0, textFont, textFontColor, true, nTextAlignment, sText, "...");
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		String sText = item.getText();
		Font textFont = new BitmapFont(GLUT.BITMAP_HELVETICA_12);
		int width = (int)textFont.getTextWidth(_context, sText);
		int height = (int)textFont.getSize(_context);
		Rectangle bounds = new Rectangle(width, height);
		return bounds;
	}
}

/*
 * $Log:	$
 */