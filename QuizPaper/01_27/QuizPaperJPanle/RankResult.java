package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

// 능력자 순위 창

public class RankResult extends JFrame {

	private JLabel lblTitle; // 상단 제목

	private String[] rankNums = { "1", "2", "3", "4", "5" };
	private JLabel[] rankNum; // 1~5위

	private JLabel[] userImg; // 사용자 이미지
	private JLabel[] userName; // 사용자 이름
	private JLabel[] imgCalendar; // 달력 이미지
	private JLabel[] testDate; // 시험날짜
	private JLabel[] totalScore; // 총점수
	private JLabel[] imgClock; // 시계 이미지
	private JLabel[] time; // 걸린시간

	private GradeCheck gradeCheck;

	private JLabel[] tag1st; // 1위 태그

	private Font lblFont;
	private Vector<TopGrade> topList;
	private int len;
	private Dimension psize;

	public RankResult(GradeCheck gradeCheck) {
		this.gradeCheck = gradeCheck;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		topList = gradeCheck.getTopList();
//		topList.sort((o1, o2) -> Double.valueOf(o2.getTotalScore()).compareTo(o1.getTotalScore()));
		topList.sort((o1, o2)-> {
			int num = Double.valueOf(o2.getTotalScore()).compareTo(o1.getTotalScore());
			num = num == 0 ? o1.getSpendedTime().compareTo(o2.getSpendedTime()) : num;				
			return num;
		});
		if (topList.size() > 5) {
			len = 5;
		} else {
			len = topList.size();
		}
		lblFont = new Font("HY견고딕", Font.BOLD, 16);

		lblTitle = new JLabel("능력자 순위", JLabel.CENTER);
		lblTitle.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 29));
		psize = new Dimension(100, 50);

		rankNum = new JLabel[len];
		userImg = new JLabel[len];
		userName = new JLabel[len];
		imgCalendar = new JLabel[len];
		testDate = new JLabel[len];
		totalScore = new JLabel[len];
		imgClock = new JLabel[len];
		time = new JLabel[len];
		tag1st = new JLabel[len];
	}

	private void setDisplay() {
		JPanel pnlNorth = new JPanel();
		pnlNorth.setBackground(Color.WHITE);
		pnlNorth.add(lblTitle);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < len; i++) {
			JPanel pnlRank = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
			rankNum[i] = new JLabel(rankNums[i], JLabel.LEFT);
			rankNum[i].setFont(lblFont);
			pnlRank.add(rankNum[i]);

			userImg[i] = new JLabel(getIcon("img\\human1.png", 30, 30));
			pnlRank.add(userImg[i]);

			userName[i] = new JLabel(topList.get(i).getId());
			userName[i].setFont(lblFont);
			userName[i].setPreferredSize(new Dimension(200, 50));
			pnlRank.add(userName[i]);

			imgCalendar[i] = new JLabel(getIcon("img\\date.png", 50, 50));
			pnlRank.add(imgCalendar[i]);

			testDate[i] = new JLabel(topList.get(i).getDay());
			testDate[i].setFont(lblFont);
			testDate[i].setPreferredSize(new Dimension(200, 50));
			pnlRank.add(testDate[i]);

			totalScore[i] = new JLabel("총점: " + topList.get(i).getTotalScore());
			totalScore[i].setFont(lblFont);
			totalScore[i].setPreferredSize(new Dimension(150, 50));
			pnlRank.add(totalScore[i]);

			imgClock[i] = new JLabel(getIcon("img\\clock.png", 30, 30));
			pnlRank.add(imgClock[i]);

			time[i] = new JLabel(topList.get(i).getSpendedTime());
			time[i].setFont(lblFont);
			time[i].setPreferredSize(psize);
			pnlRank.add(time[i]);

			tag1st[i] = new JLabel(getIcon("img\\first.png", 50, 50));
			if (i == 0) {
				pnlRank.add(tag1st[i]);
			} else {
				pnlRank.add(new JLabel(""));
			}

			pnlRank.setBackground(new Color(196, 222, 255));
			pnlRank.setBorder(new LineBorder(Color.BLACK, 1));
			pnlCenter.add(pnlRank);
		}
		pnlCenter.setBorder(new EmptyBorder(15, 50, 40, 50));
		pnlCenter.setBackground(Color.WHITE);

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
	}

	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				gradeCheck.setVisible(true);
				setVisible(false);
			}
		});
	}

	private ImageIcon getIcon(String img, int size1, int size2) {
		Image image = new ImageIcon(img).getImage().getScaledInstance(size1, size2, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}

	private void showFrame() {
		setTitle("능력자순위");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

}
