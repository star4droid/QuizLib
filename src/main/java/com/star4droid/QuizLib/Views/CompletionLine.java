package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

public class CompletionLine extends LinearLayout {
	private static String open_tag="--(",close_tag=")--";
	public CompletionLine(Context context,String line){
		super(context);
		init(context,line,false);
	}
	
	public CompletionLine(Context context,String line,boolean rightToLeft){
		super(context);
		init(context,line,rightToLeft);
	}
	
	public void init(Context context,String line,boolean rightToLeft){
		if(rightToLeft)
			setGravity(Gravity.RIGHT);
		if(line.contains(open_tag)&&line.contains(close_tag)){
			int op=line.indexOf(open_tag),
				ed=line.indexOf(close_tag,op+open_tag.length()),
				idx=0;
			while(op!=-1&&ed!=-1){
				if(op==ed) throw new RuntimeException("empty completion field!!, it looks like you have write something like : ------");
				String[] text= new String[2];
				text[0]=getSubstring(line,idx,op);
				text[1]=getSubstring(line,op+open_tag.length(),ed);
				addView(new CompletionEditText(context,text));
				if(text[1].equals("")) throw new RuntimeException("Error in getting the substring");
				idx=ed+close_tag.length();
				op=line.indexOf(open_tag,idx);
				ed=line.indexOf(close_tag,idx+open_tag.length());
			}
			String[] rest=new String[]{getSubstring(line,idx,line.length())};
			addView(new CompletionEditText(context,rest));
		} else {
			addView(new CompletionEditText(context,new String[]{line}));
		}
	}
	
	private String getSubstring(String string,int start,int end){
		if(start==end) return "";
		try {
			return string.substring(start,end);
		} catch(Exception exception){
			return "";
		}
	}
	
	//return the incorrect edittext...
	public CompletionEditText checkInCorrect(){
		for(int x=0;x<getChildCount();x++){
			CompletionEditText ed=(CompletionEditText)getChildAt(x);
			if(!ed.isCorrect())
				return ed;
		}
		return null;
	}
	
	public void reset(){
		for(int x=0;x<getChildCount();x++){
			CompletionEditText ed=(CompletionEditText)getChildAt(x);
			if(!ed.answer.equals(""))
				ed.editText.setText("");
		}
	}
}