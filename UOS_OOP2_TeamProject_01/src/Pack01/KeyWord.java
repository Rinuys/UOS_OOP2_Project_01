package Pack01;

import java.util.Vector;

public class KeyWord {
	private String[][] KeyWordList;
	public KeyWord(){
		KeyWordList = new String[6][2];
		
		KeyWordList[0][0] = "class"; KeyWordList[0][1] = "class" ;
		
		KeyWordList[1][0] = "int" ; KeyWordList[1][1] = "type" ;
		KeyWordList[2][0] = "bool" ; KeyWordList[2][1] = "type" ;
		KeyWordList[3][0] = "void" ; KeyWordList[3][1] = "type" ;
		
		KeyWordList[4][0] = "private" ; KeyWordList[4][1] = "access" ;
		KeyWordList[5][0] = "public" ; KeyWordList[5][1] = "access" ;
	}
	
	public int IsKeyWord(String str){
		int index;
		for(index = 0 ; index < 6 ; index++){
			if(str.equals(KeyWordList[index][0])){
				return index;
			}
		}
		return -1;
	}
	
	public String WhatKeyWord(int index){
		return KeyWordList[index][1];
	}
	
}