package com.star4droid.QuizLib.Views;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import androidx.fragment.app.Fragment;

public class ViewFragment extends Fragment {
	View view;
	public ViewFragment(View v){
		view= v;
		//view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
	}
	
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		return view;
	}
	
	public void setView(View v){
		view = v;
	}
	
}