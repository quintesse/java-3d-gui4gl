/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.widgets;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.Renderable;
import org.codejive.utils4gl.Texture;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.events.GuiKeyListener;
import org.codejive.gui4gl.themes.*;

/**
 * @author tako
 * @version $Revision: 111 $
 */
public class Widget implements Renderable {
	private Container m_parent;
	private Rectangle m_rectangle;
	private GLColor m_backgroundColor;
	private float m_fTransparancy;
	private Texture m_backgroundImage;
	private int m_nXPadding, m_nYPadding;
	private GLColor m_focusedBackgroundColor;
	private float m_fFocusedTransparancy;
	private Texture m_focusedBackgroundImage;
	private int m_nFocusedXPadding, m_nFocusedYPadding;
	private boolean m_bVisible;
	private boolean m_bCanHaveFocus;

	private List m_keyListeners;

	public class Padding {
		public int xPadding, yPadding;
		public Padding() {
			xPadding = yPadding = 0;
		}
		public Padding(int _nXPadding, int _nYPadding) {
			xPadding = _nXPadding;
			yPadding = _nYPadding;
		}
	}
	
	private Rectangle m_bounds;
	private Rectangle m_innerBounds;
	protected Padding m_padding;
	
	public Widget() {
		m_parent = null;
		m_rectangle = new Rectangle();
		m_backgroundColor = (GLColor)Theme.getValue(getClass(), "backgroundColor");
		m_fTransparancy = Theme.getFloatValue(getClass(), "transparancy");
		m_backgroundImage = (Texture)Theme.getValue(getClass(), "backgroundImage");
		m_nXPadding = Theme.getIntegerValue(getClass(), "xPadding");
		m_nYPadding = Theme.getIntegerValue(getClass(), "yPadding");
		m_focusedBackgroundColor = (GLColor)Theme.getValue(getClass(), "focusedBackgroundColor");
		m_fFocusedTransparancy = Theme.getFloatValue(getClass(), "focusedTransparancy");
		m_focusedBackgroundImage = (Texture)Theme.getValue(getClass(), "focusedBackgroundImage");
		m_nFocusedXPadding = Theme.getIntegerValue(getClass(), "focusedXPadding");
		m_nFocusedYPadding = Theme.getIntegerValue(getClass(), "focusedYPadding");
		m_bVisible = true;
		m_bCanHaveFocus = false;
		
		m_keyListeners = new ArrayList();

		m_bounds = new Rectangle();
		m_innerBounds = new Rectangle();
		m_padding = new Padding();
	}
	
	protected void setParent(Container _parent) {
		m_parent = _parent;
	}
	
	public Container getParent() {
		return m_parent;
	}
	
	public Toplevel getToplevel() {
		return getParent().getToplevel();
	}
	
	public Screen getScreen() {
		Screen screen;
		Container parent = getParent();
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
	
	public Widget nextFocus() {
		return getParent().getNextFocusWidget(this);
	}
	
	public Widget previousFocus() {
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
	
	public int getXPadding() {
		return m_nXPadding;
	}
	
	public void setXPadding(int _nPadding) {
		m_nXPadding = _nPadding;
	}
	
	public int getYPadding() {
		return m_nYPadding;
	}
	
	public void setYPadding(int _nPadding) {
		m_nYPadding = _nPadding;
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
	
	public int getFocusedXPadding() {
		return m_nFocusedXPadding;
	}
	
	public void setFocusedXPadding(int _nPadding) {
		m_nFocusedXPadding = _nPadding;
	}
	
	public int getFocusedYPadding() {
		return m_nFocusedYPadding;
	}
	
	public void setFocusedYPadding(int _nPadding) {
		m_nFocusedYPadding = _nPadding;
	}
	
	public boolean isVisible() {
		return m_bVisible;
	}
	
	public void setVisible(boolean _bVisible) {
		m_bVisible = _bVisible;
	}
	
	public Rectangle getBounds() {
		return m_bounds;
	}
	
	public Rectangle getInnerBounds() {
		return m_innerBounds;
	}

	protected void calculateBounds() {
		Rectangle r = getRectangle();
		if (m_parent != null) {
			m_bounds.setBounds(m_parent.getInnerBounds());
			updateBounds();
			int x, y;
			if (r.x >= 0) {
				x = m_bounds.x + r.x;
			} else {
				x = m_bounds.x + m_bounds.width + r.x;
			}
			if (r.y >= 0) {
				y = m_bounds.y + r.y;
			} else {
				y = m_bounds.y + m_bounds.height + r.y;
			}
			m_bounds.setBounds(x, y, r.width, r.height);
		} else {
			updateBounds();
			m_bounds.setBounds(r);
		}
		
		// Calculate inner bounds
		m_innerBounds.setBounds(m_bounds);
		updateInnerBounds();
	}
	
	protected void updateBounds() {
		// Override in subclass if needed
	}
	
	protected void updateInnerBounds() {
		// Substract the padding from the inner bounds
		int nXPad, nYPad;
		if (hasFocus()) {
			nXPad = getFocusedXPadding();
			nYPad = getFocusedYPadding();
		} else {
			nXPad = getXPadding();
			nYPad = getYPadding();
		}
		m_innerBounds.x += nXPad;
		m_innerBounds.y += nYPad;
		m_innerBounds.width -= 2 * nXPad;
		m_innerBounds.height -= 2 * nYPad;
	}
	
	public void addKeyListener(GuiKeyListener _listener) {
		m_keyListeners.add(_listener);
	}
	
	protected void processKeyPressedEvent(GuiKeyEvent _event) {
		GuiKeyEvent.fireKeyPressed(m_keyListeners, _event);
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyPressedEvent(_event);
		}
	}
		
	protected void processKeyReleasedEvent(GuiKeyEvent _event) {
		GuiKeyEvent.fireKeyReleased(m_keyListeners, _event);
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyReleasedEvent(_event);
		}
	}
		
	protected void processKeyTypedEvent(GuiKeyEvent _event) {
		GuiKeyEvent.fireKeyTyped(m_keyListeners, _event);
		if (!_event.isConsumed() && (getParent() != null)) {
			getParent().processKeyTypedEvent(_event);
		}
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
	
	protected void initWidget(RenderContext _context) {
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(getClass(), "renderer");
		if (renderer != null) {
			renderer.initRendering(this, _context);
		}
	}

	/* (non-Javadoc)
	 * @see org.codejive.world3d.Renderable#render(org.codejive.world3d.RenderContext)
	 */
	public void render(RenderContext _context) {
		if (isVisible()) {
			calculateBounds();
			renderWidget(_context);
		}
	}
	
	protected void renderWidget(RenderContext _context) {
		WidgetRendererModel renderer = (WidgetRendererModel)Theme.getValue(getClass(), "renderer");
		if (renderer != null) {
			renderer.render(this, _context);
		}
	}
}

/*
 * $Log$
 * Revision 1.9  2003/11/21 01:31:05  tako
 * Added getToplevel().
 *
 * Revision 1.8  2003/11/20 00:36:07  tako
 * Code change because of change from getPadding() to
 * updateInnerBounds(). This makes it possible for widgets to adjust the
 * amount of inner space to make available for their "content", which is
 * much more flexible than the previous padding system.
 *
 * Revision 1.7  2003/11/19 11:19:41  tako
 * Implemented completely new event system because tryin to re-use the
 * AWT and Swing events just was too much trouble.
 * Most names of events, listeners and adapters have been duplicated
 * from their AWT/Swing counterparts only with a Gui prefix.
 *
 * Revision 1.6  2003/11/19 00:18:44  tako
 * Added support for seperate X and Y padding.
 * Made several methods protected instead of public.
 *
 * Revision 1.5  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
