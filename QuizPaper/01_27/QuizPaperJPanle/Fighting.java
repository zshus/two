package QuizPaperJPanle;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

// 격려 메세지 창

public class Fighting extends JDialog {
	private JLabel lblImg;
	private JLabel lblStr;
	private GradeCheck gradeCheck;
	
	public Fighting(GradeCheck gradeCheck) {
		this.gradeCheck = gradeCheck;
		
		Image img = new ImageIcon("img\\cheer1.png").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(img);
		lblImg = new JLabel(icon);
		lblStr = new JLabel("아쉽지만, 조금 더 노력하세요!");
		lblStr.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 24));
		
		JPanel lblCenter = new JPanel();
		lblCenter.add(lblImg);
		lblCenter.add(lblStr);
		
		add(lblCenter, BorderLayout.CENTER);
		lblCenter.setBorder(new LineBorder(Color.LIGHT_GRAY));
		lblCenter.setBackground(Color.WHITE);
		
		
		
		setTitle("격려 메세지");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
}
