package org.sharp.swing.apps.video;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;
import org.sharp.jdkex.Utils;

public class VideoUi {

	public Container getUI() {
		JPanel mediaPanel = new JPanel();
		mediaPanel.setLayout(new BorderLayout());

		// Use lightweight components for Swing compatibility
		Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);

		try {
			// create a player to play the media specified in the URL
			Player mediaPlayer = Manager.createRealizedPlayer(new File(
					FilenameUtils.concat(System.getProperty("user.dir"),
							/*"Command.mov"*/"bailey.mpg"/*"tale.mp3"*//*"Untitled 18.avi"*/)).toURL());

			// get the components for the video and the playback controls
			Component video = mediaPlayer.getVisualComponent();
			Component controls = mediaPlayer.getControlPanelComponent();

			if (video != null)
				mediaPanel.add(video, BorderLayout.CENTER); // add video
															// component

			if (controls != null)
				mediaPanel.add(controls, BorderLayout.SOUTH); // add controls

			//mediaPlayer.start(); // start playing the media clip
		} // end try
		catch (NoPlayerException e) {
			Utils.log.error("No media player found",e);
		} // end catch
		catch (CannotRealizeException e) {
			Utils.log.error("Could not realize media player",e);
		} // end catch
		catch (IOException e) {
			Utils.log.error("Error reading from the source",e);
		} // end catch

		return mediaPanel;
	}

}
