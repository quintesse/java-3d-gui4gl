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
 * @version $Revision: 85 $
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
		Theme.setIntegerValue(Widget.class, "xPadding", 0);
		Theme.setIntegerValue(Widget.class, "yPadding", 0);
		Theme.setValue(Widget.class, "focusedBackgroundColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(Widget.class, "focusedTransparancy", 0.6f);
		Theme.setIntegerValue(Widget.class, "focusedXPadding", 0);
		Theme.setIntegerValue(Widget.class, "focusedYPadding", 0);
	
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
		Theme.setIntegerValue(Window.class, "captionXPadding", 2);
		Theme.setIntegerValue(Window.class, "captionYPadding", 2);
		Theme.setIntegerValue(Window.class, "captionAlignment", GLText.ALIGN_LEFT);

		Theme.setFloatValue(Window.class, "activeTransparancy", 0.4f);
		Theme.setValue(Window.class, "activeTitlebarColor", new GLColor(0.16f, 0.16f, 1.0f));
		Theme.setFloatValue(Window.class, "activeTitlebarTransparancy", 0.1f);
		Theme.setValue(Window.class, "activeCaptionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_18));
		Theme.setValue(Window.class, "activeCaptionFontColor", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setIntegerValue(Window.class, "activeCaptionXPadding", 2);
		Theme.setIntegerValue(Window.class, "activeCaptionYPadding", 2);
		Theme.setIntegerValue(Window.class, "activeCaptionPadding", 2);
		
		// Button
		Theme.setValue(Button.class, "renderer", new ButtonRenderer());
		Theme.setValue(Button.class, "captionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Button.class, "captionFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Button.class, "xPadding", 2);
		Theme.setIntegerValue(Button.class, "yPadding", 2);
		Theme.setIntegerValue(Button.class, "captionAlignment", GLText.ALIGN_LEFT);
		Theme.setValue(Button.class, "focusedCaptionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Button.class, "focusedCaptionFontColor", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setIntegerValue(Button.class, "focusedXPadding", 2);
		Theme.setIntegerValue(Button.class, "focusedYPadding", 2);
		Theme.setValue(Button.class, "selectedCaptionFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Button.class, "selectedCaptionFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Button.class, "selectedXPadding", 2);
		Theme.setIntegerValue(Button.class, "selectedYPadding", 2);
		Theme.setValue(Button.class, "selectedBackgroundColor", new GLColor(1.0f, 0.0f, 0.0f));
		Theme.setFloatValue(Button.class, "selectedTransparancy", 0.5f);

		// Text
		Theme.setValue(Text.class, "renderer", new TextRenderer());
		Theme.setValue(Text.class, "textFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(Text.class, "textFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(Text.class, "xPadding", 2);
		Theme.setIntegerValue(Text.class, "yPadding", 2);
		Theme.setIntegerValue(Text.class, "textAlignment", GLText.ALIGN_LEFT);
		
		// ValueBar
		Theme.setValue(ValueBar.class, "renderer", new ValueBarRenderer());
		Theme.setValue(ValueBar.class, "backgroundColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setFloatValue(ValueBar.class, "transparancy", 0.0f);
		Theme.setIntegerValue(ValueBar.class, "xPadding", 1);
		Theme.setIntegerValue(ValueBar.class, "yPadding", 1);
		Theme.setValue(ValueBar.class, "barColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(ValueBar.class, "barTransparancy", 0.0f);
		Theme.setValue(ValueBar.class, "focusedBackgroundColor", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setFloatValue(ValueBar.class, "focusedTransparancy", 0.0f);
		Theme.setIntegerValue(ValueBar.class, "focusedXPadding", 1);
		Theme.setIntegerValue(ValueBar.class, "focusedYPadding", 1);
		Theme.setValue(ValueBar.class, "focusedBarColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(ValueBar.class, "focusedBarTransparancy", 0.0f);
	}
}

/*
 * $Log$
 * Revision 1.6  2003/11/19 09:07:31  tako
 * Changed focused background color (to an ugly yellow, sorry).
 *
 * Revision 1.5  2003/11/19 00:28:53  tako
 * Added support for ValueBar's transparancy options.
 *
 * Revision 1.4  2003/11/19 00:09:17  tako
 * Added support for seperate X and Y padding.
 * Removed as much widget-specific paddings and replaced them by the
 * ones in the Widget base class.
 * Changed several ValueBar options to make use of the standard options
 * already available in the Widget base class.
 *
 * Revision 1.3  2003/11/18 15:59:39  steven
 * Added properties for the ValueBar widget
 *
 * Revision 1.2  2003/11/17 10:54:49  tako
 * Added CVS macros for revision and log.
 *
 */
