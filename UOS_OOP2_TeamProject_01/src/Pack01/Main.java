package Pack01;

public class Main {
	public static StringBuffer buffer = new StringBuffer();
	public static Class myClass = new Class(null);
	public static void main(String[] args) {
		
		
		myframe f = new myframe(myClass);
		new Parsing(buffer, myClass);
		
		for(int i = 0 ; i<myClass.getMethodListSize();i++){
			System.out.println(myClass.getMethod(i).toString());
			for(int j = 0 ; j<myClass.getMethod(i).getMemberListSize();j++)
				System.out.println(myClass.getMethod(i).getMember(j).toString());
			for(int j = 0 ; j<myClass.getMethod(i).getUsedMemberListSize();j++)
				System.out.println(myClass.getMethod(i).getUsedMember(j).toString());
		} 
		
	}

}