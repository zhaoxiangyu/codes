package org.sharp.android.widget;

import org.sharp.android.autils.HWInfo;
import org.sharp.utils.LogTags;
import org.sharp.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HintFloatView extends BaseView {
	
	Point mAnchorPoint;
	String mHintText;
	
	private int mHintXCenter;
	private int mHintYCenter;
	private boolean mPosAbove;
	
	private Paint mTextPaint;
	private Rect mTextRect;
	private int mTextRectMargin = 8;
	private final int MARGIN_TIMES = 3;
	private Paint mOutLinePaint;
	private StaticLayout layout;
	
	public HintFloatView(Context ctx, View view, String hintText, int color) {
		super(ctx);
		mTextPaint = new Paint();
		mOutLinePaint = new Paint();

		mTextPaint.setColor(color);
		//mTextPaint.setStrokeWidth(mTextPaint.getStrokeWidth()*2);
		mOutLinePaint.setColor(color);
		mOutLinePaint.setStyle(Style.STROKE);
		
		setNewHint(view, hintText);
	}
	
	public int xOffset(){
		return Math.min(mTextRect.left,mAnchorPoint.x);
	}
	
	public int yOffset(){
		if(mPosAbove){
			return mTextRect.top;
		}else{
			return mAnchorPoint.y;
		}
	}

	public void setNewHint(View view, String hintText){
		Point anchorPoint = new Point();
		int[] screenLocation = new int[2];
		view.getLocationOnScreen(screenLocation);
		anchorPoint.set(screenLocation[0] + view.getWidth()/2, 
				(screenLocation[1] + view.getHeight()/2 ));
		
		mHintText = hintText;

		Point screenMetrics = HWInfo.screenMetrics(getContext());
		/*if(mAnchorPoint.x <= screenMetrics.x/2){
			mHintXCenter = (mAnchorPoint.x + screenMetrics.x) /2;
		}else{
			mHintXCenter = mAnchorPoint.x /2;
		}*/
		mHintXCenter = screenMetrics.x/2;
		if(anchorPoint.y <= screenMetrics.y/2){
			mHintYCenter = (anchorPoint.y + screenMetrics.y) /2;
			mPosAbove = false;
			anchorPoint.set(screenLocation[0] + view.getWidth()/2, 
					screenLocation[1] + view.getHeight());
		}else{
			mHintYCenter = anchorPoint.y /2;
			mPosAbove = true;
			anchorPoint.set(screenLocation[0] + view.getWidth()/2, 
					screenLocation[1]);
		}
		mAnchorPoint = anchorPoint;
		
		mTextRect = new Rect();
		/*mTextPaint.getTextBounds(mHintText, 0, mHintText.length(), mTextRect);
		int halfWidth = (int)Math.ceil(mTextRect.width()/2.0);
		int halfHeight = (int)Math.ceil(mTextRect.height()/2.0);*/
		layout = new StaticLayout(
				mHintText,new TextPaint(mTextPaint),
				mHintXCenter,
				Alignment.ALIGN_CENTER,
				1.2F,0,false);
		int halfWidth = layout.getWidth()/2;
		int halfHeight = layout.getHeight()/2;
		
		int left = mHintXCenter - halfWidth - MARGIN_TIMES * mTextRectMargin;
		int right = mHintXCenter + halfWidth + MARGIN_TIMES * mTextRectMargin;
		int top = mHintYCenter - halfHeight - mTextRectMargin;
		int bottom = mHintYCenter + halfHeight + mTextRectMargin;
		mTextRect.set(left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		/*mTextPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(mHintText, 
				mHintXCenter - xOffset(), 
				mHintYCenter - yOffset(), 
				getHeight()/2,
				mTextPaint);*/
		/*TextView tv = new TextView(getContext());
		tv.setText(mHintText);
		tv.draw(canvas);*/
		
		int transX = mTextRect.left - xOffset();
		int transY = mTextRect.top + mTextRectMargin - yOffset();
		/*int transX = 50;
		int transY = 0;*/
		canvas.translate( transX, 
				transY);
		layout.draw(canvas);
		canvas.translate(-transX,
				-transY);
		
		Path path = new Path();
		path.setLastPoint(mAnchorPoint.x, mAnchorPoint.y);
		if(mPosAbove){
			path.lineTo(mHintXCenter - MARGIN_TIMES * mTextRectMargin, mTextRect.bottom);
			path.lineTo(mTextRect.left, mTextRect.bottom);
			path.lineTo(mTextRect.left, mTextRect.top);
			path.lineTo(mTextRect.right, mTextRect.top);
			path.lineTo(mTextRect.right, mTextRect.bottom);
			path.lineTo(mHintXCenter + MARGIN_TIMES * mTextRectMargin, mTextRect.bottom);
			path.lineTo(mAnchorPoint.x, mAnchorPoint.y);
		}else{
			path.lineTo(mHintXCenter - MARGIN_TIMES * mTextRectMargin, mTextRect.top);
			path.lineTo(mTextRect.left, mTextRect.top);
			path.lineTo(mTextRect.left, mTextRect.bottom);
			path.lineTo(mTextRect.right, mTextRect.bottom);
			path.lineTo(mTextRect.right, mTextRect.top);
			path.lineTo(mHintXCenter + MARGIN_TIMES * mTextRectMargin, mTextRect.top);
			path.lineTo(mAnchorPoint.x, mAnchorPoint.y);
		}
		Matrix matrix = new Matrix();
		matrix.setTranslate(0-xOffset(), 0-yOffset());
		path.transform(matrix);
		canvas.drawPath(path , mOutLinePaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*int width = (int)Math.ceil(mTextPaint.measureText(mHintText)) + 
				(int)mTextPaint.getStrokeWidth()*2;*/
		int width = Utils.maxDistance(mAnchorPoint.x, 
				mTextRect.right, mTextRect.left);
		
		int height = Utils.maxDistance(mAnchorPoint.y, 
				mTextRect.top, mTextRect.bottom);
		Log.v(LogTags.LT_VIEW, "HintView.setMeasureDimension(width="+width+",height="+height+")");
		setMeasuredDimension(width, height);
	}

}
