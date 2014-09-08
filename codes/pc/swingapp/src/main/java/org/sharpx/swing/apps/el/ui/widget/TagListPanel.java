package org.sharpx.swing.apps.el.ui.widget;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.TransferHandler;

import org.apache.commons.lang3.ArrayUtils;
import org.sharpx.utils.LangUtils;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.apps.el.beans.Tag;
import org.sharpx.swing.apps.el.intf.TagHandler;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JListItemClickListener;
import org.sharpx.swing.utils.SwingUtils.JListItemDblClickListener;

public class TagListPanel<T extends Object> extends JPanel {
	private Tag tag;
	private Tag rt;
	private Stack<Tag> opened = new Stack<Tag>();
	private TagHandler th;
	private TagEventHandler<List<T>> teh;
	private Class<T> clazz;

	JTextField tagNameTF;
	JList<Tag> jl;
	
	public static interface TagEventHandler<T>{
		void tagDelete(String tagName);
		void dataDropOnTag(T data,String sTagName, String cTagName);
		void tagSwitched(String tagName);
	}
	
	public TagListPanel(Tag root, TagHandler handler,TagEventHandler<List<T>> eventHandler, Class<T> claz, JListItemClickListener cl){
		setLayout(new BorderLayout());
		th = handler;
		teh = eventHandler;
		clazz = claz;
		
		JPanel control = new JPanel();
		control.add(new JLabel("Add Tag:"));
		tagNameTF = SwingUtils.newTextField(null,20, true, null);
		tagNameTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tn = tagNameTF.getText();
				if(tn!=null && !"".equals(tn.trim())){
					Tag nt = new Tag(tagNameTF.getText(),null);
					Tag[] c = ArrayUtils.add(tag.getChildren(), 0, nt);
					tag.setChildren(c);
					showTag(tag);
					if(th!=null)
						th.saveTag(rt);
				}
			}
		});
		control.add(tagNameTF);
		add(control, BorderLayout.NORTH);
		
		jl = SwingUtils.newJList4Tag(null,
				null, dcl, cl, tagListTransferHandler);
		jl.setCellRenderer(SwingUtils.newListCellRenderer(
				"/com/sun/java/swing/plaf/windows/icons/Directory.gif"));
		SwingUtils.addKeyListener(jl, new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
		        int id = e.getID();
		        if (id == KeyEvent.KEY_RELEASED) {
		            int kc = e.getKeyCode();
		            String kt = KeyEvent.getKeyText(kc);
		            if(kc == KeyEvent.VK_DELETE){
		            	Tag s = jl.getSelectedValue();
		            	deleteChildTag(s);
		            }
		            //Utils.log.debug("key:"+kt+" released.");
		        }
		        super.keyTyped(e);
			}
		});
		add(new JScrollPane(jl), BorderLayout.CENTER);
		showRootTag(root);
	}
	
	public Tag tag(){
		return tag;
	}
	
	public void showRootTag(Tag root){
		rt = root;
		tag = root;
		opened.push(rt);
		showTag(rt);
	}
	
	private void showTag(Tag t){
		if(t==null)
			return;
		
		tag = t;
		Tag p = opened.peek();
		DsUtils.sort(t.getChildren());
		Tag[] c = LangUtils.add(t.getChildren(), 0, p);
		jl.setListData(c);
	}
	
	private void deleteChildTag(Tag t){
    	if(t!= rt){
    		tag.removeChild(t);
    		teh.tagDelete(t.getName());
    		if(th!=null)
    			th.saveTag(rt);
    		
    		showTag(tag);
    	}
	}
	
	TransferHandler tagListTransferHandler =  new TransferHandler(){
	    public boolean canImport(TransferHandler.TransferSupport info) {
	        if (!info.isDataFlavorSupported(new DataFlavor(clazz,clazz+" being transfered"))) {
	            return false;
	        }
	        return true;
	   }

	    public boolean importData(TransferHandler.TransferSupport info) {
	        if (!info.isDrop()) {
	            return false;
	        }

	        JList<Tag> list = (JList<Tag>)info.getComponent();
	        ListModel<Tag> listModel = list.getModel();
	        JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
	        int index = dl.getIndex();
	        Tag st = listModel.getElementAt(index);
	        boolean insert = dl.isInsert();
	        if(insert)
	        	return false;

	        Transferable t = info.getTransferable();
	        List<T> data = null;
	        try {
	            data = (List<T>)t.getTransferData(new DataFlavor(clazz,""));
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
	        if(data!=null){
	        	teh.dataDropOnTag(data,st.getName(),tag.getName());
	        }
	                                
	        return true;
	    }

	    public int getSourceActions(JComponent c) {
	        return TransferHandler.COPY;
	    }
	    
	    protected Transferable createTransferable(JComponent c) {
	        JList list = (JList)c;
	        final Object value = list.getSelectedValue();
	        return new Transferable() {
				public boolean isDataFlavorSupported(DataFlavor flavor) {
					if(flavor.equals(new DataFlavor(Tag.class,"")))
						return true;
					return false;
				}
				
				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[]{new DataFlavor(Tag.class,"")};
				}
				
				public Object getTransferData(DataFlavor flavor)
						throws UnsupportedFlavorException, IOException {
					return value;
				}
			};
	    }
	    
	    protected void exportDone(JComponent c, Transferable data, int action) {
	        JList source = (JList)c;
	        if (action == TransferHandler.MOVE) {
	        }
	    }
	};
	
	JListItemDblClickListener dcl = new JListItemDblClickListener(){
		public void doubleClicked(Integer index) {
			System.out.println("tag:"+index+",name:"+tag.getName());
			if(--index == -1){
				if(opened.peek() != rt)
					showTag(opened.pop());
				else
					showTag(opened.peek());
			}else{
				opened.push(tag);
				showTag(tag.getChildren()[index]);
			}
			teh.tagSwitched(tag.getName());
		}
	};
}