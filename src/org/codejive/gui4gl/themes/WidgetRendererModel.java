/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes;

import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 */
public interface WidgetRendererModel {
	public void initRendering(Widget _widget, RenderContext _context); 
	public void render(Widget _widget, RenderContext _context);
}
