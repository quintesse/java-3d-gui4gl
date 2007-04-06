/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Steven Lagerweij, Tako Schotanus
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
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import javax.media.opengl.GL;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.RenderObserver;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

/**
 * @author steven
 * @version $Revision: 365 $
 */
public class TextFieldRenderer implements WidgetRendererModel {
	private TextField m_textField;
	private WidgetRendererModel m_superRenderer;
	private boolean m_bReady;
	private int m_nLastCursorPos = -1;
	private long m_lTimeOfLastChange = 0;
	
	public TextFieldRenderer(Widget _widget) {
		m_textField = (TextField)_widget;
		m_superRenderer = RenderHelper.findSuperClassRenderer(TextField.class, m_textField);
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

		Font textFont;
		GLColor textFontColor;
		if(m_textField.hasFocus()) {
			textFont = (Font)m_textField.getAttribute("textFont#focused");
			textFontColor = (GLColor)m_textField.getAttribute("textFontColor#focused");
		} else {
			if (m_textField.isEnabled()) {
				textFont = (Font)m_textField.getAttribute("textFont");
				textFontColor = (GLColor)m_textField.getAttribute("textFontColor");
			} else {
				textFont = (Font)m_textField.getAttribute("textFont#disabled");
				textFontColor = (GLColor)m_textField.getAttribute("textFontColor#disabled");
			}
		}

		// The cursor blinks, so let's check if we should draw it or not
		if (m_nLastCursorPos != m_textField.getCursorPos()) {
			m_nLastCursorPos = m_textField.getCursorPos();
			m_lTimeOfLastChange = System.currentTimeMillis();
		}
		int spd = m_textField.getIntegerAttribute("cursorBlinkSpeed");
		boolean drawCursor = m_textField.hasFocus() && ((spd == 0) || (System.currentTimeMillis() - m_lTimeOfLastChange) % spd < (spd/2));
		GLColor cursorColor = (GLColor)m_textField.getAttribute("cursorColor");
		
		gl.glDisable(GL.GL_TEXTURE_2D);
		drawTextWithCursor(_context, m_textField.getInnerBounds(), 0, 0, textFont, textFontColor, drawCursor, cursorColor, m_textField);
		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	private static void drawTextWithCursor(RenderContext _context, Rectangle _bounds, int _nXPadding, int _nYPadding, Font _font, GLColor _color, boolean _bDrawCursor, GLColor _cursorColor, TextField _textField) {
		GL gl = _context.getGl();

		float fFontHeight = _font.getSize(_context);
		float fFontBaseLine = _font.getBaseLine(_context);
		float fFontPadding = fFontHeight - fFontBaseLine;
		float fXPos = _bounds.x + _nXPadding;
		float fYPos = _bounds.y + _nYPadding + fFontBaseLine + fFontPadding;

		float fCursorXPos = fXPos;
		float fCursorWidth = 1;
		
		float fWidth = _bounds.width - 2 * _nXPadding - fCursorWidth; // make sure cursor always has room.
		
		gl.glColor3fv(_color.toArray3f(), 0);

		// note that the viewoffset may not be enough to show the cursor, which we adjust for here, since only here we know what fits.
		int nViewOffset = _textField.getViewOffset();
		
		int nCursorPos = _textField.getCursorPos();
		// at least skip anything before the viewoffset
		String sText = _textField.getText();

		int l = sText.length();
		int p = nCursorPos;
		
		float fLength  = 0;
		StringBuffer s = new StringBuffer(l);
		// left from cursor first
		while((p > 0) && (p > nViewOffset)) {
			p--;
			s.insert(0, sText.charAt(p));
			fLength = _font.getTextWidth(_context, s.toString());
			if(fLength > fWidth) {
				s.deleteCharAt(0);
				fLength = _font.getTextWidth(_context, s.toString());
				p++;
				break;
			}
		}
		fCursorXPos = fXPos + fLength;
		_textField.setViewOffset(p);
		
		gl.glRasterPos2f(fXPos, fYPos);
		_font.renderText(_context, s.toString());
		
		s = new StringBuffer(l);
		float fRightWidth = fWidth - fLength;
		// right from cursor if any room left
		if(fRightWidth > 0) {
			p = nCursorPos;
			while(p < l) {
				s.append(sText.charAt(p));
				fLength = _font.getTextWidth(_context, s.toString());
				if(fLength > fRightWidth) {
					s.deleteCharAt(s.length() - 1);
					break;
				}
				p++;
			}
			gl.glRasterPos2f(fCursorXPos, fYPos);
			_font.renderText(_context, s.toString());
		}
		
		// Only show a cursor if we have focus
		if(_bDrawCursor) {
			// Draw cursor
			gl.glColor4f(_cursorColor.getRed(), _cursorColor.getGreen(), _cursorColor.getBlue(), _cursorColor.getAlpha());
			gl.glBegin(GL.GL_QUADS);
			RenderHelper.drawRectangle(_context.getGl(), (int)fCursorXPos, (int)(fYPos - fFontHeight), (int)fCursorWidth, (int)(fFontHeight + fFontPadding));
			gl.glEnd();
		}
	}

	public Rectangle getMinimalBounds(RenderContext _context) {
		return m_superRenderer.getMinimalBounds(_context);
	}
}

/*
 * $Log$
 * Revision 1.8  2004/10/17 11:09:51  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.7  2004/05/04 21:59:24  tako
 * Now using the new attribute map instead of individual property getters and setters.
 *
 * Revision 1.6  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.5  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.4  2003/11/24 16:50:43  tako
 * Implemented updateRendering().
 *
 * Revision 1.3  2003/11/23 02:00:33  tako
 * Made the propety handling and naming a bit more like the standard.
 *
 * Revision 1.2  2003/11/21 10:48:39  steven
 * should not have used padding in combination with innerbounds
 *
 * Revision 1.1  2003/11/21 10:01:29  steven
 * A renderer for the TextField class
 *
 */
