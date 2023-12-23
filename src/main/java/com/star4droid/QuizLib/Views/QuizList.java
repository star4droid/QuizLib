package com.star4droid.QuizLib.Views;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.star4droid.QuizLib.Adapters.QuizListPagerAdapter;
import com.star4droid.QuizLib.QuizView;
import com.star4droid.QuizLib.Utils.FileUtil;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizAdapter;
import com.star4droid.QuizLib.Utils.QuizEditor;
import com.star4droid.QuizLib.Utils.QuizUtils;
import java.util.ArrayList;
/*
	it looks like little bit complex but get a coffee and read ...
	
*/
public class QuizList extends LinearLayout {
	QuizAdapter adapter;
	ViewPager2 viewPager2;
	ImageView close,addQuiz;
	LinearLayout btm;
	MaterialButton nextBtn,editBtn,saveBtn,delBtn;
	STATE state = STATE.NOTHING;
	String ERROR_TEXT="Try Again",NEXT_TEXT="< Next >",CHECK_TEXT="Check";
	QuizListPagerAdapter pagerAdapter;
	public QuizList(Context context){
		super(context);
		init(context);
	}
	
	public void init(Context context){
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		int pd=QuizUtils.toDp(context,8);
		setPadding(pd,pd,pd,pd);
		setBackgroundColor(Color.parseColor("#263238"));
		viewPager2 = new ViewPager2(context);
		/*
		viewPager2.setPageTransformer(new ViewPager2.PageTransformer(){
			@Override
			public void transformPage(View page, float position) {
				page.setTranslationX(page.getMeasuredWidth()*-position);
			}
		});
		*/
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_VERTICAL);
		close = new ImageView(context);
		close.setScaleType(ImageView.ScaleType.FIT_CENTER);
		close.setImageBitmap(SimpleDraw.crossBitmap);
		int sz = QuizUtils.toDp(context,50);
		close.setLayoutParams(new LayoutParams(sz,sz));
		addView(close);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		params.weight = 1;
		viewPager2.setLayoutParams(params);
		setGravity(Gravity.RIGHT);
		//ToDo : add progress
		//...
		addView(viewPager2);
		//add buttons
		btm= new LinearLayout(context);
		pd=QuizUtils.toDp(context,5);
		btm.setPadding(pd,pd,pd,pd);
		LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		params1.setMargins(pd,pd,0,pd);
		//ImageView result = new ImageView(context);
		//ToDo : add true/false img
		btm.setLayoutParams(new LayoutParams(params1));
		btm.setGravity(Gravity.CENTER_VERTICAL);
		params1.weight = 1;
		nextBtn= new MaterialButton(context);
		nextBtn.setText(CHECK_TEXT);
		nextBtn.setCornerRadius(QuizUtils.toDp(context,6));
		nextBtn.setLayoutParams(params1);
		
		nextBtn.setOnClickListener(v->{
				if(adapter.getSize()==0) return;
				int cr=viewPager2.getCurrentItem();
				animateBtn();
				if(state==STATE.ERROR){
					pagerAdapter.getView(cr).reset();
					nextBtn.setText(CHECK_TEXT);
					state = STATE.NOTHING;
				} else if(state==STATE.DONE){
					nextBtn.setText(CHECK_TEXT);
					state = STATE.NOTHING;
					pagerAdapter.getView(cr).reset();
					if(onQues!=null)
						onQues.onAnswer(cr,adapter.getSize()-1==cr);
					if(adapter.getSize()-1>cr){
						viewPager2.setCurrentItem(cr+1);
					}
				} else if(pagerAdapter.getView(cr).isCorrect()){
					nextBtn.setText(NEXT_TEXT);
					state = STATE.DONE;
					if((adapter.getMaxPage()==cr)&&(adapter.getSize()-1>cr))
						adapter.unLock(cr+1);
				} else {
					nextBtn.setText(ERROR_TEXT);
					state = STATE.ERROR;
				}
		});
		
		addView(btm);
		btm.addView(nextBtn);
		viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				if(position>0&&position>adapter.getMaxPage())
					viewPager2.setCurrentItem(position-1);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				super.onPageScrolled(position, positionOffset, positionOffsetPixels);
				//if(position==adapter.getMaxPage()&&positionOffset>0){
				//To disable scrolling when the user try to view the next page...	
				//}
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				super.onPageScrollStateChanged(state);
			}
		});
		
	}
	
	public QuizList setEditMode(boolean b){
		if(delBtn==null&&b){
			setupEditMode();
		} else if(delBtn==null&&!b) return this;
		int vis=(b?View.VISIBLE:View.GONE);
		editBtn.setVisibility(vis);
		saveBtn.setVisibility(vis);
		addQuiz.setVisibility(vis);
		delBtn.setVisibility(vis);
		return this;
	}
	
	private void setupEditMode(){
		LayoutParams params = (LayoutParams)nextBtn.getLayoutParams();
		Context context = getContext();
		SimpleDraw.init(context);
		delBtn = new MaterialButton(context);
		delBtn.setText("DEl");
		delBtn.setContentDescription("To Delete The Quiz");
		delBtn.setCornerRadius(QuizUtils.toDp(context,6));
		delBtn.setLayoutParams(params);
		delBtn.setVisibility(View.GONE);
		delBtn.setOnClickListener(v->{
			if(adapter.getSize()==0) return;
			int pos = viewPager2.getCurrentItem();
			pagerAdapter.removeAt(pos);
			viewPager2.setAdapter(pagerAdapter);
		});
		btm.addView(delBtn);
		
		saveBtn = new MaterialButton(context);
		saveBtn.setText("SV");
		saveBtn.setCornerRadius(QuizUtils.toDp(context,6));
		saveBtn.setLayoutParams(params);
		saveBtn.setVisibility(View.GONE);
		saveBtn.setContentDescription("Save The Quizs");
		saveBtn.setOnClickListener(v->{
			String savePath = context.getExternalFilesDir(null)+"/save.quizf";
			FileUtil.writeToFile(savePath,adapter.toStringArray());
			Snackbar snackbar = Snackbar.make(saveBtn,"Saved To : "+savePath,Snackbar.LENGTH_SHORT);
			snackbar.getView().setOnClickListener(snackview->{
				snackbar.dismiss();
			});
			snackbar.show();
		});
		btm.addView(saveBtn);
		
		editBtn = new MaterialButton(context);
		editBtn.setText("ED");
		editBtn.setContentDescription("Edit The Quiz");
		editBtn.setLayoutParams(params);
		editBtn.setCornerRadius(QuizUtils.toDp(context,6));
		btm.addView(editBtn);
		editBtn.setVisibility(View.GONE);
		editBtn.setOnClickListener(v->{
			getCurrentView().edit();
		});
		
		addQuiz = new ImageView(context);
		addQuiz.setContentDescription("Add new quiz");
		addQuiz.setLayoutParams(new LayoutParams(QuizUtils.toDp(context,40),QuizUtils.toDp(context,40)));
		addQuiz.setImageBitmap(SimpleDraw.plusBitmap);
		addQuiz.setScaleType(ImageView.ScaleType.FIT_CENTER);
		addQuiz.setVisibility(View.GONE);
		btm.addView(addQuiz,0);
		addQuiz.setOnClickListener(view->{
			QuizEditor.addQuiz(context,adapter,(q)->{
				pagerAdapter.addQuiz(q);
				pagerAdapter.notifyItemInserted(adapter.getSize()-1);
				return true;
			});
		});
	}
	
	private void animateBtn(){
		nextBtn.setScaleX(0);
		nextBtn.setScaleY(0);
		nextBtn.animate().scaleX(1).scaleY(1).start();
	}
	
	public QuizAdapter getAdapter(){
		return adapter;
	}
	
	public ImageView getCloseIcon(){
		return close;
	}
	
	public MaterialButton getNextButton(){
		return nextBtn;
	}
	
	public QuizView getCurrentView(){
		return pagerAdapter.getView(viewPager2.getCurrentItem());
	}
	
	public void setAdapter(QuizAdapter adp){
		//get last page ...
		int last=0;
		if(adapter!=null&&viewPager2.getCurrentItem()>0){
			last = viewPager2.getCurrentItem();
		}
		
		adapter = adp;
		viewPager2.setLayoutDirection(adp.isRtl()?LayoutDirection.RTL:LayoutDirection.LTR);
		
		if(pagerAdapter==null)
			pagerAdapter = new QuizListPagerAdapter((AppCompatActivity)getContext(),adp);
				else pagerAdapter.setAdapter(adp);
		
		viewPager2.setAdapter(pagerAdapter);
		
		if(last>0&&adapter.getSize()>last&&viewPager2.getCurrentItem()!=last)
			viewPager2.setCurrentItem(last);
	}
	
	onQuestionAnswer onQues;
	public void addListener(onQuestionAnswer question){
		onQues = question;
	}
	
	public interface onQuestionAnswer {
		public void onAnswer(int pos,boolean isLast);
	}
	
	private enum STATE {
		DONE,NOTHING,ERROR
	}
}