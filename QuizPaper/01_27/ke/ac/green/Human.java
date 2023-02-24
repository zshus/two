package ke.ac.green;


import javax.swing.JOptionPane;

public class Human {
	private String name;
	private String tel;
	private String id;
	private String pw;
	private String question;
	private String answer;
	public Human() {
		super();
		
	}
	public Human(String name, String tel, String id, String pw, String question, String answer) {
		super();
		this.name = name;
		this.tel=tel;
		this.id = id;
		this.pw = pw;
		setQuestion(question);
		this.answer = answer;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;	
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPw() {
		return pw;
	}



	public void setPw(String pw) {
		this.pw = pw;
	}



	public String getQuestion() {
		return question;
	}



	public void setQuestion(String question) {
		if(!question.equals("-선택-")) {
			this.question = question;
		}else {
			JOptionPane.showMessageDialog(null, "질문을 선택해 주세요!");
		}
		
	}



	public String getAnswer() {
		return answer;
	}



	public void setAnswer(String answer) {
		this.answer = answer;
	}



	@Override
	public String toString() {
		return "Human [name=" + name + ", tel=" + tel + ", id=" + id + ", pw=" + pw + ", question=" + question
				+ ", answer=" + answer + "]";
	}



	public static void main(String[] args) {
		

	}

}
