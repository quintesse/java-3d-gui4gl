/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.widgets;

import java.util.Iterator;

/**
 * @author tako
 */
public interface AbstractContainer extends AbstractWidget {
	public abstract void add(AbstractWidget _child);
	public abstract void add(AbstractWidget _child, String _sName);
	public abstract Iterator getChildren();
	public abstract AbstractWidget getChild(String _sName);
	public abstract AbstractWidget findChild(String _sName);
	public abstract AbstractWidget getFocusWidget();
	public abstract void setFocusWidget(AbstractWidget _widget);
	public abstract AbstractWidget getPreviousFocusWidget(AbstractWidget _widget);
	public abstract AbstractWidget getNextFocusWidget(AbstractWidget _widget);
}