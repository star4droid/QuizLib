package com.star4droid.QuizLib.Views.Description;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.Utils.QuizUtils;

public class NoticeView extends LinearLayout {
	public NoticeView(Context context,String note){
		super(context);
		setGravity(Gravity.CENTER_VERTICAL);
		int pd=QuizUtils.toDp(context,3);
		setPadding(pd,0,0,pd);
		
		LayoutParams ntParams = new LayoutParams(QuizUtils.toDp(context,35),QuizUtils.toDp(context,35));
		ntParams.setMargins(pd,0,0,pd);
		pd = QuizUtils.toDp(context,8);
		TextView nt = new TextView(context);
		nt.setPadding(pd,pd,pd,pd);
		nt.setText("!");
		nt.setGravity(Gravity.CENTER);
		nt.setLayoutParams(ntParams);
		nt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		QuizUtils.setCornerRadius(nt,QuizUtils.toDp(context,90),Color.parseColor("#FFC107"));
		addView(nt);
		
		TextView noteText = new TextView(context);
		noteText.setPadding(pd,pd,pd,pd);
		noteText.setText(Html.fromHtml(note,Html.FROM_HTML_MODE_LEGACY));
		noteText.setTextColor(Color.WHITE);
		addView(noteText);
		LayoutParams noteParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		noteParams.setMargins(pd,pd,0,pd);
		noteText.setLayoutParams(noteParams);
		QuizUtils.setCornerRadius(noteText,QuizUtils.toDp(context,8),Color.parseColor("#66BB6A"));
		
		
		LayoutParams thisParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		thisParams.setMargins(0,pd,0,0);
		setLayoutParams(thisParams);
	}
}