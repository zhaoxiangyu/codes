package org.sharpx.swing.apps.el.ui.widget;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sharpx.utils.DsUtils;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JListItemDblClickListener;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX.ColumnsSupportX;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX.JTableHandler;
import org.sharpx.utils.FsUtils;

public class FileListPanel extends JPanel {
	
	private JTableHandler<File> tableHandler;

	public FileListPanel(String title, final List<File> data, ColumnsSupportX<File> cs){
		setLayout(new BorderLayout());
		
		add(new JLabel(title),BorderLayout.NORTH);
		
		tableHandler = SwingUtils.JTableUtilsX.newJTable(
				SwingUtils.fileView(),new JListItemDblClickListener(){
					public void doubleClicked(Integer index) {
						File file = DsUtils.elementAt(tableHandler.data(),index, null);
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