package org.sharp.android.view;

import my.library.R;

import org.sharp.android.autils.AUtils;
import org.sharp.beans.TabItem;
import org.sharp.beans.TabItem2;
import org.sharp.utils.Option;
import org.sharp.utils.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class ViewUtils {
	
	public static void populateTabItem(TabHost tabHost,Context ctx, TabItem ti) {  
        
		View view = View.inflate(ctx, R.layout.tab_item, null); 
        ((ImageView) view.findViewById(R.id.tab_item_imageview))  
                .setImageResource(ti.imageResId);  
        ((TextView) view.findViewById(R.id.tab_item_textview)).setText(ti.label);
  
        TabSpec spec = tabHost.newTabSpec(Utils.uniqueString()).setIndicator(view)  
                .setContent(ti.intent);
        tabHost.addTab(spec);  
    }  

	public static void populateTabItem2(TabHost tabHost,Context ctx, TabItem ti) {  
        
        TabSpec spec = tabHost.newTabSpec(Utils.uniqueString());
        if(ti.imageResId != null)
        	spec.setIndicator(ti.label, 
        			AUtils.loadDrawable(ctx, ti.imageResId));
        else
        	spec.setIndicator(ti.label);
        spec.setContent(ti.intent);
        tabHost.addTab(spec); 
    }  

	public static void populateTabItem(TabHost tabHost,Context ctx, final TabItem2 ti) {  
		TabContentFactory tcf = new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				if(tag != null && tag.equals(ti.tag))
					return ti.content;
				else
					return null;
			}
		};
  
        TabSpec spec = tabHost.newTabSpec(ti.tag).setIndicator(ti.indicator)  
                .setContent(tcf);
        tabHost.addTab(spec);  
    }  
	
	public static void showToast(Context ctx,int strId){
		Toast.makeText(ctx, 
				strId, 
				Toast.LENGTH_SHORT).show();
	}
	
	public static void showToast(Context ctx,String msg){
		Toast.makeText(ctx, 
				msg, 
				Toast.LENGTH_SHORT).show();
	}

	public static View findView(Context ctx, int layoutResourceid,int viewid){
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(layoutResourceid, null);
        return root.findViewById(viewid);
	}

	public static void showDialog(Context ctx, String title, String[] items,
			DialogInterface.OnClickListener cl) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setItems(items, cl);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showDialog(Context ctx, String title, String message,
			String positiveMsg, DialogInterface.OnClickListener pcl,
			String negativeMsg, DialogInterface.OnClickListener ncl) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveMsg, pcl);
		builder.setNegativeButton(negativeMsg, ncl);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showCustomInfoDialog(Context ctx, String title, View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		if(title!=null)
			builder.setTitle(title);
		if(view != null )
			builder.setView(view);
		builder.setNeutralButton(ctx.getString(my.library.R.string.dlg_button_close), 
			new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface di, int id) {
					di.dismiss();
				}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	public static <T> void showDialog(Context ctx,String title,T[] items,int resourceid,
			int textViewResourceId, DialogInterface.OnClickListener cl,int initialPos){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setAdapter(new ArrayAdapter<T>(ctx,resourceid,
				textViewResourceId,items),cl);
		AlertDialog alert = builder.create();
		alert.show();
		ListView listView = alert.getListView();
		listView.setSelection(initialPos);
	}
	
	public static interface ViewGetter<T> {
		View getView(T data,ViewGroup parent);
	}
	
	public static <T> void showDialog2(Context ctx,String title,T[] items,int resourceid,
			DialogInterface.OnClickListener cl,int initialPos,
			final ViewGetter<T> vs){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setAdapter(new ArrayAdapter<T>(ctx,resourceid,items){
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					//super.getView(position, convertView, parent);
					T item = getItem(position);
					return vs.getView(item,parent);
				}
			},cl);
		AlertDialog alert = builder.create();
		alert.show();
		ListView listView = alert.getListView();
		listView.setSelection(initialPos);
	}
	
	public static ProgressDialog showProgressDialog(Context ctx, String title,
			int style, Option<OnCancelListener> cancelListener) {
		if(style == ProgressDialog.STYLE_SPINNER){
			ProgressDialog dialog = ProgressDialog.show(ctx, title,  
	                ctx.getString(my.library.R.string.dlg_msg_prompt_waiting), true, 
	                !cancelListener.isNull(), cancelListener.value());
			return dialog;
		}else if(style == ProgressDialog.STYLE_HORIZONTAL){
			ProgressDialog dialog = new ProgressDialog(ctx);
			dialog.setTitle(title);
			dialog.setCancelable(!cancelListener.isNull());
			if(!cancelListener.isNull())
				dialog.setOnCancelListener(cancelListener.value());
			dialog.setProgressStyle(style);
			dialog.show();
			return dialog;
		}else 
			throw new RuntimeException("illegal style for progress dialog.");
	}
	
}
