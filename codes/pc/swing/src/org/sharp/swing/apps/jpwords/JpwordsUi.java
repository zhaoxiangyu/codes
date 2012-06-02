package org.sharp.swing.apps.jpwords;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.NONE;

import java.awt.BorderLayout;
import java.awt.Container;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.sharp.intf.UI;
import org.sharp.jdkex.Utils;
import org.sharp.utils.legacy.Mp3Player;

public class JpwordsUi implements UI{
	
	private static final String LOADT_PREFIX = "loaded at:";
	private static final String LRVT_PREFIX = "last review at:";
	private static final String LEVEL_PREFIX = "level:";
	private static final String DEFAULT_MSG = "no message now.";
	
	private static final int MODE_TEST = 0;
	private static final int MODE_EDIT_EXPL = 1;
	private JTextArea expUi;
	
	private JButton pron;
	private JButton next;
	private JButton prev;
	private JButton rand;
	private JButton load;
	
	private int cpi;
	private int smpi;
	private int mode = MODE_TEST;
	private List wl;
	private JLabel msg;
	private JTextField text;
	private JTextArea sentence;
	private JLabel msgSi;
	private JLabel level;
	private JLabel lrvt;
	private JLabel loadt;
	private UserData userdata;

	public JpwordsUi(){
		Utils.log.info("new JpwordsUi constructed.");
		userdata = UserData.scanJpwords();
		freshWordList(userdata,0);
	}
	
	private void freshWordList(UserData userdata,int level){
		wl = userdata.getWordList(level);
		if(wl.size()>1){
			Utils.log.debug("total "+wl.size()+" words loaded.");
			Collections.sort(wl, new Comparator() {
				
				public int compare(Object arg0, Object arg1) {
					JpTerm t0=(JpTerm)arg0;
					JpTerm t1=(JpTerm)arg1;
					return t0.compareTo(t1);
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
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(expUi);
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
		
		load = new JButton("disk load");
		load.setMnemonic('D');
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userdata = UserData.searchJpwords();
				freshWordList(userdata,0);
			}
		});
		top.add(load);

		pron = new JButton("voice");
		pron.setMnemonic('V');
		pron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playVoice();
			}
		});
		top.add(pron);

		next = new JButton(">>");
		next.setMnemonic('N');
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cpi+1<wl.size()&& cpi+1>=0 ){
					JpwordsUi.this.freshTermUI(cpi,++cpi,true);
					playVoice();
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
					JpwordsUi.this.freshTermUI(cpi,--cpi,true);
					playVoice();
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
					JpwordsUi.this.freshTermUI(cpi,cpi=ncpi,true);
					playVoice();
				}
			}
		});
		top.add(rand);
		
		JRadioButton l0 = new JRadioButton("L0");
		l0.setActionCommand("l0");
		l0.setSelected(true);
		JRadioButton l1 = new JRadioButton("L1");
		l1.setActionCommand("l1");
		JRadioButton l2 = new JRadioButton("L2");
		l2.setActionCommand("l2");
		ButtonGroup lg = new ButtonGroup();
		lg.add(l0);
		lg.add(l1);
		lg.add(l2);
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if("l0".equals(e.getActionCommand())){
					freshWordList(userdata,0);
				}else if("l1".equals(e.getActionCommand())){
					freshWordList(userdata,1);
				}else if("l2".equals(e.getActionCommand())){
					freshWordList(userdata,2);
				}
				freshTermUI(cpi, cpi, true);
			}
		};
		l0.addActionListener(al);
		l1.addActionListener(al);
		l2.addActionListener(al);
		
		top.add(l0);
		top.add(l1);
		top.add(l2);

		return top;
	}
	
	private JpTerm term(int ci){
		JpTerm term = null;
		if(ci >= 0 && ci< wl.size()){
			/*cpi = ci;*/
			term = (JpTerm)wl.get(ci);
		}
		return term;
	}
	
	private void saveTerm(int ci){
		JpTerm term = term(ci);
		if(term!=null)
			term.save();
	}

	private void freshTermUI(int cpi,int ci,boolean test){
		JpTerm term = term(ci);
		if(term!=null){
			expUi.setText(term.expl());
			expUi.moveCaretPosition(0);
			pron.setEnabled(term.keyvoice!=null);
			next.setEnabled(ci+1<wl.size());
			prev.setEnabled(ci-1>=0);
		}else{
			expUi.setText("Type something in above box to lookup the input on web dictionary.Good luck!");
			expUi.moveCaretPosition(0);
			pron.setEnabled(false);
			next.setEnabled(ci+1<wl.size());
			prev.setEnabled(ci-1>=0);
		}
		freshWorkPanel(ci,0, false);
	}

	private void playVoice(){
		new Mp3Player(null).play(term(cpi).keyvoice);
	}

	private JPanel getWorkPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints(0,0,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		msgSi = new JLabel("SI:");
		panel.add(msgSi,gbc);

		gbc = new GridBagConstraints(1,0,2,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		msg = new JLabel(DEFAULT_MSG);
		panel.add(msg,gbc);

		gbc = new GridBagConstraints(0,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		level = new JLabel(LEVEL_PREFIX);
		panel.add(level,gbc);

		gbc = new GridBagConstraints(1,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		lrvt = new JLabel(LRVT_PREFIX);
		panel.add(lrvt,gbc);
		
		gbc = new GridBagConstraints(2,1,1,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		loadt = new JLabel(LOADT_PREFIX);
		panel.add(loadt,gbc);

		gbc = new GridBagConstraints(0,2,3,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		sentence = new JTextArea("",5,70);
		sentence.setEditable(false);
		sentence.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(sentence,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setMinimumSize(jsp.getPreferredSize());
		panel.add(jsp,gbc);

		gbc = new GridBagConstraints(0,3,3,1,
				0.001,0.001,FIRST_LINE_START,NONE,
				new Insets(2, 10, 2, 2),0,0);
		text = new JTextField("",25);
		text.setFocusAccelerator('I');
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JpTerm term = term(cpi);
				if(mode == MODE_TEST){
					boolean inputerror = false;
					if(term!=null&& term.writting().equalsIgnoreCase(text.getText())){
						/*Console.log.debug("input correct");*/
					}else{
						inputerror = true;
					}
					playVoice();
					freshWorkPanel(cpi, smpi, inputerror);
					saveTerm(cpi);
				}else if(mode == MODE_EDIT_EXPL){
					if(term!=null){
						term.oExpl.setMeaning(text.getText());
						saveTerm(cpi);
					}
					mode = MODE_TEST;
				}
			}
		});
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
		        int id = e.getID();
		        String keyString = "";
		        if (id == KeyEvent.KEY_TYPED) {
		            char c = e.getKeyChar();
		            keyString = "key character = '" + c + "'={"+(short)c+"}";
	            	JpTerm term = term(cpi);
	            	if(2 == c){ //ctrl+b blunder explanation
	            		if(term!=null)
	            			term.oExpl.setError(!term.oExpl.isError());
	            	}else if(5 == c){ //ctrl+e edit explanation
	            		mode = MODE_EDIT_EXPL;
	            	}else if(mode == MODE_TEST){
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
			            	if(term!=null ){
			            		term.info.oneLevelUp();
			            		saveTerm(cpi);
			            		freshTermUI(cpi, cpi, true);
				            	msg.setText("Ctrl a pressed,review count added by 1.");		            	
			            	}
			            }
			            if(19 == c){ //ctrl+s
			            	if(term!=null && term.info.level-1 >=0){
		            			term.info.oneLevelDown();
		            			saveTerm(cpi);
			            		freshTermUI(cpi, cpi, true);
				            	msg.setText("Ctrl s pressed,review count substracted by 1.");		            	
			            	}
			            }
	            	}
		        }
		        
		        Utils.log.debug(keyString+"\n");
				
		        super.keyTyped(e);
			}
		});
		text.setMinimumSize(text.getPreferredSize());
		panel.add(text,gbc);

		return panel;
	}
	
	private void freshWorkPanel(int cpi,int smpi, boolean inputerror){
		String hint ="";	
		JpTerm term = term(cpi);
		if(term == null)
			return;
		
		msgSi.setText("SI:"+(cpi+1)+"/"+(wl.size()));
		hint = term.writting()+"\n"+term.spellHint();
		sentence.setText(hint);
		text.setText("");
		text.requestFocusInWindow();
		if(inputerror)
			msg.setText("input error");
		else
			msg.setText("input correct");
		
		level.setText(LEVEL_PREFIX+term.info.level+"/"+term.info.schRvc());
		if(term.info.lastrvt!=null)
			lrvt.setText(LRVT_PREFIX+term.info.lastrvt);
		else
			lrvt.setText(LRVT_PREFIX);
		loadt.setText(LOADT_PREFIX+term.info.lookupt);
	}

}
