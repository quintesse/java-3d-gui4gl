/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.widgets;

import java.util.List;

/**
 * @author tako
 */
public interface AbstractContainer {
	public abstract void add(Widget _child);
	public abstract List getChildren();
	public abstract AbstractWidget getFocusWidget();
	public abstract void setFocusWidget(Widget _widget);
	public abstract AbstractWidget getPreviousFocusWidget(AbstractWidget _widget);
	public abstract AbstractWidget getNextFocusWidget(AbstractWidget _widget);
}