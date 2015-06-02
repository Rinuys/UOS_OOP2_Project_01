package Pack01;

import java.io.*;

public class WriteFileData {
	public WriteFileData(File file, String str){
		try {
		     
		      BufferedWriter out = new BufferedWriter(new FileWriter(file));

		      out.write(str);

		      out.close();
		     
		    } catch (IOException e) {
		        System.err.println(e); // ������ �ִٸ� �޽��� ���
		        System.exit(1);
		    }
	}
}
