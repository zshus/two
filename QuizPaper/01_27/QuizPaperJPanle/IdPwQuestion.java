package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class IdPwQuestion extends JFrame {
	private JLabel lblIdPw;
	private JLabel lblName;
	private JLabel lblTel;
	private JLabel lblQ;
	private JLabel lblA;
	private JTextField tfName;
	private JTextField tfTel;
	private JComboBox<String> cbQ;
	private JTextField tfA;
	private JLabel lblOk;
	private Vector<Human> vec;
	private String[] strQ = { "-선택-", "내가 좋아하는 음식은?", "내가 좋아하는 동물은?", "나의 최애는?" };

	public static final int NAME = 0;
	public static final int TEL = 1;
	public static final int QUESTION = 2;
	public static final int ANSWER = 3;
	public static final int ID = 4;
	public static final int PW = 5;
	public static final Dimension LBLSIZE = new Dimension(60, 40);
	public static final int TFSIZE = 15;
	private String[] names = { "NAME", "TEL", "QUESTION", "ANSWER" };
	private Login login;

	public IdPwQuestion(Login login) {
		this.login = login;
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	private void init() {
		vec = login.getVec();
		ImageIcon imgIdPw = new ImageIcon("img\\idpw_log.png");
		lblIdPw = new JLabel(imgIdPw);
		lblName = new JLabel("NAME");
		lblTel = new JLabel("TEL");
		lblQ = new JLabel("QUESTION");
		lblA = new JLabel("ANSWER");

		lblName.setPreferredSize(LBLSIZE);
		lblTel.setPreferredSize(LBLSIZE);
		lblQ.setPreferredSize(LBLSIZE);
		lblA.setPreferredSize(LBLSIZE);

		tfName = new JTextField(TFSIZE);
		tfTel = new JTextField(TFSIZE);
		cbQ = new JComboBox<String>(strQ);
		cbQ.setPreferredSize(new Dimension(170, 25));
		tfA = new JTextField(TFSIZE);

		ImageIcon imgOk = new ImageIcon("img\\확인.png");
		lblOk = new JLabel(imgOk);
	}

	private void setDisplay() {
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblIdPw);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		JPanel pnlName = new JPanel();
		pnlName.add(lblName);
		pnlName.add(tfName);
		JPanel pnlTel = new JPanel();
		pnlTel.add(lblTel);
		pnlTel.add(tfTel);
		JPanel pnlQ = new JPanel();
		pnlQ.add(lblQ);
		pnlQ.add(cbQ);
		JPanel pnlA = new JPanel();
		pnlA.add(lblA);
		pnlA.add(tfA);

		pnlCenter.add(pnlName);
		pnlCenter.add(pnlTel);
		pnlCenter.add(pnlQ);
		pnlCenter.add(pnlA);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(lblOk);

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

		add(pnlMain, BorderLayout.CENTER);

	}

	private void addListener() {

		lblOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean flag = false;
				String msg = "";
				if (!flag) {
					if (!isEmpty(tfName)) {
						flag = true;

					} else {
						msg = "missing input : " + names[NAME];
						tfName.requestFocus();
						flag = false;
					}
				}
				if (flag) {
					if (!isEmpty(tfTel)) {
						flag = true;
					} else {
						msg = "missing input : " + names[TEL];
						tfTel.requestFocus();
						flag = false;
					}
				}
				if (flag) {
					if (cbQ.getSelectedItem().toString().equals("-선택-")) {
						flag = false;
						msg = "missing choice : " + names[QUESTION];
					}
				}
				if (flag) {
					if (isEmpty(tfA)) {
						flag = false;
						msg = "missing input : " + names[ANSWER];
						tfA.requestFocus();
					}
				}

				if (flag) {
					flag = false;
					for (Human h : vec) {
						if ((tfName.getText().equals(h.getName()))) {
							flag = true;
							break;
						} else {
							msg = "check your NAME";
						}
					}

				}
				if (flag) {
					flag = false;
					for (Human h : vec) {
						if ((tfTel.getText().equals(h.getTel()))) {
							flag = true;
							break;
						} else {
							msg = "check your TEL";
						}
					}

				}
				if (flag) {
					flag = false;
					for (Human h : vec) {
						if ((cbQ.getSelectedItem().toString().equals(h.getQuestion()))) {
							flag = true;
							break;
						} else {
							msg = "check your Question";
						}
					}

				}
				if (flag) {
					flag = false;
					for (Human h : vec) {
						if ((tfA.getText().equals(h.getAnswer()))) {
							flag = true;
							msg = "";
							break;
						} else {
							msg = "check your ANSWER";
						}
					}

				}
				if (flag) {
					String id = "";
					String pw = "";
					for (Human h : vec) {
						if (tfA.getText().equals(h.getAnswer())
								&& cbQ.getSelectedItem().toString().equals(h.getQuestion())
								&& tfTel.getText().equals(h.getTel()) && tfName.getText().equals(h.getName())) {
							id = h.getId();
							pw = h.getPw();
						}
					}
					tfName.setText("");
					tfTel.setText("");
					tfA.setText("");
					dispose();
					new IdPwAnswer(IdPwQuestion.this, id, pw);
				}
				if (!msg.equals("")) {
					JOptionPane.showMessageDialog(IdPwQuestion.this, msg, "information",
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {				
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			

			}

			@Override
			public void mouseExited(MouseEvent e) {				
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));


			}
			
			
			
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				show_login();
				dispose();
			}
		});

	}

	private void showFrame() {
		setTitle("ID/PW 찾기");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private boolean isEmpty(JTextComponent input) {
		String text = input.getText().trim();
		return (text.length() == 0) ? true : false;
	}

	public void show_login() {
		login.setVisible(true);
	}

}
