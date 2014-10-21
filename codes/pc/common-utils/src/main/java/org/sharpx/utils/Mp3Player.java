package org.sharpx.utils;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import org.sharpx.utils.intf.AudioPlayer;

/**
 * * This class implements a simple player based on BasicPlayer. * BasicPlayer
 * is a threaded class providing most features * of a music player. BasicPlayer
 * works with underlying JavaSound * SPIs to support multiple audio formats.
 * Basically JavaSound supports * WAV, AU, AIFF audio formats. Add MP3 SPI and
 * Vorbis * SPI in your CLASSPATH to play MP3 and Ogg Vorbis file.
 */
public class Mp3Player implements BasicPlayerListener,AudioPlayer {

	private long duration;
	private float fps;
	private long microseconds;
	private PlayerCallBack pcb;
	private BasicController control;
	private int bytes;
	
	public interface PlayerCallBack {
		void oneSecondPlayed(double progress);

		void endOfMedia(int position);
	}

	/** * Contructor. */
	public Mp3Player(PlayerCallBack pcb) {
		this.pcb = pcb;
		
		// Instantiate BasicPlayer.
		BasicPlayer player = new BasicPlayer();
		// BasicPlayer is a BasicController.
		control = (BasicController) player;
		// Register BasicPlayerTest to BasicPlayerListener events.
		// It means that this object will be notified on BasicPlayer
		// events such as : opened(...), progress(...), stateUpdated(...)
		player.addBasicPlayerListener(this);
	}

	public void play(String filename) {

		try { // Open file, or URL or Stream (shoutcast, icecast) to play.
			control.open(new File(filename));

			// control.open(new URL("http://yourshoutcastserver.com:8000"));
			// Start playback in a thread. control.play();
			// If you want to pause/resume/pause the played file then
			// write a Swing player and just call control.pause(),
			// control.resume() or control.stop().
			// Use control.seek(bytesToSkip) to seek file
			// (i.e. fast forward and rewind). seek feature will
			// work only if underlying JavaSound SPI implements
			// skip(...). True for MP3SPI and SUN SPI's
			// (WAVE, AU, AIFF). // Set Volume (0 to 1.0).
			// control.setGain(0.85);
			// Set Pan (-1.0 to 1.0).
			/*control.setPan(0.0);*/
			control.play();
			/*new Thread(){

				public void run() {
					try {
						control.play();
					} catch (BasicPlayerException e) {
						Console.log.error(e.getMessage(), e);
					}
				}
				
			}.start();*/
			//Utils.log.debug("play() complete.");
		} catch (BasicPlayerException e) {
			//Utils.log.error(e.getMessage(), e);
		}
	}
	
	public String duration(){
		long seconds = duration/(1000*1000);
		return seconds/60+":"+seconds%60;
	}

	public String nowplayed(){
		long seconds = microseconds/(1000*1000);
		return seconds/60+":"+seconds%60;
	}

	public boolean pause(){
		boolean ret =false;
		try {
			control.pause();
			ret = true;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public boolean resume(){
		boolean ret =false;
		try {
			control.resume();
			ret = true;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * Open callback, stream is ready to play.
	 * 
	 * properties map includes audio format dependant features such as bitrate,
	 * duration, frequency, channels, number of frames, vbr flag, ...
	 * 
	 * @param stream
	 *            could be File, URL or InputStream
	 * @param properties
	 *            audio stream properties.
	 */
	public void opened(Object stream, Map properties) {
		// Pay attention to properties. It's useful to get duration,
		// bitrate, channels, even tag such as ID3v2.
		
		/*mp3.crc=false, mp3.copyright=true, mp3.version.mpeg=2, mp3.channels=1, audio.leng
		th.frames=8281, mp3.framesize.bytes=104, mp3.version.layer=3, mp3.length.frames=
		8281, mp3.bitrate.nominal.bps=24000, mp3.vbr.scale=0, mp3.version.encoding=MPEG2
		L3, audio.type=MP3, audio.length.bytes=894348, vbr=false, mp3.padding=false, mp3
		.length.bytes=894348, audio.framerate.fps=27.777779, audio.channels=1, mp3.frame
		rate.fps=27.777779, duration=298116000, mp3.header.pos=0, mp3.frequency.hz=16000
		, basicplayer.sourcedataline=com.sun.media.sound.DirectAudioDevice$DirectSDL@11f
		f436, bitrate=24000, mp3.mode=3, mp3.vbr=false, audio.samplerate.hz=16000.0, mp3
		.original=true*/
		//Utils.log.debug("opened : " + properties.toString());
		Long dur = (Long)properties.get("duration");
		if(dur!=null)
			duration = dur.longValue();
		Float fp = (Float)properties.get("audio.framerate.fps");
		if(fp!=null)
			fps = fp.floatValue();
		microseconds = 0;
		Integer byts = (Integer)properties.get("audio.length.bytes");
		if(byts!=null)
			bytes = byts.intValue();
	}

	/**
	 * * Progress callback while playing.
	 * 
	 * This method is called severals time per seconds while playing. properties
	 * map includes audio format features such as instant bitrate, microseconds
	 * position, current frame number, ...
	 * 
	 * @param bytesread
	 *            from encoded stream.
	 * @param microseconds
	 *            elapsed (<b>reseted after a seek !</b>).
	 * @param pcmdata
	 *            PCM samples.
	 * @param properties
	 *            audio stream parameters.
	 */
	public void progress(int bytesread, long microseconds, byte[] pcmdata,
			Map properties) {
		// Pay attention to properties. It depends on underlying JavaSound SPI
		// MP3SPI provides mp3.equalizer.
		/*Console.log.debug("progress : " + properties.toString());*/
		if(microseconds - this.microseconds >1000*1000*3){
			Long pos = (Long)properties.get("mp3.position.byte");
			if(pos!=null && pcb!=null)
				pcb.oneSecondPlayed((double)pos.longValue()/(double)bytes);
			this.microseconds = microseconds;
		}
	}
	
	/**
	 * Notification callback for basicplayer events such as opened, eom ...
	 * 
	 * @param event
	 */
	public void stateUpdated(BasicPlayerEvent event) {
		// Notification of BasicPlayer states (opened, playing, end of media,
		// ...)
		//Utils.log.debug("stateUpdated : " + event.getCode()+","+event.getDescription());
		if(pcb!=null && event.getCode() == BasicPlayerEvent.EOM){
			pcb.endOfMedia(event.getPosition());
		}
	}

	/**
	 * A handle to the BasicPlayer, plugins may control the player through the
	 * controller (play, stop, ...)
	 * 
	 * @param controller
	 *            : a handle to the player
	 */
	public void setController(BasicController controller) {
		//Utils.log.debug("setController : " + controller);
	}

	public void seek(double pro) {
		try {
			//Utils.log.debug("about to seek to pos:"+pro+",bytes:"+bytes+",total:"+(long)(pro*bytes));
			long skipped = control.seek((long)(pro*bytes));
			microseconds = 0;
		} catch (BasicPlayerException e) {
			//Utils.log.error("",e);
		}
	}

}
