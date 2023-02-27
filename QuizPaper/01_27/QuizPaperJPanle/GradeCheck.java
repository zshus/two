package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GradeCheck extends JFrame {

	private int size;
	private JLabel lblLogout; // 로그아웃
	private JLabel lblTitle; // 상단제목

	private JLabel lblDateFirst; // 날짜순
	private JLabel lblScoreFirst; // 총점순

	private JLabel lblTestResult; // 시험결과 보기
	private JLabel lblRanking; // 순위 보기
	private JLabel lblReTest; // 재시험 보기
	private JLabel lblTest; // 시험 보기

	private JLabel lblDateUp; // 날짜↑
	private JLabel lblDateDown; // 날짜↓
	private JLabel lblScoreUp; // 점수↑
	private JLabel lblScoreDown; // 점수↓

	private JLabel[] tag1st; // 고득점 태그
	private Login login;

	private Font lblFont; // Label Font지정
	private JList<DidExam> infoList;
	private Vector<DidExam> didExamInfo;
	private DidExam didExam_best;
	private File path;
	private TestPaper testPaper;
	private TestResult testResult;
	private Vector<TopGrade> topList;
	private int clickedLocation;

	public GradeCheck(TestPaper testPaper, File path, Login login, TestResult testResult) {
		this.testPaper = testPaper;
		this.path = path;
		this.login = login;
		this.testResult = testResult;
		getTotalLoad();
	}

	public GradeCheck(File path, Login login) {
		this.path = path;
		this.login = login;
		getTotalLoad();

	}

	private void init() {
		lblLogout = new JLabel(new ImageIcon("img\\logout1.png"), JLabel.LEFT);
		lblTitle = new JLabel(login.getIdName() + "님: " + path.getName() + " 성적조회", JLabel.CENTER);
		lblTitle.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 33));

		lblDateFirst = new JLabel("날짜순 ");
		lblDateFirst.setFont(new Font("돋움체", Font.BOLD, 15));
		lblScoreFirst = new JLabel("점수순 ");
		lblScoreFirst.setFont(new Font("돋움체", Font.BOLD, 15));

		lblDateUp = new JLabel(getIcon("img\\up.png", 20, 30));
		lblDateDown = new JLabel(getIcon("img\\down.png", 20, 30));
		lblScoreUp = new JLabel(getIcon("img\\up.png", 20, 30));
		lblScoreDown = new JLabel(getIcon("img\\down.png", 20, 30));

		lblTestResult = new JLabel(getIcon("img\\시험결과 보기.png", 180, 40), JLabel.LEFT);
		lblReTest = new JLabel(getIcon("img\\재 시험 보기.png", 180, 40), JLabel.RIGHT);
		lblRanking = new JLabel(getIcon("img\\순위보기.png", 130, 40), JLabel.RIGHT);
		lblTest = new JLabel(getIcon("img\\시험보기.png", 145, 40), JLabel.RIGHT);
		lblFont = new Font("맑은고딕", Font.BOLD, 14);
	}

	private void setDisplay() {

		JPanel pnlNorth = new JPanel(new GridLayout(3, 1)); // 상단패널
		JPanel pnllogout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnllogout.setBackground(Color.white);
		JLabel lbltemp = new JLabel();
		lbltemp.setPreferredSize(new Dimension(50, 1));
		pnllogout.add(lbltemp);
		pnllogout.add(lblLogout);
		pnlNorth.add(pnllogout);
		pnlNorth.add(lblTitle);
		pnlNorth.setBackground(Color.WHITE);

		JPanel pnlCenter = new JPanel(new BorderLayout()); // 중앙패널

		JPanel pnlMainNorth = new JPanel(new BorderLayout());
		JPanel pnlMainNorthS = new JPanel();
		pnlMainNorthS.setBackground(Color.WHITE);
		JPanel pnlMainNorth1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlMainNorth1.add(lblDateFirst);
		pnlMainNorth1.add(lblDateUp);
		pnlMainNorth1.add(lblDateDown);
		pnlMainNorth1.setPreferredSize(new Dimension(530, 35));
		pnlMainNorth1.setBackground(Color.WHITE);
		JPanel pnlMainNorth2 = new JPanel(new FlowLayout());
		pnlMainNorth2.add(lblScoreFirst);
		pnlMainNorth2.add(lblScoreUp);
		pnlMainNorth2.add(lblScoreDown);
		pnlMainNorth2.setPreferredSize(new Dimension(300, 35));
		pnlMainNorth2.setBackground(Color.WHITE);
		pnlMainNorthS.add(new JLabel(""));
		pnlMainNorthS.add(pnlMainNorth1);
		pnlMainNorthS.add(pnlMainNorth2);
		pnlMainNorthS.setBorder(new LineBorder(Color.LIGHT_GRAY, 5));
		pnlMainNorth.setBackground(Color.WHITE);
		pnlMainNorth.add(pnlMainNorthS);
		JLabel lbla = new JLabel("");
		lbla.setBorder(new EmptyBorder(0, 0, 3, 0));
		pnlMainNorth.add(lbla, BorderLayout.SOUTH);

		infoList = new JList<DidExam>(didExamInfo);
		infoList.setCellRenderer(new MyListCellRender());
		infoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pnlCenter.add(pnlMainNorth, BorderLayout.NORTH);
		JScrollPane scrl = new JScrollPane(infoList);
		scrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrl.setAutoscrolls(true);
		pnlCenter.add(scrl, BorderLayout.CENTER);

		pnlCenter.setBorder(new EmptyBorder(0, 50, 0, 50));
		pnlCenter.setBackground(Color.WHITE);

		JPanel pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.WHITE);
		JPanel pnlBtns = new JPanel(new GridLayout(3, 2, 520, 0));
		pnlBtns.add(lblTestResult);
		pnlBtns.add(lblReTest);
		pnlBtns.add(lblRanking);
		pnlBtns.add(lblTest);
		pnlBtns.setBackground(Color.WHITE);

		pnlSouth.add(pnlBtns);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeWindow();
			}
		});

		MouseListener ml = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getSource() == lblRanking) {
					if (topList == null || topList.size() == 0) {
						JOptionPane.showMessageDialog(GradeCheck.this, "순위 정보는 아직 없습니다! 죄송합니다!");
					} else {
						ranking();
					}

				} else if (me.getSource() == lblTestResult) {
					if (testResult == null) {
						JOptionPane.showMessageDialog(GradeCheck.this, "먼저 시험을 보시고 결과를 확인하세요!");
					} else {
						testResult();
					}

				} else if (me.getSource() == lblLogout) {
					int choice = JOptionPane.showConfirmDialog(GradeCheck.this, "로그아웃 하시겠습니까?", "알람",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (choice == JOptionPane.OK_OPTION) {
						logout();
					}
				} else if (me.getSource() == lblReTest) {
					if (!infoList.isSelectionEmpty()) {
						retest();
					} else {
						JOptionPane.showMessageDialog(GradeCheck.this, "해당 시험을 선택한 후에 버튼을 눌러 주세요!");
					}

				} else if (me.getSource() == lblTest) {
					test();
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getSource() == lblDateUp) {
					dateUp();
					infoList.updateUI();
				} else if (me.getSource() == lblDateDown) {
					dateDown();
					infoList.updateUI();
				} else if (me.getSource() == lblScoreUp) {
					scoreUp();
					infoList.updateUI();
				} else if (me.getSource() == lblScoreDown) {
					scoreDown();
					infoList.updateUI();
				}
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				if (me.getSource() == lblLogout || me.getSource() == lblTestResult || me.getSource() == lblRanking
						|| me.getSource() == lblReTest || me.getSource() == lblTest || me.getSource() == lblDateUp
						|| me.getSource() == lblDateDown || me.getSource() == lblScoreUp
						|| me.getSource() == lblScoreDown) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (me.getSource() == lblLogout || me.getSource() == lblTestResult || me.getSource() == lblRanking
						|| me.getSource() == lblReTest || me.getSource() == lblTest || me.getSource() == lblDateUp
						|| me.getSource() == lblDateDown || me.getSource() == lblScoreUp
						|| me.getSource() == lblScoreDown) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		};
		lblLogout.addMouseListener(ml);
		lblTestResult.addMouseListener(ml);
		lblRanking.addMouseListener(ml);
		lblReTest.addMouseListener(ml);
		lblTest.addMouseListener(ml);
		lblDateUp.addMouseListener(ml);
		lblDateDown.addMouseListener(ml);
		lblScoreUp.addMouseListener(ml);
		lblScoreDown.addMouseListener(ml);
	}

	private void showFrame() {
		setTitle("성적조회");
		setSize(1090, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private ImageIcon getIcon(String img, int size1, int size2) {
		Image image = new ImageIcon(img).getImage().getScaledInstance(size1, size2, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}

	private void loadTops() {
		topList = new Vector<TopGrade>();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		boolean flag = true;

		try {
			fis = new FileInputStream("topLists.txt");
			ois = new ObjectInputStream(fis);
			topList = (Vector<TopGrade>) ois.readObject();
		} catch (EOFException eof) {
			flag = false;
		} catch (FileNotFoundException e) {
			File file = new File("topLists.txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

		finally {
			if (ois != null) {
				closeAll(ois, fis);
			}

		}
	}

	private void saveTopGrade() {
		boolean flag = true;		
		if (topList.size() != 0 || topList != null) {
			for (TopGrade top : topList) {
				double d = getAllScore_best();
				if (top.getId().equals(login.getIdName())) {
					if (top.getTotalScore() <= d) {
						top.setTotalScore(d);
						top.setDay(didExam_best.getTestDay());
						top.setSpendedTime(didExam_best.getTestTime());
						flag = false;
						return;
					}
				}
			}

		}
		if (flag) {
			topList.add(new TopGrade(login.getIdName(), didExam_best.getTestDay(), getAllScore_best(),
					didExam_best.getTestTime()));
		}
	}

	private void dateUp() {
		didExamInfo.sort((o1, o2) -> o2.getTestDay().compareTo(o1.getTestDay()));
	}

	private void dateDown() {
		didExamInfo.sort((o1, o2) -> o1.getTestDay().compareTo(o2.getTestDay()));
	}

	private void scoreUp() {
		didExamInfo.sort((DidExam o1, DidExam o2) -> getScoreSum(o2).compareTo(getScoreSum(o1)));

	}

	private void scoreDown() {
		didExamInfo.sort((DidExam o1, DidExam o2) -> getScoreSum(o1).compareTo(getScoreSum(o2)));

	}

	private Double getScoreSum(DidExam d) {
		Double sum = 0.0;
		for (DidSubject sub : d.getSubjectList()) {
			sum += Double.valueOf(sub.getGotScore());
		}
		return sum;
	}

	private double getAllScore_best() {
		double all = 0;
		for (DidSubject sub : didExam_best.getSubjectList()) {
			all += Double.valueOf(sub.getGotScore());
		}
		return all;
	}

	class MyListCellRender extends DefaultListCellRenderer {
		
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnl.setBorder(new LineBorder(Color.LIGHT_GRAY));
			pnl.setBackground(new Color(196, 222, 255));
			DidExam exam = (DidExam) value;
			JLabel lbldataimg = new JLabel(GradeCheck.this.getIcon("img\\date.png", 50, 50));
			lbldataimg.setPreferredSize(new Dimension(60, 50));
			pnl.add(lbldataimg);
			JLabel lbltestday = new JLabel(exam.getTestDay());
			lbltestday.setFont(lblFont);
			lbltestday.setPreferredSize(new Dimension(100, 50));
			pnl.add(lbltestday);
			Vector<DidSubject> subject = exam.getSubjectList();
			double sum = 0;
			JPanel pnlScore = new JPanel(new FlowLayout(FlowLayout.LEFT));				
			if(size==0||size<subject.size()*120) {				
				size=subject.size()*120;
				if(size<500) {
					size=500;
				}				
			}			
			pnlScore.setPreferredSize(new Dimension(size, 50));
			pnlScore.setBorder(new EmptyBorder(12, 0, 0, 0));
			pnlScore.setBackground(new Color(196, 222, 255));
			for (int i = 0; i < subject.size(); i++) {
				DidSubject sub = subject.get(i);
				pnlScore.add(new JLabel(sub.getSubName() + ": "));
				pnlScore.add(new JLabel(sub.getGotScore() + "      "));
				sum += Double.valueOf(sub.getGotScore());
			}

			pnl.add(pnlScore);
			JPanel pnlSum = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnlSum.setPreferredSize(new Dimension(100, 50));
			pnlSum.setBorder(new EmptyBorder(12, 0, 0, 0));
			pnlSum.setBackground(new Color(196, 222, 255));
			pnlSum.add(new JLabel("||  총점: "));
			pnlSum.add(new JLabel(String.valueOf(sum)));
			pnl.add(pnlSum);
			JPanel pnlClock = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnlClock.setPreferredSize(new Dimension(100, 50));
			pnlClock.setBorder(new EmptyBorder(8, 0, 0, 0));
			pnlClock.setBackground(new Color(196, 222, 255));
			pnlClock.add(new JLabel(GradeCheck.this.getIcon("img\\clock.png", 30, 30)));
			pnlClock.add(new JLabel("   " + exam.getTestTime()));
			pnl.add(pnlClock);
			if (exam.getTestDay().equals(didExam_best.getTestDay()) && sum == getAllScore_best()
					&& exam.getTestTime().equals(didExam_best.getTestTime())) {
				pnl.add(new JLabel(GradeCheck.this.getIcon("img\\first.png", 50, 50)));
			}			
			if(clickedLocation>0) {
				if(!infoList.isSelectionEmpty()) {
					infoList.clearSelection();
				}
				isSelected=false;
			}		
			if (isSelected) {
				pnl.setBorder(new LineBorder(Color.YELLOW, 2));
			}
			if (sum < 60) {
				pnl.setBackground(Color.LIGHT_GRAY);
				pnlScore.setBackground(Color.LIGHT_GRAY);
				pnlSum.setBackground(Color.LIGHT_GRAY);
				pnlClock.setBackground(Color.LIGHT_GRAY);
			}			
			return pnl;

		}

	}

	private void sortExamInfo() {
		didExamInfo.sort(new Comparator<DidExam>() {
			@Override
			public int compare(DidExam o1, DidExam o2) {
				int num = getScoreSum(o2).compareTo(getScoreSum(o1));
				num = num == 0 ? o1.getTestTime().compareTo(o2.getTestTime()) : num;
				num = num == 0 ? o2.getTestDay().compareTo(o1.getTestDay()) : num;
				return num;
			}

		});

	}

	public void getSaveDataAllAndLogout() {
		login.saveDidExam();
		login.getSave();
		login.setVisible(true);
	}

	private void saveTops() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream("topLists.txt");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(topList);
			oos.flush();
			oos.reset();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			closeAll(oos, fos);
		}
	}

	private void closeAll(Closeable... c) {
		for (Closeable temp : c) {
			try {
				temp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public Vector<TopGrade> getTopList() {	
		return topList;
	}

	private void getTotalLoad() {
		topList = null;
		loadTops();
		didExamInfo = getCurrentExams();
		sortExamInfo();
		init();
		didExam_best = didExamInfo.get(0);
		saveTopGrade();
		setDisplay();
		addListeners();
		clearSeleted();
		showFrame();
	}

	private void test() {
		TestResult.subNums = null;
		TestResult.oldNum = 0;
		login.setDidExamName(new DidExam(null, null, new Vector<DidSubject>(), null, null, null));
		saveTops();
		new QuizFile(path, login);
		dispose();
	}

	private void retest() {
		DidExam selectedExam = infoList.getSelectedValue();
		List<String> list = selectedExam.getSelsetedList();
		TestResult.oldNum = 0;
		login.setDidExamName(new DidExam(selectedExam.getPath(), selectedExam.getTestName(), new Vector<DidSubject>(),
				null, null, null));
		saveTops();
		new TestPaper(selectedExam.getPath(), login, list);
		dispose();
	}

	private void logout() {
		saveTops();
		getSaveDataAllAndLogout();
		TestResult.subNums = null;
		login.setDidExamName(new DidExam(null, null, new Vector<DidSubject>(), null, null, null));
		dispose();
	}

	private void ranking() {
		int num = checkIdx();
		if (num <= 4) {
			dispose();			
			JOptionPane.showMessageDialog(null, "축하합니다! 당신은 " + (num + 1) + "등입니다!");
		} else {

			JOptionPane.showMessageDialog(null, "아쉽지만, 조금 더 노력하세요!");
		}
		new RankResult(GradeCheck.this);
	}

	private void testResult() {
		login.getdidExamInfo().remove(login.getdidExamInfo().size() - 1);
		saveTops();
		testResult.setVisible(true);
		dispose();
	}

	private int checkIdx() {
		int num = 10;
		for (int i = 0; i < topList.size(); i++) {
			if (topList.get(i).getId().equals(login.getIdName()) && i <= 4) {
				return i;
			}
		}
		return num;
	}

	private void closeWindow() {
		int choice = JOptionPane.showConfirmDialog(this, "시험 연습장을 종료하시겠습니까?", "알람", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.OK_OPTION) {
			login.getSave();
			login.saveDidExam();
			saveTops();
			System.exit(0);
		}
	}
	
	private void clearSeleted() {		
		infoList.addMouseListener(new MouseAdapter() {								
			@Override
			public void mousePressed(MouseEvent e) {
				Rectangle r=infoList.getCellBounds(infoList.getLastVisibleIndex(),infoList.getLastVisibleIndex());				
				int listY=r.height+r.y;
				int mouseY=e.getY();
				clickedLocation=mouseY-listY;
				if(mouseY>listY) {
					infoList.clearSelection();
				}
			}

		});	
}

}
