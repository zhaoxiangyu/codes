package org.sharp.vocreader.view;

import org.sharp.android.base.BaseMenuOperation;
import org.sharp.android.view.ViewUtils;
import org.sharp.vocreader.beans.Course;
import org.sharp.vocreader.core.JpWordsReader;
import org.sharp.vocreader.intf.Constants;

import sharpx.vocreader.R;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CourseChooseMenuOperation extends BaseMenuOperation {
	private Context mCtx;
	JpWordsReader mJpReader;
	
	public CourseChooseMenuOperation(Context ctx,JpWordsReader jpReader){
		super(ctx, sharpx.vocreader.R.string.menu_choose_course);
		mJpReader = jpReader;
	}
	
	@Override
	public boolean menuItemSelected() {

		final Course[] ca = mJpReader.getCourseList();
		if(ca == null){
			ViewUtils.showToast(mCtx, sharpx.vocreader.R.string.toast_course_manifest_not_available);
			return false;
		}else{
    		ViewUtils.showDialog2(mCtx,
    				mCtx.getString(sharpx.vocreader.R.string.dlg_title_choose_course),
	    			ca,
	    			R.layout.course_info,
    				new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	mJpReader.chooseCourse(item+1);
					    }
					},
					mJpReader.mState.courseNo-1, 
					new ViewUtils.ViewGetter<Course>() {
						@Override
						public View getView(Course course, ViewGroup parent) {
							LayoutInflater inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View row = inflater.inflate(R.layout.course_info, parent, false);
							TextView courseNameV = (TextView)row.findViewById(R.id.course_name);
							TextView zipSizeV = (TextView)row.findViewById(R.id.zip_size);
							TextView courseOpV = (TextView)row.findViewById(R.id.course_op);
							courseNameV.setText(String.format(mCtx.getString(R.string.dlg_msg_course),course.courseNo));
							if(course.status == Constants.COURSE_STATUS_DOWNLOADED || course.status == Constants.COURSE_STATUS_NOTEXIST_NOINFO){
								zipSizeV.setText("");
							}else{
								zipSizeV.setText(String.format(mCtx.getString(R.string.dlg_msg_course_sizeinfo), course.zipSize/1024));
							}
							if(course.status == Constants.COURSE_STATUS_NOTEXIST || course.status == Constants.COURSE_STATUS_NOTEXIST_NOINFO){
								courseOpV.setText(mCtx.getString(R.string.dlg_msg_download_course));
							}else{
								courseOpV.setText(mCtx.getString(R.string.dlg_msg_open_course));
							}
							return row;
						}
					});
    		return true;
		}
	}
		
}
