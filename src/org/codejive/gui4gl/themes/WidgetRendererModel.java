/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes;

import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 * @version $Revision: 48 $
 */
public interface WidgetRendererModel {
	public void initRendering(Widget _widget, RenderContext _context); 
	public void render(Widget _widget, RenderContext _context);
}

/*
 * $Log$
 * Revision 1.3  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
