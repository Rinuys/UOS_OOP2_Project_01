package Pack01;


import java.util.regex.*;
import java.util.Vector;
import java.util.StringTokenizer;


class OutParsing{
	StringTokenizer st ;
	String processOutParsing = new String();
	public String type = "void";
	public String access = "public";
	public OutParsing(String str, Class myClass, KeyWord kw){
		st = new StringTokenizer(str,": (),\r\n");
		
		while(st.hasMoreTokens()){
			String temp = st.nextToken();
			int keyWordIndex = kw.IsKeyWord(temp);
			if(keyWordIndex != -1){
				String KeyWordType;
				KeyWordType = kw.WhatKeyWord(keyWordIndex);
				if(KeyWordType.equals("class")){
					processOutParsing = "class";
					Parsing.Process = "class";
				}
				else if(KeyWordType.equals("access")){
					access = temp;
				}
				else{
					type = temp;
					Parsing.Process = "method";;
				}
				
			}
			else{
				if(processOutParsing.equals("class")){
					myClass.setName(temp);
					myClass.setAccess("public");
					processOutParsing = null;
				}
				else if(temp.equals(myClass.getName())){
					processOutParsing = "method";
					Parsing.Process = "method";
				}
				else if(processOutParsing.equals("method")){
					for(int i = 0 ; i < myClass.getMethodListSize() ; i++){
						if(myClass.getMethod(i).equals(temp)){
							Parsing.methodIndex = i;
							return ;
						}
					}
					for(int i = 0 ; i < myClass.getMethodListSize() ; i++){
						int count = 0;
						for(int j = 0 ; j < temp.length() ; j++){
							if(myClass.getMethod(i).getName().charAt(j) == temp.charAt(j)){
								count ++;
							}
						}
						if(temp.length() == count){
							Parsing.methodIndex = i;
							processOutParsing = "parameter";
							break;
						}
					}
					
				}
				else if(processOutParsing.equals("parameter")){
					Member tempMember = new Member(temp,type,access);
					myClass.getMethod(Parsing.methodIndex).addUsedMember(tempMember);
					access = "public";
					type = "void";
				}
			}
			//System.out.println(temp);
		}
	}
}

class InParsing{
	StringTokenizer st ;
	String processOutParsing = new String();
	public String type = "void";
	public String access = new String();
	public InParsing(String str, Class myClass, KeyWord kw){
		if(Parsing.Process.equals("class")){
			st = new StringTokenizer(str,"\t: {}\r\n;");
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				int keyWordIndex = kw.IsKeyWord(temp);
				if(keyWordIndex != -1){
					String KeyWordType;
					KeyWordType = kw.WhatKeyWord(keyWordIndex);
					if(KeyWordType.equals("access")){
						access = temp;
					}
					else{
						type = temp;
					}
				}
				else{
					if(IsMethod(temp)){
						if(IsMethodEnd(temp)){
							Method tempMethod = new Method(temp, type, access);
							myClass.addMethod(tempMethod);
							type = "void";
						}
						else{
							while(!IsMethodEnd(temp)){
								temp+=st.nextToken();
							}
							Method tempMethod = new Method(temp, type, access);
							myClass.addMethod(tempMethod);
							type = "void";
						}
					}
					else{
						if(IsArray(temp)){
							StringTokenizer st2 = new StringTokenizer(temp,"[] ");
							String temp2 = st2.nextToken();
							type+="[]";
							Member tempMember = new Member(temp2,type,access);
							myClass.addMember(tempMember);
						}
						else{
							Member tempMember = new Member(temp,type,access);
							myClass.addMember(tempMember);
						}
					}
				}
				
				
			}
		}
		else if(Parsing.Process.equals("method")){
			st = new StringTokenizer(str,"\t: {}+![]%=()\r\n;");
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				for(int i = 0 ; i < myClass.getMemberListSize(); i++){
					if(myClass.getMember(i).getName().equals(temp)){
						int check = 0;
						for(int j = 0 ; j < myClass.getMethod(Parsing.methodIndex).getUsedMemberListSize() ; j++){
							if(myClass.getMethod(Parsing.methodIndex).getUsedMember(j).getName().equals(temp)){
								check=1;
								break;
							}
						}
						if(check == 0){
							myClass.getMethod(Parsing.methodIndex).addUsedMember(myClass.getMember(i));
						}
						else break;
					}
				}
			}
		}
	}
	public boolean IsMethod(String str){
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == '('){
				return true;
			}
		}
		return false;
	}
	public boolean IsMethodEnd(String str){
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == ')'){
				return true;
			}
		}
		return false;
	}
	public boolean IsArray(String str){
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
			if(rightIndex==0){
				op = new OutParsing(str.substring(rightIndex, leftIndex), myClass, kw);
			}
			else{
				if(str.charAt(rightIndex+1)==';'){
					op = new OutParsing(str.substring(rightIndex+4, leftIndex), myClass, kw);
				}
				else{
					op = new OutParsing(str.substring(rightIndex+3, leftIndex), myClass, kw);
				}
				
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
			
			new InParsing(str.substring(leftIndex, rightIndex+1),myClass,kw);
			
			leftIndex = rightIndex -1 ;
		}
	}
}