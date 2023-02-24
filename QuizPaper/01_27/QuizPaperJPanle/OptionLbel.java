package QuizPaperJPanle;

import javax.swing.JLabel;

public class OptionLbel {
	
	
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl3;
	private JLabel lbl4;
	private JLabel lbl5;
	private JLabel lbl6;
	private JLabel lbl7;
	private JLabel lbl8;
	private JLabel lbl9;
	
	private JLabel[] lblAll;
	public OptionLbel() {
		
	}
	
	public JLabel[] getLblAll() {
		JLabel[] lblarr=new JLabel[10];
		lbl1=new JLabel();
		lbl2=new JLabel();
		lbl3=new JLabel();
		lbl4=new JLabel();
		lbl5=new JLabel();
		lbl6=new JLabel();
		lbl7=new JLabel();
		lbl8=new JLabel();
		lbl9=new JLabel();
		
		lblarr[1]=lbl1;
		lblarr[2]=lbl2;
		lblarr[3]=lbl3;
		lblarr[4]=lbl4;
		lblarr[5]=lbl5;
		lblarr[6]=lbl6;
		lblarr[7]=lbl7;
		lblarr[8]=lbl8;
		lblarr[9]=lbl9;

		
		return lblarr;
	}
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
