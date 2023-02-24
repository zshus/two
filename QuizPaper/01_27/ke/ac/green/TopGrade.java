package ke.ac.green;


import java.io.Serializable;

public class TopGrade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String id;
	private String day;
	private double totalScore;
	private String spendedTime;

	public TopGrade(String id, String day, double totalScore, String spendedTime) {
		super();
		this.id = id;
		this.day = day;
		this.totalScore = totalScore;
		this.spendedTime = spendedTime;
	}

	public TopGrade() {
		super();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public String getSpendedTime() {
		return spendedTime;
	}

	public void setSpendedTime(String spendedTime) {
		this.spendedTime = spendedTime;
	}

	@Override
	public String toString() {
		return "TopGrade [id=" + id + ", day=" + day + ", totalScore=" + totalScore + ", spendedTime=" + spendedTime
				+ "]";
	}

}

