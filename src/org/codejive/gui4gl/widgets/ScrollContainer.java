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
 * Created on Apr 28, 2004
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;

import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.layouts.Layouter;

/**
 * @author tako
 * @version $Revision: 255 $
 */
public class ScrollContainer extends CompoundWidget {
	protected Widget m_content;
	protected Container m_container;
	protected ScrollBar m_horizontal, m_vertical;
	
	public ScrollContainer(Widget _content) {
		m_content = _content;
		
		m_container = new Container();
		add(m_container);
		
		m_container.add(m_content);
		
		m_horizontal = new ScrollBar();
		m_horizontal.setOrientation(Orientation.HORIZONTAL);
		m_horizontal.setVisible(false);
		m_horizontal.setFocusable(false);
		m_horizontal.addChangeListener(new GuiChangeListener() {
			public void stateChanged(GuiChangeEvent _event) {
				Long value = (Long)_event.getValue(); 
				m_content.setLeft(-(int)value.longValue());
			}
		});
		add(m_horizontal);

		m_vertical = new ScrollBar();
		m_vertical.setOrientation(Orientation.VERTICAL);
		m_vertical.setVisible(false);
		m_vertical.setFocusable(false);
		m_vertical.addChangeListener(new GuiChangeListener() {
			public void stateChanged(GuiChangeEvent _event) {
				Long value = (Long)_event.getValue(); 
				m_content.setTop(-(int)value.longValue());
			}
		});
		add(m_vertical);

		if (m_content.getWidth() > getWidth()) {
			m_horizontal.setVisible(true);
			m_horizontal.setTotalSize(m_content.getWidth());
			m_horizontal.setVisibleAmount(m_container.getWidth());
		}
		
		if (m_content.getHeight() > getHeight()) {
			m_vertical.setVisible(true);
			m_vertical.setTotalSize(m_content.getHeight());
			m_vertical.setVisibleAmount(m_container.getHeight());
		}
		
		setLayouter(new ScrollContainerLayouter());
	}
	
	public boolean hasFocus() {
		return false;
	}
	
	public void setFocus() {
		// Don't allow this
	}
	
	protected class ScrollContainerLayouter implements Layouter {
		public void layoutChildren(CompoundWidget _parent) {
			Rectangle barRect = getInnerBounds();

			int width = barRect.width;
			if (m_vertical.isVisible()) {
				width -= m_vertical.getWidth();
			}
			int height = barRect.height;
			if (m_horizontal.isVisible()) {
				height -= m_horizontal.getHeight();
			}
			
			m_container.setBounds(0, 0, width, height);
			if (m_horizontal.isVisible()) {
				m_horizontal.setBounds(0, height, width, 12);
			}
			if (m_vertical.isVisible()) {
				m_vertical.setBounds(width, 0, 12, height);
			}
		}
	}
}


/*
 * $Log$
 * Revision 1.2  2004/05/05 00:14:22  tako
 * Scrolling now actually works.
 *
 * Revision 1.1  2004/05/04 23:58:51  tako
 * First check-in of a new widget implementing a scroll pane.
 *
 */