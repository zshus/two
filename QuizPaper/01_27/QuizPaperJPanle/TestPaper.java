package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import QuizMaker.Quiz;
import QuizMaker.Subject;

public class TestPaper extends JFrame {

	private Subject subject;
	private String testTime;
	private JLabel titlename;
	private String path;
	private JPanel pnlRight;
	private JPanel pnlCenter;
	private JLabel[] lblNum;
	private JLabel lblLeft;
	private JLabel lblRight;
	private JLabel testTimeCheck;
	private JLabel timeStop;
	private JLabel sbumit;
	private JPanel pnlMain;
	private JPanel pnlPro;
	private JLabel lblLogout;
	private List<String> selectedList;

	private JLabel[] lblSubject;

	private JLabel[] lblOptions;
	private Vector<Quiz> quizList;

	private Vector<Vector<Quiz>> quizListAll;
	private String currentStr = "1";
	private TestTime timer;
	private Vector<HashMap<JLabel, Integer>> item;

	private String titleString;

	private int subjectIdx;

	private QuizAnswer[] quizAnswer;
	private Vector<QuizAnswer[]> quizAnswerList;

	private int optionsLen;
	private Login login;
	private File p;

	private Vector<DidSubject> didSubjts;
	private DidExam didExamName;
	private Vector<Integer> worngTestItems;
	private String subScore;
	
	private JLabel lblDef; 
	private JLabel lblPlus1;
	private JLabel lblPlus2;
	public static final int DEF = 100;
	public static final int PLUS1 = 150;
	public static final int PLUS2 = 200;
	private Properties pro;
	private String[] fontSizes = {"100%","150%","200%"};
	private int currentFont = DEF;

	public TestPaper(File p, Login login, List<String> selectedList) {
		this.p = p;
		this.login = login;
		this.selectedList = selectedList;
		quizAnswerList = new Vector<QuizAnswer[]>();
		quizListAll = new Vector<Vector<Quiz>>();
		getTestTime();
		path = selectedList.get(0);
		load();
		init();
		setDisplay();
		addListeners();
		showFrame();

	}
	
	private void loadFontPack(int size) {
		FileReader fr = null;		
		try {
			fr = new FileReader(size+ "%.properties");
			pro = new Properties();
			pro.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(fr != null) {
				try {
					fr.close();
				}catch(IOException e) {}
			}
		}
	}

	private void init() {
		loadFontPack(currentFont);
		worngTestItems = new Vector<Integer>();
		item = new Vector<HashMap<JLabel, Integer>>();
		timer = new TestTime(this, testTime);
		pnlMain = new JPanel(new BorderLayout());

		pnlRight = new JPanel(new BorderLayout());
		pnlCenter = new JPanel(new BorderLayout());
		lblNum = new JLabel[quizList.size()];

		testTimeCheck = new JLabel(new ImageIcon("img\\?????? ??????1.png"), JLabel.RIGHT);
		testTimeCheck.setBorder(new EmptyBorder(5, 5, 5, 5));
		timeStop = new JLabel(new ImageIcon("img\\start.png"), JLabel.RIGHT);
		timeStop.setBorder(new EmptyBorder(5, 5, 5, 5));
		sbumit = new JLabel(new ImageIcon("img\\??????1.png"), JLabel.RIGHT);
		sbumit.setBorder(new EmptyBorder(5, 5, 5, 5));

		lblSubject = new JLabel[selectedList.size()];

		titleString = getfileName(path);
		didExamName = login.getDidExamName();
		didSubjts = didExamName.getSubjectList();

		didExamName.setTestName(p.getName());
		didExamName.setTestTime(testTime);
		didExamName.setTestDay(TestResult.getTestDate());
		didExamName.setPath(p);
		List<String> list = null;
		if (didExamName.getSelsetedList() == null) {
			list = new Vector<String>();
		} else {
			list = didExamName.getSelsetedList();
		}
		for (String s : selectedList) {
			list.add(s);
		}
		didExamName.setSelsetedList(list);		
		lblDef = getLabel(lblDef,"100%");		
		lblPlus1 = getLabel(lblPlus1,"150%");
		lblPlus2 = getLabel(lblPlus2,"200%");
		setFontColor();

	}
	
		

	private void setDisplay() {
		JPanel pnlNorth = new JPanel(new GridLayout(0, 5));
		for (int i = 0; i < quizList.size(); i++) {
			lblNum[i] = new JLabel(String.valueOf(i + 1), JLabel.CENTER);
			lblNum[i].setPreferredSize(new Dimension(30, 30));
			lblNum[i].setBackground(Color.LIGHT_GRAY);
			lblNum[i].setBorder(new LineBorder(Color.GRAY));
			lblNum[i].setOpaque(true);
			lblNum[i].setToolTipText("????????? ????????? ?????? ????????? ??? ??? ????????????.");
			lblNum[0].setBackground(Color.WHITE);
			worngTestItems.add(i + 1);
			pnlNorth.add(lblNum[i]);
		}
		pnlRight.add(pnlNorth, BorderLayout.NORTH);

		JPanel pnlcen = new JPanel(new GridLayout(6, 1));
		pnlcen.add(new JLabel());
		pnlcen.add(testTimeCheck);
		pnlcen.add(timeStop);
		pnlcen.add(sbumit);
		pnlcen.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlRight.add(pnlcen, BorderLayout.CENTER);

		JPanel pnlSouth = new JPanel(new GridLayout(0, 5));
		for (int i = 0; i < lblSubject.length; i++) {
			lblSubject[i] = new JLabel(getfileName(selectedList.get(i)), JLabel.CENTER);
			lblSubject[i].setBorder(new LineBorder(Color.GRAY));
			lblSubject[i].setOpaque(true);
			lblSubject[i].setToolTipText("????????? ????????? ?????? ???????????? ???????????????.");
			lblSubject[i].setBackground(Color.LIGHT_GRAY);
			lblSubject[0].setBackground(Color.WHITE);
			lblNum[0].setBorder(new BevelBorder(BevelBorder.LOWERED,Color.LIGHT_GRAY,Color.GRAY));
			DidSubject didsubject = new DidSubject(getfileName(selectedList.get(i)), "0", subScore,
					new Vector<Integer>(worngTestItems),quizAnswer);
			didSubjts.add(didsubject);
			pnlSouth.add(lblSubject[i]);
		}
		subjectIdx = 0;
		pnlRight.add(pnlSouth, BorderLayout.SOUTH);
		pnlRight.setBorder(new LineBorder(Color.DARK_GRAY));
		pnlMain.add(pnlRight, BorderLayout.EAST);
		setCenter();
		lblLogout = new JLabel(new ImageIcon("img\\logout1.png"), JLabel.LEFT);
		lblLogout.setBorder(new EmptyBorder(5, 8, 8, 5));		
		
		JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTop.add(lblLogout);
		pnlTop.add(lblDef);
		pnlTop.add(new JLabel("|"));
		pnlTop.add(lblPlus1);
		pnlTop.add(new JLabel("|"));
		pnlTop.add(lblPlus2);		
		add(pnlTop, BorderLayout.NORTH);
		
		
		
		add(pnlMain, BorderLayout.CENTER);
		add(new JLabel("   "), BorderLayout.SOUTH);
		add(new JLabel("   "), BorderLayout.WEST);
		add(new JLabel("   "), BorderLayout.EAST);

	}

	private void addListeners() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getSource() == testTimeCheck) {					
					timer.setLocationRelativeTo(TestPaper.this);
					timer.setVisible(true);
				} else if (e.getSource() == lblLogout) {
					logout();
				} else if (e.getSource() == timeStop) {
					timer.getTimeStopOrStart();
				} else if (e.getSource() == sbumit) {
					quizAnswerList.set(subjectIdx, quizAnswer);
					didSubjts.get(subjectIdx).setAnswers(quizAnswer);
					int count = 0;
					for (QuizAnswer[] ansArr : quizAnswerList) {
						for (QuizAnswer ans : ansArr) {
							if (ans.getItemSelected() == 0) {
								count++;
							}
						}
					}
					if (count == 0 && quizAnswerList.size() == lblSubject.length) {
						testTime = String.valueOf(Integer.parseInt(testTime) - Integer.parseInt(timer.getRestTime()));
						didExamName.setTestTime(testTime);
						JOptionPane.showMessageDialog(TestPaper.this, "?????? ??????!");
						new TestResult(TestPaper.this, p, login);
						dispose();
					} else {
						JOptionPane.showMessageDialog(TestPaper.this, "??? ??? ????????? ????????? ????????? ??????????????????!");
					}
				} else if (e.getSource() == lblLeft) {
					if (!timer.getTimer().isRunning()) {
						showTimeMsg();
					} else {
						int num = Integer.parseInt(currentStr);
						if (num == 1) {
							JOptionPane.showMessageDialog(TestPaper.this, "?????? ???????????? ??? ???????????????!");
						} else {
							num = num - 1;
							setArrow(num);
						}
					}

				} else if (e.getSource() == lblRight) {
					if (!timer.getTimer().isRunning()) {
						showTimeMsg();
					} else {
						int num = Integer.parseInt(currentStr);
						if (num == lblNum.length) {
							JOptionPane.showMessageDialog(TestPaper.this, "?????? ???????????? ????????? ???????????????!");
						} else {
							num = num + 1;
							setArrow(num);
						}
					}
				}else if(e.getSource()==lblPlus1){
					currentFont = PLUS1;
					loadFontPack(currentFont);
					setFontColor();
					setQuestion(currentStr);
				}else if(e.getSource()==lblPlus2){
					currentFont = PLUS2;
					loadFontPack(currentFont);
					setFontColor();
					setQuestion(currentStr);
				}else if(e.getSource()==lblDef) {
					currentFont = DEF;
					loadFontPack(currentFont);
					setFontColor();
					setQuestion(currentStr);
				}else {
					if (!timer.getTimer().isRunning()) {
						showTimeMsg();
					} else {
						for (int i = 0; i < lblNum.length; i++) {

							if (e.getSource() == lblNum[i]) {
								if(!currentStr.equals(lblNum[i].getText())) {
									setLblWhite(i);
								}								
							} else {
								setLblNotWhite(i);
							}
						}
					}

				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == testTimeCheck || e.getSource() == timeStop || e.getSource() == sbumit||
						e.getSource() == lblLeft || e.getSource() == lblRight|| e.getSource() == lblLogout||
						e.getSource() == lblDef || e.getSource() == lblPlus1|| e.getSource() == lblPlus2) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource() == testTimeCheck || e.getSource() == timeStop || e.getSource() == sbumit||
						e.getSource() == lblLeft || e.getSource() == lblRight|| e.getSource() == lblLogout||
						e.getSource() == lblDef || e.getSource() == lblPlus1|| e.getSource() == lblPlus2) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			}
			
			
		};

		for (int i = 0; i < lblNum.length; i++) {
			lblNum[i].addMouseListener(ml);
		}
		testTimeCheck.addMouseListener(ml);
		timeStop.addMouseListener(ml);
		sbumit.addMouseListener(ml);
		lblLeft.addMouseListener(ml);
		lblRight.addMouseListener(ml);
		lblLogout.addMouseListener(ml);
		
		lblPlus1.addMouseListener(ml);
		lblPlus2.addMouseListener(ml);
		lblDef.addMouseListener(ml);

		for (int i = 0; i < lblSubject.length; i++) {
			lblSubject[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (timer.getTimer().isRunning()) {
						addlblSubjectAction(e);
					} else {
						showTimeMsg();
					}

				}
			});
		}

		for (JLabel lbl : lblOptions) {
			lbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (timer.getTimer().isRunning()) {
						addOptionsAction(lbl);
					} else {
						showTimeMsg();
					}

				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					Toolkit tk = Toolkit.getDefaultToolkit();
					Image cursorimage=tk.getImage("img\\penblue.png");					    
					Cursor cursor=tk.createCustomCursor(cursorimage,new Point(0,0),"haha");				
					setCursor(cursor);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}
				
				

			});
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(TestPaper.this, "??????????????? ?????????????????????????", "??????",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					closeWindow();
				}
			}
		});

	}

	private void showFrame() {
		pack();
		setTitle("????????????");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		setResizable(false);
		setVisible(true);
	}

	private void resetAnswer() {
		currentStr = "1";
		for (int i = 0; i < lblNum.length; i++) {
			if (i == 0) {
				setLblWhite(i);
			} else {
				setLblNotWhite(i);
			}
		}
		resetOption(0);
	}

	private void addlblSubjectAction(MouseEvent e) {
		quizAnswerList.set(subjectIdx, quizAnswer);
		didSubjts.get(subjectIdx).setAnswers(quizAnswer);
		for (int i = 0; i < lblSubject.length; i++) {
			if (e.getSource() == lblSubject[i]) {
				lblSubject[i].setBackground(Color.WHITE);				
				if(i>quizAnswerList.size()){
					for(int j=1;j<i;j++) {						
						path = selectedList.get(j);						
						load();
						
					}					
				}				
				path = selectedList.get(i);
				titleString = getfileName(path);
				titlename.setText(titleString);			
				load();
				subjectIdx = i;								
				quizAnswer = quizAnswerList.get(i);				
				resetAnswer();
			} else {
				lblSubject[i].setBackground(Color.LIGHT_GRAY);
			}
		}
	}

	private void addOptionsAction(JLabel lbl) {
		int i = lblMap.get(lbl);
		int n = Integer.parseInt(currentStr);
		if (lbl.getBackground() == Color.GRAY) {
			if (quizAnswer[n - 1].getAnswer().size() < quizList.get(n - 1).getAnswer().length) {
				quizAnswer[n - 1].getAnswer().add(i);
				lbl.setIcon(new ImageIcon("img\\" + (i) + "plus.png"));
				lbl.setBackground(Color.white);
				lblNum[n - 1].setBackground(new Color(102, 205, 170));
				quizAnswer[n - 1].setItemSelected(1);
				quizAnswer[n - 1].setOptions(lblOptions);
			}
		} else {
			quizAnswer[n - 1].getAnswer().removeElement(i);
			lbl.setBackground(Color.GRAY);
			lbl.setIcon(new ImageIcon("img\\" + (i) + ".png"));
			if (quizAnswer[n - 1].getAnswer().size() == 0) {
				lblNum[n - 1].setBackground(Color.WHITE);
				quizAnswer[n - 1].setItemSelected(0);
				quizAnswer[n - 1].setOptions(lblOptions);
			}
		}

	}

	private void setLblWhite(int i) {
		lblNum[i].setBackground(Color.WHITE);
		lblNum[i].setBorder(new BevelBorder(BevelBorder.LOWERED,Color.LIGHT_GRAY,Color.GRAY));
		currentStr = lblNum[i].getText();
		setQuestion(currentStr);
		resetOption(i);
	}

	private void setLblNotWhite(int i) {
		if (quizAnswer[i].getItemSelected() == 0) {
			lblNum[i].setBackground(Color.LIGHT_GRAY);
			lblNum[i].setBorder(new LineBorder(Color.GRAY));
		} else {
			lblNum[i].setBackground(new Color(102, 205, 170));
			lblNum[i].setBorder(new LineBorder(Color.GRAY));
		}
	}

	private void resetOption(int i) {
		QuizAnswer answer = quizAnswer[i];
		JLabel[] lblTemp = answer.getOptions();
		for (int j = 0; j < lblOptions.length; j++) {
			lblOptions[j].setIcon(new ImageIcon("img\\" + (j + 1) + ".png"));
		}

		if (answer.getItemSelected() == 1) {
			for (int j = 0; j < answer.getAnswer().size(); j++) {
				int num = answer.getAnswer().get(j);
				lblOptions[num - 1].setIcon(new ImageIcon("img\\" + num + "plus.png"));
			}
		}
	}

	private void setArrow(int num) {
		for (int i = 0; i < lblNum.length; i++) {
			if (i == (num - 1)) {
				setLblWhite(i);
				resetOption(i);
			} else {
				setLblNotWhite(i);
			}
		}
	}

	private HashMap<JLabel, Integer> lblMap = new HashMap<JLabel, Integer>();

	private void setCenter() {
		pnlCenter.setBorder(new LineBorder(Color.DARK_GRAY));
		titlename = new JLabel(titleString, JLabel.LEFT);
		titlename.setBorder(new EmptyBorder(5, 0, 5, 0));
		pnlCenter.add(titlename, BorderLayout.NORTH);	
		
		pnlPro=new JPanel(new BorderLayout());
		pnlPro.setMaximumSize(new Dimension(800,500));
		pnlPro.setBackground(Color.WHITE);
		
		JScrollPane sc=new JScrollPane(pnlPro);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sc.getVerticalScrollBar().setUnitIncrement(30);		
		pnlCenter.add(sc);
		
		
		
		setQuestion(currentStr);
		JPanel pnlSs = new JPanel();
		for (int i = 0; i < (optionsLen + 2); i++) {
			JLabel lbl = null;
			if (i == 0) {
				lblLeft = new JLabel(new ImageIcon("img\\left.png"), JLabel.CENTER);
				pnlSs.add(lblLeft);
			} else if (i == (optionsLen + 1)) {
				lblRight = new JLabel(new ImageIcon("img\\right.png"), JLabel.CENTER);
				pnlSs.add(lblRight);
			} else {
				lbl = new JLabel(new ImageIcon("img\\" + i + ".png"), JLabel.CENTER);
				lbl.setBackground(Color.GRAY);
				quizAnswer[Integer.parseInt(currentStr) - 1].getOptions()[i - 1] = lbl;
				lblMap.put(lbl, i);
				pnlSs.add(lbl);
			}
		}
		lblOptions = quizAnswer[Integer.parseInt(currentStr) - 1].getOptions();
		for (int i = 0; i < quizList.size(); i++) {
			item.add(lblMap);
		}
		pnlCenter.add(pnlSs, BorderLayout.SOUTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
	}

	private void load() {
		try (FileInputStream fis = new FileInputStream(p.getPath() + "\\" + path);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			
			subject = (Subject) (ois.readObject());
			subScore = subject.getTotalScore();
			quizList = subject.getQuizs();

			quizAnswer = new QuizAnswer[quizList.size()];
			quizListAll.add(quizList);
			for (int i = 0; i < quizList.size(); i++) {
				Quiz quiz = quizList.get(i);
				quizAnswer[i] = new QuizAnswer(i, new Vector<Integer>(), new JLabel[quiz.getOptions().length], 0);				
			}
			if (quizAnswerList.size() < selectedList.size()) {
				quizAnswerList.add(quizAnswer);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void getTestTime() {
		int sum = 0;
		for (String p : selectedList) {
			path = p;
			load();
			sum += Integer.parseInt(subject.getTestTime());
		}
		testTime = String.valueOf(sum);
		path = selectedList.get(0);
		quizAnswerList = new Vector<QuizAnswer[]>();
		quizListAll = new Vector<Vector<Quiz>>();
	}

	private void setQuestion(String s) {
		pnlPro.removeAll();
		Quiz quiz = quizList.get(Integer.parseInt(s) - 1);		
		String quizTitle = quiz.getQuizTitle();
		String passage = quiz.getPassage();	
		byte[][] fpImg=quiz.getFpImg();
		String[] fpImgInfo=quiz.getFpImgInfo();
		JTextArea ta= new JTextArea(s+". "+quizTitle+"\n"+passage);
		ta.setFont(new Font("?????? ??????", Font.PLAIN,Integer.parseInt(pro.getProperty("TestPaper.size"))));
		ta.setEditable(false);
		ta.setLineWrap(true);
		pnlPro.add(ta,BorderLayout.NORTH);
		
		
		JPanel pnlCC=new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlCC.setBackground(Color.WHITE);		
		boolean flag=false;	
		
		if(fpImg!=null||fpImg.length!=0) {
			for(int i=0; i<fpImg.length;i++) {	
				JPanel pnlC=new JPanel(new BorderLayout());
				pnlC.setBackground(Color.WHITE);
				if(fpImg[i]!=null&&fpImg[i].length!=0) {
					JLabel lblpro3=getImgLabel("question",i, fpImg[i]);
					lblpro3.setToolTipText("???????????? ??????????????? ????????? ??? ????????????!");
					int n=i;
					lblpro3.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							getBingImg("question",n);
						}
					});
					pnlC.add(lblpro3,BorderLayout.CENTER);
					flag=true;
				}				
				
				if(fpImgInfo!=null|| fpImgInfo.length!=0) {
					if(fpImgInfo[i]!=null) {
						JTextArea ta4= new JTextArea(fpImgInfo[i]);
						ta4.setFont(new Font("?????? ??????", Font.PLAIN,Integer.parseInt(pro.getProperty("TestPaper.size"))));
						ta4.setEditable(false);
						ta4.setLineWrap(true);
						pnlC.add(ta4,BorderLayout.SOUTH);
					}					
				}
				pnlCC.add(pnlC);
			}
			pnlPro.add(pnlCC,BorderLayout.CENTER);
		}	
		
		pnlCC.add(new JLabel("\n\n"));	
		
		JPanel pnlSS=new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSS.setBackground(Color.WHITE);
		
		JPanel pnlTwo=new JPanel(new GridLayout(4,1));		
		
		String[] options = quiz.getOptions();
		byte[][] exampleImg=quiz.getExampleImg();		
		optionsLen = quiz.getOptions().length;	
		
		if(options!=null||options.length!=0 ) {
			for (int i = 0; i < options.length; i++) {
				JPanel pnlS=new JPanel(new GridLayout(2,1));			
				pnlS.setBackground(Color.WHITE);
				
				String option = options[i];			
				JLabel lblpro5=new JLabel(i+1+". "+options[i],JLabel.LEFT);
				lblpro5.setFont(new Font("?????? ??????", Font.BOLD,Integer.parseInt(pro.getProperty("TestPaper.size"))));
				pnlS.add(lblpro5);	
				if(exampleImg!=null||exampleImg.length!=0) {	
						if(exampleImg[i]!=null&&exampleImg[i].length!=0) {							
							JLabel lblpro6=getImgLabel("answer",i, exampleImg[i]);
							lblpro6.setToolTipText("???????????? ??????????????? ????????? ??? ????????????!");
							int n=i;
							lblpro6.addMouseListener(new MouseAdapter() {
								@Override
								public void mousePressed(MouseEvent e) {
									getBingImg("answer",n);
								}
							});
							pnlS.add(lblpro6);
						}						
					}
				pnlTwo.add(pnlS);
				JLabel temp=new JLabel();
				temp.setOpaque(true);
				temp.setBackground(Color.WHITE);
				pnlTwo.add(temp);
				
				}					
			}
		pnlSS.add(pnlTwo);
		
		if(flag) {
			pnlPro.add(pnlSS,BorderLayout.SOUTH);
		}else {
			pnlPro.add(pnlSS,BorderLayout.CENTER);
			JLabel l= new JLabel("");
			l.setPreferredSize(new Dimension(200,200));
			pnlPro.setMaximumSize(new Dimension(500,600));
			pnlPro.add(l,BorderLayout.SOUTH);
		}
				
		pnlPro.updateUI();	
		
	}
	
	private void getBingImg(String s,int n) {
		JOptionPane.showMessageDialog(this,"", "IMG", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(titleString+Integer.parseInt(currentStr)+s+n+".png"));
	}
	

	private JLabel getImgLabel(String s,int n, byte[] img) {		
		JLabel lbl= null;		
		try(FileOutputStream fos= new FileOutputStream(titleString+Integer.parseInt(currentStr)+s+n+".png");)
		{	
			fos.write(img);	
			fos.flush();
			fos.close();
			ImageIcon ic= new ImageIcon(titleString+Integer.parseInt(currentStr)+s+n+".png");
			double temp=Integer.parseInt(pro.getProperty("TestPaper.imgw"))/100.0;
			Image imG=Toolkit.getDefaultToolkit().
					getImage(titleString+Integer.parseInt(currentStr)+s+n+".png").getScaledInstance((int)(ic.getIconWidth()*0.8*temp), (int)(ic.getIconHeight()*0.8*temp), Image.SCALE_SMOOTH);			
			lbl=new JLabel(new ImageIcon(imG),JLabel.LEFT);			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			return lbl;
		}	
	}

	public Vector<QuizAnswer[]> getQuizAnswerList() {
		return quizAnswerList;
	}

	public List<String> getSelectedList() {
		return selectedList;
	}

	public Vector<Vector<Quiz>> getQuizListAll() {
		return quizListAll;
	}

	private void showTimeMsg() {
		String s = "????????? ????????? ????????? ????????? ???????????????!";		
		if (timer.getTimerStatus()) {
			s = "?????? ?????? ???????????? ?????? ???????????????!";
			JOptionPane.showMessageDialog(this, s);
			testTime = String.valueOf(Integer.parseInt(testTime) - Integer.parseInt(timer.getRestTime()));
			didExamName.setTestTime(testTime);
			login.savedidExamInfo(didExamName);
			new TestResult(TestPaper.this, p, login);
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, s);
		}

	}

	private void logout() {
		int n = JOptionPane.showConfirmDialog(this, "???????????? ???????????????????", "??????", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (n == JOptionPane.YES_OPTION) {
			getDataSave();
			login.setVisible(true);
			TestResult.subNums = null;
			login.setDidExamName(new DidExam(null, null, new Vector<DidSubject>(), null, null, null));
			dispose();
		}

	}
	/*
	 * testTime = String.valueOf(Integer.parseInt(testTime) - Integer.parseInt(timer.getRestTime()));
			didExamName.setTestTime(testTime);
			login.savedidExamInfo(didExamName);
			new TestResult(TestPaper.this, p, login, true);
			login.getSave();
			login.saveDidExam();
	 */

	private void closeWindow() {
		getDataSave();
		System.exit(0);
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
	
	public boolean getTheTimeStatus() {
		return timer.getTimerStatus();
	}
	private JLabel getLabel(JLabel lbl, String str) {
		lbl = new JLabel();
		Image image = new ImageIcon("img\\sizeChange.png").getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		lbl.setIcon(icon);
		lbl.setText(str);
		lbl.setHorizontalTextPosition(JLabel.RIGHT);
		lbl.setVerticalTextPosition(JLabel.CENTER);
		return lbl;
	}
	
	private void setFontColor() {
		lblPlus1.setForeground(Color.GRAY);
		lblPlus2.setForeground(Color.GRAY);
		lblDef.setForeground(Color.GRAY);
		if(currentFont ==DEF) {
			lblDef.setForeground(Color.blue);			
		}else if(currentFont ==PLUS1) {
			lblPlus1.setForeground(Color.blue);
		}else if(currentFont ==PLUS2) {
			lblPlus2.setForeground(Color.blue);
		}	
	}
	private void getDataSave() {
		testTime = String.valueOf(Integer.parseInt(testTime) - Integer.parseInt(timer.getRestTime()));
		didExamName.setTestTime(testTime);
		login.savedidExamInfo(didExamName);
		new TestResult(TestPaper.this, p, login, true);
		login.getSave();
		login.saveDidExam();
	}

}
