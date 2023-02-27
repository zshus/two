package ke.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import QuizMaker.Quiz;




public class TestResult extends JFrame {
	public static int oldNum;
	private Login login;
	private JLabel lblLogout; // 로그아웃 버튼
	private JLabel lblDate; // 시험날짜
	private JLabel lblTitle; // 상단 xxx님의 점수 (사용자의 이름을 나타내기 위한 Label)
	private JLabel lblScore; // 상단 내 점수 (사용자의 Total점수를 나타내기 위한 Label)
	private JLabel lblBell; // 종아이콘

	private JLabel lblOldScore;

	public static String[] subNums;
	private JLabel[] subNum; // 1과목~5과목 Label
	private JLabel[] imgTest; // 종이, 펜있는 이미지 Label
	private JLabel[] myScores; // 과목별 점수 Label
	private JLabel[] checkWrong; // 오답확인 Label
	private JLabel[] quizSolve; // 문제풀이 Label

	private JLabel lblSelectSubject; // 과목선택
	private JLabel lblReTest; // 재시험보기
	private JLabel lblTestResult; // 성적조회

	private Vector<QuizAnswer[]> quizAnswerList;
	private Vector<Vector<Quiz>> quizListAll;
	private int subjectNums;

	private double[] subjectScore;
	private double[] myScore;

	private File p;
	private TestPaper testPaper;
//	private DidExam didExam;
	public static DidExam didExam;
	private Vector<DidSubject> didSubjects;	
	int ansidx=0;

	public TestResult(TestPaper testPaper, File p, Login login) {
		this.testPaper = testPaper;
		this.p = p;
		this.login = login;

		getData();
		getCorrectAnswers();
		sortDidExam();
		getSubjectName();
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	public TestResult(TestPaper testPaper, File p, Login login, boolean b) {
		if (b) {
			this.testPaper = testPaper;
			this.p = p;
			this.login = login;
			getData();
			getCorrectAnswers();
			sortDidExam();
			getSubjectName();
			init();
			setDisplay();
			addListeners();
		}

	}

	private void init() {
		lblLogout = new JLabel(new ImageIcon("img\\logout1.png"), JLabel.LEFT);
		lblTitle = new JLabel(login.getIdName() + "님의 점수", JLabel.CENTER);
		lblTitle.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 40));
		lblTitle.setForeground(Color.GRAY);
		BigDecimal lblScoreString = getgotScoreSum();
		lblScore = new JLabel(String.valueOf(lblScoreString), JLabel.CENTER);
		lblScore.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 40));
		lblScore.setForeground(Color.RED);		
		lblBell = new JLabel(new ImageIcon("img\\timeover.png"));
				
		BigDecimal lblOldScoreString = getsubScoreSum();
		lblOldScore = new JLabel("/" + lblOldScoreString, JLabel.CENTER);
		lblOldScore.setFont(new Font("맑은고딕", Font.BOLD, 40));
		String testDate = getTestDate();

		lblDate = new JLabel();
		lblDate.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		lblDate.setText(testDate);

		subNum = new JLabel[subNums.length];
		imgTest = new JLabel[subNums.length];
		myScores = new JLabel[subNums.length];
		checkWrong = new JLabel[subNums.length];
		quizSolve = new JLabel[subNums.length];

		lblSelectSubject = new JLabel(new ImageIcon("img\\과목 선택1.png"));
		lblReTest = new JLabel(new ImageIcon("img\\재 시험 보기2.png"));
		lblTestResult = new JLabel(new ImageIcon("img\\성적 조회1.png"));

	}

	private void setDisplay() {
		JLabel lbltemp1 = new JLabel("   ");
		JLabel lbltemp2 = new JLabel("   ");
		lbltemp1.setOpaque(true);
		lbltemp2.setOpaque(true);
		lbltemp1.setBackground(Color.WHITE);
		lbltemp2.setBackground(Color.WHITE);
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setBackground(Color.WHITE);
		JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlInfo.add(lblLogout);
		pnlInfo.add(new JLabel(" "));
		pnlInfo.add(lblDate);
		pnlInfo.setBackground(Color.WHITE);

		JPanel pnlInfo2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		
		if(testPaper.getTheTimeStatus()) {
			pnlInfo2.add(lblBell);					
		}else {
			pnlInfo2.add(new JLabel());	
		}
		
		pnlInfo2.add(new JLabel("    "));
		pnlInfo2.add(lblScore);
		pnlInfo2.add(lblOldScore);
		pnlInfo2.setBackground(Color.WHITE);

		pnlNorth.add(pnlInfo, BorderLayout.NORTH);
		pnlNorth.add(lblTitle, BorderLayout.CENTER);
		pnlNorth.add(pnlInfo2, BorderLayout.SOUTH);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < subNums.length; i++) {
			JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
			subNum[i] = new JLabel("    " + (i + 1) + ". " + subNums[i], JLabel.LEFT);
			subNum[i].setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 17));
			pnl.add(subNum[i]);
			subNum[i].setPreferredSize(new Dimension(300, 50));

			imgTest[i] = new JLabel(new ImageIcon("img\\test1.png"));
			pnl.add(imgTest[i]);
			String subScore = didSubjects.get(i).getGotScore().equals("0") ? "0.0" : didSubjects.get(i).getGotScore();
			String totalScore = didSubjects.get(i).getSubScore().equals("100") ? "100.0"
					: didSubjects.get(i).getSubScore();
			myScores[i] = new JLabel(subScore + "/" + totalScore);
			myScores[i].setFont(new Font("맑은고딕", Font.BOLD, 20));
			myScores[i].setPreferredSize(new Dimension(150, 50));
			pnl.add(myScores[i]);
			pnl.add(new JLabel("    "));
			checkWrong[i] = new JLabel(new ImageIcon("img\\wrongCheck.png"), JLabel.RIGHT);
			checkWrong[i].setToolTipText("누르면 틀린 문제들을 확인이 가능합니다!");
			checkWrong[i].setBorder(new LineBorder(Color.WHITE));
			pnl.add(checkWrong[i]);
			pnl.add(new JLabel("    "));
			quizSolve[i] = new JLabel(new ImageIcon("img\\quizSolve.png"), JLabel.RIGHT);
			quizSolve[i].setToolTipText("누르면 해당 과목의 문제 풀이를 확인 가능합니다!");
			quizSolve[i].setBorder(new LineBorder(Color.WHITE));
			pnl.add(quizSolve[i]);
			pnl.setBackground(Color.WHITE);
			pnlCenter.add(pnl);
		}
		pnlCenter.setBorder(new EmptyBorder(0, 100, 0, 100));
		pnlCenter.setBorder(new LineBorder(Color.BLACK));
		pnlCenter.setBackground(Color.WHITE);

		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
		pnlSouth.add(lblSelectSubject);
		pnlSouth.add(lblReTest);
		pnlSouth.add(lblTestResult);
		pnlSouth.setBackground(Color.WHITE);
		add(lbltemp1, BorderLayout.WEST);
		add(lbltemp2, BorderLayout.EAST);
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

	}

	private void addListeners() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getSource() == lblLogout) {
					int choice = JOptionPane.showConfirmDialog(TestResult.this, "로그아웃 하시겠습니까?", "알람",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (choice == JOptionPane.OK_OPTION) {
						getSaveDataAllAndLogout();
						login.setVisible(true);
						subNums = null;
						getReset();
						dispose();
					}

				} else if (me.getSource() == lblSelectSubject) {
					oldNum = didExam.getSubjectList().size();
					if(oldNum==QuizFile.subjectNums) {
						JOptionPane.showMessageDialog(TestResult.this, "선택할 수 있는 과목이 없습니다!");
						return;
					}
					new QuizFile(p, login);
					dispose();
				} else if (me.getSource() == lblReTest) {
					login.savedidExamInfo(didExam);
					didExam=null;
					oldNum = 0;
					subNums = null;
					getReset();
					new QuizFile(p, login);
					dispose();
				} else if (me.getSource() == lblTestResult) {
					login.savedidExamInfo(didExam);
					new GradeCheck(testPaper, p, login, TestResult.this);
					dispose();
				} else {

					for (int i = 0; i < quizSolve.length; i++) {
						if (me.getSource() == quizSolve[i]) {
							new QuizSolve(TestResult.this, p, getDidsubjectSolv(i));
							quizSolve[i].setBorder(new LineBorder(Color.WHITE));
							dispose();
						}
					}

					for (int i = 0; i < checkWrong.length; i++) {
						if (me.getSource() == checkWrong[i]) {
							if (getWrongItems(i).size() > 0) {
								ansidx=i;
								new WrongCheck(getWrongItems(i), TestResult.this, p, getDidsubjectSolv(i));
								checkWrong[i].setBorder(new LineBorder(Color.WHITE));
								dispose();
							} else {
								JOptionPane.showMessageDialog(TestResult.this, "만점을 축합니다!\n 오답이 없습니다!");
							}

						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				if (me.getSource() == lblLogout || me.getSource() == lblSelectSubject || me.getSource() == lblReTest
						|| me.getSource() == lblTestResult) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {

					for (int i = 0; i < quizSolve.length; i++) {
						if (me.getSource() == quizSolve[i]) {
							quizSolve[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
						} else if (me.getSource() == checkWrong[i]) {
							checkWrong[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
						}
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (me.getSource() == lblLogout || me.getSource() == lblSelectSubject || me.getSource() == lblReTest
						|| me.getSource() == lblTestResult) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				} else {
					for (int i = 0; i < quizSolve.length; i++) {
						if (me.getSource() == quizSolve[i]) {
							quizSolve[i].setBorder(new LineBorder(Color.WHITE));
						} else if (me.getSource() == checkWrong[i]) {
							checkWrong[i].setBorder(new LineBorder(Color.WHITE));
						}
					}
				}
			}
		};
		lblLogout.addMouseListener(ml);
		lblSelectSubject.addMouseListener(ml);
		lblReTest.addMouseListener(ml);
		lblTestResult.addMouseListener(ml);

		for (int i = 0; i < quizSolve.length; i++) {
			quizSolve[i].addMouseListener(ml);
		}
		for (int i = 0; i < checkWrong.length; i++) {
			checkWrong[i].addMouseListener(ml);
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeWindow();

			}
		});
	}

	private void showFrame() {
		setTitle("시험결과");
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void closeWindow() {
		int choice = JOptionPane.showConfirmDialog(this, "시험 연습장을 종료하시겠습니까?", "알람", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.OK_OPTION) {
			getSaveDataAllAndLogout();
			System.exit(0);
		}
	}

	public static String[] getSubjectNames() {
		String[] names = subNums;
		return names;
	}

	public void getSaveDataAllAndLogout() {
		login.savedidExamInfo(didExam);
		login.saveDidExam();
		login.getSave();

	}

//	private String getDidsubjectSolv(int num) {
//		return testPaper.getSelectedList().get(num);
//	}
	
	private String getDidsubjectSolv(int num) {		
		didExam.getSelsetedList().sort((o1,o2) -> o1.compareTo(o2));		
		return didExam.getSelsetedList().get(num);
		
	}

	public static String getTestDate() {
		Calendar today = Calendar.getInstance();
		int mouth = today.get((Calendar.MONTH));
		String mouthStr = String.valueOf(mouth + 1);
		if (mouth <= 10) {
			mouthStr = "0" + mouthStr;
		}
		int day = today.get(Calendar.DAY_OF_MONTH);
		String datstr = String.valueOf(day);
		if (day <= 9) {
			datstr = "0" + datstr;
		}
		String testDatet = today.get(Calendar.YEAR) + "-" + mouthStr + "-" + datstr;
		return testDatet;
	}

	private Vector<Integer> getWrongItems(int n) {
		Vector<Integer> vec = login.getDidExamName().getSubjectList().get(n).getWorngTestItems();
		return vec;
	}

	private void getReset() {
		login.setDidExamName(new DidExam(null, null, new Vector<DidSubject>(), null, null, null));
	}

	private void getData() {
		didExam = login.getDidExamName();
		didSubjects = didExam.getSubjectList();
		quizAnswerList = testPaper.getQuizAnswerList();
		quizListAll = testPaper.getQuizListAll();
		subjectNums = quizAnswerList.size();
		subjectScore = new double[subjectNums];
		myScore = new double[subjectNums];
		subNums = new String[didSubjects.size()];
	}

	private void sortDidExam() {
		Vector<DidSubject> didSubList = didExam.getSubjectList();
		didSubList.sort((DidSubject o1, DidSubject o2) -> o1.getSubName().compareTo(o2.getSubName()));
	}

	private void getSubjectName() {
		for (int i = 0; i < didSubjects.size(); i++) {
			subNums[i] = didSubjects.get(i).getSubName();
		}
	}

	private String getfileName(String s) {
		int idx = s.lastIndexOf(".");
		if (idx >= 0) {
			String subStr = s.substring(0, idx);
			return subStr;
		} else {
			return s;
		}

	}

	private void getCorrectAnswers() {
		Vector<DidSubject> subject_temp = didExam.getSubjectList();
		int n = 0;
		for (int i = 0; i < subjectNums; i++) {
			Vector<Quiz> quizList = quizListAll.get(i);
			QuizAnswer[] quizAnswer = quizAnswerList.get(i);
			if (oldNum != 0) {
				n = oldNum;
				oldNum = 0;
			}
			DidSubject didsubject = subject_temp.get(n);
			n++;
			Vector<Integer> worngTest = didsubject.getWorngTestItems();
			BigDecimal temScore = new BigDecimal(0.0);
			for (int j = 0; j < quizList.size(); j++) {
				Quiz quiz = quizList.get(j);
				int[] answer = quiz.getAnswer();				
				QuizAnswer temp = quizAnswer[j];
				Vector<Integer> tempAnswer = temp.getAnswer();				
				subjectScore[i] += quiz.getScore();

				if (checkAnswer(answer, tempAnswer)) {
					temScore = temScore.add(new BigDecimal(quiz.getScore()));
					for (int k = worngTest.size() - 1; k >= 0; k--) {
						if (worngTest.get(k) == j + 1) {
							int nu = worngTest.remove(k);
						}
					}
				}
			}
			myScore[i] = temScore.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			didsubject.setSubScore(String.valueOf(subjectScore[i]));
			didsubject.setGotScore(String.valueOf(myScore[i]));

		}
	}

	private boolean checkAnswer(int[] answer, Vector<Integer> tempAnswer) {
		boolean flag = false;
		int count = 0;
		for (int k = 0; k < tempAnswer.size(); k++) {
			int tempMyAnswer = tempAnswer.get(k);
			for (int j = 0; j < answer.length; j++) {
				if (tempMyAnswer == answer[j]) {
					count++;
					break;
				}
			}
		}
		if (count == answer.length) {
			flag = true;
		}
		return flag;
	}

	private BigDecimal getsubScoreSum() {
		BigDecimal bd = new BigDecimal(0.0);
		Vector<DidSubject> didSubList = didExam.getSubjectList();
		for (DidSubject didSub : didSubList) {
			String s = didSub.getSubScore();
			double d = Double.valueOf(s);
			bd = bd.add(new BigDecimal(d));
		}
		return bd.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	private BigDecimal getgotScoreSum() {
		BigDecimal bd = new BigDecimal(0.0);
		Vector<DidSubject> didSubList = didExam.getSubjectList();
		for (DidSubject didSub : didSubList) {
			String s = didSub.getGotScore();
			double d = Double.valueOf(s);
			bd = bd.add(new BigDecimal(d));
		}
		return bd.setScale(1, BigDecimal.ROUND_HALF_UP);
	}
	
	public QuizAnswer[] getQuizAnswerList() {
		Vector<DidSubject> didSubList = didExam.getSubjectList();
		return didSubList.get(ansidx).getAnswers();
	}

}
