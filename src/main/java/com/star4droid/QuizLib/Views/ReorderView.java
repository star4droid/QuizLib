package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizEditor;
import com.star4droid.QuizLib.Utils.QuizUtils;
import com.star4droid.QuizLib.QuizView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReorderView extends LinearLayout implements QuizView {
	String true_order;
	boolean editMode=false;
	Quiz quiz;
	int[] dragCustom;
	TextView titleText;
	public ReorderView(Context context,Quiz qz){
		super(context);
		setQuiz(qz);
	}
	public void setQuiz(Quiz qz){
		quiz = qz;
		setOrientation(VERTICAL);
		if(titleText==null){
			titleText = new TextView(getContext());
			//titleText.setGravity(Gravity.CENTER);
			titleText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			titleText.setTextColor(Color.WHITE);
			titleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			titleText.setTextSize(QuizUtils.toSp(getContext(),8));
		}
		titleText.setText(quiz.getTitle());
		true_order = quiz.getContent();
		reset();
	}
	
	public TextView getTitleView(){
		return titleText;
	}
	
	public void setDragCustomView(int id,int imgID,int textID){
		dragCustom = new int[]{id,imgID,textID};
		for(int x=0;x<getChildCount();x++){
			DraggableView view = (DraggableView)getChildAt(x);
			view.removeAllViews();
			View csv = LayoutInflater.from(getContext()).inflate(id,null);
			view.img=csv.findViewById(imgID);
			view.img.setImageBitmap(SimpleDraw.QBitmap);
			TextView textView = csv.findViewById(textID);
			textView.setText(view.getText());
			view.addView(csv);
		}
	}
	
	@Override
	public void edit() {
		QuizEditor.editQuiz(getContext(),this,quiz);
	}
	
	@Override
	public void update() {
		setQuiz(quiz);
	}
	
	@Override
	public void reset() {
		if(getChildCount()>1)
			removeAllViews();
		addView(titleText);
		List<String> list=new ArrayList<>();
		for(String str:true_order.split("\n"))
		list.add(str);
		Collections.shuffle(list);
		for(String line:list){
			DraggableView dg=new DraggableView(getContext(),line,this);
			if(dragCustom==null) dg.setupDefault();
		}
		if(dragCustom!=null)
			setDragCustomView(dragCustom[0],dragCustom[1],dragCustom[2]);
	}
	
	//use like this :
	/*
	reorder.forIcons(icon->icon.setImageBitmap(bitmap));
	*/
	public void forIcons(ForIcons forIcons){
		for (int x = 0; x < getChildCount(); x++) {
			DraggableView v = (DraggableView)getChildAt(x);
			forIcons.forIcon(v.img);
		}
	}
	
	
	/*
		reorder.forDraggables(drag->{
			drag.findViewById(R.id.example).setOnClickListener(..);
		})
	*/
	public void forDraggables(ForDragViews forDrag){
		for (int x = 0; x < getChildCount(); x++) {
			DraggableView v = (DraggableView)getChildAt(x);
			forDrag.forDraggable(v);
		}
	}
	
	public boolean isCorrect(){
		String str="";
		for(String s:getSorted()){
			str += (str.equals("")?"":"\n")+s;
		}
		return str.equals(true_order);
	}
	
	private class DraggableView extends LinearLayout {
		int pos;
		public boolean isMoving=false;
		boolean iconSet=false;
		public ImageView img;
		private boolean isDrag=false;
		public Runnable endRunn = new Runnable(){
			@Override
			public void run() {
				isMoving = false;
			}
		};
		private String text="";
		public DraggableView(Context context,String txt,LinearLayout parent){
			super(context);
			pos = parent.getChildCount();
			parent.addView(this);
			text = txt;
		}
		
		public DraggableView setupDefault(){
			if(getChildCount()>0)
				removeAllViews();
			init();
			return this;
		}
		
		public void setIcon(Bitmap bitmap){
			iconSet = bitmap!=null;
			if(iconSet){
				img.setImageBitmap(bitmap);
			}
		}
		
		public void setIcon(Drawable drawable){
			iconSet = drawable!=null;
			if(iconSet){
				img.setImageDrawable(drawable);
			}
		}
		
		public String getText(){
			return text;
		}
		
		private void init(){
			setGravity(Gravity.CENTER_VERTICAL);
			int hg = QuizUtils.toDp(getContext(),50);
			img = new ImageView(getContext());
			img.setImageBitmap(SimpleDraw.QBitmap);
			LayoutParams params= new LayoutParams(hg,hg);
			int mrg = QuizUtils.toDp(getContext(),2);
			params.setMargins(mrg,mrg,mrg,mrg);
			img.setLayoutParams(params);
			img.setScaleType(ImageView.ScaleType.FIT_CENTER);
			//img.setMaxHeight(QuizUtils.toDp(getContext(),50));
			addView(img);
			TextView textView = new TextView(getContext());
			textView.setText(text);
			textView.setTextColor(Color.WHITE);
			textView.setTextSize(QuizUtils.toSp(getContext(),8));
			addView(textView);
			animate().withEndAction(endRunn).setDuration(200);
		}
		
		public boolean isDragging(){
			return isDrag;
		}
		
		public float startY=0;
		float initialTouchY=0;
		float initialY=0;
		@Override
		public boolean onTouchEvent(MotionEvent motionEvent) {
			if((!isEnabled())||isMoving) return false;
			int action = motionEvent.getAction();
			
			if (action == MotionEvent.ACTION_MOVE) {
				float y = this.initialY + (motionEvent.getRawY() - this.initialTouchY);
				setY(y);
			} else if(action == MotionEvent.ACTION_UP){
				isMoving=true;
				isDrag=false;
				animate().y(startY).withEndAction(endRunn).start();
				ViewParent vp=getParent();
				for(int x=0;x<4;x++){
					vp.requestDisallowInterceptTouchEvent(false);
					vp = vp.getParent();
				}
			} else if(action == MotionEvent.ACTION_DOWN){
				startY = getY();
				isDrag=true;
				ViewParent vp=getParent();
				for(int x=0;x<4;x++){
					vp.requestDisallowInterceptTouchEvent(true);
					vp = vp.getParent();
				}
			}
			this.initialY = getY();//start y
			this.initialTouchY = motionEvent.getRawY();
			
			ViewGroup parent = (ViewGroup)getParent();
			int idx=parent.indexOfChild(this);
			if(MotionEvent.ACTION_DOWN==action||MotionEvent.ACTION_MOVE==action||MotionEvent.ACTION_UP==action)
			for(int x=0;x<parent.getChildCount();x++){
				if(!(parent.getChildAt(x) instanceof DraggableView)) continue;
				DraggableView view = (DraggableView)parent.getChildAt(x);
				if(view.isMoving||view.isDragging()) continue;
				switch(action){
					case MotionEvent.ACTION_UP:
						//view.setEnabled(true);
					break;
					case MotionEvent.ACTION_DOWN:
						view.startY = view.getY();
						//To Disable other views drag..., but there is a bug 
						//if(x!=idx) view.setEnabled(false);
					break;
					case MotionEvent.ACTION_MOVE:
						if(x!=idx){
							float vy = view.getY(),
							vh = view.getMeasuredHeight();
							if(isBetween(getY(),vy,vy+vh*0.3333f)||isBetween(getY(),vy+vh,vy+vh-vh*0.3333f)){
								view.isMoving = true;
								view.animate().y(startY).withEndAction(view.endRunn).start();
								//swap startY's
								float temp=startY;
								startY = view.startY;
								view.startY = temp;
								//swap positions
								temp = pos;
								pos = view.getPosition();
								view.setPosition((int)temp);
								return true;
							}
						}
					break;
				}
			}
			return true;
		}
		
		public int getPosition(){
			return pos;
		}
		
		public void setPosition(int p){
			pos = p;
		}
		
		private boolean isBetween(float i,float i1,float i2){
			return (i>=i1&&i<=i2)||(i<=i1&&i>=i2);
		}
	}
	
	private ArrayList<String> getSorted(){
		ArrayList<String> sorted = new ArrayList<>();
		List<Pair<Float, String>> zValues = new ArrayList<>();
		
		for (int x = 0; x < getChildCount(); x++) {
				if(x==0) continue;
				DraggableView v = (DraggableView)getChildAt(x);
				float z = v.getY();
				String name = v.getText();
				zValues.add(new Pair<>(z, name));
		}
		
		// Sort the zValues list based on the z values
		Collections.sort(zValues, new Comparator<Pair<Float, String>>() {
			@Override
			public int compare(Pair<Float, String> pair1, Pair<Float, String> pair2) {
				return Float.compare(pair1.first, pair2.first);
			}
		});
		
		// Add the names to the list in the sorted order
		for (Pair<Float, String> pair : zValues) {
			sorted.add(pair.second);
		}
		return sorted;
	}
	
	public interface ForIcons {
		public void forIcon(ImageView img);
	}
	
	public interface ForDragViews {
		public void forDraggable(DraggableView view);
	}
}