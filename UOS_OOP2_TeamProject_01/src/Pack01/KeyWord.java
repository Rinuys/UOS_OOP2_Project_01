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
	Vector<Member> useMemberList = new Vector<Member>();
	Vector<String> parameter = new Vector<String>();
	public Method(String name, String type, String access){
		super.setName(name);
		super.setAccess(access);
		super.setType(type);
	}
	public void addMember(Member member){
		memberList.addElement(member);
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
	public KeyWord(String str, Class myClass){
		StringTokenizer st;
		if(process == null){
			st = new StringTokenizer(str);
			while(st.hasMoreTokens()){
				if(st.nextToken().equals("class")){
					myClass.setName(st.nextToken());
					//System.out.println(myClass.getName());
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
					if(isMethod(temp)){
						Method method = new Method(temp,type,access);
						myClass.addMethod(method);
					}
					else{
						if(isArray(temp)){
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
				if(isMethod(temp)){
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
		
	}
	public boolean isMethod(String temp){
		for(int i = 0 ; i<temp.length();i++){
			if(temp.charAt(i)=='('){
				return true;
			}
		}
		return false;
	}
	public boolean isArray(String temp){
		for(int i = 0 ; i<temp.length();i++){
			if(temp.charAt(i)=='['){
				return true;
			}
		}
		return false;
	}
}
