package Pack01;

import java.util.Vector;

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

public class Class extends Information{
	private Vector<Method> methodList = new Vector<Method>();
	private Vector<Member> memberList = new Vector<Member>();
	
	public Class(String name){
		super.setName(name);
		super.setAccess("public");
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