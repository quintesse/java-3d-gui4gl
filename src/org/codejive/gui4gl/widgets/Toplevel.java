/*
 * Created on Nov 21, 2003
 */
package org.codejive.gui4gl.widgets;

import java.util.Iterator;

/**
 * @author Tako
 * @version $Revision: 110 $
 */
public class Toplevel extends Container {

	public Toplevel getToplevel() {
		return this;
	}
	
	public boolean isActive() {
		return (getFocusWidget() != null) && getFocusWidget().hasFocus();
	}

	public void activate() {
		if (isVisible()) {
			if (getFocusWidget() == null) {
				Iterator i = getChildren();
				while (i.hasNext()) {
					Widget w = (Widget)i.next();
					if (w.isFocusable()) {
						w.setFocus();
						break;
					}
				}
			} else {
				getFocusWidget().setFocus();
			}
		}
	}
}

/*
 * $Log$
 * Revision 1.1  2003/11/21 01:30:39  tako
 * First check-in of a new container class that is a child of the Screen
 * widget. From now on Screen only accepts widgets inheriting from this
 * class.
 *
 */