package com.star4droid.QuizLib.Utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.util.TypedValue;

public class QuizUtils {
	public static String readAssetFile(Context context,String asset){
		try{	
			java.io.InputStream In = context.getAssets().open(asset);
			int i = In.available();
			byte[] Bu = new byte[i];
			In.read(Bu);
			In.close();
			String s = new String(Bu, "UTF-8");
			return s;
			} catch(Exception e){
			return "";
		}
	}
	
	public static int toDp(Context context,int pixels) {
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
	}
	
	public static int toSp(Context context,int pixels){
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,pixels,context.getResources().getDisplayMetrics());
	}
	
	public static void setCornerRadius(View v,int radius,int color){
		v.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns(radius, color));
	}
	
	public static void setCornerRadiusWithStroke(View v,int radius,int stroke,int strokeColor,int bgcolor){
		v.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns(radius, stroke, strokeColor, bgcolor));
	}
}