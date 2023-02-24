package ke.ac.green;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import QuizMaker.Quiz;
import QuizMaker.Subject;

public class QuizSolve extends JFrame {
	private Vector<Quiz> quizList;
	private String quizTitle;
	private double score;
	private String passage;
	private String explanation;
	private String[] options;
	private byte[][] fpImg;
	private String fpImgInfo;
	private byte[][] exampleImg;
	private int[] answer;

	private JLabel lblLogout;
	private JTextArea taQuiz;
	private JLabel lblAnswer;
	private JLabel lblLeft;
	private JLabel lblRight;
	private JTextField tfNum;
	private JLabel lblGo;
	private JTextArea taExplanation;
	private int currentNum = 1;
	private File file;
	private String subName;
	private Subject subject;
	private TestResult testResult;
	private JTextPane tpTestPaper;

	public QuizSolve() {
		load();
		init();
		setDisplay();
		addListeners();
		quizChange(currentNum);
		showFrame();
	}

	public QuizSolve(TestResult testResult, File file, String subName) {
		this.testResult = testResult;
		this.file = file;
		this.subName = subName;
		load();
		init();
		setDisplay();
		addListeners();
		quizChange(currentNum);
		showFrame();
	}

	private void init() {
		ImageIcon logout = new ImageIcon("img\\logout1.png");
		lblLogout = new JLabel(logout);
		taQuiz = new JTextArea(10, 50);
		taQuiz.setEditable(false);

		lblAnswer = new JLabel("");
		ImageIcon left = new ImageIcon("img\\left.png");
		ImageIcon right = new ImageIcon("img\\right.png");
		lblLeft = new JLabel(left);
		lblRight = new JLabel(right);
		tfNum = new JTextField("원하시는 번호를 입력해주세요", 16);
		ImageIcon icon = new ImageIcon("img\\go.png");
		Image image = icon.getImage();
		Image img = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon go = new ImageIcon(img);
		lblGo = new JLabel(go);
		lblGo.setPreferredSize(new Dimension(50, 50));
		taExplanation = new JTextArea(10, 60);
		taExplanation.setEditable(false);
	}

	private void setDisplay() {
		tpTestPaper = new JTextPane();
		tpTestPaper.setPreferredSize(new Dimension(800, 300));
		tpTestPaper.setEditable(false);
		JScrollPane sc = new JScrollPane(tpTestPaper);
		sc.setPreferredSize(new Dimension(800, 300));
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel pnlTop = new JPanel(new BorderLayout());
		JPanel pnlTitle1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTitle1.add(new JLabel("문제"));

		JPanel pnlAnswer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlAnswer.add(lblAnswer);
		pnlTop.add(pnlTitle1, BorderLayout.NORTH);

		pnlTop.add(sc, BorderLayout.CENTER);

		pnlTop.add(pnlAnswer, BorderLayout.SOUTH);
		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		JPanel pnlButton = new JPanel(new BorderLayout());
		pnlButton.add(lblLeft, BorderLayout.WEST);
		pnlButton.add(lblRight, BorderLayout.EAST);
		JPanel pnlGo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlGo.add(tfNum);
		pnlGo.add(lblGo);
		pnlCenter.add(pnlButton);
		pnlCenter.add(pnlGo);
		JScrollPane answer = new JScrollPane(taExplanation);
		JPanel pnlBottom = new JPanel(new BorderLayout());

		JPanel pnlTitle2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTitle2.add(new JLabel("                             "));
		pnlTitle2.add(new JLabel("해설"));

		JPanel pnlExplanation = new JPanel();
		pnlExplanation.add(answer);
		pnlBottom.add(pnlTitle2, BorderLayout.NORTH);
		pnlBottom.add(pnlExplanation, BorderLayout.CENTER);

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlTop, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

		JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLogout.add(lblLogout);
		add(pnlLogout, BorderLayout.NORTH);
		add(pnlMain, BorderLayout.CENTER);

	}

	private void addListeners() {

		MouseListener ml = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getSource() == lblLeft) {
					int num = currentNum;
					if (num == 1) {
						JOptionPane.showConfirmDialog(QuizSolve.this, "현재 페이지가 첫 문제 입니다.", "information",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon("img\\log.png"));
					} else {
						num = num - 1;
						quizChange(num);
					}

				} else if (me.getSource() == lblRight) {
					int num = currentNum;
					if (num == quizList.size()) {
						int result = JOptionPane.showConfirmDialog(QuizSolve.this, "마지막 문제 입니다.\n결과확인창으로 돌아가시겠습니까?",
								"information", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
								new ImageIcon("img\\log.png"));
						if (result == JOptionPane.YES_OPTION) {
							testResult.setVisible(true);
							dispose();
						}
					} else {
						num = num + 1;
						quizChange(num);
					}

				} else if (me.getSource() == tfNum) {
					tfNum.setText("");
				} else if (me.getSource() == lblGo) {
					String number = tfNum.getText().trim();
					try {
						int num = Integer.parseInt(number);
						number = "원하시는 번호를 입력해주세요";
						number = "";
						if (num > 0 || num < quizList.size()) {
							quizChange(num);
							tfNum.setText("");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(QuizSolve.this, "원하시는 번호를 입력해주세요.", "information",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (ArrayIndexOutOfBoundsException e) {
						JOptionPane.showMessageDialog(QuizSolve.this, "없는 문제 번호 입니다.", "information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					int result = JOptionPane.showConfirmDialog(QuizSolve.this, "로그아웃 하시겠습니까?", "information",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						testResult.getSaveDataAllAndLogout();
						TestResult.subNums = null;
						dispose();
					}
				}
			}
		};
		lblLeft.addMouseListener(ml);
		lblRight.addMouseListener(ml);
		tfNum.addMouseListener(ml);
		lblGo.addMouseListener(ml);
		lblLogout.addMouseListener(ml);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(QuizSolve.this, "결과확인창으로 돌아가시겠습니까?", "알림",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("img\\log.png"));
				if (result == JOptionPane.YES_OPTION) {
					testResult.setVisible(true);
					dispose();
				}
			}
		});

	}

	private void showFrame() {
		setTitle("문제풀이");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void load() {
		try (FileInputStream fis = new FileInputStream(file + "\\" + subName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			subject = (Subject) (ois.readObject());
			quizList = subject.getQuizs();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setAnswer(String s) {
		Quiz quiz = quizList.get(Integer.parseInt(s));
		StringBuffer buf = new StringBuffer();
		answer = quiz.getAnswer();
		for (int i = 0; i < answer.length; i++) {
			buf.append(answer[i] + "번  ");
		}
		lblAnswer.setText("정답: " + buf.toString());
	}

	private void setExplanation(String s) {
		Quiz quiz = quizList.get(Integer.parseInt(s));
		explanation = quiz.getExplanation();
		taExplanation.setText(explanation);
	}

	private void quizChange(int num) {
		currentNum = num;
		setQuiz(String.valueOf(num - 1));
		setAnswer(String.valueOf(num - 1));
		setExplanation(String.valueOf(num - 1));
	}

	private void setQuiz(String s) {
		tpTestPaper.removeAll();
		Document document = tpTestPaper.getStyledDocument();
		int len = document.getLength();
		try {
			document.remove(0, len);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		Quiz quiz = quizList.get(Integer.parseInt(s));

		StringBuffer buf = new StringBuffer();
		String quizTitle = quiz.getQuizTitle();
		String passage = quiz.getPassage();

		if (passage == null || passage.length() == 0) {
			buf.append((Integer.parseInt(s) + 1) + ". " + quizTitle + "\n\n");
		} else {
			buf.append((Integer.parseInt(s) + 1) + ". " + quizTitle + "\n").append("\n" + passage + "\n");
		}

		document = getDocument(buf, document);

		byte[][] fpImg = quiz.getFpImg();
		String[] fpImgInfo = quiz.getFpImgInfo();
		boolean flag = false;

		if (fpImg != null || fpImg.length != 0) {
			for (int i = 0; i < fpImg.length; i++) {
				buf = new StringBuffer();
				if (fpImg[i] != null) {
					JLabel lbl = new JLabel(getImgIcon("question", i, fpImg[i]));
					lbl.setToolTipText("이미지를 클릭하시면 확대할 수 있습니다!");
					int n = i;
					lbl.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							getBingImg("question", n);
						}
					});
					tpTestPaper.insertComponent(lbl);
					buf.append("\n" + fpImgInfo[i] + "\n");
					document = getDocument(buf, document);
					flag = true;
				}
			}

		}

		if (flag) {
			document = getDocument(new StringBuffer().append("\n\n"), document);
		}

		String[] options = quiz.getOptions();
		byte[][] exampleImg = quiz.getExampleImg();

		for (int i = 0; i < options.length; i++) {
			String option = options[i];
			buf = new StringBuffer();
			buf.append((i + 1) + ". " + option + "\n");
			document = getDocument(buf, document);
			if (exampleImg[i] != null && exampleImg[i].length != 0) {
				JLabel lbl = new JLabel(getImgIcon("answer", i, exampleImg[i]), JLabel.CENTER);
				lbl.setToolTipText("이미지를 클릭하시면 확대할 수 있습니다!");
				int n = i;
				lbl.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						getBingImg("answer", n);
					}
				});
				tpTestPaper.insertComponent(lbl);
				document = getDocument(new StringBuffer().append("\n\n"), document);
			}
		}

		tpTestPaper.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		tpTestPaper.updateUI();

	}

	private Document getDocument(StringBuffer b, Document document) {
		try {
			document.insertString(document.getLength(), b.toString(), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} finally {
			return document;
		}
	}

	private ImageIcon getImgIcon(String s, int n, byte[] img) {
		ImageIcon icon = null;
		try (FileOutputStream fos = new FileOutputStream(subName + currentNum + s + n + ".png");) {
			fos.write(img);
			fos.flush();
			fos.close();
			ImageIcon ic = new ImageIcon(subName + currentNum + s + n + ".png");
			Image imG = Toolkit.getDefaultToolkit().getImage(subName + currentNum + s + n + ".png").getScaledInstance(
					(int) (ic.getIconWidth() * 0.8), (int) (ic.getIconHeight() * 0.8), Image.SCALE_SMOOTH);
			icon = new ImageIcon(imG);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			return icon;
		}
	}

	private void getBingImg(String s, int n) {
		JOptionPane.showMessageDialog(this, "", "IMG", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(subName + currentNum + s + n + ".png"));
	}

}
