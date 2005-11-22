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
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.fonts;

import com.sun.opengl.utils.GLUT;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 301 $
 */
public class BitmapFont implements Font {
	private int m_nFont;
	
	public BitmapFont(int _font) {
		m_nFont = _font;
	}
	
	public float getSize(RenderContext _context) {
		int nSize;
		switch (m_nFont) {
			case GLUT.BITMAP_8_BY_13:
				nSize = 11;
				break;
			case GLUT.BITMAP_9_BY_15:
				nSize = 13;
				break;
			case GLUT.BITMAP_TIMES_ROMAN_10:
				nSize = 9; // Huh?
				break;
			case GLUT.BITMAP_TIMES_ROMAN_24:
				nSize = 22; // Huh?
				break;
			case GLUT.BITMAP_HELVETICA_10:
				nSize = 10;
				break;
			case GLUT.BITMAP_HELVETICA_12:
				nSize = 12;
				break;
			case GLUT.BITMAP_HELVETICA_18:
				nSize = 18;
				break;
			default:
				nSize = 10; // Some sill default value
				break;
		}
		return nSize;
	}

	public float getBaseLine(RenderContext _context) {
		int nBaseLine;
		switch (m_nFont) {
			case GLUT.BITMAP_8_BY_13:
				nBaseLine = 9;
				break;
			case GLUT.BITMAP_9_BY_15:
				nBaseLine = 10;
				break;
			case GLUT.BITMAP_TIMES_ROMAN_10:
				nBaseLine = 7;
				break;
			case GLUT.BITMAP_TIMES_ROMAN_24:
				nBaseLine = 17;
				break;
			case GLUT.BITMAP_HELVETICA_10:
				nBaseLine = 8;
				break;
			case GLUT.BITMAP_HELVETICA_12:
				nBaseLine = 9;
				break;
			case GLUT.BITMAP_HELVETICA_18:
				nBaseLine = 14;
				break;
			default:
			nBaseLine = 10; // Some sill default value
				break;
		}
		return nBaseLine;
	}

	public float getTextWidth(RenderContext _context, String _sText) {
		return _context.getGlut().glutBitmapLength(m_nFont, _sText);
	}
	
	public void renderText(RenderContext _context, String _sText) {
		_context.getGlut().glutBitmapString(m_nFont, _sText);
	}
}

/*
 * $Log$
 * Revision 1.3  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
