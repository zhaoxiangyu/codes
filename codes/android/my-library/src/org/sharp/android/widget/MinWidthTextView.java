package org.sharp.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.TextView;

public class MinWidthTextView extends TextView {

	private String paddingText;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(paddingText != null){
			Rect bounds = new Rect();
			getPaint().getTextBounds(paddingText, 
					0, paddingText.length() , 
					bounds);
			setMeasuredDimension(Math.max(getSuggestedMinimumWidth(),bounds.width()), 
					Math.max(getSuggestedMinimumHeight(), bounds.height()) );
		}else{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}		
	}

	public MinWidthTextView(Context context, String paddingText) {
		super(context);
		this.paddingText = paddingText;
	}

}
