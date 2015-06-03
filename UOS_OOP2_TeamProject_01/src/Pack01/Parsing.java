package Pack01;

import java.util.Vector;
import java.util.StringTokenizer;

class OutParsing{                                                                 // '{' 의 앞부분을 파싱.
	StringTokenizer st ;
	String processOutParsing = new String();                                // OutParsing의 process상태를 알려줌
	public String type = "void";
	public String access = "public";
	public OutParsing(String str, Class myClass, KeyWord kw){           // 파싱하려는 문자열(str)과 myClass, kw(KeyWord)를 인자로 받는다.
		st = new StringTokenizer(str,": (),\r\n");
		
		while(st.hasMoreTokens()){                                            // 남아있는 토큰이 없을 때 까지 돌린다.
			String temp = st.nextToken();
			int keyWordIndex = kw.IsKeyWord(temp);                      // 토큰이 keyword인지 확인
			if(keyWordIndex != -1){                                            // 토큰이 keyword일때
				String KeyWordType;
				KeyWordType = kw.WhatKeyWord(keyWordIndex);        // keyword의 타입을 받음
				if(KeyWordType.equals("class")){                              // 타입이 class일때
					processOutParsing = "class";                              // OutParsing의 process를 class로 변환
					Parsing.Process = "class";                                  // 전체 Parsing의 process를 class로 변환
				}
				else if(KeyWordType.equals("access")){                       // access일때 access에 temp를 넣음
					access = temp;
				}
				else{
					type = temp;                                                  // 나머지(type)일때 
					Parsing.Process = "method";;                               // type에 temp를 넣고 전체 process를 method로 변환
				}
			}
			else{                                                                    // 토큰이 keyword가 아닐때
				if(processOutParsing.equals("class")){                         // OutParsing의 process가 class일때
					myClass.setName(temp);                                     // 이름과 access를 설정하고 OutParsing의 process를 null로 설정
					myClass.setAccess("public");
					processOutParsing = null;
				}
				else if(temp.equals(myClass.getName())){                     // 토큰이 myClass의 이름일때
					processOutParsing = "method";                           // process들을 method로 변환
					Parsing.Process = "method";
				}
				else if(processOutParsing.equals("method")){               // OutParsing의 process가 method일때
					for(int i = 0 ; i < myClass.getMethodListSize() ; i++){
						if(myClass.getMethod(i).getName().equals(temp)){
							Parsing.methodIndex = i;                          // method의 index를 찾음
							return ;
						}
					}
					for(int i = 0 ; i < myClass.getMethodListSize() ; i++){ // 못찾을 경우 ()전까지의 이름을 비교해서 있는지 확인한다.
						int count = 0;
						for(int j = 0 ; j < temp.length() ; j++){
							if(myClass.getMethod(i).getName().charAt(j) == temp.charAt(j)){
								count ++;
							}
						}
						if(temp.length() == count){
							Parsing.methodIndex = i;                           // 이때 찾은 것은 parameter가 존재하므로 parameter로
							processOutParsing = "parameter";                // OutParsing 의 process를 parameter로 변환
							break;
						}
					}
				}
				else if(processOutParsing.equals("parameter")){            // OutParsing 의 process가 parameter일 경우 
					Member tempMember = new Member(temp,type,access);
					myClass.getMethod(Parsing.methodIndex).addMember(tempMember);
					access = "public";
					type = "void";
				}
			}
		}
	}
}

class InParsing{                                                                  // '{' 와 '}' 안의 내용을 Parsing하는 부분이다.
	public String type = "void";
	public String access = new String();
	public InParsing(String str, Class myClass, KeyWord kw){             // OutParsing과 마찬가지로 인자를 받는다.
		StringTokenizer st ;
		if(Parsing.Process.equals("class")){
			st = new StringTokenizer(str,"\t: {}\r\n;");
			while(st.hasMoreTokens()){                                         // token이 없을때 까지 반복
				String temp = st.nextToken();
				int keyWordIndex = kw.IsKeyWord(temp);                   // keyword인지 확인
				if(keyWordIndex != -1){                                         // keyword인 경우
					String KeyWordType;
					KeyWordType = kw.WhatKeyWord(keyWordIndex);   
					if(KeyWordType.equals("access")){                         // type이 access인 경우 access에 temp를 넣는다.
						access = temp;
					}
					else{                                                           // type이 type인 경우 type에 temp를 넣는다.
						type = temp;
					}
				}
				else{                                                               // keyword가 아닌경우
					if(IsMethod(temp)){                                         // method면 true
						if(IsMethodEnd(temp)){                                 // 문자열중에 ')'로 닫히면 Method를 add
							Method tempMethod = new Method(temp, type, access);
							myClass.addMethod(tempMethod);
							type = "void";
						}
						else{                                                       // ')'로 안닫히면 다음 토큰을 불러와서 ')'를 맞춤
							while(!IsMethodEnd(temp)){
								temp+=st.nextToken();
							}
							Method tempMethod = new Method(temp, type, access);
							myClass.addMethod(tempMethod);
							type = "void";
						}
					}
					else{                                                             // member일 때
						if(IsArray(temp)){                                          // array일 때
							StringTokenizer st2 = new StringTokenizer(temp,"[] ");
							String temp2 = st2.nextToken();
							type+="[]";
							Member tempMember = new Member(temp2,type,access);
							myClass.addMember(tempMember);
						}  
						else{                                                         // array가 아닐 때
							Member tempMember = new Member(temp,type,access);
							myClass.addMember(tempMember);
						}
					}
				}
			}
		}
		else if(Parsing.Process.equals("method")){                                // 전제 Parsing process가 method일 경우
			st = new StringTokenizer(str,"\t: {}+![]%=()\r\n;");
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				for(int i = 0 ; i < myClass.getMemberListSize(); i++){          // usedMember에 add 
					if(myClass.getMember(i).getName().equals(temp)){          // MemberList에 있는지 확인
						int check = 0;
						for(int j = 0 ; j < myClass.getMethod(Parsing.methodIndex).getUsedMemberListSize() ; j++){ // 동일 멤버 검색
							if(myClass.getMethod(Parsing.methodIndex).getUsedMember(j).getName().equals(temp)){
								check=1;
								break;
							}
						}
						if(check == 0){                                                                                             //usedMember를 add
							myClass.getMethod(Parsing.methodIndex).addUsedMember(myClass.getMember(i));
						}
						else break;
					}
				}
			}
		}
	}
	public boolean IsMethod(String str){                                   // 메소드인지를 확인하는 메소드
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == '('){
				return true;
			}
		}
		return false;
	}
	public boolean IsMethodEnd(String str){                               // 문자열에 메소드의 끝이 있는지 확인하는 메소드
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == ')'){
				return true;
			}
		}
		return false;
	}
	public boolean IsArray(String str){                                       // 배열인지 아닌지 확인하는 메소드
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