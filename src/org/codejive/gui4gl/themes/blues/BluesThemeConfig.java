/*
 * [gui4gl] OpenGL game-oriented GUI library
 * 
 * Copyright (C) 2003 Tako Schotanus, Steven Lagerweij, Gertjan Assies
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
 * Created on Oct 31, 2003
 */
package org.codejive.gui4gl.themes.blues;

import com.sun.opengl.util.GLUT;

import org.codejive.gui4gl.GLText;
import org.codejive.gui4gl.fonts.*;
import org.codejive.gui4gl.themes.*;
import org.codejive.gui4gl.widgets.*;
import org.codejive.utils4gl.GLColor;
import org.codejive.utils4gl.RenderContext;

/**
 * @author tako
 * @version $Revision: 341 $
 */
public class BluesThemeConfig implements ThemeConfig {

	public void configure(RenderContext _context) {
		// Widget
		Theme.setValue(WidgetBase.class, "renderer", WidgetRenderer.class);
		Theme.setValue(WidgetBase.class, "backgroundColor", new GLColor(0.45f, 0.45f, 1.0f));
		Theme.setFloatValue(WidgetBase.class, "transparancy", 1.0f);
		Theme.setValue(WidgetBase.class, "backgroundImage", null);
		Theme.setValue(WidgetBase.class, "textFont", new BitmapFont(GLUT.BITMAP_HELVETICA_12));
		Theme.setValue(WidgetBase.class, "textFontColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setIntegerValue(WidgetBase.class, "textAlignment", GLText.ALIGN_LEFT);
		Theme.setIntegerValue(WidgetBase.class, "xPadding", 0);
		Theme.setIntegerValue(WidgetBase.class, "yPadding", 0);
		Theme.setValue(WidgetBase.class, "backgroundColor#focused", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(WidgetBase.class, "transparancy#focused", 0.6f);
		Theme.setValue(WidgetBase.class, "textFontColor#focused", new GLColor(0.96f, 1.0f, 0.2f));
		Theme.setValue(WidgetBase.class, "textFontColor#disabled", new GLColor(0.5f, 0.5f, 0.5f));
	
		// Screen
		Theme.setFloatValue(Screen.class, "transparancy", 1.0f);
				
		// Window
		Theme.setValue(Window.class, "renderer", WindowRenderer.class);
		Theme.setFloatValue(Window.class, "transparancy", 0.6f);
		Theme.setIntegerValue(Window.class, "titlebarHeight", 25);
		Theme.setValue(Window.class, "titlebarColor", new GLColor(0.16f, 0.16f, 1.0f));
		Theme.setFloatValue(Window.class, "titlebarTransparancy", 0.3f);
		Theme.setValue(Window.class, "textFont", new BitmapFont(GLUT.BITMAP_HELVETICA_18));
		Theme.setIntegerValue(Window.class, "captionXPadding", 2);
		Theme.setIntegerValue(Window.class, "captionYPadding", 2);

		Theme.setFloatValue(Window.class, "titlebarTransparancy#active", 0.1f);
		Theme.setValue(Window.class, "textFontColor#active", new GLColor(.96f, 1.0f, 0.2f));
		
		// Button
		Theme.setValue(Button.class, "renderer", ButtonRenderer.class);
		Theme.setIntegerValue(Button.class, "xPadding", 2);
		Theme.setIntegerValue(Button.class, "yPadding", 2);
		Theme.setValue(Button.class, "backgroundColor#selected", new GLColor(1.0f, 0.0f, 0.0f));
		Theme.setFloatValue(Button.class, "transparancy#selected", 0.5f);
		
		// Toggle
		Theme.setValue(Toggle.class, "renderer", ToggleRenderer.class);
		Theme.setIntegerValue(Toggle.class, "xPadding", 2);
		Theme.setIntegerValue(Toggle.class, "yPadding", 2);
		Theme.setValue(Toggle.class, "checkColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setValue(Toggle.class, "checkBackgroundColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setFloatValue(Toggle.class, "checkTransparancy", 0.0f);
		Theme.setValue(Toggle.class, "checkBackgroundColor#focused", new GLColor(.96f, 1.0f, 0.2f));

		// Text
		Theme.setValue(Text.class, "renderer", TextRenderer.class);
		Theme.setIntegerValue(Text.class, "xPadding", 2);
		Theme.setIntegerValue(Text.class, "yPadding", 2);
		
		// ValueBar
		Theme.setValue(ValueBar.class, "renderer", ValueBarRenderer.class);
		Theme.setValue(ValueBar.class, "backgroundColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setFloatValue(ValueBar.class, "transparancy", 0.0f);
		Theme.setIntegerValue(ValueBar.class, "xPadding", 1);
		Theme.setIntegerValue(ValueBar.class, "yPadding", 1);
		Theme.setValue(ValueBar.class, "barColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(ValueBar.class, "barTransparancy", 0.0f);
		Theme.setValue(ValueBar.class, "backgroundColor#focused", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setFloatValue(ValueBar.class, "transparancy#focused", 0.0f);
		Theme.setValue(ValueBar.class, "textFontColor#focused", new GLColor(0.0f, 0.0f, 0.0f));
		Theme.setValue(ValueBar.class, "textFontColor", new GLColor(0.0f, 0.0f, 0.0f));
		
		// ScrollBar
//		Theme.setValue(ScrollBar.class, "renderer", new ScrollBarRenderer());
		Theme.setValue(ScrollBar.class, "backgroundColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setFloatValue(ScrollBar.class, "transparancy", 0.0f);
		Theme.setIntegerValue(ScrollBar.class, "xPadding", 1);
		Theme.setIntegerValue(ScrollBar.class, "yPadding", 1);
		Theme.setValue(ScrollBar.class, "backgroundColor#focused", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setFloatValue(ScrollBar.class, "transparancy#focused", 0.0f);
		Theme.setValue(ScrollBar.class, "innerBar.renderer", ScrollBarRenderer.class);
		Theme.setValue(ScrollBar.class, "innerBar.barColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(ScrollBar.class, "innerBar.barTransparancy", 0.0f);
		Theme.setValue(ScrollBar.class, "innerBar.barColor#focused", new GLColor(0.45f, 0.45f, 1.0f));
		Theme.setValue(ScrollBar.class, "lessButton.backgroundColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(ScrollBar.class, "lessButton.transparancy", 0.0f);
		Theme.setValue(ScrollBar.class, "lessButton.backgroundColor#focused", new GLColor(0.45f, 0.45f, 1.0f));
		Theme.setValue(ScrollBar.class, "moreButton.backgroundColor", new GLColor(0.65f, 0.65f, 1.0f));
		Theme.setFloatValue(ScrollBar.class, "moreButton.transparancy", 0.0f);
		Theme.setValue(ScrollBar.class, "moreButton.backgroundColor#focused", new GLColor(0.45f, 0.45f, 1.0f));
		
		// TextField
		Theme.setValue(TextField.class, "renderer", TextFieldRenderer.class);
		Theme.setValue(TextField.class, "backgroundColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setFloatValue(TextField.class, "transparancy", 0.0f);
		Theme.setIntegerValue(TextField.class, "xPadding", 2);
		Theme.setIntegerValue(TextField.class, "yPadding", 2);
		Theme.setValue(TextField.class, "textFontColor", new GLColor(0.0f, 0.0f, 0.0f));
		Theme.setValue(TextField.class, "cursorColor", new GLColor(0.0f, 0.0f, 0.0f, .8f));
		Theme.setIntegerValue(TextField.class, "cursorBlinkSpeed", 1000); // in millis
		Theme.setValue(TextField.class, "backgroundColor#focused", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setFloatValue(TextField.class, "transparancy#focused", 0.0f);
		Theme.setValue(TextField.class, "textFontColor#focused", new GLColor(0.0f, 0.0f, 0.0f));
		
		// Image
		Theme.setValue(Image.class, "renderer", ImageRenderer.class);
		
		// ListBox
		Theme.setValue(ListBox.class, "renderer", ListBoxRenderer.class);
		Theme.setValue(ListBox.class, "itemRenderer", ListBoxItemRenderer.class);
		Theme.setValue(ListBox.class, "backgroundColor", new GLColor(1.0f, 1.0f, 1.0f));
		Theme.setFloatValue(ListBox.class, "transparancy", 0.0f);
		Theme.setValue(ListBox.class, "backgroundColor#focused", new GLColor(.96f, 1.0f, 0.2f));
		Theme.setFloatValue(ListBox.class, "transparancy#focused", 0.0f);
		// ListBox.StringListBoxItem
		Theme.setFloatValue(ListBox.StringListBoxItem.class, "transparancy", 0.0f);
	}
}

/*
 * $Log$
 * Revision 1.18  2004/05/04 23:54:53  tako
 * Minor change in scrollbar layout.
 *
 * Revision 1.17  2004/05/04 22:31:23  tako
 * Added scrollbar attribute values.
 * Changed the names of a couple of TextField attributes.
 *
 * Revision 1.16  2004/03/17 00:41:17  tako
 * Removed background image for Windows because it looked horrible.
 *
 * Revision 1.15  2004/03/07 18:19:43  tako
 * configure() must now be passed a valid RenderContext.
 * The attribute "backgroundImage" is now properly affected by style
 * class indicators like "#focused".
 *
 * Revision 1.14  2003/12/14 00:29:04  steven
 * added & changed text font color for the valuebar widget
 *
 * Revision 1.13  2003/12/05 01:05:11  tako
 * Implemented rendering of enabled/disabled state for widgets.
 * Renamed all caption properties to text properties leaving only one set of
 * properties instead some widgets using text and others caption.
 *
 * Revision 1.12  2003/11/25 16:27:59  tako
 * All code is now subject to the Lesser GPL.
 *
 * Revision 1.11  2003/11/23 01:58:41  tako
 * The Toggle now also has a beautifull yellow background color when focused.
 * Added several more properties for the TextField widget and renamed
 * some existing ones to be more like the standard.
 *
 * Revision 1.10  2003/11/21 10:01:50  steven
 * Config for the textfield
 *
 * Revision 1.9  2003/11/21 01:27:46  tako
 * Added properties for the new Toggle widget.
 *
 * Revision 1.8  2003/11/20 00:40:49  tako
 * Added titlebarHeight property.
 *
 * Revision 1.7  2003/11/19 17:11:54  steven
 * Preliminary check in of Text widget editing
 *
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
