package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public class QuizFile extends JFrame {
	public static int subjectNums;
	private JLabel testName;
	private JList<String> list;
	private JLabel testPlaying;
	private JLabel gradCheck;
	private Vector<String> vecList;
	private Vector<String> vecBox;
	private JComboBox<String> comBox;
	private JLabel waCheck;
	private File path;
	private Login login;

	private JList<String> subs;
	private JPanel pnljlist;
	private JScrollPane scl;
	private QuizFolder quizFolder;
	private Vector<String> vecsubs;
	private QuizAnswer[] quizAnswerArr;
	private Vector<DidExam> didExamInfo;
	private String strSeletedItemName;

	public QuizFile(File path, Login login) {
		this.path = path;
		this.login = login;		
		goON();
	}	

	private void init() {
		vecList = new Vector<String>();
		getFileName(path);
		subjectNums=vecList.size();		
		removeHasTestItem();
		testName = new JLabel(path.getName(), JLabel.CENTER);
		testName.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 40));
		testName.setForeground(Color.GRAY);
		testName.setPreferredSize(new Dimension(100, 80));
		list = new JList<String>(vecList);
		list.setPrototypeCellValue("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		testPlaying = new JLabel(new ImageIcon("img\\testPlaying1.png"));
		gradCheck = new JLabel(new ImageIcon("img\\gradCheck2.png"));
		vecBox = new Vector<String>();
		vecBox.add("-시험 선택-");
		for (DidExam exam : getCurrentExam()) {
			if (!vecBox.contains(exam.getTestDay())) {
				vecBox.add(exam.getTestDay());
			}
		}
		comBox = new JComboBox<String>(vecBox);
		comBox.setPreferredSize(new Dimension(157, 25));
		vecsubs = new Vector<String>();
		subs = new JList<String>(vecsubs);
		waCheck = new JLabel(new ImageIcon("img\\waCheck1.png"));
	}

	private void setDisplay() {
		add(testName, BorderLayout.NORTH);
		JPanel pnlCenter = new JPanel();
		JScrollPane scrl = new JScrollPane(list);
		scrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrl.setAutoscrolls(true);

		pnlCenter.add(scrl);
		add(pnlCenter, BorderLayout.CENTER);
		JPanel pnlSouth = new JPanel();
		JPanel pnlbtn = new JPanel(new GridLayout(0, 2));
		pnlbtn.add(testPlaying);
		pnlbtn.add(gradCheck);

		JPanel pnlListBtn = new JPanel();
		pnlListBtn.add(comBox);
		pnlbtn.add(pnlListBtn);
		pnlbtn.add(waCheck);

		JPanel pnlbox = new JPanel(new BorderLayout());
		pnlbox.add(pnlbtn, BorderLayout.NORTH);
		pnljlist = new JPanel(new FlowLayout(FlowLayout.LEFT));
		scl = new JScrollPane(subs);
		scl.setPreferredSize(new Dimension(150, 60));
		scl.setVisible(false);
		pnljlist.add(scl);
		pnlbox.add(pnljlist, BorderLayout.CENTER);
		pnlSouth.add(pnlbox);

		add(pnlSouth, BorderLayout.SOUTH);

	}

	private void addListener() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getSource() == testPlaying) {
					showTestPaper();					
				} else if (e.getSource() == gradCheck) {
					showGradCheck();
				} else if (e.getSource() == waCheck) {
					showWrongCheck();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == testPlaying || e.getSource() == gradCheck || e.getSource() == waCheck) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource() == testPlaying || e.getSource() == gradCheck || e.getSource() == waCheck) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		};
		testPlaying.addMouseListener(ml);
		gradCheck.addMouseListener(ml);
		waCheck.addMouseListener(ml);
		
		comBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scl.setVisible(false);
				vecsubs=new Vector<>();
				vecsubs = getSubList((String) comBox.getSelectedItem());				
				strSeletedItemName = (String) comBox.getSelectedItem();
				if (!comBox.getSelectedItem().equals("-시험 선택-")) {
					subs = new JList<String>(vecsubs);					
					scl = new JScrollPane(subs);
					scl.setPreferredSize(new Dimension(150, 60));
					pnljlist.add(scl);
				}
				pack();

			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeWindow();
			}
		});
	
	}

	private void showFrame() {
		pack();
		setResizable(false);
		setTitle("QuizFile");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	private Vector<String> getSubList(String s) {
		Vector<String> vecSub = new Vector<String>();		
		for (DidExam exam : getCurrentExam()) {		
			if (exam.getTestDay().equals(s)) {
				for (DidSubject sub : exam.getSubjectList()) {					
					if(sub.getWorngTestItems()!=null&&sub.getWorngTestItems().size()!=0) {
						vecSub.add(sub.getSubName());						
					}
	
				}
			}
		}
		return vecSub;
	}

	private DidSubject getSelectedDidSub(int n) {
		DidSubject selesub = null;
		int count = 0;
		for (DidExam em : getCurrentExam()) {
			if (em.getTestDay().equals(strSeletedItemName)) {
				for (DidSubject sub : em.getSubjectList()) {
					if (count == n) {
						selesub = sub;
						return sub;
					}
					count++;
				}
			}
		}
		return selesub;
	}

	private void closeWindow() {
		int choice = JOptionPane.showConfirmDialog(QuizFile.this, "전 페이지로 돌아가겠습니까?", "종료", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			new QuizFolder(login);
			dispose();
		}
	}

	private void showTestPaper() {
		List<String> subList = list.getSelectedValuesList();
		if (subList.size() == 0 || subList == null) {
			JOptionPane.showMessageDialog(this, "치고 싶은 과목을 선택해 주세요!");
		} else {
			int num=JOptionPane.showConfirmDialog(QuizFile.this, "시험 시작합니다!","알림",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
			if(num==JOptionPane.YES_OPTION) {
				new TestPaper(path, login, subList);
				dispose();
			}
			
		}
	}

	private void showGradCheck() {
		didExamInfo=getCurrentExams();
		if(didExamInfo==null|| didExamInfo.size()==0||TestResult.oldNum!=0) {
			JOptionPane.showConfirmDialog(this, "시험 진행하시고 성적 조회하세요!", "알림", JOptionPane.DEFAULT_OPTION);
		}else {
			new GradeCheck(path, login);
			dispose();
		}
		
	}

	private void showWrongCheck() {
		if (getSelectedDidSub(subs.getSelectedIndex())==null||((String)comBox.getSelectedItem()).equals("-시험 선택-")) {
			JOptionPane.showMessageDialog(QuizFile.this, "오답 확인할 날짜을 선택하세요!");				
		} else {
			if( subs.isSelectionEmpty()) {  
				JOptionPane.showMessageDialog(QuizFile.this, "아래 해당과목을 선택하세요!");			
			}else {
				String seletItem = subs.getSelectedValue();
				String strName = null;
				for (String s : vecList) {
					String name = getName(s);
					if (name.equals(seletItem)) {
						strName = s;
					}
				}
				int idx = subs.getSelectedIndex();
				Vector<Integer> wrongItemsIntegers = getSelectedDidSub(idx).getWorngTestItems();
				quizAnswerArr=getSelectedDidSub(idx).getAnswers();				
				if (wrongItemsIntegers == null || wrongItemsIntegers.size() == 0) {
					JOptionPane.showMessageDialog(QuizFile.this, "만점을 축합니다!\n 오답이 없습니다!");
				} else {
					new WrongCheck(wrongItemsIntegers, this, path, strName);
					dispose();
				}
			}
			
			
			
		}
	}

	private Vector<DidExam> getCurrentExam() {
		Vector<DidExam> currentExam = new Vector<DidExam>();
		for (DidExam e : login.getdidExamInfo()) {
			if (e.getTestName().equals(path.getName())) {
				for(int i=0;i<e.getSubjectList().size();i++) {
					if(e.getSubjectList().get(i).getWorngTestItems()!=null&&e.getSubjectList().get(i).getWorngTestItems().size()!=0 ) {
						currentExam.add(e);
						break;
					}
				}				
				
			}
		}
		return currentExam;
	}
	public Login getLogin() {
		return login;
	}

	private void removeHasTestItem() {
		String[] testItems = TestResult.getSubjectNames();
		if (testItems != null) {
			for (int i = vecList.size() - 1; i >= 0; i--) {
				String s1 = vecList.get(i);
				for (String s2 : testItems) {
					if (getName(s1).equals(s2)) {
						vecList.removeElement(s1);
					}
				}
			}
		}
	}

	private void getFileName(File path) {
		if (!path.isFile()) {
			File[] files = path.listFiles();
			for (File f : files) {
				getFileName(f);
			}
		} else {
			String name = path.getName();
			vecList.add(name);
		}
	}
	
	private Vector<DidExam> getCurrentExams() {
		String currenName = path.getName();
		Vector<DidExam> vec = new Vector<DidExam>();
		for (DidExam e : login.getdidExamInfo()) {
			if (e.getTestName().equals(currenName)) {
				vec.add(e);
			}
		}
		return vec;
	}

	private String getName(String s) {
		int idx = s.lastIndexOf(".");
		if (idx >= 0) {
			String subStr = s.substring(0, idx);
			return subStr;
		} else {
			return s;
		}

	}
	
	private void goON() {
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	public QuizAnswer[] getQuizAnswerList() {
		return quizAnswerArr;
	}

}
