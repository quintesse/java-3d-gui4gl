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
 * Created on Nov 5, 2003
 */
package org.codejive.gui4gl.widgets;

/**
 * This widget is used to just display text, it provides no interaction with
 * the user.
 * 
 * @author tako
 * @version $Revision: 309 $
 */
public class Text extends WidgetBase {
	private String m_sText;
	
	/**
	 * Creates a new Text without any actual text in it yet.
	 */
	public Text() {
		this(null);
	}
	
	/**
	 * Creates a new Text with the given string as its content
	 * @param _sText The text to use as the contents for the new widget
	 */
	public Text(String _sText) {
		m_sText = _sText;
	}

	/**
	 * Returns the current contents of the Text.
	 * @return The current text
	 */
	public String getText() {
		return m_sText;
	}
	
	/**
	 * Sets a new text for the widget
	 * @param _sText The new text to use as the contents
	 */
	public void setText(String _sText) {
		m_sText = _sText;
	}
}

/*
 * $Log$
 * Revision 1.10  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.9  2004/05/04 22:05:43  tako
 * Now using the new attribute map instead of individual property getters and setters.
 * Consolidated event firing code into separate methods.
 *
 * Revision 1.8  2003/12/15 11:06:00  tako
 * Did a rollback of the previous code because it was introducing more
 * problems than solving them. A widget's name is now set in the constructor
 * and can not be changed anymore.
 *
 * Revision 1.7  2003/12/05 01:07:02  tako
 * Implemented enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 * Moved all text related properties to the Widget class even though that
 * class never actually uses them but this saves lots of coding in the widgets
 * that do need text properties.
 * Changed some property names during object construction.
 *
 * Revision 1.6  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.5  2003/11/21 10:00:38  steven
 * Moved editing support to a different class
 *
 * Revision 1.4  2003/11/19 17:11:54  steven
 * Preliminary check in of Text widget editing
 *
 * Revision 1.3  2003/11/19 00:13:28  tako
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
