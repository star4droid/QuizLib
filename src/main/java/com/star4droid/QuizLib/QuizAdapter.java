package com.star4droid.QuizLib;
import android.net.Uri;
import android.view.View;
import java.util.ArrayList;

public interface QuizAdapter {
	public View getQuizView(Quiz quiz);
	default public View getQuizView(int position){
		return getQuizView(getQuiz(position));
	}
	public int getSize();
	public Quiz getQuiz(int position);
	public int getMaxPage();//max page the user can open...
	
	//when the user reach max page and then answer it successfully..this method called...
	public void unLock(int pos);
	default public boolean isRtl(){
		return false;
	}
	
	//just for the editor mode ...., not important if your app questions is fixed...
	public default void removeQuiz(int position){}
	public default void addQuiz(Quiz quiz){}
	
	public static String splitter="//";
	public default String toStringArray(){
		String result="";
		for(int x=0;x<getSize();x++){
			result+=((result.equals(""))?"":splitter)+Uri.encode(getQuiz(x).toString());
		}
		return result;
	}
	
	public static ArrayList<Quiz> getStringArray(String string){
		ArrayList<Quiz> quizList= new ArrayList<>();
		if(string.contains(splitter)){
			for(String str:string.split(splitter)){
				quizList.add(Quiz.fromString(Uri.decode(str)));
			}
		} else if(!string.equals(""))
		quizList.add(Quiz.fromString(Uri.decode(string)));
		return quizList;
	}
}