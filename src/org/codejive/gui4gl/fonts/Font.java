/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.fonts;

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 */
public interface Font {
	public float getSize(RenderContext _context);
	public float getBaseLine(RenderContext _context);
	public float getTextWidth(RenderContext _context, String _sText);
	public void renderText(RenderContext _context, String _sText);
}
