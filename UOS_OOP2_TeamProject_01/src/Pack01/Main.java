package Pack01;

import java.io.*;

public class Main {
	private static StringBuffer buffer = new StringBuffer();
	
	public static void main(String[] args) {
		new ReadFileData(buffer);                 // 파일을 읽어서 buffer로 옮김
		new Parsing(buffer);                       // 파싱
	}

}
