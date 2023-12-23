package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import com.star4droid.QuizLib.QuizUtils;

public class SimpleDraw {
	public static Bitmap crossBitmap,QBitmap,plusBitmap;
	public static void init(Context ctx){
		if(crossBitmap!=null){
			/*
			crossBitmap.recycle();
			QBitmap.recycle();
			plusBitmap.recycle();
			*/
			return;
		}
		Path path = new Path();
		Paint paint=new Paint(){
			{
				setColor(Color.WHITE);
				setStrokeWidth(3);
				setStyle(Paint.Style.STROKE);
				setStrokeCap(Paint.Cap.ROUND);
				setStrokeJoin(Paint.Join.ROUND);
				setDither(true);
				setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
			}
		};
		int dp50=100;
		int dp=24;
		QBitmap = Bitmap.createBitmap(dp50,dp50,Bitmap.Config.ARGB_8888);
		//draw Q
		path.moveTo(dp50-dp,dp50-dp);
		path.lineTo(dp50/2,dp50/2);
		path.addCircle(dp50/2,dp50/2,(dp50-dp)/3.5f,Path.Direction.CCW);
		Canvas canvas=new Canvas(QBitmap);
		canvas.drawPath(path,paint);
		//draw ×
		crossBitmap = Bitmap.createBitmap(dp50,dp50,Bitmap.Config.ARGB_8888);
		canvas = new Canvas(crossBitmap);
		path = new Path();
		path.moveTo(dp,dp);
		path.lineTo(dp50-dp,dp50-dp);
		path.moveTo(dp50-dp,dp);
		path.lineTo(dp,dp50-dp);
		canvas.drawPath(path,paint);
		//draw +
		path = new Path();
		path.moveTo(dp50/2,dp);
		path.lineTo(dp50/2,dp50-dp);
		path.moveTo(dp,dp50/2);
		path.lineTo(dp50-dp,dp50/2);
		plusBitmap = Bitmap.createBitmap(dp50,dp50,Bitmap.Config.ARGB_8888);
		canvas = new Canvas(plusBitmap);
		canvas.drawPath(path,paint);
	}
}