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
 * @version $Revision: 104 $
 */
public class GLText {
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;
		
	public static void drawText(RenderContext _context, Rectangle _bounds, int _nXPadding, int _nYPadding, Font _font, GLColor _color, boolean _bMultiLine, int _nAlignment, String _sText, String _sPostFix) {
		GL gl = _context.getGl();

		float fFontHeight = _font.getSize(_context);
		float fFontBaseLine = _font.getBaseLine(_context);
		float fFontPadding = fFontHeight - fFontBaseLine;
		float fXPos = _bounds.x + _nXPadding;
		float fYPos = _bounds.y + _nYPadding + fFontBaseLine + fFontPadding;
		float fXPosDelta = 0.0f;
		float fYPosDelta = 0.0f;
		float fWidth = _bounds.width - 2 * _nXPadding;
		float fHeight = _bounds.height - 2 * _nYPadding;
		
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
						// Let's shorten the fragment even more until the PostFix fits at the end
						while ((s.length() > 0) && (fLength > fWidth)) {
							s.deleteCharAt(s.length() - 1);
							fLength = _font.getTextWidth(_context, s.toString() + _sPostFix);
						}
						s.append(_sPostFix);
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
 * Revision 1.6  2003/11/21 00:20:56  tako
 * The "..." at the end of a string that is too long to fit can now be configured
 * to be any text you want (an empty string is also possible).
 *
 * Revision 1.5  2003/11/19 00:06:34  tako
 * Added support for seperate X and Y padding.
 *
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
