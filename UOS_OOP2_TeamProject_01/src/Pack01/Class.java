package Pack01;

import java.util.Vector;           // ������ �迭�� ����ϱ� ���� Vector�� ����ߴ�.

class Information{                // Class, Method, Member class���� �������� �κ��� ��� Information class�� �������.
	private String name;
	private String type;
	private String access;
	private String In;             // �ڵ忡�� '{' ���� '}' ������ ���ڿ�
	private String Out;           // �ڵ忡�� '{' �� �պκ��� ���ڿ�
	
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
	public String getIn(){                        // �ڵ忡�� '{' ���� '}' ������ �κ��� ��ȯ
		return In;
	}
	public void setIn(String str){               // �ڵ忡�� '{' ���� '}' ������ �κ��� ����
		this.In = str;
	}
	public String getOut(){                      // �ڵ忡�� '{' ���� ������ ��ȯ
		return Out;
	}
	public void setOut(String str){             // �ڵ忡�� '{' ���� ������ ����
		this.Out = str;
	}
}

public class Class extends Information{                                         // Class �� �����ϱ� ���� class
	private Vector<Method> methodList = new Vector<Method>();        // Class�� method���� Vector�� �����Ѵ�.
	private Vector<Member> memberList = new Vector<Member>();      // Class�� field���� Vector�� �����Ѵ�.
	
	public Class(String name){
		super.setName(name);                                                      // �̸��� ���ڷ� �޾Ƽ� Class�� �̸��� ����
		super.setAccess("public");
		super.setType(null);
	}
	public void addMethod(Method method){                                  // Vector�� �޼ҵ带 �߰�
		methodList.addElement(method);
	}
	public void addMember(Member member){                                // Vector�� �ʵ带 �߰�
		memberList.addElement(member);
	}
	public Method getMethod(int index){                                       // i��° method�� ��ȯ
		return methodList.elementAt(index);
	}
	public Member getMember(int index){                                      // i��° field�� ��ȯ
		return memberList.elementAt(index);
	}
	public int getMethodListSize(){                                                // Method���� ���� ��ȯ
		return methodList.size();
	}
	public int getMemberListSize(){                                               // field���� ���� ��ȯ
		return memberList.size();
	}
}

class Method extends Information{                                             // Method class
	Vector<Member> memberList = new Vector<Member>();               // Method �� member���� ����
	Vector<Member> usedMemberList = new Vector<Member>();         // Method �� ����ϰ� �ִ� Class�� ������� ����
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