package Pack01;

import java.io.*;

public class Main {
	private static StringBuffer buffer = new StringBuffer();
	
	public static void main(String[] args) {
		new ReadFileData(buffer);                 // ������ �о buffer�� �ű�
		new Parsing(buffer);                       // �Ľ�
	}

}
