package Pack01;

public class KeyWord {
	private String[][] KeyWordList;
	public KeyWord(){
		KeyWordList = new String[6][2];                 // 0열의 행들은 각각 키워드들을 저장.
															   // 1열의 행들은 각 키워드의 형식을 저장.
		KeyWordList[0][0] = "class"; KeyWordList[0][1] = "class" ;
		
		KeyWordList[1][0] = "int" ; KeyWordList[1][1] = "type" ;
		KeyWordList[2][0] = "bool" ; KeyWordList[2][1] = "type" ;
		KeyWordList[3][0] = "void" ; KeyWordList[3][1] = "type" ;
		
		KeyWordList[4][0] = "private" ; KeyWordList[4][1] = "access" ;
		KeyWordList[5][0] = "public" ; KeyWordList[5][1] = "access" ;
	}
	
	public int IsKeyWord(String str){
		int index;											   // str을 받아서 keyword인지를 확인 후 맞으면 키워드 인덱스(행)를 반환
		for(index = 0 ; index < 6 ; index++){           // 없으면 -1을 반환
			if(str.equals(KeyWordList[index][0])){
				return index;
			}
		}
		return -1;
	}
	
	public String WhatKeyWord(int index){            // 인덱스를 받아서 키워드의 타입을 반환
		return KeyWordList[index][1];
	}
}