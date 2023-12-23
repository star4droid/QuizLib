package com.star4droid.QuizLib.Utils;
import java.io.File;
import java.io.FileReader;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileUtil {
	public static void writeToFile(String path, String text) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(text);
			osw.close();
			fos.close();
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readFromFile(String path) {
		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		try {
			fr = new FileReader(new java.io.File(path));
			
			char[] buff = new char[1024];
			int length = 0;
			
			while ((length = fr.read(buff)) > 0) {
				sb.append(new String(buff, 0, length));
			}
			return sb.toString();
			} catch (java.io.IOException e) {
			e.printStackTrace();
			} finally {
			if (fr != null) {
				try {
					fr.close();
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public static ArrayList<String> listDir(String path) {
		File dir = new File(path);
		ArrayList<String> list = new ArrayList<>();
		if (!dir.exists() || dir.isFile()) return list;
		
		File[] listFiles = dir.listFiles();
		if (listFiles == null || listFiles.length <= 0) return list;
		
		list.clear();
		for (File file : listFiles) {
			list.add(file.getAbsolutePath());
		}
		return list;
	}
}