package Pack01;

import java.util.Vector;

class Information{
	private String name;
	private String type;
	private String access;
	private String In;
	private String Out;
	
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
	public String getIn(){
		return In;
	}
	public void setIn(String str){
		this.In = str;
	}
	public String getOut(){
		return Out;
	}
	public void setOut(String str){
		this.Out = str;
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
	public String getClassOut(){
		return super.getOut();
	}
	public void setClassOut(String str){
		super.setOut(str);
	}
	public String getClassIn(){
		return super.getIn();
	}
	public void setClassIn(String str){
		super.setIn(str);
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
	public String getMethodOut(){
		return super.getOut();
	}
	public void setMethodOut(String str){
		super.setOut(str);
	}
	public String getMethodIn(){
		return super.getIn();
	}
	public void setMethodIn(String str){
		super.setIn(str);
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