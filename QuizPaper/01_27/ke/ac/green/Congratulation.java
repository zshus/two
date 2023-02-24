package ke.ac.green;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

// 축하메세지 창

public class Congratulation extends JFrame {
	private JLabel lblImg;
	private JLabel lblStr;
	private JButton btnok;
	private int grade;
	
	
	public Congratulation() {
		btnok=new JButton("OK");
//		Image img = new ImageIcon("img\\cheer2.png").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
//		ImageIcon icon = new ImageIcon(img);
//		lblImg = new JLabel(icon);
		lblStr = new JLabel("해당 시험 파일을 선택하세요!",JLabel.CENTER );
		lblStr.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblStr.setPreferredSize(new Dimension(240,30));
		
		JPanel lblCenter = new JPanel(new GridLayout(0,1));
		
		
		JPanel pnl=new JPanel();
		pnl.setBackground(Color.WHITE);
		pnl.add(btnok);
		
		lblCenter.add(lblStr);
		lblCenter.add(pnl);
		
		add(lblCenter, BorderLayout.CENTER);
		
		
		lblCenter.setBorder(new LineBorder(Color.LIGHT_GRAY));
		lblCenter.setBackground(Color.WHITE);
			
		btnok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		setTitle("알림");
		pack();
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new Congratulation();
	}
	
}

