/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.themes;

import java.awt.Rectangle;

import net.java.games.jogl.GL;

/**
 * @author tako
 */
public class RenderHelper {
	
	public static void drawRectangle(GL _gl, Rectangle _rect) {
		_gl.glTexCoord2f(0.0f, 0.0f);
		_gl.glVertex2f(_rect.x, _rect.y);
		_gl.glTexCoord2f(0.0f, 1.0f);
		_gl.glVertex2f(_rect.x, _rect.y + _rect.height);
		_gl.glTexCoord2f(1.0f, 1.0f);
		_gl.glVertex2f(_rect.x + _rect.width, _rect.y + _rect.height);
		_gl.glTexCoord2f(1.0f, 0.0f);
		_gl.glVertex2f(_rect.x + _rect.width, _rect.y);
	}
	
}
