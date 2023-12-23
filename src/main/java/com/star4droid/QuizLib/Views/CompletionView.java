package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizEditor;
import com.star4droid.QuizLib.QuizView;

public class CompletionView extends ScrollView implements QuizView {
	LinearLayout linear;
	boolean rightToLeft=false;
	Quiz quiz;
	public CompletionView(Context context,Quiz qz){
		super(context);
		init();
		setQuiz(qz);
	}
	
	public CompletionView(Context context,Quiz quiz,boolean rtl){
		super(context);
		rightToLeft = rtl;
		init();
		setQuiz(quiz);
	}
	
	public CompletionView(Context context,AttributeSet attributeSet){
		super(context,attributeSet);
		init();
	}
	
	public CompletionView(Context context,AttributeSet attributeSet,int i){
		super(context,attributeSet,i);
		init();
	}
	
	public void init(){
		setBackgroundColor(Color.parseColor("#263238"));
		linear = new LinearLayout(getContext());
		linear.setOrientation(LinearLayout.VERTICAL);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		//params.weight =1;
		linear.setLayoutParams(params);
		addView(linear);
	}
	
	public CompletionView setQuiz(Quiz qz){
		linear.removeAllViews();
		quiz = qz;
		for(String line:quiz.getContent().split("\n")){
			linear.addView(new CompletionLine(getContext(),line,rightToLeft));
		}
		return this;
	}
	
	@Override
	public void edit() {
		QuizEditor.editQuiz(getContext(),this,quiz);
	}
	
	public void reset(){
		for(int x=0;x<linear.getChildCount();x++){
			CompletionLine line=(CompletionLine)linear.getChildAt(x);
			line.reset();
		}
	}
	
	public boolean isCorrect(){
		for(int x=0;x<linear.getChildCount();x++){
			CompletionLine line=(CompletionLine)linear.getChildAt(x);
			CompletionEditText ed=line.checkInCorrect();
			if(ed!=null){
				/*
				// Move focus to editText
				ed.requestFocus();
				
				// Show the cursor in editText
				ed.editText.setSelection(ed.editText.getText().length());
				
				// Show the soft keyboard
				InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(ed.editText, InputMethodManager.SHOW_IMPLICIT);
				*/
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void update() {
		setQuiz(quiz);
	}
}