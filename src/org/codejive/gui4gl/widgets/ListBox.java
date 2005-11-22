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
 * Created on May 14, 2004
 */
package org.codejive.gui4gl.widgets;

import java.util.Iterator;

import org.codejive.gui4gl.themes.WidgetRendererModel;

/**
 * @author Tako
 * @version $Revision:	$
 */
public class ListBox extends CompoundWidget {
	
	public interface ListBoxItem extends Widget {
		public boolean isSelected();
		public void select(boolean _bSelect);
	}
	
	public class StringListBoxItem extends WidgetBase implements ListBoxItem {
		private boolean m_bSelected;
		private String m_text;

		protected StringListBoxItem(String _text) {
			m_bSelected = false;
			m_text = _text;
		}

		public boolean isSelected() {
			return m_bSelected;
		}

		public void select(boolean _bSelect) {
			m_bSelected = _bSelect;
		}

		public String getText() {
			return m_text;
		}
	}

	public ListBox() {
		setFocusable(true);
	}
	
	public Iterator getItems() {
		return this.getChildren();
	}
	
	public void addItem(ListBoxItem _item) {
		Class itemRendererClass = (Class)getAttribute("itemRenderer");
		WidgetRendererModel renderer = WidgetBase.createRenderer(itemRendererClass, (Widget)_item);
		_item.setRenderer(renderer);
		add(_item);
	}
	
	public void addItem(String _item) {
		addItem(new StringListBoxItem(_item));
	}
	
	public void removeItem(ListBoxItem _item) {
//		remove(_item);
	}
	
	public void clear() {
		clear();
	}
}


/*
 * $Log:	$
 */