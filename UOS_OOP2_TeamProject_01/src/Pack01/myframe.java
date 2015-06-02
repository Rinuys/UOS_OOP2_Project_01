package Pack01;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

class myframe extends JFrame implements ActionListener{
	public static StringBuffer buffer = new StringBuffer();
	public static Class myClass = new Class(null);
	JTree tree;
	JLabel label;

	DefaultTreeModel model;

	DefaultMutableTreeNode root;
	DefaultMutableTreeNode[] methodchild;
	DefaultMutableTreeNode[] fieldchild;

	
	JTextField method_data;
	
	JScrollPane data_scrollPane;
	JScrollPane class_scrollPane;
	JScrollPane init_scrollPane;
	JScrollPane method_scrollPane;
	JPanel leftpanel;
	JPanel rightpanel;
	JPanel totalpanel;

	JMenuItem open;
	JMenuItem save;
	JMenuItem exit;

	JMenuBar menuBar;
	JMenu menu;

	JFileChooser fc;
	JTextArea usedmember;

	public myframe(Class myClass) {
		setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Class Viewer");
		/*-------------------------------- �ʱ� ���� ���̺�(��ȭ��) ���� -----------------------------------*/

		init_scrollPane = new JScrollPane();
		init_scrollPane.setSize(300, 150);
		/*-------------------------------- �ʱ� ���� ���̺�(��ȭ��) �� ------------------------------------*/

		
		/*----------------------------- Ʈ�� �ϴܺ� �ؽ�Ʈ�ʵ� ���� -----------------------------*/
		usedmember = new JTextArea();

		/*----------------------------- Ʈ�� �ϴܺ� �ؽ�Ʈ�ʵ� �� -----------------------------*/

		/*-----------------------------�޴��� ���� -----------------------*/
		menuBar = new JMenuBar();

		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_ALT);
		menuBar.add(menu);

		// �޴� �׸���� ����
		open = new JMenuItem("open", KeyEvent.VK_1);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		open.addActionListener(this);
		menu.add(open);

		save = new JMenuItem("save", KeyEvent.VK_2);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		menu.add(save);

		exit = new JMenuItem("exit", KeyEvent.VK_3);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		exit.addActionListener(this);
		menu.add(exit);
		/*-----------------------------�޴��� �� -----------------------*/

		/*----------------------------- ������ ���� ------------------------------------*/
		leftpanel = new JPanel();
		

		rightpanel = new JPanel();
		
		totalpanel = new JPanel();
		totalpanel.add(leftpanel); // �г� 1 ����(Ʈ��, �ؽ�Ʈ ����)
		totalpanel.add(rightpanel); // �г� 2 ����(������ ����â)

		add(totalpanel); // �����ӿ� �г� ����

		setJMenuBar(menuBar); // �޴��� ����

		setVisible(true);
		/*--------------------------------------- ������ �� --------------------------*/
	}

	/*----------------------------- ���� ���ñ� ���� ------------------------------------*/

	public void actionPerformed(ActionEvent e) {
		fc = new JFileChooser();
		
		if (e.getSource().equals((JMenuItem)exit)) {
			System.exit(0);
		}
		else if (e.getSource().equals((JMenuItem)open)) {
			File data;
			fc.showOpenDialog(open);
			data = fc.getSelectedFile();
			
			new ReadFileData(buffer, data);
			new Parsing(buffer, myClass);
			makeTree();
			selectClassTable();
			selectMethodTable();
			selectDataTable();
			LeftPanel();
			RightPanel();
			for(int i = 0 ; i<myClass.getMethodListSize();i++){
				System.out.println(myClass.getMethod(i).toString());
				for(int j = 0 ; j<myClass.getMethod(i).getMemberListSize();j++)
					System.out.println(myClass.getMethod(i).getMember(j).toString());
				for(int j = 0 ; j<myClass.getMethod(i).getUsedMemberListSize();j++)
					System.out.println(myClass.getMethod(i).getUsedMember(j).toString());
			}
			this.setSize(500,400);
			this.revalidate();
			this.setVisible(true);
		}
		else if (e.getSource().equals((JMenuItem)save)) {
			fc.showSaveDialog(save);
			String OutputString;
			OutputString = myClass.getClassOut()+myClass.getClassIn();
			for(int i = 0 ; i < myClass.getMethodListSize() ; i++){
				OutputString += (myClass.getMethod(i).getOut() + myClass.getMethod(i).getIn());
				OutputString += "\n";
			}
			new WriteFileData(fc.getSelectedFile(),OutputString);               // ���� buffer�ڸ��� ������ string�� �־����
			
		}
		// ������ ���塱��ư�� ���� �׼� �̺�Ʈ ó��

	}
	/*------------------------------------ ���� ���ñ� �� -----------------------------------*/

	public class TreeListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();
			if (node == null)
				return;
			if (e.getSource().equals((DefaultMutableTreeNode)root)) {
				addTotalPanel(class_scrollPane);
				RePaint();
			}
			if (e.getSource().equals(methodchild)) {
				addTotalPanel(method_scrollPane);
				RePaint();
			}
			if (e.getSource().equals(fieldchild)) {
				addTotalPanel(data_scrollPane);
				RePaint();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals((DefaultMutableTreeNode)root)) {
			addTotalPanel(class_scrollPane);
			RePaint();
		}
		if (e.getSource().equals(methodchild)) {
			addTotalPanel(method_scrollPane);
			RePaint();
		}
		if (e.getSource().equals(fieldchild)) {
			addTotalPanel(data_scrollPane);
			RePaint();
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	public static void main(String[] args) {
		
		
		myframe f = new myframe(myClass);
		
		
		
		
	}
	
	public void makeTree(){
		/*------------------------------ Ʈ�� ���� ���� -----------------------------------*/
		root = new DefaultMutableTreeNode(myClass.getName());

		methodchild = new DefaultMutableTreeNode[myClass.getMethodListSize()];
		for (int i = 0; i < myClass.getMethodListSize(); i++)
			methodchild[i] = new DefaultMutableTreeNode(myClass.getMethod(i)
					.getName());

		fieldchild = new DefaultMutableTreeNode[myClass.getMemberListSize()];
		for (int i = 0; i < myClass.getMemberListSize(); i++)
			fieldchild[i] = new DefaultMutableTreeNode(myClass.getMember(i)
					.getName());

		for (int i = 0; i < myClass.getMethodListSize(); i++)
			root.add(methodchild[i]);

		for (int i = 0; i < myClass.getMemberListSize(); i++)
			root.add(fieldchild[i]);

		tree = new JTree(root);
		tree.setVisibleRowCount(10);
		tree.addTreeSelectionListener(new TreeListener());

		/*-----------------------------------Ʈ�� ���� �� ------------------------------------------*/

	}
	
	public void selectClassTable(){
		/*-------------------------------- Ŭ���� ���� ���̺� ���� -----------------------------------*/

		String[] class_columnNames = { "Name", "Type", "Access" };

		Object[][] class_data = new Object[myClass.getMethodListSize()][3];
		for (int i = 0; i < myClass.getMethodListSize(); i++) {
			class_data[i][0] = new Object();
			class_data[i][1] = new Object();
			class_data[i][2] = new Object();
			class_data[i][0] = myClass.getMethod(i).getName();
			class_data[i][1] = myClass.getMethod(i).getType();
			class_data[i][2] = myClass.getMethod(i).getAccess();
		}

		final JTable class_table = new JTable(class_data, class_columnNames);

		class_table.setPreferredScrollableViewportSize(new Dimension(300, 150));
		class_table.setFillsViewportHeight(true);

		class_table.setAutoCreateRowSorter(true);

		// ��ũ�� ������ �����ϰ� ���̺��� �߰�
		class_scrollPane = new JScrollPane(class_table);

		/*-------------------------------- Ŭ���� ���� ���̺� �� ------------------------------------*/

	}
	public void selectMethodTable(){
		/*-------------------------------- �޼ҵ� ���� ���̺� ���� -----------------------------------*/
		method_data = new JTextField();
		method_scrollPane = new JScrollPane(method_data);
		method_scrollPane.setSize(300, 150);
		/*-------------------------------- �޼ҵ� ���� ���̺� �� ------------------------------------*/
	}
	public void selectDataTable(){
		/*-------------------------------- �ڷ� ���� ���̺� ���� -----------------------------------*/

		String[] data_columnNames = { "Name", "Method" };

		Object[][] data_data = { { "arr", "Enqueue(), Dequeue()" },
				{ "arr", "Enqueue(), Dequeue()" } };

		final JTable data_table = new JTable(data_data, data_columnNames);

		data_table.setPreferredScrollableViewportSize(new Dimension(300, 150));
		data_table.setFillsViewportHeight(true);

		data_table.setAutoCreateRowSorter(true);

		// ��ũ�� ������ �����ϰ� ���̺��� �߰�
		data_scrollPane = new JScrollPane(data_table);

		/*-------------------------------- �ڷ� ���� ���̺� �� ------------------------------------*/

	}
	public void LeftPanel(){
		leftpanel.setLayout(new BoxLayout(leftpanel, BoxLayout.Y_AXIS));

		JScrollPane queuetree = new JScrollPane(tree); // Ʈ�� ����
		leftpanel.add(queuetree); // ���� ��� Ʈ�� ����
		leftpanel.add(usedmember); // ���� �Ʒ� �ؽ�Ʈ ���� ����
	}
	public void RightPanel(){
		rightpanel.add(class_scrollPane); // ������ ������ ���� �κ� ����

	}
	public void RePaint(){
		this.revalidate();
		this.setVisible(true);
	}
	public void addTotalPanel(JScrollPane temp){
		rightpanel.add(temp);
		totalpanel.add(rightpanel);
		add(totalpanel);
	}
} 
