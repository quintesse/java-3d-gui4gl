/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.fonts;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 48 $
 */
public interface Font {
	public float getSize(RenderContext _context);
	public float getBaseLine(RenderContext _context);
	public float getTextWidth(RenderContext _context, String _sText);
	public void renderText(RenderContext _context, String _sText);
}

/*
 * $Log$
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
