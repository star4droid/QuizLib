package com.star4droid.QuizLib.Utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.button.MaterialButton;
import com.star4droid.QuizLib.DefaultQuizAdapter;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizAdapter;
import com.star4droid.QuizLib.QuizView;

public class QuizEditor {
	public static void editQuiz(Context context,QuizView view,Quiz qz){
		AlertDialog dialog = new AlertDialog.Builder(context).create();
		LinearLayout linear = new LinearLayout(context);
		EditText editText = new EditText(context);
		EditText second = new EditText(context);
		MaterialButton save = new MaterialButton(context);
		save.setCornerRadius(0);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		linear.setLayoutParams(params);
		LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(params);
		editParams.weight = 1;
		editText.setLayoutParams(editParams);
		editText.setMinimumHeight(QuizUtils.toDp(context,300));
		editText.setGravity(Gravity.TOP);
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.addView(second);
		linear.addView(editText);
		linear.addView(save);
		save.setText("Save");
		editText.setText(qz.getContent());
		second.setText(qz.type.equals(DefaultQuizAdapter.QuizType.SIMPLE_TYPE)?qz.answer:qz.title);
		dialog.setView(linear);
		save.setOnClickListener(v->{
			qz.content = editText.getText().toString();
			if(qz.type.equals(DefaultQuizAdapter.QuizType.SIMPLE_TYPE))
				qz.answer = second.getText().toString();
					else qz.title = second.getText().toString();
			view.update();
		});
		dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		dialog.show();
	}
	
	public static void addQuiz(Context context,QuizAdapter adapter,OnAddQuiz onAddQuiz){
		AlertDialog dialog = new AlertDialog.Builder(context).create();
		MaterialButton[] typesBtns=new MaterialButton[5];
		String[] types=new String[]{
			DefaultQuizAdapter.QuizType.COMPLETE_TYPE,
			DefaultQuizAdapter.QuizType.MULTI_CHOICES,
			DefaultQuizAdapter.QuizType.REORDER_TYPE,
			DefaultQuizAdapter.QuizType.SIMPLE_TYPE,
			DefaultQuizAdapter.QuizType.DESCRIPTION_TYPE
		};
		String[] names=new String[]{
			"Complete Type","Multi Choices Type",
			"Re-order Type","Simple QA","Description Type"
		};
		LinearLayout linear = new LinearLayout(context);
		linear.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		int mar=QuizUtils.toDp(context,5);
		params.setMargins(mar,mar,mar,mar);
		for(int x=0;x<typesBtns.length;x++){
			typesBtns[x] = new MaterialButton(context);
			typesBtns[x].setText(names[x]);
			typesBtns[x].setLayoutParams(params);
			typesBtns[x].setCornerRadius(0);
			final int i=x;
			typesBtns[x].setOnClickListener(v->{
				Quiz quiz = new Quiz("Content Here\nFeel Free To ask me anytime...","Answer Here",types[i],"Title Here");
				if(onAddQuiz.onAdd(quiz))
					dialog.dismiss();
			});
			linear.addView(typesBtns[x]);
		}
		dialog.setView(linear);
		dialog.show();
	}
	
	public interface OnAddQuiz {
		public boolean onAdd(Quiz quiz);
	}
}