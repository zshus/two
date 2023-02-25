package QuizPaperJPanle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;

public class QuizAnswer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int num;
	private Vector<Integer> answer;
	private JLabel[] options;
	private int itemSelected;

	public QuizAnswer(int num,Vector<Integer> answer, JLabel[] options, int itemSelected) {
		super();
		this.num=num;
		this.answer = answer;
		this.options = options;
		this.itemSelected = itemSelected;
	}




	public int getNum() {
		return num;
	}




	public void setNum(int num) {
		this.num = num;
	}




	public Vector<Integer> getAnswer() {
		return answer;
	}




	public void setAnswer(Vector<Integer> answer) {
		this.answer = answer;
	}




	public JLabel[] getOptions() {
		return options;
	}




	public void setOptions(JLabel[] options) {
		this.options = options;
	}




	public int getItemSelected() {
		return itemSelected;
	}




	public void setItemSelected(int itemSelected) {
		this.itemSelected = itemSelected;
	}


	@Override
	public String toString() {
		return "QuizAnswer [num=" + num + ", answer=" + answer + ", options=" + Arrays.toString(options)
				+ ", itemSelected=" + itemSelected + "]";
	}




	public static void main(String[] args) {
		

	}

}
