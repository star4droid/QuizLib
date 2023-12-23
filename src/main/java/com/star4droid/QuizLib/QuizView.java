package com.star4droid.QuizLib;

import android.content.Context;

public interface QuizView {
	public boolean isCorrect();
	public void reset();
	public void edit();
	public void update();
}