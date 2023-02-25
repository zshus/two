package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class QuizFolder extends JFrame {
	private JLabel lblName;
	private JLabel lblQuiz;
	private File file;
	private Login login;

	public QuizFolder(Login login) {
		this.login = login;
		lblName = new JLabel("원하시는 시험을 선택해주세요");
		ImageIcon img = new ImageIcon("img\\workBook.png");
		lblQuiz = new JLabel(img);
		lblQuiz.setPreferredSize(new Dimension(150, 150));
		lblQuiz.setOpaque(true);
		lblQuiz.setBackground(Color.WHITE);
		lblQuiz.setText("시험선택");
		lblQuiz.setHorizontalTextPosition(JLabel.CENTER);
		lblQuiz.setVerticalTextPosition(JLabel.BOTTOM);
		lblQuiz.setToolTipText("학생아이콘을 누르면 시험 파일창으로 넘어갑니다.");

		JPanel pnlSelect = new JPanel();
		pnlSelect.setBackground(Color.WHITE);
		pnlSelect.add(lblName);

		JPanel pnlQuiz = new JPanel();
		pnlQuiz.add(lblQuiz);
		pnlQuiz.setBackground(Color.WHITE);

		add(pnlSelect, BorderLayout.NORTH);
		add(pnlQuiz, BorderLayout.CENTER);

		lblQuiz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getFolder();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {				
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image cursorimage=tk.getImage("img\\penred.png");					    
				Cursor cursor=tk.createCustomCursor(cursorimage,new Point(0,0),"haha");				
				setCursor(cursor);	
			}

			@Override
			public void mouseExited(MouseEvent e) {				
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));	
			}			
			
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeWindow();
			}
		});

		setTitle("시험 선택");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void getFolder() {
		JFileChooser ch = new JFileChooser("D:\\test");
		dispose();
		ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		ch.setApproveButtonText("선택");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("시험파일", "txt");
		ch.setFileFilter(filter);	
		int result = ch.showOpenDialog(null);		
		file = ch.getSelectedFile();
		if(file==null) {
			setVisible(true);
		}else {
			if(!file.getName().equals("test")) {			
				if (result == JFileChooser.APPROVE_OPTION) {				
					new QuizFile(file, login);
				}else {
					setVisible(true);
				}
			}else {				
				JOptionPane.showMessageDialog(this, "해당 시험 파일을 선택하세요!");
				getFolder();
				
			}
		}			
	}
	

	private void closeWindow() {
		int choice = JOptionPane.showConfirmDialog(this, "시험연습장 프로그램을 종료 하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.OK_OPTION) {
			login.getSave();
			login.saveDidExam();
			System.exit(0);
		}
	}

}
