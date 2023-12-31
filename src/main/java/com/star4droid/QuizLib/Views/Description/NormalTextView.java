package com.star4droid.QuizLib.Views.Description;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.Utils.QuizUtils;

public class NormalTextView extends TextView{
	public NormalTextView(Context context,String content){
		super(context);
		int pd=QuizUtils.toDp(context,3);
		LinearLayout.LayoutParams thisParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		thisParams.setMargins(0,pd,0,0);
		setLayoutParams(thisParams);
		setTextColor(Color.WHITE);
		setText(Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY));
	}
}