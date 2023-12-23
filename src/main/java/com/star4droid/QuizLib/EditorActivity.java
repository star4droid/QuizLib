package com.star4droid.QuizLib;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.Utils.FileUtil;
import com.star4droid.QuizLib.Utils.QuizUtils;
import com.star4droid.QuizLib.Views.QuizList;
import com.star4droid.QuizLib.Views.SimpleDraw;
import java.util.ArrayList;
/*
Powered By Star4Droid (Annas Osman Ebrahim)
--> 25/November/2023 <--
QuizLib Editor Example
--> Read well before asking :)
		....Any Questions...feel free to ask....
			Happy Coding...≈≈
*/
public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//write example Quiz List to a path...
        if(!(new java.io.File(getExternalFilesDir(null)+"/save.quizf").exists()))
			FileUtil.writeToFile(getExternalFilesDir(null)+"/save.quizf",QuizUtils.readAssetFile(this,"save.quizf"));
		//creating new ViewPager for the Questions...
		QuizList pager = new QuizList(this).setEditMode(true);
		//load from file :
		ArrayList<Quiz> quizList = QuizAdapter.getStringArray(FileUtil.readFromFile(this.getExternalFilesDir(null)+"/save.quizf"));
		pager.setAdapter(new QAdapter(this,quizList).setRightToLeft(false));//you can set it to true for Arabic Language or right To left languages...
		setContentView(pager);
		//when the user answer question successfully
		// you can show ad or anything ....
		pager.addListener((pos,isLast)->{
			if(isLast)
				finish();//if the question is the latest question, then close the app....
		});
		pager.getCloseIcon().setOnClickListener(v->{
			finish();
		});
		
    }
	
	private class QAdapter extends DefaultQuizAdapter {
		ArrayList<Quiz> quizList;
		int maxPage=0;//just for explaining...
		public QAdapter(AppCompatActivity c,ArrayList<Quiz> list){
			super(c);
			quizList = list;
		}
		@Override
		public int getMaxPage() {
			//return quizList.size();//remove this comment if you want to be able to open all pages
			return maxPage;
            //we return maxPage variable...
            //in this case the user will be able
            //to open only maxPage and pages before it...
            //more examples soon....
		}

		@Override
		public void unLock(int pos) {
		    //you must think to understand this...
		    //getMaxPage return maximum page the user can open...
		    //if the user reach the maximum page
            //and then answer it successfully...
            //the app will call this method
            //you can do something like :
			maxPage = pos;
			//we change the maximum page ...
		}

		@Override
		public Quiz getQuiz(int position) {
			return quizList.get(position);
		}

		@Override
		public int getSize() {
			return quizList.size();
		}

		@Override
		public void removeQuiz(int position) {
			quizList.remove(position);//for editor mode...not important if your app use fixed quizes...
		}

		@Override
		public void addQuiz(Quiz quiz) {
			quizList.add(quiz);//also for editor mode...
		}

	}
}