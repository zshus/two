package QuizMaker;


import java.awt.Image;
import java.io.Serializable;
import java.util.Arrays;

public class Quiz implements Serializable {
	private static final long serialVersionUID = 1L;
	String quizTitle;//title
	double score;//점수
	String passage;//문제?
	String explanation;//물이
	String[] options;//선택지에 들어가는 글자들?
	byte[][] fpImg;//질문의 이미지
	String[] fpImgInfo;//질문의 이미지의 설명
	byte[][] exampleImg;//답의 이미지
	int[] answer;//정답
	
	public Quiz(String quizTitle, double score, String passage, String explanation, String[] options,
			byte[][] fpImg, String[] fpImgInfo, byte[][] exampleImg, int[] answer) {
		setQuizTitle(quizTitle);
		setScore(score);
		setPassage(passage);
		setExplanation(explanation);
		setOptions(options);
		setFpImg(fpImg);
		setFpImgInfo(fpImgInfo);
		setExampleImg(exampleImg);
		setAnswer(answer);
	}
	
	public Quiz() {}
	
	public String getQuizTitle() {
		return quizTitle;
	}
	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getPassage() {
		return passage;
	}
	public void setPassage(String passage) {
		this.passage = passage;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String[] getOptions() {
		return options;
	}
	public void setOptions(String[] options) {
		this.options = options;
	}
	public byte[][] getFpImg() {
		return fpImg;
	}
	public void setFpImg(byte[][] fpImg) {
		this.fpImg = fpImg;
	}
	public String[] getFpImgInfo() {
		return fpImgInfo;
	}
	public void setFpImgInfo(String[] fpImgInfo) {
		this.fpImgInfo = fpImgInfo;
	}
	public byte[][] getExampleImg() {
		return exampleImg;
	}
	public void setExampleImg(byte[][] exampleImg) {
		this.exampleImg = exampleImg;
	}
	public int[] getAnswer() {
		return answer;
	}
	public void setAnswer(int[] answer) {
		this.answer = answer;
	}
	public void setAll(String quizTitle, double score, String passage, String explanation, String[] options,
			byte[][] fpImg, String[] fpImgInfo, byte[][] exampleImg, int[] answer) {
		setQuizTitle(quizTitle);
		setScore(score);
		setPassage(passage);
		setExplanation(explanation);
		setOptions(options);
		setFpImg(fpImg);
		setFpImgInfo(fpImgInfo);
		setExampleImg(exampleImg);
		setAnswer(answer);
	}

	@Override
	public String toString() {
		return "Quiz [quizTitle=" + quizTitle + ", score=" + score + ", passage=" + passage + ", explanation="
				+ explanation + ", options=" + Arrays.toString(options) + ", fpImg=" + Arrays.toString(fpImg)
				+ ", fpImgInfo=" + Arrays.toString(fpImgInfo) + ", exampleImg=" + Arrays.toString(exampleImg)
				+ ", answer=" + Arrays.toString(answer) + "]";
	}
}
