package org.sharp.swing.apps.el.ui.widget;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sharp.jdkex.Utils;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.JListItemDblClickListener;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX.ColumnsSupportX;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX.JTableHandler;
import org.sharp.utils.FsUtils;

public class FileListPanel extends JPanel {
	
	private JTableHandler<File> tableHandler;

	public FileListPanel(String title, final List<File> data, ColumnsSupportX<File> cs){
		setLayout(new BorderLayout());
		
		add(new JLabel(title),BorderLayout.NORTH);
		
		tableHandler = SwingUtils.JTableUtilsX.newJTable(
				FsUtils.fileView(),new JListItemDblClickListener(){
					public void doubleClicked(Integer index) {
						File file = Utils.elementAt(tableHandler.data(),index, null);
						SwingUtils.deskOpen(FsUtils.toCanonicalPath(file.getPath()));
					}
				},null, null);
		tableHandler.set(data, cs, null);
		add(tableHandler.jScrollPane(),BorderLayout.CENTER);
	}
	
	public void freshData(final List<File> data,ColumnsSupportX<File> cs){
		tableHandler.set(data, cs, null);
	}
	
}