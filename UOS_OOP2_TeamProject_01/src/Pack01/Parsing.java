package Pack01;

import java.util.Vector;
import java.util.StringTokenizer;

class OutParsing{                                                                 // '{' �� �պκ��� �Ľ�.
	StringTokenizer st ;
	String processOutParsing = new String();                                // OutParsing�� process���¸� �˷���
	public String type = "void";
	public String access = "public";
	public OutParsing(String str, Class myClass, KeyWord kw){           // �Ľ��Ϸ��� ���ڿ�(str)�� myClass, kw(KeyWord)�� ���ڷ� �޴´�.
		st = new StringTokenizer(str,": (),\r\n");
		
		while(st.hasMoreTokens()){                                            // �����ִ� ��ū�� ���� �� ���� ������.
			String temp = st.nextToken();
			int keyWordIndex = kw.IsKeyWord(temp);                      // ��ū�� keyword���� Ȯ��
			if(keyWordIndex != -1){                                            // ��ū�� keyword�϶�
				String KeyWordType;
				KeyWordType = kw.WhatKeyWord(keyWordIndex);        // keyword�� Ÿ���� ����
				if(KeyWordType.equals("class")){                              // Ÿ���� class�϶�
					processOutParsing = "class";                              // OutParsing�� process�� class�� ��ȯ
					Parsing.Process = "class";                                  // ��ü Parsing�� process�� class�� ��ȯ
				}
				else if(KeyWordType.equals("access")){                       // access�϶� access�� temp�� ����
					access = temp;
				}
				else{
					type = temp;                                                  // ������(type)�϶� 
					Parsing.Process = "method";;                               // type�� temp�� �ְ� ��ü process�� method�� ��ȯ
				}
			}
			else{                                                                    // ��ū�� keyword�� �ƴҶ�
				if(processOutParsing.equals("class")){                         // OutParsing�� process�� class�϶�
					myClass.setName(temp);                                     // �̸��� access�� �����ϰ� OutParsing�� process�� null�� ����
					myClass.setAccess("public");
					processOutParsing = null;
				}
				else if(temp.equals(myClass.getName())){                     // ��ū�� myClass�� �̸��϶�
					processOutParsing = "method";                           // process���� method�� ��ȯ
					Parsing.Process = "method";
				}
				else if(processOutParsing.equals("method")){               // OutParsing�� process�� method�϶�
					for(int i = 0 ; i < myClass.getMethodListSize() ; i++){
						if(myClass.getMethod(i).getName().equals(temp)){
							Parsing.methodIndex = i;                          // method�� index�� ã��
							return ;
						}
					}
					for(int i = 0 ; i < myClass.getMethodListSize() ; i++){ // ��ã�� ��� ()�������� �̸��� ���ؼ� �ִ��� Ȯ���Ѵ�.
						int count = 0;
						for(int j = 0 ; j < temp.length() ; j++){
							if(myClass.getMethod(i).getName().charAt(j) == temp.charAt(j)){
								count ++;
							}
						}
						if(temp.length() == count){
							Parsing.methodIndex = i;                           // �̶� ã�� ���� parameter�� �����ϹǷ� parameter��
							processOutParsing = "parameter";                // OutParsing �� process�� parameter�� ��ȯ
							break;
						}
					}
				}
				else if(processOutParsing.equals("parameter")){            // OutParsing �� process�� parameter�� ��� 
					Member tempMember = new Member(temp,type,access);
					myClass.getMethod(Parsing.methodIndex).addMember(tempMember);
					access = "public";
					type = "void";
				}
			}
		}
	}
}

class InParsing{                                                                  // '{' �� '}' ���� ������ Parsing�ϴ� �κ��̴�.
	public String type = "void";
	public String access = new String();
	public InParsing(String str, Class myClass, KeyWord kw){             // OutParsing�� ���������� ���ڸ� �޴´�.
		StringTokenizer st ;
		if(Parsing.Process.equals("class")){
			st = new StringTokenizer(str,"\t: {}\r\n;");
			while(st.hasMoreTokens()){                                         // token�� ������ ���� �ݺ�
				String temp = st.nextToken();
				int keyWordIndex = kw.IsKeyWord(temp);                   // keyword���� Ȯ��
				if(keyWordIndex != -1){                                         // keyword�� ���
					String KeyWordType;
					KeyWordType = kw.WhatKeyWord(keyWordIndex);   
					if(KeyWordType.equals("access")){                         // type�� access�� ��� access�� temp�� �ִ´�.
						access = temp;
					}
					else{                                                           // type�� type�� ��� type�� temp�� �ִ´�.
						type = temp;
					}
				}
				else{                                                               // keyword�� �ƴѰ��
					if(IsMethod(temp)){                                         // method�� true
						if(IsMethodEnd(temp)){                                 // ���ڿ��߿� ')'�� ������ Method�� add
							Method tempMethod = new Method(temp, type, access);
							myClass.addMethod(tempMethod);
							type = "void";
						}
						else{                                                       // ')'�� �ȴ����� ���� ��ū�� �ҷ��ͼ� ')'�� ����
							while(!IsMethodEnd(temp)){
								temp+=st.nextToken();
							}
							Method tempMethod = new Method(temp, type, access);
							myClass.addMethod(tempMethod);
							type = "void";
						}
					}
					else{                                                             // member�� ��
						if(IsArray(temp)){                                          // array�� ��
							StringTokenizer st2 = new StringTokenizer(temp,"[] ");
							String temp2 = st2.nextToken();
							type+="[]";
							Member tempMember = new Member(temp2,type,access);
							myClass.addMember(tempMember);
						}  
						else{                                                         // array�� �ƴ� ��
							Member tempMember = new Member(temp,type,access);
							myClass.addMember(tempMember);
						}
					}
				}
			}
		}
		else if(Parsing.Process.equals("method")){                                // ���� Parsing process�� method�� ���
			st = new StringTokenizer(str,"\t: {}+![]%=()\r\n;");
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				for(int i = 0 ; i < myClass.getMemberListSize(); i++){          // usedMember�� add 
					if(myClass.getMember(i).getName().equals(temp)){          // MemberList�� �ִ��� Ȯ��
						int check = 0;
						for(int j = 0 ; j < myClass.getMethod(Parsing.methodIndex).getUsedMemberListSize() ; j++){ // ���� ��� �˻�
							if(myClass.getMethod(Parsing.methodIndex).getUsedMember(j).getName().equals(temp)){
								check=1;
								break;
							}
						}
						if(check == 0){                                                                                             //usedMember�� add
							myClass.getMethod(Parsing.methodIndex).addUsedMember(myClass.getMember(i));
						}
						else break;
					}
				}
			}
		}
	}
	public boolean IsMethod(String str){                                   // �޼ҵ������� Ȯ���ϴ� �޼ҵ�
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == '('){
				return true;
			}
		}
		return false;
	}
	public boolean IsMethodEnd(String str){                               // ���ڿ��� �޼ҵ��� ���� �ִ��� Ȯ���ϴ� �޼ҵ�
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == ')'){
				return true;
			}
		}
		return false;
	}
	public boolean IsArray(String str){                                       // �迭���� �ƴ��� Ȯ���ϴ� �޼ҵ�
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == '['){
				return true;
			}
		}
		return false;
	}
}

public class Parsing {
	public static int methodIndex;
	public static int stack = 0;
	public static KeyWord kw = new KeyWord();
	public static String Process = new String();
	public Parsing(StringBuffer buffer , Class myClass) {
		
		
		String str = buffer.toString();
		int leftIndex=0 , rightIndex=0;
		
		while(rightIndex < str.length()-1){
			
			for( ; ; leftIndex++){
				if(str.charAt(leftIndex) == '{'){
					stack++;
					break;	
				}
			}
			
			OutParsing op;
			String tempstr;
			if(rightIndex==0){
				tempstr =str.substring(rightIndex, leftIndex); 
			}
			else{
				if(str.charAt(rightIndex+1)==';'){
					tempstr = str.substring(rightIndex+4, leftIndex);
				}
				else{
					tempstr = str.substring(rightIndex+3, leftIndex);
				}
				
			}
			op = new OutParsing(tempstr, myClass, kw);
			if(Process.equals("class")){
				myClass.setOut(tempstr);
			}
			else{
				myClass.getMethod(methodIndex).setOut(tempstr);
			}
			
			for(rightIndex = leftIndex+1; ; rightIndex++){
				if(str.charAt(rightIndex) == '{'){
					stack++;
				}
				if(str.charAt(rightIndex) == '}'){
					stack--;
					if(stack == 0){
						break;
					}
				}
			}
			tempstr = str.substring(leftIndex, rightIndex+1);
			new InParsing(tempstr,myClass,kw);
			
			if(Process.equals("class")){
				tempstr+=";\n";
				myClass.setIn(tempstr);
			}
			else{
				myClass.getMethod(methodIndex).setIn(tempstr);
			}
			
			leftIndex = rightIndex -1 ;
		}
	}
}