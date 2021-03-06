package org.sharpx.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.sharpx.swing.intf.AppContext;
import org.sharpx.swing.intf.Pluggable;
import org.sharpx.swing.intf.Pluggable.AppLifeCycle;
import org.sharpx.swing.intf.Pluggable.TabbedUI;
import org.sharpx.utils.FjUtils;
import org.sharpx.utils.jdkex.JdkUtils;
import org.sharpx.utils.FsUtils;
import org.sharpx.utils.LangUtils;
import org.sharpx.utils.SecurityUtils;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.beans.Config;
import org.sharpx.swing.beans.Constants;
import org.sharpx.swing.beans.UserInfo;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.TrayUtils;

public class Console {

	public final static String CONF_FILENAME = "./config.xml";

	/*private static Pluggable[] modules = {new LogUi(),new DirSnapsApp(),
		new DocReaderApp(),new WebDictApp(),new CrosswordUi(),new LrcUi(),
		new Sample(),new ClassUi(Socket.class)};*/
	private static Pluggable[] modules = null;
	
	static AppContext appContext = new AppContext() {

		HashMap<Class, Object> sharedObjects = new HashMap<Class,Object>();
		
		public String dataDirPath() {
			return Constants.userDir;
		}

		public <T> T getConfig(String name, Class<T> clazz) {
			return FsUtils.loadJox2(
					FsUtils.fnConcat(dataDirPath(),name+".xml"), 
					clazz, DsUtils.newInstance(clazz));
		}

		public <T> void saveConfig(String name, T config) {
			try {
				FsUtils.saveJox2(
						FsUtils.fnConcat(dataDirPath(),name+".xml"), 
						config);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public <T> T requestObject(Class<T> clazz) {
			return (T)sharedObjects.get(clazz);
		}

		public void shareObject(Object o) {
			sharedObjects.put(o.getClass(), o);
		}
	};

	private static int startModuleIndex = 1;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		Config.load(CONF_FILENAME);

		startSwingUI();
		/*SocketSer.startShutdownListener(2011);*/
	}
	
	static void startSwingUI(){

		final JFrame frame = new JFrame("Swing Gadgets, Build "+fetchBuildNum());

		Console.checkLicense(frame);
		/*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/

		for(int i=0;i<modules().length;i++){
			AppLifeCycle lc = modules()[i].lifeCycle();
			if(lc != null)
				lc.init(appContext);
		}
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				//Utils.log.debug("windowClosing called.");
				for(int i=modules().length-1;i>=0;i--){
					AppLifeCycle alc = modules()[i].lifeCycle();
					if(alc!=null)
						alc.exit();
				}
				Config.config.save(CONF_FILENAME);
				System.exit(0);
			}
			
		});
		/*new Intropector(frame.getContentPane()).start();*/
		
		/*Socket.class;System.class;SampleBean.class;new JFrame();new StringBuffer();new SampleBean();*/
		/*Object obj = new Socket();*/
		frame.setSize(1024, 768);
		frame.setLocation(100, 30);
		//frame.setIconImage(SwingUtils.newImageIcon("org/sharp/res/Compass-icon.png").getImage());
		
		/*initMenus(frame);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new LookupUi().getUI(),BorderLayout.CENTER);
		frame.getContentPane().validate();*/
		/*frame.add(getMainPanel());*/
		frame.add(getTabs());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame.setVisible(true);
		TrayUtils.message("app started.");
	}
	
	static Pluggable[] modules(){
		if(modules == null)
			modules = loadModules();
		return modules;
	}
	
	static void saveModules(String file,Pluggable[] modules){
		String[] ma = FjUtils.classNames(modules);
		String mns = LangUtils.join(ma, ";");
		Properties p = new Properties();
		p.setProperty("modules.load", mns);
		try {
			p.store(new FileOutputStream(new File(file)), "modules definitions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setModules(Pluggable[] pma){
		modules = pma;
	}
	
	public static void setStartModule(int i){
		startModuleIndex  = i;
	}
	
	static Pluggable[] loadModules(){
		return loadModules(JdkUtils.getResourceAsReader("org/sharpx/swing/apps/modules.properties"));
	}

	@Deprecated
	static Pluggable[] loadModules(String file){
		try {
			return loadModules(new FileReader(new File(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Pluggable[] loadModules(Reader reader){
		Properties p = new Properties();
		try {
			p.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mns = p.getProperty("modules.load");
		String[] mcna = LangUtils.split(mns, ";");
		Pluggable[] ma = FjUtils.newInstances(mcna, Pluggable[].class);
		return ma;
	}
	
	private static String fetchBuildNum(){
		Properties p = new Properties();
		try {
			p.load(JdkUtils.getResourceAsReader("org/sharpx/swing/apps/runtime.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String bn = p.getProperty("buildnum");
		String bt = p.getProperty("buildtime");
		return bn+" @"+bt;
	}
	
	static Component getTabs() {
		JTabbedPane tabbedPane = new JTabbedPane();

		for(int i=0;i<modules().length;i++){
			TabbedUI tu = modules()[i].tabbedUI();
			if(tu!=null){
				try{
					tabbedPane.addTab(tu.tabName(), null, tu.getUI(),
						tu.tabDescription());
					/*tabbedPane.setMnemonicAt(1, KeyEvent.VK_R);*/
					/*tabbedPane.setMnemonicAt(3, KeyEvent.VK_C);*/
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		/*Container videoUi = new VideoUi().getUI();
		tabbedPane.addTab("Video", null, videoUi,
		                  "Video");*/

		/*Container slidingBlock = new ChineseSlidingBlock().getUI();
		tabbedPane.addTab("SlidingBlock", null, slidingBlock,
		                  "SlidingBlock");*/

		/*Container lnkUi = null;
		try {
			lnkUi = new LnkUi().getUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tabbedPane.addTab("Lnk", null, lnkUi,
		                  "Lnk");*/
		
		tabbedPane.setFont(SwingUtils.deriveFont(tabbedPane.getFont(),1.5f, null));
		
		tabbedPane.setSelectedIndex(startModuleIndex);
		return tabbedPane;
	}

	@Deprecated
	private static void initToolbar(final JPanel panel) {
		final JToolBar toolbar = new JToolBar("Tool bar");
		ActionListener toolbarListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		        String cmd = e.getActionCommand();
		        if ("dict".equals(cmd)){
		        	panel.removeAll();
		    		panel.add(toolbar,BorderLayout.PAGE_START);
		        	/*panel.add(dict.getUI(),BorderLayout.CENTER);*/
		        	panel.validate();
		        }/*else if("reader".equals(cmd)){
		        	panel.removeAll();
		    		panel.add(toolbar,BorderLayout.PAGE_START);
		        	panel.add(reader.getUI(),BorderLayout.CENTER);
		        	panel.validate();
		        }else if("jpwords".equals(cmd)){
		        	panel.removeAll();
		    		panel.add(toolbar,BorderLayout.PAGE_START);
		        	panel.add(jpword.getUI(),BorderLayout.CENTER);
		        	panel.validate();
		        }else if("crossword".equals(cmd)){
		        	panel.removeAll();
		    		panel.add(toolbar,BorderLayout.PAGE_START);
		        	panel.add(crossword.getUI(),BorderLayout.CENTER);
		        	panel.validate();
		        }*/

			}
		};

		JButton button = new JButton();
		button.setActionCommand("dict");
		button.setToolTipText("Web dict");
		button.addActionListener(toolbarListener);
		button.setText("Dict");
		/*button.setDefaultCapable(false);*/
		toolbar.add(button);
		/*dict = new LookupUi();*/

		/*button = new JButton();
		button.setActionCommand("reader");
		button.setToolTipText("Audio book reader");
		button.addActionListener(toolbarListener);
		button.setText("Reader");
		button.setDefaultCapable(false);
		toolbar.add(button);
		reader = new LrcUi();
		
		button = new JButton();
		button.setActionCommand("jpwords");
		button.setToolTipText("Japanese words");
		button.addActionListener(toolbarListener);
		button.setText("Jpwords");
		button.setDefaultCapable(false);
		toolbar.add(button);
		jpword = new JpwordsUi();
		
		button = new JButton("crossword");
		button.setActionCommand("crossword");
		button.setToolTipText("Cross word");
		button.addActionListener(toolbarListener);
		button.setText("Crossword");
		toolbar.add(button);
		crossword = new CrosswordUi();*/
		
		/*toolbar.setFocusable(false);*/
		panel.add(toolbar,BorderLayout.PAGE_START);
	}

	@Deprecated
	private static void initMenus(final JFrame frame){
		
		final JMenuBar jmb = new JMenuBar();
		frame.setJMenuBar(jmb);
		JMenu menuGadgets = new JMenu("Gadgets");
		menuGadgets.setMnemonic('G');
		
		JMenuItem mi_classui = new JMenuItem("Class UI");
		mi_classui.setAccelerator(KeyStroke.getKeyStroke('C',InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		/*mi_classui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new ClassUi().getUI(Socket.class),0);
				frame.getContentPane().validate();
				frame.setJMenuBar(jmb);
			}
		});*/
		menuGadgets.add(mi_classui);
		
		/*JMenuItem mi_beanui = new JMenuItem("Bean UI");
		mi_beanui.setAccelerator(KeyStroke.getKeyStroke('B',InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		menuGadgets.add(mi_beanui);
		mi_beanui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				BeanUi beanUi = new BeanUi();
				beanUi.setFartherUi(frame.getContentPane());
				beanUi.updateUI(frame);
				frame.getContentPane().validate();
			}
		});*/
		
		/*JMenuItem mi_vmui = new JMenuItem("VM UI");
		mi_vmui.setAccelerator(KeyStroke.getKeyStroke('V',InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		menuGadgets.add(mi_vmui);
		mi_vmui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				new VMUI(frame.getContentPane()).start();
				frame.getContentPane().validate();
			}
		});
		
		JMenuItem mi_dictui = new JMenuItem("Dict UI");
		mi_dictui.setAccelerator(KeyStroke.getKeyStroke('D',InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		menuGadgets.add(mi_dictui);
		mi_dictui.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new LookupUi().getUI());
				frame.getContentPane().validate();
			}
		});
		jmb.add(menuGadgets);
		
		JMenuItem mi_lrceditorui1 = new JMenuItem("Lrc editor UI");
		mi_lrceditorui1.setAccelerator(KeyStroke.getKeyStroke('L',InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		 */
		/*mi_lrceditorui1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new LrcUi().getUI());
				frame.getContentPane().validate();
				frame.setJMenuBar(jmb);
			}
		});*/
		/*menuGadgets.add(mi_lrceditorui1);*/
	}

	private static void checkLicense(Frame frame) {
		String machineCode = SecurityUtils.registerNumber();
		/*final JOptionPane optionPane = new JOptionPane(
	            "Your machine code is "+machineCode+",Enter your register code:",
	            JOptionPane.QUESTION_MESSAGE,
	            JOptionPane.YES_NO_OPTION);
	
		final JDialog dialog = new JDialog(frame, 
		                             "Reister Code",
		                             true);
		dialog.setContentPane(optionPane);*/
		/*dialog.setDefaultCloseOperation(
		    JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		dialog.setVisible(true);*/
		
		boolean ret = false;
		UserInfo ui = FsUtils.loadJox2("userinfo.txt",UserInfo.class, new UserInfo());
		ret = SecurityUtils.checkKey(machineCode, ui.getUserKey());
		if(ret)
			return;
			
		System.out.println("register code:"+SecurityUtils.genKey(machineCode));
		String regcode = JOptionPane.showInputDialog("machine code:"+
				machineCode+",Enter your register code:");
		ret = SecurityUtils.checkKey(machineCode, regcode);
		if(ret){
			ui.setUserKey(regcode);
			try {
				FsUtils.saveJox2("userinfo.txt", ui);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(frame,"register code wrong!",
					"Error",JOptionPane.ERROR_MESSAGE);
			System.out.println("register code not valid, app quit.");
			System.exit(0);
		}
	}

}
