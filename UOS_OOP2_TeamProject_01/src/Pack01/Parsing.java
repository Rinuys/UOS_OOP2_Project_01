package Pack01;

import java.io.*;

public class Parsing {
	public Parsing(StringBuffer buffer){
		int firstIndex=0;
		int secondIndex=0;
		while(secondIndex<=buffer.lastIndexOf("\n")){      // ������ ������ �̵�
			while(buffer.charAt(secondIndex)!='\n'){         // "\n"�� �������� ������ ����
				secondIndex++;
			}                                   // secondIndex-firstIndex : ���� ����
			                                    // buffer.substring(firstIndex,secondIndex) : �� ������ string���� ��ȯ
			new KeyWord(buffer.substring(firstIndex,secondIndex));
			//System.out.println(secondIndex-firstIndex+" "+buffer.substring(firstIndex,secondIndex));
			secondIndex++;
			firstIndex=secondIndex;
		}
	}
}
