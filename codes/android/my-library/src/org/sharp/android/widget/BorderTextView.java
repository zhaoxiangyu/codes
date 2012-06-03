package org.sharp.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderTextView extends TextView {
	
	private Paint paint;

	public BorderTextView(Context context, int color,
			int strokeWidth) {
		
		super(context);
		paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(strokeWidth);
	}
	
	public void setBorderColor(int color){
		paint.setColor(color);
	}
	
	public void setBorderWidth(int width){
		paint.setStrokeWidth(width);
	}

	public BorderTextView(Context context, AttributeSet attrs, int color,
			int strokeWidth) {
		
		super(context, attrs);
		paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(strokeWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画TextView的4个边
		canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
		canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
		canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
				this.getHeight() - 1, paint);
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
				this.getHeight() - 1, paint);
	}

}
