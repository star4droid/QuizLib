package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.QuizUtils;

public class CompletionEditText extends LinearLayout {
	//text contains 2 strings, prefix,missing
	EditText editText;
	String answer="";
	public CompletionEditText(Context context,String[] text){
		super(context);
		init(context,text,false);
	}
	
	public CompletionEditText(Context context,String[] text,boolean rightToLeft){
		super(context);
		init(context,text,rightToLeft);
	}
	
	public void init(Context context,String[] text,boolean rightToLeft){
		
		editText = new EditText(context){
			public CharSequence getHint(){
				return "answer written here..";
			}
		};
		addView(editText);
		editText.setMaxLines(1);
		editText.setSingleLine(true);
		editText.setTextColor(Color.WHITE);
		editText.setTextSize(QuizUtils.toSp(context,8));
		int pd=QuizUtils.toDp(context,2);
		editText.setPadding(0,pd,pd,0);
		if(text.length==2){
			TextView textView=new TextView(context);
			textView.setText(text[0]);
			textView.setTextSize(QuizUtils.toSp(context,8));
			textView.setMaxLines(1);
			textView.setSingleLine(true);
			//if(rightToLeft)
				//addView(textView);
					//else 
			addView(textView,0);
			textView.setTextColor(Color.WHITE);
			editText.setHint(text[1]);
			editText.setGravity(Gravity.CENTER);
			answer = text[1];
			editText.setHintTextColor(Color.TRANSPARENT);
			//setSuffixText(text[2]);
			editText.addTextChangedListener(new TextWatcher(){
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					int txtL=editText.getText().length(),
					hntL = answer.length();
					if(txtL>hntL){
						int cursorPos=editText.getSelectionStart();
						editText.setText(editText.getText().toString().substring(0,hntL));
						editText.setSelection(cursorPos>hntL?hntL:cursorPos);
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
				}
				
			});
		} else {
			editText.setText(text[0]);
			editText.setHint("");
			editText.setBackgroundColor(Color.TRANSPARENT);
			editText.setInputType(0);
			editText.setFocusable(false);
			editText.setEnabled(false);
		}
	}
	
	public boolean isCorrect(){
		return (answer.length()==0||editText.getText().toString().equals(answer));
	}
}