package org.sharp.swing.utils;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;

public class TrayUtils {

	private static SystemTray tray;
	private static TrayIcon trayIcon;

	private static void trayIcon(String imagePath,String description){
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        tray = SystemTray.getSystemTray();
        trayIcon = new TrayIcon(createImage(imagePath, description));
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}
	
	public static void message(String message){
		if(trayIcon == null)
			trayIcon("org/sharp/res/Compass-icon.png","");
		
		if(trayIcon!=null)
			trayIcon.displayMessage("Message", message, TrayIcon.MessageType.INFO);
	}
	
    private static Image createImage(String path, String description) {
//    	URL imageURL = TrayUtils.class.getResource(path);        
    	URL imageURL = TrayUtils.class.getClassLoader().getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    
}
