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
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes;

import java.awt.Rectangle;

import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.Renderable;

/**
 * @author tako
 * @version $Revision: 320 $
 */
public interface WidgetRendererModel extends Renderable {
	public Rectangle getMinimalBounds(RenderContext _context);
}

/*
 * $Log$
 * Revision 1.6  2004/10/17 11:10:07  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.5  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.4  2003/11/24 16:48:28  tako
 * Added updateRendering().
 *
 * Revision 1.3  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
