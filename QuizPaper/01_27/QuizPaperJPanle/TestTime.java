package QuizPaperJPanle;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TestTime extends JFrame {
	private JLabel lblHour;
	private JLabel lblMin;
	private JLabel lblSec;

	private JLabel lblTime;
	private Font timerFont;
	private String testingTimer;
	private long duration;
	private TestPaper testpaper;
	private Timer timer;
	private int hour;
	private int minute;
	private int second;
	private boolean timeOver = false;
	private String timestr;

	public TestTime(TestPaper testpaper, String timestr) {
		this.timestr = timestr;
		this.testpaper = testpaper;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		int timeNum = 0;
		if (timestr != null) {
			timeNum = Integer.parseInt(timestr);
		}

		hour = timeNum / 60;
		minute = timeNum % 60;
		second = 0;
		timerFont = new Font("맑은고딕", Font.BOLD, 50);
		lblHour = new JLabel("00", JLabel.CENTER);
		lblHour.setFont(timerFont);

		lblMin = new JLabel("00", JLabel.CENTER);
		lblMin.setFont(timerFont);

		lblSec = new JLabel("00", JLabel.CENTER);
		lblSec.setFont(timerFont);

		lblHour.setText(String.format("%02d", hour));
		lblMin.setText(String.format("%02d", minute));
		lblSec.setText(String.format("%02d", second));
	}

	JPanel pnlCenter;

	private void setDisplay() {
		pnlCenter = new JPanel();
		pnlCenter.add(lblHour);
		pnlCenter.add(getSeparator());
		pnlCenter.add(lblMin);
		pnlCenter.add(getSeparator());
		pnlCenter.add(lblSec);
		add(pnlCenter, BorderLayout.CENTER);
	}

	private void addListeners() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTime();
			}
		};

		timer = new Timer(1000, listener);
		timer.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int num = JOptionPane.showConfirmDialog(TestTime.this, "시계를 끄겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (num == JOptionPane.YES_OPTION) {
					setVisible(false);
				}
			}
		});
	}

	private void showFrame() {
		setTitle("시간확인");
		pack();		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
	}

	private void setTime() {
		if (second == 0) {
			if (minute == 0) {
				if (hour != 0) {
					hour = hour - 1;
					minute = 59;
				}
			} else {
				minute = minute - 1;
			}
			second = 60;
		}
		second--;
		if (hour == 0 && minute == 0 && second == 0) {
			timer.stop();
			lblHour.setText(String.format("%02d", 0));
			lblMin.setText(String.format("%02d", 0));
			lblSec.setText(String.format("%02d", 0));
			setLocationRelativeTo(testpaper);
			setVisible(false);
			timeOver = true;

		} else {
			lblHour.setText(String.format("%02d", hour));
			lblMin.setText(String.format("%02d", minute));
			lblSec.setText(String.format("%02d", second));
		}

	}

	private JLabel getSeparator() {
		JLabel lbl = new JLabel(":", JLabel.CENTER);
		lbl.setFont(timerFont);
		return lbl;
	}

	public void getTimeStopOrStart() {
		if (timer.isRunning()) {
			int num = JOptionPane.showConfirmDialog(testpaper, "시간을 멈추겠습니까?", "알림", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (num == JOptionPane.YES_OPTION) {
				timer.stop();
				setVisible(true);
			}
		} else {
			if (!timeOver) {
				int num = JOptionPane.showConfirmDialog(testpaper, "시간을 다시 활성화 하시겠습니까?", "알림",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (num == JOptionPane.YES_OPTION) {
					timer.start();
					setVisible(true);
				}
			} else {
				JOptionPane.showMessageDialog(testpaper, "시간 초과 되었으니 시험 끝났습니다!");
			}
		}
	}

	public String getRestTime() {
		if (timer.isRunning()) {
			timer.stop();

		}
		setVisible(false);
		String str = "";
		int htemp = Integer.parseInt(lblHour.getText());
		int mtemp = Integer.parseInt(lblMin.getText());
		int temp = mtemp + htemp * 60;
		str = String.valueOf(temp);
		return str;
	}

	public Timer getTimer() {
		return timer;
	}

	public boolean getTimerStatus() {
		return timeOver;
	}

}
