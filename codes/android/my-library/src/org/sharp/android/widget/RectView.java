package org.sharp.android.widget;

import org.sharp.utils.LogTags;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

public class RectView extends BaseView {

	private Paint mPaint;
	private int mWidth;
	private int mHeight;

	@Override
	protected void onDraw(Canvas canvas) {
		Rect drawingRect = new Rect();
		drawingRect.set(0, 0, mWidth-1,mHeight-1);
		//getDrawingRect(drawingRect);
		
		canvas.drawRect(drawingRect, mPaint);
//		mPaint.setTextAlign(Paint.Align.CENTER);
//		canvas.drawText("Rect frame drawed", mWidth/2, mHeight/2, mPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(mWidth, mHeight);
	}

	public RectView(Context context, int color, float strokeWidth, int width, int height) {
		super(context);
		mWidth = width;
		mHeight = height;
		Log.v(LogTags.LT_VIEW, "RectView width="+width+",height="+height);
		
		mPaint = new Paint();
		mPaint.setColor(color);
		mPaint.setStrokeWidth(strokeWidth);
		mPaint.setStyle(Style.STROKE);
	}

}
