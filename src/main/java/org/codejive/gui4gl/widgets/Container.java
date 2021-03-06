/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Tako Schotanus
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.event.KeyEvent;
import java.util.Iterator;

import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.layouts.Layouter;

/**
 * This widget is basically a CompoundWidget with the difference that
 * is especially meant for dynamic widgets where the user has full control
 * over the component widgets that get added to it.
 * 
 * @author tako
 * @version $Revision: 361 $
 */
public class Container extends CompoundWidget {
	
	@Override
	public Layouter getLayouter() {
		return super.getLayouter();
	}
	
	@Override
	public void setLayouter(Layouter _layouter) {
		super.setLayouter(_layouter);
	}
	
	@Override
	public void add(Widget _child) {
		super.add(_child);
	}

	@Override
	protected Widget getChild(String _sName) {
		return m_childNames.get(_sName);
	}

	@Override
	public Iterator<Widget> getChildren() {
		return super.getChildren();
	}

	@Override
	public Widget findChild(String _sName) {
		return super.findChild(_sName);
	}

	@Override
	public boolean hasFocus() {
		return false;
	}
	
	@Override
	public void setFocus() {
		// Don't allow this
	}
	
	@Override
	public Widget getFocusWidget() {
		return m_focusWidget;
	}
	
	@Override
	public void processKeyPressedEvent(GuiKeyEvent _event) {
		if (getFocusWidget() != null) {
			Widget w;
			switch (_event.getKeyCode()) {
				case KeyEvent.VK_UP:
					w = getFocusWidget().previousFocus();
					if (w != null) {
						w.setFocus();
					}
					break;
				case KeyEvent.VK_DOWN:
					w = getFocusWidget().nextFocus();
					if (w != null) {
						w.setFocus();
					}
					break;
				default:
					super.processKeyPressedEvent(_event);
					break;
			}
		} else {
			super.processKeyPressedEvent(_event);
		}
	}
}

/*
 * $Log$
 * Revision 1.17  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.16  2004/05/04 22:13:32  tako
 * Removed unnecessary constructors.
 *
 * Revision 1.15  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.14  2003/12/14 03:13:57  tako
 * Widgets used in CompoundWidgets can now have their properties set
 * specifically within the CompoundWidgets hierarchy. Each widget within
 * a CompoundWidget can have a (unique) name which can be used in the
 * Theme properties like <widgetname>.<propertyname>. If the hierarchy
 * is more than one level deep the names are separated by dots as well.
 *
 * Revision 1.13  2003/12/14 02:36:26  tako
 * CompoundWidget added that has almost all the functionality of Container
 * but with all protected methods meant for complex widgets. Container has
 * become little more than a public version of CompoundWidget.
 *
 * Revision 1.12  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.11  2003/11/24 17:16:08  tako
 * Containers are now focusable by default although they won't react to
 * setFocus() calls and will always return false for hasFocus().
 * The reason is that isFocusable() is now used to determine if a container's
 * children are allowed to have focus or not.
 *
 * Revision 1.10  2003/11/24 16:52:25  tako
 * Added moveChildToFront() and moveChildToBack().
 * getWidgetUnderPoint() no longer returns references to invisible widgets.
 *
 * Revision 1.9  2003/11/23 02:02:33  tako
 * Added getWidgetUnderPoint(int, int), necessary for the mouse support.
 *
 * Revision 1.8  2003/11/20 14:20:58  tako
 * Fixed problem with unfocused containers throwing away their key events.
 *
 * Revision 1.7  2003/11/20 13:12:23  tako
 * Made focus changes a bit more robust.
 *
 * Revision 1.6  2003/11/19 11:19:41  tako
 * Implemented completely new event system because trying to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.5  2003/11/19 00:12:57  tako
 * Made several methods protected instead of public.
 *
 * Revision 1.4  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
