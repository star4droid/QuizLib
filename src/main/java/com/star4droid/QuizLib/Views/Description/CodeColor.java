package com.star4droid.QuizLib.Views.Description;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeColor {
	public static final String POSSIBLE="ABCDEFGHIJKLMNOPQRDTUVWXYZabcdefjhijklmnopqrstuvwxyz1234567890_";
	public static Spannable setColorOf(String code,CodeSettings settings){
		Spannable sp = new SpannableString(code);
		ArrayList<Pair<Integer,Integer>> blocked= new ArrayList<>();
		if(code.length()>0) {
			sp.setSpan(new ForegroundColorSpan(settings.textColor),0,code.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			String nums="1234567890";
			for(char c:nums.toCharArray()){
				int idx=code.indexOf(c);
				while(idx!=-1){
					int start=idx;
					//check if next chars is numbers...
					while(nums.contains(charAt(code,idx+1))&&(!charAt(code,idx+1).equals(""))){
						idx++;
					}
					sp.setSpan(new ForegroundColorSpan(settings.NumbersColor),start,idx+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					idx = code.indexOf(c,idx+1);
				}
			}
			int idx=0;
			while(idx<code.length()){
				int op=code.length()+999,ed=code.length()+999;
				Pair<Pair<String,String>,Integer> resPair=null;
				for(Pair<Pair<String,String>,Integer> pair:settings.openEndPair){
					int tempOp=code.indexOf(pair.first.first,idx);
					int tempEd=code.indexOf(pair.first.second,tempOp+pair.first.first.length());
					if(tempOp!=-1&&tempOp<op){
						op = tempOp;
						if(tempEd!=-1) ed = tempEd+pair.first.second.length();
						else ed=code.length();
						resPair = pair;
					}
				}
				if(resPair==null) break;
				sp.setSpan(new ForegroundColorSpan(resPair.second),op,ed,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				blocked.add(new Pair<>(op,ed));
				idx = ed;
			}
			
			for(String keyword:settings.keywords){
				//find keyword position
				int ix=code.indexOf(keyword);
				while(ix!=-1){
					//check if the keyword is between comment or string or any pair...
					boolean allow=true;
					for(Pair<Integer,Integer> block:blocked){
						if(between(ix,block.first,block.second)){
							allow=false;
							break;
						}
					}
					if(allow) {
						sp.setSpan(new ForegroundColorSpan(settings.keywordsColor),ix,ix+keyword.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
					ix=code.indexOf(keyword,ix+keyword.length());
				}
			}
		}
		return sp;
	}
	
	public static boolean between(int i,int i1,int i2){
		return (i>=i1&&i<=i2)||(i<=i1&&i>=i2);
	}
	
	public static boolean isSingleKeyword(String string,String keyword,int index){
		//check if the keyword is just a word and not part of anthor word...
		return (!POSSIBLE.contains(charAt(string,index-1))||POSSIBLE.contains(charAt(string,index+keyword.length()+1)));
	}
	
	public static String charAt(String string,int pos){
		try {
			return String.valueOf(string.charAt(pos));
		} catch(Exception exception){
			return "";
		}
	}
}