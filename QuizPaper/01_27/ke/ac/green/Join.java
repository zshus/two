package ke.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class Join extends JFrame {

	private Vector<Human> vec;

	private JLabel log;

	private String[] titles = { "NAME", "TEL", "ID", "PW", "QUESTION", "ANSWER" };
	private JLabel[] title;
	private JTextField[] input;
	private JPasswordField passWord;
	private JComboBox<String> question;
	private JPanel pnlCenter;

	private JLabel lblSch;
	private JLabel lblJoin;
	private Login login;

	public Join(Login login) {
		this.login = login;
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	private void init() {
		vec = login.getVec();
		log = new JLabel(new ImageIcon("img\\JoinLog.png"), JLabel.CENTER);
		title = new JLabel[titles.length];
		input = new JTextField[titles.length];
		pnlCenter = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < titles.length; i++) {
			JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
			title[i] = new JLabel("   " + titles[i], JLabel.LEFT);
			title[i].setPreferredSize(new Dimension(70, 30));
			pnl.add(title[i]);
			if (titles[i].equals("PW")) {
				passWord = new JPasswordField(20);
				pnl.add(passWord);
			} else if (titles[i].equals("QUESTION")) {
				question = new JComboBox<String>(new String[] { "-선택-", "내가 좋아하는 음식은?", "내가 좋아하는 동물은?", "나의 최애는?" });
				question.setPreferredSize(new Dimension(225, 25));
				pnl.add(question);
			} else {
				input[i] = new JTextField(20);
				pnl.add(input[i]);
			}
			if (titles[i].equals("ID")) {
				lblSch = new JLabel(new ImageIcon("img\\Sch.png"));
				pnl.add(lblSch);
				pnl.add(new JLabel("   "));
			}
			pnlCenter.add(pnl);

		}
		lblJoin = new JLabel(new ImageIcon("img\\join2.png"));

	}

	private void setDisplay() {
		JPanel join = new JPanel();
		join.add(lblJoin);
		join.setPreferredSize(new Dimension(50, 45));
		pnlCenter.add(join);
		add(log, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);

	}

	private void addListener() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getSource() == lblSch) {
					lblSch.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.GRAY));
					if (schAction()) {
						JOptionPane.showMessageDialog(Join.this, "사용 가능한 ID입니다!");
					}
				} else {
					lblJoin.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.GRAY));
					addHuman();
				}

			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == lblSch || e.getSource() == lblJoin) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource() == lblSch || e.getSource() == lblJoin) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

			}
			
		};
		lblSch.addMouseListener(ml);
		lblJoin.addMouseListener(ml);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				login.setVec(vec);
				System.out.println(vec);
				int num = JOptionPane.showConfirmDialog(Join.this, "로그인창으로 넘어가시겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (num == JOptionPane.YES_OPTION) {
					login.setVisible(true);
					;
					dispose();
				}
			}
		});

	}

	private void showFrame() {
		setTitle("Join");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private boolean schAction() {
		boolean flag = true;
		String temp_id = "";
		for (int i = 0; i < titles.length; i++) {
			if (titles[i].equals("ID")) {
				temp_id = input[i].getText();
			}
		}
		for (Human h : vec) {
			if (h.getId().equals(temp_id)) {
				JOptionPane.showMessageDialog(Join.this, "ID 중복되었습니다! 다시 입력해 주세요!");
				flag = false;
			}
		}
		if (temp_id.equals("")) {
			JOptionPane.showMessageDialog(Join.this, "ID를 입력해 주세요!");
			flag = false;
		}
		lblSch.setBorder(new LineBorder(new Color(233, 233, 233)));
		return flag;
	}

	private void addHuman() {
		String name = "";
		String tel = "";
		String id = "";
		String pw = "";
		String que = "";
		String answer = "";
		for (int i = 0; i < titles.length; i++) {
			if (titles[i].equals("PW")) {
				pw = new String(passWord.getPassword());
			} else if (titles[i].equals("QUESTION")) {
				que = question.getItemAt(question.getSelectedIndex());
			} else if (titles[i].equals("NAME")) {
				name = input[i].getText();
			} else if (titles[i].equals("TEL")) {
				tel = input[i].getText();
			} else if (titles[i].equals("ID")) {
				id = input[i].getText();
			} else {
				answer = input[i].getText();
			}
		}
		String msg = checkInfo(name, tel, id, pw, answer);
		if (msg.equals("")) {
			if (tel.length() == 11 && checkTel(tel)) {
				Human h = new Human(name, tel, id, pw, que, answer);
				if (h.getQuestion() != null) {
					if (schAction()) {
						vec.add(h);
						JOptionPane.showMessageDialog(Join.this, "가입 완료되었습니다!");
						passWord.setText("");
						question.setSelectedIndex(0);
						for (int i = 0; i < titles.length; i++) {
							if (input[i] != null) {
								input[i].setText("");
							}
						}
					}
				}

			} else {
				JOptionPane.showMessageDialog(Join.this, "전화번호를 올바르게 입력해 주세요!");

			}
		}
		lblJoin.setBorder(new LineBorder(new Color(233, 233, 233)));
	}

	private String checkInfo(String... s) {
		String msg = "";
		for (int i = 0; i < s.length; i++) {
			if (s[0].equals("")) {
				msg = "NAME";
			} else if (s[1].equals("")) {
				msg = "TEL";
			} else if (s[2].equals("")) {
				msg = "ID";
			} else if (s[3].equals("")) {
				msg = "PW";
			} else if (s[4].equals("")) {
				msg = "ANSWER";
			}
		}
		if (!msg.equals("")) {
			JOptionPane.showMessageDialog(Join.this, msg + "을/를입력해 주세요!");
		}
		return msg;
	}

	private boolean checkTel(String tel) {
		boolean flag = true;
		for (int i = 0; i < tel.length(); i++) {
			if (tel.charAt(i) < '0' || tel.charAt(i) > '9') {
				flag = false;
			}
		}
		if (!tel.startsWith("0")) {
			flag = false;
		}
		return flag;
	}

}
