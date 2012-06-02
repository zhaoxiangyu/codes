package org.sharp.android.widget;

import java.util.ArrayList;
import java.util.List;

import org.sharp.utils.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ToggleButton;

public class SingleLineGridView extends GridView {
	
	public static interface ClickListener {
		void OnClick(View v, int pos);
	}

	List<ToggleButton> tbList = new ArrayList<ToggleButton>();

	public SingleLineGridView(final Context ctx,
			final String[] labels, final int defSel, final ClickListener cl) {
		super(ctx);
		
		setGravity(Gravity.CENTER);
		setNumColumns(labels.length);
		setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		setAdapter(new ArrayAdapter<String>(ctx, 0, labels){
			@Override
			public View getView(final int position, View convertView, ViewGroup parent){
				ToggleButton btn = null;
				if(position >= 0 && position < tbList.size() && tbList.get(position) != null){
					btn = tbList.get(position);
				}else if(!(position < tbList.size()) 
						|| tbList.get(position) == null ){
					btn =  WidgetUtils.newToggleButton(ctx, 
						labels[position],
						labels[position],
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								check(position);
								if(cl != null)
									cl.OnClick(v, position);
							}
						},position == defSel);
					Utils.setListElement(tbList, position, btn);
				}
				return btn;
			}
		});
	}
	
	public boolean check(int pos){
		if(pos >=0 && pos < tbList.size()){
			for(int i = 0 ;i < tbList.size(); i++){
				ToggleButton tb = tbList.get(i);
				if(tb!=null)
					tb.setChecked(pos==i);
			}
			return true;
		}else
			return false;
	}
	
}
