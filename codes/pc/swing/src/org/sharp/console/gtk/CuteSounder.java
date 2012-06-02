package org.sharp.console.gtk;

import org.gnome.gdk.Event;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Label;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

public class CuteSounder
{
    public static void main(String[] args) {
        final Window w;
        final VBox x;
        final Label l;
        final Button b;

        Gtk.init(args);
        w = new Window();
        x = new VBox(false, 3);
        l = new Label("Go ahead:\nMake my day");
        x.add(l);
        b = new Button("Press me!");
        x.add(b);
        b.connect(new Button.Clicked() {
            public void onClicked(Button source) {
                System.out.println("I was clicked: " + b.getLabel());
            }
        });
        w.add(x);
        w.setTitle("Hello World");
        w.showAll();
        w.connect(new Window.DeleteEvent() {
            public boolean onDeleteEvent(Widget source, Event event) {
                Gtk.mainQuit();
                return false;
            }
        });

        Gtk.main();
    }
}

