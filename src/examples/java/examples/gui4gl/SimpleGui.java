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
 * Created on Nov 17, 2003
 */
package examples.gui4gl;

import java.io.IOException;
import java.text.NumberFormat;

import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.events.GuiActionEvent;
import org.codejive.gui4gl.events.GuiActionListener;
import org.codejive.gui4gl.events.GuiChangeEvent;
import org.codejive.gui4gl.events.GuiChangeListener;
import org.codejive.gui4gl.events.GuiKeyAdapter;
import org.codejive.gui4gl.events.GuiKeyEvent;
import org.codejive.gui4gl.themes.Theme;
import org.codejive.gui4gl.widgets.Button;
import org.codejive.gui4gl.widgets.Image;
import org.codejive.gui4gl.widgets.ListBox;
import org.codejive.gui4gl.widgets.Screen;
import org.codejive.gui4gl.widgets.ScrollBar;
import org.codejive.gui4gl.widgets.ScrollContainer;
import org.codejive.gui4gl.widgets.Text;
import org.codejive.gui4gl.widgets.TextField;
import org.codejive.gui4gl.widgets.Toggle;
import org.codejive.gui4gl.widgets.ValueBar;
import org.codejive.gui4gl.widgets.Window;
import org.codejive.utils4gl.FrameRateCounter;
import org.codejive.utils4gl.RenderContext;
import org.codejive.utils4gl.SimpleFrameRateCounter;
import org.codejive.utils4gl.textures.Texture;
import org.codejive.utils4gl.textures.TextureReader;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;

/**
 * @author tako
 * @version $Revision: 361 $
 */
public class SimpleGui implements GLEventListener, KeyListener {
	GLWindow m_glWindow;
	Animator animator;
	FrameRateCounter m_fpsCounter;
	
	RenderContext m_context;
	Screen m_screen;
	MenuWindow m_menuWindow;
	InfoWindow m_infoWindow;
	TestWindow m_testWindow;
	
	public static void main(String[] args) {
		(new SimpleGui()).start();
	}

	public SimpleGui() {
	}
	
	public void start() {
        Display display = NewtFactory.createDisplay(null);
        com.jogamp.newt.Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        m_glWindow = GLWindow.create(screen, glCapabilities);

        m_glWindow.setSize(1024, 768);
        m_glWindow.setPosition(50, 50);
        m_glWindow.setUndecorated(false);
        m_glWindow.setAlwaysOnTop(false);
        m_glWindow.setFullscreen(false);
        m_glWindow.setPointerVisible(true);
        m_glWindow.confinePointer(false);
        m_glWindow.setTitle("SimpleGui");

        m_glWindow.setVisible(true);

        m_glWindow.addGLEventListener(this);
        m_glWindow.addKeyListener(this);

        animator = new Animator(m_glWindow);
        animator.start();
	}
	
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		m_fpsCounter.addFrame();
		m_infoWindow.setFps(m_fpsCounter.getFrameRate());
		
		m_screen.render(m_context, null);
	}

	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
		// Not used
	}

	public void init(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();

		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.setSwapInterval(0);
		
		// Set up a render context
		m_context = new RenderContext(gl);
		// Determine the Theme to use
		Theme.setDefaultTheme(m_context);
		// Create a screen that will hold all our windows
		m_screen = new Screen();

		// Create our first window
		m_menuWindow = new MenuWindow();
		// Make sure it's visible
		m_menuWindow.setVisible(true);
		// Add the window to the screen
		m_screen.add(m_menuWindow);
		// Activate the window making sure it will receive key presses
		m_menuWindow.activate();
		
		// Create a second window
		m_infoWindow = new InfoWindow();
		m_infoWindow.setVisible(true);
		m_screen.add(m_infoWindow);
		
		// Create a third window
		m_testWindow = new TestWindow(m_context);
		m_testWindow.setVisible(true);
		m_screen.add(m_testWindow);
		
		// If we're interested in interactive windows the screen
		// must receive the appriopriate events
		m_glWindow.addKeyListener(m_screen);
		m_glWindow.addMouseListener(m_screen);
		
		// If we want to get any keys the GUI doesn't handle
		// (no windows active for example) we need to register
		// a listener for them
		m_screen.addKeyListener(new GuiKeyAdapter() {
			@Override
			public void keyPressed(GuiKeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					animator.stop();
				}
			}
		});

		// Initialize the GUI for subsequent rendering
		m_screen.initRendering(m_context);
		
		// Create an frame rate counter
		m_fpsCounter = new SimpleFrameRateCounter();
	}
	
    public void dispose(GLAutoDrawable drawable) {
        System.exit(0);
    }
    
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		final GLU glu = new GLU();

		if (height <= 0) // avoid a divide by zero error!
			height = 1;
		final float h = (float)width / (float)height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		// Update the GUI because of the new dimensions
		m_screen.initRendering(m_context);
	}


	class MenuWindow extends Window {
		public MenuWindow() {
			super("Test Window");
			setCenterParent(true);
			setWidth(300);
			setHeight(305);
		
			Text t = new Text("This text is being displayed inside a Text widget. Below this widget you can see several buttons");
			t.setBounds(5, 5, 290, 40);
			add(t);
			
			Button b = new Button("Resume");
			b.setBounds(5, 45, 290, 20);
			add(b);
			b = new Button("Options");
			b.setBounds(5, 65, 290, 20);
			b.setEnabled(false);
			add(b);
/*
// BEGIN TEST
b.addKeyListener(new GuiKeyAdapter() {
	public void keyTyped(GuiKeyEvent _event) {
		Button button = (Button)_event.getSource();
		switch (_event.getKeyChar()) {
			case 'a':
				button.setWidth(button.getWidth() - 1);
				_event.consume();
				break;
			case 'd':
				button.setWidth(button.getWidth() + 1);
				_event.consume();
				break;
			case 'w':
				button.setHeight(button.getHeight() - 1);
				_event.consume();
				break;
			case 's':
				button.setHeight(button.getHeight() + 1);
				_event.consume();
				break;
		}
	}
});
// END TEST
*/
			b = new Button("Exit this example");
			b.setBounds(5, 85, 290, 20);
			b.addActionListener(new GuiActionListener() {
				public void actionPerformed(GuiActionEvent _event) {
					animator.stop();
				}
			});
			add(b);
			
			Toggle tg = new Toggle("Show info window", true);
			tg.setBounds(5, 105, 290, 20);
			add(tg);
			tg.addChangeListener(new GuiChangeListener() {
				public void stateChanged(GuiChangeEvent _event) {
					Boolean value = (Boolean)_event.getValue();
					m_infoWindow.setVisible(value.booleanValue());
				}
			});
			
			ValueBar hb = new ValueBar(0.0f, 100.0f, 5.0f, true, GLText.ALIGN_CENTER);
			hb.setBounds(5, 130, 290, 12);
			add(hb);
			hb.addChangeListener(new GuiChangeListener() {
				public void stateChanged(GuiChangeEvent _event) {
					Float value = (Float)_event.getValue();
					m_infoWindow.setValue(value.floatValue());
				}
			});
	
//			ValueBar vb = new ValueBar(0.0f, 100.0f, 5.0f, true, GLText.ALIGN_RIGHT);
//			vb.setBounds(310, 5, 25, 150);
//			add(vb);			
			
			ScrollBar sb = new ScrollBar(100, 10);
			sb.setBounds(5, 145, 290, 12);
			add(sb);
			
			TextField tf = new TextField("Edit me");
			tf.setBounds(5, 165, 290, 20);
			add(tf);

			ListBox lb = new ListBox();
			lb.addItem("Aap");
			lb.addItem("Noot");
			lb.addItem("Mies");
			lb.addItem("Wim");
			lb.addItem("Zus");
			lb.addItem("Jet");
			lb.addItem("Aap");
			lb.addItem("Noot");
			lb.addItem("Mies");
			lb.addItem("Wim");
			lb.addItem("Zus");
			lb.addItem("Jet");
			lb.setBounds(5, 190, 290, 80);
			add(lb);

			addKeyListener(new GuiKeyAdapter() {
				@Override
				public void keyPressed(GuiKeyEvent _event) {
					switch (_event.getKeyCode()) {
						case KeyEvent.VK_ESCAPE:
							m_menuWindow.setVisible(false);
							_event.consume();
							break;
					}
				}
			});
		}
	}

	class InfoWindow extends Window {
		Text m_fps, m_value, m_liveCount, m_mortalCount;
		ValueBar m_gfpsHorizontal, m_gfpsVertical;
	
		public InfoWindow() {
			setBounds(10, 340, 150, 100);
			setFocusable(false);
			setDraggable(false);
		
			Text t = new Text("FPS");
			t.setBounds(5, 5, 75, 20);
			add(t);
			m_fps = new Text("?");
			m_fps.setBounds(80, 5, 60, 20);
			add(m_fps);

			t = new Text("value");
			t.setBounds(5, 25, 75, 20);
			add(t);
			m_value = new Text("?");
			m_value.setBounds(80, 25, 60, 20);
			add(m_value);

			t = new Text("#live");
			t.setBounds(5, 45, 75, 20);
			add(t);
			m_liveCount = new Text("?");
			m_liveCount.setBounds(80, 45, 60, 20);
			add(m_liveCount);

			t = new Text("#mortal");
			t.setBounds(5, 65, 75, 20);
			add(t);
			m_mortalCount = new Text("?");
			m_mortalCount.setBounds(80, 65, 60, 20);
			add(m_mortalCount);
			
			m_gfpsHorizontal = new ValueBar(0,700);
			m_gfpsHorizontal.setBounds(5,85, 100, 10);
			add(m_gfpsHorizontal);

			m_gfpsVertical = new ValueBar(0,700);
			m_gfpsVertical.setBounds(130,5, 10, 80);
			//m_gfpsVertical.setFillColor(null); // Would leave out the background fill 
			add(m_gfpsVertical);
			
		}
	
		public void setFps(float _fFps) {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMinimumFractionDigits(1);
			nf.setMaximumFractionDigits(1);
			Float f = new Float(_fFps);
			m_fps.setText(nf.format(f));
			m_gfpsHorizontal.setValue(_fFps);
			m_gfpsVertical.setValue(_fFps);
		}
	
		public void setValue(float _fValue) {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMinimumFractionDigits(1);
			nf.setMaximumFractionDigits(1);
			Float f = new Float(_fValue);
			m_value.setText(nf.format(f));
		}
	
		public void setLiveCount(String _sCount) {
			m_liveCount.setText(_sCount);
		}
	
		public void setMortalCount(String _sCount) {
			m_mortalCount.setText(_sCount);
		}
	}

	class TestWindow extends Window {
		Text m_fps, m_value, m_liveCount, m_mortalCount;
		ValueBar m_gfpsHorizontal, m_gfpsVertical;
		
		public TestWindow(RenderContext _context) {
			super("Test Window");
			setBounds(475, 10, 150, 125);

			Texture img = null;
			try {
				img = TextureReader.readTexture(_context, "examples/gui4gl/toucan.png", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image imgw = new Image(img);
			imgw.setBounds(0, 0, 324, 300);
//			imgw.setBounds(5, 5, 162, 150);
//			imgw.setBackgroundColor(1.0f, 0.0f, 0.0f);

			ScrollContainer sc = new ScrollContainer(imgw);
			sc.setBounds(0, 0, 150, 100);
			add(sc);
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}

/*
 * $Log$
 * Revision 1.19  2004/10/17 11:12:03  tako
 * Removed updateRendering().
 * Added getMinimalBounds().
 *
 * Revision 1.18  2004/05/07 23:30:13  tako
 * Tiny aspect ratio fix for the image.
 *
 * Revision 1.17  2004/05/04 23:59:45  tako
 * Added ScrollBar and ScrollContainer examples.
 *
 * Revision 1.16  2004/03/17 00:52:27  tako
 * Updated the example app to include an Image widget.
 *
 * Revision 1.15  2004/03/07 18:28:51  tako
 * Updated the example to set the default theme.
 *
 * Revision 1.14  2003/12/14 00:29:24  steven
 * updated to show valuebars with their value as text
 *
 * Revision 1.13  2003/12/05 01:08:58  tako
 * Updated the example to show what a disabled widget looks like.
 *
 * Revision 1.12  2003/11/26 00:12:32  tako
 * Class is now public like it should be.
 *
 * Revision 1.11  2003/11/25 16:28:00  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.10  2003/11/24 16:45:54  tako
 * Window resize now properly updates the GUI.
 * Made info window non-draggable.
 *
 * Revision 1.9  2003/11/23 01:56:43  tako
 * Minor change to enable mouse support for the GUI.
 *
 * Revision 1.8  2003/11/21 10:53:35  steven
 * NO longer makes the text widget focusable
 *
 * Revision 1.7  2003/11/21 10:03:17  steven
 * Removed warnings and added textfield editing example
 *
 * Revision 1.6  2003/11/21 01:25:00  tako
 * Updated example to make use of the new Toggle widget.
 *
 * Revision 1.5  2003/11/19 11:21:48  tako
 * Updated the code to use the new event system.
 *
 * Revision 1.4  2003/11/19 09:27:22  tako
 * Added ValueBar as slider to the menu window.
 *
 * Revision 1.3  2003/11/18 16:01:08  steven
 * Added example for a horizontal and vertical bar which showing the fps
 *
 * Revision 1.2  2003/11/18 15:54:57  tako
 * Added some silly test code.
 *
 * Revision 1.1  2003/11/17 11:05:19  tako
 * First check-in of the gui4gl example application.
 *
 */
