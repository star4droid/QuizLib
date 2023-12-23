package com.star4droid.QuizLib.Views.Description;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizView;
import com.star4droid.QuizLib.Utils.QuizEditor;
import java.util.ArrayList;

public class DescriptionView extends ScrollView implements QuizView{
	Quiz quiz;
	LinearLayout linear;
	ArrayList<Pair<String,OnTag>> tags;
	public DescriptionView(Context context,Quiz qz){
		super(context);
		initTags();
		linear = new LinearLayout(context);
		addView(linear);
		linear.setOrientation(LinearLayout.VERTICAL);
		setQuiz(qz);
	}
	
	public void setQuiz(Quiz qz){
		quiz = qz;
		if(linear.getChildCount()>0)
			linear.removeAllViews();
		int idx =0;
		Pair<Pair<View,View>,Integer> pair= getType(qz.content,idx);
		while(qz.content.length()>idx){
			if(pair.first.first!=null) linear.addView(pair.first.first);
			if(pair.first.second!=null) linear.addView(pair.first.second);
			idx=pair.second;
			pair = getType(quiz.content,idx);
		}
	}
	
	public void initTags(){
		tags = new ArrayList<>();
		tags.add(new Pair<>("bord",(content)->{
			return new BorderView(getContext(),content);
		}));
		tags.add(new Pair<>("note",(content)->{
			return new NoticeView(getContext(),content);
		}));
		tags.add(new Pair<>("code",(content)->{
			return new CodeView(getContext(),content);
		}));
		tags.add(new Pair<>("normal",(content)->{
			return new NormalTextView(getContext(),content);
		}));
	}
	
	@Override
	public boolean isCorrect() {
	    return true;
	}

	@Override
	public void reset() {
	}

	@Override
	public void edit() {
		QuizEditor.editQuiz(getContext(),this,quiz);
	}

	@Override
	public void update() {
		setQuiz(quiz);
	}
	
	public void addTag(String tag,OnTag onTag){
		if(tags==null) initTags();
		for(int x=0;x<tags.size();x++){
			if(tags.get(x).first.equals(tag)){
				tags.remove(x);
				break;
			}
		}
		tags.add(new Pair<>(tag,onTag));
	}
	
	public void removeTag(String tag){
		if(tags==null) return;
		for(int x=0;x<tags.size();x++)
			if(tags.get(x).equals(tag)){
				tags.remove(x);
				break;
			}
	}
	
	private static Pair<String,Integer> getBetween(String string,String start,String end,int startIndex){
		int index=string.indexOf(start,startIndex);
		int to=(index!=-1)?string.indexOf(end,startIndex):-1;
		if(index==-1||to==-1) return null;
		String result=getSubstring(string,index+start.length(),to);
		return new Pair<String,Integer>(result,to);
	}
	
	private static String getSubstring(String string,int start,int end){
		if(start==end) return "";
		try {
			return string.substring(start,end);
			} catch(Exception exception){
			return "";
		}
	}
	
	private Pair<Pair<View,View>,Integer> getType(String string,int fromIndex){
		String resType="";
		int op=string.length()+999,ed=string.length()+999;
		for(Pair<String,OnTag> pair:tags){
			String s=pair.first;
			int tempOp=string.indexOf("<"+s+">",fromIndex);
			int tempOd=(tempOp!=-1)?string.indexOf("</"+s+">",tempOp+2+s.length()):-1;
			if(tempOp<op&&tempOd!=-1){
				op = tempOp;
				ed = tempOd;
				resType=s;
			}
		}
		if(op!=-1&&ed!=-1&&(!resType.equals(""))){
			View v1=getView("normal",getSubstring(string,fromIndex,op));
			//2 is length of <>
			View v2=getView(resType,getSubstring(string,op+resType.length()+2,ed));
			Pair<View,View> viewPair=new Pair<>(v1,v2);
			return new Pair<Pair<View,View>,Integer>(viewPair,ed+resType.length()+3);
		}
		Pair<View,View> viewPair=new Pair<>(getView("normal",getSubstring(string,fromIndex,string.length())),null);
		return new Pair<Pair<View,View>,Integer>(viewPair,string.length());
	}
	
	public View getView(String type,String content){
		if(content.equals("")||content.trim().equals("")) return null;
		if(content.startsWith("\n")) content = getSubstring(content,1,content.length());
		if(content.endsWith("\n")) content = getSubstring(content,0,content.length()-1);
		String br="&lt;br&gt;";
		content = content.replace("<br>",br).replace("<br/>","&lt;br/&gt;").replace("</br>","&lt;/br&gt;");
		content = content.replace("\n","<br>");
		
		for(Pair<String,OnTag> pair:tags){
			if(type.equals(pair.first)){
				return pair.second.onTag(content);
			}
		}
		return new NormalTextView(getContext(),content);
	}
	
	public interface OnTag {
		public View onTag(String content);
	}
}