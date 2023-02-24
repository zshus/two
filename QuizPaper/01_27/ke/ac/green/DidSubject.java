package ke.ac.green;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

public class DidSubject implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String subName;
	private String gotScore;
	private String subScore;
	private Vector<Integer> worngTestItems;
	private QuizAnswer[] answers;

	public QuizAnswer[] getAnswers() {
		return answers;
	}


	public void setAnswers(QuizAnswer[] answers) {
		this.answers = answers;
	}


	public DidSubject(String subName, String gotScore, String subScore, Vector<Integer> worngTestItems,QuizAnswer[] answers) {
		this.subName = subName;
		this.gotScore = gotScore;
		this.subScore = subScore;
		this.worngTestItems = worngTestItems;
		this.answers=answers;
	}


	public String getSubName() {
		return subName;
	}





	public void setSubName(String subName) {
		this.subName = subName;
	}





	public String getGotScore() {
		return gotScore;
	}





	public void setGotScore(String gotScore) {
		this.gotScore = gotScore;
	}





	public String getSubScore() {
		return subScore;
	}





	public void setSubScore(String subScore) {
		this.subScore = subScore;
	}





	public Vector<Integer> getWorngTestItems() {
		return worngTestItems;
	}





	public void setWorngTestItems(Vector<Integer> worngTestItems) {
		this.worngTestItems = worngTestItems;
	}





	@Override
	public String toString() {
		return "DidSubject [subName=" + subName + ", gotScore=" + gotScore + ", subScore=" + subScore
				+ ", worngTestItems=" + worngTestItems + ", answers=" + Arrays.toString(answers) + "]";
	}

}
