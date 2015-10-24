package br.com.targettrust.exercicio13;

import java.util.ArrayList;

public class DataHandler {

	ArrayList<String> mListStrings = new ArrayList<String>();

	public void setData(ArrayList<String> temp) {
		mListStrings = temp;
	}

	public ArrayList<String> getData() {
		return mListStrings;
	}
}
