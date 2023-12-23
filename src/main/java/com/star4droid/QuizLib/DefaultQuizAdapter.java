package com.star4droid.QuizLib;

import android.content.Context;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.star4droid.QuizLib.Quiz;
import android.view.View;
import com.star4droid.QuizLib.Views.CompletionView;
import com.star4droid.QuizLib.Views.Description.DescriptionView;
import com.star4droid.QuizLib.Views.MultiChoicesView;
import com.star4droid.QuizLib.Views.ReorderView;
import com.star4droid.QuizLib.Views.SimpleQuiz;
import java.util.ArrayList;

public abstract class DefaultQuizAdapter implements QuizAdapter {
	Context context;
	boolean rightToLeft=false;
	public DefaultQuizAdapter(AppCompatActivity ctx){
		context = ctx;
	}
	
	public DefaultQuizAdapter(AppCompatActivity ctx,boolean rtl){
		context = ctx;
		rightToLeft = rtl;
	}
	
	public DefaultQuizAdapter setRightToLeft(boolean b){
		rightToLeft = b;
		return this;
	}
	
	@Override
	public View getQuizView(Quiz quiz) {
		switch(quiz.getType()){
			case QuizType.COMPLETE_TYPE:
				return new CompletionView(context,quiz,rightToLeft);
			case QuizType.REORDER_TYPE:
				return new ReorderView(context,quiz);
			case QuizType.MULTI_CHOICES:
				return new MultiChoicesView(context,quiz);
			case QuizType.SIMPLE_TYPE:
				return new SimpleQuiz(context,quiz);
			case QuizType.DESCRIPTION_TYPE:
				return new DescriptionView(context,quiz);
		}
	    return null;
	}
	@Override
	public boolean isRtl(){
		return rightToLeft;
	}
	public abstract int getSize();
	public abstract void unLock(int pos);
	public abstract Quiz getQuiz(int position);
	public abstract int getMaxPage();
	
	public static class QuizType {
		public static final String COMPLETE_TYPE="CMPT",DESCRIPTION_TYPE="DESC",
		REORDER_TYPE="RDRT",MULTI_CHOICES="MLTI",SIMPLE_TYPE="SMPL";
	}
}