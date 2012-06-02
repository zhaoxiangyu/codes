package org.sharp.swing.apps.webdict;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.sharp.intf.UI;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.webdict.OxfordUrlTerm.SentenceHint;
import org.sharp.swing.apps.webdict.beans.Config;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.utils.legacy.Mp3Player;

public class LookupUi implements UI{
	
	private static final String LOOKUPT_PREFIX = "looked up at:";
	private static final String LRVT_PREFIX = "last review at:";
	private static final String RVC_PREFIX = "review times:";
	private static final String DEFAULT_MSG = "ctrl+d:delete; ctrl+a:add count; " +
				"ctrl+s:substract count;ctrl+e:add to selection.";
	private JTextField word;
	private JTextArea expUi;
	
	private JButton pron;
	private JButton next;
	private JButton prev;
	private JButton rand;
	
	private int cpi;
	private int smpi;
	private List<OxfordUrlTerm> wl;
	private JLabel msg;
	private JTextField text;
	private JTextArea sentence;
	private JLabel msgSi;
	private JLabel msgHi;
	private JLabel rvc;
	private JLabel lrvt;
	private JLabel lookupt;
	private UserData userdata;
	private JTextField tag;
	private JLabel msgTotal;
	private JButton reload;
	Config config = new Config();	//TODO get it from appContext

	public LookupUi(){
		Utils.log.info("new LookupUi constructed.");
		userdata = UserData.scanTerms(config);
		learnWL();
	}
	
	private void learnWL(){
		wl = userdata.getWordList4Review();
		if(wl.size()>=1){
			Collections.sort(wl, new Comparator<OxfordUrlTerm>() {
				
				public int compare(OxfordUrlTerm arg0, OxfordUrlTerm arg1) {
					return arg0.compareTo(arg1);
				}
			});
			cpi = 0;
		}else
			cpi = -1;		
	}
	
	public Container getUI(){
		
		Container ui = new JPanel(new BorderLayout());
		JPanel cp = getControlPanel();
		ui.add(cp,BorderLayout.NORTH);

		expUi = new JTextArea("",50,100);
		expUi.setLineWrap(true);
		expUi.setEditable(false);
		expUi.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(expUi);
		ui.add(jsp,BorderLayout.CENTER);
		
		JPanel wp = getWorkPanel();
		ui.add(wp,BorderLayout.SOUTH);
		Utils.log.info("return ui "+ui);
		
		freshTermUI(cpi,cpi,true);
		/*ui.setFocusable(true);
		ui.addKeyListener(new KeyAdapter() {
			
		    private void displayInfo(KeyEvent e){
		        
		        int id = e.getID();
		        String keyString;
		        if (id == KeyEvent.KEY_TYPED) {
		            char c = e.getKeyChar();
		            keyString = "key character = '" + c + "'";
		        } else {
		            int keyCode = e.getKeyCode();
		            keyString = "key code = " + keyCode
		                    + " ("
		                    + KeyEvent.getKeyText(keyCode)
		                    + ")";
		        }
		        
		        int modifiersEx = e.getModifiersEx();
		        String modString = "extended modifiers = " + modifiersEx;
		        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
		        if (tmpString.length() > 0) {
		            modString += " (" + tmpString + ")";
		        } else {
		            modString += " (no extended modifiers)";
		        }
		        
		        String actionString = "action key? ";
		        if (e.isActionKey()) {
		            actionString += "YES";
		        } else {
		            actionString += "NO";
		        }
		        
		        String locationString = "key location: ";
		        int location = e.getKeyLocation();
		        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
		            locationString += "standard";
		        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
		            locationString += "left";
		        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
		            locationString += "right";
		        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
		            locationString += "numpad";
		        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
		            locationString += "unknown";
		        }
		        Console.log.debug(keyString+"\n"+
		        		modString+"\n"+actionString+"\n"+locationString);
		        
		    }

			@Override
			public void keyTyped(KeyEvent e) {
				displayInfo(e);
				super.keyTyped(e);
			}
		});*/
		return ui;
	}
	
	private JPanel getControlPanel() {

		JPanel top = new JPanel();
		
		top.add(new JLabel("Look Up"));
		word = new JTextField(10);
		Font of = word.getFont();
		word.setFont(of.deriveFont(of.getSize()*2.0f));
		word.setFocusAccelerator('L');
		word.requestFocusInWindow();
		word.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lword = word.getText();
				msg.setText("word '"+lword+"' looking up under way.");
				
				OxfordUrlTerm term = userdata.lookup(lword);
				if(term == null){
					term = OxfordUrlTerm.lookupOxford(config.getTermsBaseDir(),lword);
					if(term == null){
						msg.setText("word '"+lword+"' lookupOxford null.");
						return;
					}else{
						if(term.attachVoiceSave(config.getVoiceLibBaseDir())){
							wl.add(term);
							cpi = wl.size()-1;
							LookupUi.this.freshTermUI(cpi,cpi,false);	
							msg.setText("word '"+lword+"' looked up on web.");
						}else{
							msg.setText("word '"+lword+"' not exists.");
						}
					}
				}else{
					cpi = wl.indexOf(term);
					LookupUi.this.freshTermUI(cpi,cpi,false);
					msg.setText("word '"+lword+"' looked up in local disk.");
				}
				
			}
		});
		top.add(word);

		pron = new JButton("voice");
		pron.setMnemonic('V');
		pron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playVoice(cpi);
			}
		});
		top.add(pron);

		next = new JButton(">>");
		next.setMnemonic('N');
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cpi+1<wl.size()&& cpi+1>=0 ){
					LookupUi.this.freshTermUI(cpi,++cpi,true);
					playVoice(cpi);
				}
			}
		});
		top.add(next);

		prev = new JButton("<<");
		prev.setEnabled(false);
		prev.setMnemonic('P');
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cpi-1<wl.size()&& cpi-1>=0 ){
					LookupUi.this.freshTermUI(cpi,--cpi,true);
					playVoice(cpi);
				}
			}
		});
		top.add(prev);

		rand = new JButton("random");
		rand.setMnemonic('M');
		rand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ncpi = Utils.nextInt(wl.size());
				if(ncpi<wl.size()&& ncpi>=0 ){
					LookupUi.this.freshTermUI(cpi,cpi=ncpi,true);
					playVoice(cpi);
				}
			}
		});
		top.add(rand);

		reload = new JButton("reload");
		reload.setMnemonic('D');
		reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				learnWL();
				freshTermUI(cpi, cpi, false);
			}
		});
		top.add(reload);

		return top;
	}
	
	private OxfordUrlTerm term(int ci){
		OxfordUrlTerm term = null;
		if(ci >= 0 && ci< wl.size()){
			/*cpi = ci;*/
			term = (OxfordUrlTerm)wl.get(ci);
		}
		return term;
	}
	
	private SentenceHint sample(int smpi){
		SentenceHint sh = null;
		OxfordUrlTerm term = term(cpi);
		if(term!=null&&term.samples!=null){
			if(smpi>=0&&smpi<term.samples.length)
				sh = term.samples[smpi];
		}
		return sh;
	}
	
	private void saveTerm(int ci){
		OxfordUrlTerm term = term(ci);
		if(term!=null)
			term.saveInfo();
	}

	private void freshTermUI(int cpi,int ci,boolean test){
		OxfordUrlTerm term = term(ci);
		if(term!=null){
			/*word.setText(term.key);*/
			expUi.setText(term.be);
			expUi.moveCaretPosition(0);
			pron.setEnabled(term.keyvoice!=null);
			next.setEnabled(ci+1<wl.size());
			prev.setEnabled(ci-1>=0);
		}else{
			word.setText("");
			expUi.setText("Type something in above box to lookup the input on web dictionary.Good luck!");
			expUi.moveCaretPosition(0);
			pron.setEnabled(false);
			next.setEnabled(ci+1<wl.size());
			prev.setEnabled(ci-1>=0);
		}
		
		if(test)
			showSentenceHint(ci,smpi=0,false);
	}

	private void playVoice(int ci){
		if(term(ci)!=null)
			new Mp3Player(null).play(term(ci).keyvoice);
		else
			msg.setText("term at "+ci+" not exists.");
		text.requestFocusInWindow();
	}

	private JPanel getWorkPanel(){
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
		msg = new JLabel(DEFAULT_MSG);
		panel.add(msg,gbc);

		gbc = new GridBagConstraints(0,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("ss");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		msgSi = new JLabel("SI:");
		panel.add(msgSi,gbc);

		gbc = new GridBagConstraints(2,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		msgHi = new JLabel("HI:");
		panel.add(msgHi,gbc);
		
		gbc = new GridBagConstraints(3,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		msgTotal = new JLabel("Total:"+userdata.totalwords());
		panel.add(msgTotal,gbc);
		
		gbc = new GridBagConstraints(0,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("rs");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		rvc = new JLabel(RVC_PREFIX);
		panel.add(rvc,gbc);

		gbc = new GridBagConstraints(2,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		lrvt = new JLabel(LRVT_PREFIX);
		panel.add(lrvt,gbc);
		
		gbc = new GridBagConstraints(3,2,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		lookupt = new JLabel(LOOKUPT_PREFIX);
		panel.add(lookupt,gbc);

		gbc = new GridBagConstraints(0,3,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		fpad = new JLabel("sentence");
		panel.add(fpad,gbc);

		gbc = new GridBagConstraints(1,3,3,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		sentence = new JTextArea("",3,65);
		sentence.setEditable(false);
		sentence.setLineWrap(true);
		sentence.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(sentence,
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
		text = new JTextField("",10);
		Font of = text.getFont();
		text.setFont(of.deriveFont(of.getSize()*2.0f));
		text.revalidate();
		text.setMinimumSize(text.getPreferredSize());
		text.setFocusAccelerator('I');
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OxfordUrlTerm term = term(cpi);
				boolean inputerror = false;
				if(term!=null&& term.key.equalsIgnoreCase(text.getText())){
					smpi++;
				}else{
					inputerror = true;
				}
				showSentenceHint(cpi,smpi,inputerror);
				playVoice(cpi);
				saveTerm(cpi);
			}
		});
		SwingUtils.addKeyListener(text,new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
		        int id = e.getID();
		        String keyString = "";
		        if (id == KeyEvent.KEY_TYPED) {
		            char c = e.getKeyChar();
		            keyString = "key character = '" + c + "'={"+(short)c+"}";
	            	OxfordUrlTerm term = term(cpi);
		            if(5 == c){ //ctrl+e
		            	if(term!=null&&sample(smpi)!=null){
		            		term.info.samplesSelected.add(sample(smpi));
		            		term.saveInfo();
		            	}
		            	msg.setText("Ctrl e pressed,current sentence added to selection.");
		            }
		            if(4 == c){ //ctrl+d
		            	if(term!=null){
		            		wl.remove(cpi);
		            		term.rmFromDisk();
		            	}
		            	if(cpi < wl.size())
		            		freshTermUI(cpi, cpi, true);
		            	msg.setText("Ctrl d pressed,current term deleted.");
		            }
		            if(1 == c){ //ctrl+a
		            	if(term!=null && term.info.rvc+1 <OxfordUrlTerm.REVIEW_DAYS.length){
		            		term.info.oneMoreReview();
		            		term.saveInfo();
		            		freshTermUI(cpi, cpi, true);
			            	msg.setText("Ctrl a pressed,review count added by 1.");		            	
		            	}
		            }
		            if(19 == c){ //ctrl+s
		            	if(term!=null && term.info.rvc-1 >=0){
	            			term.info.oneLessReview();
		            		term.saveInfo();
		            		freshTermUI(cpi, cpi, true);
			            	msg.setText("Ctrl s pressed,review count substracted by 1.");		            	
		            	}
		            }
		        }
		        
		        Utils.log.debug(keyString+"\n");
				
		        super.keyTyped(e);
			}
		});
		panel.add(text,gbc);
		
		JLabel descri = new JLabel("");
		gbc = new GridBagConstraints(2,4,2,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		panel.add(descri,gbc);
		
		panel.add(new JLabel("grep"),new GridBagConstraints(0,5,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0));
		tag = new JTextField(15);
		tag.setMinimumSize(tag.getPreferredSize());
		tag.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				wl = userdata.getWordList4Key(tag.getText());
				if(wl.size()>=1){
					cpi = 0;
				}else
					cpi = -1;
				freshTermUI(cpi, cpi, false);
			}
		});
		panel.add(tag,new GridBagConstraints(1,5,3,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0));
		
		return panel;
	}
	
	private void message(String mssg,Color color){
		if(mssg!=null)
			msg.setText(mssg);
		if(color!=null)
			msg.setForeground(color);
	}
	
	private void showSentenceHint(int cpi,int smpi, boolean inputerror){
		String exp="";
		String hi="";
		String hint ="";
		
		OxfordUrlTerm term = term(cpi);
		if(term == null)
			return;
		SentenceHint[] sh = term.samples;
		if(sh.length > 0){
			if(smpi>=0&&smpi<sh.length){
				if(smpi < UserData.termRepeatCount || smpi %UserData.termRepeatCount != 0){
					message(DEFAULT_MSG, Color.black);
				}else{
					message("you've learn the term enough times," +
							"review count increased by 1.", Color.red);
					term.info.oneMoreReview();
				}
				if(inputerror){
					message("input error,total review times subtracted by 1!", Color.red);
					term.info.oneLessReview();
				}			
			}else{
				message("you've learned all sample sentences.", Color.red);
				if(smpi %UserData.termRepeatCount == 0 || smpi < UserData.termRepeatCount){
					term.info.oneMoreReview();
				}
				smpi=0;
				this.smpi = 0;
			}
			hint = sh[smpi].hint(50);
			exp = term.defof(sh[smpi]);
			hi = "HI:"+(smpi+1)+"/"+(sh.length);
		}else{
			msg.setForeground(Color.black);
			if(smpi>0){
				message("you've learned all sample sentences.", Color.red);
				term.info.oneMoreReview();
				this.smpi = 0;
			}else{
				if(inputerror){
					message("input error,total review times subtracted by 1!", Color.red);
					term.info.oneLessReview();
				}else{
					message(DEFAULT_MSG, Color.black);
				}
			}
			hint = "No proper sentence exists.";
			exp = term.be;
			hi = "HI:";
		}		

		expUi.setText(exp);
		if(smpi==0)
			expUi.setCaretPosition(0);
		else
			expUi.setCaretPosition(exp.length());
		msgSi.setText("SI:"+(cpi+1)+"/"+(wl.size()));
		msgHi.setText(hi);
		sentence.setText(hint);
		text.setText("");
		text.requestFocusInWindow();
		
		rvc.setText(RVC_PREFIX+term.info.rvc+"/"+term.info.schRvc()+
				" after "+term.info.shouldReviewAfter()+" days.");
		if(term.info.lastrvt!=null)
			lrvt.setText(LRVT_PREFIX+Utils.toShortStr(term.info.lastrvt));
		else
			lrvt.setText(LRVT_PREFIX);
		lookupt.setText(LOOKUPT_PREFIX+Utils.toShortStr(term.info.lookupt));
	}
	
}
