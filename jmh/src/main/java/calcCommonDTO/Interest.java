package calcCommonDTO;

public class Interest {

	private double rate;
	private int    endDate;
	
	public Interest(int date, double rate) {
		// TODO 磊悼 积己等 积己磊 胶庞
		this.endDate = date;
		this.rate = rate;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}
	

}
