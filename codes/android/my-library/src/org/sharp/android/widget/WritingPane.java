package org.sharp.android.widget;

import java.util.ArrayList;
import java.util.List;

import org.sharp.utils.LogTags;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class WritingPane extends BaseView {
	private CurveDrawer mCd = new CurveDrawer();
	private CurvesDrawer mCsd = new CurvesDrawer();
	private int mStrokeWidth = 3;
	private int mPaintColor = Color.WHITE;

	public static class Coordinates {
		public Coordinates(Integer deviceId, Integer pointId, Float x, Float y,
				Float size, Float pressure, Float rawX, Float rawY,
				Float xPrecision, Float yPrecision, Integer metaState) {
			super();
			this.deviceId = deviceId;
			this.pointId = pointId;
			this.x = x;
			this.y = y;
			this.size = size;
			this.pressure = pressure;
			this.rawX = rawX;
			this.rawY = rawY;
			this.xPrecision = xPrecision;
			this.yPrecision = yPrecision;
			this.metaState = metaState;
		}

		Integer deviceId;
		Integer pointId;
		Float x;
		Float y;
		Float size;
		Float pressure;
		Float rawX;
		Float rawY;
		Float xPrecision;
		Float yPrecision;
		Integer metaState;
	}

	public static interface CoordinatesConsumer {
		void newCoordinates(Coordinates co);
	}

	public static class CurveDrawer implements CoordinatesConsumer {
		List<Coordinates> mCoList = new ArrayList<WritingPane.Coordinates>();
		Paint mPaint = new Paint();

		@Override
		public void newCoordinates(Coordinates co) {
			mCoList.add(co);
		}

		public void clearView() {
			mCoList.clear();
		}

		public void paint(Canvas canvas, int strokeWidth, int color) {
			mPaint.setStrokeWidth(strokeWidth);
			mPaint.setColor(color);
			Coordinates co1 = null;
			for (Coordinates co2 : mCoList) {
				// paintCoordinates(canvas, mPaint, co2);
				paintCoordinates(canvas, mPaint, co1, co2);
				co1 = co2;
			}
		}

		private void paintCoordinates(Canvas canvas, Paint paint, Coordinates co) {
			if (canvas != null) {
				canvas.drawPoint(co.x, co.y, paint);
				// Log.d(LogTags.LT_WRITEINGPANE,
				// "x:"+co.x+",y:"+co.y+",size:"+
				// co.size+",pressure:"+co.pressure);
			}
		}

		private void paintCoordinates(Canvas canvas, Paint paint,
				Coordinates co1, Coordinates co2) {
			if (canvas != null) {
				if (co1 == null) {
					canvas.drawPoint(co2.x, co2.y, paint);
				} else {
					canvas.drawLine(co1.x, co1.y, co2.x, co2.y, paint);
				}
			}
		}

		private void feedMotionEvent(MotionEvent event) {
			int pc = event.getPointerCount();
			int hs = event.getHistorySize();
			int did = event.getDeviceId();

			for (int j = 0; j < hs; j++) {
				for (int i = 0; i < pc; i++) {
					int pid = event.getPointerId(i);
					Coordinates co = new Coordinates(did, pid,
							event.getHistoricalX(i, j), event.getHistoricalY(i,
									j), event.getHistoricalSize(i, j),
							event.getHistoricalPressure(i, j), event.getRawX(),
							event.getRawY(), event.getXPrecision(),
							event.getYPrecision(), event.getMetaState());
					newCoordinates(co);
				}
			}
			for (int i = 0; i < pc; i++) {
				int pid = event.getPointerId(i);
				Coordinates co = new Coordinates(did, pid, event.getX(i),
						event.getY(i), event.getSize(i), event.getPressure(i),
						event.getRawX(), event.getRawY(),
						event.getXPrecision(), event.getYPrecision(),
						event.getMetaState());
				newCoordinates(co);
			}
		}
	}

	public static class CurvesDrawer {
		List<CurveDrawer> mCdList = new ArrayList<CurveDrawer>();
		CurveDrawer mCd = new CurveDrawer();
		Paint mPaint = new Paint();

		public CurvesDrawer() {
		}

		public void clearView() {
			mCdList.clear();
		}

		public void paint(Canvas canvas, int strokeWidth, int color,
				int width, int height){
			mPaint.setStrokeWidth(strokeWidth);
			mPaint.setColor(color);
			for(CurveDrawer cd:mCdList){
				cd.paint(canvas, strokeWidth, color);
			}
			if(mCdList.isEmpty()){
				drawText(canvas, "手写区", width, height);
			}
		}
		
		private void drawText(Canvas canvas,String text, int x, int y){
			Paint paint = new Paint(mPaint);
			paint.setTextScaleX(3.0f);
			paint.setTextAlign(Paint.Align.CENTER);
//			Style style = Style.
//			paint.setStyle();
			if(mCdList.isEmpty()){
				canvas.drawText("手写区", x/2, y/2, paint);
			}
		}
		
		private void initCd(){
			if(mCd == null)
				mCd = new CurveDrawer();
		}

		private void feedMotionEvent(MotionEvent event) {
			initCd();
			if (event.getAction() == MotionEvent.ACTION_UP) {
				mCd.feedMotionEvent(event);
				mCd = new CurveDrawer();
				mCdList.add(mCd);
			} else {
				mCd.feedMotionEvent(event);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		// mCd.feedMotionEvent(event);
		mCsd.feedMotionEvent(event);
		invalidate();
		// Log.d(LogTags.LT_WRITEINGPANE, ""+event.toString());
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mCsd.paint(canvas, mStrokeWidth, mPaintColor, getWidth(), getHeight());
	}

	public void clearView() {
		mCsd.clearView();
		invalidate();
	}

	public void setStrokeWidth(int strokeWidth) {
		mStrokeWidth = strokeWidth;
	}

	public void setPaintColor(int color) {
		mPaintColor = color;
	}

	public WritingPane(Context context) {
		super(context);
	}

	public WritingPane(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WritingPane(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

}
