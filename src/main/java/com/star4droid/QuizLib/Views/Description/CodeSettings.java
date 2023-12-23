package com.star4droid.QuizLib.Views.Description;
import java.util.ArrayList;
import java.util.List;
import android.util.Pair;
import android.graphics.Color;

public class CodeSettings {
	public int commentsColor=Color.parseColor("#777777"),
	textColor=Color.parseColor("#FFFFFF"),
	NumbersColor=Color.parseColor("#546679"),
	stringsColor=Color.parseColor("#6A8759"),
	keywordsColor=Color.parseColor("#B6753D");
	List<Pair<Pair<String,String>,Integer>> openEndPair = new ArrayList<>();
	List<String> keywords = new ArrayList<>();
	
	public CodeSettings(){}
	
	public CodeSettings setup(){
		openEndPair.clear();
		addColorPair("//","\n",commentsColor);
		addColorPair("/*","*/",commentsColor);
		addColorPair("\"","\"",stringsColor);
		return this;
	}
	
	public void addColorPair(String open,String end,int color){
		openEndPair.add(new Pair<>(new Pair<>(open,end),color));
	}
}