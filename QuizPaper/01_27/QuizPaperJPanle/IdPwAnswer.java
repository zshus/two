package QuizPaperJPanle;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class IdPwAnswer extends JDialog {
	private JLabel lblId;
	private JLabel lblPw;
	private JLabel lblOk;
	private String strId;
	private String strPw;

	public IdPwAnswer(IdPwQuestion owner, String strId, String strPw) {
		super(owner, "ID/PW", true);
		this.strId = strId;
		this.strPw = strPw;
		lblId = new JLabel("ID: " + strId);
		lblPw = new JLabel("PW: " + strPw);
		ImageIcon imgOk = new ImageIcon("img\\확인.png");
		lblOk = new JLabel(imgOk);

		JPanel pnlMain = new JPanel(new GridLayout(0, 1));
		pnlMain.add(lblId);
		pnlMain.add(lblPw);
		pnlMain.add(lblOk);

		pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

		add(pnlMain);

		lblOk.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				owner.show_login();
				dispose();
			}

		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				owner.show_login();
				dispose();
			}
		});

		setSize(200, 200);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
