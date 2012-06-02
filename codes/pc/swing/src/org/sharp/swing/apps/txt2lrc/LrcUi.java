package org.sharp.swing.apps.txt2lrc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import org.sharp.intf.Pluggable;
import org.sharp.jdkex.Utils;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.utils.HtmlUtils;
import org.sharp.utils.legacy.Mp3Player;

public class LrcUi implements Pluggable {
	
	private static final String STATE_INITED = "inited";
	private static final String STATE_PLAYING = "playing";
	private static final String STATE_PAUSED = "paused";
	
	private JScrollPane scrlp;
	private JFileChooser fc = new JFileChooser();;
	private File mp3file = new File("V:\\VMshare\\tale.mp3");
	private File textfile;
	private TextDoc t;
	private JTextArea text;
	private JButton play;
	private String state = STATE_INITED;
	private Mp3Player player = new Mp3Player(new Mp3Player.PlayerCallBack() {
		public void oneSecondPlayed(double progress) {
			prog.setText(player.nowplayed()+"/"+player.duration());
			markSentence(progress);
		}

		public void endOfMedia(int position) {
			play.setText("play");
			state = STATE_INITED;
		}
	});
	private JLabel prog;
	
	public LrcUi(){
		Utils.log.info("new ClassUi2 constructed.");
	}
	
	/* (non-Javadoc)
	 * @see org.sharp.txt2lrc.UI#getUI()
	 */
	public Container getUi(){
		Container ui = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
		ui.add(top,BorderLayout.NORTH);

		JButton btnMp3 = new JButton("mp3");
		btnMp3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("show UI to choose mp3 file.");
				int returnVal = fc.showOpenDialog(scrlp);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            mp3file = fc.getSelectedFile();
		        } else {
		        	Utils.log.info("Open command cancelled by user." );
		        }
			}
		});
		top.add(btnMp3);

		JButton btnText = new JButton("text");
		btnText.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("text load button pressed.");
				int returnVal = fc.showOpenDialog(scrlp);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            textfile = fc.getSelectedFile();
		            text.setText(HtmlUtils.htmlFile2Text(textfile.getPath(),text.getColumns()));
		        } else {
		        	Utils.log.info("Open command cancelled by user." );
		        }
			}
		});
		top.add(btnText);
	
		play = new JButton("play");
		play.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(state.equals(STATE_INITED) && mp3file!=null && mp3file.exists()){
					player.play(mp3file.getPath());
					play.setText("pause");
					state = STATE_PLAYING;
				}else if(state.equals(STATE_PLAYING)){
					player.pause();
					play.setText("resume");
					state = STATE_PAUSED;
				}else if(state.equals(STATE_PAUSED)){
					player.resume();
					play.setText("pause");
					state = STATE_PLAYING;
				}
				text.requestFocusInWindow();
			}
		});
		top.add(play);

		prog = new JLabel("/");
		top.add(prog);
		
		JPanel panel = null;
		panel = /*getConstructorTable((Class)clazz);*/getContentPanel();
		
		scrlp = null;
		if (panel!=null){
			scrlp = new JScrollPane(panel);
		}
		
		ui.add(scrlp,BorderLayout.CENTER);
		Utils.log.info("return ui "+ui);
		return ui;
	}
	
	private void markSentence(double pro){
		/*MutableInt begin = new MutableInt(0),end = new MutableInt(0);
		t.sentenceAt(pro, begin, end);
		Console.log.debug("pro:"+pro+",begin:"+begin.intValue()+",end:"+end.intValue());
		text.select(begin.intValue(), end.intValue());*/
		int offset = (int)(text.getDocument().getLength()*pro);
		try {
			int line = text.getLineOfOffset(offset);
			text.select(text.getLineStartOffset(line),text.getLineEndOffset(line));
		} catch (BadLocationException e) {
			Utils.log.error("",e);
		}
	}

	JPanel getContentPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
				
		GridBagConstraints gbc = null;

		gbc = new GridBagConstraints(0,0,1,1,
				0.001,0.001,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE,
				new Insets(2, 10, 2, 2),0,0);
		text = SwingUtils.newTextArea(0,50,true,true);
		text.setCaretPosition(0);
		Font of = text.getFont();
		text.setFont(of.deriveFont(of.getSize()*2.0f));
		panel.add(text,gbc);
		
		t = new TextDoc(getClass().getClassLoader().getResourceAsStream("org/sharp/swing/apps/txt2lrc/res/tale.txt")/*"V:\\VMshare\\tale.txt"*/);
		while(t.hasNextPara()){
			Sentence[] ses = t.nextPara();
			for (int i = 0; i < ses.length; i++) {
				/*textArea.append("["+((Sentence)ses[i]).posinodc+"]"+ses[i]+"\n");*/
				/*String[] ps = ses[i].toPhrases(115);*/
				//text.append("["+((Sentence)ses[i]).posindoc+"]\n");
				/*for (int j = 0; j < ps.length; j++) {
					text.append(ps[j]+"["+ps[j].length()+"]"+"\n");
				}*/
				text.append(ses[i].toString()+"\n");
			}
			/*text.append("\n");*/
		}
		text.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				ajustMp3position();
			}

		});
		
		return panel;
	}

	private void ajustMp3position() {
		double pro = text.getCaretPosition()/(double)text.getDocument().getLength();
		player.seek(pro);
	}
	
	public AppLifeCycle lifeCycle() {
		return null;
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI() {
			public Container getUI() {
				return getUi();
			}
			public String tabName() {
				return "Reader";
			}
			public String tabDescription() {
				return "Mp3 reader";
			}
		};
	}
}

