/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.Renderable;
import org.codejive.utils4gl.Texture;
import org.codejive.gui4gl.themes.*;

/**
 * @author tako
 */
public class Widget implements Renderable, AbstractWidget {
	private Container m_parent;
	private Rectangle m_rectangle;
	private GLColor m_backgroundColor;
	private float m_fTransparancy;
	private Texture m_backgroundImage;
	private GLColor m_focusedBackgroundColor;
	private float m_fFocusedTransparancy;
	private Texture m_focusedBackgroundImage;
	private boolean m_bVisible;
	private boolean m_bCanHaveFocus;

	private List m_keyListeners;

	private Rectangle m_bounds;
	
	public Widget() {
		m_parent = null;
		m_rectangle = new Rectangle();
		m_backgroundColor = (GLColor)Theme.getValue(getClass(), "backgroundColor");
		m_fTransparancy = Theme.getFloatValue(getClass(), "transparancy");
		m_backgroundImage = (Texture)Theme.getValue(getClass(), "backgroundImage");
		m_focusedBackgroundColor = (GLColor)Theme.getValue(getClass(), "focusedBackgroundColor");
		m_fFocusedTransparancy = Theme.getFloatValue(getClass(), "focusedTransparancy");
		m_focusedBackgroundImage = (Texture)Theme.getValue(getClass(), "focusedBackgroundImage");
		m_bVisible = true;
		m_bCanHaveFocus = false;
		
		m_keyListeners = new ArrayList();

		m_bounds = new Rectangle();
	}
	
	protected void setParent(Container _parent) {
		m_parent = _parent;
	}
	
	public Container getParent() {
		return m_parent;
	}
	
	public Screen getScreen() {
		Screen screen;
		AbstractWidget parent = getParent();
		if (parent != null) {
			screen = parent.getScreen();
		} else {
			if (this instanceof Screen) {
				screen = (Screen)this;
			} else {
				throw new RuntimeException("Widget not part of a Screen's widget tree");
			}
		}
		return screen;
	}
	
	public boolean isFocusable() {
		return m_bCanHaveFocus;
	}
	
	public void setFocusable(boolean _bCanHaveFocus) {
		m_bCanHaveFocus = _bCanHaveFocus;
	}
	
	public boolean hasFocus() {
		return (this == getScreen().getFocusWidget());
	}
	
	public void setFocus() {
		if (isFocusable()) {
			getParent().setFocusWidget(this);
		}
	}
	
	public AbstractWidget nextFocus() {
		return getParent().getNextFocusWidget(this);
	}
	
	public AbstractWidget previousFocus() {
		return getParent().getPreviousFocusWidget(this);
	}
	
	public int getLeft() {
		return m_rectangle.x;
	}
	
	public void setLeft(int _nPos) {
		m_rectangle.x = _nPos;
	}
	
	public int getTop() {
		return m_rectangle.y;
	}
	
	public void setTop(int _nPos) {
		m_rectangle.y = _nPos;
	}
	
	public int getWidth() {
		return m_rectangle.width;
	}
	
	public void setWidth(int _nPos) {
		m_rectangle.width = _nPos;
	}
	
	public int getHeight() {
		return m_rectangle.height;
	}
	
	public void setHeight(int _nPos) {
		m_rectangle.height = _nPos;
	}
	
	public void setBounds(int _nLeft, int _nTop, int _nWidth, int _nHeight) {
		m_rectangle.setBounds(_nLeft, _nTop, _nWidth, _nHeight);
	}
	
	public Rectangle getRectangle() {
		return m_rectangle;
	}
	
	public void setRectangle(Rectangle _rect) {
		m_rectangle.setRect(_rect);
	}
	
	public Rectangle getBounds() {
		return m_bounds;
	}
	
	public GLColor getBackgroundColor() {
		return m_backgroundColor;
	}
	
	public void setBackgroundColor(float _fRed, float _fGreen, float _fBlue) {
		m_backgroundColor.set(_fRed, _fGreen, _fBlue);
	}
	
	public void setBackgroundColor(GLColor _color) {
		m_backgroundColor.set(_color);
	}
	
	public float getTransparancy() {
		return m_fTransparancy;
	}
	
	public void setTransparancy(float _fTransparancy) {
		m_fTransparancy = _fTransparancy;
	}
	
	public Texture getBackgroundImage() {
		return m_backgroundImage;
	}
	
	public void setBackgroundImage(Texture _image) {
		m_backgroundImage = _image;
	}
	
	public GLColor getFocusedBackgroundColor() {
		return m_focusedBackgroundColor;
	}
	
	public void setFocusedBackgroundColor(float _fRed, float _fGreen, float _fBlue) {
		m_focusedBackgroundColor.set(_fRed, _fGreen, _fBlue);
	}
	
	public void setFocusedBackgroundColor(GLColor _color) {
		m_focusedBackgroundColor.set(_color);
	}
	
	public float getFocusedTransparancy() {
		return m_fFocusedTransparancy;
	}
	
	public void setFocusedTransparancy(float _fTransparancy) {
		m_fFocusedTransparancy = _fTransparancy;
	}
	
	public Texture getFocusedBackgroundImage() {
		return m_focusedBackgroundImage;
	}
	
	public void setFocusedBackgroundImage(Texture _image) {
		m_focusedBackgroundImage = _image;
	}
	
	public boolean isVisible() {
		return m_bVisible;
	}
	
	public void setVisible(boolean _bVisible) {
		m_bVisible = _bVisible;
	}

	public void calculateBounds(Rectangle _rect) {
		Rectangle r = getRectangle();
		if (m_parent != null) {
			m_parent.calculateBounds(_rect); // TODO Figure out why this is necessary. Should already have been done by parent??
			updateBounds(_rect.width, _rect.height);
			_rect.setBounds(_rect.x + r.x, _rect.y + r.y, r.width, r.height);
		} else {
			updateBounds(-1, -1);
			_rect.setBounds(r);
		}
	}
	
	public void updateBounds(int _parentWidth, int _parentHeight) {
	}
	
	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#readyForRendering()
	 */
	public boolean readyForRendering() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#initRendering(org.codejive.world3d.RenderContext)
	 */
	public void initRendering(RenderContext _context) {
		if (isVisible()) {
			initWidget(_context);
		}
	}
	
	public void initWidget(RenderContext _context) {
		AbstractWidgetRenderer renderer = (AbstractWidgetRenderer)Theme.getValue(getClass(), "renderer");
		if (renderer != null) {
			renderer.initRendering(this, _context);
		}
	}
	
	public void addKeyListener(KeyListener _listener) {
		m_keyListeners.add(_listener);
	}
	
	public void processKeyPressedEvent(KeyEvent _event) {
		_event.setSource(this);
		if (!_event.isConsumed() && !m_keyListeners.isEmpty()) {
			Iterator i = m_keyListeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				KeyListener listener = (KeyListener)i.next();
				listener.keyPressed(_event);
			}
		}
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyPressedEvent(_event);
		}
	}
		
	public void processKeyReleasedEvent(KeyEvent _event) {
		_event.setSource(this);
		if (!_event.isConsumed() && !m_keyListeners.isEmpty()) {
			Iterator i = m_keyListeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				KeyListener listener = (KeyListener)i.next();
				listener.keyReleased(_event);
			}
		}
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyReleasedEvent(_event);
		}
	}
		
	public void processKeyTypedEvent(KeyEvent _event) {
		_event.setSource(this);
		if (!_event.isConsumed() && !m_keyListeners.isEmpty()) {
			Iterator i = m_keyListeners.iterator();
			while (i.hasNext() && !_event.isConsumed()) {
				KeyListener listener = (KeyListener)i.next();
				listener.keyTyped(_event);
			}
		}
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyTypedEvent(_event);
		}
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#render(org.codejive.world3d.RenderContext)
	 */
	public void render(RenderContext _context) {
		if (isVisible()) {
			calculateBounds(m_bounds);
			renderWidget(_context);
		}
	}
	
	public void renderWidget(RenderContext _context) {
		AbstractWidgetRenderer renderer = (AbstractWidgetRenderer)Theme.getValue(getClass(), "renderer");
		if (renderer != null) {
			renderer.render(this, _context);
		}
	}
}
