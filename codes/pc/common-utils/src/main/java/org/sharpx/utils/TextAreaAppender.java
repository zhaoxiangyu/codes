package org.sharpx.utils;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Simple example of creating a Log4j appender that will
 * write to a JTextArea. 
 */
public class TextAreaAppender extends WriterAppender {
	
	private static JTextArea jTextArea = null;
	public static String name = "taa";
	private boolean used = false;
	
	/** Set the target JTextArea for the logging information to appear. */
	public TextAreaAppender() {
		super();
		TextAreaAppender.jTextArea = new JTextArea();
		jTextArea.setLineWrap(true);
		String pattern = "%d [%t] %c %p - %m @ %l%n";
		setLayout(new PatternLayout(pattern));
		setName(name);
	}
	
	public JTextArea getTextArea(){
		used = true;
		return jTextArea;
	}
	
	/**
	 * Format and then append the loggingEvent to the stored
	 * JTextArea.
	 */
	public void append(LoggingEvent event) {
		if(!used)
			return;
		
		final String message = layout.format(event);

		// Append formatted message to textarea using the Swing Thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextArea.append(message);
			}
		});
		
        if (layout.ignoresThrowable()) {
        	String[] lines = event.getThrowableStrRep();
			if (lines != null) {
				int len = lines.length;
				for (int i = 0; i < len; i++) {
					final String line = lines[i];
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							jTextArea.append(line);
						}
					});
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							jTextArea.append(Layout.LINE_SEP);
						}
					});
				}
			}
		}
	}
	
    public boolean requiresLayout() {
        return true;
    }
}