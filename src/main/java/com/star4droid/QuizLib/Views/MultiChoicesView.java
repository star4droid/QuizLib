package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizEditor;
import com.star4droid.QuizLib.QuizUtils;
import com.star4droid.QuizLib.QuizView;

public class MultiChoicesView extends ScrollView implements QuizView {
	ViewGroup viewGroup;
	String TRUE_STRING="--true--";
	Quiz quiz;
	public MultiChoicesView(Context context,Quiz qz){
		super(context);
		setQuiz(qz);
	}
	public void setQuiz(Quiz qz){
		if(getChildCount()>0)
			removeAllViews();
		Context context = getContext();
		quiz = qz;
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		int mrg=QuizUtils.toDp(context,2);
		params.setMargins(mrg,mrg,mrg,mrg);
		String q = quiz.getContent();
		boolean isMulti = q.indexOf(TRUE_STRING,q.indexOf("{{true}}")+1)!=-1;
		LinearLayout linear=new LinearLayout(context);
		linear.setOrientation(LinearLayout.VERTICAL);
		TextView title = new TextView(getContext());
		title.setGravity(Gravity.CENTER);
		title.setText(quiz.getTitle());
		title.setLayoutParams(params);
		title.setTextColor(Color.WHITE);
		title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		linear.addView(title);
		if(isMulti){
			viewGroup = linear;
		} else {
			RadioGroup rg = new RadioGroup(context);
			rg.setOrientation(RadioGroup.VERTICAL);
			viewGroup=rg;
			linear.addView(rg);
		}
		addView(linear);
		for(String line:quiz.getContent().split("\n")){
			CompoundButton view=isMulti?new CheckBox(context):new RadioButton(context);
			view.setText(line.replace(TRUE_STRING,""));
			view.setTag(String.valueOf(line.contains(TRUE_STRING)));
			view.setLayoutParams(params);
			view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
			viewGroup.addView(view);
			view.setTextColor(Color.WHITE);
		}
	}
	
	public void reset(){
		for(int x=0;x<viewGroup.getChildCount();x++){
			CompoundButton view=(CompoundButton)viewGroup.getChildAt(x);
			view.setChecked(false);
		}
	}
	
	public boolean isCorrect(){
		for(int x=0;x<viewGroup.getChildCount();x++){
			if(!(viewGroup.getChildAt(x) instanceof CompoundButton)) continue;
			CompoundButton view=(CompoundButton)viewGroup.getChildAt(x);
			if(!view.getTag().toString().equals(String.valueOf(view.isChecked())))
				return false;
		}
		return true;
	}
	@Override
	public void edit() {
		QuizEditor.editQuiz(getContext(),this,quiz);
	}

	@Override
	public void update() {
		setQuiz(quiz);
	}
}