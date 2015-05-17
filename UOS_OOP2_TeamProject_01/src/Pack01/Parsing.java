package Pack01;

import java.io.*;

public class Parsing {
	public Parsing(StringBuffer buffer){
		int firstIndex=0;
		int secondIndex=0;
		while(secondIndex<=buffer.lastIndexOf("\n")){      // 파일의 끝까지 이동
			while(buffer.charAt(secondIndex)!='\n'){         // "\n"을 기준으로 라인을 끊음
				secondIndex++;
			}                                   // secondIndex-firstIndex : 라인 길이
			                                    // buffer.substring(firstIndex,secondIndex) : 그 라인을 string으로 반환
			new KeyWord(buffer.substring(firstIndex,secondIndex));
			//System.out.println(secondIndex-firstIndex+" "+buffer.substring(firstIndex,secondIndex));
			secondIndex++;
			firstIndex=secondIndex;
		}
	}
}
