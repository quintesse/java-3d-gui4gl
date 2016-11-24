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

import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 219 $
 */
public interface ThemeConfig {
	public void configure(RenderContext _context);
}

/*
 * $Log$
 * Revision 1.4  2004/03/07 18:16:42  tako
 * ThemeConfig now needs a RenderContext to function. Because of that
 * it is not possible anymore to select a default theme anymore!
 * Use Theme.setDefaultConfig() first when starting to use use gui4gl.
 *
 * Revision 1.3  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
