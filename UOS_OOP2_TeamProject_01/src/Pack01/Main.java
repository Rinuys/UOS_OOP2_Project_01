package Pack01;

import java.io.*;

public class Main {
	private static StringBuffer buffer = new StringBuffer();
	
	public static void main(String[] args) {
		Class myClass = new Class(null);
		new ReadFileData(buffer);                 // 파일을 읽어서 buffer로 옮김
		new Parsing(buffer, myClass);            // 파싱
		//System.out.println(myClass.getName());
		for(int i = 0 ; i<myClass.getMethodListSize();i++){
			System.out.println(myClass.getMethod(i).toString());
		}
		for(int i = 0 ; i<myClass.getMemberListSize();i++){
			System.out.println(myClass.getMember(i).toString());
		}
	}

}
