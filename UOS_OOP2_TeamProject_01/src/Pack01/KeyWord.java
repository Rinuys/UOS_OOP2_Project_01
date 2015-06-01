package Pack01;

import java.util.Vector;
import java.util.StringTokenizer;

class Information{
	private String name;
	private String type;
	private String access;
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public void setAccess(String access){
		this.access = access;
	}
	public String getAccess(){
		return access;
	}
}

class Class extends Information{
	private Vector<Method> methodList = new Vector<Method>();
	private Vector<Member> memberList = new Vector<Member>();
	
	public Class(String name){
		super.setName(name);
		super.setAccess(null);
		super.setType(null);
	}
	public void addMethod(Method method){
		methodList.addElement(method);
	}
	public void addMember(Member member){
		memberList.addElement(member);
	}
	public Method getMethod(int index){
		return methodList.elementAt(index);
	}
	public Member getMember(int index){
		return memberList.elementAt(index);
	}
	public int getMethodListSize(){
		return methodList.size();
	}
	public int getMemberListSize(){
		return memberList.size();
	}
}

class Method extends Information{
	Vector<Member> memberList = new Vector<Member>();
	Vector<Member> usedMemberList = new Vector<Member>();
	public Method(String name, String type, String access){
		super.setName(name);
		super.setAccess(access);
		super.setType(type);
	}
	public void addMember(Member member){
		memberList.addElement(member);
	}
	public void addUsedMember(Member member){
		usedMemberList.addElement(member);
	}
	public Member getMember(int index){
		return memberList.elementAt(index);
	}
	public Member getUsedMember(int index){
		return usedMemberList.elementAt(index);
	}
	public int getUsedMemberListSize(){
		return usedMemberList.size();
	}
	public int getMemberListSize(){
		return memberList.size();
	}
	public String toString(){
		return super.getName() + " " + super.getType() + " " + super.getAccess();
	}
}

class Member extends Information{
	public Member(String name, String type, String access){
		super.setName(name);
		super.setAccess(access);
		super.setType(type);
	}
	public String toString(){
		return super.getName() + " " + super.getType() + " " + super.getAccess();
	}
}

public class KeyWord {
	public static String process = null;
	public static String access = "public";
	public static int stack = 0;
	public static int methodIndex = 0;
	public KeyWord(String str, Class myClass){
		StringTokenizer st;
		if(process == null){
			st = new StringTokenizer(str);
			while(st.hasMoreTokens()){
				if(st.nextToken().equals("class")){
					myClass.setName(st.nextToken());
					break;
				}
			}
			process = "class";
		}
		else if(process.equals("class")){
			st = new StringTokenizer(str);
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				if(temp.equals("{")){
					stack++;
					break;
				}
				if(temp.equals("public:")){
					process = "public:";
					access = "public";
					break;
				}
				if(temp.equals("private:")){
					process = "private:";
					access = "private";
					break;
				}
			}
		}
		else if(process.equals("public:") || process.equals("private:")){
			st=new StringTokenizer(str,"\t ;");
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				if(temp.equals("bool") || temp.equals("int") || temp.equals("void")){
					String type = temp;
					temp = st.nextToken();
					if(IsMethod(temp)){
						Method method = new Method(temp,type,access);
						myClass.addMethod(method);
					}
					else{
						if(IsArray(temp)){
							type=type+"[]";
							st = new StringTokenizer(temp,"[ ]");
							temp = st.nextToken();
						}
						Member member = new Member(temp,type,access);
						myClass.addMember(member);
					}
					break;
				}
				if(temp.equals("private:")){
					access = "private";
					break;
				}
				if(temp.equals("public:")){
					access = "public";
					break;
				}
				if(IsMethod(temp)){
					Method method = new Method(temp,"void",access);
					myClass.addMethod(method);
					break;
				}
				if(temp.equals("}")){
					process = "method";
					stack--;
					break;
				}
				
			}
		}
		else if(process.equals("method")){
			st = new StringTokenizer(str,":: \r");
			int param = 0;
			String paramType = null;
			while(st.hasMoreTokens() && process.equals("method")){
				String temp = st.nextToken();
				
				//System.out.print("/"+temp+"/");
				if(temp.equals("bool") || temp.equals("int") || temp.equals("void")){
					temp = st.nextToken();
					temp = st.nextToken();
					
					if(HasParam(temp)){
						for(int i = 0 ; i < myClass.getMethodListSize() && param != 1;i++){
							StringTokenizer st2 = new StringTokenizer(temp,"( )");
							String temp2;
							String processingMethod = null;
							while(st2.hasMoreTokens()){
								temp2 = st2.nextToken();
								if(processingMethod == null){
									int j;
									for(j = 0 ; j<temp2.length(); j++){
										if(temp2.charAt(j) != myClass.getMethod(i).getName().charAt(j)){
											break;
										}
									}
									if(j == temp2.length()){
										methodIndex = i;
										processingMethod = "select";
									}
									else break;
								}
								if(temp2.equals("int") || temp2.equals("bool") || temp2.equals("void")){
									paramType = temp2;
									param = 1;
								}
							}
							processingMethod = "null";
							continue;
							
							
						}
						
					}
					else{
						for(int i = 0 ; i < myClass.getMethodListSize();i++){
							if(myClass.getMethod(i).getName().equals(temp)){
								methodIndex = i;
								process = "InMethod";
								break;
							}
						}
					}
				}
				if(param == 1){
					while(st.hasMoreTokens()){
						temp = st.nextToken();
						
						StringTokenizer st2 = new StringTokenizer(temp,")");
						
						String temp2 = st2.nextToken();
						if(st2.hasMoreTokens()) continue;
						
						if(temp2.equals("int") || temp2.equals("bool") || temp2.equals("void")){
							paramType = temp2;
						}
						else{
							
							Member tempMember = new Member(temp2, paramType, "public");
							myClass.getMethod(methodIndex).addMember(tempMember);
						}
					}
					process = "InMethod";
					param = 0;
					break;
					
				}
				if(temp.equals(myClass.getName())){
					temp = st.nextToken();
					//System.out.println(myClass.getMethodListSize());
					for(int i = 0 ; i < myClass.getMethodListSize();i++){
						if(myClass.getMethod(i).getName().equals(temp)){
							methodIndex = i;
							process = "InMethod";
							break;
						}
					}
				}
				if(param == 1 && st.hasMoreTokens()){
					process = "InMethod";
					param = 0;
					break;
				}
			}
			
		}
		else if(process.equals("InMethod")){
			
			st = new StringTokenizer(str," \n\t\r[]()%+=!:;");
			
			while(st.hasMoreTokens()){
				String temp = st.nextToken();
				
				
				//System.out.println(myClass.getMethod(methodIndex).getName()+" "+temp);
				
				if(temp.equals("{")){
					stack++;
					break;
				}
				if(temp.equals("}")){
					stack--;
					break;
				}
				
				for(int i = 0; i < myClass.getMemberListSize();i++){
					if(myClass.getMember(i).getName().equals(temp)){
						int j;
						for(j = 0 ; j < myClass.getMethod(methodIndex).getUsedMemberListSize();j++){
							if(myClass.getMethod(methodIndex).getUsedMember(j).getName().equals(temp)){
								break;
							}
						}
						
						if(j==myClass.getMethod(methodIndex).getUsedMemberListSize()){
							myClass.getMethod(methodIndex).addUsedMember(myClass.getMember(i));
							break;
						}
						break;
					}
				}
				
			}
			if(stack == 0){
				process = "method";
			}
		}
		
	}
	public boolean IsMethod(String temp){
		for(int i = 0 ; i<temp.length();i++){
			if(temp.charAt(i)=='('){
				return true;
			}
		}
		return false;
	}
	public boolean IsArray(String temp){
		for(int i = 0 ; i<temp.length();i++){
			if(temp.charAt(i)=='['){
				return true;
			}
		}
		return false;
	}
	public boolean HasParam(String temp){
		for(int i = 0 ; i<temp.length();i++){
			if(temp.charAt(i)=='('){
				if(temp.charAt(i+1) != ')'){
					return true;
				}
			}
		}
		return false;
	}
}
