package Pack01;

import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;

public class Parsing {
	public Parsing(StringBuffer buffer, Class myClass){
		int firstIndex=0;
		int secondIndex=0;
		while(secondIndex<=buffer.lastIndexOf("\n")){      // ������ ������ �̵�
			while(buffer.charAt(secondIndex)!='\n'){         // "\n"�� �������� ������ ����
				secondIndex++;
			}                                   // secondIndex-firstIndex : ���� ����
			                                    // buffer.substring(firstIndex,secondIndex) : �� ������ string���� ��ȯ
			
			new KeyWord(buffer.substring(firstIndex,secondIndex), myClass);
			
			//System.out.println(secondIndex-firstIndex+" "+buffer.substring(firstIndex,secondIndex));
			secondIndex++;
			firstIndex=secondIndex;
		}
	}
}
