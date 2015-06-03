package Pack01;

import java.util.Vector;           // 동적인 배열을 사용하기 위해 Vector를 사용했다.

class Information{                // Class, Method, Member class들의 공통적인 부분을 묶어서 Information class를 만들었다.
	private String name;
	private String type;
	private String access;
	private String In;             // 코드에서 '{' 부터 '}' 까지의 문자열
	private String Out;           // 코드에서 '{' 의 앞부분의 문자열
	
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
	public String getIn(){                        // 코드에서 '{' 부터 '}' 까지의 부분을 반환
		return In;
	}
	public void setIn(String str){               // 코드에서 '{' 부터 '}' 까지의 부분을 수정
		this.In = str;
	}
	public String getOut(){                      // 코드에서 '{' 전의 내용을 반환
		return Out;
	}
	public void setOut(String str){             // 코드에서 '{' 전의 내용을 수정
		this.Out = str;
	}
}

public class Class extends Information{                                         // Class 를 저장하기 위한 class
	private Vector<Method> methodList = new Vector<Method>();        // Class의 method들을 Vector로 관리한다.
	private Vector<Member> memberList = new Vector<Member>();      // Class의 field들을 Vector로 관리한다.
	
	public Class(String name){
		super.setName(name);                                                      // 이름을 인자로 받아서 Class의 이름을 설정
		super.setAccess("public");
		super.setType(null);
	}
	public void addMethod(Method method){                                  // Vector에 메소드를 추가
		methodList.addElement(method);
	}
	public void addMember(Member member){                                // Vector에 필드를 추가
		memberList.addElement(member);
	}
	public Method getMethod(int index){                                       // i번째 method를 반환
		return methodList.elementAt(index);
	}
	public Member getMember(int index){                                      // i번째 field를 반환
		return memberList.elementAt(index);
	}
	public int getMethodListSize(){                                                // Method들의 수를 반환
		return methodList.size();
	}
	public int getMemberListSize(){                                               // field들의 수를 반환
		return memberList.size();
	}
}

class Method extends Information{                                             // Method class
	Vector<Member> memberList = new Vector<Member>();               // Method 의 member들을 관리
	Vector<Member> usedMemberList = new Vector<Member>();         // Method 가 사용하고 있는 Class의 멤버들을 관리
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

class Member extends Information{                                             // Member class
	public Member(String name, String type, String access){
		super.setName(name);
		super.setAccess(access);
		super.setType(type);
	}
	public String toString(){
		return super.getName() + " " + super.getType() + " " + super.getAccess();
	}
}