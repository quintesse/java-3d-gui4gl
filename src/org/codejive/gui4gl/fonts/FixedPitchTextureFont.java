/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Steven Lagerweij
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
 * Created on Dec 10, 2003
 */
package org.codejive.gui4gl.fonts;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.RenderContext;

/**
 * @author steven
 * @version $Revision: 197 $
 */
public class FixedPitchTextureFont implements Font {
	
	// opnelg id for the texture
	private int m_lTextureId;
	
	// normalized size of a pixel in the texture
	private float m_fTexturePixelWidth;
	private float m_fTexturePixelHeight;
	
	// precalculated texture coordinates for each character
	private float m_characterCoords[][] = new float[128][2];
	
	private float m_fCharWidth;
	private float m_fCharHeight;
	// height including Y spacing
	private float m_fCharHeightSize;
	private float m_fBaseLine;
	
	// width & height of a character in texture coordinate space
	private float m_fTextureCoordWidth;
	private float m_fTextureCoordHeight;
	
	
	public static final String m_defaultAsciiSet = "!\\\"#$%'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`{|}~abcdefghijklmnopqrstuvwxyz";
	
	/** Creates a texture font (non-proportional) from the given texture. This uses the default character set. See m_defaultAsciiSet.
	 * It is assumed that each character includes spacing between characters for the X space, but not for the Y space.
	 * Each row in the texture contains 10 characters when using this constructor.  
	 * Also note that only characters in the range 0-127 are mapped.
	 *  
	 * So given the m_defaultAsciiSet the texture would look something like:
	 * !\"#$%'()*
	 * +,-./01234
	 * 56789:;<=>
	 * ..etcetera
	 * 
	 * 
	 * @param _lTextureId 		OpenGL Id of the texture to use
	 * @param _lTextureWidth	Width of the texture in pixels
	 * @param _lTextureHeight	Height of the texture in pixels
	 * @param _fXOffset			Offset for the X axis to use. Note this is relative to 
	 * @param _fYOffset			Offset for the Y axis to use. Note that '0' is the top of the image. 
	 * @param _fCharacterWidth	Width of the character in pixels
	 * @param _fCharacterHeight	Height of the character in pixels
	 * @param _fBaseLine		Baseline of the font, relative to the top of the character
	 * @param _bHasLowercase	Whether the font includes lowercase characters. If not, character are uppercased.
	 */
	public FixedPitchTextureFont(int _lTextureId, int _lTextureWidth, int _lTextureHeight, float _fXOffset, float _fYOffset, float _fCharacterWidth, float _fCharacterHeight, float _fBaseLine, boolean _bHasLowercase) {
		this(m_defaultAsciiSet, 10, _lTextureId, _lTextureWidth, _lTextureHeight, _fXOffset, _fYOffset, _fCharacterWidth, _fCharacterHeight, _fBaseLine, _bHasLowercase);
	}

	/** Same as above, except that you can specify your own characterset and amount of characters per row in the texture.
	 * 
	 * @param _sCharacterSet	String specifiying the characters sequentially as the exist in the texture. 
	 * @param _lCharactersPerRow Indicates the amount of characters contained in each 'row' of the texture
	 * @param _lTextureId 		OpenGL Id of the texture to use
	 * @param _lTextureWidth	Width of the texture in pixels
	 * @param _lTextureHeight	Height of the texture in pixels
	 * @param _fXOffset			Offset for the X axis to use. Note this is relative to 
	 * @param _fYOffset			Offset for the Y axis to use. Note that '0' is the top of the image. 
	 * @param _fCharacterWidth	Width of the character in pixels
	 * @param _fCharacterHeight	Height of the character in pixels
	 * @param _fBaseLine		Baseline of the font, relative to the top of the character
	 * @param _bHasLowercase	Whether the font includes lowercase characters. If not, character are uppercased.
	 */
	public FixedPitchTextureFont(String _sCharacterSet, int _lCharactersPerRow, int _lTextureId, int _lTextureWidth, int _lTextureHeight, float _fXOffset, float _fYOffset, float _fCharacterWidth, float _fCharacterHeight, float _fBaseLine, boolean _bHasLowercase) {
		m_lTextureId = _lTextureId;
		m_fTexturePixelWidth = 1.0f / _lTextureWidth;
		m_fTexturePixelHeight = 1.0f / _lTextureHeight;
		
		m_fCharWidth = _fCharacterWidth;
		m_fCharHeight = _fCharacterHeight;
		
		// NOTE NOTE : Although the texture has X spacing, it does not for Y spacing. When the size is retrieved 
		// using getSize() bij gui4gl we add an extra pixel as y spacing.
		m_fCharHeightSize = m_fCharHeight + 1;

		m_fBaseLine = _fBaseLine;
		
		m_fTextureCoordWidth = m_fTexturePixelWidth * m_fCharWidth;
		m_fTextureCoordHeight = m_fTexturePixelHeight * m_fCharHeight;
		
		initCoords(_sCharacterSet, _lCharactersPerRow, _fXOffset, _fYOffset, _bHasLowercase);
	}
	

	public float getSize(RenderContext _context) {
		return m_fCharHeightSize;
	}

	public float getBaseLine(RenderContext _context) {
		return m_fBaseLine;
	}

	public float getTextWidth(RenderContext _context, String _sText) {
		// pretty easy since we only suport fixed pitch fonts
		return _sText.length() * m_fCharWidth;
	}

	// Used to get the current rasterpos from opengl
	private final int[] m_rasterPos = new int[4];	
	// Used to get the current viewport dimensions from opengl
	private final int[] m_viewPort = new int[4];
	
	public void renderText(RenderContext _context, String _sText) {
		final GL gl = _context.getGl();

		// get the current position
		gl.glGetIntegerv(GL.GL_CURRENT_RASTER_POSITION, m_rasterPos);

		// get the viewport because we need to invert the Y coordinate
		gl.glGetIntegerv(GL.GL_VIEWPORT, m_viewPort);
		
		float x = m_rasterPos[0];
		float y = m_viewPort[3] - m_rasterPos[1];
		
		
		gl.glBindTexture(GL.GL_TEXTURE_2D,  m_lTextureId);
		gl.glEnable(GL.GL_TEXTURE_2D);
		
// Note: blending may give nice effects, but this doesn't allow us to show the text properly using the requested color.
//		gl.glBlendFunc(GL.GL_ONE_MINUS_DST_COLOR,GL.GL_ONE);
//		gl.glEnable(GL.GL_BLEND);
		
//		gl.glDisable(GL.GL_BLEND);
//		gl.glColor4f(1f,1f,1f,1f);
		
		// Simple alpha testing is used to make sure the text is shown without background
		gl.glEnable(GL.GL_ALPHA_TEST);		
		gl.glAlphaFunc(GL.GL_GREATER,0.1f); // Somehow this MUST BE .1f instead of 0f. (will get shadow effect when using 0f)  Need to check out why. 
		
		gl.glBegin(GL.GL_QUADS);
		
		final int l = _sText.length();
		for(int n = 0 ; n < l; n++) {
			char c = _sText.charAt(n);
			if(c >= 0 && c < 128) {
				final float tx = m_characterCoords[ c ][0];
				final float ty = m_characterCoords[ c ][1];
				
				// tx will be >= 0 to indicate this is a mapped character 
				if(tx >= 0) {
					gl.glTexCoord2f(tx,ty);				
					gl.glVertex2f(x,  y - m_fCharHeight); // gui4gl apparantly uses bottom as position for the text 
					
					gl.glTexCoord2f(tx,ty - m_fTextureCoordHeight);				
					gl.glVertex2f(x,  y);
					
					gl.glTexCoord2f(tx+m_fTextureCoordWidth,ty - m_fTextureCoordHeight);  
					gl.glVertex2f(x+m_fCharWidth,y);
					
					gl.glTexCoord2f(tx+m_fTextureCoordWidth,ty);  
					gl.glVertex2f(x+m_fCharWidth,y - m_fCharHeight);
				}
			}
			
			x+=m_fCharWidth;
		}		
		gl.glEnd();
//		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_ALPHA_TEST);		
	}
	
	
	// Calculates the texture coordinates for each available character
	private void initCoords(String _ascii, int _lCharactersPerRow, float _fXOffset, float _fYOffset, boolean _bHasLowercase) {
		for(int i = 0 ; i < 128; i++) {
			int index = _ascii.indexOf(i);
			
			if(!_bHasLowercase && i >= 'a' && i <='z') {
				index = _ascii.indexOf( Character.toUpperCase((char) i) );
			}
			
			if(index != -1) {
				int x = index % _lCharactersPerRow;
				int y = index / _lCharactersPerRow;
				m_characterCoords[i][0] = _fXOffset * m_fTexturePixelWidth + x * m_fCharWidth * m_fTexturePixelWidth;
				// note: Y is inverted so Y == 0 is top, which is easier for when creating the images used for the textures.
				m_characterCoords[i][1] = 1f - (_fYOffset * m_fTexturePixelHeight + y * m_fCharHeight * m_fTexturePixelHeight);
			} else {
				// indicate this character is not mapped
				m_characterCoords[i][0] = -1;
			}			
		}
	}
}


/*
 * $Log$
 * Revision 1.3  2003/12/12 09:48:43  steven
 * Offsets were not transformed to texture coordinate space
 *
 * Revision 1.2  2003/12/11 13:59:19  tako
 * Added GPL license and got rid of 2 minor warnings.
 *
 * Revision 1.1  2003/12/11 12:57:07  steven
 * Allows for texture mapped (transparant) fonts.
 * You must load the texture yourself and you must use 
 * the alpha channel to indicate transparancy
 *
 *
 */