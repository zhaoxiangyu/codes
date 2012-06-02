package org.sharp.pm.view;

import java.util.List;

import org.sharp.pm.intf.ListItemViewer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListViewTemplateAdapter<T> extends BaseAdapter {
	
	List<T> data;
	ListItemViewer<T> liv;
	
	public ListViewTemplateAdapter(List<T> list, ListItemViewer<T> liv){
		data = list;
		this.liv = liv;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		T item = (T)getItem(position);
		if(convertView == null || convertView.getTag() == null){
			view = convertView;
			liv.freshView(view, item);
		}else{
			view = liv.newView(item);
		}
		return view;
	}

}
