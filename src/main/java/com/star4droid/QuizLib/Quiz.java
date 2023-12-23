package com.star4droid.QuizLib;

public class Quiz {
	public String content="",answer="",type="",title="";
	private static String QuizSave="{{key==>content<==}}";
	public Quiz(String cnt,String ans,String tp,String tl){
		content = cnt;
		ans=answer;
		type=tp;
		title = tl;
	}
	public Quiz(){}
	public String getTitle(){
		return title;
	}
	public String getContent(){
		return content;
	}
	public String getAnswer(){
		return answer;
	}
	public String getType(){
		return type;
	}
	
	public void setTitle(String tl){
		title = tl;
	}
	
	public void setContent(String cnt){
		content = cnt;
	}
	
	public String toString(){
		return getSave("title",title).concat(getSave("content",content)).concat(getSave("answer",answer)).concat(getSave("type",type));
	}
	
	public static Quiz fromString(String string){
		Quiz quiz = new Quiz();
		quiz.answer = getBetween(string,op("answer"),ed("answer"));
		quiz.title = getBetween(string,op("title"),ed("title"));
		quiz.content = getBetween(string,op("content"),ed("content"));
		quiz.type = getBetween(string,op("type"),ed("type"));
		return quiz;
	}
	
	private static String op(String str){
		return "{{"+str+"==>";
	}
	
	private static String ed(String str){
		return "}<=="+str+"}";
	}
	
	private static String getBetween(String from,String op,String end){
		try {
			return from.substring(from.indexOf(op)+op.length(),from.lastIndexOf(end));
		} catch(Exception e){
			return "";
		}
	}
	
	private String getSave(String key,String con){
		if(con.equals("")) return "";
		return op(key)+con+ed(key);
	}
}