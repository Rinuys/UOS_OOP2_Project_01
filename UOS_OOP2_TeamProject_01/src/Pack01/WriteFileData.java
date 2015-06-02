package Pack01;

import java.io.*;

public class WriteFileData {
	public WriteFileData(File file, String str){
		try {
		     
		      BufferedWriter out = new BufferedWriter(new FileWriter(file));

		      out.write(str);

		      out.close();
		     
		    } catch (IOException e) {
		        System.err.println(e); // 에러가 있다면 메시지 출력
		        System.exit(1);
		    }
	}
}
