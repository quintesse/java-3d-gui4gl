/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003, 2004 Tako Schotanus, Gertjan Assies
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
 * Created on Jan 26, 2004
 */
package org.codejive.gui4gl.widgets;

import org.codejive.utils4gl.textures.Texture;

/**
 * @author gertjan
 * @version $Revision: 231 $
 */
public class Image extends Widget {
	private Texture m_image;
	
	
	public Image(Texture _image) {
		this(null, _image);
	}

	public Image(String _sName, Texture _image) {
		super(_sName);
		m_image = _image;
	}
	
	public Texture getImage() {
		return m_image;
	}
	
	public void setTexture(Texture _image) {
		m_image = _image;
	}
	
}
/*
 * $Log$
 * Revision 1.2  2004/03/17 00:44:59  tako
 * Image widget now uses the new Texture system as well.
 *
 * Revision 1.1  2004/01/27 13:29:19  steven
 * image widget patch by gertjan
 *
 */
