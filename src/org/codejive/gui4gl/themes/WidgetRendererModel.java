/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes;

import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 */
public interface AbstractWidgetRenderer {
	public void initRendering(AbstractWidget _widget, RenderContext _context); 
	public void render(AbstractWidget _widget, RenderContext _context);
}
