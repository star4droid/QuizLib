package com.star4droid.QuizLib.Adapters;

import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.QuizView;
import com.star4droid.QuizLib.Views.ViewFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import com.star4droid.QuizLib.QuizAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizListPagerAdapter extends FragmentStateAdapter {
	QuizAdapter adapter;
	ArrayList<Fragment> viewsList= new ArrayList<>();
	public QuizListPagerAdapter(AppCompatActivity activity,QuizAdapter adp){
		super(activity);
		setAdapter(adp);
	}
	public void setAdapter(QuizAdapter adp){
		adapter = adp;
		viewsList.clear();
		for(int x=0;x<adp.getSize();x++){
			viewsList.add(new ViewFragment(adp.getQuizView(x)));
		}
	}
	
	public void removeAt(int position){
		adapter.removeQuiz(position);
		viewsList.remove(position);
	}
	
	public void addQuiz(Quiz qz){
		adapter.addQuiz(qz);
		viewsList.add(new ViewFragment(adapter.getQuizView(qz)));
	}
	
	public QuizView getView(int pos){
		return (QuizView)viewsList.get(pos).getView();
	}
	
	@Override
	public int getItemCount() {
		return adapter==null?0:adapter.getSize();
	}
	
	@Override
	public Fragment createFragment(int pos) {
		return viewsList.get(pos);
	}
	
  
