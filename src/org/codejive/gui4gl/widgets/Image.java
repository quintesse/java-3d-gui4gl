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

/**
 * @author gertjan
 * @version $Revision: 209 $
 */
public class Image extends Widget {
	private int m_lTextureID;
	
	
	public Image(int _lTextureID) {
		this(null, _lTextureID);
	}

	public Image(String _sName, int _lTextureID) {
		super(_sName);
		m_lTextureID = _lTextureID;
	}
	
	public int getTextureID() {
		return m_lTextureID;
	}
	
	public void setTextureID(int _lTextureID) {
		m_lTextureID = _lTextureID;
	}
	
}
/*
 * $Log$
 * Revision 1.1  2004/01/27 13:29:19  steven
 * image widget patch by gertjan
 *
 */
