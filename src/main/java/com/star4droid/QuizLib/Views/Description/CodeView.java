package com.star4droid.QuizLib.Views.Description;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.Utils.QuizUtils;

public class CodeView extends LinearLayout {
	public CodeView(Context context,String content){
		super(context);
		setOrientation(VERTICAL);
		TextView title = new TextView(context);
		title.setText("Code : ");
		title.setBackgroundColor(Color.parseColor("#B0BEC5"));
		title.setTextColor(Color.BLACK);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		QuizUtils.setCornerRadiusWithStroke(this,0,2,Color.BLACK,Color.parseColor("#2B2B2B"));
		TextView code= new TextView(context);
		code.setTextColor(Color.WHITE);
		int pd=QuizUtils.toDp(context,8);
		code.setPadding(pd,0,0,pd);
		title.setPadding(pd,0,0,pd);
		
		pd=QuizUtils.toDp(context,3);
		LayoutParams thisParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		thisParams.setMargins(0,pd,0,0);
		setLayoutParams(thisParams);
		
		code.setText(CodeColor.setColorOf(content.replace("<br>","\n").replace("&lt;","<").replace("&gt;",">"),(new CodeSettings()).setup()));
		addView(title);
		addView(code);
	}
}