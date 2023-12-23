package com.star4droid.QuizLib.Adapters.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import com.star4droid.QuizLib.DefaultQuizAdapter;
import com.star4droid.QuizLib.Quiz;
import java.util.List;

public class ListAdapter extends DefaultQuizAdapter {
	List<Quiz> quizList;
	int maxPage=-1;
	public ListAdapter(AppCompatActivity c,List<Quiz> list){
		super(c);
		quizList = list;
	}
	@Override
	public int getMaxPage() {
		return maxPage==-1?quizList.size():maxPage;
	}
	
	public void setMaxPage(int page){
		maxPage = page;
	}
	
	@Override
	public void unLock(int pos) {
		maxPage = pos;
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