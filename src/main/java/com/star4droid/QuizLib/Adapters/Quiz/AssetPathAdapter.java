package com.star4droid.QuizLib.Adapters.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.Utils.QuizUtils;
import java.util.ArrayList;

public class AssetPathAdapter extends ListAdapter {
	public AssetPathAdapter(AppCompatActivity activity,String path){
		super(activity,new ArrayList<>());
		try {
		for (String p:activity.getAssets().list(path)){
			String assetPath=path+(path.equals("")?"":"/")+p;
			quizList.add(Quiz.fromString(QuizUtils.readAssetFile(activity,assetPath)));
		}
		} catch(Exception ex){}
	}
}