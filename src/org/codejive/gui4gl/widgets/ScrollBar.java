/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003, 2004 Tako Schotanus
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
 * Created on Mar 17, 2004
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import org.codejive.gui4gl.events.GuiActionEvent;
import org.codejive.gui4gl.events.GuiActionListener;
import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiMouseEvent;
import org.codejive.gui4gl.layouts.Layouter;

/**
 * This widget implements a scroll bar. A scroll bar is most commonly used
 * to browse around in a data set (like items in a list, a text document or
 * even an image) that is too large to be displayed all at once. Only a
 * certain part will be displayed at any given time, this subsection will
 * be referred to as the "Display Window" or just plain "Window" from now on.
 * To work properly the scroll bar needs 3 important values:
 * <li>the Display Window size, which is just a number that can represent
 * anything you want, the number of items or lines of text that can be displayed
 * at one time on the screen for example.</li>
 * <li>the total size which is also just a number but it should use the
 * same system you used for the Display Window size, but now representing
 * the total number of items, lines of text, etc. that is supposed to be
 * in the entire data set</li>
 * <li>the position or offset of the Display Window within the entire data set</li>
 * 
 * @author Tako
 * @version $Revision: 240 $
 */
public class ScrollBar extends CompoundWidget {
	private int m_nOrientation;
	private long m_nVisibleAmount;
	private long m_nTotalSize;
	private long m_nValue;

	protected Widget m_innerBar;
	protected Button m_lessButton, m_moreButton;
	
	private List m_changeListeners;
	
	/**
	 * Constructs a new ScrollBar with a total size of 100 with all of it visible
	 * by default (visible amount is 100 as well) and an orientation
	 * that will be automatically determined by the shape of the scroll bar.
	 */
	public ScrollBar() {
		this(100, 100, Orientation.DEFAULT);
	}
	
	/**
	 * Constructs a new ScrollBar with a total size of 100 and an orientation
	 * that will be automatically determined by the shape of the scroll bar.
	 * @param _nVisibleAmount The maximum amount/nr. of items visible at any given time
	 */
	public ScrollBar(long _nVisibleAmount) {
		this(100, _nVisibleAmount, Orientation.DEFAULT);
	}
	
	/**
	 * Constructs a new ScrollBar with an orientation that will be automatically
	 * determined by the shape of the scroll bar.
	 * @param _nTotalSize The total amount/nr. of itmes in the data set
	 * @param _nVisibleAmount The maximum amount/nr. of items visible at any given time
	 */
	public ScrollBar(long _nTotalSize, long _nVisibleAmount) {
		this(_nTotalSize, _nVisibleAmount, Orientation.DEFAULT);
	}
	
	/**
	 * Constructs a new ScrollBar
	 * @param _nTotalSize The total amount/nr. of itmes in the data set
	 * @param _nVisibleAmount The maximum amount/nr. of items visible at any given time
	 * @param _nOrientation The bar's orientation (horizontal, vertical or auto-determine)
	 */
	public ScrollBar(long _nTotalSize, long _nVisibleAmount, int _nOrientation) {
		setTotalSize(_nTotalSize);
		setVisibleAmount(_nVisibleAmount);
		setOrientation(_nOrientation);
		setValue(0);

		m_lessButton = new Button("L");
		m_lessButton.setName("lessButton");
		m_lessButton.addActionListener(new GuiActionListener() {
			public void actionPerformed(GuiActionEvent _event) {
				doSmallStepLess();
			}
		});
		add(m_lessButton);

		m_innerBar = new InnerBar();
		m_innerBar.setName("innerBar");
		add(m_innerBar);

		m_moreButton = new Button("M");
		m_moreButton.setName("lessButton");
		m_moreButton.addActionListener(new GuiActionListener() {
			public void actionPerformed(GuiActionEvent _event) {
				doSmallStepMore();
			}
		});
		add(m_moreButton);
		
		setLayouter(new ScrollBarLayouter());
		m_changeListeners = new LinkedList();
	}

	/**
	 * Returns the current size of the Display Window.
	 * @return The size of the Display Window
	 */
	public long getVisibleAmount() {
		return m_nVisibleAmount;
	}
	
	/**
	 * Sets the size of the Display Window.
	 * @param _nVisibleAmount The new size of the Display Window
	 */
	public void setVisibleAmount(long _nVisibleAmount) {
		m_nVisibleAmount = _nVisibleAmount;
	}

	/**
	 * Returns the current orientation of the scroll bar.
	 * @return The orientation of the scroll bar
	 */
	public int getOrientation() {
		return m_nOrientation;
	}
	
	/**
	 * Sets the orientation of the scroll bar.
	 * @param _nOrientation The new orientation of the scroll bar
	 */
	public void setOrientation(int _nOrientation) {
		m_nOrientation = _nOrientation;
	}

	/**
	 * Returns the current total size of the data set represented by the scroll bar.
	 * @return The total size of the data set
	 */
	public long getTotalSize() {
		return m_nTotalSize;
	}
	
	/**
	 * Sets the total size of the data set represented by the scroll bar.
	 * @param _nTotalSize The new total size of the data set
	 */
	public void setTotalSize(long _nTotalSize) {
		m_nTotalSize = _nTotalSize;
	}

	/**
	 * Returns the current position of the Display Window within the data set
	 * @return The current position of the Display Window
	 */
	public long getValue() {
		return m_nValue;
	}
	
	/**
	 * Sets the position of the Display Window within the data set
	 * @param _nValue The new position of the Display Window
	 */
	public void setValue(long _nValue) {
		m_nValue = _nValue;
		fireChangeEvent();
	}
	
	/**
	 * Adds a listener for the change events that get fired when the user adjusts the scroll bar
	 * @param _listener The GuiChangeListener to add
	 */
	public void addChangeListener(GuiChangeListener _listener) {
		m_changeListeners.add(_listener);
	}
	
	protected void processMouseClickedEvent(GuiMouseEvent _event) {
		super.processMouseClickedEvent(_event);
		handleBarChangeEvent(_event);
	}
	
	protected void processMouseDraggedEvent(GuiMouseEvent _event) {
		super.processMouseDraggedEvent(_event);
		handleBarChangeEvent(_event);
	}
	
	protected void handleBarChangeEvent(GuiMouseEvent _event) {
		if (!_event.isConsumed()) {
			Rectangle bounds = getInnerBounds();
			float fPct = (float)(_event.getX() - bounds.x) / bounds.width;
//			float fRelVal = fPct * (m_fMax - m_fMin);
//			fRelVal = (int)((fRelVal + m_fStepSize / 2) / m_fStepSize) * m_fStepSize;
//			setValue(m_fMin + fRelVal);
		}
	}
	
	protected void fireChangeEvent() {
		GuiChangeEvent e = new GuiChangeEvent(this, new Float(getValue()));
		GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
	}
	
	/**
	 * Returns the actual orientation for this widget.
	 * If the orientation property is either HORIZONTAL or VERTICAL
	 * this will just return the same value as getOrientation() but
	 * if the value is DEFAULT this method will try to determine the
	 * orientation by looking at the widget's bounds.
	 * @return The actual orientation of this widget
	 */
	public int getActualOrientation() {
		int orientation = getOrientation();
		if (orientation == Orientation.DEFAULT) {
			Rectangle barRect = getBounds();
			if (barRect.height > barRect.width) {
				orientation = Orientation.VERTICAL;
			} else {
				orientation = Orientation.HORIZONTAL;
			}
		}
		return orientation;
	}
	
	protected void doStep(long _nStep) {
		m_nValue += _nStep;
		if (_nStep < 0) {
			if (m_nValue < 0) {
				m_nValue = 0;
			}
		} else {
			if ((m_nValue + m_nVisibleAmount) > m_nTotalSize) {
				m_nValue = m_nTotalSize - m_nVisibleAmount;
			}
		}
		GuiChangeEvent e = new GuiChangeEvent(this, new Long(m_nValue));
		GuiChangeEvent.fireChangeEvent(m_changeListeners, e);
	}
	
	protected void doSmallStepLess() {
		doStep(-1);
	}
	
	protected void doSmallStepMore() {
		doStep(1);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		switch (_event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!_event.isConsumed()) {
					doSmallStepLess();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!_event.isConsumed()) {
					doSmallStepMore();
				}
				break;
			default:
				super.processKeyPressedEvent(_event);
				break;
		}
	}
	
	protected class ScrollBarLayouter implements Layouter {
		public void layoutChildren(CompoundWidget _parent) {
			Rectangle barRect = getInnerBounds();
			int orientation = getActualOrientation();
			if (orientation == Orientation.VERTICAL) {
				int buttonSize = barRect.height / 10;
				if (buttonSize > barRect.width) {
					buttonSize = barRect.width;
				}
				m_lessButton.setBounds(0, 0, barRect.width, buttonSize);
				m_innerBar.setBounds(0, buttonSize, barRect.width, barRect.height - 2 * buttonSize );
				m_moreButton.setBounds(0, barRect.height - buttonSize, barRect.width, buttonSize);
			} else {
				int buttonSize = barRect.width / 10;
				if (buttonSize > barRect.height) {
					buttonSize = barRect.height;
				}
				m_lessButton.setBounds(0, 0, buttonSize, barRect.height);
				m_innerBar.setBounds(buttonSize, 0, barRect.width - 2 * buttonSize, barRect.height);
				m_moreButton.setBounds(barRect.width - buttonSize, 0, buttonSize, barRect.height);
			}
		}
	}
	
	public class InnerBar extends Widget {
		
		public InnerBar() {
			setFocusable(true);
		}
		
		public float getStartValue() {
			float fStart = (float)getValue() / getTotalSize();
			return fStart;
		}
		
		public float getEndValue() {
			long nSize = getTotalSize();
			long nEndVal = getValue() + getVisibleAmount();
			if (nEndVal > nSize) {
				nEndVal = nSize;
			}
			float fEnd = (float)nEndVal / nSize;
			return fEnd;
		}
		
		public int getActualOrientation() {
			return ScrollBar.this.getActualOrientation();
		}
	}
}


/*
 * $Log$
 * Revision 1.1  2004/05/04 22:07:57  tako
 * First check-in of a new widget that implements a scroll bar.
 *
 */