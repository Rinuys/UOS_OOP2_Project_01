package Pack01;

import java.io.*;

public class ReadFileData {
	
	public ReadFileData(StringBuffer buffer){
		int b = 0;
		FileInputStream file = null;
		try{
			file = new FileInputStream("Queue.cpp");
			b=file.read();
			while(b!=-1){
				buffer.append((char)b);
				b=file.read();
			}
			//System.out.println(buffer);
		} catch(FileNotFoundException e){
			System.out.println("Oops : FileNotFoundException");
		} catch(IOException e){
			System.out.println("Input error");
		}
	}
	
}
