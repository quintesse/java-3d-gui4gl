/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.fonts;

import net.java.games.jogl.util.GLUT;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 48 $
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
		_context.getGlut().glutBitmapString(_context.getGl(), m_nFont, _sText);
	}
}

/*
 * $Log$
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
