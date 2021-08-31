/*
������ �ֿ� ��� Ŭ������ ����
20150846 ��������а� ������(InputPanel,SortPanel)
20160528 ���������а� ������(Chart, PiePanel)
*/
import java.awt.*;
import javax.swing.*;

//�׵θ��� ���� �ֱ� ���� �ش� �κ��� �߰���. ������ ��α�: https://blog.naver.com/rice3320/140061211604
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {
	static AllStudent all_stu = new AllStudent(4);// all_stu: ��� �л��� ������ ���� ����
	JButton Pro = new JButton("����");// ���̾�α��� ���� ��ư
	JButton Stu = new JButton("�л�");// ���̾�α��� �л� ��ư
	InputPanel input;// ������ ������ �Է��ϴ� �г�
	JTabbedPane pane;// �л��� ������ ���� �г�
	JTextArea ta = new JTextArea(7, 20);// input �г��� JTextArea �κ�
	Color color = new Color(178, 235, 244); // ����

	//ChooseDialog: �������� �л������� �����ϴ� ���̾�α�
	class ChooseDialog extends JDialog {
		ChooseDialog() {
			setTitle("����");
			setLayout(new FlowLayout());
			add(new JLabel("�� �� �ϳ��� �������ּ���."));
			//������ư ������
			add(Pro);
			Pro.setBackground(Color.BLACK);
			Pro.setForeground(Color.WHITE);
			Pro.setFont(new Font("Hy����L", Font.BOLD, 17));
			//�л���ư ������
			add(Stu);
			Stu.setBackground(Color.BLACK);
			Stu.setForeground(Color.WHITE);
			Stu.setFont(new Font("Hy����L", Font.BOLD, 17));
			setSize(200, 100);
		}
	}

	// ������ �о���� Ŭ����1: ������ �л������� �Էµ� ����(stdinfo.txt)�� �л������� ������ �Էµ� ����(stu.txt)�� �������� ���
	class InputFile {
		String name, dept, tel; // �̸�, �а�, ��ȭ��ȣ
		int id, java, C, python; // �й�, �ڹ�, C���, ���̽�

		InputFile() {
			// ������ ��Ŵ�� �����ϱ� ���� all_stu�� �ִ� ������ ��� ����
			all_stu.removeAll();
			try {
				// stdinfo.txt: �л������� �Էµ� ����
				File file = new File("stdinfo.txt");
				Scanner s = new Scanner(file);
				// stu.txt: �л������� ������ �Էµ� ����
				File file2 = new File("stu.txt");
				Scanner s2 = new Scanner(file2);
				// �л������� �Էµ� ���
				while (s.hasNext()) {
					name = s.next();
					id = s.nextInt();
					dept = s.next();
					tel = s.next();
					Student ss = new Student(name, id, dept, tel);
					all_stu.appendStudent(ss);
				}
				// �л������� ������ �Էµ� ���
				while (s2.hasNext()) {
					name = s2.next();
					id = s2.nextInt();
					dept = s2.next();
					tel = s2.next();
					java = s2.nextInt();
					C = s2.nextInt();
					python = s2.nextInt();
					Student ss = new Student(name, id, dept, tel, java, C, python);
					all_stu.appendStudent(ss);
				}
				s.close();
				s2.close();
			} catch (IOException e1) {
				System.out.println("File not found.");
			}
		}
	}

	// ������ �о���� Ŭ����2: ��� �л��� ������ ���� �Է��� �Ϸ�� ����(data.txt)�� �������� ���
	class OutputFile {
		String name, dept, tel; // �̸�, �а�, ��ȭ��ȣ
		int id, java, C, python; // �й�, �ڹ�, C���, ���̽�

		OutputFile() {
			// ������ ��Ŵ�� �����ϱ� ���� all_stu�� �ִ� ������ ��� ����
			all_stu.removeAll();
			try {
				// ��� �л��� ������ ���� �Է��� �Ϸ�� ����
				File file = new File("data.txt");
				Scanner s = new Scanner(file);
				while (s.hasNext()) {
					name = s.next();
					id = s.nextInt();
					dept = s.next();
					tel = s.next();
					java = s.nextInt();
					C = s.nextInt();
					python = s.nextInt();
					Student ss = new Student(name, id, dept, tel, java, C, python);
					all_stu.appendStudent(ss);
				}
				s.close();
			} catch (IOException e1) {
				System.out.println("������ ��������� �ʾҽ��ϴ�.");
			}
		}
	}

	//Main: inputPanel(����), JTabbedPane(�л�)�� ���ɾ� ��ȯ�� �� �ִ� �г� 
	public Main() {
		setTitle("����ó�����α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		//���ȭ��
		ImagePanel c=new ImagePanel(new ImageIcon("background.jpg"));
		content.add(c);
		c.setLayout(null);
		// �������� �л������� �����ϴ� ���̾�α�
		ChooseDialog ch_dialog = new ChooseDialog();
		ch_dialog.setVisible(true);

		// �������� �л����� ��ȯ Ȥ�� �л����� ������ ��ȯ�ϰ��� �ϴ� ��� ���̾�α׸� �ٽ� ���� ��ư
		JButton jb = new JButton("����/�л� ����");
		jb.setLocation(700, 20);
		jb.setSize(150, 30);
		c.add(jb);
		jb.setBackground(Color.GRAY);
		jb.setForeground(Color.WHITE);
		jb.setFont(new Font("Hy����L", Font.BOLD, 15));
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ch_dialog.setVisible(true);
			}
		});

		// ���̾�α׿��� ������ư�� �����ϴ� ���
		Pro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ch_dialog.setVisible(false);
				// ��ó������ ������ư�� �������� input�г��� ��������� �ʾұ� ������ �������ִ� �κ�
				if (input == null) {
					int flag;
					//data.txt(������ �Է��� �Ϸ��� ��� ����� ����)�� �����ϴ� ��쿡�� flag�� 0����, data.txt�� �������� �ʴ� ��� flag=1�� �������
					//flag=0�̸� data.txt�� �д� OutputFile()�� ����� ���̰�, flag=1�̸�  stdinfo.txt, stu.txt�� �д� InputFile()��  ����
					
					try {
						File file = new File("data.txt");
						Scanner s = new Scanner(file);
						flag=0;
					} catch (IOException e1) {
						flag=1;
					}
					
					input = new InputPanel(flag);
					setVisible(true);
					input.setVisible(true);
					c.add(input);
					input.setBackground(Color.white);
					input.setSize(830, 480);
					input.setLocation(20, 60);
				}
				// �ٽ� ������ư�� ������ �̹� input�г��� ��������� ������ �ٽ� �����ֱ⸸ ��.
				else {
					input.setVisible(true);
				}
				// ������ư�� ������, �л��� ��� ���� pane�� ������ �ʰ� ��.
				if (pane != null) {
					pane.setVisible(false);
				}
			}
		});

		// ���̾�α׿��� �л���ư�� �����ϴ� ���
		Stu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ch_dialog.setVisible(false);
				// ��ó������ �л���ư�� �������� pane�� ��������� �ʾұ� ������ �������ִ� �κ�
				if (pane == null) {
					// data.txt ������ ���� ��� �л���ư���� �Ѿ�� ���� ���� ���� data.txt ������ �ִ��� �˻�����
					try {
						File file = new File("data.txt");
						Scanner s = new Scanner(file);
					} catch (IOException e1) {
						// ���� ������ ���ٸ�, ������ ���� ������ư�� ���� ������ �Է��϶�� ���̾�α׸� ���.
						JOptionPane.showMessageDialog(null, "���� ������ �������� �ʾҽ��ϴ�. ������ ���� ������ �Է����ּ���!", "���Ͽ���",
								JOptionPane.ERROR_MESSAGE);
						ch_dialog.setVisible(true);
						return;
					}
					pane = createTabbedPane();
					c.add(pane);
					pane.setBackground(new Color(209,178,255));
					pane.setFont(new Font("Hy����L", Font.BOLD, 20));
					pane.setSize(830, 500);
					pane.setLocation(20, 50);
					setVisible(true);
				}
				// �ٽ� �л���ư�� ������ �̹� pane�г��� ��������� ������ �ٽ� �����ֱ⸸ ��
				else {
					pane.setVisible(true);
				}
				// �л���ư�� ������, ������ ��� ���� input�� ������ �ʰ� ��
				if (input != null) {
					input.setVisible(false);
				}
			}
		});

		// input �гο����� JTextArea�� ���� Ű������
		// ���: +��ư�� ������ �۾� Ȯ��, -��ư�� ������ �۾� ���
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyChar();
				if (keyCode == '+') {
					Font f = ta.getFont();
					int size = f.getSize();
					ta.setFont(new Font("����", Font.PLAIN, size + 5));
				} 
				else if (keyCode == '-') {
					Font f = ta.getFont();
					int size = f.getSize();
					if (size <= 5)
						return;
					ta.setFont(new Font("����", Font.PLAIN, size - 5));
				}
			}
		});
		c.setFocusable(true);
		c.requestFocus();
		// Ű�� focus�� �Ҿ���� ��� ���콺�� Ŭ���ϸ� �ٽ� ��Ŀ���� ã����
		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component com = (Component) e.getSource();
				com.setFocusable(true);
				com.requestFocus();
			}
		});

		setSize(900, 610);
		setVisible(false);
	}

	//�л��κп��� ���Ȯ��, ���뵵ǥ, ������Ʈ�� �����ϴ� JTabbedPane
	private JTabbedPane createTabbedPane() {
		new OutputFile();//������ �о��
		JTabbedPane pane = new JTabbedPane();
		pane.setBackground(Color.BLACK);
		pane.addTab("���Ȯ��", new SortPanel());
		pane.addTab("���뵵ǥ", new Chart());
		pane.addTab("������Ʈ", new PiePanel());
		return pane;
	}

	//InputPanel: ������ �л��� ������ �Է��ϴ� �г�
	class InputPanel extends JPanel {
		JRadioButton jrb_show[] = new JRadioButton[4];
		InputPanel(int flag) {
			setLayout(null);
			setBackground(Color.WHITE);
			TitledBorder blacktb = new TitledBorder(new LineBorder(Color.BLACK));
			setBorder(blacktb);
			// ����
			JLabel title = new JLabel("���� �Է�");
			add(title);
			title.setSize(280, 50);
			title.setLocation(20, 40);
			title.setHorizontalAlignment(JLabel.CENTER);
			title.setFont(new Font("Hy����L", Font.BOLD, 40));

			// ��ư
			JButton jb[] = new JButton[4];
			jb[0] = new JButton("���");
			jb[1] = new JButton("�߰�");
			jb[2] = new JButton("�Ϸ�");
			jb[3] = new JButton("�˻�");
			for (int i = 0; i < jb.length; i++) {
				jb[i].setBackground(Color.BLACK);
				jb[i].setForeground(Color.WHITE);
				jb[i].setFont(new Font("Hy����L", Font.BOLD, 17));
			}

			// jp1�г�: �й�(JLabel la), �й��� �Է��ϴ� �ؽ�Ʈ(JTextArea tt), �˻� ��ư(jb[4])�� ����
			JPanel jp1 = new JPanel(new GridLayout(1, 3, 5, 0));
			jp1.setBackground(Color.white);
			jp1.setSize(280, 40);
			jp1.setLocation(5, 100);
			add(jp1);
			JLabel la = new JLabel("�й�");
			la.setHorizontalAlignment(JLabel.CENTER);
			la.setFont(new Font("Gothic", Font.BOLD, 15));
			jp1.add(la);
			JTextField tt = new JTextField(8);
			jp1.add(tt);
			jp1.add(jb[3]);

			// jp2�г�: �˻� ��ư�� ��������, ä���� �й�, �̸�, ������ ������ ���̺�(jl)�� �ؽ�Ʈ(tf)�� ����
			JPanel jp2 = new JPanel();
			jp2.setLayout(new GridLayout(7, 2, 5, 2));
			jp2.setBackground(Color.white);
			jp2.setSize(235, 200);
			jp2.setLocation(40, 150);
			add(jp2);
			TitledBorder graytb = new TitledBorder(new LineBorder(Color.LIGHT_GRAY));//�� ���̺��� ������ ��
			JLabel[] jl = new JLabel[11];
			jl[0] = new JLabel("�й�");
			jl[1] = new JLabel("");
			jl[2] = new JLabel("�̸�");
			jl[3] = new JLabel("");
			for (int i = 0; i < 4; i++) {
				jp2.add(jl[i]);
				jl[i].setBorder(graytb);
			}
			JTextField[] tf = new JTextField[3];
			jl[4] = new JLabel("�ڹ�");
			jl[5] = new JLabel("C���");
			jl[6] = new JLabel("���̽�");
			for (int i = 0; i < tf.length; i++) {
				jp2.add(jl[4 + i]);
				jl[4+i].setBorder(graytb);
				tf[i] = new JTextField(10);
				jp2.add(tf[i]);
			}
			jl[7] = new JLabel("����");
			jl[8] = new JLabel("");
			jl[9] = new JLabel("���");
			jl[10] = new JLabel("");
			for (int i = 7; i < jl.length; i++) {
				jp2.add(jl[i]);
				jl[i].setBorder(graytb);
			}
			for (int i = 0; i < jl.length; i++) {
				jl[i].setHorizontalAlignment(JLabel.CENTER);
			}

			// jp3�г�: ���, �߰�, �Ϸ� ��ư���� ����
			JPanel jp3 = new JPanel(new GridLayout(1, 3, 5, 0));
			add(jp3);
			jp3.setBackground(Color.white);
			jp3.setLocation(30, 360);
			jp3.setSize(260, 40);
			for (int i = 0; i < 3; i++) {
				jp3.add(jb[i]);
			}

			// jp_ta�г�: JTextArea�� ����
			JPanel jp_ta = new JPanel(new BorderLayout());
			jp_ta.setSize(500, 300);
			jp_ta.setLocation(310, 100);
			add(jp_ta);
			jp_ta.add(new JScrollPane(ta), BorderLayout.CENTER);
			
			//jp4�г�:�� ���񺰷� �����ϴ� RadioButton���� ����
			JPanel jp4 = new JPanel(new GridLayout(1, 4, 5, 0));
			add(jp4);
			jp4.setBackground(Color.white);
			jp4.setLocation(510, 410);
			jp4.setSize(300, 40);
			ButtonGroup g = new ButtonGroup();
			jrb_show[0] = new JRadioButton("�ڹ�");
			jrb_show[1] = new JRadioButton("C���");
			jrb_show[2] = new JRadioButton("���̽�");
			jrb_show[3] = new JRadioButton("����");
			for (int i = 0; i < jrb_show.length; i++) {
				g.add(jrb_show[i]);
				jp4.add(jrb_show[i]);
				jrb_show[i].setBackground(Color.WHITE);
				jrb_show[i].setFont(new Font("Hy����L", Font.BOLD, 14));
			}

			// ImagePanel(�̹����� ������ ������ ��ä��� ���[�ڿ� ����]) �г�: ���� data.txt�� �ְų�, �Ϸ��ư�� ������ ��� ��Ÿ���� �г�
			ImageIcon icon = new ImageIcon("finish.png");
			ImagePanel im_panel = new ImagePanel(icon);
			im_panel.setSize(200, 200);
			im_panel.setLocation(80, 150);
			add(im_panel);
			im_panel.setVisible(false);

			// flag�� 0�� ���� ������ �Է��� �Ϸ� �Ǿ��ٴ� �ǹ��̹Ƿ�, ��� ����� ������
			if (flag==0) {
				new OutputFile();
				im_panel.setVisible(true);//�̹����� ��Ÿ��.
				jp2.setVisible(false);
				jb[0].setEnabled(false);//��� ��� ����
				jb[1].setEnabled(false);//�߰� ��� ����
				jb[2].setEnabled(false);//�Ϸ��� ����
				jb[3].setEnabled(false);//�˻���� ����
			}
			//flag�� 0�� �ƴ� ��쿡�� ������ �Է��� ���Ҵٴ� �ǹ�
			else {
				new InputFile();
			}
			
			Iterator it = all_stu.iterator();
			while (it.hasNext()) {
				Student ss = (Student) it.next();
				// ��ü �л��� ������ ������
				ta.append("�̸�: " + ss.getName() + ", �й�: " + ss.getStd_id() + ", �ڹ�: " + ss.getJava() + ", ����: "
						+ ss.getJava_grade() + ", C���: " + ss.getC() + ", ����: " + ss.getC_grade() + ", ���̽�: "
						+ ss.getPython() + ", ����: " + ss.getPython_grade() + ", ����: " + ss.getSum() + ", ���: "
						+ ss.getAvg() + "\n");
			}
			ta.setFont(new Font("����", Font.PLAIN, 10));

			// �˻���ư: ����ڰ� �ش��ϴ� �й��� �Է��ϸ�, �ش� �й��� �̸�, �й�, �� ���� ������ ������
			jb[3].addActionListener(new ActionListener() {
				int length = all_stu.getLength();
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						// �й��� ���� ���
						if (Integer.parseInt(tt.getText()) > length || Integer.parseInt(tt.getText()) < 0) {
							tt.setText("");
							JOptionPane.showMessageDialog(null, "�ش��ϴ� �й��� �����ϴ�.", "�����̻�", JOptionPane.ERROR_MESSAGE);
							return;
						}
						Iterator it = all_stu.iterator();
						// �ش� �й��� �̸�, �й�, �� ���� ������ ���� �ڵ�
						while (it.hasNext()) {
							Student ss = (Student) it.next();
							if (ss.getStd_id() == Integer.parseInt(tt.getText())) {// ���� tt(����ڰ� �Է��ϴ� �κ�)�� ã�´ٸ�
								jl[1].setText(ss.getName());// �ش� �л��� �̸�,
								jl[3].setText(Integer.toString(ss.getStd_id()));// �ش� �л��� �й�,
								if (ss.getJava() > 0) {// �ش� �л��� �ڹ� ������ �ִ� ���
									tf[0].setText(Integer.toString(ss.getJava()));// �ش� �л��� �ڹ� �������� ä����
								}
								else {//�ش� �л��� �ڹټ����� ���� ���
									tf[0].setText("");
								}
								if (ss.getC() > 0) {// �ش� �л��� C��� ������ �ִ� ���
									tf[1].setText(Integer.toString(ss.getC()));// �ش� �л��� C��� �������� ä����
								}
								else {//�ش� �л��� C������ ���� ���
									tf[1].setText("");
								}
								if (ss.getPython() > 0) {// �ش� �л��� ���̽� ������ �ִ� ���
									tf[2].setText(Integer.toString(ss.getPython()));// �ش� �л��� ���̽� �������� ä����
								}
								else {//�ش� �л��� ���̽� ������ ���� ���
									tf[2].setText("");
								}
							}
						}			
						jl[8].setText("");
						jl[10].setText("");
					} catch (NumberFormatException e1) {
						//�й��� ���� ���
						JOptionPane.showMessageDialog(null, "�й��� �Է����� �ʾҽ��ϴ�.", "�Է¿���", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			// ����ư: �Է��� �� ���� ������ ����, ����� �˷���
			jb[0].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int[] subject = new int[3];
					Iterator it = all_stu.iterator();
					try {
						subject[0] = Integer.parseInt(tf[0].getText());// �ڹټ���
						subject[1] = Integer.parseInt(tf[1].getText());// C��� ����
						subject[2] = Integer.parseInt(tf[2].getText());// ���̽� ����

						// ������ 0�� 100������ ������ �Է����� ���� ���
						for (int i = 0; i < 3; i++) {
							if (subject[i] < 0 || subject[i] > 100) {
								jl[8].setText("");// ����
								jl[10].setText("");// ���
								JOptionPane.showMessageDialog(null, "0���� 100������ ������ �Է����ּ���.", "�Է¿���",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						// �Է��� ����� �Ѱ��
						int sum = subject[0] + subject[1] + subject[2];
						double avg = sum / 3;
						jl[8].setText(Integer.toString(sum));// ����
						jl[10].setText(Double.toString(avg));// ���
					} catch (NumberFormatException e1) {
						// ������ �Է��� ���� ���� ���
						JOptionPane.showMessageDialog(null, "�Է����� ���� ������ �ֽ��ϴ�.", "�Է¿���", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			// �߰���ư: �ش� �л��� ������ ����
			jb[1].addActionListener(new ActionListener() {
				int[] subject = new int[3];

				@Override
				public void actionPerformed(ActionEvent e) {
					Iterator it = all_stu.iterator();
					Iterator it2 = all_stu.iterator();
					int length = all_stu.getLength();
					try {
						// �й��� �ùٸ��� ���� ���
						if (Integer.parseInt(tt.getText()) > length || Integer.parseInt(tt.getText()) < 0) {
							JOptionPane.showMessageDialog(null, "�ش��ϴ� �й��� �����ϴ�.", "�����̻�", JOptionPane.ERROR_MESSAGE);
							return;
						}
						subject[0] = Integer.parseInt(tf[0].getText());//����ڰ� �Է��� �ڹ� ������ ������
						subject[1] = Integer.parseInt(tf[1].getText());//����ڰ� �Է��� C��� ������ ������
						subject[2] = Integer.parseInt(tf[2].getText());//����ڰ� �Է��� ���̽� ������ ������
						while (it.hasNext()) {
							// ���� ������ 0�̸� 100�ʰ��� �Էµ� ���
							for (int i = 0; i < 3; i++) {
								if (subject[i] < 0 || subject[i] > 100) {
									JOptionPane.showMessageDialog(null, "0�̻� 100���Ϸ� �Է����ּ���.", "�����̻�",
											JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							// �л��� ���� �߰�
							Student ss = (Student) it.next();
							if (ss.getStd_id() == Integer.parseInt(tt.getText())) {
								ss.setJava(subject[0]);//�ڹټ���
								ss.setJava_grade(subject[0]);//�ڹ�����
								ss.setC(subject[1]);//C����
								ss.setC_grade(subject[1]);//C�������
								ss.setPython(subject[2]);//���̽㼺��
								ss.setPython_grade(subject[2]);//���̽�����
								ss.setSum(subject[0], subject[1], subject[2]);//����
								ss.setAvg(subject[0], subject[1], subject[2]);//���
							}
						}
						//����ڰ� �Է��ϴ� �κ��� ������
						tt.setText("");
						// ta�� �ִ� �����͸� �����ְ�, �Էµ� ���ο� ������ �޾� �ٽ� ä��
						ta.setText("");
						while (it2.hasNext()) {
							Student ss = (Student) it2.next();
							ta.append("�̸�: " + ss.getName() + ", �й�: " + ss.getStd_id() + ", �ڹ�: " + ss.getJava()
									+ ", ����: " + ss.getJava_grade() + ", C���: " + ss.getC() + ", ����: " + ss.getC_grade()
									+ ", ���̽�: " + ss.getPython() + ", ����: " + ss.getPython_grade() + ", ����: "
									+ ss.getSum() + ", ���: " + ss.getAvg() + "\n");
						}
						ta.setFont(new Font("����", Font.PLAIN, 10));

						// ���̺� ���� �й�, �̸�, �ڹټ���, C���, ���̽� ������ ������
						for (int i = 0; i < tf.length; i++) {
							jl[1].setText("");
							jl[3].setText("");
							jl[8].setText("");
							jl[10].setText("");
							tf[i].setText("");
						}
					} catch (NumberFormatException e1) {
						// �Է����� ���� ������ �ִ� ���
						JOptionPane.showMessageDialog(null, "�Է����� ���� ������ �ֽ��ϴ�.", "�Է¿���", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			// �Ϸ��ư: ������ �Է��� �� �Ϸ����� ���, ���� �л��� �� �� �ִ� data.txt ���ϰ� ������ ������ grade.dat�� �����.
			jb[2].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Iterator it = all_stu.iterator();
					Iterator it2 = all_stu.iterator();
					try {
						while (it.hasNext()) {
							// �Է����� ���� ������ �����ϴ� ���
							Student ss = (Student) it.next();
							if (ss.getJava() == -1 || ss.getC() == -1 || ss.getPython() == -1) {
								JOptionPane.showMessageDialog(null, "�Է����� ���� ������ �ֽ��ϴ�.", "�Է¿���",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						PrintWriter pw = new PrintWriter("data.txt");
						PrintWriter pw2 = new PrintWriter("grade.dat");

						while (it2.hasNext()) {
							// �Է����� ���� ������ �����ϴ� ���
							Student ss = (Student) it2.next();
							
							// data.txt�� �л��� �� ����
							pw.println(ss.getName() + " " + ss.getStd_id() + " " + ss.getDept() + " " + ss.getTel()
									+ " " + ss.getJava() + " " + ss.getC() + " " + ss.getPython());
							// grade.dat�� ������ ����
							pw2.println("�̸�: " + ss.getName() + ", �й�: " + ss.getStd_id() + ", �а�: " + ss.getDept()
									+ ", ��ȭ��ȣ: " + ss.getTel() + ", �ڹټ���: " + ss.getJava() + ", �ڹ�����: "
									+ ss.getJava_grade() + ", C����: " + ss.getC() + ", C�������: " + ss.getC_grade()
									+ ", ���̽㼺��: " + ss.getPython() + ", ���̽�����: " + ss.getPython_grade() + ", ����: "
									+ ss.getSum() + ", ���: " + ss.getAvg());
						}
						pw.close();
						pw2.close();
					} catch (IOException e2) {
						System.out.println("File not Put.");
					}

					im_panel.setVisible(true);//�̹��� ��Ÿ��
					jp2.setVisible(false);//�Է��ϴ� ������ �����
					jb[0].setEnabled(false);//��� ��ư �����
					jb[1].setEnabled(false);//�߰� ��ư �����
					jb[2].setEnabled(false);//�Ϸ� ��ư �����
					jb[3].setEnabled(false);//�˻� ��ư �����
				}
			});
			// �ش� ������ ������ư�� ������ ������ ������ ��
			for (int i = 0; i < jrb_show.length; i++) {
				jrb_show[i].addItemListener(new RadioItemListener());
			}
		}

		// ���ľ˰���: �ش��ϴ� ������ ���� ��ư�� ������ �ش� ������ ���� ���� ������� ����
		class RadioItemListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Iterator it = all_stu.iterator();
				int length = all_stu.getLength();
				try {
					//ta�� �����.
					ta.setText("");
					//array �迭
					Student[] array = new Student[length];
					int index = 0;
					//all_stu�� ������ array �迭�� �־���.
					while (it.hasNext()) {
						Student ss = (Student) it.next();
						array[index] = ss;
						index++;
					}

					int i, j;
					for (i = 0; i < length; i++) {
						for (j = i; j < length; j++) {
							// �ڹ� ����
							if (jrb_show[0].isSelected()) {
								if (array[i].getJava() < array[j].getJava()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
							//C��� ����
							if (jrb_show[1].isSelected()) {
								if (array[i].getC() < array[j].getC()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
							//���̽� ����
							if (jrb_show[2].isSelected()) {
								if (array[i].getPython() < array[j].getPython()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
							//��ü ����
							if (jrb_show[3].isSelected()) {
								if (array[i].getSum() < array[j].getSum()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
						}
					}
					//������ �������� �ٽ� ta�� ä��
					for (int k = 0; k < length; k++) {
						ta.append("�̸�: " + array[k].getName() + ", �й�: " + array[k].getStd_id() + ", �ڹ�: "
								+ array[k].getJava() + ", ����: " + array[k].getJava_grade() + ", C���: " + array[k].getC()
								+ ", ����: " + array[k].getC_grade() + ", ���̽�: " + array[k].getPython() + ", ����: "
								+ array[k].getPython_grade() + ", ����: " + array[k].getSum() + ", ���: "
								+ array[k].getAvg() + "\n");
					}
					ta.setFont(new Font("����", Font.PLAIN, 10));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "�Է����� ���� ������ �ֽ��ϴ�.", "�Է¿���", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	//SortPanel: �л��� �� ���� �ڽ��� ����� ��ȸ�ϰ�, ���б� ȹ���� Ȯ���ϴ� �г�
	class SortPanel extends JPanel {
		SortPanel() {
			setLayout(null);
			setBackground(Color.WHITE);
			
			//jp1�г�: �й�, ����ڰ� �Է��ϴ� �ؽ���, �˻����� ���� 
			JPanel jp1 = new JPanel();
			jp1.setBackground(Color.WHITE);
			jp1.setLayout(new GridLayout(1, 3, 5, 0));
			jp1.setSize(300, 40);
			jp1.setLocation(0, 30);
			add(jp1);
			JLabel la = new JLabel("�й�");
			la.setHorizontalAlignment(JLabel.CENTER);
			jp1.add(la);
			JTextField tt = new JTextField(8);
			jp1.add(tt);
			JButton search = new JButton("�˻�");
			jp1.add(search);
			search.setBackground(Color.black);
			search.setForeground(Color.WHITE);
			search.setFont(new Font("Hy����L", Font.BOLD, 17));

			//jp2�г�: ������ ����� �����ִ� label�� ����
			JPanel jp2 = new JPanel();
			jp2.setBackground(Color.white);
			jp2.setSize(280, 200);
			jp2.setLocation(30, 100);
			add(jp2);
			jp2.setLayout(new GridLayout(5, 4, 5, 5));
			String title[]= {"", "����", "���", "����"};
			String name[] = { "�ڹ�", "C���", "���̽�", "��ü" };
			JLabel jl_title[]=new JLabel[4];//����
			JLabel subject[] = new JLabel[4];//����
			JLabel subject_grade[] = new JLabel[4];//����
			JLabel subject_score[]=new JLabel[4];//����
			JLabel subject_rank[]=new JLabel[4];//���
			TitledBorder graytb = new TitledBorder(new LineBorder(Color.LIGHT_GRAY));//�� ���̺��� ������ ��
			for (int i=0;i<subject.length;i++) {
				jl_title[i] = new JLabel(title[i]);
				jl_title[i].setHorizontalAlignment(JLabel.CENTER);
				jl_title[i].setBorder(graytb);
				jp2.add(jl_title[i]);
			}
			
			for (int i = 0; i < subject.length; i++) {	
				subject[i] = new JLabel(name[i]);
				subject[i].setHorizontalAlignment(JLabel.CENTER);
				subject[i].setBorder(graytb);
				jp2.add(subject[i]);
				subject_score[i] = new JLabel();
				subject_score[i].setHorizontalAlignment(JLabel.CENTER);
				subject_score[i].setBorder(graytb);
				jp2.add(subject_score[i]);
				subject_rank[i] = new JLabel();
				subject_rank[i].setHorizontalAlignment(JLabel.CENTER);
				subject_rank[i].setBorder(graytb);
				jp2.add(subject_rank[i]);
				subject_grade[i] = new JLabel();
				subject_grade[i].setHorizontalAlignment(JLabel.CENTER);
				subject_grade[i].setBorder(graytb);
				jp2.add(subject_grade[i]);
			}

			//�л����� ������ ���� �̾Ƴ�
			Vector<Integer> java = new Vector<Integer>();
			Vector<Integer> C = new Vector<Integer>();
			Vector<Integer> python = new Vector<Integer>();
			Vector<Integer> all = new Vector<Integer>();
			Iterator it = all_stu.iterator();
			while (it.hasNext()) {
				Student ss = (Student) it.next();
				java.add(ss.getJava());
				C.add(ss.getC());
				python.add(ss.getPython());
				all.add(ss.getSum());
			}
			//����
			Collections.sort(java);
			Collections.reverse(java);
			Collections.sort(C);
			Collections.reverse(C);
			Collections.sort(python);
			Collections.reverse(python);
			Collections.sort(all);
			Collections.reverse(all);

			//�˻���ư: �˻��� ������ �� ������ ����� ������.
			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Iterator it = all_stu.iterator();
						while (it.hasNext()) {
							Student ss = (Student) it.next();
							if (ss.getStd_id() == Integer.parseInt(tt.getText())) {
								//����
								subject_score[0].setText(Integer.toString(ss.getJava()));
								subject_score[1].setText(Integer.toString(ss.getC()));
								subject_score[2].setText(Integer.toString(ss.getPython()));
								subject_score[3].setText(Integer.toString(ss.getSum()));
								
								//����
								subject_grade[0].setText(Character.toString(ss.getJava_grade()));
								subject_grade[1].setText(Character.toString(ss.getC_grade()));
								subject_grade[2].setText(Character.toString(ss.getPython_grade()));
								subject_grade[3].setText("");
								
								int java_grade = ss.getJava();
								int C_grade = ss.getC();
								int python_grade = ss.getPython();
								int sum_grade = ss.getSum();
								//�ڹ� ����
								for (int i = 0; i < java.size(); i++) {
									//�ش� �й��� ������ ���� ������ ���ð�� �� �ε����� ������
									if (java_grade == java.get(i)) {
										subject_rank[0].setText(Integer.toString(i + 1));
										break;
									}
								}
								//C��� ����
								for (int i = 0; i < java.size(); i++) {
									if (C_grade == C.get(i)) {
										subject_rank[1].setText(Integer.toString(i + 1));
										break;
									}
								}
								//���̽� ����
								for (int i = 0; i < java.size(); i++) {
									if (python_grade == python.get(i)) {
										subject_rank[2].setText(Integer.toString(i + 1));
										break;
									}
								}
								//��ü ����
								for (int i = 0; i < java.size(); i++) {
									if (sum_grade == all.get(i)) {
										subject_rank[3].setText(Integer.toString(i + 1));
										break;
									}
								}
							}
						}
						tt.setText("");
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "���� �й��Դϴ�.", "���Ͽ���", JOptionPane.ERROR_MESSAGE);//�й��� ���� ���
					}
				}
			});
			//���бݹ�ư: ��ü ������ �������� 1���� ����, 2���� ����, 30%�̳��� ������б� ����, �������� ���б� ������ ���� ����
			JButton getMoney = new JButton("���б�");
			getMoney.setBackground(Color.black);
			getMoney.setForeground(Color.WHITE);
			getMoney.setFont(new Font("Hy����L", Font.BOLD, 17));
			getMoney.setSize(100, 40);
			getMoney.setLocation(200, 320);
			add(getMoney);
			
			//�ƹ� �й��� �Է����� ���� ���(�� ó��ȭ��) ��Ÿ�� �̹���
			ImageIcon waiting = new ImageIcon("waiting.png");
			ImagePanel ip_waiting = new ImagePanel(waiting);//�̹����� ������ ������ �°� ä��� �г�(�ڿ� ����)
			ip_waiting.setSize(300, 300);
			ip_waiting.setLocation(400, 50);
			add(ip_waiting);
			ip_waiting.setVisible(true);
			
			//���б� ȹ�濡 ������ ��� ��Ÿ�� ȭ�� 
			ImageIcon success = new ImageIcon("success.jpg");
			ImageIcon success2 = new ImageIcon("success2.jpg");
			FickeringImg fk_success = new FickeringImg(success, success2, 300);//������ ���(�ڿ� ����)
			fk_success.setSize(300, 300);
			fk_success.setLocation(400, 50);
			add(fk_success);
			fk_success.setVisible(false);

			//���б� ȹ�濡 ������ ��� ��Ÿ�� ȭ��
			ImageIcon fail = new ImageIcon("fail.png");
			ImageIcon fail2 = new ImageIcon("fail2.png");
			FickeringImg fk_fail = new FickeringImg(fail, fail2, 300);
			fk_fail.setSize(300, 300);
			fk_fail.setLocation(400, 50);
			add(fk_fail);
			fk_fail.setVisible(false);
			
			//jl_result: ���б� ����� �˷���
			JLabel jl_result = new JLabel("���б� ��ư�� �����ּ���!");
			jl_result.setSize(450, 50);
			jl_result.setLocation(400, 350);
			add(jl_result);
			jl_result.setFont(new Font("Hy����L", Font.BOLD, 17));

			//last: ������б��� ���޵Ǵ� �л��� ��
			int last = (int) (all.size() * 0.3);
			getMoney.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Iterator it = all_stu.iterator();
						while (it.hasNext()) {
							Student ss = (Student) it.next();
							//����
							if (Integer.parseInt(subject_rank[3].getText()) == 1) {
								jl_result.setText("�����մϴ�. �����Դϴ�.");
								ip_waiting.setVisible(false);
								fk_fail.setVisible(false);
								fk_success.setVisible(true);
							}
							//����
							else if (Integer.parseInt(subject_rank[3].getText()) == 2) {
								jl_result.setText("�����մϴ�. �����Դϴ�.");
								ip_waiting.setVisible(false);
								fk_fail.setVisible(false);
								fk_success.setVisible(true);
							}
							//������б�
							else if (Integer.parseInt(subject_rank[3].getText()) <= last) {
								jl_result.setText("�����մϴ�. ��� ���б��Դϴ�.");
								ip_waiting.setVisible(false);
								fk_fail.setVisible(false);
								fk_success.setVisible(true);
							} 
							//ȹ������ ���ϴ� ���
							else {
								jl_result.setText("���� ��ȸ��....");
								ip_waiting.setVisible(false);
								fk_success.setVisible(false);
								fk_fail.setVisible(true);
							}
						}
					}catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "������ �й��� �Է��� ���ϼ̰ų� �˻���ư�� ������ �ʽ��ϴ�.", "�Է¿���",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
	}
	
	//ImagePanel: �̹����������� �޾Ƽ� �ش� �̹����� �г��� ũ�⿡ ���߾� �׸��� �ϴ� �г�
	class ImagePanel extends JPanel {
		Image img;
		ImagePanel() {}
		
		ImagePanel(ImageIcon icon) {
			img = icon.getImage();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}

	//FickeringImg: ImagePanel�� ��ӹ޾� �ΰ��� �̹����� ������ ���̴� ������ ���
	class FickeringImg extends ImagePanel implements Runnable {
		long delay;
		ImageIcon icon;
		ImageIcon icon2;
		boolean flag = false;

		FickeringImg(ImageIcon icon, ImageIcon icon2, long delay) {
			super();
			this.delay = delay;
			this.icon = icon;
			this.icon2 = icon2;
			setOpaque(true);

			Thread th = new Thread(this);
			th.start();
		}

		@Override
		public void run() {
			int n = 0;
			while (true) {
				//n=0�̸� icon��, n=1�̸� icon2�� ������
				if (n == 0) {
					repaint();
					img = icon.getImage();
				}
				else {
					repaint();
					img = icon2.getImage();
				}

				if (n == 0)
					n = 1;
				else
					n = 0;
				try {
					Thread.sleep(delay);
					if (flag = true) {
						repaint();
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	// Chart: ���뵵ǥ�� �޾Ƽ� �׸��� ���� ��հ� �ְ���, �������� �˷��ִ� �г�
	class Chart extends JPanel {
		JCheckBox[] subject = new JCheckBox[3];//üũ�ڽ�
		JLabel[] java_score=new JLabel[3];//�ڹ��� ���, �ְ���, ������
		JLabel[] C_score=new JLabel[3];//C����� ���, �ְ���, ������
		JLabel[] python_score=new JLabel[3];//���̽��� ���, �ְ���, ������
		Chart() {
			setLayout(new BorderLayout());
			setBackground(Color.WHITE);
			//jp1: üũ�ڽ�(�ڹ�, C���, ���̽�)���� ����
			JPanel jp1=new JPanel();
			jp1.setBackground(Color.WHITE);
			add(jp1, BorderLayout.SOUTH);
			String[] classes = { "�ڹ�", "C���", "���̽�" };
			for (int i=0;i<classes.length;i++) {
				subject[i]=new JCheckBox(classes[i]);
				subject[i].setBackground(Color.WHITE);
				subject[i].setSelected(true);
				subject[i].addItemListener(new CheckBoxItemListener());
				subject[i].setFont(new Font("HY�߰��", Font.PLAIN, 15));
				jp1.add(subject[i]);
			}
			
			//cp�г�: ���뵵ǥ�� ����	
			ChartPanel cp = new ChartPanel();
			cp.setLayout(null);
			add(cp, BorderLayout.CENTER);
			cp.setSize(600,400);
			cp.setLocation(0,0);
			
			//�ڹ��� ���, �ְ���, ������
			java_score[0]=new JLabel(Integer.toString(cp.javer));
			java_score[1]=new JLabel(Integer.toString(cp.jmax));
			java_score[2]=new JLabel(Integer.toString(cp.jmin));
			
			
			//C����� ���, �ְ���, ������
			C_score[0]=new JLabel(Integer.toString(cp.caver));
			C_score[1]=new JLabel(Integer.toString(cp.cmax));
			C_score[2]=new JLabel(Integer.toString(cp.cmin));
			
			//���̽��� ���, �ְ���, ������
			python_score[0]=new JLabel(Integer.toString(cp.javer));
			python_score[1]=new JLabel(Integer.toString(cp.jmax));
			python_score[2]=new JLabel(Integer.toString(cp.jmin));
		
			//�ڹ��� ��ġ
			int[] java_y=new int[3];		
			java_y[0]=cp.javer;
			java_y[1]=cp.jmax;
			java_y[2]=cp.jmin;
			
			//C����� ��ġ
			int[] C_y=new int[3];
			C_y[0]=cp.caver;
			C_y[1]=cp.cmax;
			C_y[2]=cp.cmin;
			
			//���̽��� ��ġ
			int[] python_y=new int[3];
			python_y[0]=cp.pyaver;
			python_y[1]=cp.pymax;
			python_y[2]=cp.pymin;
			
			//�ڹ��� ���, �ְ���, �������� ��ġ ����
			for (int i=0;i<3;i++) {
				java_score[i].setSize(50,20);
				java_score[i].setLocation(150+i*40,380-java_y[i]*3);
				cp.add(java_score[i]);
			}
			//C����� ���, �ְ���, �������� ��ġ ����
			for(int i=0;i<3;i++) {
				C_score[i].setSize(50,20);
				C_score[i].setLocation(340+i*40,380-C_y[i]*3);
				cp.add(C_score[i]);
			}
			
			//���̽����� ���, �ְ���, �������� ��ġ ����
			for(int i=0;i<3;i++) {
				python_score[i].setSize(50,20);
				python_score[i].setLocation(530+i*40,380-python_y[i]*3);
				cp.add(python_score[i]);
			}
			
		}
		
		//���뵵ǥ�� ��ġ ���̺�
		class CheckBoxItemListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					//�ڹ� ��ġ ǥ��
					if (e.getItem()==subject[0]) {
						for (int i=0;i<3;i++) {
							java_score[i].setVisible(true);
						}
					}
					//C��� ��ġ ǥ��
					else if (e.getItem()==subject[1]) {
						for (int i=0;i<3;i++) {
							C_score[i].setVisible(true);
						}
					}
					//���̽� ��ġ ǥ��
					else {
						for (int i=0;i<3;i++) {
							python_score[i].setVisible(true);
						}
					}
				}
				else {
					//�ڹ� ��ġ ǥ�þ���
					if (e.getItem()==subject[0]) {
						for (int i=0;i<3;i++) {
							java_score[i].setVisible(false);
						}
					}
					//C��� ��ġ ǥ�þ���
					else if (e.getItem()==subject[1]) {
						for (int i=0;i<3;i++) {
							C_score[i].setVisible(false);
						}
					}
					//���̽� ��ġ ǥ�þ���
					else {
						for (int i=0;i<3;i++) {
							python_score[i].setVisible(false);
						}
					}
			   }
			}
		}
	}
	// CharPanel: ���뵵ǥ�� ����� �г�
	class ChartPanel extends JPanel {
		Vector<Integer> jstudent = new Vector<Integer>();
		Vector<Integer> cstudent = new Vector<Integer>();
		Vector<Integer> pystudent = new Vector<Integer>();

		// ���� ��հ� �ִ���, �ּ����� ���� �Ӽ���
		int jsum, javer, jmax, jmin; 
		int csum, caver, cmax, cmin;
		int pysum, pyaver, pymax, pymin;

		// ��Ʈ �� ����
		Color averageColor = Color.cyan;
		Color maxColor = Color.yellow;
		Color minColor = Color.LIGHT_GRAY;
		
		public ChartPanel() {
			makeChart();
			
			// ����Ŭ���ϸ� ��Ʈ�� ���� �ٲٴ� ���콺������
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount()==2) {
						  int r = (int)(Math.random()*256); // 0~255������ ��������
						  int g = (int)(Math.random()*256);
						  int b = (int)(Math.random()*256);   
						  averageColor = new Color(r,g,b);
						  maxColor = new Color(g,b,r);
						  minColor = new Color(b,r,g);
						  repaint();
					}
				}
			});
		}

		public void makeChart() {
			Iterator it = all_stu.iterator();
			while (it.hasNext()) {
				Student ss = (Student) it.next();
				jstudent.add(ss.getJava());
				cstudent.add(ss.getC());
				pystudent.add(ss.getPython());
			}

			// �ڹ� ���� ���, �ִ���, �ּ���
			jsum = 0;
			jmax = Integer.MIN_VALUE;
			jmin = Integer.MAX_VALUE;
			for (int i = 0; i < jstudent.size(); i++) {
				int n = jstudent.get(i);
				jsum += n;
				if (jmax < n)
					jmax = n;
				if (n > 0 && jmin > n)
					jmin = n;
			}
			javer = jsum / jstudent.size();

			// C��� ����  ���, �ִ���, �ּ���
			csum = 0;
			cmax = Integer.MIN_VALUE;
			cmin = Integer.MAX_VALUE;
			for (int i = 0; i < cstudent.size(); i++) {
				int n = cstudent.get(i);
				csum += n;
				if (cmax < n)
					cmax = n;
				if (n > 0 && cmin > n)
					cmin = n;
			}
			caver = csum / cstudent.size();

			// ���̽� ����  ���, �ִ���, �ּ���
			pysum = 0;
			pymax = Integer.MIN_VALUE;
			pymin = Integer.MAX_VALUE;
			for (int i = 0; i < pystudent.size(); i++) {
				int n = pystudent.get(i);
				pysum += n;
				if (pymax < n)
					pymax = n;
				if (n > 0 && pymin > n)
					pymin = n;
			}
			pyaver = pysum / pystudent.size();
			repaint();
		}

		// ���뵵ǥ �׸���
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawLine(120, 400, 650, 400);

			for (int line = 1; line < 11; line++) { // ���� 10��
				g.drawString(line * 10 + "", 80, 405 - 30 * line);
				g.drawLine(120, 400 - 30 * line, 650, 400 - 30 * line);
			}
			g.drawLine(120, 80, 120, 400);

			g.setFont(new Font("���ü", Font.BOLD, 15));
			g.drawString("�ڹ�", 180, 420);
			g.drawString("C���", 360, 420);
			g.drawString("���̽�", 550, 420);

			g.setColor(averageColor);
			g.fillRect(140, 400 - javer * 3, 32, javer * 3);
			g.setColor(maxColor);
			g.fillRect(180, 400 - jmax * 3, 32, jmax * 3);
			g.setColor(minColor);
			g.fillRect(220, 400 - jmin * 3, 32, jmin * 3);

			g.setColor(averageColor);
			g.fillRect(330, 400 - caver * 3, 32, caver * 3);
			g.setColor(maxColor);
			g.fillRect(370, 400 - cmax * 3, 32, cmax * 3);
			g.setColor(minColor);
			g.fillRect(410, 400 - cmin * 3, 32, cmin * 3);

			g.setColor(averageColor);
			g.fillRect(520, 400 - pyaver * 3, 32, pyaver * 3);
			g.setColor(maxColor);
			g.fillRect(560, 400 - pymax * 3, 32, pymax * 3);
			g.setColor(minColor);
			g.fillRect(600, 400 - pymin * 3, 32, pymin * 3);
			
			// ���, �ְ���, ������ ���� ���� �˷���.
			g.setColor(averageColor); // ����� û�ϻ�
			g.fillRect(680, 190, 15, 15);
			g.setColor(Color.black);
			g.drawString("���", 700, 200); 

			g.setColor(maxColor); // �ְ����� �����
			g.fillRect(680, 240, 15, 15);
			g.setColor(Color.black);
			g.drawString("�ְ���", 700, 250);

			g.setColor(minColor); // �������� ȸ��
			g.fillRect(680, 290, 15, 15);
			g.setColor(Color.black);
			g.drawString("������", 700, 300);
		}
	}
	
	// PiePanel: ���� ������Ʈ�� �޾� �׸��� �г�
	class PiePanel extends JPanel {
		private Pie[] pie=new Pie[3];
		
		PiePanel() {
			setLayout(null);
			setBackground(Color.white);
			//�޺��ڽ�
			String[] DEPT= {"��ǻ���а�", "���а�", "�����й̵���а�"};
			JComboBox<String> strCombo=new JComboBox<String>(DEPT);
			strCombo.setForeground(Color.black);
			strCombo.setFont(new Font("�������", Font.BOLD, 15));
			add(strCombo);
			strCombo.setSize(150,30);
			strCombo.setLocation(650,10);
			
			//�������� ����
			JPanel title_panel = new JPanel(new GridLayout(1, 3));
			title_panel.setSize(720,50);
			title_panel.setLocation(130,100);
			title_panel.setBackground(Color.WHITE);
			add(title_panel);
			JLabel title[]=new JLabel[3];
			title[0]=new JLabel("�ڹ�");
			title[1]=new JLabel("C���");
			title[2]=new JLabel("���̽�");
			
			for (int i=0;i<title.length;i++) {
				title_panel.add(title[i]);
				title[i].setFont(new Font("Hy����L", Font.BOLD, 20));
			}
			
			//���׷���
			for (int i=0;i<3;i++) {
				pie[i]=new Pie(i);			
				add(pie[i]);
				pie[i].setSize(750, 200);
				pie[i].setLocation(50, 150);
	
			}
			pie[0].setVisible(true);//�а��� ��ǻ���а��� ��� ������Ʈ
			pie[1].setVisible(false);//�а��� ���а��� ��� ������Ʈ
			pie[2].setVisible(false);//�а��� �����й̵���а��� ��� ������Ʈ
			
			strCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> cb=(JComboBox<String>)e.getSource();
					int i=cb.getSelectedIndex();
					if (i==0) {
						pie[0].setVisible(true);//��ǻ���а��� ��찡 ��Ÿ��
						pie[1].setVisible(false);
						pie[2].setVisible(false);
					}
					if (i==1) {
						pie[0].setVisible(false);
						pie[1].setVisible(true);//���а��� ��찡 ��Ÿ��
						pie[2].setVisible(false);
					}
					if (i==2) {
						pie[0].setVisible(false);
						pie[1].setVisible(false);
						pie[2].setVisible(true);//�����й̵���а��� ��찡 ��Ÿ��
					}
				}
			});
			
			// jp3: ������ �������ִ� ���� ��� �󺧰� ���򺰷� ������ ���� �󺧷� ����
			JPanel jp3 = new JPanel(new GridLayout(1, 10, 10, 0));
			jp3.setBackground(color);
			jp3.setLocation(250, 400);
			jp3.setSize(300, 20);
			jp3.setBackground(Color.WHITE);
			add(jp3);

			JLabel jl[] = new JLabel[5];
			jl[0] = new JLabel("A");
			jl[1] = new JLabel("B");
			jl[2] = new JLabel("C");
			jl[3] = new JLabel("D");
			jl[4] = new JLabel("F");

			JLabel color[] = new JLabel[5];
			for (int i = 0; i < 5; i++) {
				color[i] = new JLabel("");
				color[i].setSize(10, 10);
				color[i].setOpaque(true);
			}

			color[0].setBackground(Color.yellow); // A�� �����
			color[1].setBackground(Color.orange); // B�� ��Ȳ��
			color[2].setBackground(Color.green); // C�� �ʷϻ�
			color[3].setBackground(Color.CYAN);  // D�� û�ϻ�
			color[4].setBackground(Color.LIGHT_GRAY); // F�� ȸ��
			for (int i = 0; i < 5; i++) {
				jp3.add(color[i]);
				jl[i].setFont(new Font("���ü", Font.BOLD, 15));
				jp3.add(jl[i]);
			}
		}

	}
	// �ڹ��� ���� ������ ��Ÿ���� ���׷���
	class Pie extends JPanel {
		int jcount[] = { 0, 0, 0, 0, 0 }; // ������ ������ ������ �迭 jcount
		double jrate[] = { 0, 0, 0, 0, 0 }; // ������ ������ ������ �迭 jrate
		int jarcAngle[] = new int[5]; // ��Ʈ �׸��� ����� ������ ��� jarcAngle

		int ccount[] = { 0, 0, 0, 0, 0 }; // ������ ������ ������ ccount
		double crate[] = { 0, 0, 0, 0, 0 }; // ������ ������ ������ ccount
		int carcAngle[] = new int[5]; // ��Ʈ �׸��� ����� ������ ��� carcAngle

		int pycount[] = { 0, 0, 0, 0, 0 }; // ������ ������ ������ �迭 pycount
		double pyrate[] = { 0, 0, 0, 0, 0 }; // ������ ������ ������ �迭 pyrate
		int pyarcAngle[] = new int[5]; // ��Ʈ �׸��� ����� ������ ��� pyarcAngle
		
		Color[] color = { Color.yellow, Color.orange, Color.green, Color.cyan, Color.LIGHT_GRAY };
		
		int index;//�а��� �����ϱ� ���� �뵵
		Pie(int index) {
			this.index=index;
			drawChart();
		}

		public void drawChart() {
			Vector<Character> jstudent = new Vector<Character>();
			Vector<Character> cstudent = new Vector<Character>();
			Vector<Character> pystudent = new Vector<Character>();
			Vector<Character> allstudent=new Vector<Character>();
			Iterator it = all_stu.iterator();
			String dept[]= {"��ǻ�Ͱ��а�", "���а�","�����й̵���а�"};
			while (it.hasNext()) {
				Student ss = (Student) it.next();
				String std_dept=ss.getDept();
				int i=0;
				if (std_dept.equals(dept[index])) {
					jstudent.add(ss.getJava_grade());
					cstudent.add(ss.getC_grade());
					pystudent.add(ss.getPython_grade());
				}
			}
			
			// �ڹ� ���� ����
			for (int i = 0; i < jstudent.size(); i++) {
				char grade = jstudent.get(i);
				if (grade == 'A')
					jcount[0]++;
				else if (grade == 'B')
					jcount[1]++;
				else if (grade == 'C')
					jcount[2]++;
				else if (grade == 'D')
					jcount[3]++;
				else
					jcount[4]++;
			}
			for (int i = 0; i < 5; i++) {
				jrate[i] = ((double) jcount[i] / jstudent.size());
			}
			for (int i = 0; i < 5; i++) { // ��Ʈ �׸��� ����� ������ ���
				jarcAngle[i] = (int) Math.round(jrate[i] * 360);
			}
			
			// C��� ���� ����
			for (int i = 0; i < cstudent.size(); i++) {
				char grade = cstudent.get(i);
				if (grade == 'A')
					ccount[0]++;
				else if (grade == 'B')
					ccount[1]++;
				else if (grade == 'C')
					ccount[2]++;
				else if (grade == 'D')
					ccount[3]++;
				else
					ccount[4]++;
			}
			
			for (int i = 0; i < 5; i++) {
				crate[i] = ((double) ccount[i] / cstudent.size());
			}
			for (int i = 0; i < 5; i++) { // ��Ʈ �׸��� ����� ������ ���
				carcAngle[i] = (int) Math.round(crate[i] * 360);
			}
			
			// ���̽� ���� ����
			for (int i = 0; i < pystudent.size(); i++) {
				char grade = pystudent.get(i);
				if (grade == 'A')
					pycount[0]++;
				else if (grade == 'B')
					pycount[1]++;
				else if (grade == 'C')
					pycount[2]++;
				else if (grade == 'D')
					pycount[3]++;
				else
					pycount[4]++;
			}

			for (int i = 0; i < 5; i++) {
				pyrate[i] = ((double) pycount[i] / pystudent.size());
			}
			for (int i = 0; i < 5; i++) { // ��Ʈ �׸��� ����� ������ ���
				pyarcAngle[i] = (int) Math.round(pyrate[i] * 360);
			}
			repaint();
		}

		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			int jstartAngle = 0;
			int cstartAngle = 0;
			int pystartAngle=0;
			for (int i = 0; i < 5; i++) {
				g.setColor(color[i]); 
				//�ڹ� ������Ʈ �׸���
				g.fillArc(0, 0, 200, 200, jstartAngle, jarcAngle[i]);
				jstartAngle = jstartAngle + jarcAngle[i];
				//C��� ������Ʈ �׸���
				g.fillArc(250, 0, 200, 200, cstartAngle, carcAngle[i]);
				cstartAngle = cstartAngle + carcAngle[i];
				//���̽� ������Ʈ �׸���
				g.fillArc(500, 0, 200, 200, pystartAngle, pyarcAngle[i]);
				pystartAngle = pystartAngle + pyarcAngle[i];
			}
		}
	}


	public static void main(String[] args) {
		new Main();
	}
}
