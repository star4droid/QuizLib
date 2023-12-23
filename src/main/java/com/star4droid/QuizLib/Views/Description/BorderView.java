package com.star4droid.QuizLib.Views.Description;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.Utils.QuizUtils;

public class BorderView extends TextView {
	public BorderView(Context context,String text){
		super(context);
		setTextColor(Color.WHITE);
		int pd=QuizUtils.toDp(context,4);
		setPadding(pd,pd,pd,pd);
		setText(Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY));
		setBackgroundColor(Color.BLACK);
		
		pd=QuizUtils.toDp(context,3);
		LinearLayout.LayoutParams thisParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		thisParams.setMargins(0,pd,0,0);
		setLayoutParams(thisParams);
	}
}