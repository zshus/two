package QuizPaperJPanle;


import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class DidExam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File path;
	private String testName;
	private Vector<DidSubject> subjectList;
	private String testTime;
	private String testDay;
	private List<String> selsetedList;

	public DidExam(File path,String testName,Vector<DidSubject> subjectList, String testTime, String testDay,List<String> selsetedList) {
		super();
		this.path=path;
		this.testName = testName;
		this.subjectList = subjectList;
		this.testTime = testTime;
		this.testDay = testDay;
		this.selsetedList=selsetedList;
	}
	


	public List<String> getSelsetedList() {
		return selsetedList;
	}



	public void setSelsetedList(List<String> selsetedList) {
		this.selsetedList = selsetedList;
	}



	public File getPath() {
		return path;
	}



	public void setPath(File path) {
		this.path = path;
	}



	public String getTestName() {
		return testName;
	}





	public void setTestName(String testName) {
		this.testName = testName;
	}





	public Vector<DidSubject> getSubjectList() {
		return subjectList;
	}





	public void setSubjectList(Vector<DidSubject> subjectList) {
		this.subjectList = subjectList;
	}





	public String getTestTime() {
		return testTime;
	}





	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}





	public String getTestDay() {
		return testDay;
	}





	public void setTestDay(String testDay) {
		this.testDay = testDay;
	}



	@Override
	public String toString() {
		return "DidExam [testName=" + testName + ", subjectList=" + subjectList + ", testTime=" + testTime
				+ ", testDay=" + testDay + "]";
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

