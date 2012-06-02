package org.sharp.swing.apps.webdict2;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.NONE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.sharp.intf.AppContext;
import org.sharp.intf.Pluggable;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.jpwords.JpwordsUi;
import org.sharp.swing.apps.webdict.LookupUi;
import org.sharp.swing.apps.webdict2.UserData.RetCode;
import org.sharp.swing.apps.webdict2.beans.Config;
import org.sharp.swing.apps.webdict2.utils.OxfordWebDict;
import org.sharp.swing.utils.ClosableTab;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.utils.FsUtils.DirSnaps;
import org.sharp.utils.legacy.Mp3Player;

public class WebDictApp implements Pluggable {
	
	private Config config;
	
	private static class UI {
		JTextField word;
		JTextArea expUi;
		
		JButton pron;
		JButton next;
		JButton prev;
		JButton rand;
		
		JLabel msg;
		JTextField text;
		JTextArea sentence;
		JLabel msgSi;
		JLabel msgHi;
		JLabel rvc;
		JLabel lrvt;
		JLabel lookupt;
		JTextField tag;
		JLabel msgTotal;
		JButton reload;
		
		JButton ending;
		JButton beginning;
		final String DEFAULT_MSG = "ctrl+d:delete; ctrl+a:add count; " +
		"ctrl+s:substract count;ctrl+e:add to selection.";
		final String LOOKUPT_PREFIX = "looked up at:";
		final String LRVT_PREFIX = "last review at:";
		final String RVC_PREFIX = "review times:";
		static JTabbedPane ui;
	}
	
	UI ui = new UI();
	
	AppContext appCtx;
	DirSnaps dirSnaps;
	UserData userdata;
	
	public WebDictApp(){
		Utils.log.info("new LookupUi constructed.");
	}
	
	private JPanel getMainPanel(){
		userdata =  UserData.getWordList4Review(new OxfordWebDict(), new Mp3Player(null),config);
		JPanel wlPanel = new JPanel(new BorderLayout());
		JPanel np = getNaviPanel();
		wlPanel.add(np,BorderLayout.NORTH);

		ui.expUi = new JTextArea("",50,100);
		ui.expUi.setLineWrap(true);
		ui.expUi.setEditable(false);
		ui.expUi.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(ui.expUi);
		wlPanel.add(jsp,BorderLayout.CENTER);
		
		JPanel wp = getLearnPanel();
		wlPanel.add(wp,BorderLayout.SOUTH);
		Utils.log.info("return wlPanel "+wlPanel);
		
		freshTermUI(userdata.cpi);
		showSentenceHint(UserData.RetCode.EXAM_NORMAL);
		return wlPanel;
	}
	
	private JPanel getNaviPanel() {

		JPanel top = new JPanel();
		
		JCheckBox filter = new JCheckBox("No filter");
		filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(filter)
			}
		});
		top.add(filter);
		
		top.add(new JLabel("Look Up"));
		ui.word = new JTextField(10);
		Font of = ui.word.getFont();
		ui.word.setFont(of.deriveFont(of.getSize()*2.0f));
		ui.word.setFocusAccelerator('L');
		ui.word.requestFocusInWindow();
		ui.word.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lword = ui.word.getText();
				ui.msg.setText("word '"+lword+"' looking up under way.");
				RetCode code = userdata.lookup(dirSnaps,lword);
				if(code.equals(UserData.RetCode.WEB_NOT_FOUND)){
					ui.msg.setText("word '"+lword+"' not found on web.");
				}else if(code.equals(RetCode.VOICE_FOUND)){
					WebDictApp.this.freshTermUI(userdata.cpi);	
					ui.msg.setText("word '"+lword+"' looked up on web.");
				}else if(code.equals(RetCode.VOICE_NOT_FOUND)){
					ui.msg.setText("word '"+lword+"' 's voice file not found.");
				}else if(code.equals(RetCode.LOCAL_FOUND)){
					WebDictApp.this.freshTermUI(userdata.cpi);
					ui.msg.setText("word '"+lword+"' looked up in local disk.");
				}
				
			}
		});
		top.add(ui.word);

		ui.pron = new JButton("voice");
		ui.pron.setMnemonic('V');
		ui.pron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playVoice();
			}
		});
		top.add(ui.pron);

		ui.next = new JButton(">>");
		ui.next.setMnemonic('N');
		ui.next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userdata.hasNext()){
					WebDictApp.this.freshTermUI(++userdata.cpi);
					showSentenceHint(UserData.RetCode.EXAM_NORMAL);
					playVoice();
				}
			}
		});
		top.add(ui.next);

		ui.prev = new JButton("<<");
		ui.prev.setEnabled(false);
		ui.prev.setMnemonic('P');
		ui.prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userdata.hasPrevious()){
					WebDictApp.this.freshTermUI(--userdata.cpi);
					showSentenceHint(UserData.RetCode.EXAM_NORMAL);
					playVoice();
				}
			}
		});
		top.add(ui.prev);

		ui.ending = new JButton(">|");
		ui.ending.setMnemonic('E');
		ui.ending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userdata.hasNext()){
					userdata.cpi = userdata.wordCount()-1;
					WebDictApp.this.freshTermUI(userdata.cpi);
					showSentenceHint(UserData.RetCode.EXAM_NORMAL);
					playVoice();
				}
			}
		});
		top.add(ui.ending);

		ui.beginning = new JButton("|<");
		ui.beginning.setEnabled(false);
		ui.beginning.setMnemonic('B');
		ui.beginning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userdata.hasPrevious()){
					userdata.cpi = 0;
					WebDictApp.this.freshTermUI(userdata.cpi);
					showSentenceHint(UserData.RetCode.EXAM_NORMAL);
					playVoice();
				}
			}
		});
		top.add(ui.beginning);

		ui.rand = new JButton("random");
		ui.rand.setMnemonic('M');
		ui.rand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userdata.toRandom()){
					WebDictApp.this.freshTermUI(userdata.cpi);
					showSentenceHint(UserData.RetCode.EXAM_NORMAL);
					playVoice();
				}
			}
		});
		top.add(ui.rand);

		ui.reload = new JButton("reload");
		ui.reload.setMnemonic('D');
		ui.reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				learnWL();
				freshTermUI(userdata.cpi);
			}
		});
		top.add(ui.reload);

		return top;
	}
	
	private void freshTermUI(int ci){
		OxfordUrlTerm term = userdata.term(ci);
		if(term!=null){
			ui.expUi.setText(term.explString());
			ui.expUi.moveCaretPosition(0);
			ui.pron.setEnabled(term.term.getKeyvoice()!=null);
			ui.next.setEnabled(userdata.hasNext());
			ui.prev.setEnabled(userdata.hasPrevious());
		}else{
			ui.word.setText("");
			ui.expUi.setText("Type something in above box to lookup the input on web dictionary.Good luck!");
			ui.expUi.moveCaretPosition(0);
			ui.pron.setEnabled(false);
			ui.next.setEnabled(userdata.hasNext());
			ui.prev.setEnabled(userdata.hasPrevious());
		}
		
	}

	private final void playVoice(){
		if(userdata.playVoice().equals(RetCode.TERM_NOT_EXIST))
			ui.msg.setText("term at "+userdata.cpi+" not exists.");
		ui.text.requestFocusInWindow();
	}

	private JPanel getLearnPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints(0,0,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		JLabel fpad = new JLabel("msg");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,0,3,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.msg = new JLabel(ui.DEFAULT_MSG);
		panel.add(ui.msg,gbc);

		gbc = new GridBagConstraints(0,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("ss");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.msgSi = new JLabel("SI:");
		panel.add(ui.msgSi,gbc);

		gbc = new GridBagConstraints(2,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.msgHi = new JLabel("HI:");
		panel.add(ui.msgHi,gbc);
		
		gbc = new GridBagConstraints(3,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.msgTotal = new JLabel("Total:"+userdata.wordCount());
		panel.add(ui.msgTotal,gbc);
		
		gbc = new GridBagConstraints(0,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("rs");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.rvc = new JLabel(ui.RVC_PREFIX);
		panel.add(ui.rvc,gbc);

		gbc = new GridBagConstraints(2,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.lrvt = new JLabel(ui.LRVT_PREFIX);
		panel.add(ui.lrvt,gbc);
		
		gbc = new GridBagConstraints(3,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.lookupt = new JLabel(ui.LOOKUPT_PREFIX);
		panel.add(ui.lookupt,gbc);

		gbc = new GridBagConstraints(0,3,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("sentence");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,3,3,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.sentence = new JTextArea("",3,65);
		ui.sentence.setEditable(false);
		ui.sentence.setLineWrap(true);
		ui.sentence.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(ui.sentence,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setMinimumSize(jsp.getPreferredSize());
		panel.add(jsp,gbc);

		gbc = new GridBagConstraints(0,4,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("input");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,4,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		ui.text = new JTextField("",10);
		Font of = ui.text.getFont();
		ui.text.setFont(of.deriveFont(of.getSize()*2.0f));
		ui.text.revalidate();
		ui.text.setMinimumSize(ui.text.getPreferredSize());
		ui.text.setFocusAccelerator('I');
		ui.text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSentenceHint(userdata.exam(ui.text.getText()));
				playVoice();
			}
		});
		SwingUtils.addKeyListener(ui.text,new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		panel.add(ui.text,gbc);
		
		JLabel descri = new JLabel("");
		gbc = new GridBagConstraints(2,4,2,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		panel.add(descri,gbc);
		
		panel.add(new JLabel("grep"),new GridBagConstraints(0,5,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0));
		ui.tag = new JTextField(15);
		ui.tag.setMinimumSize(ui.tag.getPreferredSize());
		ui.tag.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				/*ui.addTab("Grep"+tag.getText(), null, new WebDictApp().getMainPanel(),"grep "+tag.getText());
				userdata.wl = userdata.getWordList4Key(tag.getText());
				if(userdata.wl.size()>=1){
					userdata.cpi = 0;
				}else
					userdata.cpi = -1;
				freshTermUI(userdata.cpi, userdata.cpi, false);*/
			}
		});
		panel.add(ui.tag,new GridBagConstraints(1,5,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0));
		
		JComboBox course = SwingUtils.newComboBox(
				config.getCourseNames(),
				0, true,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox)e.getSource();
						//TODO
					}
				});
		panel.add(course,new GridBagConstraints(2,5,2,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0));
		
		return panel;
	}
	
	private final void message(String mssg,Color color){
		if(mssg!=null)
			ui.msg.setText(mssg);
		if(color!=null)
			ui.msg.setForeground(color);
	}
	
	private final void showSentenceHint(RetCode examResult){
		String exp="";
		String hi="";
		String hint ="";
		
		OxfordUrlTerm term = userdata.term(userdata.cpi);
		if(term == null)
			return;

		if(UserData.RetCode.EXAM_NORMAL.equals(examResult)){
			message(ui.DEFAULT_MSG, Color.black);
		}else if(UserData.RetCode.EXAM_LEARN_ENOUGH.equals(examResult)){
			message("you've learn the term enough times," +
					"review count increased by 1.", Color.red);
		}else if(UserData.RetCode.EXAM_INPUTERROR.equals(examResult)){
			message("input error,total review times subtracted by 1!", Color.red);
		}else if(UserData.RetCode.EXAM_LEARN_ALL.equals(examResult)){
			message("you've learned all sample sentences.", Color.red);
		}else if(UserData.RetCode.EXAM_NO_SENTENCE.equals(examResult)){
			hint = "No proper sentence exists.";
			hi = "HI:";
		}

		exp = term.explString();
		ui.expUi.setText(exp);
		if(userdata.smpi==0)
			ui.expUi.setCaretPosition(0);
		else
			ui.expUi.setCaretPosition(exp.length());
		ui.msgSi.setText("SI:"+(userdata.cpi+1)+"/"+(userdata.wordCount()));
		ui.msgHi.setText(hi);
		String[] defsAndExamples = term.defsAndExamples2();
		if(defsAndExamples!=null && defsAndExamples.length > 1)
			hint = defsAndExamples[userdata.smpi];
		ui.sentence.setText(hint);
		ui.text.setText("");
		ui.text.requestFocusInWindow();
		
		ui.rvc.setText(ui.RVC_PREFIX+term.info.getRvc()+"/"+term.info.schRvc()+
				" after "+term.info.shouldReviewAfter()+" days.");
		if(term.info.getLastrvt()!=null)
			ui.lrvt.setText(ui.LRVT_PREFIX+Utils.toShortStr(term.info.getLastrvt()));
		else
			ui.lrvt.setText(ui.LRVT_PREFIX);
		ui.lookupt.setText(ui.LOOKUPT_PREFIX+Utils.toShortStr(term.info.getLookupt()));
	}

	public AppLifeCycle lifeCycle() {
		return new AppLifeCycle(){

			public void init(AppContext context) {
				if(context==null)
					throw new NullPointerException();
				appCtx = context;
				config = appCtx.getConfig(WebDictApp.class.getSimpleName(), 
						Config.class);
				dirSnaps = appCtx.requestObject(DirSnaps.class);
			}

			public void exit() {
				appCtx.saveConfig(WebDictApp.class.getSimpleName(),config);
			}
			
		};
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI(){
			public Container getUI(){
				JTabbedPane tabbedPane = new JTabbedPane();
				tabbedPane.addTab("牛津双解", null, new LookupUi().getUI(),
				                  "Oxford dictionary lookup");
				tabbedPane.addTab("Japanese", null, new JpwordsUi().getUI(),
				                  "Japanese vocabulary");
				tabbedPane.addTab("Oxford", null, getMainPanel(),"word leaning");
				tabbedPane.setTabComponentAt(2, new ClosableTab(tabbedPane));
				/*tabbedPane.addTab("GrepXXX", null, getMainPanel() ,"grep xxx");
				tabbedPane.setTabComponentAt(3, new ClosableTab(tabbedPane));*/
				
				tabbedPane.setSelectedIndex(2);
				
				return tabbedPane;
			}

			public String tabName() {
				return "Dict";
			}

			public String tabDescription() {
				return "Dictionary lookup";
			}
		};
	}
	
}
