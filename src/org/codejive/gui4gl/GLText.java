/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl;

import java.awt.Rectangle;

import org.codejive.gui4gl.fonts.Font;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

import net.java.games.jogl.GL;

/**
 * @author tako
 * @version $Revision: 48 $
 */
public class GLText {
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;
		
	public static void drawText(RenderContext _context, Rectangle _bounds, int _nPadding, Font _font, GLColor _color, boolean _bMultiLine, int _nAlignment, String _sText) {
		GL gl = _context.getGl();

		float fFontHeight = _font.getSize(_context);
		float fFontBaseLine = _font.getBaseLine(_context);
		float fFontPadding = fFontHeight - fFontBaseLine;
		float fXPos = _bounds.x + _nPadding;
		float fYPos = _bounds.y + _nPadding + fFontBaseLine + fFontPadding;
		float fXPosDelta = 0.0f;
		float fYPosDelta = 0.0f;
		float fWidth = _bounds.width - 2 * _nPadding;
		float fHeight = _bounds.height - 2 * _nPadding;
		
		// Determine if the text will fit the bounds
		gl.glColor3fv(_color.toArray3f());
		int p = 0;
		int l = _sText.length();
		// Continue while there are still characters and vertical space left
		while ((p < l) && ((fYPosDelta + fFontHeight) < fHeight)) {
			while ((p < l) && (_sText.charAt(p) == ' ')) {
				p++;
			}
			if (p < l) {
				StringBuffer s = new StringBuffer();
				s.append(_sText.charAt(p++));
				float fLength = _font.getTextWidth(_context, s.toString());
				// Continue adding character while there is still horizontal space left
				while ((p < l) && (fLength < fWidth)) {
					char c = _sText.charAt(p++);
					s.append(c);
					fLength = _font.getTextWidth(_context, s.toString());
				}
				// Remove last character if we crossed the boundary
				if (fLength > fWidth) {
					s.deleteCharAt(s.length() - 1);
					p--;
				}
				// Determine if this is the last line that will be able to fit
				boolean bLastLine = (!_bMultiLine || ((fYPosDelta + 2 * fFontHeight + fFontPadding) >= fHeight));
				if (bLastLine) {
					if (p < l) {
						// Let's shorten the fragment even more until ... fits at the end
						fLength = _font.getTextWidth(_context, s.toString() + "...");
						while ((s.length() > 0) && (fLength > fWidth)) {
							s.deleteCharAt(s.length() - 1);
							fLength = _font.getTextWidth(_context, s.toString() + "...");
						}
						s.append("...");
					}
				} else {
					if (inWord(_sText, p)) {
						// Did we encounter a word boundary somewhere in the fragment?
						int nLastBreak = s.lastIndexOf(" ");
						if (nLastBreak >= 0) {
							// Shorten the string to the last word boundary
							p -= s.length() - nLastBreak - 1;
							s.delete(nLastBreak, s.length());
						}
					}
				}
				// Strip any spaces from the fragment and calculate its final length
				String sFinal = s.toString().trim();
				fLength = _font.getTextWidth(_context, sFinal);
				// Render the text fragment
				switch (_nAlignment) {
					case ALIGN_LEFT:
						break;
					case ALIGN_CENTER:
						fXPosDelta = (fWidth - fLength) / 2;
						break;
					case ALIGN_RIGHT:
						fXPosDelta = fWidth - fLength;
						break;
				}
				gl.glRasterPos2f(fXPos + fXPosDelta, fYPos + fYPosDelta);
				_font.renderText(_context, sFinal);
				// Move down one line
				fYPosDelta += fFontHeight + fFontPadding;
			}
		}
	}
	
	private static boolean inWord(String _sText, int _nPos) {
		return ((_nPos >= 0) && (_nPos < _sText.length()) && (_sText.charAt(_nPos) != ' ') && (_sText.charAt(_nPos-1) != ' '));
	}
}

/*
 * $Log$
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
