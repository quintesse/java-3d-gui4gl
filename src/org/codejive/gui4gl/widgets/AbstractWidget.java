/*
 * Created on Nov 4, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.Texture;

/**
 * @author tako
 */
public interface AbstractWidget {
	public abstract AbstractContainer getParent();
	public abstract void setParent(AbstractContainer _parent);
	public abstract Screen getScreen();
	public abstract boolean isFocusable();
	public abstract void setFocusable(boolean _bCanHaveFocus);
	public abstract boolean hasFocus();
	public abstract void setFocus();
	public abstract AbstractWidget nextFocus();
	public abstract AbstractWidget previousFocus();
	public abstract int getLeft();
	public abstract void setLeft(int _nPos);
	public abstract int getTop();
	public abstract void setTop(int _nPos);
	public abstract int getWidth();
	public abstract void setWidth(int _nPos);
	public abstract int getHeight();
	public abstract void setHeight(int _nPos);
	public abstract void setBounds(int _nLeft, int _nTop, int _nWidth, int _nHeight);
	public abstract Rectangle getRectangle();
	public abstract void setRectangle(Rectangle _rect);
	public abstract Rectangle getBounds();
	public abstract GLColor getBackgroundColor();
	public abstract void setBackgroundColor(float _fRed, float _fGreen, float _fBlue);
	public abstract void setBackgroundColor(GLColor _color);
	public abstract float getTransparancy();
	public abstract void setTransparancy(float _fTransparancy);
	public abstract Texture getBackgroundImage();
	public abstract void setBackgroundImage(Texture _image);
	public abstract GLColor getFocusedBackgroundColor();
	public abstract void setFocusedBackgroundColor(float _fRed, float _fGreen, float _fBlue);
	public abstract void setFocusedBackgroundColor(GLColor _color);
	public abstract float getFocusedTransparancy();
	public abstract void setFocusedTransparancy(float _fTransparancy);
	public abstract Texture getFocusedBackgroundImage();
	public abstract void setFocusedBackgroundImage(Texture _image);
	public abstract boolean isVisible();
	public abstract void setVisible(boolean _bVisible);
	public abstract void calculateBounds(Rectangle _rect);
	public abstract void updateBounds(int _parentWidth, int _parentHeight);
	public abstract void addKeyListener(KeyListener _listener);
	public abstract void processKeyPressedEvent(KeyEvent _event);
	public abstract void processKeyReleasedEvent(KeyEvent _event);
	public abstract void processKeyTypedEvent(KeyEvent _event);
}