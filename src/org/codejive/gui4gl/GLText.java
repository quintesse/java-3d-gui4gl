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
 * @version $Revision: 53 $
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
			// Skip any leading spaces
			while ((p < l) && (_sText.charAt(p) == ' ')) {
				p++;
			}
			if (p < l) {
				StringBuffer s = new StringBuffer();
				// Add the next word to the string buffer
				int p2 = _sText.indexOf(' ', p);
				if (p2 > p) {
					s.append(_sText.substring(p, p2));
					p = p2 + 1;
				} else {
					s.append(_sText.substring(p));
					p = l;
				}
				float fLength = _font.getTextWidth(_context, s.toString());
				// Continue adding words while there is still horizontal space left
				while ((p < l) && (fLength < fWidth)) {
					s.append(' ');
					p2 = _sText.indexOf(' ', p);
					if (p2 > p) {
						s.append(_sText.substring(p, p2));
						p = p2 + 1;
					} else {
						s.append(_sText.substring(p));
						p = l;
					}
					fLength = _font.getTextWidth(_context, s.toString());
				}
				// Check if the text fragment crossed the boundary
				if (fLength > fWidth) {
					// Determine if this is the last line that will be able to fit
					boolean bLastLine = (!_bMultiLine || ((fYPosDelta + 2 * fFontHeight + fFontPadding) >= fHeight));
					if (bLastLine) {
						// Let's shorten the fragment even more until "..." fits at the end
						while ((s.length() > 0) && (fLength > fWidth)) {
							s.deleteCharAt(s.length() - 1);
							fLength = _font.getTextWidth(_context, s.toString() + "...");
						}
						s.append("...");
					} else {
						// Did we encounter a word boundary somewhere in the fragment?
						int nLastBreak = s.lastIndexOf(" ");
						if (nLastBreak >= 0) {
							// Shorten the string to the last word boundary
							p -= s.length() - nLastBreak;
							s.delete(nLastBreak, s.length());
						} else {
							// Let's shorten the fragment until it fits again
							fLength = _font.getTextWidth(_context, s.toString());
							while ((s.length() > 0) && (fLength > fWidth)) {
								s.deleteCharAt(s.length() - 1);
								p--;
								fLength = _font.getTextWidth(_context, s.toString());
							}
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
}

/*
 * $Log$
 * Revision 1.4  2003/11/18 11:02:38  tako
 * Removed unused code.
 *
 * Revision 1.3  2003/11/18 11:02:13  tako
 * Improved rendering speed quite a bit.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
