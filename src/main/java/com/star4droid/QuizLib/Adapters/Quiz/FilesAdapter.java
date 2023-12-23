package com.star4droid.QuizLib.Adapters.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import com.star4droid.QuizLib.Quiz;
import com.star4droid.QuizLib.Utils.FileUtil;
import java.io.File;
import java.util.ArrayList;
/*
Powered By Star4Droid (Annas Osman Ebrahim)
--> 25/November/2023 <--
FilesAdapter ...to load path contains quizes...
--> Read well before asking :)
....Any Questions...feel free to ask....
Happy Coding...≈≈
*/
public class FilesAdapter extends ListAdapter {
	
	public FilesAdapter(AppCompatActivity activity,String path){
		super(activity,new ArrayList<>());
		File[] files = new File(path).listFiles();
		if(files!=null)
			for(File file:files){
				quizList.add(Quiz.fromString(FileUtil.readFromFile(file.getAbsolutePath())));
			}
	}
	
}