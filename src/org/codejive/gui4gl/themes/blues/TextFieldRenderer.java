/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes.blues;

import java.awt.Rectangle;

import net.java.games.jogl.GL;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;

/**
 * @author steven
 * @version $Revision: 120 $
 */
public class TextFieldRenderer implements WidgetRendererModel {
	
	public void initRendering(Widget _widget, RenderContext _context) {
		RenderHelper.initSuperClass(TextField.class, _widget, _context);
	}

	public void render(Widget _widget, RenderContext _context) {
		RenderHelper.renderSuperClass(TextField.class, _widget, _context);

		GL gl = _context.getGl();

		TextField textField = (TextField)_widget;
		
		GLColor textFontColor;
		if(textField.hasFocus()) {
			textFontColor = textField.getTextFontFocusedColor();
		} else {
			textFontColor = textField.getTextFontColor();
		}

		gl.glDisable(GL.GL_TEXTURE_2D);
		drawTextWithCursor(_context, _widget.getInnerBounds(), 0, 0, textFontColor, textField);
		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	private static void drawTextWithCursor(RenderContext _context, Rectangle _bounds, int _nXPadding, int _nYPadding, GLColor _color, TextField _textField) {
		GL gl = _context.getGl();

		
		float fFontHeight = _textField.getTextFont().getSize(_context);
		float fFontBaseLine = _textField.getTextFont().getBaseLine(_context);
		float fFontPadding = fFontHeight - fFontBaseLine;
		float fXPos = _bounds.x + _nXPadding;
		float fYPos = _bounds.y + _nYPadding + fFontBaseLine + fFontPadding;

		float fCursorXPos = fXPos;
		float fCursorWidth = 2;
		
		float fWidth = _bounds.width - 2 * _nXPadding - fCursorWidth; // make sure cursor always has room.
		
		gl.glColor3fv(_color.toArray3f());
		

		// note that the viewoffset may not be enough to show the cursor, which we adjust for here, since only here we know what fits.
		int nViewOffset = _textField.getViewOffset();
		
		int nCursorPos = _textField.getCursorPos();
		// at least skip anything before the viewoffset
		String sText = _textField.getText();

		int l = sText.length();
		int p = nCursorPos;
		
		float fLength  = 0;
		StringBuffer s = new StringBuffer(l);
		// left from cursor first
		while((p > 0) && (p > nViewOffset)) {
			p--;
			s.insert(0, sText.charAt(p));
			fLength = _textField.getTextFont().getTextWidth(_context, s.toString());
			if(fLength > fWidth) {
				s.deleteCharAt(0);
				fLength = _textField.getTextFont().getTextWidth(_context, s.toString());
				p++;
				break;
			}
		}
		fCursorXPos = fXPos + fLength;
		_textField.setViewOffset(p);
		
		gl.glRasterPos2f(fXPos, fYPos);
		_textField.getTextFont().renderText(_context, s.toString());
		
		
		s = new StringBuffer(l);
		float fRightWidth = fWidth - fLength;
		// right from cursor if any room left
		if(fRightWidth > 0) {
			p = nCursorPos;
			while(p < l) {
				s.append(sText.charAt(p));
				fLength = _textField.getTextFont().getTextWidth(_context, s.toString());
				if(fLength > fRightWidth) {
					s.deleteCharAt(s.length() - 1);
					break;
				} else {
					p++;
				}
			}
			gl.glRasterPos2f(fCursorXPos, fYPos);
			_textField.getTextFont().renderText(_context, s.toString());
		}
		
		
		if(_textField.hasFocus()) {
			int spd = _textField.getCursorBlinkSpeed();
			if(System.currentTimeMillis() % spd < (spd/2)) {
				GLColor c = _textField.getTextCursorColor();
				gl.glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
				gl.glBegin(GL.GL_QUADS);
				RenderHelper.drawRectangle(_context.getGl(), (int)fCursorXPos, (int) (fYPos - fFontHeight + fFontPadding), (int)fCursorWidth, (int)(fFontHeight - fFontPadding));
				gl.glEnd();
			}
		}
		
	}
	
}

/*
 * $Log$
 * Revision 1.2  2003/11/21 10:48:39  steven
 * should not have used padding in combination with innerbounds
 *
 * Revision 1.1  2003/11/21 10:01:29  steven
 * A renderer for the TextField class
 *
 */
