/*
각자의 주요 담당 클래스와 팀원
20150846 정보통계학과 공가은(InputPanel,SortPanel)
20160528 문헌정보학과 김지수(Chart, PiePanel)
*/
import java.awt.*;
import javax.swing.*;

//테두리에 선을 넣기 위해 해당 부분을 추가함. 참고한 블로그: https://blog.naver.com/rice3320/140061211604
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {
	static AllStudent all_stu = new AllStudent(4);// all_stu: 모든 학생의 정보를 담을 변수
	JButton Pro = new JButton("교수");// 다이얼로그의 교수 버튼
	JButton Stu = new JButton("학생");// 다이얼로그의 학생 버튼
	InputPanel input;// 교수가 성적을 입력하는 패널
	JTabbedPane pane;// 학생이 성적을 보는 패널
	JTextArea ta = new JTextArea(7, 20);// input 패널의 JTextArea 부분
	Color color = new Color(178, 235, 244); // 배경색

	//ChooseDialog: 교수인지 학생인지를 선택하는 다이얼로그
	class ChooseDialog extends JDialog {
		ChooseDialog() {
			setTitle("선택");
			setLayout(new FlowLayout());
			add(new JLabel("둘 중 하나를 선택해주세요."));
			//교수버튼 디자인
			add(Pro);
			Pro.setBackground(Color.BLACK);
			Pro.setForeground(Color.WHITE);
			Pro.setFont(new Font("Hy엽서L", Font.BOLD, 17));
			//학생버튼 디자인
			add(Stu);
			Stu.setBackground(Color.BLACK);
			Stu.setForeground(Color.WHITE);
			Stu.setFont(new Font("Hy엽서L", Font.BOLD, 17));
			setSize(200, 100);
		}
	}

	// 파일을 읽어오는 클래스1: 교수가 학생정보만 입력된 파일(stdinfo.txt)과 학생정보와 성적이 입력된 파일(stu.txt)을 가져오는 기능
	class InputFile {
		String name, dept, tel; // 이름, 학과, 전화번호
		int id, java, C, python; // 학번, 자바, C언어, 파이썬

		InputFile() {
			// 데이터 엉킴을 방지하기 위해 all_stu에 있는 정보를 모두 삭제
			all_stu.removeAll();
			try {
				// stdinfo.txt: 학생정보만 입력된 파일
				File file = new File("stdinfo.txt");
				Scanner s = new Scanner(file);
				// stu.txt: 학생정보와 성적이 입력된 파일
				File file2 = new File("stu.txt");
				Scanner s2 = new Scanner(file2);
				// 학생정보만 입력된 경우
				while (s.hasNext()) {
					name = s.next();
					id = s.nextInt();
					dept = s.next();
					tel = s.next();
					Student ss = new Student(name, id, dept, tel);
					all_stu.appendStudent(ss);
				}
				// 학생정보와 성적이 입력된 경우
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

	// 파일을 읽어오는 클래스2: 모든 학생의 성적에 대한 입력이 완료된 파일(data.txt)을 가져오는 기능
	class OutputFile {
		String name, dept, tel; // 이름, 학과, 전화번호
		int id, java, C, python; // 학번, 자바, C언어, 파이썬

		OutputFile() {
			// 데이터 엉킴을 방지하기 위해 all_stu에 있는 정보를 모두 삭제
			all_stu.removeAll();
			try {
				// 모든 학생의 성적에 대한 입력이 완료된 파일
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
				System.out.println("파일이 만들어지지 않았습니다.");
			}
		}
	}

	//Main: inputPanel(교수), JTabbedPane(학생)이 번걸아 전환할 수 있는 패널 
	public Main() {
		setTitle("성적처리프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		//배경화면
		ImagePanel c=new ImagePanel(new ImageIcon("background.jpg"));
		content.add(c);
		c.setLayout(null);
		// 교수인지 학생인지를 선택하는 다이얼로그
		ChooseDialog ch_dialog = new ChooseDialog();
		ch_dialog.setVisible(true);

		// 교수에서 학생으로 전환 혹은 학생에서 교수로 전환하고자 하는 경우 다이얼로그를 다시 띄우는 버튼
		JButton jb = new JButton("교수/학생 선택");
		jb.setLocation(700, 20);
		jb.setSize(150, 30);
		c.add(jb);
		jb.setBackground(Color.GRAY);
		jb.setForeground(Color.WHITE);
		jb.setFont(new Font("Hy엽서L", Font.BOLD, 15));
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ch_dialog.setVisible(true);
			}
		});

		// 다이얼로그에서 교수버튼을 선택하는 경우
		Pro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ch_dialog.setVisible(false);
				// 맨처음으로 교수버튼을 눌렀을때 input패널이 만들어지지 않았기 때문에 생성해주는 부분
				if (input == null) {
					int flag;
					//data.txt(교수가 입력을 완료할 경우 생기는 파일)가 존재하는 경우에는 flag를 0으로, data.txt가 존재하지 않는 경우 flag=1로 만들어줌
					//flag=0이면 data.txt를 읽는 OutputFile()이 실행될 것이고, flag=1이면  stdinfo.txt, stu.txt를 읽는 InputFile()이  실행
					
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
				// 다시 교수버튼을 누르면 이미 input패널이 만들어졌기 때문에 다시 보여주기만 함.
				else {
					input.setVisible(true);
				}
				// 교수버튼을 누르면, 학생인 경우 보는 pane은 보이지 않게 함.
				if (pane != null) {
					pane.setVisible(false);
				}
			}
		});

		// 다이얼로그에서 학생버튼을 선택하는 경우
		Stu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ch_dialog.setVisible(false);
				// 맨처음으로 학생버튼을 눌렀을때 pane이 만들어지지 않았기 때문에 생성해주는 부분
				if (pane == null) {
					// data.txt 파일이 없는 경우 학생버튼으로 넘어가는 것을 막기 위해 data.txt 파일이 있는지 검사해줌
					try {
						File file = new File("data.txt");
						Scanner s = new Scanner(file);
					} catch (IOException e1) {
						// 만약 파일이 없다면, 에러가 나서 교수버튼을 눌러 정보를 입력하라는 다이얼로그를 띄움.
						JOptionPane.showMessageDialog(null, "아직 파일이 형성되지 않았습니다. 교수를 눌러 정보를 입력해주세요!", "파일오류",
								JOptionPane.ERROR_MESSAGE);
						ch_dialog.setVisible(true);
						return;
					}
					pane = createTabbedPane();
					c.add(pane);
					pane.setBackground(new Color(209,178,255));
					pane.setFont(new Font("Hy엽서L", Font.BOLD, 20));
					pane.setSize(830, 500);
					pane.setLocation(20, 50);
					setVisible(true);
				}
				// 다시 학생버튼을 누르면 이미 pane패널이 만들어졌기 때문에 다시 보여주기만 함
				else {
					pane.setVisible(true);
				}
				// 학생버튼을 누르면, 교수인 경우 보는 input은 보이지 않게 함
				if (input != null) {
					input.setVisible(false);
				}
			}
		});

		// input 패널에서의 JTextArea에 대한 키리스너
		// 기능: +버튼을 누르면 글씨 확대, -버튼을 누르면 글씨 축소
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyChar();
				if (keyCode == '+') {
					Font f = ta.getFont();
					int size = f.getSize();
					ta.setFont(new Font("굴림", Font.PLAIN, size + 5));
				} 
				else if (keyCode == '-') {
					Font f = ta.getFont();
					int size = f.getSize();
					if (size <= 5)
						return;
					ta.setFont(new Font("굴림", Font.PLAIN, size - 5));
				}
			}
		});
		c.setFocusable(true);
		c.requestFocus();
		// 키가 focus를 잃어버릴 경우 마우스를 클릭하면 다시 포커스를 찾아줌
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

	//학생부분에서 등수확인, 막대도표, 파이차트를 선택하는 JTabbedPane
	private JTabbedPane createTabbedPane() {
		new OutputFile();//파일을 읽어옴
		JTabbedPane pane = new JTabbedPane();
		pane.setBackground(Color.BLACK);
		pane.addTab("등수확인", new SortPanel());
		pane.addTab("막대도표", new Chart());
		pane.addTab("파이차트", new PiePanel());
		return pane;
	}

	//InputPanel: 교수가 학생의 성적을 입력하는 패널
	class InputPanel extends JPanel {
		JRadioButton jrb_show[] = new JRadioButton[4];
		InputPanel(int flag) {
			setLayout(null);
			setBackground(Color.WHITE);
			TitledBorder blacktb = new TitledBorder(new LineBorder(Color.BLACK));
			setBorder(blacktb);
			// 제목
			JLabel title = new JLabel("성적 입력");
			add(title);
			title.setSize(280, 50);
			title.setLocation(20, 40);
			title.setHorizontalAlignment(JLabel.CENTER);
			title.setFont(new Font("Hy엽서L", Font.BOLD, 40));

			// 버튼
			JButton jb[] = new JButton[4];
			jb[0] = new JButton("계산");
			jb[1] = new JButton("추가");
			jb[2] = new JButton("완료");
			jb[3] = new JButton("검색");
			for (int i = 0; i < jb.length; i++) {
				jb[i].setBackground(Color.BLACK);
				jb[i].setForeground(Color.WHITE);
				jb[i].setFont(new Font("Hy엽서L", Font.BOLD, 17));
			}

			// jp1패널: 학번(JLabel la), 학번을 입력하는 텍스트(JTextArea tt), 검색 버튼(jb[4])로 구성
			JPanel jp1 = new JPanel(new GridLayout(1, 3, 5, 0));
			jp1.setBackground(Color.white);
			jp1.setSize(280, 40);
			jp1.setLocation(5, 100);
			add(jp1);
			JLabel la = new JLabel("학번");
			la.setHorizontalAlignment(JLabel.CENTER);
			la.setFont(new Font("Gothic", Font.BOLD, 15));
			jp1.add(la);
			JTextField tt = new JTextField(8);
			jp1.add(tt);
			jp1.add(jb[3]);

			// jp2패널: 검색 버튼이 눌러지면, 채워질 학번, 이름, 각과목별 성적이 레이블(jl)과 텍스트(tf)로 구성
			JPanel jp2 = new JPanel();
			jp2.setLayout(new GridLayout(7, 2, 5, 2));
			jp2.setBackground(Color.white);
			jp2.setSize(235, 200);
			jp2.setLocation(40, 150);
			add(jp2);
			TitledBorder graytb = new TitledBorder(new LineBorder(Color.LIGHT_GRAY));//각 레이블의 테투리 색
			JLabel[] jl = new JLabel[11];
			jl[0] = new JLabel("학번");
			jl[1] = new JLabel("");
			jl[2] = new JLabel("이름");
			jl[3] = new JLabel("");
			for (int i = 0; i < 4; i++) {
				jp2.add(jl[i]);
				jl[i].setBorder(graytb);
			}
			JTextField[] tf = new JTextField[3];
			jl[4] = new JLabel("자바");
			jl[5] = new JLabel("C언어");
			jl[6] = new JLabel("파이썬");
			for (int i = 0; i < tf.length; i++) {
				jp2.add(jl[4 + i]);
				jl[4+i].setBorder(graytb);
				tf[i] = new JTextField(10);
				jp2.add(tf[i]);
			}
			jl[7] = new JLabel("총점");
			jl[8] = new JLabel("");
			jl[9] = new JLabel("평균");
			jl[10] = new JLabel("");
			for (int i = 7; i < jl.length; i++) {
				jp2.add(jl[i]);
				jl[i].setBorder(graytb);
			}
			for (int i = 0; i < jl.length; i++) {
				jl[i].setHorizontalAlignment(JLabel.CENTER);
			}

			// jp3패널: 계산, 추가, 완료 버튼으로 구성
			JPanel jp3 = new JPanel(new GridLayout(1, 3, 5, 0));
			add(jp3);
			jp3.setBackground(Color.white);
			jp3.setLocation(30, 360);
			jp3.setSize(260, 40);
			for (int i = 0; i < 3; i++) {
				jp3.add(jb[i]);
			}

			// jp_ta패널: JTextArea로 구성
			JPanel jp_ta = new JPanel(new BorderLayout());
			jp_ta.setSize(500, 300);
			jp_ta.setLocation(310, 100);
			add(jp_ta);
			jp_ta.add(new JScrollPane(ta), BorderLayout.CENTER);
			
			//jp4패널:각 과목별로 정렬하는 RadioButton으로 구성
			JPanel jp4 = new JPanel(new GridLayout(1, 4, 5, 0));
			add(jp4);
			jp4.setBackground(Color.white);
			jp4.setLocation(510, 410);
			jp4.setSize(300, 40);
			ButtonGroup g = new ButtonGroup();
			jrb_show[0] = new JRadioButton("자바");
			jrb_show[1] = new JRadioButton("C언어");
			jrb_show[2] = new JRadioButton("파이썬");
			jrb_show[3] = new JRadioButton("총점");
			for (int i = 0; i < jrb_show.length; i++) {
				g.add(jrb_show[i]);
				jp4.add(jrb_show[i]);
				jrb_show[i].setBackground(Color.WHITE);
				jrb_show[i].setFont(new Font("Hy엽서L", Font.BOLD, 14));
			}

			// ImagePanel(이미지가 지정한 공간에 꽉채우는 기능[뒤에 나옴]) 패널: 만약 data.txt가 있거나, 완료버튼을 눌러진 경우 나타나는 패널
			ImageIcon icon = new ImageIcon("finish.png");
			ImagePanel im_panel = new ImagePanel(icon);
			im_panel.setSize(200, 200);
			im_panel.setLocation(80, 150);
			add(im_panel);
			im_panel.setVisible(false);

			// flag가 0인 경우는 데이터 입력이 완료 되었다는 의미이므로, 몇몇 기능을 제한함
			if (flag==0) {
				new OutputFile();
				im_panel.setVisible(true);//이미지가 나타남.
				jp2.setVisible(false);
				jb[0].setEnabled(false);//계산 기능 제한
				jb[1].setEnabled(false);//추가 기능 제한
				jb[2].setEnabled(false);//완료기능 제한
				jb[3].setEnabled(false);//검색기능 제한
			}
			//flag가 0이 아닌 경우에는 데이터 입력이 남았다는 의미
			else {
				new InputFile();
			}
			
			Iterator it = all_stu.iterator();
			while (it.hasNext()) {
				Student ss = (Student) it.next();
				// 전체 학생의 정보를 보여줌
				ta.append("이름: " + ss.getName() + ", 학번: " + ss.getStd_id() + ", 자바: " + ss.getJava() + ", 학점: "
						+ ss.getJava_grade() + ", C언어: " + ss.getC() + ", 학점: " + ss.getC_grade() + ", 파이썬: "
						+ ss.getPython() + ", 학점: " + ss.getPython_grade() + ", 총점: " + ss.getSum() + ", 평균: "
						+ ss.getAvg() + "\n");
			}
			ta.setFont(new Font("굴림", Font.PLAIN, 10));

			// 검색버튼: 사용자가 해당하는 학번을 입력하면, 해당 학번의 이름, 학번, 각 과목별 성적을 보여줌
			jb[3].addActionListener(new ActionListener() {
				int length = all_stu.getLength();
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						// 학번이 없는 경우
						if (Integer.parseInt(tt.getText()) > length || Integer.parseInt(tt.getText()) < 0) {
							tt.setText("");
							JOptionPane.showMessageDialog(null, "해당하는 학번이 없습니다.", "범위이상", JOptionPane.ERROR_MESSAGE);
							return;
						}
						Iterator it = all_stu.iterator();
						// 해당 학번의 이름, 학번, 각 과목별 성적을 띄우는 코드
						while (it.hasNext()) {
							Student ss = (Student) it.next();
							if (ss.getStd_id() == Integer.parseInt(tt.getText())) {// 만약 tt(사용자가 입력하는 부분)을 찾는다면
								jl[1].setText(ss.getName());// 해당 학생의 이름,
								jl[3].setText(Integer.toString(ss.getStd_id()));// 해당 학생의 학번,
								if (ss.getJava() > 0) {// 해당 학생의 자바 성적이 있는 경우
									tf[0].setText(Integer.toString(ss.getJava()));// 해당 학생의 자바 성적으로 채워줌
								}
								else {//해당 학생의 자바성적이 없는 경우
									tf[0].setText("");
								}
								if (ss.getC() > 0) {// 해당 학생의 C언어 성적이 있는 경우
									tf[1].setText(Integer.toString(ss.getC()));// 해당 학생의 C언어 성적으로 채워줌
								}
								else {//해당 학생의 C언어성적이 없는 경우
									tf[1].setText("");
								}
								if (ss.getPython() > 0) {// 해당 학생의 파이썬 성적이 있는 경우
									tf[2].setText(Integer.toString(ss.getPython()));// 해당 학생의 파이썬 성적으로 채워줌
								}
								else {//해당 학생의 파이썬 성적이 없는 경우
									tf[2].setText("");
								}
							}
						}			
						jl[8].setText("");
						jl[10].setText("");
					} catch (NumberFormatException e1) {
						//학번이 없는 경우
						JOptionPane.showMessageDialog(null, "학번을 입력하지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			// 계산버튼: 입력한 각 과목별 성적의 총점, 평균을 알려줌
			jb[0].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int[] subject = new int[3];
					Iterator it = all_stu.iterator();
					try {
						subject[0] = Integer.parseInt(tf[0].getText());// 자바성적
						subject[1] = Integer.parseInt(tf[1].getText());// C언어 성적
						subject[2] = Integer.parseInt(tf[2].getText());// 파이썬 성적

						// 성적이 0과 100사이의 정수로 입력하지 않은 경우
						for (int i = 0; i < 3; i++) {
							if (subject[i] < 0 || subject[i] > 100) {
								jl[8].setText("");// 총점
								jl[10].setText("");// 평균
								JOptionPane.showMessageDialog(null, "0부터 100사이의 정수를 입력해주세요.", "입력오류",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						// 입력을 제대로 한경우
						int sum = subject[0] + subject[1] + subject[2];
						double avg = sum / 3;
						jl[8].setText(Integer.toString(sum));// 총점
						jl[10].setText(Double.toString(avg));// 평균
					} catch (NumberFormatException e1) {
						// 정보가 입력이 되지 않은 경우
						JOptionPane.showMessageDialog(null, "입력하지 않은 정보가 있습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			// 추가버튼: 해당 학생의 정보를 넣음
			jb[1].addActionListener(new ActionListener() {
				int[] subject = new int[3];

				@Override
				public void actionPerformed(ActionEvent e) {
					Iterator it = all_stu.iterator();
					Iterator it2 = all_stu.iterator();
					int length = all_stu.getLength();
					try {
						// 학번이 올바르지 않은 경우
						if (Integer.parseInt(tt.getText()) > length || Integer.parseInt(tt.getText()) < 0) {
							JOptionPane.showMessageDialog(null, "해당하는 학번은 없습니다.", "범위이상", JOptionPane.ERROR_MESSAGE);
							return;
						}
						subject[0] = Integer.parseInt(tf[0].getText());//사용자가 입력한 자바 성적을 가져옴
						subject[1] = Integer.parseInt(tf[1].getText());//사용자가 입력한 C언어 성적을 가져옴
						subject[2] = Integer.parseInt(tf[2].getText());//사용자가 입력한 파이썬 성적을 가져옴
						while (it.hasNext()) {
							// 과목별 성적이 0미만 100초과로 입력된 경우
							for (int i = 0; i < 3; i++) {
								if (subject[i] < 0 || subject[i] > 100) {
									JOptionPane.showMessageDialog(null, "0이상 100이하로 입력해주세요.", "범위이상",
											JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							// 학생의 정보 추가
							Student ss = (Student) it.next();
							if (ss.getStd_id() == Integer.parseInt(tt.getText())) {
								ss.setJava(subject[0]);//자바성적
								ss.setJava_grade(subject[0]);//자바학점
								ss.setC(subject[1]);//C언어성적
								ss.setC_grade(subject[1]);//C언어학점
								ss.setPython(subject[2]);//파이썬성적
								ss.setPython_grade(subject[2]);//파이썬학점
								ss.setSum(subject[0], subject[1], subject[2]);//총점
								ss.setAvg(subject[0], subject[1], subject[2]);//평균
							}
						}
						//사용자가 입력하는 부분을 지워줌
						tt.setText("");
						// ta의 있던 데이터를 지워주고, 입력된 새로운 정보를 받아 다시 채움
						ta.setText("");
						while (it2.hasNext()) {
							Student ss = (Student) it2.next();
							ta.append("이름: " + ss.getName() + ", 학번: " + ss.getStd_id() + ", 자바: " + ss.getJava()
									+ ", 학점: " + ss.getJava_grade() + ", C언어: " + ss.getC() + ", 학점: " + ss.getC_grade()
									+ ", 파이썬: " + ss.getPython() + ", 학점: " + ss.getPython_grade() + ", 총점: "
									+ ss.getSum() + ", 평균: " + ss.getAvg() + "\n");
						}
						ta.setFont(new Font("굴림", Font.PLAIN, 10));

						// 레이블에 적힌 학번, 이름, 자바성적, C언어, 파이썬 성적을 지워줌
						for (int i = 0; i < tf.length; i++) {
							jl[1].setText("");
							jl[3].setText("");
							jl[8].setText("");
							jl[10].setText("");
							tf[i].setText("");
						}
					} catch (NumberFormatException e1) {
						// 입력하지 않은 정보가 있는 경우
						JOptionPane.showMessageDialog(null, "입력하지 않은 정보가 있습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			// 완료버튼: 교수가 입력을 다 완료했을 경우, 이제 학생이 볼 수 있는 data.txt 파일과 정리된 파일인 grade.dat를 만든다.
			jb[2].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Iterator it = all_stu.iterator();
					Iterator it2 = all_stu.iterator();
					try {
						while (it.hasNext()) {
							// 입력하지 않은 정보가 존재하는 경우
							Student ss = (Student) it.next();
							if (ss.getJava() == -1 || ss.getC() == -1 || ss.getPython() == -1) {
								JOptionPane.showMessageDialog(null, "입력하지 않은 정보가 있습니다.", "입력오류",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						PrintWriter pw = new PrintWriter("data.txt");
						PrintWriter pw2 = new PrintWriter("grade.dat");

						while (it2.hasNext()) {
							// 입력하지 않은 정보가 존재하는 경우
							Student ss = (Student) it2.next();
							
							// data.txt는 학생이 볼 파일
							pw.println(ss.getName() + " " + ss.getStd_id() + " " + ss.getDept() + " " + ss.getTel()
									+ " " + ss.getJava() + " " + ss.getC() + " " + ss.getPython());
							// grade.dat는 정리된 파일
							pw2.println("이름: " + ss.getName() + ", 학번: " + ss.getStd_id() + ", 학과: " + ss.getDept()
									+ ", 전화번호: " + ss.getTel() + ", 자바성적: " + ss.getJava() + ", 자바학점: "
									+ ss.getJava_grade() + ", C언어성적: " + ss.getC() + ", C언어학점: " + ss.getC_grade()
									+ ", 파이썬성적: " + ss.getPython() + ", 파이썬학점: " + ss.getPython_grade() + ", 총점: "
									+ ss.getSum() + ", 평균: " + ss.getAvg());
						}
						pw.close();
						pw2.close();
					} catch (IOException e2) {
						System.out.println("File not Put.");
					}

					im_panel.setVisible(true);//이미지 나타남
					jp2.setVisible(false);//입력하는 공간이 사라짐
					jb[0].setEnabled(false);//계산 버튼 사라짐
					jb[1].setEnabled(false);//추가 버튼 사라짐
					jb[2].setEnabled(false);//완료 버튼 사라짐
					jb[3].setEnabled(false);//검색 버튼 사라짐
				}
			});
			// 해당 과목의 라디오버튼을 누르면 나오는 정렬이 됨
			for (int i = 0; i < jrb_show.length; i++) {
				jrb_show[i].addItemListener(new RadioItemListener());
			}
		}

		// 정렬알고리즘: 해당하는 과목의 라디오 버튼을 누르면 해당 과목의 점수 높은 순서대로 정렬
		class RadioItemListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Iterator it = all_stu.iterator();
				int length = all_stu.getLength();
				try {
					//ta를 비워줌.
					ta.setText("");
					//array 배열
					Student[] array = new Student[length];
					int index = 0;
					//all_stu의 내용을 array 배열에 넣어줌.
					while (it.hasNext()) {
						Student ss = (Student) it.next();
						array[index] = ss;
						index++;
					}

					int i, j;
					for (i = 0; i < length; i++) {
						for (j = i; j < length; j++) {
							// 자바 정렬
							if (jrb_show[0].isSelected()) {
								if (array[i].getJava() < array[j].getJava()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
							//C언어 정렬
							if (jrb_show[1].isSelected()) {
								if (array[i].getC() < array[j].getC()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
							//파이썬 정렬
							if (jrb_show[2].isSelected()) {
								if (array[i].getPython() < array[j].getPython()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
							//전체 정렬
							if (jrb_show[3].isSelected()) {
								if (array[i].getSum() < array[j].getSum()) {
									Student temp = array[i];
									array[i] = array[j];
									array[j] = temp;
								}
							}
						}
					}
					//정렬한 내용으로 다시 ta를 채움
					for (int k = 0; k < length; k++) {
						ta.append("이름: " + array[k].getName() + ", 학번: " + array[k].getStd_id() + ", 자바: "
								+ array[k].getJava() + ", 학점: " + array[k].getJava_grade() + ", C언어: " + array[k].getC()
								+ ", 학점: " + array[k].getC_grade() + ", 파이썬: " + array[k].getPython() + ", 학점: "
								+ array[k].getPython_grade() + ", 총점: " + array[k].getSum() + ", 평균: "
								+ array[k].getAvg() + "\n");
					}
					ta.setFont(new Font("굴림", Font.PLAIN, 10));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "입력하지 않은 정보가 있습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	//SortPanel: 학생이 각 과목별 자신의 등수를 조회하고, 장학금 획득을 확인하는 패널
	class SortPanel extends JPanel {
		SortPanel() {
			setLayout(null);
			setBackground(Color.WHITE);
			
			//jp1패널: 학번, 사용자가 입력하는 텍스스, 검색으로 구성 
			JPanel jp1 = new JPanel();
			jp1.setBackground(Color.WHITE);
			jp1.setLayout(new GridLayout(1, 3, 5, 0));
			jp1.setSize(300, 40);
			jp1.setLocation(0, 30);
			add(jp1);
			JLabel la = new JLabel("학번");
			la.setHorizontalAlignment(JLabel.CENTER);
			jp1.add(la);
			JTextField tt = new JTextField(8);
			jp1.add(tt);
			JButton search = new JButton("검색");
			jp1.add(search);
			search.setBackground(Color.black);
			search.setForeground(Color.WHITE);
			search.setFont(new Font("Hy엽서L", Font.BOLD, 17));

			//jp2패널: 각과목별 등수를 보여주는 label로 구성
			JPanel jp2 = new JPanel();
			jp2.setBackground(Color.white);
			jp2.setSize(280, 200);
			jp2.setLocation(30, 100);
			add(jp2);
			jp2.setLayout(new GridLayout(5, 4, 5, 5));
			String title[]= {"", "점수", "등수", "학점"};
			String name[] = { "자바", "C언어", "파이썬", "전체" };
			JLabel jl_title[]=new JLabel[4];//제목
			JLabel subject[] = new JLabel[4];//과목
			JLabel subject_grade[] = new JLabel[4];//학점
			JLabel subject_score[]=new JLabel[4];//성적
			JLabel subject_rank[]=new JLabel[4];//등수
			TitledBorder graytb = new TitledBorder(new LineBorder(Color.LIGHT_GRAY));//각 레이블의 테투리 색
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

			//학생들의 각과목만 따로 뽑아냄
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
			//정렬
			Collections.sort(java);
			Collections.reverse(java);
			Collections.sort(C);
			Collections.reverse(C);
			Collections.sort(python);
			Collections.reverse(python);
			Collections.sort(all);
			Collections.reverse(all);

			//검색버튼: 검색을 누르면 각 과목의 등수를 보여줌.
			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Iterator it = all_stu.iterator();
						while (it.hasNext()) {
							Student ss = (Student) it.next();
							if (ss.getStd_id() == Integer.parseInt(tt.getText())) {
								//성적
								subject_score[0].setText(Integer.toString(ss.getJava()));
								subject_score[1].setText(Integer.toString(ss.getC()));
								subject_score[2].setText(Integer.toString(ss.getPython()));
								subject_score[3].setText(Integer.toString(ss.getSum()));
								
								//학점
								subject_grade[0].setText(Character.toString(ss.getJava_grade()));
								subject_grade[1].setText(Character.toString(ss.getC_grade()));
								subject_grade[2].setText(Character.toString(ss.getPython_grade()));
								subject_grade[3].setText("");
								
								int java_grade = ss.getJava();
								int C_grade = ss.getC();
								int python_grade = ss.getPython();
								int sum_grade = ss.getSum();
								//자바 성적
								for (int i = 0; i < java.size(); i++) {
									//해당 학번의 점수와 같은 점수가 나올경우 그 인덱스를 적어줌
									if (java_grade == java.get(i)) {
										subject_rank[0].setText(Integer.toString(i + 1));
										break;
									}
								}
								//C언어 성적
								for (int i = 0; i < java.size(); i++) {
									if (C_grade == C.get(i)) {
										subject_rank[1].setText(Integer.toString(i + 1));
										break;
									}
								}
								//파이썬 성적
								for (int i = 0; i < java.size(); i++) {
									if (python_grade == python.get(i)) {
										subject_rank[2].setText(Integer.toString(i + 1));
										break;
									}
								}
								//전체 성적
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
						JOptionPane.showMessageDialog(null, "없는 학번입니다.", "파일오류", JOptionPane.ERROR_MESSAGE);//학번이 없는 경우
					}
				}
			});
			//장학금버튼: 전체 성적을 기준으로 1등은 수석, 2등은 차석, 30%이내면 우수장학금 지급, 나머지는 장학금 지급을 하지 않음
			JButton getMoney = new JButton("장학금");
			getMoney.setBackground(Color.black);
			getMoney.setForeground(Color.WHITE);
			getMoney.setFont(new Font("Hy엽서L", Font.BOLD, 17));
			getMoney.setSize(100, 40);
			getMoney.setLocation(200, 320);
			add(getMoney);
			
			//아무 학번도 입력하지 않은 경우(맨 처음화면) 나타날 이미지
			ImageIcon waiting = new ImageIcon("waiting.png");
			ImagePanel ip_waiting = new ImagePanel(waiting);//이미지를 지정한 공간에 맞게 채우는 패널(뒤에 설명)
			ip_waiting.setSize(300, 300);
			ip_waiting.setLocation(400, 50);
			add(ip_waiting);
			ip_waiting.setVisible(true);
			
			//장학금 획득에 성공한 경우 나타날 화면 
			ImageIcon success = new ImageIcon("success.jpg");
			ImageIcon success2 = new ImageIcon("success2.jpg");
			FickeringImg fk_success = new FickeringImg(success, success2, 300);//스레드 기능(뒤에 설명)
			fk_success.setSize(300, 300);
			fk_success.setLocation(400, 50);
			add(fk_success);
			fk_success.setVisible(false);

			//장학금 획득에 실패한 경우 나타날 화면
			ImageIcon fail = new ImageIcon("fail.png");
			ImageIcon fail2 = new ImageIcon("fail2.png");
			FickeringImg fk_fail = new FickeringImg(fail, fail2, 300);
			fk_fail.setSize(300, 300);
			fk_fail.setLocation(400, 50);
			add(fk_fail);
			fk_fail.setVisible(false);
			
			//jl_result: 장학금 결과를 알려줌
			JLabel jl_result = new JLabel("장학금 버튼을 눌러주세요!");
			jl_result.setSize(450, 50);
			jl_result.setLocation(400, 350);
			add(jl_result);
			jl_result.setFont(new Font("Hy엽서L", Font.BOLD, 17));

			//last: 우수장학금이 지급되는 학생의 수
			int last = (int) (all.size() * 0.3);
			getMoney.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Iterator it = all_stu.iterator();
						while (it.hasNext()) {
							Student ss = (Student) it.next();
							//수석
							if (Integer.parseInt(subject_rank[3].getText()) == 1) {
								jl_result.setText("축하합니다. 수석입니다.");
								ip_waiting.setVisible(false);
								fk_fail.setVisible(false);
								fk_success.setVisible(true);
							}
							//차석
							else if (Integer.parseInt(subject_rank[3].getText()) == 2) {
								jl_result.setText("축하합니다. 차석입니다.");
								ip_waiting.setVisible(false);
								fk_fail.setVisible(false);
								fk_success.setVisible(true);
							}
							//우수장학금
							else if (Integer.parseInt(subject_rank[3].getText()) <= last) {
								jl_result.setText("축하합니다. 우수 장학금입니다.");
								ip_waiting.setVisible(false);
								fk_fail.setVisible(false);
								fk_success.setVisible(true);
							} 
							//획득하지 못하는 경우
							else {
								jl_result.setText("다음 기회에....");
								ip_waiting.setVisible(false);
								fk_success.setVisible(false);
								fk_fail.setVisible(true);
							}
						}
					}catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "본인의 학번을 입력을 안하셨거나 검색버튼을 누르지 않습니다.", "입력오류",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
	}
	
	//ImagePanel: 이미지아이콘을 받아서 해당 이미지를 패널의 크기에 맞추어 그리게 하는 패널
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

	//FickeringImg: ImagePanel을 상속받아 두개의 이미지를 번갈아 보이는 스레드 기능
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
				//n=0이면 icon를, n=1이면 icon2를 가져옴
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

	// Chart: 막대도표를 받아서 그리고 과목별 평균과 최고점, 최저점을 알려주는 패널
	class Chart extends JPanel {
		JCheckBox[] subject = new JCheckBox[3];//체크박스
		JLabel[] java_score=new JLabel[3];//자바의 평균, 최고점, 최저점
		JLabel[] C_score=new JLabel[3];//C언어의 평균, 최고점, 최저점
		JLabel[] python_score=new JLabel[3];//파이썬의 평균, 최고점, 최저점
		Chart() {
			setLayout(new BorderLayout());
			setBackground(Color.WHITE);
			//jp1: 체크박스(자바, C언어, 파이썬)으로 구성
			JPanel jp1=new JPanel();
			jp1.setBackground(Color.WHITE);
			add(jp1, BorderLayout.SOUTH);
			String[] classes = { "자바", "C언어", "파이썬" };
			for (int i=0;i<classes.length;i++) {
				subject[i]=new JCheckBox(classes[i]);
				subject[i].setBackground(Color.WHITE);
				subject[i].setSelected(true);
				subject[i].addItemListener(new CheckBoxItemListener());
				subject[i].setFont(new Font("HY견고딕", Font.PLAIN, 15));
				jp1.add(subject[i]);
			}
			
			//cp패널: 막대도표로 구성	
			ChartPanel cp = new ChartPanel();
			cp.setLayout(null);
			add(cp, BorderLayout.CENTER);
			cp.setSize(600,400);
			cp.setLocation(0,0);
			
			//자바의 평균, 최고점, 최저점
			java_score[0]=new JLabel(Integer.toString(cp.javer));
			java_score[1]=new JLabel(Integer.toString(cp.jmax));
			java_score[2]=new JLabel(Integer.toString(cp.jmin));
			
			
			//C언어의 평균, 최고점, 최저점
			C_score[0]=new JLabel(Integer.toString(cp.caver));
			C_score[1]=new JLabel(Integer.toString(cp.cmax));
			C_score[2]=new JLabel(Integer.toString(cp.cmin));
			
			//파이썬의 평균, 최고점, 최저점
			python_score[0]=new JLabel(Integer.toString(cp.javer));
			python_score[1]=new JLabel(Integer.toString(cp.jmax));
			python_score[2]=new JLabel(Integer.toString(cp.jmin));
		
			//자바의 위치
			int[] java_y=new int[3];		
			java_y[0]=cp.javer;
			java_y[1]=cp.jmax;
			java_y[2]=cp.jmin;
			
			//C언어의 위치
			int[] C_y=new int[3];
			C_y[0]=cp.caver;
			C_y[1]=cp.cmax;
			C_y[2]=cp.cmin;
			
			//파이썬의 위치
			int[] python_y=new int[3];
			python_y[0]=cp.pyaver;
			python_y[1]=cp.pymax;
			python_y[2]=cp.pymin;
			
			//자바의 평균, 최고점, 최저점의 위치 부착
			for (int i=0;i<3;i++) {
				java_score[i].setSize(50,20);
				java_score[i].setLocation(150+i*40,380-java_y[i]*3);
				cp.add(java_score[i]);
			}
			//C언어의 평균, 최고점, 최저점의 위치 부착
			for(int i=0;i<3;i++) {
				C_score[i].setSize(50,20);
				C_score[i].setLocation(340+i*40,380-C_y[i]*3);
				cp.add(C_score[i]);
			}
			
			//파이썬언어의 평균, 최고점, 최저점의 위치 부착
			for(int i=0;i<3;i++) {
				python_score[i].setSize(50,20);
				python_score[i].setLocation(530+i*40,380-python_y[i]*3);
				cp.add(python_score[i]);
			}
			
		}
		
		//막대도표의 수치 레이블
		class CheckBoxItemListener implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					//자바 수치 표시
					if (e.getItem()==subject[0]) {
						for (int i=0;i<3;i++) {
							java_score[i].setVisible(true);
						}
					}
					//C언어 수치 표시
					else if (e.getItem()==subject[1]) {
						for (int i=0;i<3;i++) {
							C_score[i].setVisible(true);
						}
					}
					//파이썬 수치 표시
					else {
						for (int i=0;i<3;i++) {
							python_score[i].setVisible(true);
						}
					}
				}
				else {
					//자바 수치 표시안함
					if (e.getItem()==subject[0]) {
						for (int i=0;i<3;i++) {
							java_score[i].setVisible(false);
						}
					}
					//C언어 수치 표시안함
					else if (e.getItem()==subject[1]) {
						for (int i=0;i<3;i++) {
							C_score[i].setVisible(false);
						}
					}
					//파이썬 수치 표시안함
					else {
						for (int i=0;i<3;i++) {
							python_score[i].setVisible(false);
						}
					}
			   }
			}
		}
	}
	// CharPanel: 막대도표를 만드는 패널
	class ChartPanel extends JPanel {
		Vector<Integer> jstudent = new Vector<Integer>();
		Vector<Integer> cstudent = new Vector<Integer>();
		Vector<Integer> pystudent = new Vector<Integer>();

		// 과목별 평균과 최대점, 최소점을 담은 속성들
		int jsum, javer, jmax, jmin; 
		int csum, caver, cmax, cmin;
		int pysum, pyaver, pymax, pymin;

		// 차트 색 설정
		Color averageColor = Color.cyan;
		Color maxColor = Color.yellow;
		Color minColor = Color.LIGHT_GRAY;
		
		public ChartPanel() {
			makeChart();
			
			// 더블클릭하면 차트의 색을 바꾸는 마우스리스너
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount()==2) {
						  int r = (int)(Math.random()*256); // 0~255사이의 랜덤정수
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

			// 자바 점수 평균, 최대점, 최소점
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

			// C언어 점수  평균, 최대점, 최소점
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

			// 파이썬 점수  평균, 최대점, 최소점
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

		// 막대도표 그리기
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawLine(120, 400, 650, 400);

			for (int line = 1; line < 11; line++) { // 라인 10개
				g.drawString(line * 10 + "", 80, 405 - 30 * line);
				g.drawLine(120, 400 - 30 * line, 650, 400 - 30 * line);
			}
			g.drawLine(120, 80, 120, 400);

			g.setFont(new Font("고딕체", Font.BOLD, 15));
			g.drawString("자바", 180, 420);
			g.drawString("C언어", 360, 420);
			g.drawString("파이썬", 550, 420);

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
			
			// 평균, 최고점, 최저점 별로 색을 알려줌.
			g.setColor(averageColor); // 평균은 청록색
			g.fillRect(680, 190, 15, 15);
			g.setColor(Color.black);
			g.drawString("평균", 700, 200); 

			g.setColor(maxColor); // 최고점은 노랑색
			g.fillRect(680, 240, 15, 15);
			g.setColor(Color.black);
			g.drawString("최고점", 700, 250);

			g.setColor(minColor); // 최저점은 회색
			g.fillRect(680, 290, 15, 15);
			g.setColor(Color.black);
			g.drawString("최저점", 700, 300);
		}
	}
	
	// PiePanel: 과목별 파이차트를 받아 그리는 패널
	class PiePanel extends JPanel {
		private Pie[] pie=new Pie[3];
		
		PiePanel() {
			setLayout(null);
			setBackground(Color.white);
			//콤보박스
			String[] DEPT= {"컴퓨터학과", "수학과", "디지털미디어학과"};
			JComboBox<String> strCombo=new JComboBox<String>(DEPT);
			strCombo.setForeground(Color.black);
			strCombo.setFont(new Font("나눔고딕", Font.BOLD, 15));
			add(strCombo);
			strCombo.setSize(150,30);
			strCombo.setLocation(650,10);
			
			//각과목의 제목
			JPanel title_panel = new JPanel(new GridLayout(1, 3));
			title_panel.setSize(720,50);
			title_panel.setLocation(130,100);
			title_panel.setBackground(Color.WHITE);
			add(title_panel);
			JLabel title[]=new JLabel[3];
			title[0]=new JLabel("자바");
			title[1]=new JLabel("C언어");
			title[2]=new JLabel("파이썬");
			
			for (int i=0;i<title.length;i++) {
				title_panel.add(title[i]);
				title[i].setFont(new Font("Hy엽서L", Font.BOLD, 20));
			}
			
			//원그래프
			for (int i=0;i<3;i++) {
				pie[i]=new Pie(i);			
				add(pie[i]);
				pie[i].setSize(750, 200);
				pie[i].setLocation(50, 150);
	
			}
			pie[0].setVisible(true);//학과가 컴퓨터학과인 경우 파이차트
			pie[1].setVisible(false);//학과가 수학과인 경우 파이차트
			pie[2].setVisible(false);//학과가 디지털미디어학과인 경우 파이차트
			
			strCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> cb=(JComboBox<String>)e.getSource();
					int i=cb.getSelectedIndex();
					if (i==0) {
						pie[0].setVisible(true);//컴퓨터학과인 경우가 나타남
						pie[1].setVisible(false);
						pie[2].setVisible(false);
					}
					if (i==1) {
						pie[0].setVisible(false);
						pie[1].setVisible(true);//수학과인 경우가 나타남
						pie[2].setVisible(false);
					}
					if (i==2) {
						pie[0].setVisible(false);
						pie[1].setVisible(false);
						pie[2].setVisible(true);//디지털미디어학과인 경우가 나타남
					}
				}
			});
			
			// jp3: 학점을 구분해주는 색이 담긴 라벨과 색깔별로 학점을 나눈 라벨로 구성
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

			color[0].setBackground(Color.yellow); // A는 노랑색
			color[1].setBackground(Color.orange); // B는 주황색
			color[2].setBackground(Color.green); // C는 초록색
			color[3].setBackground(Color.CYAN);  // D는 청록색
			color[4].setBackground(Color.LIGHT_GRAY); // F는 회색
			for (int i = 0; i < 5; i++) {
				jp3.add(color[i]);
				jl[i].setFont(new Font("고딕체", Font.BOLD, 15));
				jp3.add(jl[i]);
			}
		}

	}
	// 자바의 학점 비율을 나타내는 원그래프
	class Pie extends JPanel {
		int jcount[] = { 0, 0, 0, 0, 0 }; // 학점별 개수를 가지는 배열 jcount
		double jrate[] = { 0, 0, 0, 0, 0 }; // 학점별 비율을 가지는 배열 jrate
		int jarcAngle[] = new int[5]; // 차트 그릴때 사용할 각도를 담는 jarcAngle

		int ccount[] = { 0, 0, 0, 0, 0 }; // 학점별 개수를 가지는 ccount
		double crate[] = { 0, 0, 0, 0, 0 }; // 학점별 비율을 가지는 ccount
		int carcAngle[] = new int[5]; // 차트 그릴때 사용할 각도를 담는 carcAngle

		int pycount[] = { 0, 0, 0, 0, 0 }; // 학점별 개수를 가지는 배열 pycount
		double pyrate[] = { 0, 0, 0, 0, 0 }; // 학점별 비율을 가지는 배열 pyrate
		int pyarcAngle[] = new int[5]; // 차트 그릴때 사용할 각도를 담는 pyarcAngle
		
		Color[] color = { Color.yellow, Color.orange, Color.green, Color.cyan, Color.LIGHT_GRAY };
		
		int index;//학과를 구분하기 위한 용도
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
			String dept[]= {"컴퓨터공학과", "수학과","디지털미디어학과"};
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
			
			// 자바 학점 비율
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
			for (int i = 0; i < 5; i++) { // 차트 그릴때 사용할 각도를 계산
				jarcAngle[i] = (int) Math.round(jrate[i] * 360);
			}
			
			// C언어 학점 비율
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
			for (int i = 0; i < 5; i++) { // 차트 그릴때 사용할 각도를 계산
				carcAngle[i] = (int) Math.round(crate[i] * 360);
			}
			
			// 파이썬 학점 비율
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
			for (int i = 0; i < 5; i++) { // 차트 그릴때 사용할 각도를 계산
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
				//자바 파이차트 그리기
				g.fillArc(0, 0, 200, 200, jstartAngle, jarcAngle[i]);
				jstartAngle = jstartAngle + jarcAngle[i];
				//C언어 파이차트 그리기
				g.fillArc(250, 0, 200, 200, cstartAngle, carcAngle[i]);
				cstartAngle = cstartAngle + carcAngle[i];
				//파이썬 파이차트 그리기
				g.fillArc(500, 0, 200, 200, pystartAngle, pyarcAngle[i]);
				pystartAngle = pystartAngle + pyarcAngle[i];
			}
		}
	}


	public static void main(String[] args) {
		new Main();
	}
}
