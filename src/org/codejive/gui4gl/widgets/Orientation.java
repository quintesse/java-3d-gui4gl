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
 * Created on Apr 6, 2004
 */
package org.codejive.gui4gl.widgets;

/**
 * This class just holds the constants for variables, arguments or attributes
 * that need to define either a horizontal or vertical orientation. A third
 * option DEFAULT is added when no specific value can/must be given.
 * 
 * @author tako
 * @version $Revision: 261 $
 */
public abstract class Orientation {
	/**
	 * The default orientation. This normally means that the code will
	 * decide for itslef which orientation should be used.
	 */
	public static final int DEFAULT = 0;
	/**
	 * Horizontal orientation.
	 */
	public static final int HORIZONTAL = 1;
	/**
	 * Vertical orientation.
	 */
	public static final int VERTICAL = 2;
}

/*
 * $Log$
 * Revision 1.2  2004/05/10 23:48:10  tako
 * Added javadocs for all public classes and methods.
 *
 * Revision 1.1  2004/05/04 22:07:57  tako
 * First check-in of a new widget that implements a scroll bar.
 *
 */