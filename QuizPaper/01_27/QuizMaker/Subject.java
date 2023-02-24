package QuizMaker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class Subject implements Serializable {	
	private String test;
	private String testTime;
	private String subjectName;
	private String subjectMaker;
	private String totalScore;
	private Vector<Quiz> quizs;
	
	public Subject() {
		Vector<Quiz> v = new Vector<Quiz>();
		v.add(new Quiz());
		setQuizs(v);
	}
	
	public Subject(String subjectName) {
		Vector<Quiz> v = new Vector<Quiz>();
		v.add(new Quiz());
		setQuizs(v);
		setSubjectName(subjectName);
	}
	
	public Subject(String test, String testTime, String subjectName, String subjectMaker, String totalScore, Vector<Quiz> quizs) {
		setTest(test);
		setTestTime(testTime);
		setSubjectName(subjectName);
		setSubjectMaker(subjectMaker);
		setTotalScore(totalScore);
		setQuizs(quizs);
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectMaker() {
		return subjectMaker;
	}
	public void setSubjectMaker(String subjectMaker) {
		this.subjectMaker = subjectMaker;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public Vector<Quiz> getQuizs() {
		return quizs;
	}
	public void setQuizs(Vector<Quiz> quizs) {
		this.quizs = quizs;
	}	
	
	@Override
	public String toString() {
		return "Subject [test=" + test + ", testTime=" + testTime + ", subjectName=" + subjectName + ", subjectMaker="
				+ subjectMaker + ", totalScore=" + totalScore + ", quizs=" + quizs + "]";
	}
}
