/*
 * Created on Nov 3, 2003
 */
package org.codejive.gui4gl.themes;

import org.codejive.utils4gl.RenderContext;
import org.codejive.gui4gl.widgets.*;

/**
 * @author tako
 */
public abstract class DefaultWidgetRendererImpl implements AbstractWidgetRenderer {

	/* (non-Javadoc)
	 * @see org.codejive.world3d.gui.WidgetRenderer#initRendering(org.codejive.world3d.gui.Widget, org.codejive.world3d.RenderContext)
	 */
	public void initRendering(AbstractWidget _widget, RenderContext _context) {
		initSuperClass(_widget.getClass(), _widget, _context);
	}

	protected void initSuperClass(Class _widgetClass, AbstractWidget _widget, RenderContext _context) {
		Class superClass = _widgetClass.getSuperclass();
		if (superClass != null) {
			renderSuperClass(superClass, _widget, _context);
		}
		AbstractWidgetRenderer renderer = (AbstractWidgetRenderer)Theme.getValue(superClass, "renderer");
		if (renderer != null) {
			renderer.initRendering(_widget, _context);
		}
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.gui.WidgetRenderer#render(org.codejive.world3d.gui.Widget, org.codejive.world3d.RenderContext)
	 */
	public void render(AbstractWidget _widget, RenderContext _context) {
		renderSuperClass(_widget.getClass(), _widget, _context);
	}

	protected void renderSuperClass(Class _widgetClass, AbstractWidget _widget, RenderContext _context) {
		Class superClass = _widgetClass.getSuperclass();
		if (superClass != null) {
			renderSuperClass(superClass, _widget, _context);
			AbstractWidgetRenderer renderer = (AbstractWidgetRenderer)Theme.getValue(superClass, "renderer");
			if (renderer != null) {
				renderer.render(_widget, _context);
			}
		}
	}
}
