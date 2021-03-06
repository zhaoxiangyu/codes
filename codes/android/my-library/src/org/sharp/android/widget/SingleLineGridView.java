package org.sharp.android.widget;

import java.util.ArrayList;
import java.util.List;

import org.sharp.android.viewlet.CheckableBGCText;
import org.sharp.android.viewlet.intf.CheckableViewlet;
import org.sharp.utils.LogTags;
import org.sharp.utils.Utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class SingleLineGridView extends GridView {
	
	public static interface ClickListener {
		void OnClick(View v, int pos);
	}

	List<CheckableViewlet> tbList = new ArrayList<CheckableViewlet>();
	ArrayAdapter<String> adapter;

	public SingleLineGridView(final Context ctx,
			final String[] labels, final int defSel, final ClickListener cl) {
		super(ctx);
		
		WidgetUtils.setup(this, Gravity.CENTER, 
				labels.length, 
				GridView.STRETCH_COLUMN_WIDTH);
		int i = 0;
		for(String label:labels){
			CheckableViewlet cv = new CheckableBGCText(ctx, 
					label, 
					defSel == i);
			tbList.add(cv);
			i++;
		}
		adapter = new ArrayAdapter<String>(ctx, 0, labels){
			@Override
			public View getView(final int position, View convertView, ViewGroup parent){
				CheckableViewlet cv = null;
				if(position >= 0 && position < tbList.size() 
						&& tbList.get(position) != null){
					cv = tbList.get(position);
					Log.d(LogTags.LT_VIEW, "got button "+position+" from list");
				}else if(!(position < tbList.size()) 
						|| tbList.get(position) == null ){
					cv = new CheckableBGCText(ctx, 
							labels[position], 
							position == defSel);
					Log.d(LogTags.LT_VIEW, "create new button "+position+" at position "+position);
					Utils.setListElement(tbList, position, cv);
				}
				cv.setText(labels[position]);
				return cv.getView();
			}
		};
		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
							Log.d(LogTags.LT_VIEW,"view at "+position+" clicked");
							check(position);
							if(cl != null)
								cl.OnClick(view, position);
						}
			}
		);
	}
	
	public List<CheckableViewlet> checkableViewletList(){
		return tbList;
	}
	
	private boolean check(int pos){
		if(pos >=0 && pos < tbList.size()){
			for(int i = 0 ;i < tbList.size(); i++){
				CheckableViewlet cv = tbList.get(i);
				if(cv!=null){
					cv.setChecked(pos==i);
					Log.d(LogTags.LT_VIEW, "checked status at "+i+" set to "+(pos == i));
				}
			}
			return true;
		}else
			return false;
	}

	public void dataChanged() {
		adapter.notifyDataSetChanged();
	}
	
}
