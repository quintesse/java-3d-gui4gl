/*
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes.blues;

import java.io.IOException;

import net.java.games.jogl.util.GLUT;

import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.*;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.TextureReader;

/**
 * @author tako
 * @version $Revision: 63 $
 */
public class BluesThemeConfig implements ThemeConfig {

	/* (non-Javadoc)
	 * @see org.codejive.world3d.gui.ThemeConfig#configure()
	 */
	public void configure() {
		// Widget
		Theme.setValue(Widget.class, "renderer", new WidgetRenderer());
		Theme.setValue(Widget.class, "backgroundColor", new GLColor(0.45f, 0.45f, 1.0f));
		Theme.setFloatValue(Widget.class, "transparancy", 1.0f);
		Theme.setValue(Widget.class, "focusedBackgroundColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(Widget.class, "focusedTransparancy", 0.6f);
	
		// Screen
		Theme.setFloatValue(Screen.class, "transparancy", 1.0f);
				
		// Window
		Theme.setValue(Window.class, "renderer", new WindowRenderer());
		Theme.setFloatValue(Window.class, "transparancy", 0.6f);
		try {
			Theme.setValue(Window.class, "backgroundImage", TextureReader.readTexture("org/codejive/gui4gl/themes/blues/images/Prairie Wind.bmp", true));
			Theme.setValue(Window.class, "focusedBackgroundImage", Theme.getValue(Window.class, "backgroundImage"));
		} catch (IOException e) { /* ignore */ }
		Theme.setValue(Window.class, "titlebarColor", new GLColor(0.16f, 0.16f, 1.0f));
		Theme.setFloatValue(Window.class, "titlebarTransparancy", 0.3f);
		Theme.setValue(Window.class, "captionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_18));
		Theme.setValue(Window.class, "captionFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Window.class, "captionPadding", 2);
		Theme.setIntegerValue(Window.class, "captionAlignment", GLText.ALIGN_LEFT);

		Theme.setFloatValue(Window.class, "activeTransparancy", 0.4f);
		Theme.setValue(Window.class, "activeTitlebarColor", new GLColor(0.16f, 0.16f, 1.0f));
		Theme.setFloatValue(Window.class, "activeTitlebarTransparancy", 0.1f);
		Theme.setValue(Window.class, "activeCaptionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_18));
		Theme.setValue(Window.class, "activeCaptionFontColor", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setIntegerValue(Window.class, "activeCaptionPadding", 2);
		
		// Button
		Theme.setValue(Button.class, "renderer", new ButtonRenderer());
		Theme.setValue(Button.class, "captionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Button.class, "captionFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Button.class, "captionPadding", 2);
		Theme.setIntegerValue(Button.class, "captionAlignment", GLText.ALIGN_LEFT);
		Theme.setValue(Button.class, "focusedCaptionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Button.class, "focusedCaptionFontColor", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setIntegerValue(Button.class, "focusedCaptionPadding", 2);
		Theme.setValue(Button.class, "selectedCaptionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Button.class, "selectedCaptionFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Button.class, "selectedCaptionPadding", 2);
		Theme.setValue(Button.class, "selectedBackgroundColor", new GLColor(1.0f, 0.0f, 0.0f));
		Theme.setFloatValue(Button.class, "selectedTransparancy", 0.5f);

		// Text
		Theme.setValue(Text.class, "renderer", new TextRenderer());
		Theme.setValue(Text.class, "textFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Text.class, "textFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Text.class, "textPadding", 2);
		Theme.setIntegerValue(Text.class, "textAlignment", GLText.ALIGN_LEFT);
		
		// ValueBar
		Theme.setValue(ValueBar.class, "renderer", new ValueBarRenderer());
		Theme.setValue(ValueBar.class, "barValueColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setValue(ValueBar.class, "barFillColor", new GLColor(1.0f, 1.0f, 1.0f)); //new GLColor(0.08f, 0.08f, 1.0f)); // this may be null, to indicate there is no bar background fill
		Theme.setValue(ValueBar.class, "barXPadding", new Integer(1)); // x-pad for the value bar
		Theme.setValue(ValueBar.class, "barYPadding", new Integer(1)); // y-pad for the value bar
	}
}

/*
 * $Log$
 * Revision 1.3  2003/11/18 15:59:39  steven
 * Added properties for the ValueBar widget
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
