package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizEditor;
import com.star4droid.QuizLib.QuizView;

public class SimpleQuiz extends LinearLayout implements QuizView {
	EditText editText;
	String ans;
	Quiz quiz;
	public SimpleQuiz(Context context,Quiz qz){
		super(context);
		quiz = qz;
		init(quiz);
	}
	
	public void init(Quiz qz){
		if(getChildCount()>0)
			removeAllViews();
		quiz = qz;
		setOrientation(VERTICAL);
		ans=quiz.getAnswer();
		//setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		setGravity(Gravity.CENTER);
		TextView textView = new TextView(getContext());
		textView.setText(quiz.getContent());
		textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		params.weight = 1;
		textView.setLayoutParams(params);
		textView.setTextColor(Color.WHITE);
		addView(textView);
		editText = new EditText(getContext()){
			public CharSequence getHint(){
				return "answer written here..";
			}
		};
		editText.setHint(quiz.getAnswer());
		editText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		editText.setHintTextColor(Color.TRANSPARENT);
		editText.setTextColor(Color.WHITE);
		addView(editText);
		editText.setMaxLines(1);
		editText.setSingleLine(true);
		editText.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int txtL=editText.getText().length(),
				hntL = ans.length();
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
	}
	@Override
	public void edit() {
		QuizEditor.editQuiz(getContext(),this,quiz);
	}
	@Override
	public void update() {
		init(quiz);
	}
	@Override
	public boolean isCorrect() {
	    return editText.getText().toString().equals(ans);
	}

	@Override
	public void reset() {
		editText.setText("");
	}
}