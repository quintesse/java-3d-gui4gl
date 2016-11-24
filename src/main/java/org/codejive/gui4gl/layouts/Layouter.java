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
 * Created on Apr 8, 2004
 */
package org.codejive.gui4gl.layouts;

import org.codejive.gui4gl.widgets.CompoundWidget;

/**
 * Layouters can move and size a CompoundWidget's child widgets according
 * to whatever set of rules was programmed into it.
 * 
 * @author tako
 * @version $Revision: 246 $
 */
public interface Layouter {
	/**
	 * This method performs the actual layouting of the given CompoundWidget's child widgets.
	 * @param _parent The parent widget containing the child widgets to layout
	 */
	public void layoutChildren(CompoundWidget _parent);
}


/*
 * $Log$
 * Revision 1.1  2004/05/04 22:27:07  tako
 * First check-in of the Layouter interface.
 *
 */