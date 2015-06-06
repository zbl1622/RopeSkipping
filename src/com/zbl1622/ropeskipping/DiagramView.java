package com.zbl1622.ropeskipping;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class DiagramView extends View {
	
	int width=0;
	int height=0;
	
	private float value_y=0;
	
	private int x=0;
	
	private boolean render_flag=false;
	
	private Paint paint=new Paint();

	public DiagramView(Context context, AttributeSet attrs) {
		super(context, attrs); 
		paint.setColor(0xff000000);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setTextSize(30);
	}

	public void setValue(float value){
		value_y=value;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width=getWidth();
		height=getHeight();
		int ox=x,oy=(int) (height/2-value_y);
		canvas.drawLine(ox, oy-4,ox,oy+4, paint);
//		canvas.drawText(""+value_y, 0, 40, paint);
//		if(x>width){
//			canvas.translate(x-width, 0);
//		}
	}
	
	public void startRender(){
		render_flag=true;
		new Thread(){
			public void run() {
				while(render_flag){
					try {
						sleep(20);
						x++;
						if(x>width){
							x=0;
							postInvalidate();
						}
						int ox=x,oy=(int) (height/2-value_y);
						postInvalidate(ox,oy-4,ox+1,oy+4);
//						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	
	public void stopRender(){
		render_flag=false;
	}
	
	public void resetRender(){
		
	}
}
